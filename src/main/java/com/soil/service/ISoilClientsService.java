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

    /**
     * 更新 上位机状态
     * online：在线， offline 离线
     * @return
     */
    boolean updateClientStatus(int id, String status);
}
