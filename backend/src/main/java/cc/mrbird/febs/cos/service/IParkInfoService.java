package cc.mrbird.febs.cos.service;

import cc.mrbird.febs.cos.entity.BuildingInfo;
import cc.mrbird.febs.cos.entity.ParkInfo;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.LinkedHashMap;

/**
 * @author QQ
 */
public interface IParkInfoService  extends IService<ParkInfo> {

    // 分页获取车位信息
    IPage<LinkedHashMap<String, Object>> parkInfoByPage(Page page, ParkInfo parkInfo);
}
