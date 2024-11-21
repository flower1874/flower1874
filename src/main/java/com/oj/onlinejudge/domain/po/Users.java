package com.oj.onlinejudge.domain.po;

import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import java.time.LocalDateTime;
import java.io.Serializable;

import com.oj.onlinejudge.domain.enums.Role;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * <p>
 * 用户表
 * </p>
 *
 * @author flower
 * @since 2024-11-14
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("users")
@ApiModel(value="Users对象", description="用户表")
public class Users implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    @ApiModelProperty(value = "用户名")
    private String username;

    @ApiModelProperty(value = "密码（应使用哈希算法存储）")
    private String password;

    @ApiModelProperty(value = "角色：学生或教师")
    private Role role;

    @ApiModelProperty(value = "电子邮件地址")
    private String email;

    @ApiModelProperty(value = "学生号，仅当角色为学生时有效")
    private String studentNumber;

    @ApiModelProperty(value = "教师号，仅当角色为教师时有效")
    private String teacherNumber;

    @ApiModelProperty(value = "用户等级，默认值为 1")
    private Integer level;

    @ApiModelProperty(value = "最后登录时间")
    private LocalDateTime lastLogin;

    @ApiModelProperty(value = "用户创建时间")
    private LocalDateTime createdAt;

    @ApiModelProperty(value = "用户更新时间")
    private LocalDateTime updatedAt;


}
