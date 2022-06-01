package com.jeemodel.unit.coding.controller;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;

import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.ArrayUtils;
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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.jeemodel.bean.http.Pong;
import com.jeemodel.bean.http.PongData;
import com.jeemodel.bean.http.PongTable;
import com.jeemodel.bean.http.PongUtils;
import com.jeemodel.core.utils.DataTypeConvertUtils;
import com.jeemodel.core.utils.ExceptionUtils;
import com.jeemodel.core.web.controller.BaseController;
import com.jeemodel.data.utils.PageUtils;
import com.jeemodel.unit.coding.dto.DBTableListReq;
import com.jeemodel.unit.coding.dto.EditGenTable;
import com.jeemodel.unit.coding.dto.GenTableDetailsVO;
import com.jeemodel.unit.coding.dto.GenTableIncludeColumn;
import com.jeemodel.unit.coding.dto.GenTableListReq;
import com.jeemodel.unit.coding.entity.GenTable;
import com.jeemodel.unit.coding.entity.GenTableColumn;
import com.jeemodel.unit.coding.service.IGenTableColumnService;
import com.jeemodel.unit.coding.service.IGenTableService;
import com.jeemodel.unit.manage.core.annotation.Log;
import com.jeemodel.unit.manage.core.enums.BusinessType;

/**
 * 代码生成 操作处理
 * 
 * @author Rootfive
 */
@RestController
@RequestMapping("/coding/gen")
public class GenController extends BaseController {
	
	@Autowired
	private IGenTableService genTableService;

	@Autowired
	private IGenTableColumnService genTableColumnService;

	/**
	 * 查询代码生成列表
	 */
	@PreAuthorize("@ss.hasPermi('coding:gen:list')")
	@GetMapping("/list")
	public PongTable<GenTable> genList(GenTableListReq listReq) {
		PageUtils.startPage();
		List<GenTable> list = genTableService.selectGenTableList(listReq);
		return PageUtils.okTable(list);
	}

	/**
	 * 修改代码生成业务
	 */
	@PreAuthorize("@ss.hasPermi('coding:gen:query')")
	@GetMapping(value = "/{talbleId}")
	public PongData<GenTableDetailsVO> getInfo(@PathVariable Long talbleId) {
		GenTableIncludeColumn table = genTableService.selectGenTableById(talbleId);
		
		List<GenTableIncludeColumn> tables = genTableService.allGenTableIncludeColumn();
		
		List<GenTableColumn> list = genTableColumnService.selectGenTableColumnListByTableId(talbleId);
		
		GenTableDetailsVO genTableDetailsVO = new GenTableDetailsVO(table, list, tables);
		
		return PongUtils.okData(genTableDetailsVO);
	}

	/**
	 * 查询数据库列表
	 */
	@PreAuthorize("@ss.hasPermi('coding:gen:list')")
	@GetMapping("/db/list")
	public PongTable<GenTable> dataList(DBTableListReq listReq) {
		PageUtils.startPage();
		List<GenTable> list = genTableService.selectDbTableList(listReq);
		return PageUtils.okTable(list);
	}

	/**
	 * 查询数据表字段列表
	 */
	@PreAuthorize("@ss.hasPermi('coding:gen:list')")
	@GetMapping(value = "/column/{talbleId}")
	public PongTable<GenTableColumn> columnList(Long tableId) {
		List<GenTableColumn> list = genTableColumnService.selectGenTableColumnListByTableId(tableId);
		return PageUtils.okTable(list);
	}

	/**
	 * 导入表结构（保存）
	 */
	@PreAuthorize("@ss.hasPermi('coding:gen:import')")
	@Log(title = "代码生成", businessType = BusinessType.IMPORT)
	@PostMapping("/importTable")
	public Pong importTableSave(String tableSchemas) {
		String[] dbTableSchemas = DataTypeConvertUtils.toStrArray(tableSchemas);
		// 查询表信息
		List<GenTable> tableList = genTableService.selectDbTableListByNames(dbTableSchemas);
		genTableService.importGenTable(tableList);
		return PongUtils.ok();
	}

