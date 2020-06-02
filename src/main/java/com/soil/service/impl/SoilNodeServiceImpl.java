package com.soil.service.impl;

import com.soil.common.ServerResponse;
import com.soil.dao.SoilNodeMapper;
import com.soil.pojo.SoilNode;
import com.soil.pojo.SoilNodeLocation;
import com.soil.service.ISoilNodeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author ymj
 * @Date： 2019/11/13 20:21
 */
@Service("iSoilNodeService")
public class SoilNodeServiceImpl implements ISoilNodeService {

    @Autowired
    private SoilNodeMapper soilNodeMapper;

    @Override
    public ServerResponse<List<SoilNode>> selectSoilNodeSet() {

        List<SoilNode> resultCount = soilNodeMapper.selectSoilNodeSet();

        if(resultCount.size() == 0 ){
            return ServerResponse.createByErrorMessage("没有相应记录");
        }
        return ServerResponse.createBySuccess("查询成功", resultCount);
    }

    @Override
    public ServerResponse<String> insertSoilNode(SoilNode soilNode) {

        int resultCount = soilNodeMapper.insertSoilNode(soilNode);
        if(resultCount == 0){
            return ServerResponse.createByErrorMessage("添加失败");
        }
        return ServerResponse.createBySuccessMessage("添加成功");
    }

    @Override
    public ServerResponse<String> updateSoilNodeLocation(int node, Double lng, Double lat) {

        int resultCount = soilNodeMapper.updateSoilNodeLocation(node, lng, lat);
        if(resultCount == 0){
            return ServerResponse.createByErrorMessage("更新失败");
        }
        return ServerResponse.createBySuccessMessage("更新成功");
    }

    @Override
    public ServerResponse<String> deleterSoilNode(int node) {
        int resultCount = soilNodeMapper.deleteSoilNode(node);
        if(resultCount == 0){
            return ServerResponse.createByErrorMessage("删除失败");
        }
        return ServerResponse.createBySuccessMessage("删除成功");
    }

    @Override
    public ServerResponse<List<SoilNodeLocation>> selectSoilNodeLocationSet() {
        List<SoilNodeLocation> resultCount = soilNodeMapper.selectSoilNodeLocationSet();

        if(resultCount.size() == 0 ){
            return ServerResponse.createByErrorMessage("没有相应记录");
        }
        return ServerResponse.createBySuccess("查询成功", resultCount);
    }


}
