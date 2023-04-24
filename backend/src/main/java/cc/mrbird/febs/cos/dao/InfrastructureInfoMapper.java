package cc.mrbird.febs.cos.dao;

import cc.mrbird.febs.cos.entity.BuildingInfo;
import cc.mrbird.febs.cos.entity.InfrastructureInfo;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.apache.ibatis.annotations.Param;

import java.util.LinkedHashMap;

/**
 * @author FanK
 */
public interface InfrastructureInfoMapper extends BaseMapper<InfrastructureInfo> {

    // 分页获取楼宇信息
    IPage<LinkedHashMap<String, Object>> infrastructureInfoByPage(Page page, @Param("infrastructureInfo") InfrastructureInfo infrastructureInfo);
}
