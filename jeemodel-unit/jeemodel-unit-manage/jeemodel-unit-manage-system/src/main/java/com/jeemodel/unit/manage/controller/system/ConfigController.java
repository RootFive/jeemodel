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

import com.baomidou.dynamic.datasource.annotation.DS;
import com.jeemodel.bean.http.Pong;
import com.jeemodel.bean.http.PongData;
import com.jeemodel.bean.http.PongTable;
import com.jeemodel.bean.http.PongUtils;
import com.jeemodel.core.web.controller.BaseController;
import com.jeemodel.data.utils.PageUtils;
import com.jeemodel.unit.manage.bean.dto.system.ConfigListReq;
import com.jeemodel.unit.manage.bean.entity.Config;
import com.jeemodel.unit.manage.core.annotation.Log;
import com.jeemodel.unit.manage.core.constant.UserConstants;
import com.jeemodel.unit.manage.core.enums.BusinessType;
import com.jeemodel.unit.manage.core.utils.SecurityUtils;
import com.jeemodel.unit.manage.core.utils.poi.ExcelUtil;
import com.jeemodel.unit.manage.service.IConfigService;

/**
 * 参数配置 信息操作处理
 * 
 * @author Rootfive
 */
@RestController
@RequestMapping("/manage/config")
public class ConfigController extends BaseController {
	@Autowired
	private IConfigService configService;

	/**
	 * 获取参数配置列表
	 */
	@PreAuthorize("@ss.hasPermi('manage:config:list')")
	@GetMapping("/list")
	public PongTable<Config> list(ConfigListReq listReq) {
		PageUtils.startPage();
		List<Config> list = configService.selectConfigList(listReq);
		return PageUtils.okTable(list);
	}

	@Log(title = "参数管理", businessType = BusinessType.EXPORT)
	@PreAuthorize("@ss.hasPermi('manage:config:export')")
	@PostMapping("/export")
	public void export(HttpServletResponse response, ConfigListReq listReq) {
		List<Config> list = configService.selectConfigList(listReq);
		ExcelUtil<Config> util = new ExcelUtil<Config>(Config.class);
		util.exportExcel(response, list, "参数数据");
	}

	/**
	 * 根据参数编号获取详细信息
	 */
	@DS("slave")
	@PreAuthorize("@ss.hasPermi('manage:config:query')")
	@GetMapping(value = "/{id}")
	public PongData<Config> getInfo(@PathVariable Long id) {
		
		return PongUtils.okData(configService.getById(id));
	}

	/**
	 * 根据参数键名查询参数值
	 */
	@GetMapping(value = "/configKey/{configKey}")
	public PongData<String> getConfigKey(@PathVariable String configKey) {
		
		return PongUtils.okData(configService.selectConfigByKey(configKey));
	}

	/**
	 * 新增参数配置
	 */
	@PreAuthorize("@ss.hasPermi('manage:config:add')")
	@Log(title = "参数管理", businessType = BusinessType.INSERT)
	@PostMapping
	public Pong add(@Validated @RequestBody Config config) {
		if (UserConstants.NOT_UNIQUE.equals(configService.checkConfigKeyUnique(config))) {
			return PongUtils.fail("新增参数'" + config.getConfigName() + "'失败，参数键名已存在");
		}
		config.setCreateBy(SecurityUtils.getUsername());
		return PongUtils.result(configService.insertConfig(config));
	}

	/**
	 * 修改参数配置
	 */
	@PreAuthorize("@ss.hasPermi('manage:config:edit')")
	@Log(title = "参数管理", businessType = BusinessType.UPDATE)
	@PutMapping
	public Pong edit(@Validated @RequestBody Config config) {
		if (UserConstants.NOT_UNIQUE.equals(configService.checkConfigKeyUnique(config))) {
			return PongUtils.fail("修改参数'" + config.getConfigName() + "'失败，参数键名已存在");
		}
		config.setUpdateBy(SecurityUtils.getUsername());
		return PongUtils.result(configService.updateConfig(config));
	}

	/**
	 * 删除参数配置
	 */
	@PreAuthorize("@ss.hasPermi('manage:config:remove')")
	@Log(title = "参数管理", businessType = BusinessType.DELETE)
	@DeleteMapping("/{ids}")
	public Pong remove(@PathVariable Long[] ids) {
		configService.deleteConfigByIds(ids);
		return PongUtils.ok();
	}

	/**
	 * 刷新参数缓存
	 */
	@PreAuthorize("@ss.hasPermi('manage:config:remove')")
	@Log(title = "参数管理", businessType = BusinessType.CLEAN)
	@DeleteMapping("/refreshCache")
	public Pong refreshCache() {
		configService.resetConfigCache();
		return PongUtils.ok();
	}
}
