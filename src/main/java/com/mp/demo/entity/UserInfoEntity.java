package com.mp.demo.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * @Description 状态信息实体类
 * @Author Sans
 * @CreateTime 2019/5/26 21:41
 */
@Data
@TableName("T_TSC_JUNCMODETAB") //@TableName中的值对应着表名
public class UserInfoEntity {

    private static final long serialVersionUID=1L;

    private BigDecimal NOAREA;

    private BigDecimal NOJUNC;

    private BigDecimal CTRMODE;

    private BigDecimal IDEN;

    private LocalDateTime DATETIME;

}
