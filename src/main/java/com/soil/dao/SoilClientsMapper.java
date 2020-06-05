package com.soil.dao;

import com.soil.pojo.SoilClients;

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

}
