package com.jeemodel.unit.coding.service.impl;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.StringWriter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.IOUtils;
import org.apache.velocity.Template;
import org.apache.velocity.VelocityContext;
import org.apache.velocity.app.Velocity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.alibaba.fastjson2.JSON;
import com.alibaba.fastjson2.JSONObject;
import com.baomidou.dynamic.datasource.annotation.DS;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.jeemodel.bean.exception.type.CheckException;
import com.jeemodel.core.constant.Constants;
import com.jeemodel.core.utils.BlankUtils;
import com.jeemodel.core.utils.DataTypeConvertUtils;
import com.jeemodel.core.utils.StringUtils;
import com.jeemodel.unit.coding.constant.GenConstants;
import com.jeemodel.unit.coding.dto.DBTableListReq;
import com.jeemodel.unit.coding.dto.DBTableSchema;
import com.jeemodel.unit.coding.dto.EditGenTable;
import com.jeemodel.unit.coding.dto.GenTableIncludeColumn;
import com.jeemodel.unit.coding.dto.GenTableListReq;
import com.jeemodel.unit.coding.entity.GenTable;
import com.jeemodel.unit.coding.entity.GenTableColumn;
import com.jeemodel.unit.coding.mapper.DbMySQLMapper;
import com.jeemodel.unit.coding.mapper.GenTableMapper;
import com.jeemodel.unit.coding.service.IGenTableColumnService;
import com.jeemodel.unit.coding.service.IGenTableService;
import com.jeemodel.unit.coding.util.GenUtils;
import com.jeemodel.unit.coding.util.VelocityInitializer;
import com.jeemodel.unit.coding.util.VelocityUtils;
import com.jeemodel.unit.manage.core.utils.SecurityUtils;

/**
 * 业务 服务层实现
 * 
 * @author Rootfive
 */
@DS("gen")
@Service
public class GenTableServiceImpl extends ServiceImpl<GenTableMapper, GenTable>  implements IGenTableService {

	@Autowired
	private DbMySQLMapper dbMySQLMapper;
	
	@Autowired
	private IGenTableColumnService genTableColumnService;

	/**
	 * 查询业务信息
	 * 
	 * @param id 业务ID
	 * @return 业务信息
	 */
	@Override
	public GenTableIncludeColumn selectGenTableById(Long id) {
		GenTableIncludeColumn genTable = baseMapper.selectGenTableById(id);
		setTableFromOptions(genTable);
		return genTable;
	}

	/**
	 * 查询业务列表
	 * 
	 * @param genTable 业务信息
	 * @return 业务集合
	 */
	@Override
	public List<GenTable> selectGenTableList(GenTableListReq listReq) {
		return baseMapper.selectGenTableList(listReq);
	}

	/**
	 * 查询据库列表
	 * 
	 * @param genTable 业务信息
	 * @return 数据库表集合
	 */
	@Override
	public List<GenTable> selectDbTableList(DBTableListReq listReq) {
		return dbMySQLMapper.selectDbTableList(listReq);
	}

	/**
	 * 查询据库列表
	 * 
	 * @param tableNames 表名称组
	 * @return 数据库表集合
	 */
	@Override
	public List<GenTable> selectDbTableListByNames(String ... dbTableSchemas) {
		
		//构建查询数据库list对象
		List<DBTableSchema> listTableSchema = new ArrayList<>();
		
		for (String dbTableSchema : dbTableSchemas) {
			if (StringUtils.isBlank(dbTableSchema)) {
				continue;
			}
			
			String[] dbAndTable = DataTypeConvertUtils.toStrArray("@-@",dbTableSchema);
			if (dbAndTable == null || dbAndTable.length < 2) {
				continue;
			}
			DBTableSchema tableSchema = new DBTableSchema();
			tableSchema.setTableSchema(dbAndTable[0]);
			tableSchema.setTableName(dbAndTable[1]);
			listTableSchema.add(tableSchema);
		}
		
		if (CollectionUtils.isNotEmpty(listTableSchema)) {
			return dbMySQLMapper.selectDbTableListByNames(listTableSchema);
		}
		return new ArrayList<>();
	}

