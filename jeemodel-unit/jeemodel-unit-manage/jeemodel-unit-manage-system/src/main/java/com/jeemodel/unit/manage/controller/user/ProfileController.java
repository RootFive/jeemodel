package com.jeemodel.unit.manage.controller.user;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.jeemodel.bean.http.Pong;
import com.jeemodel.bean.http.PongData;
import com.jeemodel.bean.http.PongUtils;
import com.jeemodel.core.utils.StringUtils;
import com.jeemodel.core.web.controller.BaseController;
import com.jeemodel.solution.file.config.FileConfigHelper;
import com.jeemodel.solution.file.utils.FileUploadUtils;
import com.jeemodel.unit.manage.bean.dto.user.UserAvatar;
import com.jeemodel.unit.manage.bean.dto.user.UserProfile;
import com.jeemodel.unit.manage.core.annotation.Log;
import com.jeemodel.unit.manage.core.bean.entity.User;
import com.jeemodel.unit.manage.core.bean.model.LoginUser;
import com.jeemodel.unit.manage.core.constant.UserConstants;
import com.jeemodel.unit.manage.core.enums.BusinessType;
import com.jeemodel.unit.manage.core.utils.SecurityUtils;
import com.jeemodel.unit.manage.core.web.service.TokenService;
import com.jeemodel.unit.manage.service.IUserService;

/**
 * 个人信息 业务处理
 * 
 * @author Rootfive
 */
@RestController
@RequestMapping("/manage/user/profile")
public class ProfileController extends BaseController {
	
	@Autowired
	private IUserService userService;

	@Autowired
	private TokenService tokenService;

	/**
	 * 查询个人信息
	 */
	@GetMapping
	public PongData<UserProfile> profile() {
		LoginUser loginUser = SecurityUtils.getLoginUser();
		User user = loginUser.getUser();
		String roleGroup = userService.selectUserRoleGroup(loginUser.getUsername());
		String postGroup = userService.selectUserPostGroup(loginUser.getUsername());
		
		UserProfile userProfile = new UserProfile(user, roleGroup, postGroup);
		
		return PongUtils.okData(userProfile);
	}

	/**
	 * 修改个人信息
	 */
	@Log(title = "个人信息", businessType = BusinessType.UPDATE)
	@PutMapping
	public Pong updateProfile(@RequestBody User user) {
		LoginUser loginUser = SecurityUtils.getLoginUser();
		User User = loginUser.getUser();
		user.setUserName(User.getUserName());
		if (StringUtils.isNotEmpty(user.getPhonenumber()) && UserConstants.NOT_UNIQUE.equals(userService.checkPhoneUnique(user))) {
			return PongUtils.fail("修改用户'" + user.getUserName() + "'失败，手机号码已存在");
		}
		if (StringUtils.isNotEmpty(user.getEmail())	&& UserConstants.NOT_UNIQUE.equals(userService.checkEmailUnique(user))) {
			return PongUtils.fail("修改用户'" + user.getUserName() + "'失败，邮箱账号已存在");
		}
		user.setId(User.getId());
		user.setPassword(null);
		if (userService.updateUserProfile(user) > 0) {
			// 更新缓存用户信息
			User.setNickName(user.getNickName());
			User.setPhonenumber(user.getPhonenumber());
			User.setEmail(user.getEmail());
			User.setSex(user.getSex());
			tokenService.setLoginUser(loginUser);
			return PongUtils.ok();
		}
		return PongUtils.error("修改个人信息异常，请联系管理员");
	}

	/**
	 * 重置密码
	 */
	@Log(title = "个人信息", businessType = BusinessType.UPDATE)
	@PutMapping("/updatePwd")
	public Pong updatePwd(String oldPassword, String newPassword) {
		LoginUser loginUser = SecurityUtils.getLoginUser();
		String userName = loginUser.getUsername();
		String password = loginUser.getPassword();
		if (!SecurityUtils.matchesPassword(oldPassword, password)) {
			return PongUtils.fail("修改密码失败，旧密码错误");
		}
		if (SecurityUtils.matchesPassword(newPassword, password)) {
			return PongUtils.fail("新密码不能与旧密码相同");
		}
//		if (userService.resetUserPwd(userName, SecurityUtils.encryptPassword(newPassword)) > 0) {
		if (userService.resetUserPwd(userName, SecurityUtils.encryptPassword(newPassword))) {
			// 更新缓存用户密码
			loginUser.getUser().setPassword(SecurityUtils.encryptPassword(newPassword));
			tokenService.setLoginUser(loginUser);
			return PongUtils.ok();
		}
		return PongUtils.fail("修改密码异常，请联系管理员");
	}

	/**
	 * 头像上传
	 */
	@SuppressWarnings("unchecked")
	@Log(title = "用户头像", businessType = BusinessType.UPDATE)
	@PostMapping("/avatar")
	public PongData<UserAvatar> avatar(@RequestParam("avatarfile") MultipartFile file) throws IOException {
		if (!file.isEmpty()) {
			LoginUser loginUser = SecurityUtils.getLoginUser();
			String avatar = FileUploadUtils.upload(getAvatarPath(), file);
			if (userService.updateUserAvatar(loginUser.getUsername(), avatar)) {
				// 更新缓存用户头像
				loginUser.getUser().setAvatar(avatar);
				tokenService.setLoginUser(loginUser);
				return PongUtils.okData(new UserAvatar(avatar));
			}
		}
		return PongUtils.error("上传图片异常，请联系管理员");
	}
	
	
	/**
	 * 获取头像上传路径
	 */
	private static String getAvatarPath() {
		return FileConfigHelper.getDefaultBaseDir() + "/avatar";
	}
	
}
