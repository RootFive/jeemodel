# 第一部分：jeemodel_idcode【业务库】业务表
-- 启用 jeemodel_idcode 数据库
USE jeemodel_idcode;

-- 更新数据前 禁用外键约束
SET FOREIGN_KEY_CHECKS = 0;


-- 创建 场景标识表
DROP TABLE IF EXISTS `idcode_use_scene`;
CREATE TABLE `idcode_use_scene`  (
  `id` 				bigint 			NOT NULL AUTO_INCREMENT 	COMMENT '主键ID',
  `scene_name` 		varchar(30)  	NOT NULL 					COMMENT '场景标识-名称',
  `scene_code` 		char(3)  		NOT NULL 					COMMENT '场景标识-编码（35进制，最大42874）',
  `uid_length` 		tinyint(1) 		NOT NULL			 		COMMENT '场景标识-长度（3-6）',
  `uid_serial` 		bigint  		NOT NULL DEFAULT 0 			COMMENT '场景标识-序列号',
  
  `remark` 			varchar(500)  		NOT NULL DEFAULT '' 						COMMENT '备注',
  `create_by` 		varchar(64)  		NOT NULL DEFAULT '' 						COMMENT '创建者',
  `create_time` 	datetime 			NOT NULL DEFAULT CURRENT_TIMESTAMP 			COMMENT '创建时间',
  `status` 			char(1)  			NOT NULL DEFAULT '0' 						COMMENT '状态（0正常 1停用）',
  `update_by` 		varchar(64)  		NOT NULL DEFAULT '' 						COMMENT '更新者',
  `update_time` 	datetime 			DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP 	COMMENT '更新时间',
  `del_flag` 		char(1)  			NOT NULL DEFAULT '0' 						COMMENT '删除标志（0代表存在 2代表删除）',
  `del_time` 		datetime 			DEFAULT NULL 								COMMENT '删除时间',
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE KEY `uk_code` (`scene_code`) COMMENT '场景标识-标识唯一'
) ENGINE = InnoDB  COMMENT = '场景标识表' ;


-- 更新数据后可以在开启外键约束
SET FOREIGN_KEY_CHECKS = 1;



# 第二部分：jeemodel_manage【管理系统】 菜单信息
-- 启用 jeemodel_manage 数据库
USE jeemodel_manage;

-- 更新数据前 禁用外键约束
SET FOREIGN_KEY_CHECKS = 0;


-- 菜单 SQL

set @expandUnitId = '';
SELECT @expandUnitId:=id FROM `manage_menu`  WHERE  menu_name = '拓展功能';
-- 菜单 排序
set @lastOrderNum = '';
SELECT @lastOrderNum:=order_num FROM `manage_menu`  WHERE  parent_id =@expandUnitId  ORDER BY order_num desc LIMIT 1;

insert into manage_menu (menu_name, parent_id, order_num, path, component, is_frame, is_cache, menu_type, visible, status, perms, icon, create_by, create_time, update_by, update_time, remark)
values('场景标识', @expandUnitId, @lastOrderNum +1, 'useScene', 'unit/idcode/useScene/index', 1, 0, 'C', '0', '0', 'idcode:useScene:list', '#', 'admin', sysdate(), '', null, '场景标识菜单');




-- 按钮父菜单ID
SELECT @parentId := LAST_INSERT_ID();

-- 按钮 SQL
insert into manage_menu (menu_name, parent_id, order_num, path, component, is_frame, is_cache, menu_type, visible, status, perms, icon, create_by, create_time, update_by, update_time, remark)
values('场景标识-查询', @parentId, '1',  '#', '', 1, 0, 'F', '0', '0', 'idcode:useScene:query',        '#', 'admin', sysdate(), '', null, '');

insert into manage_menu (menu_name, parent_id, order_num, path, component, is_frame, is_cache, menu_type, visible, status, perms, icon, create_by, create_time, update_by, update_time, remark)
values('场景标识-新增', @parentId, '2',  '#', '', 1, 0, 'F', '0', '0', 'idcode:useScene:add',          '#', 'admin', sysdate(), '', null, '');

insert into manage_menu (menu_name, parent_id, order_num, path, component, is_frame, is_cache, menu_type, visible, status, perms, icon, create_by, create_time, update_by, update_time, remark)
values('场景标识-修改', @parentId, '3',  '#', '', 1, 0, 'F', '0', '0', 'idcode:useScene:edit',         '#', 'admin', sysdate(), '', null, '');

insert into manage_menu (menu_name, parent_id, order_num, path, component, is_frame, is_cache, menu_type, visible, status, perms, icon, create_by, create_time, update_by, update_time, remark)
values('场景标识-删除', @parentId, '4',  '#', '', 1, 0, 'F', '0', '0', 'idcode:useScene:remove',       '#', 'admin', sysdate(), '', null, '');

insert into manage_menu (menu_name, parent_id, order_num, path, component, is_frame, is_cache, menu_type, visible, status, perms, icon, create_by, create_time, update_by, update_time, remark)
values('场景标识-导出', @parentId, '5',  '#', '', 1, 0, 'F', '0', '0', 'idcode:useScene:export',       '#', 'admin', sysdate(), '', null, '');



-- 更新数据后可以在开启外键约束
SET FOREIGN_KEY_CHECKS = 1;
