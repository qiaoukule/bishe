package cc.mrbird.febs.cos.dao;

import cc.mrbird.febs.cos.entity.BuildingInfo;
import cc.mrbird.febs.cos.entity.ParkInfo;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.apache.ibatis.annotations.Param;

import java.util.LinkedHashMap;

/**
 * @author QQ
 */
public interface ParkInfoMapper extends BaseMapper<ParkInfo> {

    // 分页获取楼宇信息
    IPage<LinkedHashMap<String, Object>> parkInfoByPage(Page page, @Param("parkInfo") ParkInfo parkInfo);
}
