package net.maku.college.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class userRequest {
    /**
     *  注册类型（0：学生；1：教师；2：企业人员）
     */
    int type;

    /**
     *  学号 / 工号
     */
    String workNumber;

    /**
     *  所属单位
     */
    int orgId;
}
