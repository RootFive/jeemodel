package com.jeemodel.unit.coding.util;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

import org.apache.velocity.VelocityContext;

import com.alibaba.fastjson2.JSON;
import com.alibaba.fastjson2.JSONObject;
import com.jeemodel.bean.exception.type.CheckException;
import com.jeemodel.core.utils.BlankUtils;
import com.jeemodel.core.utils.DateTimeUtils;
import com.jeemodel.core.utils.StringUtils;
import com.jeemodel.unit.coding.config.GenConfigHelper;
import com.jeemodel.unit.coding.constant.GenConstants;
import com.jeemodel.unit.coding.dto.GenTableIncludeColumn;
import com.jeemodel.unit.coding.entity.GenTable;
import com.jeemodel.unit.coding.entity.GenTableColumn;
import com.jeemodel.unit.coding.model.BaseTypeModel;

/**
 * 模板处理工具类
 * 
 * @author Rootfive
 */
public class VelocityUtils {
	/** 项目空间路径 */
	private static final String PROJECT_PATH = "main/java";

	/** mybatis空间路径 */
	private static final String MYBATIS_PATH = "main/resources/mapper";

	/**
	 * 设置模板变量信息
	 *
	 * @return 模板列表
	 */
	public static VelocityContext prepareContext(GenTableIncludeColumn genTable) {
		
		//表名称
		String tableName = genTable.getTableName();
		
		//使用的模板（crud单表操作 tree树表操作 sub主子表操作）
		String tplCategory = genTable.getTplCategory();
		//生成功能名称描述
		String functionName = genTable.getFunctionName();
		//实体类名称(首字母大写)
		String className = genTable.getClassName();
		//实体类名称(首字母小写)
		String classNameUncapitalize = StringUtils.uncapitalize(className);
		
		//生成模块名
		String moduleName = genTable.getModuleName();
		//生成业务名
		String businessName = genTable.getBusinessName();
		//生成业务名(首字母大写)
		String businessNameCapitalize = StringUtils.capitalize(businessName);
		
		//生成包路径
		String packageName = genTable.getPackageName();
		//获取包前缀
		String packagePrefix = getPackagePrefix(packageName);
		
		//作者
		String functionAuthor = genTable.getFunctionAuthor();
		
		//主键信息
		GenTableColumn pkColumn = genTable.getPkColumn();
		//导入包列表 -根据列类型获取
		HashSet<String> importList = getImportList(genTable);
		
		//权限前缀
		String permissionPrefix = getPermissionPrefix(moduleName, businessName);
		
		//表列信息
		List<GenTableColumn> columns = genTable.getColumns();
		//基类父类baseEntityType
//		String baseEntityType = GenUtils.getClassBaseType(columns);
		BaseTypeModel tableBaseType = GenUtils.getTableBaseType(columns);
		

		VelocityContext velocityContext = new VelocityContext();
		velocityContext.put("tplCategory", tplCategory);
		velocityContext.put("tableName", tableName);
		velocityContext.put("functionName", StringUtils.isNotEmpty(functionName) ? functionName : "【请填写功能名称】");
		velocityContext.put("ClassName", className);
		velocityContext.put("className", classNameUncapitalize);
		velocityContext.put("moduleName", moduleName);
		velocityContext.put("BusinessName", businessNameCapitalize);
		velocityContext.put("businessName", businessName);
		velocityContext.put("basePackage", packagePrefix);
		velocityContext.put("packageName", packageName);
		velocityContext.put("author", functionAuthor);
		velocityContext.put("datetime", DateTimeUtils.yyyy_MM_dd());
		velocityContext.put("pkColumn", pkColumn);
		velocityContext.put("importList", importList);
		velocityContext.put("permissionPrefix", permissionPrefix);
		velocityContext.put("columns", columns);
		velocityContext.put("table", genTable);
		velocityContext.put("dicts", getDicts(genTable));
		velocityContext.put("baseEntityType", tableBaseType.getBaseEntityType());
		velocityContext.put("baseDTOType", tableBaseType.getBaseDTOType());
		velocityContext.put("baseEditSaveType", tableBaseType.getBaseEditSaveType());
		
		
		
		setMenuVelocityContext(velocityContext, genTable);
		if (GenConstants.TPL_TREE.equals(tplCategory)) {
			setTreeVelocityContext(velocityContext, genTable);
		}
		if (GenConstants.TPL_SUB.equals(tplCategory)) {
			GenTableIncludeColumn subTable = genTable.getSubTable();
			List<GenTableColumn> subTableColumns = subTable.getColumns();
			//基类父类baseEntityType
//			String subTableBaseEntityType = GenUtils.getClassBaseType(subTableColumns);
			 BaseTypeModel subTableBaseType = GenUtils.getTableBaseType(subTableColumns);
			velocityContext.put("subTableBaseEntityType", subTableBaseType.getBaseEntityType());
			velocityContext.put("subTableBaseDTOType", subTableBaseType.getBaseDTOType());
			velocityContext.put("subTableBaseEditSaveType", subTableBaseType.getBaseEditSaveType());
			setSubVelocityContext(velocityContext, genTable);
			
			
			/** 本表关联子表的外键名 */
			String subTableFkName = genTable.getSubTableFkName();
			
			/** 本表关联子表使用字段 */
			String subTableUseColumnName = genTable.getSubTableUseColumnName();
			
			/** 关联子表的外键-Java */
			String subTableFkJavaType =  null;
			/** 关联子表的外键-Java包名 */
			String subTableFkJavaTypeImportPackage = null;
			
			for (GenTableColumn genTableColumn : subTableColumns) {
				String columnName = genTableColumn.getColumnName();
				if (StringUtils.equalsIgnoreCase(subTableFkName, columnName)) {
					subTableFkJavaType = genTableColumn.getJavaType();
				}
				if (GenConstants.TYPE_DATE.equals(subTableFkJavaType)) {
					subTableFkJavaTypeImportPackage ="java.util.Date";
				} else if (GenConstants.TYPE_BIGDECIMAL.equals(subTableFkJavaType)) {
					subTableFkJavaTypeImportPackage ="java.math.BigDecimal";
				}				
			}
			
			
			/** 关联子表的外键-属性名 */
			String subTableFkAttrName = StringUtils.capitalize(StringUtils.toCamelCase(subTableUseColumnName));
			
			/** 关联子表的外键-属性名 */
			String subTableFkSubAttrName = StringUtils.capitalize(StringUtils.toCamelCase(subTableFkName));
			
			velocityContext.put("subTableFkJavaType", subTableFkJavaType);
			velocityContext.put("subTableFkJavaTypeImportPackage", subTableFkJavaTypeImportPackage);
			velocityContext.put("subTableFkAttrName", subTableFkAttrName);
			velocityContext.put("subTableFkSubAttrName", subTableFkSubAttrName);
			
			
		}
		return velocityContext;
	}

