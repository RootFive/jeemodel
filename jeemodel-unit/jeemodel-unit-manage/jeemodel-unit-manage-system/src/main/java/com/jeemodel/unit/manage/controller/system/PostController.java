package com.jeemodel.unit.manage.controller.system;

import java.util.List;

import javax.servlet.http.HttpServletResponse;

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

import com.jeemodel.bean.rpc.Pong;
import com.jeemodel.bean.rpc.PongData;
import com.jeemodel.bean.rpc.PongTable;
import com.jeemodel.bean.rpc.PongUtils;
import com.jeemodel.core.web.controller.BaseController;
import com.jeemodel.data.utils.PageUtils;
import com.jeemodel.unit.manage.bean.entity.Post;
import com.jeemodel.unit.manage.core.annotation.Log;
import com.jeemodel.unit.manage.core.constant.UserConstants;
import com.jeemodel.unit.manage.core.enums.BusinessType;
import com.jeemodel.unit.manage.core.utils.SecurityUtils;
import com.jeemodel.unit.manage.core.utils.poi.ExcelUtil;
import com.jeemodel.unit.manage.service.IPostService;

/**
 * 岗位信息操作处理
 * 
 * @author Rootfive
 */
@RestController
@RequestMapping("/manage/post")
public class PostController extends BaseController {
	@Autowired
	private IPostService postService;

	/**
	 * 获取岗位列表
	 */
	@PreAuthorize("@ss.hasPermi('manage:post:list')")
	@GetMapping("/list")
	public PongTable<Post> list(Post post) {
		PageUtils.startPage();
		List<Post> list = postService.selectPostList(post);
		return PageUtils.okTable(list);
	}

	@Log(title = "岗位管理", businessType = BusinessType.EXPORT)
	@PreAuthorize("@ss.hasPermi('manage:post:export')")
	@PostMapping("/export")
	public void export(HttpServletResponse response, Post post) {
		List<Post> list = postService.selectPostList(post);
		ExcelUtil<Post> util = new ExcelUtil<Post>(Post.class);
		util.exportExcel(response, list, "岗位数据");
	}

	/**
	 * 根据岗位编号获取详细信息
	 */
	@PreAuthorize("@ss.hasPermi('manage:post:query')")
	@GetMapping(value = "/{id}")
	public PongData<Post> getInfo(@PathVariable Long id) {
		return PongUtils.okData(postService.getById(id));
	}

	/**
	 * 新增岗位
	 */
	@PreAuthorize("@ss.hasPermi('manage:post:add')")
	@Log(title = "岗位管理", businessType = BusinessType.INSERT)
	@PostMapping
	public Pong add(@Validated @RequestBody Post post) {
		if (UserConstants.NOT_UNIQUE.equals(postService.checkPostNameUnique(post))) {
			return PongUtils.fail("新增岗位'" + post.getPostName() + "'失败，岗位名称已存在");
		} else if (UserConstants.NOT_UNIQUE.equals(postService.checkPostCodeUnique(post))) {
			return PongUtils.fail("新增岗位'" + post.getPostName() + "'失败，岗位编码已存在");
		}
		post.setCreateBy(SecurityUtils.getUsername());
		return PongUtils.result(postService.save(post));
	}

	/**
	 * 修改岗位
	 */
	@PreAuthorize("@ss.hasPermi('manage:post:edit')")
	@Log(title = "岗位管理", businessType = BusinessType.UPDATE)
	@PutMapping
	public Pong edit(@Validated @RequestBody Post post) {
		if (UserConstants.NOT_UNIQUE.equals(postService.checkPostNameUnique(post))) {
			return PongUtils.fail("修改岗位'" + post.getPostName() + "'失败，岗位名称已存在");
		} else if (UserConstants.NOT_UNIQUE.equals(postService.checkPostCodeUnique(post))) {
			return PongUtils.fail("修改岗位'" + post.getPostName() + "'失败，岗位编码已存在");
		}
		post.setUpdateBy(SecurityUtils.getUsername());
		return PongUtils.result(postService.updateById(post));
	}

	/**
	 * 删除岗位
	 */
	@PreAuthorize("@ss.hasPermi('manage:post:remove')")
	@Log(title = "岗位管理", businessType = BusinessType.DELETE)
	@DeleteMapping("/{ids}")
	public Pong remove(@PathVariable Long[] ids) {
		return PongUtils.result(postService.deletePostByIds(ids));
	}

	/**
	 * 获取岗位选择框列表
	 */
	@GetMapping("/optionselect")
	public PongData<List<Post>> optionselect() {
		List<Post> posts = postService.lambdaQuery().list();
		return PongUtils.okData(posts);
	}
}
