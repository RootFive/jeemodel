# 第一部分：jeemodel_gen数据库创建【代码生产模块】业务表
-- 启用 jeemodel_gen 数据库
USE jeemodel_gen;

## 更新数据前 禁用外键约束
SET FOREIGN_KEY_CHECKS = 0;
-- ----------------------------
-- Table structure for gen_table
-- ----------------------------
DROP TABLE IF EXISTS `gen_table`;
CREATE TABLE `gen_table`  (
  `id` 							bigint(0) 			NOT NULL AUTO_INCREMENT 				COMMENT '编号',
  `table_db` 					varchar(200)  		NOT NULL DEFAULT '' 					COMMENT '数据库-库名称',
  `table_name` 					varchar(200)  		NOT NULL 								COMMENT '数据库-表名称',
  `table_comment` 				varchar(500)  		NOT NULL DEFAULT '' 					COMMENT '表描述',
  `sub_table_name` 				varchar(64)  		NOT NULL DEFAULT '' 					COMMENT '关联子表的表名',
  `sub_table_fk_name` 			varchar(64)  		NOT NULL DEFAULT '' 					COMMENT '子表关联的外键名',
  `sub_table_use_column_name` 	varchar(64)  		NOT NULL DEFAULT '' 					COMMENT '子表关联使用字段',
  `class_name` 					varchar(100)  		NOT NULL 								COMMENT '实体类名称',
  `vue_version` 				varchar(4)  		NOT NULL DEFAULT 'V2'					COMMENT 'VUE版本（V2和Vue3）',
  `tpl_category` 				varchar(200)  		NOT NULL DEFAULT 'crud' 				COMMENT '使用的模板（crud单表操作 tree树表操作）',
  `package_name` 				varchar(100)  		NOT NULL DEFAULT '' 					COMMENT '生成包路径',
  `module_name` 				varchar(30)  		NOT NULL DEFAULT '' 					COMMENT '生成模块名',
  `business_name` 				varchar(30)  		NOT NULL DEFAULT '' 					COMMENT '生成业务名',
  `function_name` 				varchar(50)  		NOT NULL DEFAULT '' 					COMMENT '生成功能名',
  `function_author` 			varchar(50)  		NOT NULL DEFAULT '' 					COMMENT '生成功能作者',
  `gen_type` 					char(1)  			NOT NULL DEFAULT '0' 					COMMENT '生成代码方式（0zip压缩包 1自定义路径）',
  `gen_path` 					varchar(200)  		NOT NULL DEFAULT '/' 					COMMENT '生成路径（不填默认项目路径）',
  `options` 					varchar(1000)  		NOT NULL DEFAULT '' 					COMMENT '其它生成选项',
  
  `remark` 			varchar(500)  		NOT NULL DEFAULT '' 						COMMENT '备注',
  `create_by` 		varchar(64)  		NOT NULL DEFAULT '' 						COMMENT '创建者',
  `create_time` 	datetime 			NOT NULL DEFAULT CURRENT_TIMESTAMP 			COMMENT '创建时间',
  `status` 			char(1)  			NOT NULL DEFAULT '0' 						COMMENT '状态（0正常 1停用）',
  `update_by` 		varchar(64)  		NOT NULL DEFAULT '' 						COMMENT '更新者',
  `update_time` 	datetime 			DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP 	COMMENT '更新时间',
  PRIMARY KEY (`id`),
  UNIQUE KEY `uk_db_table` (`table_db`,`table_name`) COMMENT '数据库与表唯一'
) ENGINE = InnoDB   COMMENT = '代码生成业务表' ;

-- ----------------------------
-- Records of gen_table
-- ----------------------------

