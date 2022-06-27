package com.jeemodel.unit.idcode.common.bean;

import lombok.Data;

/**
 * @author Rootfive
 * 
 */
@Data
public class IDCodeDemandDTO implements java.io.Serializable {
	
	/** 唯一ID,数字 */
	public static final String SCENE_UID = "UID";

	/** 回声,字符串 */
	public static final String SCENE_ECHO = "ECHO";
	
	
	private static final long serialVersionUID = 1L;
	
	/** 应用场景 */
	private String scene;

	/** 需求数量 */
	private int needSize = 1;
	

	public IDCodeDemandDTO(String scene) {
		super();
		this.scene = scene;
	}

	public IDCodeDemandDTO(String scene, int needSize) {
		super();
		this.scene = scene;
		this.needSize = needSize;
	}
}