	public static void setMenuVelocityContext(VelocityContext context, GenTable genTable) {
		String options = genTable.getOptions();
		JSONObject paramsObj = JSON.parseObject(options);
		String parentMenuId = getParentMenuId(paramsObj);
		context.put("parentMenuId", parentMenuId);
		context.put("defaultParentMenuName", GenConfigHelper.getDefaultParentMenuName());
	}

	public static void setTreeVelocityContext(VelocityContext context, GenTableIncludeColumn genTable) {
		String options = genTable.getOptions();
		JSONObject paramsObj = JSON.parseObject(options);
		String treeCode = getTreecode(paramsObj);
		String treeParentCode = getTreeParentCode(paramsObj);
		String treeName = getTreeName(paramsObj);

		context.put("treeCode", treeCode);
		context.put("treeParentCode", treeParentCode);
		context.put("treeName", treeName);
		context.put("expandColumn", getExpandColumn(genTable));
		if (paramsObj.containsKey(GenConstants.TREE_PARENT_CODE)) {
			context.put("tree_parent_code", paramsObj.getString(GenConstants.TREE_PARENT_CODE));
		}
		if (paramsObj.containsKey(GenConstants.TREE_NAME)) {
			context.put("tree_name", paramsObj.getString(GenConstants.TREE_NAME));
		}
	}

	public static void setSubVelocityContext(VelocityContext context, GenTableIncludeColumn genTable) {
		GenTable subTable = genTable.getSubTable();
		String subTableName = genTable.getSubTableName();
		String subTableFkName = genTable.getSubTableFkName();
		String subTableUseColumnName = genTable.getSubTableUseColumnName();
		String subClassName = genTable.getSubTable().getClassName();
		String subTableFkClassName = StringUtils.convertToCamelCase(subTableFkName);

		context.put("subTable", subTable);
		context.put("subTableName", subTableName);
		context.put("subTableFkName", subTableFkName);
		context.put("subTableUseColumnName", subTableUseColumnName);
		context.put("subTableFkClassName", subTableFkClassName);
		context.put("subTableFkclassName", StringUtils.uncapitalize(subTableFkClassName));
		context.put("subClassName", subClassName);
		context.put("subclassName", StringUtils.uncapitalize(subClassName));
		context.put("subImportList", getImportList(genTable.getSubTable()));
	}

