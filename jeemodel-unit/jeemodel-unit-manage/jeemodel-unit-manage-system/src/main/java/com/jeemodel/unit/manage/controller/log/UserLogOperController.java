package com.jeemodel.unit.manage.controller.log;

import java.util.Arrays;
import java.util.List;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.jeemodel.bean.http.Pong;
import com.jeemodel.bean.http.PongTable;
import com.jeemodel.bean.http.PongUtils;
import com.jeemodel.core.web.controller.BaseController;
import com.jeemodel.data.utils.PageUtils;
import com.jeemodel.unit.manage.bean.dto.system.UserLogOperListReq;
import com.jeemodel.unit.manage.bean.entity.UserLogOper;
import com.jeemodel.unit.manage.core.annotation.Log;
import com.jeemodel.unit.manage.core.enums.BusinessType;
import com.jeemodel.unit.manage.core.utils.poi.ExcelUtil;
import com.jeemodel.unit.manage.service.IUserLogOperService;

/**
 * 操作日志记录
 * 
 * @author Rootfive
 */
@RestController
@RequestMapping("/manage/log/userOper")
public class UserLogOperController extends BaseController {
	@Autowired
	private IUserLogOperService operLogService;

	@PreAuthorize("@ss.hasPermi('manage:logUserOper:list')")
	@GetMapping("/list")
	public PongTable<UserLogOper> list(UserLogOperListReq listReq) {
		PageUtils.startPage();
		List<UserLogOper> list = operLogService.selectOperLogList(listReq);
		return PageUtils.okTable(list);
	}

	@Log(title = "操作日志", businessType = BusinessType.EXPORT)
	@PreAuthorize("@ss.hasPermi('manage:logUserOper:export')")
	@PostMapping("/export")
	public void export(HttpServletResponse response, UserLogOperListReq listReq) {
		List<UserLogOper> list = operLogService.selectOperLogList(listReq);
		ExcelUtil<UserLogOper> util = new ExcelUtil<UserLogOper>(UserLogOper.class);
		util.exportExcel(response, list, "操作日志");
	}

	@Log(title = "操作日志", businessType = BusinessType.DELETE)
	@PreAuthorize("@ss.hasPermi('manage:logUserOper:remove')")
	@DeleteMapping("/{ids}")
	public Pong remove(@PathVariable Long[] ids) {
//		return PongUtils.result(operLogService.deleteOperLogByIds(ids));
		return PongUtils.result(operLogService.removeByIds(Arrays.asList(ids)));
	}

	@Log(title = "操作日志", businessType = BusinessType.CLEAN)
	@PreAuthorize("@ss.hasPermi('manage:logUserOper:remove')")
	@DeleteMapping("/clean")
	public Pong clean() {
		operLogService.cleanOperLog();
		return PongUtils.ok();
	}
}
