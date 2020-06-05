package com.soil.service.impl;

import com.soil.common.ServerResponse;
import com.soil.dao.SoilClientsMapper;
import com.soil.pojo.SoilClients;
import com.soil.service.ISoilClientsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author ymj
 * @Date： 2020/6/5 10:15
 */
@Service("iSoilClientsService")
public class SoilClientsServiceImpl implements ISoilClientsService {

    @Autowired
    private SoilClientsMapper soilClientsMapper;

    @Override
    public ServerResponse<List<SoilClients>> selectSoilClients() {

        List<SoilClients> resultCount = soilClientsMapper.selectSoilClients();

        if(resultCount.size() == 0 ){
            return ServerResponse.createByErrorMessage("没有相应记录");
        }
        return ServerResponse.createBySuccess("查询成功", resultCount);
    }
}
