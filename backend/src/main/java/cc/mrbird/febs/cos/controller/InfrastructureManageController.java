package cc.mrbird.febs.cos.controller;

import cc.mrbird.febs.common.utils.R;
import cc.mrbird.febs.cos.entity.BuildingInfo;
import cc.mrbird.febs.cos.entity.InfrastructureInfo;
import cc.mrbird.febs.cos.entity.InfrastructureManage;
import cc.mrbird.febs.cos.entity.ParkInfo;
import cc.mrbird.febs.cos.service.InfrastructureManageService;
import cn.hutool.core.date.DateField;
import cn.hutool.core.date.DateRange;
import cn.hutool.core.date.DateTime;
import cn.hutool.core.date.DateUtil;
import cn.hutool.json.JSONArray;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;

/**
 * @author QQ
 */
@Slf4j
@RestController
@RequestMapping("/cos/infrastructure-manage")
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class InfrastructureManageController {
    private final InfrastructureManageService infrastructureManageService;

    /**
     * 获取基础设施的七天检查记录
     * 之不想分页了
     * @param infrastructureManage
     * @return
     */
    @GetMapping("/searchInfrastructureInRangeByPage")
    public R searchInfrastructureInRangeByPage(InfrastructureManage infrastructureManage) throws ParseException {
        //根据开始和结束日期，生成连续的日期
        Date startDate = new SimpleDateFormat("yyyy-MM-dd").parse(infrastructureManage.getStartDate());
        Date endDate = new SimpleDateFormat("yyyy-MM-dd").parse(infrastructureManage.getEndDate());
        DateRange range = DateUtil.range(new DateTime(startDate), new DateTime(endDate), DateField.DAY_OF_MONTH);
        ArrayList dateList = new ArrayList();
        //把连续的日期保存到集合中
        range.forEach(one -> {
            dateList.add(one.toDateStr());
        });

        LinkedHashMap<String, Object> result = infrastructureManageService.searchInfrastructureInRangeByPage(infrastructureManage,dateList);
        List<String> dateStrList = new ArrayList<>();
        // range.reset();
        range = DateUtil.range(new DateTime(startDate), new DateTime(endDate), DateField.DAY_OF_MONTH);
        //往dateList放入格式化的日期，前端用作表头栏目输出(怎么只剩一个日期了)
        range.forEach(one -> {
            log.info(one.toString("MM月dd日") + "（" + one.dayOfWeekEnum().toChinese() + "）");
            dateStrList.add(one.toString("MM月dd日") + "（" + one.dayOfWeekEnum().toChinese() + "）");
        });
        result.put("dateList", dateStrList);
        return R.ok(result);

//        //调用service方法
//        JSONArray record = infrastructureManageService.searchInfrastructureInRangeByPage(infrastructureManage,dateList);
//
//        return R.ok().put("dataList",record).put("dateList",dateList);
    }

    /**
     * 添加设施对象(废物)
     * @param infrastructureInfo
     * @return
     */
    @PostMapping("/addInfo")
    public R addInfo(InfrastructureInfo infrastructureInfo) {
        log.info(infrastructureInfo.getInfrastructureName());
        int i = infrastructureManageService.addInfo(infrastructureInfo);
        return R.ok();
    }

    /**
     * 根据id查看指定的检查记录
     * @param id
     * @return
     */
    @GetMapping("/searchInfrastructureRecord")
    public R searchInfrastructureRecord(Integer id) {
        return R.ok(infrastructureManageService.searchInfrastructureRecord(id));
    }

    /**
     * 编辑某条检查记录
     * @param infrastructureManage
     * @return
     */
    @PutMapping("/updateInfrastructureRecord")
    public R edit(InfrastructureManage infrastructureManage) {
        return R.ok(infrastructureManageService.editRecord(infrastructureManage));
    }

    /**
     * 增加设施的检查记录
     * @param infrastructureManage
     * @return
     */
    @PostMapping("/addRecord")
    public R addRecord(InfrastructureManage infrastructureManage) {
        return R.ok(infrastructureManageService.addRecord(infrastructureManage));
    }


}
