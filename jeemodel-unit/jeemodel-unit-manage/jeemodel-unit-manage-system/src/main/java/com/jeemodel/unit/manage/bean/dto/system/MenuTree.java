package com.jeemodel.unit.manage.bean.dto.system;

import java.util.ArrayList;
import java.util.List;

import com.jeemodel.unit.manage.core.bean.entity.Menu;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

/**
 * 	菜单-树
 * @author Rootfive
 *
 */
@NoArgsConstructor
@AllArgsConstructor
@Data
@EqualsAndHashCode(callSuper = false)
public class MenuTree extends Menu {

	private static final long serialVersionUID = 1L;

	/** 子菜单 */
	private List<MenuTree> children = new ArrayList<>();
}
