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
 * 题目示例表
 * </p>
 *
 * @author flower
 * @since 2024-11-14
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("problem_samples")
@ApiModel(value="ProblemSamples对象", description="题目示例表")
public class ProblemSamples implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    @ApiModelProperty(value = "题目的 ID")
    private Integer problemId;

    @ApiModelProperty(value = "示例输入")
    private String sampleInput;

    @ApiModelProperty(value = "示例输出")
    private String sampleOutput;

    @ApiModelProperty(value = "示例创建时间")
    private LocalDateTime createdAt;

    @ApiModelProperty(value = "示例更新时间")
    private LocalDateTime updatedAt;


}
