package com.soil.controller;

import com.soil.common.ServerResponse;
import com.soil.pojo.SoilNode;
import com.soil.pojo.SoilNodeLocation;
import com.soil.service.cloudNio.NioServerWrite;
import com.soil.service.impl.SoilNodeServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpSession;
import java.util.Date;
import java.util.List;

/**
 * 下位机节点信息
 * 1. 查询结点位置信息 GET
 * 2. 更新结点位置信息 PUT
 * 3. 查询结点所有信息 GET
 * 4. 增加结点 POST
 * 5. 删除结点 DELETE
 * @author ymj
 * @Date： 2019/11/13 20:35
 */
@Controller("SoilNodeController")
@RequestMapping("/soilNode")
public class SoilNodeController {

    @Autowired
    private SoilNodeServiceImpl soilNodeService;


    /**
     * 1.查询所有节点信息 接口
     * data 包括：node state，interval_s，lng，lat，last_update
     * @param session 会话
     * @return json序列
     */
    @RequestMapping(value = "selectSoilNodeSet.do", method = RequestMethod.GET)
    @ResponseBody // 使得序列化为json
    public ServerResponse<List<SoilNode>> selectSoilNodeSet(HttpSession session){

        ServerResponse<List<SoilNode>> response = soilNodeService.selectSoilNodeSet();

//        System.out.println(response);
//        if(response.isSuccess()){
//            session.setAttribute(Const.CURRENT_USER,response.getData());
//        }
        return response;
    }


    /**
     * 增加一个结点
     * @param node  结点号
     * @param lng 经度
     * @param lat 纬度
     * @param session 会话
     * @return  结果集
     */
    @RequestMapping(value = "addSoilNode.do", method = RequestMethod.POST)
    @ResponseBody // 使得序列化为json
    public ServerResponse<String> addSoilNode(int node, double lng, double lat, HttpSession session) {
//        List<SoilNode> list  = new ArrayList<>();
//        SimpleDateFormat sdf = new SimpleDateFormat();// 格式化时间
//        sdf.applyPattern("yyyy-MM-dd HH:mm:ss");// a为am/pm的标记
        Date date = new Date();// 获取当前时间

        SoilNode soilNode = new SoilNode(node, "00", 0, lng, lat, date);

        System.out.println(soilNode); // 存入数据库
        return soilNodeService.insertSoilNode(soilNode);
    }

    /**
     * 更新一个结点的位置信息
     * @param node 结点号
     * @param lng 经度
     * @param lat 纬度
     * @param session
     * @return
     */
    @RequestMapping(value = "updateSoilNodeLocation.do" , method = RequestMethod.GET)
//  Todo  @PutMapping(value = "updateSoilNodeLocation.do")
    @ResponseBody // 使得序列化为json
    public ServerResponse<String> updateSoilNodeLocation(int node, @RequestParam(required = false) Double lng, @RequestParam(required = false)  Double lat, HttpSession session) {

        if(lng == null){
            System.out.println("移除此节点");
        }
        System.out.println(node + ": " + lng + ": " + lat); // update数据库
        return soilNodeService.updateSoilNodeLocation(node, lng, lat);
    }



    /**
     * 删除指定结点
     * @param node 结点号
     * @param session
     * @return
     */
    @RequestMapping(value = "deleteSoilNode.do", method = RequestMethod.DELETE)
    @ResponseBody // 使得序列化为json
    public ServerResponse<String> deleteSoilNode(int node, HttpSession session) {
        System.out.println("要删除的结点： " + node); // update数据库
        return soilNodeService.deleterSoilNode(node);
    }


    /**
     * 查询所有结点 位置
     * 接口
     * @param session 会话
     * @return json序列
     */
    @RequestMapping(value = "SoilNodeLocation.do", method = RequestMethod.GET)
    @ResponseBody // 使得序列化为json
    public ServerResponse<List<SoilNodeLocation>> selectSoilNodeLocation(HttpSession session){

        // todo 管理员验证
        ServerResponse<List<SoilNodeLocation>> response = soilNodeService.selectSoilNodeLocationSet();

        System.out.println(response);

        return response;
    }



}
