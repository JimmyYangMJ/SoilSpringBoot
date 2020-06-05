package com.soil.service;

import com.soil.common.ServerResponse;
import com.soil.pojo.SoilClients;

import java.util.List;

/**
 *
 * @author ymj
 * @Date： 2020/6/5 9:42
 */
public interface ISoilClientsService {

    /**
     * 查询 上位机 状态
     * @return
     */
    ServerResponse<List<SoilClients>> selectSoilClients();
}
