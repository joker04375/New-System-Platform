package net.maku.system.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.EqualsAndHashCode;
import net.maku.framework.common.entity.BaseEntity;
import net.maku.system.enums.UserStatusEnum;

@Data
@EqualsAndHashCode(callSuper = false)
@TableName("sys_user")
public class SysUserEntity extends BaseEntity {
    /**
     * 用户名
     */
    private String username;
    /**
     * 密码
     */
    private String password;
    /**
     * 姓名
     */
    private String realName;
    /**
     * 头像
     */
    private String avatar;
    /**
     * 性别   0：男   1：女   2：未知
     */
    private Integer gender;
    /**
     * 邮箱
     */
    private String email;
    /**
    * 身份证
    * */
    private String idCard;
    /**
     * 工号 / 学号
     * */
    private String workNumber;
    /**
     * 手机号
     */
    private String mobile;
    /**
     * 机构ID / 学院ID
     */
    private Long orgId;
    /**
     * 超级管理员   0：否   1：是
     */
    private Integer superAdmin;
    /**
     * 状态  {@link UserStatusEnum}
     */
    private Integer status;
    /**
     * 机构名称
     */
    @TableField(exist=false)
    private String orgName;
}
