package com.soil.service;

import com.soil.common.ServerResponse;
import com.soil.pojo.SoilTimeList;
import com.soil.pojo.SoilWater;

import java.util.List;

public interface ISoilService {

    /**
     * 查询某结点对应的时间的水势值
     * @param node  选择的结点
     * @param day 选择结点的开始时间(天)
     * @return  对应水势数据数组
     */
    ServerResponse<List<SoilWater>> selectSoilSet(Integer node, String day);

    /**
     * 查询某时间段的水势
     * @param startTimes 开始时间
     * @param endTimes 结束时间
     * @return  对应水势数据数组
     */
    ServerResponse<List<SoilWater>> selectSoilTimeRegionSet(String startTimes, String endTimes);

    /**
     * 查询哪几天有数据
     * @return  天
     */
    ServerResponse<List<SoilTimeList>> selectSoilWhichTime();



}
