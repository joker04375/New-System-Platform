package net.maku.auth.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.time.LocalDateTime;

@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("sys_user")
public class UserInfo implements Serializable {
    private static final long serialVersionUID = 1L;

    @TableId(value = "id")
    private String id;

    private String username;

    private String password;

//    private Integer activeStatus;

    private String role;

    private LocalDateTime createTime;
}
