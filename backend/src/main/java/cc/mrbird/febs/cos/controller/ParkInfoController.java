package cc.mrbird.febs.cos.controller;

import cc.mrbird.febs.common.utils.R;
import cc.mrbird.febs.cos.entity.ParkInfo;
import cc.mrbird.febs.cos.service.IParkInfoService;
import cn.hutool.core.date.DateUtil;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;

/**
 * @author QQ
 */
@RestController
@RequestMapping("/cos/park-info")
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class ParkInfoController {
    private final IParkInfoService parkInfoService;
    /**
     * 查询所有车位
     * @return
     */
    @GetMapping("/list")
    public R list() {
        return R.ok(parkInfoService.list());
    }

    /**
     * 分页获取车位信息
     * @param page
     * @param parkInfo
     * @return
     */
    @GetMapping("/page")
    public R page(Page page, ParkInfo parkInfo) {
        return R.ok(parkInfoService.parkInfoByPage(page, parkInfo));
    }

    /**
     * 添加车位信息
     * @param parkInfo
     * @return
     */
    @PostMapping
    public R save(ParkInfo parkInfo) {
        parkInfo.setOwnDate(DateUtil.formatDateTime(new Date()));
        return R.ok(parkInfoService.save(parkInfo));
    }

    /**
     * 修改车位信息
     * @param parkInfo
     * @return
     */
    @PutMapping
    public R edit(ParkInfo parkInfo) {
        return R.ok(parkInfoService.updateById(parkInfo));
    }

    /**
     * 删除车位信息
     * @param ids
     * @return
     */
    @DeleteMapping("/{ids}")
    public R deleteByIds(@PathVariable("ids") List<Integer> ids) {
        return R.ok(parkInfoService.removeByIds(ids));
    }

}
