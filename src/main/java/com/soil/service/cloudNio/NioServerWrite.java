package com.soil.service.cloudNio;

import java.io.IOException;

/**
 * @author ymj
 * @Date： 2020/4/28 20:06
 */
public class NioServerWrite implements Runnable{

    /**
     * 向客户端发送的消息
     */
    private static volatile String sendMessage = null;
    /** 发送 锁 */
    private static volatile int sendId = 0;

    public static void setSendMessage(String sendMessage) {
        NioServerWrite.sendMessage = sendMessage;
    }

    public static void setSendId(int sendId) {
        NioServerWrite.sendId = sendId;
    }

    @Override
    public void run() {

        while (true) {
            if (sendId > 0 && sendMessage != null){
                try {
                    if (NioServer.ClientMap.get(sendId) != null){
                        int answer = NioServer.messageSend(sendId,sendMessage);

                        if (answer == -1){
                            System.out.printf("客户端 %d 连接断开, 通道设为空\n", sendId);

                                NioServer.ClientMap.get(sendId).channel().close();

                            NioServer.ClientMap.put(sendId, null); // 通道设为空
                        }
                    } else {
                        System.out.println("用户：" + sendId + "不存在" );

                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
                sendMessage = null;
                sendId = 0;
            }
        }
    }
}
