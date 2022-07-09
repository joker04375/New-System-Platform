package net.maku.enterprise.entity;


import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import net.maku.enterprise.common.OrgConstants;
import net.maku.framework.common.entity.BaseEntity;


/**
 * 企业实习学生管理表
 */
@Data
@EqualsAndHashCode(callSuper=false)
@TableName("sys_org_prac_stu")
@AllArgsConstructor
@NoArgsConstructor
public class SysOrgPracStuEntity extends BaseEntity {

  /**
   *企业id
   */
  private long orgId;

  /**
   *实习id
   */
  private long pracId;

  /**
   *岗位id
   */
  private long postId;

  /**
   *学生学号
   */
  private String stuId;

  /**
   *学生姓名
   */
  private String stuName;

  /**
   *学生电话
   */
  private String stuPhone;

  /**
   *学生邮箱
   */
  private String stuEmail;

  /**
   *岗位名称
   */
  private String post;

  /**
   *学生状态 -1为淘汰 0为报名中 1为正在进行本轮测试 2为通过本轮测试 3为已录取 4为正在实习
   */
  private long status;

  /*public SysOrgPracStuEntity(SysStuApplyPostDTO DTO) {
    this.orgId = DTO.getOrgId();
    this.pracId = DTO.getPracId();
    this.postId = DTO.getPostId();
    this.stuId = DTO.getStuNum();
    this.stuName = DTO.getName();
    this.stuPhone = DTO.getPhonenum();
    this.stuEmail = DTO.getEmail();
    this.postId = DTO.getPostId();
    this.status = OrgConstants.STU_STATUS_APPLY;
    this.post=DTO.getPostName();
  }*/
}
