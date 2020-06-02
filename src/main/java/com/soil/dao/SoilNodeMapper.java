package com.soil.dao;

import com.soil.pojo.SoilNode;
import com.soil.pojo.SoilNodeLocation;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @author ymj
 * @Date： 2019/11/13 20:24
 */
public interface SoilNodeMapper {

    /**
     * 查询结点的 详细信息
     * @return 结点信息表
     */
    List<SoilNode> selectSoilNodeSet();

    /**
     * 增加一个结点
     * @param soilNode 结点
     * @return
     */
    int insertSoilNode(SoilNode soilNode);


    /**
     * 修改结点 位置信息
     * @param node 结点号
     * @param lng 经度
     * @param lat 纬度
     * @return
     */
    int updateSoilNodeLocation(@Param("node") int node, @Param("lng") Double lng, @Param("lat") Double lat);

    /**
     * 删除一个结点
     * @param node
     * @return
     */
    int deleteSoilNode(@Param("node") int node);


    /**
     * 查询结点的 位置信息
     * @return 结点信息表
     */
    List<SoilNodeLocation> selectSoilNodeLocationSet();




}
