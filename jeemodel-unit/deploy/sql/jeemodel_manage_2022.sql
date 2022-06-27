# 启用 jeemodel_manage 数据库
USE jeemodel_manage;

## 更新数据前 禁用外键约束
SET FOREIGN_KEY_CHECKS = 0;
-- ----------------------------
-- Table structure for manage_config
-- ----------------------------
DROP TABLE IF EXISTS `manage_config`;
CREATE TABLE `manage_config`  (
  `id` 				bigint(0) 		NOT NULL AUTO_INCREMENT 		COMMENT '主键ID',
  `config_name` 	varchar(100)  	NOT NULL 						COMMENT '参数名称',
  `config_key` 		varchar(200)  	NOT NULL			 			COMMENT '参数键名',
  `config_value` 	varchar(400)  	NOT NULL			 			COMMENT '参数键值',
  `config_type` 	char(1)  		NOT NULL DEFAULT 'N' 			COMMENT '系统内置（Y是 N否）',
  
  `remark` 			varchar(500)  		NOT NULL DEFAULT '' 						COMMENT '备注',
  `create_by` 		varchar(64)  		NOT NULL DEFAULT '' 						COMMENT '创建者',
  `create_time` 	datetime 			NOT NULL DEFAULT CURRENT_TIMESTAMP 			COMMENT '创建时间',
  `status` 			char(1)  			NOT NULL DEFAULT '0' 						COMMENT '状态（0正常 1停用）',
  `update_by` 		varchar(64)  		NOT NULL DEFAULT '' 						COMMENT '更新者',
  `update_time` 	datetime 			DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP 	COMMENT '更新时间',
  PRIMARY KEY (`id`),
  UNIQUE KEY `uk_key` (`config_key`) COMMENT '参数键名key唯一'
) ENGINE = InnoDB  COMMENT = '参数配置表' ;

-- ----------------------------
-- Records of manage_config
-- ----------------------------
INSERT INTO `manage_config` (`config_name`, `config_key`, `config_value`, `config_type`,`create_by`, `remark`) VALUES ('主框架页-默认皮肤样式名称', 'sys.index.skinName', 'skin-blue', 'Y', 'system', '蓝色 skin-blue、绿色 skin-green、紫色 skin-purple、红色 skin-red、黄色 skin-yellow');
INSERT INTO `manage_config` (`config_name`, `config_key`, `config_value`, `config_type`,`create_by`, `remark`) VALUES ('用户管理-账号初始密码', 'sys.user.initPassword', '123456', 'Y', 'system', '初始化密码 123456');
INSERT INTO `manage_config` (`config_name`, `config_key`, `config_value`, `config_type`,`create_by`, `remark`) VALUES ('主框架页-侧边栏主题', 'sys.index.sideTheme', 'theme-dark', 'Y', 'system', '深色主题theme-dark，浅色主题theme-light');
INSERT INTO `manage_config` (`config_name`, `config_key`, `config_value`, `config_type`,`create_by`, `remark`) VALUES ('账号自助-验证码开关', 'sys.account.captchaOnOff', 'true', 'Y', 'system', '是否开启验证码功能（true开启，false关闭）');
INSERT INTO `manage_config` (`config_name`, `config_key`, `config_value`, `config_type`,`create_by`, `remark`) VALUES ('账号自助-是否开启用户注册功能', 'sys.account.registerUser', 'false', 'Y', 'system', '是否开启注册用户功能（true开启，false关闭）');




-- ----------------------------
-- Table structure for manage_dict
-- ----------------------------
DROP TABLE IF EXISTS `manage_dict`;
CREATE TABLE `manage_dict`  (
  `id` 						bigint(0) 		NOT NULL AUTO_INCREMENT 				COMMENT '主键ID',
  `dict_name` 				varchar(100)  	NOT NULL								COMMENT '字典名称',
  `dict_type` 				varchar(100)  	NOT NULL		 						COMMENT '字典类型',
  `dict_data_java_type` 	varchar(100)  	NOT NULL DEFAULT 'java.lang.String'		COMMENT '字典数据Java类型',
  
  `remark` 			varchar(500)  		NOT NULL DEFAULT '' 						COMMENT '备注',
  `create_by` 		varchar(64)  		NOT NULL DEFAULT '' 						COMMENT '创建者',
  `create_time` 	datetime 			NOT NULL DEFAULT CURRENT_TIMESTAMP 			COMMENT '创建时间',
  `status` 			char(1)  			NOT NULL DEFAULT '0' 						COMMENT '状态（0正常 1停用）',
  `update_by` 		varchar(64)  		NOT NULL DEFAULT '' 						COMMENT '更新者',
  `update_time` 	datetime 			DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP 	COMMENT '更新时间',
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE INDEX `uk_type`(`dict_type`) COMMENT '字典类型一'
) ENGINE = InnoDB  COMMENT = '字典类型表' ;

