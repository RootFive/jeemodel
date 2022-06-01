package com.jeemodel.unit.manage.service;

import java.util.List;

import com.baomidou.mybatisplus.extension.service.IService;
import com.jeemodel.unit.manage.bean.dto.system.ConfigListReq;
import com.jeemodel.unit.manage.bean.entity.Config;

/**
 * 参数配置 服务层
 * 
 * @author Rootfive
 */
public interface IConfigService extends IService<Config>{

	/**
	 * 根据键名查询参数配置信息
	 * 
	 * @param configKey 参数键名
	 * @return 参数键值
	 */
	public String selectConfigByKey(String configKey);

	/**
	 * 获取验证码开关
	 * 
	 * @return true开启，false关闭
	 */
	public boolean selectCaptchaOnOff();

	/**
	 * 查询参数配置列表
	 * 
	 * @param listReq 参数配置信息
	 * @return 参数配置集合
	 */
	public List<Config> selectConfigList(ConfigListReq listReq);

	/**
	 * 新增参数配置
	 * 
	 * @param config 参数配置信息
	 * @return 结果
	 */
	public int insertConfig(Config config);

	/**
	 * 修改参数配置
	 * 
	 * @param config 参数配置信息
	 * @return 结果
	 */
	public int updateConfig(Config config);

	/**
	 * 批量删除参数信息
	 * 
	 * @param ids 需要删除的参数ID
	 * @return 结果
	 */
	public void deleteConfigByIds(Long[] ids);

	/**
	 * 加载参数缓存数据
	 */
	public void loadingConfigCache();

	/**
	 * 清空参数缓存数据
	 */
	public void clearConfigCache();

	/**
	 * 重置参数缓存数据
	 */
	public void resetConfigCache();

	/**
	 * 校验参数键名是否唯一
	 * 
	 * @param config 参数信息
	 * @return 结果
	 */
	public String checkConfigKeyUnique(Config config);
}