	/**
	 * 获取模板信息
	 *
	 * @return 模板列表
	 */
	public static List<String> getTemplateList(GenTable table) {
		
		String tplCategory = table.getTplCategory();
		
		List<String> templates = new ArrayList<String>();
		//JavaBean通用： Entity、DTO、EditSave编辑保存
		templates.add("vm/java/bean-entity.java.vm");
		templates.add("vm/java/bean-dto.java.vm");
		templates.add("vm/java/bean-dto_edit_save.java.vm");
		
		//除了树形结构（树形结构父树和子树同是一张表），一般需要分页查询
		if (GenConstants.TPL_TREE.equals(tplCategory)) {
			//树形结构（树形结构父树和子树同是一张表）  需要传输对象
			templates.add("vm/java/bean-dto-tree.java.vm");
		}else {
			//非树形结构
			templates.add("vm/java/bean-dto_list_req.java.vm");
		}
		
		//主子表（主表和子表示两张表）需要：主子领域Domain和主子领域传输对象DTO
		if (GenConstants.TPL_SUB.equals(tplCategory)) {
			//主子领域Domain
			templates.add("vm/java/bean-domain_inc_sub_list.java.vm");
			//主子领域传输对象DTO
			templates.add("vm/java/bean-dto_inc_sub_list.java.vm");
			
		}
		
		
		//XML,默认都需要
		templates.add("vm/xml/mapper.xml.vm");
		//Mapper,默认都需要
		templates.add("vm/java/mapper.java.vm");
		
		//业务层接口和实现类,默认都需要
		templates.add("vm/java/service.java.vm");
		templates.add("vm/java/serviceImpl.java.vm");
		
		//Controller类,默认都需要
		templates.add("vm/java/controller.java.vm");
		
		//管理系统的菜单sql类,默认都需要
		templates.add("vm/sql/sql.vm");
		
		//管理系统的JS接口罗列,默认都需要
		templates.add("vm/js/api.js.vm");
		
		//VUE版本
		String vueVersion = table.getVueVersion();
		if (StringUtils.equalsAnyIgnoreCase(vueVersion, GenConstants.VUE_2)) {
			//管理系统的页面
			if (StringUtils.equalsAnyIgnoreCase(tplCategory, GenConstants.TPL_CRUD,GenConstants.TPL_SUB) ) {
				//普通页面和主子页面
				templates.add("vm/vue2/index.vue.vm");
			} else if (GenConstants.TPL_TREE.equals(tplCategory)) {
				//树形页面
				templates.add("vm/vue2/index-tree.vue.vm");
			}
		}else if (StringUtils.equalsAnyIgnoreCase(vueVersion, GenConstants.VUE_3)) {
			//管理系统的页面
			if (StringUtils.equalsAnyIgnoreCase(tplCategory, GenConstants.TPL_CRUD,GenConstants.TPL_SUB) ) {
				//普通页面和主子页面
				templates.add("vm/vue3/index.vue.vm");
			} else if (GenConstants.TPL_TREE.equals(tplCategory)) {
				//树形页面
				templates.add("vm/vue3/index-tree.vue.vm");
			}
		}else {
			throw new CheckException("请在生成信息选择VUE版本");
		}
	
		return templates;
	}

