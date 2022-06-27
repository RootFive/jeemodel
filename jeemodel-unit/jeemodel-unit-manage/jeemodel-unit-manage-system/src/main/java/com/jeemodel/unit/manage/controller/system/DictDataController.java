package com.jeemodel.unit.manage.controller.system;

import java.util.ArrayList;
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
import com.jeemodel.core.utils.BlankUtils;
import com.jeemodel.core.web.controller.BaseController;
import com.jeemodel.data.utils.PageUtils;
import com.jeemodel.unit.manage.bean.dto.system.DictDataListReq;
import com.jeemodel.unit.manage.core.annotation.Log;
import com.jeemodel.unit.manage.core.bean.entity.DictData;
import com.jeemodel.unit.manage.core.enums.BusinessType;
import com.jeemodel.unit.manage.core.utils.SecurityUtils;
import com.jeemodel.unit.manage.core.utils.poi.ExcelUtil;
import com.jeemodel.unit.manage.service.IDictDataService;
import com.jeemodel.unit.manage.service.IDictService;

/**
 * 数据字典信息
 * 
 * @author Rootfive
 */
@RestController
@RequestMapping("/manage/dict/data")
public class DictDataController extends BaseController {
	@Autowired
	private IDictDataService dictDataService;

	@Autowired
	private IDictService dictTypeService;

	@PreAuthorize("@ss.hasPermi('manage:dict:list')")
	@GetMapping("/list")
	public PongTable<DictData> list(DictDataListReq listReq) {
		PageUtils.startPage();
		List<DictData> list = dictDataService.selectDictDataList(listReq);
		return PageUtils.okTable(list);
	}

	@Log(title = "字典数据", businessType = BusinessType.EXPORT)
	@PreAuthorize("@ss.hasPermi('manage:dict:export')")
	@PostMapping("/export")
	public void export(HttpServletResponse response, DictDataListReq listReq) {
		List<DictData> list = dictDataService.selectDictDataList(listReq);
		ExcelUtil<DictData> util = new ExcelUtil<DictData>(DictData.class);
		util.exportExcel(response, list, "字典数据");
	}

	/**
	 * 查询字典数据详细
	 */
	@PreAuthorize("@ss.hasPermi('manage:dict:query')")
	@GetMapping(value = "/{id}")
	public PongData<DictData> getInfo(@PathVariable Long id) {
		return PongUtils.okData(dictDataService.selectDictDataById(id));
	}

	/**
	 * 根据字典类型查询字典数据信息
	 */
	@GetMapping(value = "/type/{dictType}")
	public PongData<List<DictData>> dictType(@PathVariable String dictType) {
		List<DictData> data = dictTypeService.selectDictDataByType(dictType);
		if (BlankUtils.isNull(data)) {
			data = new ArrayList<DictData>();
		}
		return PongUtils.okData(data);
	}

	/**
	 * 新增字典类型
	 */
	@PreAuthorize("@ss.hasPermi('manage:dict:add')")
	@Log(title = "字典数据", businessType = BusinessType.INSERT)
	@PostMapping
	public Pong add(@Validated @RequestBody DictData dict) {
		dict.setCreateBy(SecurityUtils.getUsername());
		return PongUtils.result(dictDataService.insertDictData(dict));
	}

	/**
	 * 修改保存字典类型
	 */
	@PreAuthorize("@ss.hasPermi('manage:dict:edit')")
	@Log(title = "字典数据", businessType = BusinessType.UPDATE)
	@PutMapping
	public Pong edit(@Validated @RequestBody DictData dict) {
		dict.setUpdateBy(SecurityUtils.getUsername());
		return PongUtils.result(dictDataService.updateDictData(dict));
	}

	/**
	 * 删除字典类型
	 */
	@PreAuthorize("@ss.hasPermi('manage:dict:remove')")
	@Log(title = "字典类型", businessType = BusinessType.DELETE)
	@DeleteMapping("/{ids}")
	public Pong remove(@PathVariable Long[] ids) {
		dictDataService.deleteDictDataByIds(ids);
		return PongUtils.ok();
	}
}
