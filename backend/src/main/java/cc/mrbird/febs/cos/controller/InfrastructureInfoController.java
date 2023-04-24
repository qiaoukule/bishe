package cc.mrbird.febs.cos.controller;

import cc.mrbird.febs.common.utils.R;
import cc.mrbird.febs.cos.entity.BuildingInfo;
import cc.mrbird.febs.cos.entity.InfrastructureInfo;
import cc.mrbird.febs.cos.service.IInfrastructureInfoService;
import cn.hutool.core.date.DateUtil;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;

@RestController
@RequestMapping("/cos/infrastructure-info")
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class InfrastructureInfoController {
    //代替@AutoWired注解，需要注意的是在注入时需要用final定义，或者使用@notnull注解
    private final IInfrastructureInfoService infrastructureInfoService;

    /**
     * 查询所有楼宇
     * @return
     */
    @GetMapping("/list")
    public R list() {
        return R.ok(infrastructureInfoService.list());
    }

    /**
     * 分页获取楼宇信息
     * @param page
     * @param infrastructureInfo
     * @return
     */
    @GetMapping("/page")
    public R page(Page page, InfrastructureInfo infrastructureInfo) {
        return R.ok(infrastructureInfoService.infrastructureInfoByPage(page,infrastructureInfo));
    }

    /**
     * 添加楼宇信息
     * @param infrastructureInfo
     * @return
     */
    @PostMapping
    public R save(InfrastructureInfo infrastructureInfo) {
        infrastructureInfo.setCreateDate(DateUtil.formatDateTime(new Date()));
        return R.ok(infrastructureInfoService.save(infrastructureInfo));
    }

    /**
     * 修改楼宇信息
     * @param infrastructureInfo
     * @return
     */
    @PutMapping
    public R edit(InfrastructureInfo infrastructureInfo) {
        return R.ok(infrastructureInfoService.updateById(infrastructureInfo));
    }

    /**
     * 删除楼宇信息
     * @param ids
     * @return
     */
    @DeleteMapping("/{ids}")
    public R deleteByIds(@PathVariable("ids") List<Integer> ids) {
        return R.ok(infrastructureInfoService.removeByIds(ids));
    }

}