-- ----------------------------
-- Table structure for gen_table_column
-- ----------------------------
DROP TABLE IF EXISTS `gen_table_column`;
CREATE TABLE `gen_table_column`  (
  `id` 					bigint(0) 			NOT NULL AUTO_INCREMENT 		COMMENT '编号',
  `table_id` 			varchar(64)  		NOT NULL		 				COMMENT '归属表编号',
  `column_name` 		varchar(200)  		NOT NULL 						COMMENT '列名称',
  `column_comment` 		varchar(500)  		NOT NULL DEFAULT '' 			COMMENT '列描述',
  `column_type` 		varchar(100)  		NOT NULL  						COMMENT '列类型',
  `java_type` 			varchar(500)  		NOT NULL 						COMMENT 'JAVA类型',
  `java_field` 			varchar(200)  		NOT NULL 						COMMENT 'JAVA字段名',
  `is_pk` 				char(1)  			NOT NULL 						COMMENT '是否主键（1是）',
  `is_increment` 		char(1)  			NOT NULL 						COMMENT '是否自增（1是）',
  `is_required` 		char(1)  			NOT NULL DEFAULT '0' 			COMMENT '是否必填（1是）',
  `is_insert` 			char(1)  			NOT NULL DEFAULT '1' 			COMMENT '是否为插入字段（1是）',
  `is_edit` 			char(1)  			NOT NULL DEFAULT '0' 			COMMENT '是否编辑字段（1是）',
  `is_list` 			char(1)  			NOT NULL DEFAULT '0' 			COMMENT '是否列表字段（1是）',
  `is_query` 			char(1)  			NOT NULL DEFAULT '0' 			COMMENT '是否查询字段（1是）',
  `query_type` 			varchar(200)  		NOT NULL DEFAULT 'EQ' 			COMMENT '查询方式（等于、不等于、大于、小于、范围）',
  `html_type` 			varchar(200)  		NOT NULL 		 				COMMENT '显示类型（文本框、文本域、下拉框、复选框、单选框、日期控件）',
  `dict_type` 			varchar(200)  		NOT NULL DEFAULT '' 			COMMENT '字典类型',
  `sort` 				int(0) 				NOT NULL DEFAULT 0 				COMMENT '排序',
  
  `remark` 			varchar(500)  		NOT NULL DEFAULT '' 						COMMENT '备注',
  `create_by` 		varchar(64)  		NOT NULL DEFAULT '' 						COMMENT '创建者',
  `create_time` 	datetime 			NOT NULL DEFAULT CURRENT_TIMESTAMP 			COMMENT '创建时间',
  `status` 			char(1)  			NOT NULL DEFAULT '0' 						COMMENT '状态（0正常 1停用）',
  `update_by` 		varchar(64)  		NOT NULL DEFAULT '' 						COMMENT '更新者',
  `update_time` 	datetime 			DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP 	COMMENT '更新时间',
  PRIMARY KEY (`id`),
  UNIQUE KEY `uk_table_column` (`table_id`,`column_name`) COMMENT '表字段唯一'
) ENGINE = InnoDB  COMMENT = '代码生成业务表字段' ;

-- ----------------------------
-- Records of gen_table_column
-- ----------------------------

## 更新数据后可以在开启外键约束
SET FOREIGN_KEY_CHECKS = 1;


# 第二部分：jeemodel_manage 插入菜单权限数据
-- 启用 jeemodel_manage 数据库
USE jeemodel_manage;

## 更新数据前 禁用外键约束
SET FOREIGN_KEY_CHECKS = 0;

-- 一级菜单-研发工具 
-- 获取菜单 排序
set @lastOrderNum = '';
SELECT @lastOrderNum:=order_num FROM `manage_menu`  WHERE  parent_id =0  ORDER BY order_num desc LIMIT 1;

-- 一级菜单-研发工具 
INSERT INTO `manage_menu` ( `menu_name`, `parent_id`, `order_num`, `path`, `query`, `is_frame`, `is_cache`, `menu_type`, `visible`, `status`, `perms`, `icon`, `create_by`,`remark`)
				   VALUES ('研发工具', 0, @lastOrderNum +1, 'coding', '', 1, 0, 'M', '0', '0', '', 'tool', 'system', '系统工具目录');
SELECT @codeToolId := LAST_INSERT_ID();				   
INSERT INTO `manage_menu` ( `menu_name`, `parent_id`, `order_num`, `path`, `component`, `query`, `is_frame`, `is_cache`, `menu_type`, `visible`, `status`, `perms`, `icon`, `create_by`,`remark`) 
				   VALUES ('系统接口', @codeToolId, 1, 'swagger', 'unit/coding/swagger/index', '', 1, 0, 'C', '0', '0', 'coding:swagger:list', 'swagger', 'system', '系统接口菜单');

INSERT INTO `manage_menu` ( `menu_name`, `parent_id`, `order_num`, `path`, `component`, `query`, `is_frame`, `is_cache`, `menu_type`, `visible`, `status`, `perms`, `icon`, `create_by`,`remark`) 
				   VALUES ('代码生成', @codeToolId, 2, 'gen', 'unit/coding/gen/index', '', 1, 0, 'C', '0', '0', 'coding:gen:list', 'code', 'system', '代码生成菜单');
