package com.jeemodel.unit.coding.controller.tool;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

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

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import io.swagger.annotations.ApiOperation;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * swagger 用户测试方法
 * 
 * @author Rootfive
 */
@SuppressWarnings("unchecked")
@Api("用户信息管理")
@RestController
@RequestMapping("/coding/test/user")
public class TestController extends BaseController {
	private final static Map<Integer, UserEntity> users = new LinkedHashMap<Integer, UserEntity>();
	{
		users.put(1, new UserEntity(1, "admin", "admin123", "15888888888"));
		users.put(2, new UserEntity(2, "ry", "admin123", "15666666666"));
	}

	@ApiOperation("获取用户列表")
	@GetMapping("/list")
	public PongData<List<UserEntity>> userList() {
		List<UserEntity> userList = new ArrayList<UserEntity>(users.values());
		return PongUtils.okData(userList);
	}

	@ApiOperation("获取用户详细")
	@ApiImplicitParam(name = "userId", value = "用户ID", required = true, dataType = "int", paramType = "path", dataTypeClass = Integer.class)
	@GetMapping("/{userId}")
	public PongData<UserEntity> getUser(@PathVariable Integer userId) {
		if (!users.isEmpty() && users.containsKey(userId)) {
			return PongUtils.okData(users.get(userId));
		} else {
			return PongUtils.fail("用户不存在");
		}
	}

	@ApiOperation("新增用户")
	@ApiImplicitParams({
			@ApiImplicitParam(name = "userId", value = "用户id", dataType = "Integer", dataTypeClass = Integer.class),
			@ApiImplicitParam(name = "username", value = "用户名称", dataType = "String", dataTypeClass = String.class),
			@ApiImplicitParam(name = "password", value = "用户密码", dataType = "String", dataTypeClass = String.class),
			@ApiImplicitParam(name = "mobile", value = "用户手机", dataType = "String", dataTypeClass = String.class) })
	@PostMapping("/save")
	public PongData<UserEntity> save(UserEntity user) {
		if (StringUtils.isNull(user) || StringUtils.isNull(user.getUserId())) {
			return PongUtils.fail("用户ID不能为空");
		}
		return PongUtils.okData(users.put(user.getUserId(), user));
	}

	@ApiOperation("更新用户")
	@PutMapping("/update")
	public PongData<UserEntity> update(@RequestBody UserEntity user) {
		if (StringUtils.isNull(user) || StringUtils.isNull(user.getUserId())) {
			return PongUtils.fail("用户ID不能为空");
		}
		if (users.isEmpty() || !users.containsKey(user.getUserId())) {
			return PongUtils.fail("用户不存在");
		}
		users.remove(user.getUserId());
		return PongUtils.okData(users.put(user.getUserId(), user));
	}

	@ApiOperation("删除用户信息")
	@ApiImplicitParam(name = "userId", value = "用户ID", required = true, dataType = "int", paramType = "path", dataTypeClass = Integer.class)
	@DeleteMapping("/{userId}")
	public Pong delete(@PathVariable Integer userId) {
		if (!users.isEmpty() && users.containsKey(userId)) {
			users.remove(userId);
			return PongUtils.ok();
		} else {
			return PongUtils.fail("用户不存在");
		}
	}
}

@Data
@NoArgsConstructor
@ApiModel(value = "UserEntity", description = "用户实体")
class UserEntity {
	@ApiModelProperty("用户ID")
	private Integer userId;

	@ApiModelProperty("用户名称")
	private String username;

	@ApiModelProperty("用户密码")
	private String password;

	@ApiModelProperty("用户手机")
	private String mobile;

	public UserEntity(Integer userId, String username, String password, String mobile) {
		this.userId = userId;
		this.username = username;
		this.password = password;
		this.mobile = mobile;
	}

}
