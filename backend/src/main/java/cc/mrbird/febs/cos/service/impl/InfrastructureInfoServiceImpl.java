package cc.mrbird.febs.cos.service.impl;

import cc.mrbird.febs.cos.dao.BuildingInfoMapper;
import cc.mrbird.febs.cos.dao.InfrastructureInfoMapper;
import cc.mrbird.febs.cos.entity.BuildingInfo;
import cc.mrbird.febs.cos.entity.InfrastructureInfo;
import cc.mrbird.febs.cos.service.IBuildingInfoService;
import cc.mrbird.febs.cos.service.IInfrastructureInfoService;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

import java.util.LinkedHashMap;

/**
 * @author FanK
 */
@Service
public class InfrastructureInfoServiceImpl extends ServiceImpl<InfrastructureInfoMapper, InfrastructureInfo> implements IInfrastructureInfoService {

    @Override
    public IPage<LinkedHashMap<String, Object>> infrastructureInfoByPage(Page page, InfrastructureInfo infrastructureInfo) {
        return baseMapper.infrastructureInfoByPage(page, infrastructureInfo);
    }
}
