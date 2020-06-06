package com.soil.service.cloudNio;

import com.soil.common.ServerResponse;
import com.soil.controller.ClientsUMController;
import com.soil.pojo.SoilClients;
import com.soil.service.ISoilClientsService;
import com.soil.service.impl.SoilClientsServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Controller;

import javax.annotation.PostConstruct;
import java.io.IOException;
import java.util.Iterator;
import java.util.List;

/**
 * 实时心跳包
 * @author ymj
 * @Date： 2020/4/28 19:56
 */
@Component
public class NioServerTick implements Runnable{

    @Autowired
    private ISoilClientsService iSoilClientsService;

    /** 在 非 Spring 类中 使用 */
    public static NioServerTick nioServerTick;
    @PostConstruct
    public void init(){
        nioServerTick = this;
    }

    /**
     * 发送心跳包
     */
    @Override
    public void run() {
        int tag = 0; // 更新数据库标记
        while (true) {
            int id_key = 0;
            try {
                Iterator<Integer> iterator = NioServer.ClientMap.keySet().iterator();
                while (iterator.hasNext()){
                    id_key = iterator.next();
                    if (NioServer.ClientMap.get(id_key) != null){
                        /** 发送心跳包 */
                        int answer = NioServer.messageSend(id_key,"<tick>");
                        if (tag == 0){
                            nioServerTick.iSoilClientsService.updateClientStatus(id_key, "online"); // 更新数据库中内容
                            System.out.println("更新上位机状态");
                            tag = 1;
                        }
                        if (answer == -1){ // 连接断开
                            System.out.printf("客户端 %d 连接断开, 通道设为空\n", id_key);
                            nioServerTick.iSoilClientsService.updateClientStatus(id_key, "offline");
                            tag = 0;
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
