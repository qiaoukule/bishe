package cc.mrbird.febs.common.domain;

import cc.mrbird.febs.common.utils.DateUtil;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;
import org.apache.commons.lang3.RandomStringUtils;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * 在线用户
 */
@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ActiveUser implements Serializable {
    //无法保证一个类的结构是永久不变的，凡是 implements serializable 的类，最好都显式的定义一个 serialVersionUID
    private static final long serialVersionUID = 2055229953429884344L;  //验证版本一致性，序列化前后的唯一标识符

    // 唯一编号
    private String id = RandomStringUtils.randomAlphanumeric(20);
    // 用户名
    private String username;
    // ip地址
    private String ip;
    // token(加密后)
    private String token;
    // 登录时间
    private String loginTime = DateUtil.formatFullTime(LocalDateTime.now(),DateUtil.FULL_TIME_SPLIT_PATTERN);
    // 登录地点
    private String loginAddress;
}
