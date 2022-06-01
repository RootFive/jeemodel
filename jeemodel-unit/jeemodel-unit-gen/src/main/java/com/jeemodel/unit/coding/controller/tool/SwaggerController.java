package com.jeemodel.unit.coding.controller.tool;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.jeemodel.core.utils.StringUtils;
import com.jeemodel.core.web.controller.BaseController;

/**
 * swagger 接口
 * 
 * @author Rootfive
 */
@Controller
@RequestMapping("/coding/swagger")
public class SwaggerController extends BaseController {

	
	@PreAuthorize("@ss.hasPermi('coding:swagger:view')")
	@GetMapping()
	public String index() {
		return redirect("/swagger-ui.html");
	}
	/**
	 * 页面跳转
	 */
	public String redirect(String url) {
		return StringUtils.format("redirect:{}", url);
	}
}
