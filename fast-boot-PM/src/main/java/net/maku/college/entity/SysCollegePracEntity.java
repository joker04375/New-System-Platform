package net.maku.college.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.EqualsAndHashCode;
import net.maku.framework.common.entity.BaseEntity;


@Data
@EqualsAndHashCode(callSuper=false)
@TableName("sys_college_prac")
public class SysCollegePracEntity extends BaseEntity {
    /**
     * 企业id
     */
    int collegeId;

    /**
     * 实习id
     */
    long timeId;

    /**
     *  季度
     */
    String quarter;

    /**
     * 年份
     */
    String year;

    /**
     * 实习状态
     */
    int status;

    /**
     * 实习人数
     */
    @TableField(exist = false)
    long stuNum;
}