-- ----------------------------
-- Records of manage_dict
-- ----------------------------
INSERT INTO `manage_dict` (`dict_name`, `dict_type`, `create_by`, `remark`) VALUES ('用户性别', 'manage_user_sex', 'system', '用户性别列表');
INSERT INTO `manage_dict` (`dict_name`, `dict_type`, `create_by`, `remark`) VALUES ('菜单状态', 'manage_menu_show_hide', 'system', '菜单状态列表');
INSERT INTO `manage_dict` (`dict_name`, `dict_type`, `create_by`, `remark`) VALUES ('系统开关', 'manage_normal_disable', 'system', '系统开关列表');
INSERT INTO `manage_dict` (`dict_name`, `dict_type`, `create_by`, `remark`) VALUES ('系统是否', 'manage_yes_no', 'system', '系统是否列表');
INSERT INTO `manage_dict` (`dict_name`, `dict_type`, `create_by`, `remark`) VALUES ('通知类型', 'manage_notice_type', 'system', '通知类型列表');
INSERT INTO `manage_dict` (`dict_name`, `dict_type`, `create_by`, `remark`) VALUES ('通知状态', 'manage_notice_status', 'system', '通知状态列表');
INSERT INTO `manage_dict` (`dict_name`, `dict_type`, `create_by`, `remark`) VALUES ('操作类型', 'manage_user_log_oper_type', 'system', '操作类型列表');
INSERT INTO `manage_dict` (`dict_name`, `dict_type`, `create_by`, `remark`) VALUES ('系统状态', 'manage_common_status', 'system', '登录状态列表');
INSERT INTO `manage_dict` (`dict_name`, `dict_type`, `create_by`, `remark`) VALUES ('Java类型', 'manage_common_java_type', 'system', 'Java常用数据类型列表');
-- ----------------------------
-- Table structure for manage_dict_data
-- ----------------------------
DROP TABLE IF EXISTS `manage_dict_data`;
CREATE TABLE `manage_dict_data`  (
  `id` 				bigint(0) 		NOT NULL AUTO_INCREMENT 		COMMENT '主键ID',
  `dict_type` 		varchar(100)  	NOT NULL		 				COMMENT '字典类型',
  `dict_sort` 		int(0) 			NOT NULL DEFAULT 0 				COMMENT '字典排序',
  `dict_label` 		varchar(100)  	NOT NULL 						COMMENT '字典标签',
  `dict_value` 		varchar(100)  	NOT NULL 						COMMENT '字典标签-键值',
  `css_class` 		varchar(100)  	NOT NULL DEFAULT '' 			COMMENT '样式属性（其他样式扩展）',
  `list_class` 		varchar(100)  	NOT NULL DEFAULT '' 			COMMENT '表格回显样式',
  `is_default` 		char(1)  		NOT NULL DEFAULT 'N' 			COMMENT '是否默认（Y是 N否）',
  
  `remark` 			varchar(500)  		NOT NULL DEFAULT '' 						COMMENT '备注',
  `create_by` 		varchar(64)  		NOT NULL DEFAULT '' 						COMMENT '创建者',
  `create_time` 	datetime 			NOT NULL DEFAULT CURRENT_TIMESTAMP 			COMMENT '创建时间',
  `status` 			char(1)  			NOT NULL DEFAULT '0' 						COMMENT '状态（0正常 1停用）',
  `update_by` 		varchar(64)  		NOT NULL DEFAULT '' 						COMMENT '更新者',
  `update_time` 	datetime 			DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP 	COMMENT '更新时间',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB   COMMENT = '字典数据表' ;

-- ----------------------------
-- Records of manage_dict_data
-- ----------------------------
INSERT INTO `manage_dict_data` (`dict_sort`, `dict_label`, `dict_value`, `dict_type`, `css_class`, `list_class`, `is_default`, `create_by`,`remark`) VALUES (1, '男', '0', 'manage_user_sex', '', '', 'Y', 'system', '性别男');
INSERT INTO `manage_dict_data` (`dict_sort`, `dict_label`, `dict_value`, `dict_type`, `css_class`, `list_class`, `is_default`, `create_by`,`remark`) VALUES (2, '女', '1', 'manage_user_sex', '', '', 'N', 'system', '性别女');
INSERT INTO `manage_dict_data` (`dict_sort`, `dict_label`, `dict_value`, `dict_type`, `css_class`, `list_class`, `is_default`, `create_by`,`remark`) VALUES (3, '未知', '2', 'manage_user_sex', '', '', 'N', 'system', '性别未知');
INSERT INTO `manage_dict_data` (`dict_sort`, `dict_label`, `dict_value`, `dict_type`, `css_class`, `list_class`, `is_default`, `create_by`,`remark`) VALUES (1, '显示', '0', 'manage_menu_show_hide', '', 'primary', 'Y', 'system', '显示菜单');
INSERT INTO `manage_dict_data` (`dict_sort`, `dict_label`, `dict_value`, `dict_type`, `css_class`, `list_class`, `is_default`, `create_by`,`remark`) VALUES (2, '隐藏', '1', 'manage_menu_show_hide', '', 'danger', 'N', 'system', '隐藏菜单');
INSERT INTO `manage_dict_data` (`dict_sort`, `dict_label`, `dict_value`, `dict_type`, `css_class`, `list_class`, `is_default`, `create_by`,`remark`) VALUES (1, '正常', '0', 'manage_normal_disable', '', 'primary', 'Y', 'system', '正常状态');
INSERT INTO `manage_dict_data` (`dict_sort`, `dict_label`, `dict_value`, `dict_type`, `css_class`, `list_class`, `is_default`, `create_by`,`remark`) VALUES (2, '停用', '1', 'manage_normal_disable', '', 'danger', 'N', 'system', '停用状态');
INSERT INTO `manage_dict_data` (`dict_sort`, `dict_label`, `dict_value`, `dict_type`, `css_class`, `list_class`, `is_default`, `create_by`,`remark`) VALUES (1, '是', 'Y', 'manage_yes_no', '', 'primary', 'Y', 'system', '系统默认是');
INSERT INTO `manage_dict_data` (`dict_sort`, `dict_label`, `dict_value`, `dict_type`, `css_class`, `list_class`, `is_default`, `create_by`,`remark`) VALUES (2, '否', 'N', 'manage_yes_no', '', 'danger', 'N', 'system', '系统默认否');
INSERT INTO `manage_dict_data` (`dict_sort`, `dict_label`, `dict_value`, `dict_type`, `css_class`, `list_class`, `is_default`, `create_by`,`remark`) VALUES (1, '通知', '1', 'manage_notice_type', '', 'warning', 'Y', 'system', '通知');
INSERT INTO `manage_dict_data` (`dict_sort`, `dict_label`, `dict_value`, `dict_type`, `css_class`, `list_class`, `is_default`, `create_by`,`remark`) VALUES (2, '公告', '2', 'manage_notice_type', '', 'success', 'N', 'system', '公告');
INSERT INTO `manage_dict_data` (`dict_sort`, `dict_label`, `dict_value`, `dict_type`, `css_class`, `list_class`, `is_default`, `create_by`,`remark`) VALUES (1, '正常', '0', 'manage_notice_status', '', 'primary', 'Y', 'system', '正常状态');
INSERT INTO `manage_dict_data` (`dict_sort`, `dict_label`, `dict_value`, `dict_type`, `css_class`, `list_class`, `is_default`, `create_by`,`remark`) VALUES (2, '关闭', '1', 'manage_notice_status', '', 'danger', 'N', 'system', '关闭状态');
INSERT INTO `manage_dict_data` (`dict_sort`, `dict_label`, `dict_value`, `dict_type`, `css_class`, `list_class`, `is_default`, `create_by`,`remark`) VALUES (1, '新增', '1', 'manage_user_log_oper_type', '', 'info', 'N', 'system', '新增操作');
INSERT INTO `manage_dict_data` (`dict_sort`, `dict_label`, `dict_value`, `dict_type`, `css_class`, `list_class`, `is_default`, `create_by`,`remark`) VALUES (2, '修改', '2', 'manage_user_log_oper_type', '', 'info', 'N', 'system', '修改操作');
INSERT INTO `manage_dict_data` (`dict_sort`, `dict_label`, `dict_value`, `dict_type`, `css_class`, `list_class`, `is_default`, `create_by`,`remark`) VALUES (3, '删除', '3', 'manage_user_log_oper_type', '', 'danger', 'N', 'system', '删除操作');
INSERT INTO `manage_dict_data` (`dict_sort`, `dict_label`, `dict_value`, `dict_type`, `css_class`, `list_class`, `is_default`, `create_by`,`remark`) VALUES (4, '授权', '4', 'manage_user_log_oper_type', '', 'primary', 'N', 'system', '授权操作');
INSERT INTO `manage_dict_data` (`dict_sort`, `dict_label`, `dict_value`, `dict_type`, `css_class`, `list_class`, `is_default`, `create_by`,`remark`) VALUES (5, '导出', '5', 'manage_user_log_oper_type', '', 'warning', 'N', 'system', '导出操作');
INSERT INTO `manage_dict_data` (`dict_sort`, `dict_label`, `dict_value`, `dict_type`, `css_class`, `list_class`, `is_default`, `create_by`,`remark`) VALUES (6, '导入', '6', 'manage_user_log_oper_type', '', 'warning', 'N', 'system', '导入操作');
INSERT INTO `manage_dict_data` (`dict_sort`, `dict_label`, `dict_value`, `dict_type`, `css_class`, `list_class`, `is_default`, `create_by`,`remark`) VALUES (7, '强退', '7', 'manage_user_log_oper_type', '', 'danger', 'N', 'system', '强退操作');
INSERT INTO `manage_dict_data` (`dict_sort`, `dict_label`, `dict_value`, `dict_type`, `css_class`, `list_class`, `is_default`, `create_by`,`remark`) VALUES (8, '生成代码', '8', 'manage_user_log_oper_type', '', 'warning', 'N', 'system', '生成操作');
INSERT INTO `manage_dict_data` (`dict_sort`, `dict_label`, `dict_value`, `dict_type`, `css_class`, `list_class`, `is_default`, `create_by`,`remark`) VALUES (9, '清空数据', '9', 'manage_user_log_oper_type', '', 'danger', 'N', 'system', '清空操作');
INSERT INTO `manage_dict_data` (`dict_sort`, `dict_label`, `dict_value`, `dict_type`, `css_class`, `list_class`, `is_default`, `create_by`,`remark`) VALUES (1, '成功', '0', 'manage_common_status', '', 'primary', 'N', 'system', '正常状态');
INSERT INTO `manage_dict_data` (`dict_sort`, `dict_label`, `dict_value`, `dict_type`, `css_class`, `list_class`, `is_default`, `create_by`,`remark`) VALUES (2, '失败', '1', 'manage_common_status', '', 'danger', 'N', 'system', '停用状态');

INSERT INTO `manage_dict_data` (`dict_sort`, `dict_label`, `dict_value`, `dict_type`, `css_class`, `list_class`, `is_default`, `create_by`,`remark`) VALUES (1, 'String（字符串）', 'java.lang.String', 'manage_common_java_type', '', 'info', 'N', 'system', 'String（字符串）');
INSERT INTO `manage_dict_data` (`dict_sort`, `dict_label`, `dict_value`, `dict_type`, `css_class`, `list_class`, `is_default`, `create_by`,`remark`) VALUES (2, 'Integer（整型）', 'java.lang.Integer', 'manage_common_java_type', '', 'info', 'N', 'system', 'Integer（整型）');
INSERT INTO `manage_dict_data` (`dict_sort`, `dict_label`, `dict_value`, `dict_type`, `css_class`, `list_class`, `is_default`, `create_by`,`remark`) VALUES (3, 'Long（长整型）', 'java.lang.Long', 'manage_common_java_type', '', 'danger', 'N', 'system', 'Long（长整型）');
INSERT INTO `manage_dict_data` (`dict_sort`, `dict_label`, `dict_value`, `dict_type`, `css_class`, `list_class`, `is_default`, `create_by`,`remark`) VALUES (4, 'Byte（字节型）', 'java.lang.Byte', 'manage_common_java_type', '', 'primary', 'N', 'system', 'Byte（字节型）');
INSERT INTO `manage_dict_data` (`dict_sort`, `dict_label`, `dict_value`, `dict_type`, `css_class`, `list_class`, `is_default`, `create_by`,`remark`) VALUES (5, 'BigDecimal（商业计算）', 'java.math.BigDecimal', 'manage_common_java_type', '', 'warning', 'N', 'system', 'BigDecimal（商业计算，用来对超过16位有效位的数进行精确的运算）');
INSERT INTO `manage_dict_data` (`dict_sort`, `dict_label`, `dict_value`, `dict_type`, `css_class`, `list_class`, `is_default`, `create_by`,`remark`) VALUES (6, 'Boolean（布尔型）', 'java.lang.Boolean', 'manage_common_java_type', '', 'warning', 'N', 'system', 'Boolean（布尔型）');
INSERT INTO `manage_dict_data` (`dict_sort`, `dict_label`, `dict_value`, `dict_type`, `css_class`, `list_class`, `is_default`, `create_by`,`remark`) VALUES (7, 'Character（字符）', 'java.lang.Character', 'manage_common_java_type', '', 'danger', 'N', 'system', 'Character（字符）');

-- ----------------------------
-- Table structure for manage_dept
-- ----------------------------
DROP TABLE IF EXISTS `manage_dept`;
CREATE TABLE `manage_dept`  (
  `id` 				bigint(0) 		NOT NULL AUTO_INCREMENT 		COMMENT '主键ID',
  `parent_id` 		bigint(0) 		NOT NULL DEFAULT 0 				COMMENT '父部门id',
  `ancestors` 		varchar(50)  	NOT NULL DEFAULT '0' 			COMMENT '祖级列表',
  `dept_name` 		varchar(30)  	NOT NULL  						COMMENT '部门名称',
  `order_num` 		int(0) 			NOT NULL DEFAULT 0 				COMMENT '显示顺序',
  `leader` 			varchar(20)  	NOT NULL DEFAULT '' 			COMMENT '负责人',
  `phone` 			varchar(11)  	NOT NULL DEFAULT '' 			COMMENT '联系电话',
  `email` 			varchar(50)  	NOT NULL DEFAULT '' 			COMMENT '邮箱',
  
  `remark` 			varchar(500)  		NOT NULL DEFAULT '' 						COMMENT '备注',
  `create_by` 		varchar(64)  		NOT NULL DEFAULT '' 						COMMENT '创建者',
  `create_time` 	datetime 			NOT NULL DEFAULT CURRENT_TIMESTAMP 			COMMENT '创建时间',
  `status` 			char(1)  			NOT NULL DEFAULT '0' 						COMMENT '状态（0正常 1停用）',
  `update_by` 		varchar(64)  		NOT NULL DEFAULT '' 						COMMENT '更新者',
  `update_time` 	datetime 			DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP 	COMMENT '更新时间',
  `del_flag` 		char(1)  			NOT NULL DEFAULT '0' 						COMMENT '删除标志（0代表存在 2代表删除）',
  `del_time` 		datetime 			DEFAULT NULL 								COMMENT '删除时间',
  PRIMARY KEY (`id`)
) ENGINE = InnoDB  COMMENT = '部门表' ;

-- ----------------------------
-- Records of manage_dept
-- ----------------------------
-- 插入-上海总公司市场部门
INSERT INTO `manage_dept` (`ancestors`, `dept_name`, `order_num`, `leader`, `phone`, `email`, `create_by`) VALUES ('0', '上海总公司', 1, 'JeeModel', '15888888888', 'ry@qq.com', 'system');
SELECT @headOfficeId := LAST_INSERT_ID();

INSERT INTO `manage_dept` (`parent_id`, `ancestors`, `dept_name`, `order_num`, `leader`, `phone`, `email`, `create_by`) VALUES (@headOfficeId, CONCAT('0,',@headOfficeId), '研发部门', 1, 'JeeModel', '15888888888', 'ry@qq.com', 'system');
INSERT INTO `manage_dept` (`parent_id`, `ancestors`, `dept_name`, `order_num`, `leader`, `phone`, `email`, `create_by`) VALUES (@headOfficeId, CONCAT('0,',@headOfficeId), '市场部门', 2, 'JeeModel', '15888888888', 'ry@qq.com', 'system');
INSERT INTO `manage_dept` (`parent_id`, `ancestors`, `dept_name`, `order_num`, `leader`, `phone`, `email`, `create_by`) VALUES (@headOfficeId, CONCAT('0,',@headOfficeId), '测试部门', 3, 'JeeModel', '15888888888', 'ry@qq.com', 'system');
SELECT @headOfficeDeptTestId := LAST_INSERT_ID();
INSERT INTO `manage_dept` (`parent_id`, `ancestors`, `dept_name`, `order_num`, `leader`, `phone`, `email`, `create_by`) VALUES (@headOfficeId, CONCAT('0,',@headOfficeId), '财务部门', 4, 'JeeModel', '15888888888', 'ry@qq.com', 'system');
INSERT INTO `manage_dept` (`parent_id`, `ancestors`, `dept_name`, `order_num`, `leader`, `phone`, `email`, `create_by`) VALUES (@headOfficeId, CONCAT('0,',@headOfficeId), '运维部门', 5, 'JeeModel', '15888888888', 'ry@qq.com', 'system');




-- 初始化-合肥分公司 市场和财务部门
INSERT INTO `manage_dept` (`ancestors`, `dept_name`, `order_num`, `leader`, `phone`, `email`, `create_by`) VALUES ('0', '合肥分公司', 2, 'JeeModel', '15888888888', 'ry@qq.com', 'system');
SELECT @branchComId := LAST_INSERT_ID();

INSERT INTO `manage_dept` (`parent_id`, `ancestors`, `dept_name`, `order_num`, `leader`, `phone`, `email`, `create_by`) VALUES (@branchComId, CONCAT('0,',@branchComId), '市场部门', 1, 'JeeModel', '15888888888', 'ry@qq.com', 'system');
INSERT INTO `manage_dept` (`parent_id`, `ancestors`, `dept_name`, `order_num`, `leader`, `phone`, `email`, `create_by`) VALUES (@branchComId, CONCAT('0,',@branchComId), '财务部门', 2, 'JeeModel', '15888888888', 'ry@qq.com', 'system');



-- ----------------------------
-- Table structure for manage_role
-- ----------------------------
DROP TABLE IF EXISTS `manage_role`;
CREATE TABLE `manage_role`  (
  `id` 						bigint(0) 		NOT NULL AUTO_INCREMENT 	COMMENT '主键ID',
  `role_name` 				varchar(30)  	NOT NULL 					COMMENT '角色名称',
  `role_key` 				varchar(100)  	NOT NULL 					COMMENT '角色权限字符串',
  `role_sort` 				int(0) 			NOT NULL 					COMMENT '显示顺序',
  `data_scope` 				char(1)  		NOT NULL DEFAULT '2' 		COMMENT '数据范围（1：全部数据权限 2：自定数据权限 3：本部门数据权限 4：本部门及以下数据权限）',
  `menu_check_strictly` 	tinyint(1) 		NOT NULL DEFAULT 1 			COMMENT '菜单树选择项是否关联显示',
  `dept_check_strictly` 	tinyint(1) 		NOT NULL DEFAULT 1 			COMMENT '部门树选择项是否关联显示',
  
  `remark` 			varchar(500)  		NOT NULL DEFAULT '' 						COMMENT '备注',
  `create_by` 		varchar(64)  		NOT NULL DEFAULT '' 						COMMENT '创建者',
  `create_time` 	datetime 			NOT NULL DEFAULT CURRENT_TIMESTAMP 			COMMENT '创建时间',
  `status` 			char(1)  			NOT NULL DEFAULT '0' 						COMMENT '状态（0正常 1停用）',
  `update_by` 		varchar(64)  		NOT NULL DEFAULT '' 						COMMENT '更新者',
  `update_time` 	datetime 			DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP 	COMMENT '更新时间',
  `del_flag` 		char(1)  			NOT NULL DEFAULT '0' 						COMMENT '删除标志（0代表存在 2代表删除）',
  `del_time` 		datetime 			DEFAULT NULL 								COMMENT '删除时间',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB  COMMENT = '角色信息表' ;

-- ----------------------------
-- Records of manage_role
-- ----------------------------
-- 插入【角色信息表】初始数据-并获取获取—系统管理员ID、普通角色ID
INSERT INTO `manage_role` (`role_name`, `role_key`, `role_sort`, `data_scope`, `create_by`) VALUES ('超级管理员', 'admin', 1, '1', 'system');
SELECT @roleAdminId := LAST_INSERT_ID();
INSERT INTO `manage_role` (`role_name`, `role_key`, `role_sort`, `data_scope`, `create_by`) VALUES ('普通角色', 'common', 2, '2', 'system');
SELECT @roleCommonId := LAST_INSERT_ID();


-- ----------------------------
-- Table structure for manage_role_dept
-- ----------------------------
DROP TABLE IF EXISTS `manage_role_dept`;
CREATE TABLE `manage_role_dept`  (
  `id` 				bigint(0) 		NOT NULL AUTO_INCREMENT 	COMMENT '主键ID',
  `role_id` 		bigint(0) 		NOT NULL 					COMMENT '角色ID',
  `dept_id` 		bigint(0) 		NOT NULL 					COMMENT '部门ID',
  
  `remark` 			varchar(500)  		NOT NULL DEFAULT '' 						COMMENT '备注',
  `create_time` 	datetime 			NOT NULL DEFAULT CURRENT_TIMESTAMP 			COMMENT '创建时间',
  PRIMARY KEY (`id`),
  UNIQUE KEY `uk_role_dept` (`role_id`,`dept_id`) USING BTREE COMMENT '角色-部门 唯一'
) ENGINE = InnoDB  COMMENT = '角色和部门关联表' ;

-- ----------------------------
-- Records of manage_role_dept
-- ----------------------------
## 插入【角色和部门关系表】初始数据（admin角色默认拥有所有权限）
-- INSERT INTO `manage_role_dept` (`role_id`, `dept_id`) VALUES (@roleCommonId, @rootDeptId);
INSERT INTO `manage_role_dept` (`role_id`, `dept_id`) VALUES (@roleCommonId, @headOfficeId);
INSERT INTO `manage_role_dept` (`role_id`, `dept_id`) VALUES (@roleCommonId, @headOfficeDeptTestId);




-- ----------------------------
-- Table structure for manage_post
-- ----------------------------
DROP TABLE IF EXISTS `manage_post`;
CREATE TABLE `manage_post`  (
  `id` 				bigint(0) 		NOT NULL AUTO_INCREMENT 		COMMENT '主键ID',
  `post_code` 		varchar(64)  	NOT NULL 						COMMENT '岗位编码',
  `post_name` 		varchar(50)  	NOT NULL 						COMMENT '岗位名称',
  `post_sort` 		int(0) 			NOT NULL 						COMMENT '显示顺序',
  
  `remark` 			varchar(500)  		NOT NULL DEFAULT '' 						COMMENT '备注',
  `create_by` 		varchar(64)  		NOT NULL DEFAULT '' 						COMMENT '创建者',
  `create_time` 	datetime 			NOT NULL DEFAULT CURRENT_TIMESTAMP 			COMMENT '创建时间',
  `status` 			char(1)  			NOT NULL DEFAULT '0' 						COMMENT '状态（0正常 1停用）',
  `update_by` 		varchar(64)  		NOT NULL DEFAULT '' 						COMMENT '更新者',
  `update_time` 	datetime 			DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP 	COMMENT '更新时间',
  `del_flag` 		char(1)  			NOT NULL DEFAULT '0' 						COMMENT '删除标志（0代表存在 2代表删除）',
  `del_time` 		datetime 			DEFAULT NULL 								COMMENT '删除时间',
  
  PRIMARY KEY (`id`) ,
  UNIQUE KEY `uk_code` (`post_code`) COMMENT '岗位编码唯一'
) ENGINE = InnoDB   COMMENT = '岗位信息表' ;

-- ----------------------------
-- Records of manage_post
-- ----------------------------

INSERT INTO `manage_post` (`post_code`, `post_name`, `post_sort`, `create_by`) VALUES ('ceo', '董事长', 1, 'system');
SELECT @postCeoId := LAST_INSERT_ID();
INSERT INTO `manage_post` (`post_code`, `post_name`, `post_sort`, `create_by`) VALUES ('se', '项目经理', 2, 'system');
SELECT @postPmId := LAST_INSERT_ID();
INSERT INTO `manage_post` (`post_code`, `post_name`, `post_sort`, `create_by`) VALUES ('hr', '人力资源', 3, 'system');
INSERT INTO `manage_post` (`post_code`, `post_name`, `post_sort`, `create_by`) VALUES ('user', '普通员工', 4, 'system');



-- ----------------------------
-- Table structure for manage_notice
-- ----------------------------
DROP TABLE IF EXISTS `manage_notice`;
CREATE TABLE `manage_notice`  (
  `id` 				int(0) 			NOT NULL AUTO_INCREMENT 		COMMENT '主键ID',
  `notice_title` 	varchar(50)  	NOT NULL 						COMMENT '公告标题',
  `notice_type` 	char(1)  		NOT NULL 						COMMENT '公告类型（1通知 2公告）',
  `notice_content` 	longblob 		NOT NULL 						COMMENT '公告内容',
  
  `remark` 			varchar(500)  		NOT NULL DEFAULT '' 						COMMENT '备注',
  `create_by` 		varchar(64)  		NOT NULL DEFAULT '' 						COMMENT '创建者',
  `create_time` 	datetime 			NOT NULL DEFAULT CURRENT_TIMESTAMP 			COMMENT '创建时间',
  `status` 			char(1)  			NOT NULL DEFAULT '0' 						COMMENT '状态（0正常 1停用）',
  `update_by` 		varchar(64)  		NOT NULL DEFAULT '' 						COMMENT '更新者',
  `update_time` 	datetime 			DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP 	COMMENT '更新时间',
  `del_flag` 		char(1)  			NOT NULL DEFAULT '0' 						COMMENT '删除标志（0代表存在 2代表删除）',
  `del_time` 		datetime 			DEFAULT NULL 								COMMENT '删除时间',
  PRIMARY KEY (`id`),
  UNIQUE KEY `uk_title` (`notice_title`) COMMENT '标题唯一'
) ENGINE = InnoDB  COMMENT = '通知公告表' ;

-- ----------------------------
-- Records of manage_notice
-- ----------------------------
INSERT INTO `manage_notice` (`notice_title`, `notice_type`, `notice_content`, `create_by`) VALUES ('温馨提醒：2018-07-01 JeeModel新版本发布啦', '2', 0xE696B0E78988E69CACE58685E5AEB9, 'system');
INSERT INTO `manage_notice` (`notice_title`, `notice_type`, `notice_content`, `create_by`) VALUES ('维护通知：2018-07-01 JeeModel系统凌晨维护', '1', 0xE7BBB4E68AA4E58685E5AEB9, 'system');




-- ----------------------------
-- Table structure for manage_user
-- ----------------------------
DROP TABLE IF EXISTS `manage_user`;
CREATE TABLE `manage_user`  (
  `id` 				bigint(0) 		NOT NULL AUTO_INCREMENT 		COMMENT '主键ID',
  `user_name` 		varchar(30)  	NOT NULL 						COMMENT '用户账号',
  `nick_name` 		varchar(30)  	NOT NULL 						COMMENT '用户昵称',
  `user_type` 		char(1)  		NOT NULL DEFAULT 0 				COMMENT '用户类型（0系统用户 1注册用户）',
  `sex` 			char(1)  		NOT NULL DEFAULT '0' 			COMMENT '用户性别（0男 1女 2未知）',
  `password` 		varchar(100)  	NOT NULL 			 			COMMENT '密码',
  `email` 			varchar(50)  	NOT NULL DEFAULT '' 			COMMENT '用户邮箱',
  `phonenumber` 	varchar(11)  	NOT NULL DEFAULT '' 			COMMENT '手机号码',
  `dept_id` 		bigint(0) 		NOT NULL DEFAULT 0 				COMMENT '部门ID',
  `avatar` 			varchar(100)  	NOT NULL DEFAULT '' 			COMMENT '头像地址',
  `login_ip` 		varchar(128)  	NOT NULL DEFAULT '' 			COMMENT '最后登录IP',
  `login_date` 		datetime(0) 	NULL DEFAULT NULL 				COMMENT '最后登录时间',
  
  `remark` 			varchar(500)  		NOT NULL DEFAULT '' 						COMMENT '备注',
  `create_by` 		varchar(64)  		NOT NULL DEFAULT '' 						COMMENT '创建者',
  `create_time` 	datetime 			NOT NULL DEFAULT CURRENT_TIMESTAMP 			COMMENT '创建时间',
  `status` 			char(1)  			NOT NULL DEFAULT '0' 						COMMENT '状态（0正常 1停用）',
  `update_by` 		varchar(64)  		NOT NULL DEFAULT '' 						COMMENT '更新者',
  `update_time` 	datetime 			DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP 	COMMENT '更新时间',
  `del_flag` 		char(1)  			NOT NULL DEFAULT '0' 						COMMENT '删除标志（0代表存在 2代表删除）',
  `del_time` 		datetime 			DEFAULT NULL 								COMMENT '删除时间',
  PRIMARY KEY (`id`),
  UNIQUE KEY `uk_name` (`user_name`) COMMENT '账号唯一'
) ENGINE = InnoDB  COMMENT = '用户信息表' ;

-- ----------------------------
-- Records of manage_user
-- ----------------------------
INSERT INTO `manage_user` (`dept_id`, `user_name`, `nick_name`, `email`, `phonenumber`, `sex`, `avatar`, `password`, `login_ip`, `create_by`, `remark`) 
				   VALUES (@headOfficeId, 'admin', '超级管理', 'rootfive@rootfive.cn', '15888888888', '1', '', '$2a$10$7JB720yubVSZvUI0rEqK/.VqGOZTH.ulu33dHOiBE8ByOhJIrdAu2', '127.0.0.1', 'system', '超级管理员');
SELECT @userAdminId := LAST_INSERT_ID();
INSERT INTO `manage_user` (`dept_id`, `user_name`, `nick_name`, `email`, `phonenumber`, `sex`, `avatar`, `password`, `login_ip`, `create_by`, `remark`) 
				   VALUES (@headOfficeDeptTestId, 'jm', '研发测试', 'jeemodel@rootfive.cn', '15666666666', '1', '', '$2a$10$7JB720yubVSZvUI0rEqK/.VqGOZTH.ulu33dHOiBE8ByOhJIrdAu2', '127.0.0.1', 'system', '测试员');
SELECT @userJeeModelId := LAST_INSERT_ID();


-- ----------------------------
-- Table structure for manage_user_account
-- ----------------------------
DROP TABLE IF EXISTS `manage_user_account`;
CREATE TABLE `manage_user_account`  (
  `id` 				bigint(0) 		NOT NULL AUTO_INCREMENT 		COMMENT '主键ID',
  `user_id` 		bigint(0) 		NOT NULL 						COMMENT '用户ID',
  `account_type` 	tinyint(0) 		NOT NULL DEFAULT 0 				COMMENT '账户类型（1手机 2邮箱）',
  `account_code` 	varchar(64)  	NOT NULL DEFAULT '' 			COMMENT '账户编码',
  `account_name` 	varchar(64)  	NOT NULL DEFAULT '' 			COMMENT '账户名称',
  `pwd_secret` 		varchar(128)  	NOT NULL DEFAULT '' 			COMMENT '密码',
  `login_ip` 		varchar(128)  	NOT NULL DEFAULT '' 			COMMENT '最后登录IP',
  `login_date` 		datetime(0) 	NULL DEFAULT NULL 				COMMENT '最后登录时间',
  
  `remark` 			varchar(500)  		NOT NULL DEFAULT '' 						COMMENT '备注',
  `create_by` 		varchar(64)  		NOT NULL DEFAULT '' 						COMMENT '创建者',
  `create_time` 	datetime 			NOT NULL DEFAULT CURRENT_TIMESTAMP 			COMMENT '创建时间',
  `status` 			char(1)  			NOT NULL DEFAULT '0' 						COMMENT '状态（0正常 1停用）',
  `update_by` 		varchar(64)  		NOT NULL DEFAULT '' 						COMMENT '更新者',
  `update_time` 	datetime 			DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP 	COMMENT '更新时间',
  `del_flag` 		char(1)  			NOT NULL DEFAULT '0' 						COMMENT '删除标志（0代表存在 2代表删除）',
  `del_time` 		datetime 			DEFAULT NULL 								COMMENT '删除时间',
  PRIMARY KEY (`id`),
  UNIQUE KEY `uk_type_code` (`account_type`,`account_code`) COMMENT '账号类型唯一'
) ENGINE = InnoDB   	COMMENT = '用户账户表' ;




-- ----------------------------
-- Table structure for manage_user_post
-- ----------------------------
DROP TABLE IF EXISTS `manage_user_post`;
CREATE TABLE `manage_user_post`  (
  `id` 				bigint(0) 		NOT NULL AUTO_INCREMENT 	COMMENT '主键ID',
  `user_id` 		bigint(0) 		NOT NULL 					COMMENT '用户ID',
  `post_id` 		bigint(0) 		NOT NULL 					COMMENT '岗位ID',
  
  `remark` 			varchar(500)  		NOT NULL DEFAULT '' 						COMMENT '备注',
  `create_time` 	datetime 			NOT NULL DEFAULT CURRENT_TIMESTAMP 			COMMENT '创建时间',
  PRIMARY KEY (`id`),
  UNIQUE KEY `uk_user_post` (`user_id`,`post_id`) USING BTREE COMMENT '用户-岗位 唯一'
) ENGINE = InnoDB  COMMENT = '用户与岗位关联表' ;


-- ----------------------------
-- Records of manage_user_post
-- ----------------------------
## 初始化插入【用户与岗位关系表】数据
INSERT INTO `manage_user_post` (`user_id`, `post_id`) VALUES (@userAdminId, @postCeoId),(@userJeeModelId, @postPmId);



-- ----------------------------
-- Table structure for manage_user_role
-- ----------------------------
DROP TABLE IF EXISTS `manage_user_role`;
CREATE TABLE `manage_user_role`  (
  `id` 				bigint(0) 		NOT NULL AUTO_INCREMENT 	COMMENT '主键ID',
  `user_id` 		bigint(0) 		NOT NULL 		COMMENT '用户ID',
  `role_id` 		bigint(0) 		NOT NULL 		COMMENT '角色ID',
  
  `remark` 			varchar(500)  		NOT NULL DEFAULT '' 						COMMENT '备注',
  `create_time` 	datetime 			NOT NULL DEFAULT CURRENT_TIMESTAMP 			COMMENT '创建时间',
  PRIMARY KEY (`id`),
  UNIQUE KEY `uk_user_role` (`user_id`,`role_id`) USING BTREE COMMENT '用户-角色 唯一'
) ENGINE = InnoDB  COMMENT = '用户和角色关联表' ;

-- ----------------------------
-- Records of manage_user_role
-- ----------------------------
## 初始化插入【用户和角色关系表】数据
INSERT INTO `manage_user_role` (`user_id`, `role_id`) VALUES (@userAdminId, @roleAdminId),(@userJeeModelId, @roleCommonId);



-- ----------------------------
-- Table structure for manage_user_log_login
-- ----------------------------
DROP TABLE IF EXISTS `manage_user_log_login`;
CREATE TABLE `manage_user_log_login`  (
  `id` 				bigint(0) 		NOT NULL AUTO_INCREMENT 		COMMENT '主键ID',
  `user_name` 		varchar(50)  	NOT NULL 						COMMENT '用户账号',
  `ipaddr` 			varchar(128)  	NOT NULL 						COMMENT '登录IP地址',
  `login_location` 	varchar(255)  	NOT NULL 						COMMENT '登录地点',
  `browser` 		varchar(50)  	NOT NULL DEFAULT ''				COMMENT '浏览器类型',
  `os` 				varchar(50)  	NOT NULL DEFAULT '' 			COMMENT '操作系统',
  `status` 			char(1)  		NOT NULL DEFAULT '0' 			COMMENT '登录状态（0成功 1失败）',
  `msg` 			varchar(255)  	NOT NULL DEFAULT '' 			COMMENT '提示消息',
  `login_time` 		datetime(0) 	NOT NULL  						COMMENT '访问时间',
  
  `remark` 			varchar(500)  		NOT NULL DEFAULT '' 						COMMENT '备注',
  `create_time` 	datetime 			NOT NULL DEFAULT CURRENT_TIMESTAMP 			COMMENT '创建时间',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB  COMMENT = '用户-访问日志记录表' ;

-- ----------------------------
-- Records of manage_user_log_login
-- ----------------------------


-- ----------------------------
-- Table structure for manage_user_log_oper
-- ----------------------------
DROP TABLE IF EXISTS `manage_user_log_oper`;
CREATE TABLE `manage_user_log_oper`  (
  `id` 				bigint(0) 		NOT NULL AUTO_INCREMENT 		COMMENT '主键ID',
  `title` 			varchar(50)  	NOT NULL DEFAULT '' 			COMMENT '模块标题',
  `business_type` 	int(0) 			NOT NULL DEFAULT 0 				COMMENT '业务类型（0其它 1新增 2修改 3删除）',
  `method` 			varchar(100)  	NOT NULL 						COMMENT '方法名称',
  `request_method` 	varchar(10)  	NOT NULL		 				COMMENT '请求方式',
  `operator_type` 	int(0) 			NOT NULL DEFAULT 0 				COMMENT '操作类别（0其它 1后台用户 2手机端用户）',
  `oper_name` 		varchar(50)  	NOT NULL 						COMMENT '操作人员',
  `dept_name` 		varchar(50)  	NOT NULL DEFAULT '' 			COMMENT '部门名称',
  `oper_url` 		varchar(255)  	NOT NULL DEFAULT '' 			COMMENT '请求URL',
  `oper_ip` 		varchar(128)  	NOT NULL DEFAULT '' 			COMMENT '主机地址',
  `oper_location` 	varchar(255)  	NOT NULL DEFAULT '' 			COMMENT '操作地点',
  `oper_param` 		varchar(2000)  	NOT NULL DEFAULT '' 			COMMENT '请求参数',
  `json_result` 	varchar(2000)  	NOT NULL DEFAULT '' 			COMMENT '返回参数',
  `status` 			int(0) 			NOT NULL DEFAULT 0 				COMMENT '操作状态（0正常 1异常）',
  `error_msg` 		varchar(2000)  	NOT NULL DEFAULT '' 			COMMENT '错误消息',
  `oper_time` 		datetime(0) 	NOT NULL  						COMMENT '操作时间',
  
  `remark` 			varchar(500)  		NOT NULL DEFAULT '' 						COMMENT '备注',
  `create_time` 	datetime 			NOT NULL DEFAULT CURRENT_TIMESTAMP 			COMMENT '创建时间',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB COMMENT = '用户-操作日志记录表' ;

-- ----------------------------
-- Records of manage_user_log_oper
-- ----------------------------





-- ----------------------------
-- Table structure for manage_menu
-- ----------------------------
DROP TABLE IF EXISTS `manage_menu`;
CREATE TABLE `manage_menu`  (
  `id` 				bigint(0) 		NOT NULL AUTO_INCREMENT 		COMMENT '主键ID',
  `menu_name` 		varchar(50)  	NOT NULL 						COMMENT '菜单名称',
  `parent_id` 		bigint(0) 		NOT NULL DEFAULT 0 					COMMENT '父菜单ID',
  `order_num` 		int(0) 			NOT NULL DEFAULT 0 					COMMENT '显示顺序',
  `path` 			varchar(200)  	NOT NULL DEFAULT '' 				COMMENT '路由地址',
  `component` 		varchar(255)  	NOT NULL DEFAULT '' 				COMMENT '组件路径',
  `query` 			varchar(255)  	NOT NULL DEFAULT '' 				COMMENT '路由参数',
  `is_frame` 		int(0) 			NOT NULL DEFAULT 1 					COMMENT '是否为外链（0是 1否）',
  `is_cache` 		int(0) 			NOT NULL DEFAULT 0 					COMMENT '是否缓存（0缓存 1不缓存）',
  `menu_type` 		char(1)  		NOT NULL DEFAULT '' 				COMMENT '菜单类型（M目录 C菜单 F按钮）',
  `visible` 		char(1)  		NOT NULL DEFAULT '0' 				COMMENT '菜单状态（0显示 1隐藏）',
  `perms` 			varchar(100)  	NOT NULL DEFAULT '' 				COMMENT '权限标识',
  `icon` 			varchar(100)  	NOT NULL DEFAULT '#' 				COMMENT '菜单图标',
  
  `remark` 			varchar(500)  		NOT NULL DEFAULT '' 						COMMENT '备注',
  `create_by` 		varchar(64)  		NOT NULL DEFAULT '' 						COMMENT '创建者',
  `create_time` 	datetime 			NOT NULL DEFAULT CURRENT_TIMESTAMP 			COMMENT '创建时间',
  `status` 			char(1)  			NOT NULL DEFAULT '0' 						COMMENT '状态（0正常 1停用）',
  `update_by` 		varchar(64)  		NOT NULL DEFAULT '' 						COMMENT '更新者',
  `update_time` 	datetime 			DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP 	COMMENT '更新时间',
  `del_flag` 		char(1)  			NOT NULL DEFAULT '0' 						COMMENT '删除标志（0代表存在 2代表删除）',
  `del_time` 		datetime 			DEFAULT NULL 								COMMENT '删除时间',
  PRIMARY KEY (`id`),
  UNIQUE KEY `uk_parent_menu_name` (`menu_name`,`parent_id`)  COMMENT '同一父菜单下，菜单名称唯一'
) ENGINE = InnoDB   COMMENT = '菜单权限表' ;

-- ----------------------------
-- Records of manage_menu
-- ----------------------------
-- 一级菜单-官网示例
INSERT INTO `manage_menu` ( `menu_name`, `parent_id`, `order_num`, `path`,`query`, `is_frame`, `is_cache`, `menu_type`, `visible`, `status`, `perms`, `icon`, `create_by`,`remark`) 
				   VALUES ('官网示例', 0, 1, 'http://www.rootfive.cn','', 0, 0, 'M', '0', '0', '', 'guide', 'system', 'JeeModel官网地址');
SELECT @officialWebsiteId := LAST_INSERT_ID();
				   
-- 一级菜单-系统管理
INSERT INTO `manage_menu` ( `menu_name`, `parent_id`, `order_num`, `path`, `query`, `is_frame`, `is_cache`, `menu_type`, `visible`, `status`, `perms`, `icon`, `create_by`,`remark`) 
				   VALUES ('系统管理', 0, 2, 'manage', '', 1, 0, 'M', '0', '0', '', 'system', 'system', '系统管理目录');
SELECT @systemManageId := LAST_INSERT_ID();

-- 一级菜单-系统管理二级菜单：参数、字典、菜单、部门、角色、岗位、公告
	
INSERT INTO `manage_menu` ( `menu_name`, `parent_id`, `order_num`, `path`, `component`, `query`, `is_frame`, `is_cache`, `menu_type`, `visible`, `status`, `perms`, `icon`, `create_by`,`remark`) 
				   VALUES ('字典管理', @systemManageId, 1, 'dict', 'manage/dict/index', '', 1, 0, 'C', '0', '0', 'manage:dict:list', 'dict', 'system', '字典管理菜单');
SELECT @dictManageId := LAST_INSERT_ID();
				   

INSERT INTO `manage_menu` ( `menu_name`, `parent_id`, `order_num`, `path`, `component`, `query`, `is_frame`, `is_cache`, `menu_type`, `visible`, `status`, `perms`, `icon`, `create_by`,`remark`) 
				   VALUES ('参数设置', @systemManageId, 2, 'config', 'manage/config/index', '', 1, 0, 'C', '0', '0', 'manage:config:list', 'edit', 'system', '参数设置菜单');
SELECT @configManageId := LAST_INSERT_ID();


INSERT INTO `manage_menu` ( `menu_name`, `parent_id`, `order_num`, `path`, `component`, `query`, `is_frame`, `is_cache`, `menu_type`, `visible`, `status`, `perms`, `icon`, `create_by`,`remark`) 
				   VALUES ('通知公告', @systemManageId, 3, 'notice', 'manage/notice/index', '', 1, 0, 'C', '0', '0', 'manage:notice:list', 'message', 'system', '通知公告菜单');
SELECT @noticeManageId := LAST_INSERT_ID();

				   
INSERT INTO `manage_menu` ( `menu_name`, `parent_id`, `order_num`, `path`, `component`, `query`, `is_frame`, `is_cache`, `menu_type`, `visible`, `status`, `perms`, `icon`, `create_by`,`remark`) 
				   VALUES ('菜单管理', @systemManageId, 4, 'menu', 'manage/menu/index', '', 1, 0, 'C', '0', '0', 'manage:menu:list', 'tree-table', 'system', '菜单管理菜单');
SELECT @menuManageId := LAST_INSERT_ID();


INSERT INTO `manage_menu` ( `menu_name`, `parent_id`, `order_num`, `path`, `component`, `query`, `is_frame`, `is_cache`, `menu_type`, `visible`, `status`, `perms`, `icon`, `create_by`,`remark`) 
				   VALUES ('部门管理', @systemManageId, 5, 'dept', 'manage/dept/index', '', 1, 0, 'C', '0', '0', 'manage:dept:list', 'tree', 'system', '部门管理菜单');
SELECT @deptManageId := LAST_INSERT_ID();				   


INSERT INTO `manage_menu` ( `menu_name`, `parent_id`, `order_num`, `path`, `component`, `query`, `is_frame`, `is_cache`, `menu_type`, `visible`, `status`, `perms`, `icon`, `create_by`,`remark`) 
				   VALUES ('角色管理', @systemManageId, 6, 'role', 'manage/role/index', '', 1, 0, 'C', '0', '0', 'manage:role:list', 'peoples', 'system', '角色管理菜单');
SELECT @roleManageId := LAST_INSERT_ID();


INSERT INTO `manage_menu` ( `menu_name`, `parent_id`, `order_num`, `path`, `component`, `query`, `is_frame`, `is_cache`, `menu_type`, `visible`, `status`, `perms`, `icon`, `create_by`,`remark`) 
				   VALUES ('岗位管理', @systemManageId, 7, 'post', 'manage/post/index', '', 1, 0, 'C', '0', '0', 'manage:post:list', 'post', 'system', '岗位管理菜单');
SELECT @postManageId := LAST_INSERT_ID();



INSERT INTO `manage_menu` ( `menu_name`, `parent_id`, `order_num`, `path`, `query`, `is_frame`, `is_cache`, `menu_type`, `visible`, `status`, `perms`, `icon`, `create_by`,`remark`) 
				   VALUES ('用户管理', @systemManageId, 8, 'user', '', 1, 0, 'M', '0', '0', '', 'peoples', 'system', '用户管理目录');				   
SELECT @userManageId := LAST_INSERT_ID();
				   
INSERT INTO `manage_menu` ( `menu_name`, `parent_id`, `order_num`, `path`, `component`, `query`, `is_frame`, `is_cache`, `menu_type`, `visible`, `status`, `perms`, `icon`, `create_by`,`remark`) 
				   VALUES ('管理用户', @userManageId, 1, 'list', 'manage/user/list/index', '', 1, 0, 'C', '0', '0', 'manage:user:list', 'user', 'system', '用户管理菜单');
SELECT @manageUserId := LAST_INSERT_ID();
				   
INSERT INTO `manage_menu` ( `menu_name`, `parent_id`, `order_num`, `path`, `component`, `query`, `is_frame`, `is_cache`, `menu_type`, `visible`, `status`, `perms`, `icon`, `create_by`,`remark`) 
				   VALUES ('在线用户', @userManageId, 2, 'online', 'manage/user/online/index', '', 1, 0, 'C', '0', '0', 'manage:userOnline:list', 'online', 'system', '在线用户菜单');
SELECT @userOnlineManageId := LAST_INSERT_ID();


INSERT INTO `manage_menu` ( `menu_name`, `parent_id`, `order_num`, `path`, `component`, `query`, `is_frame`, `is_cache`, `menu_type`, `visible`, `status`, `perms`, `icon`, `create_by`,`remark`) 
				   VALUES ('日志管理', @systemManageId, 9, 'log', '', '', 1, 0, 'M', '0', '0', '', 'log', 'system', '日志管理菜单');
SELECT @logManageId := LAST_INSERT_ID();

INSERT INTO `manage_menu` ( `menu_name`, `parent_id`, `order_num`, `path`, `component`, `query`, `is_frame`, `is_cache`, `menu_type`, `visible`, `status`, `perms`, `icon`, `create_by`,`remark`) 
				   VALUES ('登录日志',  @logManageId, 1, 'user/login', 'manage/log/userLogin/index', '', 1, 0, 'C', '0', '0', 'manage:logUserLogin:list', 'logininfor', 'system', '登录日志菜单');
SELECT @loginLogManageId := LAST_INSERT_ID();

INSERT INTO `manage_menu` ( `menu_name`, `parent_id`, `order_num`, `path`, `component`, `query`, `is_frame`, `is_cache`, `menu_type`, `visible`, `status`, `perms`, `icon`, `create_by`,`remark`) 
				   VALUES ('操作日志',  @logManageId, 2, 'user/oper', 'manage/log/userOper/index', '', 1, 0, 'C', '0', '0', 'manage:logUserOper:list', 'form', 'system', '操作日志菜单');
SELECT @operLogManageId := LAST_INSERT_ID();
				   
				   
				   
-- 一级菜单-拓展功能 
				   
INSERT INTO `manage_menu` ( `menu_name`, `parent_id`, `order_num`, `path`, `query`, `is_frame`, `is_cache`, `menu_type`, `visible`, `status`, `perms`, `icon`, `create_by`,`remark`)
				   VALUES ('拓展功能', 0, 3, 'unit', '', 1, 0, 'M', '0', '0', '', 'link', 'system', '拓展功能目录');
SELECT @expandUnitId := LAST_INSERT_ID();				   
				   

INSERT INTO `manage_menu` ( `menu_name`, `parent_id`, `order_num`, `path`, `query`, `is_frame`, `is_cache`, `menu_type`, `visible`, `status`, `perms`, `icon`, `create_by`,`remark`) 
				   VALUES ('系统监控', @expandUnitId, 1, 'monitor', '', 1, 0, 'M', '0', '0', '', 'monitor', 'system', '系统监控目录');
SELECT @systemMonitorId := LAST_INSERT_ID();

INSERT INTO `manage_menu` ( `menu_name`, `parent_id`, `order_num`, `path`, `component`, `query`, `is_frame`, `is_cache`, `menu_type`, `visible`, `status`, `perms`, `icon`, `create_by`,`remark`) 
				   VALUES ('数据监控', @systemMonitorId, 1, 'druid', 'unit/monitor/druid/index', '', 1, 0, 'C', '0', '0', 'monitor:druid:list', 'druid', 'system', '数据监控菜单');
SELECT @systemMonitorDruidId := LAST_INSERT_ID();

INSERT INTO `manage_menu` ( `menu_name`, `parent_id`, `order_num`, `path`, `component`, `query`, `is_frame`, `is_cache`, `menu_type`, `visible`, `status`, `perms`, `icon`, `create_by`,`remark`) 
				   VALUES ('缓存监控', @systemMonitorId, 2, 'cache', 'unit/monitor/cache/index', '', 1, 0, 'C', '0', '0', 'monitor:cache:list', 'redis', 'system', '缓存监控菜单');
SELECT @systemMonitorCacheId := LAST_INSERT_ID();
				   
				   
				   



-- 按钮 字典管理
INSERT INTO `manage_menu` ( `menu_name`, `parent_id`, `order_num`, `path`, `component`, `query`, `is_frame`, `is_cache`, `menu_type`, `visible`, `status`, `perms`, `icon`, `create_by`,`remark`) 
				   VALUES ('字典查询', @dictManageId, 1, '#', '', '', 1, 0, 'F', '0', '0', 'manage:dict:query', '#', 'system', '');
SELECT @dictManageBQueryId := LAST_INSERT_ID();
INSERT INTO `manage_menu` ( `menu_name`, `parent_id`, `order_num`, `path`, `component`, `query`, `is_frame`, `is_cache`, `menu_type`, `visible`, `status`, `perms`, `icon`, `create_by`,`remark`) 
				   VALUES ('字典新增', @dictManageId, 2, '#', '', '', 1, 0, 'F', '0', '0', 'manage:dict:add', '#', 'system', '');
SELECT @dictManageBAddId := LAST_INSERT_ID();
INSERT INTO `manage_menu` ( `menu_name`, `parent_id`, `order_num`, `path`, `component`, `query`, `is_frame`, `is_cache`, `menu_type`, `visible`, `status`, `perms`, `icon`, `create_by`,`remark`) 
				   VALUES ('字典修改', @dictManageId, 3, '#', '', '', 1, 0, 'F', '0', '0', 'manage:dict:edit', '#', 'system', '');
SELECT @dictManageBUpdateId := LAST_INSERT_ID();
INSERT INTO `manage_menu` ( `menu_name`, `parent_id`, `order_num`, `path`, `component`, `query`, `is_frame`, `is_cache`, `menu_type`, `visible`, `status`, `perms`, `icon`, `create_by`,`remark`) 
				   VALUES ('字典删除', @dictManageId, 4, '#', '', '', 1, 0, 'F', '0', '0', 'manage:dict:remove', '#', 'system', '');
SELECT @dictManageBDelId := LAST_INSERT_ID();
INSERT INTO `manage_menu` ( `menu_name`, `parent_id`, `order_num`, `path`, `component`, `query`, `is_frame`, `is_cache`, `menu_type`, `visible`, `status`, `perms`, `icon`, `create_by`,`remark`) 
				   VALUES ('字典导出', @dictManageId, 5, '#', '', '', 1, 0, 'F', '0', '0', 'manage:dict:export', '#', 'system', '');
SELECT @dictManageBExportId := LAST_INSERT_ID();





-- 按钮 参数管理
INSERT INTO `manage_menu` ( `menu_name`, `parent_id`, `order_num`, `path`, `component`, `query`, `is_frame`, `is_cache`, `menu_type`, `visible`, `status`, `perms`, `icon`, `create_by`,`remark`) 
				   VALUES ('参数查询', @configManageId, 1, '#', '', '', 1, 0, 'F', '0', '0', 'manage:config:query', '#', 'system', '');
SELECT @configManageBQueryId := LAST_INSERT_ID();
INSERT INTO `manage_menu` ( `menu_name`, `parent_id`, `order_num`, `path`, `component`, `query`, `is_frame`, `is_cache`, `menu_type`, `visible`, `status`, `perms`, `icon`, `create_by`,`remark`) 
				   VALUES ('参数新增', @configManageId, 2, '#', '', '', 1, 0, 'F', '0', '0', 'manage:config:add', '#', 'system', '');
SELECT @configManageBAddId := LAST_INSERT_ID();
INSERT INTO `manage_menu` ( `menu_name`, `parent_id`, `order_num`, `path`, `component`, `query`, `is_frame`, `is_cache`, `menu_type`, `visible`, `status`, `perms`, `icon`, `create_by`,`remark`) 
				   VALUES ('参数修改', @configManageId, 3, '#', '', '', 1, 0, 'F', '0', '0', 'manage:config:edit', '#', 'system', '');
SELECT @configManageBUpdateId := LAST_INSERT_ID();
INSERT INTO `manage_menu` ( `menu_name`, `parent_id`, `order_num`, `path`, `component`, `query`, `is_frame`, `is_cache`, `menu_type`, `visible`, `status`, `perms`, `icon`, `create_by`,`remark`) 
				   VALUES ('参数删除', @configManageId, 4, '#', '', '', 1, 0, 'F', '0', '0', 'manage:config:remove', '#', 'system', '');
SELECT @configManageBDelId := LAST_INSERT_ID();
INSERT INTO `manage_menu` ( `menu_name`, `parent_id`, `order_num`, `path`, `component`, `query`, `is_frame`, `is_cache`, `menu_type`, `visible`, `status`, `perms`, `icon`, `create_by`,`remark`) 
				   VALUES ('参数导出', @configManageId, 5, '#', '', '', 1, 0, 'F', '0', '0', 'manage:config:export', '#', 'system', '');
SELECT @configManageBExportId := LAST_INSERT_ID();




-- 按钮 公告管理
INSERT INTO `manage_menu` ( `menu_name`, `parent_id`, `order_num`, `path`, `component`, `query`, `is_frame`, `is_cache`, `menu_type`, `visible`, `status`, `perms`, `icon`, `create_by`,`remark`) 
				   VALUES ('公告查询', @noticeManageId, 1, '#', '', '', 1, 0, 'F', '0', '0', 'manage:notice:query', '#', 'system', '');
SELECT @noticeManageBQueryId := LAST_INSERT_ID();
INSERT INTO `manage_menu` ( `menu_name`, `parent_id`, `order_num`, `path`, `component`, `query`, `is_frame`, `is_cache`, `menu_type`, `visible`, `status`, `perms`, `icon`, `create_by`,`remark`) 
				   VALUES ('公告新增', @noticeManageId, 2, '#', '', '', 1, 0, 'F', '0', '0', 'manage:notice:add', '#', 'system', '');
SELECT @noticeManageBAddId := LAST_INSERT_ID();
INSERT INTO `manage_menu` ( `menu_name`, `parent_id`, `order_num`, `path`, `component`, `query`, `is_frame`, `is_cache`, `menu_type`, `visible`, `status`, `perms`, `icon`, `create_by`,`remark`) 
				   VALUES ('公告修改', @noticeManageId, 3, '#', '', '', 1, 0, 'F', '0', '0', 'manage:notice:edit', '#', 'system', '');
SELECT @noticeManageBUpdateId := LAST_INSERT_ID();
INSERT INTO `manage_menu` ( `menu_name`, `parent_id`, `order_num`, `path`, `component`, `query`, `is_frame`, `is_cache`, `menu_type`, `visible`, `status`, `perms`, `icon`, `create_by`,`remark`) 
				   VALUES ('公告删除', @noticeManageId, 4, '#', '', '', 1, 0, 'F', '0', '0', 'manage:notice:remove', '#', 'system', '');
SELECT @noticeManageBDelId := LAST_INSERT_ID();




-- 按钮 菜单管理
INSERT INTO `manage_menu` ( `menu_name`, `parent_id`, `order_num`, `path`, `component`, `query`, `is_frame`, `is_cache`, `menu_type`, `visible`, `status`, `perms`, `icon`, `create_by`,`remark`) 
				   VALUES ('菜单查询', @menuManageId, 1, '', '', '', 1, 0, 'F', '0', '0', 'manage:menu:query', '#', 'system', '');
SELECT @menuManageBQueryId := LAST_INSERT_ID();
INSERT INTO `manage_menu` ( `menu_name`, `parent_id`, `order_num`, `path`, `component`, `query`, `is_frame`, `is_cache`, `menu_type`, `visible`, `status`, `perms`, `icon`, `create_by`,`remark`) 
				   VALUES ('菜单新增', @menuManageId, 2, '', '', '', 1, 0, 'F', '0', '0', 'manage:menu:add', '#', 'system', '');
SELECT @menuManageBAddId := LAST_INSERT_ID();
INSERT INTO `manage_menu` ( `menu_name`, `parent_id`, `order_num`, `path`, `component`, `query`, `is_frame`, `is_cache`, `menu_type`, `visible`, `status`, `perms`, `icon`, `create_by`,`remark`) 
				   VALUES ('菜单修改', @menuManageId, 3, '', '', '', 1, 0, 'F', '0', '0', 'manage:menu:edit', '#', 'system', '');
SELECT @menuManageBUpdateId := LAST_INSERT_ID();
INSERT INTO `manage_menu` ( `menu_name`, `parent_id`, `order_num`, `path`, `component`, `query`, `is_frame`, `is_cache`, `menu_type`, `visible`, `status`, `perms`, `icon`, `create_by`,`remark`) 
				   VALUES ('菜单删除', @menuManageId, 4, '', '', '', 1, 0, 'F', '0', '0', 'manage:menu:remove', '#', 'system', '');
SELECT @menuManageBDelId := LAST_INSERT_ID();



-- 按钮 部门管理
INSERT INTO `manage_menu` ( `menu_name`, `parent_id`, `order_num`, `path`, `component`, `query`, `is_frame`, `is_cache`, `menu_type`, `visible`, `status`, `perms`, `icon`, `create_by`,`remark`) 
				   VALUES ('部门查询', @deptManageId, 1, '', '', '', 1, 0, 'F', '0', '0', 'manage:dept:query', '#', 'system', '');
SELECT @deptManageBQueryId := LAST_INSERT_ID();
INSERT INTO `manage_menu` ( `menu_name`, `parent_id`, `order_num`, `path`, `component`, `query`, `is_frame`, `is_cache`, `menu_type`, `visible`, `status`, `perms`, `icon`, `create_by`,`remark`) 
				   VALUES ('部门新增', @deptManageId, 2, '', '', '', 1, 0, 'F', '0', '0', 'manage:dept:add', '#', 'system', '');
SELECT @deptManageBAddId := LAST_INSERT_ID();
INSERT INTO `manage_menu` ( `menu_name`, `parent_id`, `order_num`, `path`, `component`, `query`, `is_frame`, `is_cache`, `menu_type`, `visible`, `status`, `perms`, `icon`, `create_by`,`remark`) 
				   VALUES ('部门修改', @deptManageId, 3, '', '', '', 1, 0, 'F', '0', '0', 'manage:dept:edit', '#', 'system', '');
SELECT @deptManageBUpdateId := LAST_INSERT_ID();
INSERT INTO `manage_menu` ( `menu_name`, `parent_id`, `order_num`, `path`, `component`, `query`, `is_frame`, `is_cache`, `menu_type`, `visible`, `status`, `perms`, `icon`, `create_by`,`remark`) 
				   VALUES ('部门删除', @deptManageId, 4, '', '', '', 1, 0, 'F', '0', '0', 'manage:dept:remove', '#', 'system', '');
SELECT @deptManageBDelId := LAST_INSERT_ID();


-- 按钮 角色管理
INSERT INTO `manage_menu` ( `menu_name`, `parent_id`, `order_num`, `path`, `component`, `query`, `is_frame`, `is_cache`, `menu_type`, `visible`, `status`, `perms`, `icon`, `create_by`,`remark`) 
				   VALUES ('角色查询', @roleManageId, 1, '', '', '', 1, 0, 'F', '0', '0', 'manage:role:query', '#', 'system', '');
SELECT @roleManageBQueryId := LAST_INSERT_ID();
INSERT INTO `manage_menu` ( `menu_name`, `parent_id`, `order_num`, `path`, `component`, `query`, `is_frame`, `is_cache`, `menu_type`, `visible`, `status`, `perms`, `icon`, `create_by`,`remark`) 
				   VALUES ('角色新增', @roleManageId, 2, '', '', '', 1, 0, 'F', '0', '0', 'manage:role:add', '#', 'system', '');
SELECT @roleManageBAddId := LAST_INSERT_ID();
INSERT INTO `manage_menu` ( `menu_name`, `parent_id`, `order_num`, `path`, `component`, `query`, `is_frame`, `is_cache`, `menu_type`, `visible`, `status`, `perms`, `icon`, `create_by`,`remark`) 
				   VALUES ('角色修改', @roleManageId, 3, '', '', '', 1, 0, 'F', '0', '0', 'manage:role:edit', '#', 'system', '');
SELECT @roleManageBUpdateId := LAST_INSERT_ID();
INSERT INTO `manage_menu` ( `menu_name`, `parent_id`, `order_num`, `path`, `component`, `query`, `is_frame`, `is_cache`, `menu_type`, `visible`, `status`, `perms`, `icon`, `create_by`,`remark`) 
				   VALUES ('角色删除', @roleManageId, 4, '', '', '', 1, 0, 'F', '0', '0', 'manage:role:remove', '#', 'system', '');
SELECT @roleManageBDelId := LAST_INSERT_ID();
INSERT INTO `manage_menu` ( `menu_name`, `parent_id`, `order_num`, `path`, `component`, `query`, `is_frame`, `is_cache`, `menu_type`, `visible`, `status`, `perms`, `icon`, `create_by`,`remark`) 
				   VALUES ('角色导出', @roleManageId, 5, '', '', '', 1, 0, 'F', '0', '0', 'manage:role:export', '#', 'system', '');
SELECT @roleManageBExportId := LAST_INSERT_ID();




-- 按钮 岗位管理
INSERT INTO `manage_menu` ( `menu_name`, `parent_id`, `order_num`, `path`, `component`, `query`, `is_frame`, `is_cache`, `menu_type`, `visible`, `status`, `perms`, `icon`, `create_by`,`remark`) 
				   VALUES ('岗位查询', @postManageId, 1, '', '', '', 1, 0, 'F', '0', '0', 'manage:post:query', '#', 'system', '');
SELECT @postManageBQueryId := LAST_INSERT_ID();
INSERT INTO `manage_menu` ( `menu_name`, `parent_id`, `order_num`, `path`, `component`, `query`, `is_frame`, `is_cache`, `menu_type`, `visible`, `status`, `perms`, `icon`, `create_by`,`remark`) 
				   VALUES ('岗位新增', @postManageId, 2, '', '', '', 1, 0, 'F', '0', '0', 'manage:post:add', '#', 'system', '');
SELECT @postManageBAddId := LAST_INSERT_ID();
INSERT INTO `manage_menu` ( `menu_name`, `parent_id`, `order_num`, `path`, `component`, `query`, `is_frame`, `is_cache`, `menu_type`, `visible`, `status`, `perms`, `icon`, `create_by`,`remark`) 
				   VALUES ('岗位修改', @postManageId, 3, '', '', '', 1, 0, 'F', '0', '0', 'manage:post:edit', '#', 'system', '');
SELECT @postManageBUpdateId := LAST_INSERT_ID();
INSERT INTO `manage_menu` ( `menu_name`, `parent_id`, `order_num`, `path`, `component`, `query`, `is_frame`, `is_cache`, `menu_type`, `visible`, `status`, `perms`, `icon`, `create_by`,`remark`) 
				   VALUES ('岗位删除', @postManageId, 4, '', '', '', 1, 0, 'F', '0', '0', 'manage:post:remove', '#', 'system', '');
SELECT @postManageBDelId := LAST_INSERT_ID();
INSERT INTO `manage_menu` ( `menu_name`, `parent_id`, `order_num`, `path`, `component`, `query`, `is_frame`, `is_cache`, `menu_type`, `visible`, `status`, `perms`, `icon`, `create_by`,`remark`) 
				   VALUES ('岗位导出', @postManageId, 5, '', '', '', 1, 0, 'F', '0', '0', 'manage:post:export', '#', 'system', '');
SELECT @postManageBExportId := LAST_INSERT_ID();






-- 按钮 管理用户
INSERT INTO `manage_menu` ( `menu_name`, `parent_id`, `order_num`, `path`, `component`, `query`, `is_frame`, `is_cache`, `menu_type`, `visible`, `status`, `perms`, `icon`, `create_by`,`remark`) 
				   VALUES ('用户查询', @manageUserId, 1, '', '', '', 1, 0, 'F', '0', '0', 'manage:user:query', '#', 'system', '');
SELECT @userManageBQueryId := LAST_INSERT_ID();
INSERT INTO `manage_menu` ( `menu_name`, `parent_id`, `order_num`, `path`, `component`, `query`, `is_frame`, `is_cache`, `menu_type`, `visible`, `status`, `perms`, `icon`, `create_by`,`remark`) 
				   VALUES ('用户新增', @manageUserId, 2, '', '', '', 1, 0, 'F', '0', '0', 'manage:user:add', '#', 'system', '');
SELECT @userManageBAddId := LAST_INSERT_ID();
INSERT INTO `manage_menu` ( `menu_name`, `parent_id`, `order_num`, `path`, `component`, `query`, `is_frame`, `is_cache`, `menu_type`, `visible`, `status`, `perms`, `icon`, `create_by`,`remark`) 
				   VALUES ('用户修改', @manageUserId, 3, '', '', '', 1, 0, 'F', '0', '0', 'manage:user:edit', '#', 'system', '');
SELECT @userManageBUpdateId := LAST_INSERT_ID();
INSERT INTO `manage_menu` ( `menu_name`, `parent_id`, `order_num`, `path`, `component`, `query`, `is_frame`, `is_cache`, `menu_type`, `visible`, `status`, `perms`, `icon`, `create_by`,`remark`) 
				   VALUES ('用户删除', @manageUserId, 4, '', '', '', 1, 0, 'F', '0', '0', 'manage:user:remove', '#', 'system', '');
SELECT @userManageBDelId := LAST_INSERT_ID();
INSERT INTO `manage_menu` ( `menu_name`, `parent_id`, `order_num`, `path`, `component`, `query`, `is_frame`, `is_cache`, `menu_type`, `visible`, `status`, `perms`, `icon`, `create_by`,`remark`) 
				   VALUES ('用户导出', @manageUserId, 5, '', '', '', 1, 0, 'F', '0', '0', 'manage:user:export', '#', 'system', '');
SELECT @userManageBExportId := LAST_INSERT_ID();
INSERT INTO `manage_menu` ( `menu_name`, `parent_id`, `order_num`, `path`, `component`, `query`, `is_frame`, `is_cache`, `menu_type`, `visible`, `status`, `perms`, `icon`, `create_by`,`remark`) 
				   VALUES ('用户导入', @manageUserId, 6, '', '', '', 1, 0, 'F', '0', '0', 'manage:user:import', '#', 'system', '');
SELECT @userManageBImportId := LAST_INSERT_ID();
INSERT INTO `manage_menu` ( `menu_name`, `parent_id`, `order_num`, `path`, `component`, `query`, `is_frame`, `is_cache`, `menu_type`, `visible`, `status`, `perms`, `icon`, `create_by`,`remark`) 
				   VALUES ('重置密码', @manageUserId, 7, '', '', '', 1, 0, 'F', '0', '0', 'manage:user:resetPwd', '#', 'system', '');
SELECT @userManageBPwdResetId := LAST_INSERT_ID();


-- 按钮 在线用户
INSERT INTO `manage_menu` ( `menu_name`, `parent_id`, `order_num`, `path`, `component`, `query`, `is_frame`, `is_cache`, `menu_type`, `visible`, `status`, `perms`, `icon`, `create_by`,`remark`) 
				   VALUES ('在线查询', @userOnlineManageId, 1, '#', '', '', 1, 0, 'F', '0', '0', 'manage:userOnline:query', '#', 'system', '');
SELECT @userOnlineManageBQueryId := LAST_INSERT_ID();
INSERT INTO `manage_menu` ( `menu_name`, `parent_id`, `order_num`, `path`, `component`, `query`, `is_frame`, `is_cache`, `menu_type`, `visible`, `status`, `perms`, `icon`, `create_by`,`remark`) 
				   VALUES ('批量强退', @userOnlineManageId, 2, '#', '', '', 1, 0, 'F', '0', '0', 'manage:userOnline:batchLogout', '#', 'system', '');
SELECT @userOnlineManageBForcedReturnBatchId := LAST_INSERT_ID();
INSERT INTO `manage_menu` ( `menu_name`, `parent_id`, `order_num`, `path`, `component`, `query`, `is_frame`, `is_cache`, `menu_type`, `visible`, `status`, `perms`, `icon`, `create_by`,`remark`) 
				   VALUES ('单条强退', @userOnlineManageId, 3, '#', '', '', 1, 0, 'F', '0', '0', 'manage:userOnline:forceLogout', '#', 'system', '');
SELECT @userOnlineManageBForcedReturnOneId := LAST_INSERT_ID();



-- 按钮 日志管理
-- 按钮 登陆日志
INSERT INTO `manage_menu` ( `menu_name`, `parent_id`, `order_num`, `path`, `component`, `query`, `is_frame`, `is_cache`, `menu_type`, `visible`, `status`, `perms`, `icon`, `create_by`,`remark`) 
				   VALUES ('登录查询', @loginLogManageId, 1, '#', '', '', 1, 0, 'F', '0', '0', 'manage:logUserLogin:query', '#', 'system', '');
SELECT @loginLogManageBQueryId := LAST_INSERT_ID();
INSERT INTO `manage_menu` ( `menu_name`, `parent_id`, `order_num`, `path`, `component`, `query`, `is_frame`, `is_cache`, `menu_type`, `visible`, `status`, `perms`, `icon`, `create_by`,`remark`) 
				   VALUES ('登录删除', @loginLogManageId, 2, '#', '', '', 1, 0, 'F', '0', '0', 'manage:logUserLogin:remove', '#', 'system', '');
SELECT @loginLogManageBDelId := LAST_INSERT_ID();
INSERT INTO `manage_menu` ( `menu_name`, `parent_id`, `order_num`, `path`, `component`, `query`, `is_frame`, `is_cache`, `menu_type`, `visible`, `status`, `perms`, `icon`, `create_by`,`remark`) 
				   VALUES ('日志导出', @loginLogManageId, 3, '#', '', '', 1, 0, 'F', '0', '0', 'manage:logUserLogin:export', '#', 'system', '');
SELECT @loginLogManageBExportId := LAST_INSERT_ID();

-- 按钮 操作日志
INSERT INTO `manage_menu` ( `menu_name`, `parent_id`, `order_num`, `path`, `component`, `query`, `is_frame`, `is_cache`, `menu_type`, `visible`, `status`, `perms`, `icon`, `create_by`,`remark`) 
				   VALUES ('操作查询', @operLogManageId, 1, '#', '', '', 1, 0, 'F', '0', '0', 'manage:logUserOper:query', '#', 'system', '');
SELECT @operLogManageBQueryId := LAST_INSERT_ID();
INSERT INTO `manage_menu` ( `menu_name`, `parent_id`, `order_num`, `path`, `component`, `query`, `is_frame`, `is_cache`, `menu_type`, `visible`, `status`, `perms`, `icon`, `create_by`,`remark`) 
				   VALUES ('操作删除', @operLogManageId, 2, '#', '', '', 1, 0, 'F', '0', '0', 'manage:logUserOper:remove', '#', 'system', '');
SELECT @operLogManageBDelId := LAST_INSERT_ID();
INSERT INTO `manage_menu` ( `menu_name`, `parent_id`, `order_num`, `path`, `component`, `query`, `is_frame`, `is_cache`, `menu_type`, `visible`, `status`, `perms`, `icon`, `create_by`,`remark`) 
				   VALUES ('日志导出', @operLogManageId, 4, '#', '', '', 1, 0, 'F', '0', '0', 'manage:logUserOper:export', '#', 'system', '');
SELECT @operLogManageBExportId := LAST_INSERT_ID();


-- ----------------------------
-- Table structure for manage_role_menu
-- ----------------------------
DROP TABLE IF EXISTS `manage_role_menu`;
CREATE TABLE `manage_role_menu`  (
  `id` 				bigint(0) 		NOT NULL AUTO_INCREMENT 	COMMENT '主键ID',
  `role_id` 		bigint(0) 		NOT NULL 		COMMENT '角色ID',
  `menu_id` 		bigint(0) 		NOT NULL 		COMMENT '菜单ID',
  
  `remark` 			varchar(500)  		NOT NULL DEFAULT '' 						COMMENT '备注',
  `create_time` 	datetime 			NOT NULL DEFAULT CURRENT_TIMESTAMP 			COMMENT '创建时间',
  PRIMARY KEY (`id`) ,
  UNIQUE KEY `uk_role_menu` (`role_id`,`menu_id`)  COMMENT '角色-菜单 唯一'
) ENGINE = InnoDB  COMMENT = '角色和菜单关联表' ;

-- ----------------------------
-- Records of manage_role_menu
-- ----------------------------

-- 菜单目录
INSERT INTO `manage_role_menu` (`role_id`, `menu_id`) VALUES (@roleCommonId, @officialWebsiteId);
INSERT INTO `manage_role_menu` (`role_id`, `menu_id`) VALUES (@roleCommonId, @systemManageId);
INSERT INTO `manage_role_menu` (`role_id`, `menu_id`) VALUES (@roleCommonId, @dictManageId);
INSERT INTO `manage_role_menu` (`role_id`, `menu_id`) VALUES (@roleCommonId, @configManageId);
INSERT INTO `manage_role_menu` (`role_id`, `menu_id`) VALUES (@roleCommonId, @noticeManageId);
INSERT INTO `manage_role_menu` (`role_id`, `menu_id`) VALUES (@roleCommonId, @menuManageId);
INSERT INTO `manage_role_menu` (`role_id`, `menu_id`) VALUES (@roleCommonId, @deptManageId);
INSERT INTO `manage_role_menu` (`role_id`, `menu_id`) VALUES (@roleCommonId, @roleManageId);
INSERT INTO `manage_role_menu` (`role_id`, `menu_id`) VALUES (@roleCommonId, @postManageId);
INSERT INTO `manage_role_menu` (`role_id`, `menu_id`) VALUES (@roleCommonId, @userManageId);
INSERT INTO `manage_role_menu` (`role_id`, `menu_id`) VALUES (@roleCommonId, @manageUserId);
INSERT INTO `manage_role_menu` (`role_id`, `menu_id`) VALUES (@roleCommonId, @userOnlineManageId);
INSERT INTO `manage_role_menu` (`role_id`, `menu_id`) VALUES (@roleCommonId, @logManageId);
INSERT INTO `manage_role_menu` (`role_id`, `menu_id`) VALUES (@roleCommonId, @loginLogManageId);
INSERT INTO `manage_role_menu` (`role_id`, `menu_id`) VALUES (@roleCommonId, @operLogManageId);
INSERT INTO `manage_role_menu` (`role_id`, `menu_id`) VALUES (@roleCommonId, @expandUnitId);
INSERT INTO `manage_role_menu` (`role_id`, `menu_id`) VALUES (@roleCommonId, @systemMonitorId);
INSERT INTO `manage_role_menu` (`role_id`, `menu_id`) VALUES (@roleCommonId, @systemMonitorDruidId);
INSERT INTO `manage_role_menu` (`role_id`, `menu_id`) VALUES (@roleCommonId, @systemMonitorCacheId);

-- 按钮 字典管理
INSERT INTO `manage_role_menu` (`role_id`, `menu_id`) VALUES (@roleCommonId, @dictManageBQueryId);
INSERT INTO `manage_role_menu` (`role_id`, `menu_id`) VALUES (@roleCommonId, @dictManageBAddId);
INSERT INTO `manage_role_menu` (`role_id`, `menu_id`) VALUES (@roleCommonId, @dictManageBUpdateId);
INSERT INTO `manage_role_menu` (`role_id`, `menu_id`) VALUES (@roleCommonId, @dictManageBDelId);
INSERT INTO `manage_role_menu` (`role_id`, `menu_id`) VALUES (@roleCommonId, @dictManageBExportId);
-- 按钮 参数管理
INSERT INTO `manage_role_menu` (`role_id`, `menu_id`) VALUES (@roleCommonId, @configManageBQueryId);
INSERT INTO `manage_role_menu` (`role_id`, `menu_id`) VALUES (@roleCommonId, @configManageBAddId);
INSERT INTO `manage_role_menu` (`role_id`, `menu_id`) VALUES (@roleCommonId, @configManageBUpdateId);
INSERT INTO `manage_role_menu` (`role_id`, `menu_id`) VALUES (@roleCommonId, @configManageBDelId);
INSERT INTO `manage_role_menu` (`role_id`, `menu_id`) VALUES (@roleCommonId, @configManageBExportId);
-- 按钮 公告管理
INSERT INTO `manage_role_menu` (`role_id`, `menu_id`) VALUES (@roleCommonId, @noticeManageBQueryId);
INSERT INTO `manage_role_menu` (`role_id`, `menu_id`) VALUES (@roleCommonId, @noticeManageBAddId);
INSERT INTO `manage_role_menu` (`role_id`, `menu_id`) VALUES (@roleCommonId, @noticeManageBUpdateId);
INSERT INTO `manage_role_menu` (`role_id`, `menu_id`) VALUES (@roleCommonId, @noticeManageBDelId);
-- 按钮 菜单管理
INSERT INTO `manage_role_menu` (`role_id`, `menu_id`) VALUES (@roleCommonId, @menuManageBQueryId);
INSERT INTO `manage_role_menu` (`role_id`, `menu_id`) VALUES (@roleCommonId, @menuManageBAddId);
INSERT INTO `manage_role_menu` (`role_id`, `menu_id`) VALUES (@roleCommonId, @menuManageBUpdateId);
INSERT INTO `manage_role_menu` (`role_id`, `menu_id`) VALUES (@roleCommonId, @menuManageBDelId);
-- 按钮 部门管理
INSERT INTO `manage_role_menu` (`role_id`, `menu_id`) VALUES (@roleCommonId, @deptManageBQueryId);
INSERT INTO `manage_role_menu` (`role_id`, `menu_id`) VALUES (@roleCommonId, @deptManageBAddId);
INSERT INTO `manage_role_menu` (`role_id`, `menu_id`) VALUES (@roleCommonId, @deptManageBUpdateId);
INSERT INTO `manage_role_menu` (`role_id`, `menu_id`) VALUES (@roleCommonId, @deptManageBDelId);
-- 按钮 角色管理
INSERT INTO `manage_role_menu` (`role_id`, `menu_id`) VALUES (@roleCommonId, @roleManageBQueryId);
INSERT INTO `manage_role_menu` (`role_id`, `menu_id`) VALUES (@roleCommonId, @roleManageBAddId);
INSERT INTO `manage_role_menu` (`role_id`, `menu_id`) VALUES (@roleCommonId, @roleManageBUpdateId);
INSERT INTO `manage_role_menu` (`role_id`, `menu_id`) VALUES (@roleCommonId, @roleManageBDelId);
INSERT INTO `manage_role_menu` (`role_id`, `menu_id`) VALUES (@roleCommonId, @roleManageBExportId);
-- 按钮 岗位管理
INSERT INTO `manage_role_menu` (`role_id`, `menu_id`) VALUES (@roleCommonId, @postManageBQueryId);
INSERT INTO `manage_role_menu` (`role_id`, `menu_id`) VALUES (@roleCommonId, @postManageBAddId);
INSERT INTO `manage_role_menu` (`role_id`, `menu_id`) VALUES (@roleCommonId, @postManageBUpdateId);
INSERT INTO `manage_role_menu` (`role_id`, `menu_id`) VALUES (@roleCommonId, @postManageBDelId);
INSERT INTO `manage_role_menu` (`role_id`, `menu_id`) VALUES (@roleCommonId, @postManageBExportId);
-- 按钮 管理用户
INSERT INTO `manage_role_menu` (`role_id`, `menu_id`) VALUES (@roleCommonId, @userManageBQueryId);
INSERT INTO `manage_role_menu` (`role_id`, `menu_id`) VALUES (@roleCommonId, @userManageBAddId);
INSERT INTO `manage_role_menu` (`role_id`, `menu_id`) VALUES (@roleCommonId, @userManageBUpdateId);
INSERT INTO `manage_role_menu` (`role_id`, `menu_id`) VALUES (@roleCommonId, @userManageBDelId);
INSERT INTO `manage_role_menu` (`role_id`, `menu_id`) VALUES (@roleCommonId, @userManageBExportId);
INSERT INTO `manage_role_menu` (`role_id`, `menu_id`) VALUES (@roleCommonId, @userManageBImportId);
INSERT INTO `manage_role_menu` (`role_id`, `menu_id`) VALUES (@roleCommonId, @userManageBPwdResetId);
-- 按钮 在线用户
INSERT INTO `manage_role_menu` (`role_id`, `menu_id`) VALUES (@roleCommonId, @userOnlineManageBQueryId);
INSERT INTO `manage_role_menu` (`role_id`, `menu_id`) VALUES (@roleCommonId, @userOnlineManageBForcedReturnBatchId);
INSERT INTO `manage_role_menu` (`role_id`, `menu_id`) VALUES (@roleCommonId, @userOnlineManageBForcedReturnOneId);

-- 按钮 日志管理
-- 按钮 登陆日志
INSERT INTO `manage_role_menu` (`role_id`, `menu_id`) VALUES (@roleCommonId, @loginLogManageBQueryId);
INSERT INTO `manage_role_menu` (`role_id`, `menu_id`) VALUES (@roleCommonId, @loginLogManageBDelId);
INSERT INTO `manage_role_menu` (`role_id`, `menu_id`) VALUES (@roleCommonId, @loginLogManageBExportId);
-- 按钮 操作日志
INSERT INTO `manage_role_menu` (`role_id`, `menu_id`) VALUES (@roleCommonId, @operLogManageBQueryId);
INSERT INTO `manage_role_menu` (`role_id`, `menu_id`) VALUES (@roleCommonId, @operLogManageBDelId);
INSERT INTO `manage_role_menu` (`role_id`, `menu_id`) VALUES (@roleCommonId, @operLogManageBExportId);


## 更新数据后可以在开启外键约束
SET FOREIGN_KEY_CHECKS = 1;
