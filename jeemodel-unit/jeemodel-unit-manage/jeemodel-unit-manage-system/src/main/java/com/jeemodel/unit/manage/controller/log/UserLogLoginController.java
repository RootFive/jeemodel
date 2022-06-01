package com.jeemodel.unit.manage.controller.log;

import java.io.IOException;
import java.util.ArrayList;
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
import com.jeemodel.core.utils.bean.BeanUtils;
import com.jeemodel.core.web.controller.BaseController;
import com.jeemodel.data.utils.PageUtils;
import com.jeemodel.solution.file.dto.ExcelSheet;
import com.jeemodel.solution.file.utils.EasyExcelUtils;
import com.jeemodel.unit.manage.bean.dto.log.UserLogLoginDTO;
import com.jeemodel.unit.manage.bean.dto.system.UserLogLoginListReq;
import com.jeemodel.unit.manage.bean.entity.UserLogLogin;
import com.jeemodel.unit.manage.core.annotation.Log;
import com.jeemodel.unit.manage.core.enums.BusinessType;
import com.jeemodel.unit.manage.service.IUserLogLoginService;

/**
 * 系统访问记录
 * 
 * @author Rootfive
 */
@RestController
@RequestMapping("/manage/log/userLogin")
public class UserLogLoginController extends BaseController {
	
	@Autowired
	private IUserLogLoginService userLogLoginService;

	@PreAuthorize("@ss.hasPermi('manage:logUserLogin:list')")
	@GetMapping("/list")
	public PongTable<UserLogLogin> list(UserLogLoginListReq listReq) {
		PageUtils.startPage();
		List<UserLogLogin> list = userLogLoginService.selectLogininforList(listReq);
		return PageUtils.okTable(list);
	}

//	@Log(title = "登录日志", businessType = BusinessType.EXPORT)
//	@PreAuthorize("@ss.hasPermi('manage:logUserLogin:export')")
//	@PostMapping("/export")
//	public void export(HttpServletResponse response, UserLogLoginListReq listReq) {
//		List<UserLogLogin> list = userLogLoginService.selectLogininforList(listReq);
//		ExcelUtil<UserLogLogin> util = new ExcelUtil<UserLogLogin>(UserLogLogin.class);
//		util.exportExcel(response, list, "登录日志");
//	}
	
	@Log(title = "登录日志", businessType = BusinessType.EXPORT)
	@PreAuthorize("@ss.hasPermi('manage:logUserLogin:export')")
	@PostMapping("/export")
	public void export(HttpServletResponse response, UserLogLoginListReq listReq) throws IOException {
		List<UserLogLogin> list = userLogLoginService.selectLogininforList(listReq);
		List<UserLogLoginDTO> converList = BeanUtils.converList(list, UserLogLoginDTO.class);
		
		ExcelSheet<UserLogLoginDTO> userLogLogin = new  ExcelSheet<>("登录日志", converList, UserLogLoginDTO.class);
		List<ExcelSheet<?>> excelShees= new ArrayList<>();
		excelShees.add(userLogLogin);
		
		EasyExcelUtils.exportFailedUsingJson(excelShees, "登录日志", response);
	}
	
	
	
	
	

	@PreAuthorize("@ss.hasPermi('manage:logUserLogin:remove')")
	@Log(title = "登录日志", businessType = BusinessType.DELETE)
	@DeleteMapping("/{ids}")
	public Pong remove(@PathVariable Long[] ids) {
//		userLogLoginService.deleteLogininforByIds(ids);
		
		boolean removeResult = userLogLoginService.removeByIds(Arrays.asList(ids));
		return PongUtils.result(removeResult);
	}

	@PreAuthorize("@ss.hasPermi('manage:logUserLogin:remove')")
	@Log(title = "登录日志", businessType = BusinessType.CLEAN)
	@DeleteMapping("/clean")
	public Pong clean() {
		userLogLoginService.cleanLogininfor();
		return PongUtils.ok();
	}
}
