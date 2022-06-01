package com.jeemodel.unit.manage.service;

import java.util.List;
import java.util.Set;

import com.baomidou.mybatisplus.extension.service.IService;
import com.jeemodel.unit.manage.bean.dto.system.MenuTree;
import com.jeemodel.unit.manage.bean.model.TreeSelect;
import com.jeemodel.unit.manage.bean.vo.RouterVo;
import com.jeemodel.unit.manage.core.bean.entity.Menu;

/**
 * 菜单 业务层
 * 
 * @author Rootfive
 */
public interface IMenuService extends IService<Menu>{
	/**
	 * 根据用户查询系统菜单列表
	 * 
	 * @param userId 用户ID
	 * @return 菜单列表
	 */
	public List<MenuTree> selectMenuList(Long userId);

	/**
	 * 根据用户查询系统菜单列表
	 * 
	 * @param menu   菜单信息
	 * @param userId 用户ID
	 * @return 菜单列表
	 */
	public List<MenuTree> selectMenuList(Menu menu, Long userId);

	/**
	 * 根据用户ID查询权限
	 * 
	 * @param userId 用户ID
	 * @return 权限列表
	 */
	public Set<String> selectMenuPermsByUserId(Long userId);

	/**
	 * 根据用户ID查询菜单树信息
	 * 
	 * @param userId 用户ID
	 * @return 菜单列表
	 */
	public List<MenuTree> selectMenuTreeByUserId(Long userId);

	/**
	 * 根据角色ID查询菜单树信息
	 * 
	 * @param roleId 角色ID
	 * @return 选中菜单列表
	 */
	public List<Long> selectMenuListByRoleId(Long roleId);

	/**
	 * 构建前端路由所需要的菜单
	 * 
	 * @param menus 菜单列表
	 * @return 路由列表
	 */
	public List<RouterVo> buildMenus(List<MenuTree> menus);

	/**
	 * 构建前端所需要树结构
	 * 
	 * @param menus 菜单列表
	 * @return 树结构列表
	 */
	public List<MenuTree> buildMenuTree(List<MenuTree> menus);

	/**
	 * 构建前端所需要下拉树结构
	 * 
	 * @param menus 菜单列表
	 * @return 下拉树结构列表
	 */
	public List<TreeSelect> buildMenuTreeSelect(List<MenuTree> menus);

	/**
	 * 根据菜单ID查询信息
	 * 
	 * @param id 菜单ID
	 * @return 菜单信息
	 */
	public Menu selectMenuById(Long id);

	/**
	 * 是否存在菜单子节点
	 * 
	 * @param id 菜单ID
	 * @return 结果 true 存在 false 不存在
	 */
	public boolean hasChildById(Long id);

	/**
	 * 查询菜单是否存在角色
	 * 
	 * @param menuId 菜单ID
	 * @return 结果 true 存在 false 不存在
	 */
	public boolean checkMenuExistRole(Long menuId);

	/**
	 * 新增保存菜单信息
	 * 
	 * @param menu 菜单信息
	 * @return 结果
	 */
	public int insertMenu(Menu menu);

	/**
	 * 修改保存菜单信息
	 * 
	 * @param menu 菜单信息
	 * @return 结果
	 */
	public int updateMenu(Menu menu);

	/**
	 * 删除菜单管理信息
	 * 
	 * @param id 菜单ID
	 * @return 结果
	 */
	public int deleteMenuById(Long id);

	/**
	 * 校验菜单名称是否唯一
	 * 
	 * @param menu 菜单信息
	 * @return 结果
	 */
	public String checkMenuNameUnique(Menu menu);
	
	/**
	 * 	根据权限标识获取菜单功能名
	 * 
	 * @param perms 权限标识
	 * @return 菜单功能名
	 */
	public String getMenuNameByPerms(String perms);
}
