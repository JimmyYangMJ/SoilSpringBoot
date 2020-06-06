package com.soil.controller;

import com.soil.common.ServerResponse;
import com.soil.pojo.SoilClients;
import com.soil.pojo.SoilTimeList;
import com.soil.service.ISoilClientsService;
import com.soil.service.WebSocket;
import com.soil.service.cloudNio.NioServerWrite;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.util.List;

/**
 * 上位机客户端的信息
 * @author ymj
 * @Date： 2020/6/2 17:21
 */
@Controller("ClientsUMController")
@RequestMapping("/clients")
public class ClientsUMController {


    @Autowired
    private ISoilClientsService iSoilClientsService;

    /**
     * 1. 查询 上位机客户端 信息
     * @param session
     * @return
     */
//    @CrossOrigin(origins = "*")
    @RequestMapping(value = "clientsInfo.do", method = RequestMethod.GET)
    @ResponseBody // 使得序列化为json
    public ServerResponse<List<SoilClients>> clientsInfo(HttpSession session){

        ServerResponse<List<SoilClients>> response = iSoilClientsService.selectSoilClients();

        System.out.println(response);
//        if(response.isSuccess()){
//            session.setAttribute(Const.CURRENT_USER,response.getData());
//        }
        return response;
    }

    /**
     * 2. 向上位机客户端发送消息
     * arouse 唤醒
     * sleep 休眠
     * @param session 会话
     * @return json序列
     */
    @RequestMapping(value = "sendMessage.do", method = RequestMethod.GET)
    @ResponseBody // 使得序列化为json
    public ServerResponse<String> sendMassage(int id, String message, HttpSession session){

        System.out.println("客户机 " + id + " 发送: " + message);
        if (message.equalsIgnoreCase("arouse")) {
            message = String.format("<3,2,%d,%d,>", id,1);
        } else if (message.equalsIgnoreCase("sleep")) {
            message = String.format("<3,2,%d,%d,>", id,2);
        }

        NioServerWrite.setSendId(id);
        NioServerWrite.setSendMessage(message);
        // todo 发送状态
        return ServerResponse.createBySuccessMessage("发送成功");
    }


    public boolean updateClientStatus(int id, String status) {
        return iSoilClientsService.updateClientStatus(id, status);
    }


}
