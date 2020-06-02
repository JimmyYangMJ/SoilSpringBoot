package com.soil.service.cloudNio;

import java.io.IOException;
import java.util.Iterator;

/**
 * @author ymj
 * @Date： 2020/4/28 19:56
 */
public class NioServerTick implements Runnable{

    /**
     * 发送心跳包
     */
    @Override
    public void run() {
        while (true) {
            int id_key = 0;

            try {
                Iterator<Integer> iterator = NioServer.ClientMap.keySet().iterator();
                while (iterator.hasNext()){
                    id_key = iterator.next();
                    if (NioServer.ClientMap.get(id_key) != null){
                        /** 发送心跳包 */
                        int answer = NioServer.messageSend(id_key,"<tick>");
                        if (answer == -1){
                            System.out.printf("客户端 %d 连接断开, 通道设为空\n", id_key);
                            NioServer.ClientMap.get(id_key).channel().close();
                            NioServer.ClientMap.put(id_key, null); // 通道设为空
                        }
                    }
                }
            }catch (IOException e) {
                e.printStackTrace();
            }
            try {
                Thread.sleep(4000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
