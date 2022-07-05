package com.jeemodel.unit.idcode.bean.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.jeemodel.bean.helper.JeeModelToStringBuilder;
import com.jeemodel.data.entity.BaseEntityCRUDX;
import com.jeemodel.unit.idcode.bean.dto.UseSceneDTO;
import com.jeemodel.unit.idcode.bean.dto.UseSceneEditSave;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

/**
 * 数据库表【idcode_use_scene】场景标识-Entity实体类对象
 * 
 * @author JeeModel
 * @date 2022-07-04
 */
@TableName("idcode_use_scene")
@Data
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class UseScene extends BaseEntityCRUDX {

	private static final long serialVersionUID = 1L;

	/** 场景标识-名称 */
	private String sceneName;

	/** 场景标识-编码（35进制，最大42874） */
	private String sceneCode;

	/** 标识-长度（3-6） */
	private Integer uidLength;

	/** 标识序列号 */
	private Integer uidSerial;

	/** 状态（0正常 1停用） */
	private String status;

	/**
	 * 构造方法 UseSceneDTO 转 UseScene
	 * 
	 * @param useSceneDTO
	 */
	public UseScene(UseSceneDTO useSceneDTO) {
		this.id = useSceneDTO.getId();
		this.sceneName = useSceneDTO.getSceneName();
		this.sceneCode = useSceneDTO.getSceneCode();
		this.uidLength = useSceneDTO.getUidLength();
		this.uidSerial = useSceneDTO.getUidSerial();
		this.remark = useSceneDTO.getRemark();
		this.createBy = useSceneDTO.getCreateBy();
		this.createTime = useSceneDTO.getCreateTime();
		this.status = useSceneDTO.getStatus();
		this.updateBy = useSceneDTO.getUpdateBy();
		this.updateTime = useSceneDTO.getUpdateTime();
		this.delFlag = useSceneDTO.getDelFlag();
		this.delTime = useSceneDTO.getDelTime();
	}

	/**
	 * 构造方法 UseSceneEditSave 转 UseScene
	 * 
	 * @param useSceneEditSave
	 */
	public UseScene(UseSceneEditSave useSceneEditSave) {
		this.id = useSceneEditSave.getId();
		this.sceneName = useSceneEditSave.getSceneName();
		this.uidLength = useSceneEditSave.getUidLength();
		this.uidSerial = useSceneEditSave.getUidSerial();
		this.status = useSceneEditSave.getStatus();
		this.remark = useSceneEditSave.getRemark();
	}

	/**
	 * UseScene 转 UseSceneDTO
	 */
	public UseSceneDTO toDTO() {
		UseSceneDTO useSceneDTO = new UseSceneDTO();
		useSceneDTO.setId(this.id);
		useSceneDTO.setSceneName(this.sceneName);
		useSceneDTO.setSceneCode(this.sceneCode);
		useSceneDTO.setUidLength(this.uidLength);
		useSceneDTO.setUidSerial(this.uidSerial);
		useSceneDTO.setRemark(this.remark);
		useSceneDTO.setCreateBy(this.createBy);
		useSceneDTO.setCreateTime(this.createTime);
		useSceneDTO.setStatus(this.status);
		useSceneDTO.setUpdateBy(this.updateBy);
		useSceneDTO.setUpdateTime(this.updateTime);
		useSceneDTO.setDelFlag(this.delFlag);
		useSceneDTO.setDelTime(this.delTime);
		return useSceneDTO;
	}

	/**
	 * 以JSON格式输出，重写toString方法
	 */
	@Override
	public String toString() {
		JeeModelToStringBuilder builder = new JeeModelToStringBuilder(this);
		builder.append("id", id);
		builder.append("sceneName", sceneName);
		builder.append("sceneCode", sceneCode);
		builder.append("uidLength", uidLength);
		builder.append("uidSerial", uidSerial);
		builder.append("remark", remark);
		builder.append("createBy", createBy);
		builder.append("createTime", createTime);
		builder.append("status", status);
		builder.append("updateBy", updateBy);
		builder.append("updateTime", updateTime);
		builder.append("delFlag", delFlag);
		builder.append("delTime", delTime);
		return builder.toString();
	}

}
