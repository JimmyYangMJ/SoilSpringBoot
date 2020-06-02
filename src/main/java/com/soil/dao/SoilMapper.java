package com.soil.dao;


import com.soil.pojo.SoilTimeList;
import com.soil.pojo.SoilWater;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @author dell
 */
public interface SoilMapper {


    /**
     *  查询某一结点的 某一天的数据
     * @param node 结点编号
     * @param times 查询时间（天）： 2019-09-22%
     * @return json数据
     */
    List<SoilWater> selectSoilSet(@Param("node") Integer node, @Param("times") String times);


    /**
     * 查询某时间段的水势
     * @return  对应水势数据数组
     */
    List<SoilWater> selectSoilTimeRegionSet(@Param("startTimes") String startTimes, @Param("endTimes") String endTimes);


    /**
     * 查询哪几天有数据
     * @return  天
     */
    List<SoilTimeList> selectSoilWhichTime();

}
