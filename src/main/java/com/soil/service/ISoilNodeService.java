package com.soil.service;

import com.soil.common.ServerResponse;
import com.soil.pojo.SoilNode;
import com.soil.pojo.SoilNodeLocation;

import java.util.List;

/**
 * 查询结点信息
 * @author ymj
 * @Date： 2019/11/13 20:18
 */
public interface ISoilNodeService {

    /**
     * 查询结点相关的所有信息
     * @return 结点相关信息 List
     */
    ServerResponse<List<SoilNode>> selectSoilNodeSet();


    /**
     * 增加一个结点
     * @param soilNode 结点
     * @return
     */
    ServerResponse<String> insertSoilNode(SoilNode soilNode);

    /**
     * 修改结点位置信息
     * @param node 结点号
     * @param lng 经度
     * @param lat 纬度
     * @return
     */
    ServerResponse<String> updateSoilNodeLocation(int node, Double lng, Double lat);

    /**
     * 删除一个结点（结点号）
     * @param node 结点号
     * @return
     */
    ServerResponse<String> deleterSoilNode(int node);

    /**
     * 查询所有结点位置信息
     * @return 结点相关位置 List
     */
    ServerResponse<List<SoilNodeLocation>> selectSoilNodeLocationSet();



}
