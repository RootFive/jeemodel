package com.jeemodel.unit.manage.controller.system;

import java.util.Arrays;
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
import com.jeemodel.bean.http.PongTable;
import com.jeemodel.bean.http.PongUtils;
import com.jeemodel.core.web.controller.BaseController;
import com.jeemodel.data.utils.PageUtils;
import com.jeemodel.unit.manage.bean.entity.Notice;
import com.jeemodel.unit.manage.core.annotation.Log;
import com.jeemodel.unit.manage.core.enums.BusinessType;
import com.jeemodel.unit.manage.core.utils.SecurityUtils;
import com.jeemodel.unit.manage.service.INoticeService;

/**
 * 公告 信息操作处理
 * 
 * @author Rootfive
 */
@RestController
@RequestMapping("/manage/notice")
public class NoticeController extends BaseController {
	@Autowired
	private INoticeService noticeService;

	/**
	 * 获取通知公告列表
	 */
	@PreAuthorize("@ss.hasPermi('manage:notice:list')")
	@GetMapping("/list")
	public PongTable<Notice> list(Notice notice) {
		PageUtils.startPage();
		List<Notice> list = noticeService.selectNoticeList(notice);
		return PageUtils.okTable(list);
	}

	/**
	 * 根据通知公告编号获取详细信息
	 */
	@PreAuthorize("@ss.hasPermi('manage:notice:query')")
	@GetMapping(value = "/{id}")
	public PongData<Notice> getInfo(@PathVariable Long id) {
		return PongUtils.okData(noticeService.getById(id));
	}

	/**
	 * 新增通知公告
	 */
	@PreAuthorize("@ss.hasPermi('manage:notice:add')")
	@Log(title = "通知公告", businessType = BusinessType.INSERT)
	@PostMapping
	public Pong add(@Validated @RequestBody Notice notice) {
		notice.setCreateBy(SecurityUtils.getUsername());
		return PongUtils.result(noticeService.save(notice));
	}

	/**
	 * 修改通知公告
	 */
	@PreAuthorize("@ss.hasPermi('manage:notice:edit')")
	@Log(title = "通知公告", businessType = BusinessType.UPDATE)
	@PutMapping
	public Pong edit(@Validated @RequestBody Notice notice) {
		notice.setUpdateBy(SecurityUtils.getUsername());
		return PongUtils.result(noticeService.updateById(notice));
	}

	/**
	 * 删除通知公告
	 */
	@PreAuthorize("@ss.hasPermi('manage:notice:remove')")
	@Log(title = "通知公告", businessType = BusinessType.DELETE)
	@DeleteMapping("/{ids}")
	public Pong remove(@PathVariable Long[] ids) {
//		return PongUtils.result(noticeService.deleteNoticeByIds(ids));
		return PongUtils.result(noticeService.removeByIds(Arrays.asList(ids)));
	}
}
