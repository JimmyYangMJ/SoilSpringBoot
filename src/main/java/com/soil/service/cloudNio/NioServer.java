package com.soil.service.cloudNio;


import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.*;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;


public class NioServer implements Runnable{


	public static Map<Integer, SelectionKey> ClientMap = new HashMap<>();

	private static final int PORT = 8001;

	/**
	 * 通道初始化
	 * @param selector 多路选择器
	 * @param serverSocketChannel 监听套接字通道
	 */
	private static void socketChannelInitial(Selector selector, ServerSocketChannel serverSocketChannel) {
		try {
			serverSocketChannel.configureBlocking(false); // 开启非阻塞模式
			serverSocketChannel.socket().bind(new InetSocketAddress(PORT), 1024); // 驻守在 port端口
			System.out.println("===accept 接受===");
			serverSocketChannel.register(selector, SelectionKey.OP_ACCEPT); // 绑定多路选择器+通道
			System.out.printf("===服务器在%d端口守候===\n", PORT);
		} catch (IOException e) {
			e.printStackTrace();
			System.exit(1);
		}
	}

	/**
	 * 监听 客户端 消息
	 */
	@Override
	public void run() {

		Selector selector = null;
		ServerSocketChannel serverSocketChannel = null;

		/** 通道初始化 */
		try {
			selector = Selector.open(); // 产生多路选择器
			serverSocketChannel = ServerSocketChannel.open(); // 开启 socket通道（Channel）
		} catch (IOException e) {
			e.printStackTrace();
		}

		/** 通道初始化 */
		socketChannelInitial(selector, serverSocketChannel);

		/** 开启线程
		 * 1. 心跳包-线程
		 * 2. 发送信息-线程*/
		startThread();

		while(true) {
			try {
				selector.select(1000); // 多路选择器开始轮询 通道最多阻塞2000ms

				Set<SelectionKey> selectedKeys = selector.selectedKeys();
				Iterator<SelectionKey> it = selectedKeys.iterator();
				SelectionKey key = null;
				while (it.hasNext()) {
					key = it.next();
					it.remove();
					try {
						/** 开始处理通道 */
						handleInput(selector, key);
					} catch (Exception e) {
						if (key != null) {
							key.cancel();
							if (key.channel() != null)
								key.channel().close();
						}
					}
				}
			} catch(Exception ex) {
				ex.printStackTrace();
			}


		}
	}

	/**
	 * 处理通道连接(接收消息)
 	 * @param selector 选择器
	 * @param selectionKey 选择通道对应key
	 * @throws IOException
	 */
    public static void handleInput(Selector selector, SelectionKey selectionKey) throws IOException {

		if (selectionKey.isValid()) {
			// 处理新接入的请求消息
			if (selectionKey.isAcceptable()) { // 连接刚刚建立
				// Accept the new connection
				ServerSocketChannel ssc = (ServerSocketChannel) selectionKey.channel();
				SocketChannel socketChannel = ssc.accept();
				socketChannel.configureBlocking(false);
				// Add the new connection to the selector
				System.out.println("读取");
				socketChannel.register(selector, SelectionKey.OP_READ);
				System.out.println("==有一个新的连接==" + selectionKey.channel());
			}

			/** 接受消息 */
			if (selectionKey.isReadable()) { // 可以读数据
				// Read the data
				SocketChannel sc = (SocketChannel) selectionKey.channel();
				ByteBuffer readBuffer = ByteBuffer.allocate(1024);
				int readBytes = sc.read(readBuffer);
				if (readBytes > 0) {
					readBuffer.flip();
					byte[] bytes = new byte[readBuffer.remaining()];
					readBuffer.get(bytes);
					/** request 接受到的消息 */
					String requestMessage = new String(bytes, "UTF-8"); //接收到的输入


					/** 消息处理 */
					int status = messageHandel(requestMessage);

					/**  格式：[client_id]：[message]
					 * <[参数个数],[参数类型],[发送端],···,[参数n]> */
					/** 发送端 */
					int client_id = Integer.parseInt(requestMessage.split(",")[2]);

					/** 记录客户的连接通道 */
					if(ClientMap.containsValue(selectionKey) == false) { //新的通道连接
						if (ClientMap.containsKey(client_id)) { // 之前注册过
							System.out.printf("客户 %d 重新连接\n", client_id);
						}else {
							System.out.printf("客户 %d 初次登陆\n", client_id);
						}
						ClientMap.put(client_id, selectionKey);
					}

					/** 接受消息 返回给客户端确认 */
					doWrite(sc, "<0,accept,0>"); //  + System.getProperty("line.separator")
				} else if (readBytes < 0) {
					// 对端链路关闭
					selectionKey.cancel();
					sc.close();
				} else
					; // 读到0字节，忽略
			}
		}
	}

