package com.soil.controller;

import com.soil.common.Const;
import com.soil.common.ServerResponse;
import com.soil.pojo.SoilTimeList;
import com.soil.pojo.SoilWater;
import com.soil.service.ISoilService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpSession;
import java.util.List;

/**
 * 水势数据查询
 * 1. 查询某天某结点水势 GET
 * 2. 查询某时间段的水势 GET
 * 3. 查询有数据的时间和对应结点 GET
 */
@Controller("SoilController")
@RequestMapping("/soil")
public class SoilController {

    @Autowired
    private ISoilService iSoilService;

    /**
     * 1.查询某天的某节点水势信息
     * 接口
     * @param node 结点
     * @param day 日期
     * @param session 会话
     * @return json文件
     */
    @RequestMapping(value = "selectSoilSet.do", method = RequestMethod.GET)
    @ResponseBody // 使得序列化为json
    public ServerResponse<List<SoilWater> > selectSoilSet(Integer node, String day, HttpSession session){
        day = day + "%";
        System.out.println(day);
        ServerResponse<List<SoilWater>> response = iSoilService.selectSoilSet(node, day);

        System.out.println(response);

        if(response.isSuccess()){
            session.setAttribute(Const.CURRENT_USER,response.getData());
        }
        return response;
    }


    /**
     * 2.查询某时间段的水势
     * @param startTime 开始时间
     * @param endTime 结束时间
     * @param session
     * @return json数据
     */
    @RequestMapping(value = "selectSoilTimeRegionSet.do", method = RequestMethod.GET)
    @ResponseBody // 使得序列化为json
    public ServerResponse<List<SoilWater> > selectSoilTimeRegionSet(String startTime, String endTime, HttpSession session){
        startTime = startTime + "%";
        endTime = endTime + "%";

        System.out.println(startTime + " // " + endTime);
        ServerResponse<List<SoilWater>> response = iSoilService.selectSoilTimeRegionSet(startTime, endTime);

        System.out.println(response);

        if(response.isSuccess()){
            session.setAttribute(Const.CURRENT_USER,response.getData());
        }
        return response;
    }


    /**
     * 3.查询有数据的是哪几天
     * @param session
     * @return
     */
    @RequestMapping(value = "selectSoilWhichTime.do", method = RequestMethod.GET)
    @ResponseBody // 使得序列化为json
    public ServerResponse<List<SoilTimeList>> selectSoilWhichTime(HttpSession session){

        ServerResponse<List<SoilTimeList>> response = iSoilService.selectSoilWhichTime();

        System.out.println(response);
//        if(response.isSuccess()){
//            session.setAttribute(Const.CURRENT_USER,response.getData());
//        }
        return response;
    }

//    @RequestMapping("test.do")
//    public String echartsJSP(){
//        System.out.println("调用了echartsJSP界面");
//        return "test";
//    }
}
