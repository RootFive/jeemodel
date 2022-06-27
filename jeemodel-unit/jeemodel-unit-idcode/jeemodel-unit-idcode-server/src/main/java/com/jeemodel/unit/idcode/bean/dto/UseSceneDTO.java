package com.jeemodel.unit.idcode.bean.dto;

import com.jeemodel.bean.annotation.Excel;
import com.jeemodel.bean.dto.BaseDTOCRUDX;
import com.jeemodel.bean.helper.JeeModelToStringBuilder;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 统一标识编码使用场景 的DTO数据传输对象
 *   
 * Entity实体类对象 【com.jeemodel.unit.idcode.entity.bean.UseScene】
 *  
 * @author JeeModel
 * @date 2022-06-26
 */
@ApiModel(description = "统一标识编码使用场景-DTO")
@Data
@EqualsAndHashCode(callSuper=true)
public class UseSceneDTO extends BaseDTOCRUDX {

    @ApiModelProperty(value = "统一标识编码使用场景-名称")
    @Excel(name = "统一标识编码使用场景-名称")
    private String sceneName;

    @ApiModelProperty(value = "统一标识编码使用场景-编码（36进制，最大1295）")
    @Excel(name = "统一标识编码使用场景-编码", readConverterExp = "3=6进制，最大1295")
    private String sceneCode;

    @ApiModelProperty(value = "统一标识编码-长度（3-6）")
    @Excel(name = "统一标识编码-长度", readConverterExp = "3=-6")
    private Integer uidLength;

    @ApiModelProperty(value = "统一标识编码序列号")
    @Excel(name = "统一标识编码序列号")
    private Long uidSerial;

    @ApiModelProperty(value = "状态（0正常 1停用）")
    @Excel(name = "状态", readConverterExp = "0=正常,1=停用")
    private String status;


    @Override
    public String toString() {
        JeeModelToStringBuilder builder = new JeeModelToStringBuilder(this);
        builder.append("id", getId());
        builder.append("sceneName", getSceneName());
        builder.append("sceneCode", getSceneCode());
        builder.append("uidLength", getUidLength());
        builder.append("uidSerial", getUidSerial());
        builder.append("remark", getRemark());
        builder.append("createBy", getCreateBy());
        builder.append("createTime", getCreateTime());
        builder.append("status", getStatus());
        builder.append("updateBy", getUpdateBy());
        builder.append("updateTime", getUpdateTime());
        builder.append("delFlag", getDelFlag());
        builder.append("delTime", getDelTime());
        return builder.toString();
    }
}