	/**
	 * 修改保存代码生成业务
	 */
	@PreAuthorize("@ss.hasPermi('coding:gen:edit')")
	@Log(title = "代码生成", businessType = BusinessType.UPDATE)
	@PutMapping
	public Pong editSave(@Validated @RequestBody EditGenTable genTable) {
		genTableService.validateEdit(genTable);
		genTableService.updateGenTable(genTable);
		return PongUtils.ok();
	}

	/**
	 * 删除代码生成
	 */
	@PreAuthorize("@ss.hasPermi('coding:gen:remove')")
	@Log(title = "代码生成", businessType = BusinessType.DELETE)
	@DeleteMapping("/{tableIds}")
	public Pong remove(@PathVariable Long[] tableIds) {
		genTableService.deleteGenTableByIds(tableIds);
		return PongUtils.ok();
	}

	/**
	 * 预览代码
	 */
	@PreAuthorize("@ss.hasPermi('coding:gen:preview')")
	@GetMapping("/preview/{tableId}")
	public PongData<Map<String, String>> preview(@PathVariable("tableId") Long tableId) throws IOException {
		Map<String, String> dataMap = genTableService.previewCode(tableId);
		return PongUtils.okData(dataMap);
	}

	/**
	 * 生成代码（下载方式）
	 */
	@Deprecated
	@PreAuthorize("@ss.hasPermi('coding:gen:code')")
	@Log(title = "代码生成", businessType = BusinessType.GENCODE)
	@GetMapping("/download/{tableId}")
	public void download(HttpServletResponse response, @PathVariable("tableId") Long tableId) throws IOException {
		byte[] data = genTableService.downloadCode(tableId);
		genCode(response, data);
	}

	/**
	 * 生成代码（自定义路径）
	 */
	@PreAuthorize("@ss.hasPermi('coding:gen:code')")
	@Log(title = "代码生成", businessType = BusinessType.GENCODE)
	@GetMapping("/genCode/{tableId}")
	public Pong genCode(@PathVariable("tableId") Long tableId) {
		genTableService.generatorCode(tableId);
		return PongUtils.ok();
	}

	/**
	 * 同步数据库
	 */
	@PreAuthorize("@ss.hasPermi('coding:gen:edit')")
	@Log(title = "代码生成", businessType = BusinessType.UPDATE)
	@GetMapping("/synchDb/{tableId}")
	public Pong synchDb(@PathVariable("tableId") Long tableId) {
		genTableService.synchDb(tableId);
		return PongUtils.ok();
	}

	/**
	 * 批量生成代码
	 */
	@PreAuthorize("@ss.hasPermi('coding:gen:code')")
	@Log(title = "代码生成", businessType = BusinessType.GENCODE)
	@GetMapping("/batchGenCode")
	public void batchGenCode(HttpServletResponse response,@RequestParam(value = "tableIds") Long[] tableIds) throws IOException {
//		String[] tableNames = DataTypeConvertUtils.toStrArray(tables);
		if (ArrayUtils.isEmpty(tableIds)) {
			throw ExceptionUtils.missing("请选择要生成的数据");
		}
		
		byte[] data = genTableService.downloadCode(tableIds);
		genCode(response, data);
	}

	/**
	 * 生成zip文件
	 */
	private void genCode(HttpServletResponse response, byte[] data) throws IOException {
		response.reset();
		response.addHeader("Access-Control-Allow-Origin", "*");
		response.addHeader("Access-Control-Expose-Headers", "Content-Disposition");
		response.setHeader("Content-Disposition", "attachment; filename=\"JeeModel代码生成.zip\"");
		response.addHeader("Content-Length", "" + data.length);
		response.setContentType("application/octet-stream; charset=UTF-8");
		IOUtils.write(data, response.getOutputStream());
	}
}