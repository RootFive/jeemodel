package com.jeemodel.unit.idcode.bean.dto;

import com.jeemodel.bean.dto.BaseEditSaveExpand;
import com.jeemodel.bean.helper.JeeModelToStringBuilder;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 统一标识编码使用场景 的编辑保存对象，属于DTO
 *   
 * Entity实体类对象 【com.jeemodel.unit.idcode.bean.entity.UseScene】
 *   
 * @author JeeModel
 * @date 2022-06-26
 */
@ApiModel(description = "统一标识编码使用场景-编辑保存")
@Data
@EqualsAndHashCode(callSuper=true)
public class UseSceneEditSave extends BaseEditSaveExpand {

    @ApiModelProperty(value = "统一标识编码使用场景-名称")
    private String sceneName;

    @ApiModelProperty(value = "统一标识编码使用场景-编码（36进制，最大1295）")
    private String sceneCode;

    @ApiModelProperty(value = "统一标识编码-长度（3-6）")
    private Integer uidLength;

    @ApiModelProperty(value = "统一标识编码序列号")
    private Long uidSerial;

    @ApiModelProperty(value = "状态（0正常 1停用）")
    private String status;


    @Override
    public String toString() {
        JeeModelToStringBuilder builder = new JeeModelToStringBuilder(this);
        builder.append("id", getId());
        builder.append("sceneName", getSceneName());
        builder.append("sceneCode", getSceneCode());
        builder.append("uidLength", getUidLength());
        builder.append("uidSerial", getUidSerial());
        builder.append("status", getStatus());
        return builder.toString();
    }
}