	/**
	 * 查询所有表信息
	 * 
	 * @return 表信息集合
	 */
	@Override
	public List<GenTableIncludeColumn> allGenTableIncludeColumn() {
		return baseMapper.allGenTableIncludeColumn();
	}

	/**
	 * 修改业务
	 * 
	 * @param genTable 业务信息
	 * @return 结果
	 */
	@Override
	@Transactional
	public void updateGenTable(EditGenTable genTable) {
		String options = JSON.toJSONString(genTable.getParams());
		genTable.setOptions(options);
//		int row = baseMapper.updateGenTable(genTable);
		int row = baseMapper.updateById(genTable);
		if (row > 0) {
			for (GenTableColumn cenTableColumn : genTable.getColumns()) {
				genTableColumnService.updateGenTableColumn(cenTableColumn);
			}
		}
	}

	/**
	 * 删除业务对象
	 * 
	 * @param tableIds 需要删除的数据ID
	 * @return 结果
	 */
	@Override
	@Transactional
	public void deleteGenTableByIds(Long[] tableIds) {
		LambdaQueryWrapper<GenTable> lambdaQueryWrapper = new LambdaQueryWrapper<>();
		lambdaQueryWrapper.in(GenTable::getId, Arrays.asList(tableIds));
		remove(lambdaQueryWrapper);
		
		genTableColumnService.deleteGenTableColumnByTableIds(tableIds);
	}

	/**
	 * 导入表结构
	 * 
	 * @param tableList 导入表列表
	 */
	@Override
	@Transactional
	public void importGenTable(List<GenTable> tableList) {
		String operName = SecurityUtils.getUsername();
		try {
			for (GenTable table : tableList) {
				GenUtils.initTable(table, operName);
				int row = baseMapper.insert(table);
				if (row > 0) {
					// 保存列信息
					List<GenTableColumn> genTableColumns = dbMySQLMapper.selectDbTableColumnsByName(table);
					for (GenTableColumn column : genTableColumns) {
						GenUtils.initColumnField(column, table);
						genTableColumnService.insertGenTableColumn(column);
					}
				}
			}
		} catch (Exception e) {
			throw new CheckException("导入失败：" + e.getMessage());
		}
	}

	/**
	 * 预览代码
	 * 
	 * @param tableId 表编号
	 * @return 预览数据列表
	 */
	@Override
	public Map<String, String> previewCode(Long tableId) {
		Map<String, String> dataMap = new LinkedHashMap<>();
		// 查询表信息
		GenTableIncludeColumn table = baseMapper.selectGenTableById(tableId);
		// 设置主子表信息
		setSubTable(table);
		// 设置主键列信息
		setPkColumn(table);
		VelocityInitializer.initVelocity();

		VelocityContext context = VelocityUtils.prepareContext(table);

		// 获取模板列表
		List<String> templates = VelocityUtils.getTemplateList(table);
		for (String template : templates) {
			// 渲染模板
			StringWriter sw = new StringWriter();
			Template tpl = Velocity.getTemplate(template, Constants.UTF_8);
			tpl.merge(context, sw);
			dataMap.put(template, sw.toString());
		}
		return dataMap;
	}

	/**
	 * 生成代码（下载方式）
	 * 
	 * @param tableName 表名称
	 * @return 数据
	 */
//	@Override
//	public byte[] downloadCode(Long[] tableIds) {
//		ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
//		ZipOutputStream zip = new ZipOutputStream(outputStream);
//		generatorCode(tableIds, zip);
//		IOUtils.closeQuietly(zip);
//		return outputStream.toByteArray();
//	}
	
	/**
	 * 批量生成代码（下载方式）
	 * 
	 * @param tableNames 表数组
	 * @return 数据
	 */
	@Override
	public byte[] downloadCode(Long... tableIds) {
		ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
		ZipOutputStream zip = new ZipOutputStream(outputStream);
		for (Long tableId : tableIds) {
			generatorCode(tableId, zip);
		}
		IOUtils.closeQuietly(zip);
		return outputStream.toByteArray();
	}
	
	
	
	

