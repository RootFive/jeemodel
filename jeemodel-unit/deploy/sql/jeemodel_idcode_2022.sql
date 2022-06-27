-- ----------------------------
-- Table structure for idcode_use_scene
-- ----------------------------
DROP TABLE IF EXISTS `idcode_use_scene`;
CREATE TABLE `idcode_use_scene`  (
  `id` 				bigint 			NOT NULL AUTO_INCREMENT 	COMMENT '主键ID',
  `scene_name` 		varchar(30)  	NOT NULL 					COMMENT '统一标识编码使用场景-名称',
  `scene_code` 		char(2)  		NOT NULL 					COMMENT '统一标识编码使用场景-编码（36进制，最大1295）',
  `uid_length` 		tinyint(1) 		NOT NULL			 		COMMENT '统一标识编码-长度（3-6）',
  `uid_serial` 		bigint  		NOT NULL DEFAULT 0 			COMMENT '统一标识编码序列号',
  
  `remark` 			varchar(500)  		NOT NULL DEFAULT '' 						COMMENT '备注',
  `create_by` 		varchar(64)  		NOT NULL DEFAULT '' 						COMMENT '创建者',
  `create_time` 	datetime 			NOT NULL DEFAULT CURRENT_TIMESTAMP 			COMMENT '创建时间',
  `status` 			char(1)  			NOT NULL DEFAULT '0' 						COMMENT '状态（0正常 1停用）',
  `update_by` 		varchar(64)  		NOT NULL DEFAULT '' 						COMMENT '更新者',
  `update_time` 	datetime 			DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP 	COMMENT '更新时间',
  `del_flag` 		char(1)  			NOT NULL DEFAULT '0' 						COMMENT '删除标志（0代表存在 2代表删除）',
  `del_time` 		datetime 			DEFAULT NULL 								COMMENT '删除时间',
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE KEY `uk_code` (`scene_code`) COMMENT '统一标识编码使用场景-编码唯一'
) ENGINE = InnoDB  COMMENT = '统一标识编码使用场景表' ;


INSERT INTO `idcode_use_scene` ( `scene_name`, `scene_code`, `uid_length`,`remark` ) VALUES( '上市公司编码', '01', 4 , '闲捞财上市公司编码');