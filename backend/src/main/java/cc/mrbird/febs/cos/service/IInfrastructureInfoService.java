package cc.mrbird.febs.cos.service;

import cc.mrbird.febs.cos.entity.InfrastructureInfo;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.LinkedHashMap;

public interface IInfrastructureInfoService extends IService<InfrastructureInfo> {

    // 分页获取楼宇信息
    IPage<LinkedHashMap<String, Object>> infrastructureInfoByPage(Page page, InfrastructureInfo infrastructureInfo);
}