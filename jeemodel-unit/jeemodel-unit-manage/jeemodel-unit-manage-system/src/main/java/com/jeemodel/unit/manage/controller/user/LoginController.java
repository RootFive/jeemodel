package com.jeemodel.unit.manage.controller.user;

import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.jeemodel.bean.rpc.PongData;
import com.jeemodel.bean.rpc.PongUtils;
import com.jeemodel.unit.manage.bean.dto.LoginResult;
import com.jeemodel.unit.manage.bean.dto.UserRolesPermissions;
import com.jeemodel.unit.manage.bean.dto.system.MenuTree;
import com.jeemodel.unit.manage.bean.vo.RouterVo;
import com.jeemodel.unit.manage.core.bean.entity.User;
import com.jeemodel.unit.manage.core.bean.model.LoginBody;
import com.jeemodel.unit.manage.core.utils.SecurityUtils;
import com.jeemodel.unit.manage.service.IMenuService;
import com.jeemodel.unit.manage.web.service.LoginService;
import com.jeemodel.unit.manage.web.service.SysPermissionService;

/**
 * 登录验证
 * 
 * @author Rootfive
 */
@RestController
public class LoginController {
	
	@Autowired
	private LoginService loginService;

	@Autowired
	private IMenuService menuService;

	@Autowired
	private SysPermissionService permissionService;

	/**
	 * 登录方法
	 * 
	 * @param loginBody 登录信息
	 * @return 结果
	 */
	@PostMapping("/login")
	public PongData<LoginResult> login(@RequestBody LoginBody loginBody) {
		// 生成令牌
//		throw ExceptionUtils.warn();
		String token = loginService.login(loginBody.getUsername(), loginBody.getPassword(), loginBody.getCode(),loginBody.getUuid());
		return PongUtils.okData(new LoginResult(token));
	}

	/**
	 * 获取用户信息
	 * 
	 * @return 用户信息
	 */
	@GetMapping("getInfo")
	public PongData<UserRolesPermissions> getInfo() {
		User user = SecurityUtils.getLoginUser().getUser();
		// 角色集合
		Set<String> roles = permissionService.getRolePermission(user);
		// 权限集合
		Set<String> permissions = permissionService.getMenuPermission(user);
		UserRolesPermissions userRolesPermissions = new UserRolesPermissions(user, roles, permissions);
		return PongUtils.okData(userRolesPermissions);

	}

	/**
	 * 获取路由信息
	 * 
	 * @return 路由信息
	 */
	@GetMapping("getRouters")
	public PongData<List<RouterVo>> getRouters() {
		Long userId = SecurityUtils.getUserId();
		List<MenuTree> menus = menuService.selectMenuTreeByUserId(userId);
		return PongUtils.okData(menuService.buildMenus(menus));
	}
}
