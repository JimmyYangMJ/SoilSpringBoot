package com.soil.pojo;

/**
 * @author ymj
 * @Date： 2020/6/5 9:51
 */
public class SoilClients {
    /** 结点编号*/
    private int clients_id;
    /** 结点状态
     * 异常：00
     * 在线 online ，离线 offline
     * */
    private String client_status;

    /**
     * 上位机ip
     */
    private String ip;

    /** 信息修改时间 */
    private String last_update;

    public int getClients_id() {
        return clients_id;
    }

    public void setClients_id(int clients_id) {
        this.clients_id = clients_id;
    }

    public String getClient_status() {
        return client_status;
    }

    public void setClient_status(String client_status) {
        this.client_status = client_status;
    }

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    public String getLast_update() {
        return last_update;
    }

    public void setLast_update(String last_update) {
        this.last_update = last_update;
    }
}
