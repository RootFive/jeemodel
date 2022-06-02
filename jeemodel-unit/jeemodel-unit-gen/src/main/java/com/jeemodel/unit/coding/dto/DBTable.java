package com.jeemodel.unit.coding.dto;

import java.time.LocalDateTime;

import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;

import lombok.Data;
import lombok.EqualsAndHashCode;



/**
 * 
 * @author Rootfive 2021-1-12	 联系方式: QQ群：2236067977  
 * <p>数据库 元数据 TABLES表</p>
 * <blockquote><pre>
 *  eg:此表示MySQL原生库：information_schema.TABLES，主要说明如下
 *     	字段				含义
 *     Table_catalog	数据表登记目录
 *     Table_schema		数据表所属的数据库名
 *     Table_name	表名称
 *     Table_type	表类型[system view|base table]
 *     Engine	使用的数据库引擎[MyISAM|CSV|InnoDB]
 *     Version	版本，默认值10
 *     Row_format	行格式[Compact|Dynamic|Fixed]
 *     Table_rows	表里所存多少行数据
 *     Avg_row_length	平均行长度
 *     Data_length	数据长度
 *     Max_data_length	最大数据长度
 *     Index_length	索引长度
 *     Data_free	空间碎片
 *     Auto_increment	做自增主键的自动增量当前值
 *     Create_time	表的创建时间
 *     Update_time	表的更新时间
 *     Check_time	表的检查时间
 *     Table_collation	表的字符校验编码集
 *     Checksum	校验和
 *     Create_options	创建选项
 *     Table_comment	表的注释、备注
 * </pre></blockquote><p>
 * @since   JeeModel.0.0
 */
@TableName(value = "TABLES",schema="information_schema")
@Data
@EqualsAndHashCode(callSuper = false)
public class DBTable extends DBTableSchema{
	
	/** 表的注释、备注 */
	private String tableComment;
	
	/** 创建时间 */
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	protected LocalDateTime createTime;
	
	/** 更新时间 */
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	protected LocalDateTime updateTime ;
}