	/**
	 * 生成代码（自定义路径）
	 * 
	 * @param tableId 表名称
	 */
	@Override
	public void generatorCode(Long tableId) {
		// 查询表信息
		GenTableIncludeColumn table = baseMapper.selectGenTableById(tableId);
		// 设置主子表信息
		setSubTable(table);
		// 设置主键列信息
		setPkColumn(table);

		VelocityInitializer.initVelocity();

		VelocityContext context = VelocityUtils.prepareContext(table);

		// 获取模板列表
		List<String> templates = VelocityUtils.getTemplateList(table);
		for (String template : templates) {
			if (!StringUtils.containsAny(template, "sql.vm", "api.js.vm", "index.vue.vm", "index-tree.vue.vm")) {
				// 渲染模板
				StringWriter sw = new StringWriter();
				Template tpl = Velocity.getTemplate(template, Constants.UTF_8);
				tpl.merge(context, sw);
				try {
					String path = getGenPath(table, template);
					FileUtils.writeStringToFile(new File(path), sw.toString(), Constants.UTF_8);
				} catch (IOException e) {
					throw new CheckException("渲染模板失败，表名：" + table.getTableName());
				}
			}
		}
	}

	/**
	 * 同步数据库
	 * 
	 * @param tableName 表名称
	 */
	@Override
	@Transactional
	public void synchDb(Long tableId) {
		
		
		GenTableIncludeColumn table = baseMapper.selectGenTableById(tableId);
		List<GenTableColumn> tableColumns = table.getColumns();
		List<String> tableColumnNames = tableColumns.stream().map(GenTableColumn::getColumnName).collect(Collectors.toList());

		List<GenTableColumn> dbTableColumns = dbMySQLMapper.selectDbTableColumnsByName(table);
		if (BlankUtils.isBlank(dbTableColumns)) {
			throw new CheckException("同步数据失败，原表结构不存在");
		}
		List<String> dbTableColumnNames = dbTableColumns.stream().map(GenTableColumn::getColumnName)
				.collect(Collectors.toList());

		dbTableColumns.forEach(column -> {
			if (!tableColumnNames.contains(column.getColumnName())) {
				GenUtils.initColumnField(column, table);
				genTableColumnService.insertGenTableColumn(column);
			}
		});

		List<GenTableColumn> delColumns = tableColumns.stream()
				.filter(column -> !dbTableColumnNames.contains(column.getColumnName())).collect(Collectors.toList());
		if (BlankUtils.isNotBlank(delColumns)) {
			genTableColumnService.deleteGenTableColumns(delColumns);
		}
	}


	/**
	 * 查询表信息并生成代码
	 */
	private void generatorCode(Long tableId, ZipOutputStream zip) {
		// 查询表信息
		GenTableIncludeColumn table = baseMapper.selectGenTableById(tableId);
		// 设置主子表信息
		setSubTable(table);
		// 设置主键列信息
		setPkColumn(table);

		VelocityInitializer.initVelocity();

		VelocityContext context = VelocityUtils.prepareContext(table);

		// 获取模板列表
		List<String> templates = VelocityUtils.getTemplateList(table);
		for (String template : templates) {
			// 渲染模板
			StringWriter sw = new StringWriter();
			Template tpl = Velocity.getTemplate(template, Constants.UTF_8);
			tpl.merge(context, sw);
			try {
				// 添加到zip
				zip.putNextEntry(new ZipEntry(VelocityUtils.getFileName(template, table)));
				IOUtils.write(sw.toString(), zip, Constants.UTF_8);
				IOUtils.closeQuietly(sw);
				zip.flush();
				zip.closeEntry();
			} catch (IOException e) {
				log.error("渲染模板失败，表名：" + table.getTableName(), e);
			}
		}
	}

