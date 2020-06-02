package com.soil.service.impl;

import com.soil.common.ServerResponse;
import com.soil.dao.SoilMapper;
import com.soil.pojo.SoilTimeList;
import com.soil.pojo.SoilWater;
import com.soil.service.ISoilService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service("iSoilService")
public class SoilServiceImpl implements ISoilService {

    @Autowired
    private SoilMapper soilMapper;

    @Override
    public ServerResponse<List<SoilWater>> selectSoilSet(Integer node, String day) {
        System.out.println("node: " + node );

        List<SoilWater> resultCount = soilMapper.selectSoilSet(node, day);

        System.out.println( resultCount.size());

        if(resultCount.size() == 0 ){
            return ServerResponse.createByErrorMessage("没有相应记录");
        }

        return ServerResponse.createBySuccess("查询成功", resultCount);
    }

    @Override
    public ServerResponse<List<SoilWater>> selectSoilTimeRegionSet(String startTimes, String endTimes) {
        System.out.println("startTime: " + startTimes + "endTime:" + endTimes );

        List<SoilWater> resultCount = soilMapper.selectSoilTimeRegionSet(startTimes, endTimes);

        System.out.println( resultCount.size());

        if(resultCount.size() == 0 ){
            return ServerResponse.createByErrorMessage("没有相应记录");
        }

        return ServerResponse.createBySuccess("查询成功", resultCount);
    }

    @Override
    public ServerResponse<List<SoilTimeList>> selectSoilWhichTime() {

        List<SoilTimeList> resultCount = soilMapper.selectSoilWhichTime();

        if(resultCount.size() == 0 ){
            return ServerResponse.createByErrorMessage("没有相应记录");
        }

        return ServerResponse.createBySuccess("查询成功", resultCount);
    }
}