	/**
	 * 开启 <br>
	 * 1. 心跳包Thread <br>
	 * 2. 发送消息（向客户端）Thread
	 */
	private static void startThread(){
		/** 向客户端发送心跳包-线程*/
		NioServerTick nioServerTick = new NioServerTick();
		Thread tick = new Thread(nioServerTick);
		tick.setDaemon(true);
		tick.start();

		/** 向指定客户端发送信息-线程*/
		NioServerWrite nioServerWrite = new NioServerWrite();
		Thread write = new Thread(nioServerWrite);
		write.setDaemon(true);
		write.start();
	}

	/**
	 * 处理接受的数据
	 * <[参数个数], [参数类型], [发送端], ···, [参数n],>
	 * @param message
	 * @return  1：消息的确认, 2：连接请求 3 水势数据, 4主动关闭连接
	 */
	public static int messageHandel(String message){

		String[] m = message.split(",");

		if(m[1].trim().equals("accept")){
			return 1;
		}else if(m[1].trim().equals("request")){

			return 2;
		}else if(m[1].trim().equals("shutdown")){
			// Todo 主动关闭连接
			return 4;
		}else{
			/**
			 * 1. 水势数据
			 * <[参数个数],[参数类型],[id],[AD],[AD_base],[水势值],[时间],>
			 *  <6,1,1,1.2,1.5,6.4,2020-06-06 12:12:13,>
			 */
			System.out.println(message);
			return 3;
		}
	}

	/**
	 * @param socketChannel
	 * @param response
	 * @return -1: 通道连接断开， 1；数据已发送， -2：没有要发送的数据
	 * @throws IOException
	 */
	public static int doWrite(SocketChannel socketChannel, String response) throws IOException{
		if (response != null && response.trim().length() > 0) {

			byte[] bytes = response.getBytes();
			ByteBuffer writeBuffer = ByteBuffer.allocate(bytes.length);


			writeBuffer.put(bytes);
			writeBuffer.flip();

			try {

				//System.out.println("状态：" + socketChannel.isConnected());
				if (socketChannel.isConnected() == false){
					System.out.printf("连接断开\n");
					return -1;
				}
				socketChannel.write(writeBuffer);

			}catch (ClosedChannelException e) {
				return -1;
			}

			return 1;
		}
		return -2;
	}

	/**
	 * 指定客户端发送消息
	 * @param id 客户端 id
	 * @param message 消息
	 * @return -1: 通道连接断开， 1；数据已发送， -2：没有要发送的数据
	 */
	public static int messageSend(int id, String message) throws IOException{

		int answer = 1;
		if (ClientMap.containsKey(id) == true) {

			SocketChannel sc = (SocketChannel) ClientMap.get(id).channel();
			answer = doWrite(sc, message); //  + System.getProperty("line.separator")

		}else {
			System.out.println("没有此用户");
		}
		return  answer;

	}


	/**
	 * 关闭通道连接
	 * @param _clientSocket
	 */
	public void ShutDownClient(SocketChannel _clientSocket)
	{
		if(_clientSocket.isOpen())
		{
			try
			{
				//将连接的输入输出都关闭，而不是直接Close连接
				_clientSocket.shutdownInput();
				_clientSocket.shutdownOutput();
				ClientMap.remove(_clientSocket);
				System.out.println("客户端无响应：" + _clientSocket.socket().getPort());
			}
			catch (Exception e)
			{
				e.printStackTrace();
			}
		}
	}
}
