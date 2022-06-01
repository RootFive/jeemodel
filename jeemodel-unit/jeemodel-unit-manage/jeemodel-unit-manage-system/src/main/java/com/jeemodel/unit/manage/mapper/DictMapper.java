package com.jeemodel.unit.manage.mapper;

import org.apache.ibatis.annotations.Mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.jeemodel.unit.manage.core.bean.entity.Dict;

/**
 * 字典表 数据层
 * 
 * @author Rootfive
 */
@Mapper
public interface DictMapper extends BaseMapper<Dict>{

}
