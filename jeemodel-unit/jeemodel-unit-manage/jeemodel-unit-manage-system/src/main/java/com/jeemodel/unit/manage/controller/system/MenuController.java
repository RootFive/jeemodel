package com.jeemodel.unit.manage.controller.system;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.jeemodel.bean.http.Pong;
import com.jeemodel.bean.http.PongData;
import com.jeemodel.bean.http.PongUtils;
import com.jeemodel.core.utils.StringUtils;
import com.jeemodel.core.web.controller.BaseController;
import com.jeemodel.unit.manage.bean.dto.RoleMenuTreeselect;
import com.jeemodel.unit.manage.bean.dto.system.MenuTree;
import com.jeemodel.unit.manage.bean.model.TreeSelect;
import com.jeemodel.unit.manage.core.annotation.Log;
import com.jeemodel.unit.manage.core.bean.entity.Menu;
import com.jeemodel.unit.manage.core.constant.UserConstants;
import com.jeemodel.unit.manage.core.enums.BusinessType;
import com.jeemodel.unit.manage.core.utils.SecurityUtils;
import com.jeemodel.unit.manage.service.IMenuService;

import io.swagger.annotations.ApiOperation;

/**
 * 菜单信息
 * 
 * @author Rootfive
 */
@RestController
@RequestMapping("/manage/menu")
public class MenuController extends BaseController {
	@Autowired
	private IMenuService menuService;

	@ApiOperation(value = "查询-菜单列表树")
	@PreAuthorize("@ss.hasPermi('manage:menu:list')")
	@GetMapping("/list")
	public PongData<List<MenuTree>> list(Menu menu) {
		List<MenuTree> menus = menuService.selectMenuList(menu, SecurityUtils.getUserId());
		return PongUtils.okData(menus);
	}

	@ApiOperation(value = "查询-菜单详情")
	@PreAuthorize("@ss.hasPermi('manage:menu:query')")
	@GetMapping(value = "/{id}")
	public PongData<Menu> getInfo(@PathVariable Long id) {
		return PongUtils.okData(menuService.selectMenuById(id));
	}

	@ApiOperation(value = "查询-菜单下拉树列表")
	@GetMapping("/treeselect")
	public PongData<List<TreeSelect>> treeselect(Menu menu) {
		List<MenuTree> menus = menuService.selectMenuList(menu, SecurityUtils.getUserId());
		return PongUtils.okData(menuService.buildMenuTreeSelect(menus));
	}

	@ApiOperation(value = "查询-角色对应的菜单列表树")
	@GetMapping(value = "/roleMenuTreeselect/{roleId}")
	public PongData<RoleMenuTreeselect> roleMenuTreeselect(@PathVariable("roleId") Long roleId) {
		List<MenuTree> menuList = menuService.selectMenuList(SecurityUtils.getUserId());
		List<Long> checkedKeys = menuService.selectMenuListByRoleId(roleId);
		List<TreeSelect> menus = menuService.buildMenuTreeSelect(menuList);
		
		RoleMenuTreeselect roleMenuTreeselect = new RoleMenuTreeselect(checkedKeys, menus);
		return PongUtils.okData(roleMenuTreeselect);
	}

	@ApiOperation(value = "新增-菜单")
	@PreAuthorize("@ss.hasPermi('manage:menu:add')")
	@Log(title = "菜单管理", businessType = BusinessType.INSERT)
	@PostMapping
	public Pong add(@Validated @RequestBody Menu menu) {
		if (UserConstants.NOT_UNIQUE.equals(menuService.checkMenuNameUnique(menu))) {
			return PongUtils.fail("新增菜单'" + menu.getMenuName() + "'失败，菜单名称已存在");
		} else if (UserConstants.YES_FRAME.equals(menu.getIsFrame()) && !StringUtils.ishttp(menu.getPath())) {
			return PongUtils.fail("新增菜单'" + menu.getMenuName() + "'失败，地址必须以http(s)://开头");
		}
		menu.setCreateBy(SecurityUtils.getUsername());
		return PongUtils.result(menuService.insertMenu(menu));
	}

	@ApiOperation(value = "修改-菜单")
	@PreAuthorize("@ss.hasPermi('manage:menu:edit')")
	@Log(title = "菜单管理", businessType = BusinessType.UPDATE)
	@PutMapping
	public Pong edit(@Validated @RequestBody Menu menu) {
		if (UserConstants.NOT_UNIQUE.equals(menuService.checkMenuNameUnique(menu))) {
			return PongUtils.fail("修改菜单'" + menu.getMenuName() + "'失败，菜单名称已存在");
		} else if (UserConstants.YES_FRAME.equals(menu.getIsFrame()) && !StringUtils.ishttp(menu.getPath())) {
			return PongUtils.fail("修改菜单'" + menu.getMenuName() + "'失败，地址必须以http(s)://开头");
		} else if (menu.getId().equals(menu.getParentId())) {
			return PongUtils.fail("修改菜单'" + menu.getMenuName() + "'失败，上级菜单不能选择自己");
		}
		menu.setUpdateBy(SecurityUtils.getUsername());
		return PongUtils.result(menuService.updateMenu(menu));
	}

	
	@ApiOperation(value = "删除-菜单")
	@PreAuthorize("@ss.hasPermi('manage:menu:remove')")
	@Log(title = "菜单管理", businessType = BusinessType.DELETE)
	@DeleteMapping("/{id}")
	public Pong remove(@PathVariable("id") Long id) {
		if (menuService.hasChildById(id)) {
			return PongUtils.fail("存在子菜单,不允许删除");
		}
		if (menuService.checkMenuExistRole(id)) {
			return PongUtils.fail("菜单已分配,不允许删除");
		}
		return PongUtils.result(menuService.deleteMenuById(id));
	}
}