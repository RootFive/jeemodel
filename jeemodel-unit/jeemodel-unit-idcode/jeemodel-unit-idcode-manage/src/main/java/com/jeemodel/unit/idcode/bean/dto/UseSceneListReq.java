package com.jeemodel.unit.idcode.bean.dto;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

import com.jeemodel.bean.helper.JeeModelToStringBuilder;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 统一标识编码使用场景-分页请求
 *   
 * Entity实体类对象 【com.jeemodel.unit.idcode.bean.entity.UseScene】
 * 
 * @author JeeModel
 * @date 2022-06-27
 */
@ApiModel(description = "统一标识编码使用场景-分页请求")
@Data
@EqualsAndHashCode(callSuper=true)
public class UseSceneListReq extends UseSceneDTO {

    @ApiModelProperty(value = "其他参数")
    private Map<String, Object> params;


    public Map<String, Object> getParams() {
        if (params == null) {
            params = new HashMap<>();
        }
        return params;
    }

    @ApiModelProperty(value = "统一标识编码使用场景-名称， like模糊查询：【value like 查询值 】")
    private String sceneName;
    
    @ApiModelProperty(value = "统一标识编码使用场景-编码（36进制，最大1295）， 等于查询EQ 【value = 查询值】")
    private String sceneCode;
    
    @ApiModelProperty(value = "统一标识编码-长度（3-6）， 等于查询EQ 【value = 查询值】")
    private Integer uidLength;
    
    @ApiModelProperty(value = "统一标识编码序列号， 等于查询EQ 【value = 查询值】")
    private Long uidSerial;
    
    @ApiModelProperty(value = "状态（0正常 1停用）， 等于查询EQ 【value = 查询值】")
    private String status;
    
    @ApiModelProperty(value = "删除时间， 等于查询EQ 【value = 查询值】")
    private LocalDateTime delTime;
    

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
