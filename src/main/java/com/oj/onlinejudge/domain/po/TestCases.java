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
 * 测试用例表
 * </p>
 *
 * @author flower
 * @since 2024-11-14
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("test_cases")
@ApiModel(value="TestCases对象", description="测试用例表")
public class TestCases implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    @ApiModelProperty(value = "题目的 ID")
    private Integer problemId;

    @ApiModelProperty(value = "测试用例的输入")
    private String input;

    @ApiModelProperty(value = "测试用例的输出")
    private String output;

    @ApiModelProperty(value = "是否为示例测试用例")
    private Boolean isSample;

    @ApiModelProperty(value = "测试用例的分数")
    private Integer points;

    @ApiModelProperty(value = "测试用例创建时间")
    private LocalDateTime createdAt;

    @ApiModelProperty(value = "测试用例更新时间")
    private LocalDateTime updatedAt;


}
