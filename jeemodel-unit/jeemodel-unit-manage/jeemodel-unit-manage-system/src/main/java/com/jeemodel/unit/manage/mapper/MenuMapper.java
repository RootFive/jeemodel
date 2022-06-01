package com.jeemodel.unit.manage.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.jeemodel.unit.manage.bean.dto.system.MenuTree;
import com.jeemodel.unit.manage.core.bean.entity.Menu;

/**
 * 菜单表 数据层
 */
public interface MenuMapper  extends BaseMapper<Menu>{

	/**
	 * 根据用户所有权限
	 *
	 * @return 权限列表
	 */
	List<String> selectMenuPerms();

	/**
	 * 根据用户查询系统菜单列表
	 *
	 * @param menu 菜单信息
	 * @return 菜单列表
	 */
	List<Menu> selectMenuListByUserId(@Param("menu") Menu menu, @Param("userId") Long userId);

	/**
	 * 根据用户ID查询权限
	 *
	 * @param userId 用户ID
	 * @return 权限列表
	 */
	List<String> selectMenuPermsByUserId(Long userId);

	/**
	 * 根据用户ID查询菜单
	 *
	 * @return 菜单列表
	 */
	List<MenuTree> selectMenuTreeAll();

	/**
	 * 根据用户ID查询菜单
	 *
	 * @param userId 用户ID
	 * @return 菜单列表
	 */
	List<MenuTree> selectMenuTreeByUserId(Long userId);

	/**
	 * 根据角色ID查询菜单树信息
	 * 
	 * @param roleId            角色ID
	 * @param menuCheckStrictly 菜单树选择项是否关联显示
	 * @return 选中菜单列表
	 */
	List<Long> selectMenuListByRoleId(@Param("roleId") Long roleId,
			@Param("menuCheckStrictly") boolean menuCheckStrictly);

}