	/**
	 * 获取文件名
	 */
	public static String getFileName(String template, GenTableIncludeColumn genTable) {
		// 文件名称
		String fileName = "";
		// 包路径
		String packageName = genTable.getPackageName();
		// 模块名
		String moduleName = genTable.getModuleName();
		// 大写类名
		String className = genTable.getClassName();
		// 业务名称
		String businessName = genTable.getBusinessName();

		String javaPath = PROJECT_PATH + "/" + StringUtils.replace(packageName, ".", "/");
		String mybatisPath = MYBATIS_PATH + "/" + moduleName;
		String vuePath = "vue";

		//JavaBean通用： Entity、DTO、EditSave编辑保存
		if (template.contains("vm/java/bean-entity.java.vm")) {
			return StringUtils.format("{}/bean/entity/{}.java", javaPath, className);
		}
		
		if (template.contains("vm/java/bean-dto.java.vm")) {
			return StringUtils.format("{}/bean/dto/{}DTO.java", javaPath, className);
		}
		
		if (template.contains("vm/java/bean-dto_edit_save.java.vm")) {
			return StringUtils.format("{}/bean/dto/{}EditSave.java", javaPath, className);
		}
		
		
		
		
		///树形结构（树形结构父树和子树同是一张表）  需要传输对象
		if (template.contains("vm/java/bean-dto-tree.java.vm") && StringUtils.equals(GenConstants.TPL_TREE, genTable.getTplCategory())) {
			return StringUtils.format("{}/bean/dto/{}Tree.java", javaPath, className);
		}
		
		//非树形结构，一般需要分页查询
		if (template.contains("vm/java/bean-dto_list_req.java.vm") && !StringUtils.equals(GenConstants.TPL_TREE, genTable.getTplCategory())) {
			return StringUtils.format("{}/bean/dto/{}ListReq.java", javaPath, className);
		}
		
		//主子表（主表和子表示两张表）需要：主子领域Domain和主子领域传输对象DTO
		if (template.contains("vm/java/bean-domain_inc_sub_list.java.vm") && StringUtils.equals(GenConstants.TPL_SUB, genTable.getTplCategory())) {
			//主子领域Domain
			return StringUtils.format("{}/bean/domain/{}Inc{}Domain.java", javaPath, className,genTable.getSubTable().getClassName());
		}else if (template.contains("vm/java/bean-dto_inc_sub_list.java.vm") && StringUtils.equals(GenConstants.TPL_SUB, genTable.getTplCategory())) {
			//主子领域传输对象DTO
			return StringUtils.format("{}/bean/dto/{}Inc{}List.java", javaPath, className,genTable.getSubTable().getClassName());
		}
		
		
		if (template.contains("vm/xml/mapper.xml.vm")) {
			return StringUtils.format("{}/{}Mapper.xml", mybatisPath, className);
		}else if (template.contains("mapper.java.vm")) {
			return StringUtils.format("{}/mapper/{}Mapper.java", javaPath, className);
		}else if (template.contains("service.java.vm")) {
			return StringUtils.format("{}/service/I{}Service.java", javaPath, className);
		} else if (template.contains("serviceImpl.java.vm")) {
			return StringUtils.format("{}/service/impl/{}ServiceImpl.java", javaPath, className);
		}else if (template.contains("controller.java.vm")) {
			return StringUtils.format("{}/controller/{}Controller.java", javaPath, className);
		}else if (template.contains("sql.vm")) {
			//管理系统的菜单sql类,默认都需要
			return businessName + "Menu.sql";
		}else if (template.contains("api.js.vm")) {
			//管理系统的JS接口罗列,默认都需要
			return StringUtils.format("{}/api/unit/{}/{}.js", vuePath, moduleName, businessName);
		}
		
		
		//VUE版本
		String vueVersion = genTable.getVueVersion();
		if (StringUtils.equalsAnyIgnoreCase(vueVersion, GenConstants.VUE_2)) {
			//管理系统的页面
			if (template.contains("vm/vue2/index.vue.vm")  && StringUtils.equalsAnyIgnoreCase(genTable.getTplCategory(), GenConstants.TPL_CRUD,GenConstants.TPL_SUB) ) {
				//普通页面和主子页面
				fileName = StringUtils.format("{}/views/unit/{}/{}/index.vue", vuePath, moduleName, businessName);
			} else if (template.contains("vm/vue2/index-tree.vue.vm")  && GenConstants.TPL_TREE.equals(genTable.getTplCategory()) ) {
				//树形页面
				fileName = StringUtils.format("{}/views/unit/{}/{}/index.vue", vuePath, moduleName, businessName);
			}
		}else if (StringUtils.equalsAnyIgnoreCase(vueVersion, GenConstants.VUE_3)) {
			//管理系统的页面
			if (template.contains("vm/vue3/index.vue.vm")  && StringUtils.equalsAnyIgnoreCase(genTable.getTplCategory(), GenConstants.TPL_CRUD,GenConstants.TPL_SUB) ) {
				//普通页面和主子页面
				fileName = StringUtils.format("{}/views/unit/{}/{}/index.vue", vuePath, moduleName, businessName);
			} else if (template.contains("vm/vue3/index-tree.vue.vm")  && GenConstants.TPL_TREE.equals(genTable.getTplCategory()) ) {
				//树形页面
				fileName = StringUtils.format("{}/views/unit/{}/{}/index.vue", vuePath, moduleName, businessName);
			}
		}else {
			throw new CheckException("请在生成信息选择VUE版本");
		}
		return fileName;
	}

	/**
	 * 获取包前缀
	 *
	 * @param packageName 包名称
	 * @return 包前缀名称
	 */
	public static String getPackagePrefix(String packageName) {
		int lastIndex = packageName.lastIndexOf(".");
		String basePackage = StringUtils.substring(packageName, 0, lastIndex);
		return basePackage;
	}

