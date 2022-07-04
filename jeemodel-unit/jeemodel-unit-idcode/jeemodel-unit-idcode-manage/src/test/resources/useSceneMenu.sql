# 第一部分：jeemodel_gen数据库创建【代码生产模块】业务表
-- 启用 jeemodel_gen 数据库
USE jeemodel_gen;

-- 更新数据前 禁用外键约束
SET FOREIGN_KEY_CHECKS = 0;

-- 菜单 SQL

set @expandUnitId = '';
SELECT @expandUnitId:=id FROM `manage_menu`  WHERE  menu_name = '拓展功能';
-- 菜单 排序
set @lastOrderNum = '';
SELECT @lastOrderNum:=order_num FROM `manage_menu`  WHERE  parent_id =@expandUnitId  ORDER BY order_num desc LIMIT 1;

insert into manage_menu (menu_name, parent_id, order_num, path, component, is_frame, is_cache, menu_type, visible, status, perms, icon, create_by, create_time, update_by, update_time, remark)
values('统一标识编码使用场景', @expandUnitId, @lastOrderNum +1, 'useScene', 'unit/idcode/useScene/index', 1, 0, 'C', '0', '0', 'idcode:useScene:list', '#', 'admin', sysdate(), '', null, '统一标识编码使用场景菜单');




-- 按钮父菜单ID
SELECT @parentId := LAST_INSERT_ID();

-- 按钮 SQL
insert into manage_menu (menu_name, parent_id, order_num, path, component, is_frame, is_cache, menu_type, visible, status, perms, icon, create_by, create_time, update_by, update_time, remark)
values('统一标识编码使用场景查询', @parentId, '1',  '#', '', 1, 0, 'F', '0', '0', 'idcode:useScene:query',        '#', 'admin', sysdate(), '', null, '');

insert into manage_menu (menu_name, parent_id, order_num, path, component, is_frame, is_cache, menu_type, visible, status, perms, icon, create_by, create_time, update_by, update_time, remark)
values('统一标识编码使用场景新增', @parentId, '2',  '#', '', 1, 0, 'F', '0', '0', 'idcode:useScene:add',          '#', 'admin', sysdate(), '', null, '');

insert into manage_menu (menu_name, parent_id, order_num, path, component, is_frame, is_cache, menu_type, visible, status, perms, icon, create_by, create_time, update_by, update_time, remark)
values('统一标识编码使用场景修改', @parentId, '3',  '#', '', 1, 0, 'F', '0', '0', 'idcode:useScene:edit',         '#', 'admin', sysdate(), '', null, '');

insert into manage_menu (menu_name, parent_id, order_num, path, component, is_frame, is_cache, menu_type, visible, status, perms, icon, create_by, create_time, update_by, update_time, remark)
values('统一标识编码使用场景删除', @parentId, '4',  '#', '', 1, 0, 'F', '0', '0', 'idcode:useScene:remove',       '#', 'admin', sysdate(), '', null, '');

insert into manage_menu (menu_name, parent_id, order_num, path, component, is_frame, is_cache, menu_type, visible, status, perms, icon, create_by, create_time, update_by, update_time, remark)
values('统一标识编码使用场景导出', @parentId, '5',  '#', '', 1, 0, 'F', '0', '0', 'idcode:useScene:export',       '#', 'admin', sysdate(), '', null, '');



-- 更新数据后可以在开启外键约束
SET FOREIGN_KEY_CHECKS = 1;
