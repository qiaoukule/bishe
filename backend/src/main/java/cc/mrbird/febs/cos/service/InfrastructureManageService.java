package cc.mrbird.febs.cos.service;

import cc.mrbird.febs.cos.entity.InfrastructureInfo;
import cc.mrbird.febs.cos.entity.InfrastructureManage;


import cc.mrbird.febs.cos.entity.dto.InfrastructureDto;
import cn.hutool.json.JSONArray;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public interface InfrastructureManageService{
    //查基础设施的七天检查记录
    LinkedHashMap<String, Object> searchInfrastructureInRangeByPage(InfrastructureManage infrastructureManage, ArrayList dateList);

    int addInfo(InfrastructureInfo infrastructureInfo);

    LinkedHashMap<String, InfrastructureDto> searchInfrastructureRecord(Integer id);

    int editRecord(InfrastructureManage infrastructureManage);

    int addRecord(InfrastructureManage infrastructureManage);
}
