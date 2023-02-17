package cc.mrbird.febs.cos.service.impl;

import cc.mrbird.febs.cos.dao.ParkInfoMapper;
import cc.mrbird.febs.cos.entity.ParkInfo;
import cc.mrbird.febs.cos.service.IParkInfoService;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

import java.util.LinkedHashMap;

/**
 * @author QQ
 */
@Service
public class ParkInfoServiceImpl extends ServiceImpl<ParkInfoMapper, ParkInfo> implements IParkInfoService {

    @Override
    public IPage<LinkedHashMap<String, Object>> parkInfoByPage(Page page, ParkInfo parkInfo) {
        return baseMapper.parkInfoByPage(page, parkInfo);
    }
}
