package cc.mrbird.febs.cos.service.impl;

import cc.mrbird.febs.cos.dao.InfrastructureMapper;
import cc.mrbird.febs.cos.entity.InfrastructureInfo;
import cc.mrbird.febs.cos.entity.InfrastructureManage;
import cc.mrbird.febs.cos.entity.dto.InfrastructureDto;
import cc.mrbird.febs.cos.service.InfrastructureManageService;
import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.date.DateTime;
import cn.hutool.core.date.DateUtil;
import cn.hutool.core.map.MapUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.json.JSONArray;
import cn.hutool.json.JSONUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.*;
import java.util.concurrent.atomic.AtomicReference;
import java.util.stream.Collectors;

@Service
@Slf4j
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class InfrastructureManageImpl implements InfrastructureManageService {

    @Resource
    private InfrastructureMapper infrastructureMapper;

    /**
     * 查看设施的七天记录
     * @param infrastructureManage
     * @param dateList
     * @return
     */
    @Override
    public LinkedHashMap<String, Object> searchInfrastructureInRangeByPage(InfrastructureManage infrastructureManage, ArrayList dateList) {
        // 返回數據
        LinkedHashMap<String, Object> result = new LinkedHashMap<>();
        result.put("dateList", dateList);

        List<InfrastructureDto> list = infrastructureMapper.searchInfrastructureInRangeByPage(infrastructureManage);
        if (CollectionUtil.isEmpty(list)) {
            result.put("dataList", list);
            return result;
        }

        Map<Integer, List<InfrastructureDto>> resultMap = list.stream().filter(e -> e.getId() != null).collect(Collectors.groupingBy(InfrastructureDto::getId));
        // 循環設置返回數據
        List<LinkedHashMap<String, Object>> infrastructureResult = new ArrayList<>();
        resultMap.keySet().forEach(id -> {
            List<InfrastructureDto> infrastructureDtoList = resultMap.get(id);
            // 設置item數據
            LinkedHashMap<String, Object> item = new LinkedHashMap<String, Object>() {
                {
                    put("infrastructureName", infrastructureDtoList.get(0).getInfrastructureName());
                    put("community", infrastructureDtoList.get(0).getCommunity());
                    put("id", infrastructureDtoList.get(0).getId());
                }
            };
            List<LinkedHashMap<String, Object>> recordResult = new ArrayList<>();
            // 添加設施記錄
            infrastructureDtoList.forEach(infrastructureDto -> {
                dateList.forEach(date -> {
                    if (StrUtil.toString(date).equals(infrastructureDto.getDate())) {
                        recordResult.add(new LinkedHashMap<String, Object>() {
                            {
                                put("date", StrUtil.toString(date));
                                put("mes", infrastructureDto.getWorkerName() + " - " + infrastructureDto.getMessage());
                                put("status", infrastructureDto.getStatus());
                                put("recordId", infrastructureDto.getRecordId());
                            }
                        });
                    } else {
                        recordResult.add(new LinkedHashMap<String, Object>() {
                            {
                                put("date", StrUtil.toString(date));
                            }
                        });
                    }
                });
            });
            item.put("record", recordResult);
            infrastructureResult.add(item);
        });
        result.put("dataList", infrastructureResult);
        return result;
//
//
//        String tempName = null;
//        String tempDate = null;
//        HashMap tempResult = new HashMap();
//
//        //遍历数据库查出来的数据
//        for (HashMap one : list) {
//            String infrastructureName = MapUtil.getStr(one, "name");
//            String status = String.valueOf(MapUtil.getInt(one, "status"));
//            String message = MapUtil.getStr(one, "message");
//            String community = MapUtil.getStr(one, "community");
//            String use_date = MapUtil.getStr(one,"use_date");
//         //   int worker = MapUtil.getInt(one, "worker");
//            String date = MapUtil.getStr(one, "date");
//            String workerName = MapUtil.getStr(one, "workerName");
//            int id = MapUtil.getInt(one,"id"); //是设施info的id
//
//            //遍历到的第一条记录
//            if (tempName == null) {
//                log.info("第一条记录");
//                tempName = infrastructureName;
//                tempDate = date;
//                //处理第一条记录，创建设施对象
//                HashMap temp = new HashMap() {{
//                    put("infrastructureName", infrastructureName);
//                    put("id",id);
//                    put("community",community);
//                    put("use_date",use_date);
//                    /*
//                     * 该设施的七天记录
//                     * 为了保证添加的顺日期序不被打乱，遂用LinkedHashMap
//                     *
//                     */
//                    put("record", new LinkedHashMap<String,ArrayList<String>>() {{
//                        put(date, new ArrayList<String>() {{
//                            add(status);add("检查员工："+workerName);add("备注信息"+message);
//                        }}
//                        );
//                    }});
//                }};
//                tempResult.put(infrastructureName, temp);
//            }
//
//            //TODO 不想做修改功能，想做同一天新增覆盖了呜呜呜呜呜呜
//            //非第一条记录，但是该记录与前一条记录是相同设施，但不是同一天检查记录
//            else if (tempName.equals(infrastructureName) && !tempDate.equals(date)) {
//                log.info("相同设施"+infrastructureName);
//                tempDate = date;
//                //取出该设施
//                HashMap map = (HashMap) tempResult.get(infrastructureName);
//                //从设施中取出维检记录
//                LinkedHashMap record = (LinkedHashMap) map.get("record");
//                //创建新的检查日期列表，添加上这一天的设施状态、检查备注、员工姓名的信息
//                record.put(date, new ArrayList<String>() {{
//                    add(status);add("检查员工："+workerName);add("备注信息"+message);
//                }});
//            }
//            //如果该记录与上一条记录不是同一个设施
//            else if (!tempName.equals(infrastructureName)) {
//                tempName = infrastructureName;
//                tempDate = date;
//                //fix:和上一个不是同一个设施，但是已经创建过这个设施对象了,不然就被新创建的对象覆盖了
//                if (tempResult.containsKey(infrastructureName)){
//                    log.info("不同设施，但已经出现过了"+infrastructureName);
//                    //取出该设施
//                    HashMap map = (HashMap) tempResult.get(infrastructureName);
//                    //从设施中取出维检记录
//                    LinkedHashMap record = (LinkedHashMap) map.get("record");
//                    //创建新的检查日期列表，添加上这一天的设施状态、检查备注、员工姓名的信息
//                    record.put(date, new ArrayList<String>() {{
//                        add(status);add("检查员工："+workerName);add("备注信息"+message);
//                    }});
//                } else {
//                    log.info("不同设施"+infrastructureName);
//                    //创建新的设施对象
//                    HashMap temp = new HashMap() {{
//                        put("infrastructureName", infrastructureName);
//                        put("id",id);
//                        put("community",community);
//                        put("use_date",use_date);
//                        //创建这个设施的检查记录
//                        put("record", new LinkedHashMap<String,ArrayList<String>>() {{
//                            //添加该日子的检查内容(ArrayList->HashMap)
//                            put(date, new ArrayList<String>() {{
//                                add(status);add("检查员工："+workerName);add("备注信息"+message);
//                            }});
//                        }});
//                    }};
//                    //把这个设施对象添加到结果集
//                    tempResult.put(infrastructureName, temp);
//                }
//            }
//        }
//
//        //为了循环HashMap中的元素，所以要获取对象的集合
//        Set<Map.Entry> set = tempResult.entrySet();
//        //循环每个元素
//        set.forEach(one -> {
//            //获取值，即设施对象
//            HashMap map = (HashMap) one.getValue();
//            //该设施的检查记录
//            LinkedHashMap record = (LinkedHashMap) map.get("record");
//            /*
//             * 拿方法第二个参数提取七天的日期，判断维检记录中是否有该日期，
//             * 如果木有该日期，说明这一天没有对这个设施进行检查，就往这天记录中添加空的数据
//             */
//            dateList.forEach(date -> {
//                if (!record.containsKey(date)) {
//                    record.put(date, new ArrayList<>());
//                }
//            });
//
//            //由于往LinkedHashMap中添加了新元素，所以要对所有元素排序
//            TreeMap sort = MapUtil.sort(record, new Comparator() {
//                @Override
//                public int compare(Object o1, Object o2) {
//                    String key1 = (String) o1;
//                    String key2 = (String) o2;
//                    DateTime datetime1 = DateUtil.parse(key1);
//                    DateTime datetime2 = DateUtil.parse(key2);
//                    boolean bool = new DateTime(datetime1).isAfter(new DateTime(datetime2));
//                    return bool ? 1 : -1;
//                }
//            });
//            //把排好序的检查记录更新到设施对象的检查记录中
//            map.replace("record", sort);
//        });
//
//        //每个设施的record是TreeMap，要转换成列表形式，将来才能变成JSON数组
//        Collection<HashMap> values = tempResult.values();
//        values.forEach(one -> {
//            TreeMap record = (TreeMap) one.get("record");
//            //取出TreeMap每个元素
//            Set<Map.Entry> tempSet = record.entrySet();
//            ArrayList temp = new ArrayList();
//            //把每日维检记录保存到列表中
//            tempSet.forEach(entry -> {
//                temp.add(new HashMap<String,Object>() {{
//                    put("date", entry.getKey());
//                    //这一天的设施状态+检查备注+员工名字
//                    put("mes", entry.getValue());
//                }});
//            });
//            //更新设施对象的record
//            one.replace("record", temp);
//        });
//        return JSONUtil.parseArray(values);
    }

    @Override
    public int addInfo(InfrastructureInfo infrastructureInfo){
        int i = infrastructureMapper.addInfo(infrastructureInfo);
        return i;
    }

    @Override
    public LinkedHashMap<String, InfrastructureDto> searchInfrastructureRecord(Integer id) {
        LinkedHashMap<String, InfrastructureDto> result = new LinkedHashMap<>();
        result.put("data", infrastructureMapper.searchInfrastructureRecord(id));
        return result;
    }

    @Override
    public int editRecord(InfrastructureManage infrastructureManage) {
        int i = infrastructureMapper.editRecord(infrastructureManage);
        return i;
    }

    @Override
    public int addRecord(InfrastructureManage infrastructureManage) {
        return infrastructureMapper.addRecord(infrastructureManage);
    }

    ;



}
