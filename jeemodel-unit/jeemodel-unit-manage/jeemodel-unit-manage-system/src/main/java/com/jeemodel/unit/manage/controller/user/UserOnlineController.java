package com.jeemodel.unit.manage.controller.user;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.jeemodel.bean.http.Pong;
import com.jeemodel.bean.http.PongTable;
import com.jeemodel.bean.http.PongUtils;
import com.jeemodel.core.utils.StringUtils;
import com.jeemodel.core.web.controller.BaseController;
import com.jeemodel.data.utils.PageUtils;
import com.jeemodel.solution.redis.core.cache.RedisCacheHelper;
import com.jeemodel.unit.manage.bean.entity.UserOnline;
import com.jeemodel.unit.manage.core.annotation.Log;
import com.jeemodel.unit.manage.core.bean.model.LoginUser;
import com.jeemodel.unit.manage.core.constant.ManageConstants;
import com.jeemodel.unit.manage.core.enums.BusinessType;
import com.jeemodel.unit.manage.service.IUserOnlineService;

/**
 * 在线用户监控
 * 
 * @author Rootfive
 */
@RestController
@RequestMapping("/monitor/online")
public class UserOnlineController extends BaseController {
	@Autowired
	private IUserOnlineService userOnlineService;

	@Autowired
	private RedisCacheHelper redisCacheHelper;

	@PreAuthorize("@ss.hasPermi('manage:userOnline:list')")
	@GetMapping("/list")
	public PongTable<UserOnline> list(String ipaddr, String userName) {
		Collection<String> keys = redisCacheHelper.keys(ManageConstants.LOGIN_TOKEN_KEY + "*");
		List<UserOnline> userOnlineList = new ArrayList<UserOnline>();
		for (String key : keys) {
			LoginUser user = redisCacheHelper.getObject(key);
			if (StringUtils.isNotEmpty(ipaddr) && StringUtils.isNotEmpty(userName)) {
				if (StringUtils.equals(ipaddr, user.getIpaddr()) && StringUtils.equals(userName, user.getUsername())) {
					userOnlineList.add(userOnlineService.selectOnlineByInfo(ipaddr, userName, user));
				}
			} else if (StringUtils.isNotEmpty(ipaddr)) {
				if (StringUtils.equals(ipaddr, user.getIpaddr())) {
					userOnlineList.add(userOnlineService.selectOnlineByIpaddr(ipaddr, user));
				}
			} else if (StringUtils.isNotEmpty(userName) && StringUtils.isNotNull(user.getUser())) {
				if (StringUtils.equals(userName, user.getUsername())) {
					userOnlineList.add(userOnlineService.selectOnlineByUserName(userName, user));
				}
			} else {
				userOnlineList.add(userOnlineService.loginUserToUserOnline(user));
			}
		}
		Collections.reverse(userOnlineList);
		userOnlineList.removeAll(Collections.singleton(null));
		return PageUtils.okTable(userOnlineList);
	}

	/**
	 * 强退用户
	 */
	@PreAuthorize("@ss.hasPermi('manage:userOnline:forceLogout')")
	@Log(title = "在线用户", businessType = BusinessType.FORCE)
	@DeleteMapping("/{tokenId}")
	public Pong forceLogout(@PathVariable String tokenId) {
		redisCacheHelper.deleteObject(ManageConstants.LOGIN_TOKEN_KEY + tokenId);
		return PongUtils.ok();
	}
}