	/**
	 * 根据列类型获取导入包
	 * 
	 * @param genTable 业务表对象
	 * @return 返回需要导入的包列表
	 */
	public static HashSet<String> getImportList(GenTableIncludeColumn genTable) {
		List<GenTableColumn> columns = genTable.getColumns();
		GenTable subGenTable = genTable.getSubTable();
		HashSet<String> importList = new HashSet<String>();
		if (BlankUtils.isNotNull(subGenTable)) {
			// importList.add("java.util.List"); TODO
		}
		for (GenTableColumn column : columns) {
			if (!column.isSuperColumn() && GenConstants.TYPE_DATE.equals(column.getJavaType())) {
				importList.add("java.util.Date");
				importList.add("com.fasterxml.jackson.annotation.JsonFormat");
			} else if (!column.isSuperColumn() && GenConstants.TYPE_BIGDECIMAL.equals(column.getJavaType())) {
				importList.add("java.math.BigDecimal");
			}
		}
		return importList;
	}

	/**
	 * 根据列类型获取字典组
	 * 
	 * @param genTable 业务表对象
	 * @return 返回字典组
	 */
	public static String getDicts(GenTableIncludeColumn genTable) {
		List<GenTableColumn> columns = genTable.getColumns();
		List<String> dicts = new ArrayList<String>();
		for (GenTableColumn column : columns) {
			if (!column.isSuperColumn() && StringUtils.isNotEmpty(column.getDictType()) && StringUtils.equalsAny(
					column.getHtmlType(),
					new String[] { GenConstants.HTML_SELECT, GenConstants.HTML_RADIO, GenConstants.HTML_CHECKBOX })) {
				dicts.add("'" + column.getDictType() + "'");
			}
		}
		return StringUtils.join(dicts, ", ");
	}

	/**
	 * 获取权限前缀
	 *
	 * @param moduleName   模块名称
	 * @param businessName 业务名称
	 * @return 返回权限前缀
	 */
	public static String getPermissionPrefix(String moduleName, String businessName) {
		return StringUtils.format("{}:{}", moduleName, businessName);
	}

	/**
	 * 获取上级菜单ID字段
	 *
	 * @param paramsObj 生成其他选项
	 * @return 上级菜单ID字段
	 */
	public static String getParentMenuId(JSONObject paramsObj) {
		if (BlankUtils.isNotBlank(paramsObj) && paramsObj.containsKey(GenConstants.PARENT_MENU_ID)
				&& StringUtils.isNotEmpty(paramsObj.getString(GenConstants.PARENT_MENU_ID))) {
			return paramsObj.getString(GenConstants.PARENT_MENU_ID);
		}
		return "";
	}

	/**
	 * 获取树编码
	 *
	 * @param paramsObj 生成其他选项
	 * @return 树编码
	 */
	public static String getTreecode(JSONObject paramsObj) {
		if (paramsObj.containsKey(GenConstants.TREE_CODE)) {
			return StringUtils.toCamelCase(paramsObj.getString(GenConstants.TREE_CODE));
		}
		return StringUtils.EMPTY;
	}

	/**
	 * 获取树父编码
	 *
	 * @param paramsObj 生成其他选项
	 * @return 树父编码
	 */
	public static String getTreeParentCode(JSONObject paramsObj) {
		if (paramsObj.containsKey(GenConstants.TREE_PARENT_CODE)) {
			return StringUtils.toCamelCase(paramsObj.getString(GenConstants.TREE_PARENT_CODE));
		}
		return StringUtils.EMPTY;
	}

	/**
	 * 获取树名称
	 *
	 * @param paramsObj 生成其他选项
	 * @return 树名称
	 */
	public static String getTreeName(JSONObject paramsObj) {
		if (paramsObj.containsKey(GenConstants.TREE_NAME)) {
			return StringUtils.toCamelCase(paramsObj.getString(GenConstants.TREE_NAME));
		}
		return StringUtils.EMPTY;
	}

	/**
	 * 获取需要在哪一列上面显示展开按钮
	 *
	 * @param genTable 业务表对象
	 * @return 展开按钮列序号
	 */
	public static int getExpandColumn(GenTableIncludeColumn genTable) {
		String options = genTable.getOptions();
		JSONObject paramsObj = JSON.parseObject(options);
		String treeName = paramsObj.getString(GenConstants.TREE_NAME);
		int num = 0;
		for (GenTableColumn column : genTable.getColumns()) {
			if (column.isList()) {
				num++;
				String columnName = column.getColumnName();
				if (columnName.equals(treeName)) {
					break;
				}
			}
		}
		return num;
	}
}