SELECT @genManageId := LAST_INSERT_ID();

INSERT INTO `manage_menu` ( `menu_name`, `parent_id`, `order_num`, `path`, `component`, `query`, `is_frame`, `is_cache`, `menu_type`, `visible`, `status`, `perms`, `icon`, `create_by`,`remark`) 
				   VALUES ('表单构建', @codeToolId, 3, 'build', 'unit/coding/build/index', '', 1, 0, 'C', '0', '0', 'coding:build:list', 'build', 'system', '表单构建菜单');
				   
INSERT INTO `manage_menu` ( `menu_name`, `parent_id`, `order_num`, `path`, `component`, `query`, `is_frame`, `is_cache`, `menu_type`, `visible`, `perms`, `icon`, `remark`, `create_by`) 
				   VALUES ('ECharts示例', @codeToolId, 4, 'https://echarts.apache.org/examples/zh/index.html', '', '', 1, 0, 'M', '0', '', 'chart', 'ECharts示例官方链接', 'admin');

				   
-- 按钮 代码生成
INSERT INTO `manage_menu` ( `menu_name`, `parent_id`, `order_num`, `path`, `component`, `query`, `is_frame`, `is_cache`, `menu_type`, `visible`, `status`, `perms`, `icon`, `create_by`,`remark`) 
				   VALUES ('生成查询', @genManageId, 1, '#', '', '', 1, 0, 'F', '0', '0', 'coding:gen:query', '#', 'system', '');

INSERT INTO `manage_menu` ( `menu_name`, `parent_id`, `order_num`, `path`, `component`, `query`, `is_frame`, `is_cache`, `menu_type`, `visible`, `status`, `perms`, `icon`, `create_by`,`remark`) 
				   VALUES ('生成修改', @genManageId, 2, '#', '', '', 1, 0, 'F', '0', '0', 'coding:gen:edit', '#', 'system', '');

INSERT INTO `manage_menu` ( `menu_name`, `parent_id`, `order_num`, `path`, `component`, `query`, `is_frame`, `is_cache`, `menu_type`, `visible`, `status`, `perms`, `icon`, `create_by`,`remark`) 
				   VALUES ('生成删除', @genManageId, 3, '#', '', '', 1, 0, 'F', '0', '0', 'coding:gen:remove', '#', 'system', '');

INSERT INTO `manage_menu` ( `menu_name`, `parent_id`, `order_num`, `path`, `component`, `query`, `is_frame`, `is_cache`, `menu_type`, `visible`, `status`, `perms`, `icon`, `create_by`,`remark`) 
				   VALUES ('导入代码', @genManageId, 2, '#', '', '', 1, 0, 'F', '0', '0', 'coding:gen:import', '#', 'system', '');

INSERT INTO `manage_menu` ( `menu_name`, `parent_id`, `order_num`, `path`, `component`, `query`, `is_frame`, `is_cache`, `menu_type`, `visible`, `status`, `perms`, `icon`, `create_by`,`remark`) 
				   VALUES ('预览代码', @genManageId, 4, '#', '', '', 1, 0, 'F', '0', '0', 'coding:gen:preview', '#', 'system', '');

INSERT INTO `manage_menu` ( `menu_name`, `parent_id`, `order_num`, `path`, `component`, `query`, `is_frame`, `is_cache`, `menu_type`, `visible`, `status`, `perms`, `icon`, `create_by`,`remark`) 
				   VALUES ('生成代码', @genManageId, 5, '#', '', '', 1, 0, 'F', '0', '0', 'coding:gen:code', '#', 'system', '');


-- 插入代码生成默认选择				   
INSERT INTO `manage_dict` (`dict_name`, `dict_type`, `create_by`, `remark`) VALUES ('生成代码选库', 'coding_gen_table_db_optional', 'system', '代码生成数据库选择列表');
INSERT INTO `manage_dict_data` (`dict_sort`, `dict_label`, `dict_value`, `dict_type`, `css_class`, `list_class`, `is_default`, `create_by`,`remark`) VALUES (1, '代码生成', 'jeemodel_gen', 'coding_gen_table_db_optional', '', 'primary', 'Y', 'system', '正常状态');
				   

## 更新数据后可以在开启外键约束
SET FOREIGN_KEY_CHECKS = 1;
