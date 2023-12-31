package com.jeemodel.unit.manage.aspectj;

import java.util.HashMap;
import java.util.Map;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.stereotype.Component;

import com.jeemodel.core.utils.BlankUtils;
import com.jeemodel.core.utils.StringUtils;
import com.jeemodel.unit.manage.annotation.DataScope;
import com.jeemodel.unit.manage.core.bean.entity.Role;
import com.jeemodel.unit.manage.core.bean.model.LoginUser;
import com.jeemodel.unit.manage.core.bean.model.UserDataScope;
import com.jeemodel.unit.manage.core.utils.SecurityUtils;

/**
 * 数据过滤处理
 *
 * @author Rootfive
 */
@Aspect
@Component
public class DataScopeAspect {
	/**
	 * 全部数据权限
	 */
	public static final String DATA_SCOPE_ALL = "1";

	/**
	 * 自定数据权限
	 */
	public static final String DATA_SCOPE_CUSTOM = "2";

	/**
	 * 部门数据权限
	 */
	public static final String DATA_SCOPE_DEPT = "3";

	/**
	 * 部门及以下数据权限
	 */
	public static final String DATA_SCOPE_DEPT_AND_CHILD = "4";

	/**
	 * 仅本人数据权限
	 */
	public static final String DATA_SCOPE_SELF = "5";

	/**
	 * 数据权限过滤关键字
	 */
	public static final String DATA_SCOPE = "dataScope";

	
	
	@Before("@annotation(controllerDataScope)")
	public void doBefore(JoinPoint point, DataScope controllerDataScope) throws Throwable {
		handleDataScope(point, controllerDataScope);
	}

	protected void handleDataScope(final JoinPoint joinPoint, DataScope controllerDataScope) {
		// 获取当前的用户
		LoginUser loginUser = SecurityUtils.getLoginUser();
		if (BlankUtils.isNotNull(loginUser)) {
			UserDataScope currentUser = loginUser.getUser();
			// 如果是超级管理员，则不过滤数据
			if (BlankUtils.isNotNull(currentUser) && !currentUser.isAdmin()) {
				dataScopeFilter(joinPoint, currentUser, controllerDataScope.deptAlias(),controllerDataScope.userAlias());
			}
		}
	}

	/**
	 * 数据范围过滤
	 *
	 * @param joinPoint 切点
	 * @param user      用户
	 * @param userAlias 别名
	 */
	public static void dataScopeFilter(JoinPoint joinPoint, UserDataScope user, String deptAlias, String userAlias) {
		StringBuilder sqlString = new StringBuilder();

		for (Role role : user.getRoles()) {
			String dataScope = role.getDataScope();
			if (DATA_SCOPE_ALL.equals(dataScope)) {
				sqlString = new StringBuilder();
				break;
			} else if (DATA_SCOPE_CUSTOM.equals(dataScope)) {
				sqlString.append(StringUtils.format(
						" OR {}.id IN ( SELECT dept_id FROM manage_role_dept WHERE role_id = {} ) ",
						deptAlias,role.getId()));
			} else if (DATA_SCOPE_DEPT.equals(dataScope)) {
				sqlString.append(StringUtils.format(" OR {}.id = {} ", deptAlias, user.getDeptId()));
			} else if (DATA_SCOPE_DEPT_AND_CHILD.equals(dataScope)) {
				sqlString.append(StringUtils.format(
						" OR {}.id IN ( SELECT id FROM manage_dept WHERE id = {} or find_in_set( {} , ancestors ) )",
						deptAlias, user.getDeptId(), user.getDeptId()));
			} else if (DATA_SCOPE_SELF.equals(dataScope)) {
				if (StringUtils.isNotBlank(userAlias)) {
					sqlString.append(StringUtils.format(" OR {}.user_id = {} ", userAlias, user.getId()));
				} else {
					// 数据权限为仅本人且没有userAlias别名不查询任何数据
					sqlString.append(" OR 1=0 ");
				}
			}
		}

		
		if (StringUtils.isNotBlank(sqlString.toString())) {
			Map<String, Object> dataScopeParams = new HashMap<String, Object>();
			dataScopeParams.put(DATA_SCOPE, " AND (" + sqlString.substring(4) + ")");
			DataScopeContext.set(dataScopeParams);
		}
	}

}
