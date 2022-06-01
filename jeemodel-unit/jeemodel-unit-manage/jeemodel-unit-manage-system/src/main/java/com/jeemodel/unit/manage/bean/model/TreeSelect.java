package com.jeemodel.unit.manage.bean.model;

import java.io.Serializable;
import java.util.List;
import java.util.stream.Collectors;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.jeemodel.unit.manage.bean.dto.system.DeptTree;
import com.jeemodel.unit.manage.bean.dto.system.MenuTree;

import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Treeselect树结构实体类
 * 
 * @author Rootfive
 */
@NoArgsConstructor
@Data
public class TreeSelect implements Serializable {
	private static final long serialVersionUID = 1L;

	/** 节点ID */
	private Long id;

	/** 节点名称 */
	private String label;

	/** 子节点 */
	@JsonInclude(JsonInclude.Include.NON_EMPTY)
	private List<TreeSelect> children;

	public TreeSelect(DeptTree dept) {
		this.id = dept.getId();
		this.label = dept.getDeptName();
		this.children = dept.getChildren().stream().map(TreeSelect::new).collect(Collectors.toList());
	}

	public TreeSelect(MenuTree menu) {
		this.id = menu.getId();
		this.label = menu.getMenuName();
		this.children = menu.getChildren().stream().map(TreeSelect::new).collect(Collectors.toList());
	}
}
