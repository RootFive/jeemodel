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

import com.jeemodel.bean.http.Pong;
import com.jeemodel.bean.http.PongData;
import com.jeemodel.bean.http.PongTable;
import com.jeemodel.bean.http.PongUtils;
import com.jeemodel.core.web.controller.BaseController;
import com.jeemodel.data.utils.PageUtils;
import com.jeemodel.unit.manage.bean.dto.system.DictListReq;
import com.jeemodel.unit.manage.core.annotation.Log;
import com.jeemodel.unit.manage.core.bean.entity.Dict;
import com.jeemodel.unit.manage.core.constant.UserConstants;
import com.jeemodel.unit.manage.core.enums.BusinessType;
import com.jeemodel.unit.manage.core.utils.SecurityUtils;
import com.jeemodel.unit.manage.core.utils.poi.ExcelUtil;
import com.jeemodel.unit.manage.service.IDictService;

/**
 * 数据字典信息
 * 
 * @author Rootfive
 */
@RestController
@RequestMapping("/manage/dict/type")
public class DictController extends BaseController {
	@Autowired
	private IDictService dictTypeService;

	@PreAuthorize("@ss.hasPermi('manage:dict:list')")
	@GetMapping("/list")
	public PongTable<Dict> list(DictListReq listReq) {
		PageUtils.startPage();
		List<Dict> list = dictTypeService.selectDictTypeList(listReq);
		return PageUtils.okTable(list);
	}

	@Log(title = "字典类型", businessType = BusinessType.EXPORT)
	@PreAuthorize("@ss.hasPermi('manage:dict:export')")
	@PostMapping("/export")
	public void export(HttpServletResponse response, DictListReq listReq) {
		List<Dict> list = dictTypeService.selectDictTypeList(listReq);
		ExcelUtil<Dict> util = new ExcelUtil<Dict>(Dict.class);
		util.exportExcel(response, list, "字典类型");
	}

	/**
	 * 查询字典类型详细
	 */
	@PreAuthorize("@ss.hasPermi('manage:dict:query')")
	@GetMapping(value = "/{id}")
	public PongData<Dict> getInfo(@PathVariable Long id) {
		return PongUtils.okData(dictTypeService.selectDictTypeById(id));
	}

	/**
	 * 新增字典类型
	 */
	@PreAuthorize("@ss.hasPermi('manage:dict:add')")
	@Log(title = "字典类型", businessType = BusinessType.INSERT)
	@PostMapping
	public Pong add(@Validated @RequestBody Dict dict) {
		if (UserConstants.NOT_UNIQUE.equals(dictTypeService.checkDictTypeUnique(dict))) {
			return PongUtils.fail("新增字典'" + dict.getDictName() + "'失败，字典类型已存在");
		}
		dict.setCreateBy(SecurityUtils.getUsername());
		return PongUtils.result(dictTypeService.insertDictType(dict));
	}

	/**
	 * 修改字典类型
	 */
	@PreAuthorize("@ss.hasPermi('manage:dict:edit')")
	@Log(title = "字典类型", businessType = BusinessType.UPDATE)
	@PutMapping
	public Pong edit(@Validated @RequestBody Dict dict) {
		if (UserConstants.NOT_UNIQUE.equals(dictTypeService.checkDictTypeUnique(dict))) {
			return PongUtils.fail("修改字典'" + dict.getDictName() + "'失败，字典类型已存在");
		}
		dict.setUpdateBy(SecurityUtils.getUsername());
		return PongUtils.result(dictTypeService.updateDictType(dict));
	}

	/**
	 * 删除字典类型
	 */
	@PreAuthorize("@ss.hasPermi('manage:dict:remove')")
	@Log(title = "字典类型", businessType = BusinessType.DELETE)
	@DeleteMapping("/{ids}")
	public Pong remove(@PathVariable Long[] ids) {
		dictTypeService.deleteDictTypeByIds(ids);
		return PongUtils.ok();
	}

	/**
	 * 刷新字典缓存
	 */
	@PreAuthorize("@ss.hasPermi('manage:dict:remove')")
	@Log(title = "字典类型", businessType = BusinessType.CLEAN)
	@DeleteMapping("/refreshCache")
	public Pong refreshCache() {
		dictTypeService.resetDictCache();
		return PongUtils.ok();
	}

	/**
	 * 获取字典选择框列表
	 */
	@GetMapping("/optionselect")
	public PongData<List<Dict>> optionselect() {
		List<Dict> dictTypes = dictTypeService.selectDictTypeAll();
		return PongUtils.okData(dictTypes);
	}
}
