package com.oj.onlinejudge.domain.po;

import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import java.time.LocalDateTime;
import java.io.Serializable;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * <p>
 * 提交表
 * </p>
 *
 * @author flower
 * @since 2024-11-14
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("submissions")
@ApiModel(value="Submissions对象", description="提交表")
public class Submissions implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    @ApiModelProperty(value = "题目的 ID")
    private Integer problemId;

    @ApiModelProperty(value = "提交者的用户 ID")
    private Integer userId;

    @ApiModelProperty(value = "提交的代码")
    private String code;

    @ApiModelProperty(value = "代码的语言")
    private String language;

    @ApiModelProperty(value = "提交状态")
    private String status;

    @ApiModelProperty(value = "编译或运行错误的具体信息")
    private String message;

    @ApiModelProperty(value = "运行时间（毫秒）")
    private Integer runtime;

    @ApiModelProperty(value = "使用内存（KB）")
    private Integer memory;

    @ApiModelProperty(value = "提交的得分")
    private Integer score;

    @ApiModelProperty(value = "提交创建时间")
    private LocalDateTime createdAt;

    @ApiModelProperty(value = "提交更新时间")
    private LocalDateTime updatedAt;


}
