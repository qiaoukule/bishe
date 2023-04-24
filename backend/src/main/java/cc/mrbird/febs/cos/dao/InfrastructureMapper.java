package cc.mrbird.febs.cos.dao;


import cc.mrbird.febs.cos.entity.InfrastructureInfo;
import cc.mrbird.febs.cos.entity.InfrastructureManage;
import cc.mrbird.febs.cos.entity.dto.InfrastructureDto;

import org.apache.ibatis.annotations.Param;
import java.util.List;

public interface InfrastructureMapper {
    //获取设施的七天维检记录
    List<InfrastructureDto> searchInfrastructureInRangeByPage(@Param("infrastructureManage") InfrastructureManage InfrastructureManage);

    //增加设施信息info
    int addInfo(@Param("infrastructureInfo") InfrastructureInfo infrastructureInfo);

    //查看指定的设施检查记录
    InfrastructureDto searchInfrastructureRecord(int id);

    //修改检查记录
    int editRecord(@Param("infrastructureManage") InfrastructureManage infrastructureManage);

    //新增检查记录
    int addRecord(@Param("infrastructureManage") InfrastructureManage infrastructureManage);
}
