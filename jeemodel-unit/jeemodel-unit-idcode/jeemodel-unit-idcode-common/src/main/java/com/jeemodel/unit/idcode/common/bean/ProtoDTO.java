package com.jeemodel.unit.idcode.common.bean;

import java.util.Arrays;

import com.jeemodel.bean.helper.JeeModelToStringBuilder;

import lombok.Data;

@Data
public class ProtoDTO implements java.io.Serializable {

	private static final long serialVersionUID = 1L;

	/** 应用场景 */
	private String scene;

	/** 需要的ID */
	private long[] needIds;

	/**  需要的Code */
	private String[] needCodes;

	public ProtoDTO(String scene, long... needIds) {
		super();
		this.scene = scene;
		this.needIds = needIds;
	}

	public ProtoDTO(String scene, String... needCodes) {
		super();
		this.scene = scene;
		this.needCodes = needCodes;
	}

	@Override
	public String toString() {
		JeeModelToStringBuilder builder = new JeeModelToStringBuilder(this);
		if (scene != null)
			builder.append("scene", scene);
		if (needIds != null)
			builder.append("needIds", Arrays.toString(needIds));
		if (needCodes != null)
			builder.append("needCodes", Arrays.toString(needCodes));
		return builder.toString();
	}

}
