package com.jeemodel.unit.manage.bean.dto.system;

import java.util.ArrayList;
import java.util.List;

import com.jeemodel.unit.manage.core.bean.entity.Dept;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

/**
 * 	部门-树
 * @author Rootfive
 *
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = false)
public class DeptTree extends Dept{

	private static final long serialVersionUID = 1L;

	/** 子部门 */
	private List<DeptTree> children = new ArrayList<>();
}
