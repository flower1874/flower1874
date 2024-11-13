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
 * 题目表
 * </p>
 *
 * @author flower
 * @since 2024-11-14
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("problems")
@ApiModel(value="Problems对象", description="题目表")
public class Problems implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    @ApiModelProperty(value = "题目标题")
    private String title;

    @ApiModelProperty(value = "题目描述")
    private String description;

    @ApiModelProperty(value = "输入描述")
    private String inputDescription;

    @ApiModelProperty(value = "输出描述")
    private String outputDescription;

    @ApiModelProperty(value = "时间限制（毫秒）")
    private Integer timeLimit;

    @ApiModelProperty(value = "内存限制（KB）")
    private Integer memoryLimit;

    @ApiModelProperty(value = "题目难度")
    private String difficulty;

    @ApiModelProperty(value = "题目标签")
    private String tags;

    @ApiModelProperty(value = "题目是否对所有用户可见")
    private Boolean visible;

    @ApiModelProperty(value = "创建者用户的 ID")
    private Integer createdBy;

    @ApiModelProperty(value = "题目创建时间")
    private LocalDateTime createdAt;

    @ApiModelProperty(value = "题目更新时间")
    private LocalDateTime updatedAt;


}
