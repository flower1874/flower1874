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
 * 排名表
 * </p>
 *
 * @author flower
 * @since 2024-11-14
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("rankings")
@ApiModel(value="Rankings对象", description="排名表")
public class Rankings implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    @ApiModelProperty(value = "用户的 ID")
    private Integer userId;

    @ApiModelProperty(value = "竞赛ID")
    private Integer contestId;

    @ApiModelProperty(value = "总提交次数")
    private Integer totalSubmissions;

    @ApiModelProperty(value = "通过的提交次数")
    private Integer acceptedSubmissions;

    @ApiModelProperty(value = "总运行时间（毫秒）")
    private Integer totalRuntime;

    @ApiModelProperty(value = "平均运行时间（毫秒）")
    private Float averageRuntime;

    @ApiModelProperty(value = "罚时")
    private Integer penalty;

    @ApiModelProperty(value = "排名创建时间")
    private LocalDateTime createdAt;

    @ApiModelProperty(value = "排名更新时间")
    private LocalDateTime updatedAt;


}
