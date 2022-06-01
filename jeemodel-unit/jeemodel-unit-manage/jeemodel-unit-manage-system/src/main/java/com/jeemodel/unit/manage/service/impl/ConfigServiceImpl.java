package com.jeemodel.unit.manage.service.impl;

import java.util.Collection;
import java.util.List;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.extension.conditions.query.LambdaQueryChainWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.jeemodel.bean.exception.type.CheckException;
import com.jeemodel.core.utils.DataTypeConvertUtils;
import com.jeemodel.core.utils.StringUtils;
import com.jeemodel.solution.redis.core.cache.RedisCacheHelper;
import com.jeemodel.unit.manage.bean.dto.system.ConfigListReq;
import com.jeemodel.unit.manage.bean.entity.Config;
import com.jeemodel.unit.manage.core.constant.ManageConstants;
import com.jeemodel.unit.manage.core.constant.UserConstants;
import com.jeemodel.unit.manage.mapper.ConfigMapper;
import com.jeemodel.unit.manage.service.IConfigService;

/**
 * 参数配置 服务层实现
 * 
 * @author Rootfive
 */
@Service
public class ConfigServiceImpl extends ServiceImpl<ConfigMapper, Config> implements IConfigService {

	@Autowired
	private ConfigMapper configMapper;

	@Autowired
	private RedisCacheHelper redisCacheHelper;

	/**
	 * 项目启动时，初始化参数到缓存
	 */
	@PostConstruct
	public void init() {
		loadingConfigCache();
	}


	/**
	 * 根据键名查询参数配置信息
	 * 
	 * @param configKey 参数key
	 * @return 参数键值
	 */
	@Override
	public String selectConfigByKey(String configKey) {
		String configValue = DataTypeConvertUtils.toStr(redisCacheHelper.getObject(getCacheKey(configKey)));
		if (StringUtils.isNotEmpty(configValue)) {
			return configValue;
		}
		Config retConfig =lambdaQuery().eq(Config::getConfigKey, configKey).one();
		if (StringUtils.isNotNull(retConfig)) {
			redisCacheHelper.setObject(getCacheKey(configKey), retConfig.getConfigValue());
			return retConfig.getConfigValue();
		}
		return StringUtils.EMPTY;
	}

	/**
	 * 获取验证码开关
	 * 
	 * @return true开启，false关闭
	 */
	@Override
	public boolean selectCaptchaOnOff() {
		String captchaOnOff = selectConfigByKey("sys.account.captchaOnOff");
		if (StringUtils.isEmpty(captchaOnOff)) {
			return true;
		}
		return DataTypeConvertUtils.toBool(captchaOnOff);
	}

	/**
	 * 查询参数配置列表
	 * 
	 * @param config 参数配置信息
	 * @return 参数配置集合
	 */
	@Override
	public List<Config> selectConfigList(ConfigListReq listReq) {
		LambdaQueryChainWrapper<Config> lambdaQuery = lambdaQuery();
		lambdaQuery.like(StringUtils.isNotBlank(listReq.getConfigName()), Config::getConfigName, listReq.getConfigName());
		lambdaQuery.eq(StringUtils.isNotBlank(listReq.getConfigType()), Config::getConfigType, listReq.getConfigType());
		lambdaQuery.like(StringUtils.isNotBlank(listReq.getConfigKey()), Config::getConfigKey, listReq.getConfigKey());
		lambdaQuery.between( listReq.getBeginTime()!= null && listReq.getEndTime()!= null , Config::getCreateTime, listReq.getBeginTime(), listReq.getEndTime());
		
		return lambdaQuery.list();
		
	}

	/**
	 * 新增参数配置
	 * 
	 * @param config 参数配置信息
	 * @return 结果
	 */
	@Override
	public int insertConfig(Config config) {
		int row = baseMapper.insert(config);
		if (row > 0) {
			redisCacheHelper.setObject(getCacheKey(config.getConfigKey()), config.getConfigValue());
		}
		return row;
	}

	/**
	 * 修改参数配置
	 * 
	 * @param config 参数配置信息
	 * @return 结果
	 */
	@Override
	public int updateConfig(Config config) {
		int row = configMapper.updateById(config);
		if (row > 0) {
			redisCacheHelper.setObject(getCacheKey(config.getConfigKey()), config.getConfigValue());
		}
		return row;
	}

	/**
	 * 批量删除参数信息
	 * 
	 * @param ids 需要删除的参数ID
	 * @return 结果
	 */
	@Override
	public void deleteConfigByIds(Long[] ids) {
		for (Long id : ids) {
			Config config = getById(id);
			if (StringUtils.equals(UserConstants.YES, config.getConfigType())) {
				throw new CheckException(String.format("内置参数【%1$s】不能删除 ", config.getConfigKey()));
			}
			configMapper.deleteById(id);
			redisCacheHelper.deleteObject(getCacheKey(config.getConfigKey()));
		}
	}

	/**
	 * 加载参数缓存数据
	 */
	@Override
	public void loadingConfigCache() {
		List<Config> configsList = lambdaQuery().list();
		for (Config config : configsList) {
			redisCacheHelper.setObject(getCacheKey(config.getConfigKey()), config.getConfigValue());
		}
	}

	/**
	 * 清空参数缓存数据
	 */
	@Override
	public void clearConfigCache() {
		Collection<String> keys = redisCacheHelper.keys(ManageConstants.manage_config_KEY + "*");
		redisCacheHelper.deleteObjects(keys);
	}

	/**
	 * 重置参数缓存数据
	 */
	@Override
	public void resetConfigCache() {
		clearConfigCache();
		loadingConfigCache();
	}

	/**
	 * 校验参数键名是否唯一
	 * 
	 * @param config 参数配置信息
	 * @return 结果
	 */
	@Override
	public String checkConfigKeyUnique(Config config) {
		Long id = StringUtils.isNull(config.getId()) ? -1L : config.getId();
		Config info = lambdaQuery().eq(Config::getConfigKey, config.getConfigKey()).one();;
		if (StringUtils.isNotNull(info) && info.getId().longValue() != id.longValue()) {
			return UserConstants.NOT_UNIQUE;
		}
		return UserConstants.UNIQUE;
	}

	/**
	 * 设置cache key
	 * 
	 * @param configKey 参数键
	 * @return 缓存键key
	 */
	private String getCacheKey(String configKey) {
		return ManageConstants.manage_config_KEY + configKey;
	}
}