	/**
	 * 修改保存参数校验
	 * 
	 * @param genTable 业务信息
	 */
	@Override
	public void validateEdit(EditGenTable genTable) {
		if (!StringUtils.equalsAnyIgnoreCase(genTable.getVueVersion(), GenConstants.VUE_VERSION)) {
			throw new CheckException("请在生成信息选择VUE版本");
		}
		
		if (GenConstants.TPL_TREE.equals(genTable.getTplCategory())) {
			String options = JSON.toJSONString(genTable.getParams());
			JSONObject paramsObj = JSON.parseObject(options);
			if (StringUtils.isEmpty(paramsObj.getString(GenConstants.TREE_CODE))) {
				throw new CheckException("树编码字段不能为空");
			} else if (StringUtils.isEmpty(paramsObj.getString(GenConstants.TREE_PARENT_CODE))) {
				throw new CheckException("树父编码字段不能为空");
			} else if (StringUtils.isEmpty(paramsObj.getString(GenConstants.TREE_NAME))) {
				throw new CheckException("树名称字段不能为空");
			} else if (GenConstants.TPL_SUB.equals(genTable.getTplCategory())) {
				if (StringUtils.isEmpty(genTable.getSubTableName())) {
					throw new CheckException("关联子表的表名不能为空");
				} else if (StringUtils.isEmpty(genTable.getSubTableFkName())) {
					throw new CheckException("子表关联的外键名不能为空");
				}
			}
		}
	}

	/**
	 * 设置主键列信息
	 * 
	 * @param table 业务表信息
	 */
	public void setPkColumn(GenTableIncludeColumn table) {
		for (GenTableColumn column : table.getColumns()) {
			if (column.isPk()) {
				table.setPkColumn(column);
				break;
			}
		}
		if (BlankUtils.isNull(table.getPkColumn())) {
			table.setPkColumn(table.getColumns().get(0));
		}
		if (GenConstants.TPL_SUB.equals(table.getTplCategory())) {
			for (GenTableColumn column : table.getSubTable().getColumns()) {
				if (column.isPk()) {
					table.getSubTable().setPkColumn(column);
					break;
				}
			}
			if (BlankUtils.isNull(table.getSubTable().getPkColumn())) {
				table.getSubTable().setPkColumn(table.getSubTable().getColumns().get(0));
			}
		}
	}

	/**
	 * 设置主子表信息
	 * 
	 * @param table 业务表信息
	 */
	public void setSubTable(GenTableIncludeColumn table) {
		String subTableName = table.getSubTableName();
		String tableDb = table.getTableDb();
		if (StringUtils.isNotEmpty(subTableName)) {
			GenTableIncludeColumn subTable = baseMapper.selectGenTableByName(tableDb,subTableName);
			table.setSubTable(subTable);
		}
	}

	/**
	 * 设置代码生成其他选项值
	 * 
	 * @param genTable 设置后的生成对象
	 */
	public void setTableFromOptions(GenTableIncludeColumn genTable) {
		JSONObject paramsObj = JSON.parseObject(genTable.getOptions());
		if (BlankUtils.isNotNull(paramsObj)) {
			String treeCode = paramsObj.getString(GenConstants.TREE_CODE);
			String treeParentCode = paramsObj.getString(GenConstants.TREE_PARENT_CODE);
			String treeName = paramsObj.getString(GenConstants.TREE_NAME);
			String parentMenuId = paramsObj.getString(GenConstants.PARENT_MENU_ID);
			String parentMenuName = paramsObj.getString(GenConstants.PARENT_MENU_NAME);

			genTable.setTreeCode(treeCode);
			genTable.setTreeParentCode(treeParentCode);
			genTable.setTreeName(treeName);
			genTable.setParentMenuId(parentMenuId);
			genTable.setParentMenuName(parentMenuName);
		}
	}

	/**
	 * 获取代码生成地址
	 * 
	 * @param table    业务表信息
	 * @param template 模板文件路径
	 * @return 生成地址
	 */
	public static String getGenPath(GenTableIncludeColumn table, String template) {
		String genPath = table.getGenPath();
		if (StringUtils.equals(genPath, "/")) {
			return System.getProperty("user.dir") + File.separator + "src" + File.separator
					+ VelocityUtils.getFileName(template, table);
		}
		return genPath + File.separator + VelocityUtils.getFileName(template, table);
	}
}