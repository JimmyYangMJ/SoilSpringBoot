package com.soil.dao;

import com.soil.pojo.SoilClients;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @author ymj
 * @Date： 2020/6/5 9:45
 */
public interface SoilClientsMapper {


    /**
     * 查询上位机状态
     */
    List<SoilClients> selectSoilClients();


    /**
     * 更新 上位机状态
     * online：在线， offline 离线
     * @param id 上位机编号
     * @param status 上位机状态
     * @return
     */
    int updateClientStatus(@Param("client_id") int client_id, @Param("client_status") String client_status);

}
