package com.jeemodel.solution.redis.core.cache;

import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.TimeUnit;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.BoundSetOperations;
import org.springframework.data.redis.core.HashOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.util.CollectionUtils;

import com.jeemodel.base.annotation.HelpService;
import com.jeemodel.bean.exception.type.UtilsException;

import lombok.extern.slf4j.Slf4j;

/**
 * spring redis 工具类
 *
 * @author Rootfive
 **/
@Slf4j
@SuppressWarnings(value = { "unchecked", "rawtypes" })
@HelpService
public class RedisCacheHelper {
	
	@Autowired
	public RedisTemplate redisTemplate;
	
	
	/**
	 * 判断key是否存在
	 * @param key 键
	 * @return true 存在 false不存在
	 */
	public boolean hasKey(final String key) {
		try {
			return redisTemplate.hasKey(key);
		} catch (Exception e) {
			log.error("Redis服务异常,hasKey:",e);
			throw new UtilsException("Redis服务异常："+e.getMessage());
		}
	}
	

	/**
	 * 缓存基本的对象，Integer、String、实体类等
	 *
	 * @param key   缓存的键值
	 * @param value 缓存的值
	 */
	public <T> void setObject(final String key, final T value) {
		try {
			redisTemplate.opsForValue().set(key, value);
		} catch (Exception e) {
			log.error("Redis服务异常,hasKey:",e);
			throw new UtilsException("Redis服务异常："+e.getMessage());
		}
	}

	/**
	 * 缓存基本的对象，Integer、String、实体类等
	 *
	 * @param key      缓存的键值
	 * @param value    缓存的值
	 * @param timeout  时间
	 * @param timeUnit 时间颗粒度
	 */
	public <T> void setObject(final String key, final T value, final Integer timeout, final TimeUnit timeUnit) {
		if (timeout!=null && timeout.intValue() > 0) {
			try {
				redisTemplate.opsForValue().set(key, value, timeout, timeUnit);
			} catch (Exception e) {
				log.error("Redis服务异常,hasKey:",e);
				throw new UtilsException("Redis服务异常："+e.getMessage());
			}
		}else {
			setObject(key, value);
		}
	}

	
	/**
	 * 根据key 获取过期时间
	 * @param key 键 不能为null
	 * @return 时间(秒) 返回0代表为永久有效
	 */
	public long getExpire(final String key) {
		try {
			return redisTemplate.getExpire(key, TimeUnit.SECONDS);
		} catch (Exception e) {
			log.error("Redis服务异常,hasKey:",e);
			throw new UtilsException("Redis服务异常："+e.getMessage());
		}
	}
	
	/**
	 * 设置有效时间
	 *
	 * @param key     Redis键
	 * @param timeout 超时时间
	 * @return true=设置成功；false=设置失败
	 */
	public boolean expire(final String key, final long timeout) {
		if (timeout> 0) {
			return expire(key, timeout, TimeUnit.SECONDS);
		}
		return false;
		
	}

	/**
	 * 设置有效时间
	 *
	 * @param key     Redis键
	 * @param timeout 超时时间
	 * @param unit    时间单位
	 * @return true=设置成功；false=设置失败
	 */
	public boolean expire(final String key, final long timeout, final TimeUnit unit) {
		try {
			return redisTemplate.expire(key, timeout, unit);
		} catch (Exception e) {
			log.error("Redis服务异常,hasKey:",e);
			throw new UtilsException("Redis服务异常："+e.getMessage());
		}
	}

	/**
	 * 获得缓存的基本对象。
	 *
	 * @param key 缓存键值
	 * @return 缓存键值对应的数据
	 */
	public <T> T getObject(final String key) {
		try {
			ValueOperations<String, T> operation = redisTemplate.opsForValue();
			return operation.get(key);
		} catch (Exception e) {
			log.error("Redis服务异常,hasKey:",e);
			throw new UtilsException("Redis服务异常："+e.getMessage());
		}
	}

	/**
	 * 删除单个对象
	 *
	 * @param key
	 */
	public boolean deleteObject(final String key) {
		try {
			return redisTemplate.delete(key);
		} catch (Exception e) {
			log.error("Redis服务异常,hasKey:",e);
			throw new UtilsException("Redis服务异常："+e.getMessage());
		}
	}
	
	
	/**
	 * 删除缓存
	 * @param key 可以传一个值 或多个
	 */
	public void deleteObject(final String... key) {
		if (key != null && key.length > 0) {
			if (key.length == 1) {
				deleteObject(key[0]);
			} else {
				deleteObjects(CollectionUtils.arrayToList(key));
			}
		}
	}

	/**
	 * 删除多个对象
	 *
	 * @param collection 多个对象
	 * @return
	 */
	public long deleteObjects(final Collection keys) {
		try {
			return redisTemplate.delete(keys);
		} catch (Exception e) {
			log.error("Redis服务异常,hasKey:",e);
			throw new UtilsException("Redis服务异常："+e.getMessage());
		}
	}

	
	
	
	/**
	 * 递增
	 * @param key 键
	 * @param delta 要增加几(大于0)
	 * @return
	 */
	public long incr(final String key, final long delta) {
		if (delta < 0) {
			throw new UtilsException("递增因子必须大于0");
		}
		try {
			return redisTemplate.opsForValue().increment(key, delta);
		} catch (Exception e) {
			log.error("Redis服务异常,hasKey:",e);
			throw new UtilsException("Redis服务异常："+e.getMessage());
		}
	}
	
	/**
	 * 递减
	 * @param key 键
	 * @param delta 要减少几(大于0)
	 * @return
	 */
	public long decr(final String key, final long delta) {
		if (delta < 0) {
			throw new UtilsException("递减因子必须大于0");
		}
		try {
			return redisTemplate.opsForValue().increment(key, -delta);
		} catch (Exception e) {
			log.error("Redis服务异常,hasKey:",e);
			throw new UtilsException("Redis服务异常："+e.getMessage());
		}
	}
	
	
	
	
	
	// ================================Map=================================

	/**
	 * 缓存Map
	 *
	 * @param key
	 * @param dataMap
	 */
	public <T> void setMap(final String key, final Map<String, T> dataMap) {
		if (dataMap == null) {
			throw new UtilsException("缓存数据不能为空");
		}
		try {
			redisTemplate.opsForHash().putAll(key, dataMap);
		} catch (Exception e) {
			log.error("Redis服务异常,hasKey:",e);
			throw new UtilsException("Redis服务异常："+e.getMessage());
		}
	}

	/**
	 * 向一张hash表中放入数据,如果不存在将创建
	 *
	 * @param key   Redis键
	 * @param hashKey  Hash键
	 * @param value 值
	 */
	public <T> boolean setMapValue(final String key, final String hashKey, final T value) {
		try {
			redisTemplate.opsForHash().put(key, hashKey, value);
			return true;
		} catch (Exception e) {
			log.error("Redis服务异常,hasKey:",e);
			return false;
		}
	}
	
	
	
	/**
	 * 向一张hash表中放入数据,如果不存在将创建
	 * @param key 键
	 * @param hashKey 项
	 * @param value 值
	 * @param time 时间(秒)  注意:如果已存在的hash表有时间,这里将会替换原有的时间
	 * @return true 成功 false失败
	 */
	public boolean setMapValue(final String key, final String hashKey, final Object value, final long time) {
		try {
			redisTemplate.opsForHash().put(key, hashKey, value);
			if (time > 0) {
				expire(key, time);
			}
			return true;
		} catch (Exception e) {
			log.error("Redis服务异常,hasKey:",e);
			return false;
		}
	}
	
	
	
	

	/**
	 * 往Hash中存入数据(多个key-value)
	 * @param key 键
	 * @param map 对应多个键值
	 * @return true 成功 false 失败
	 */
	public boolean setMapMultiKeyValue(final String key, final Map<String, Object> map) {
		try {
			redisTemplate.opsForHash().putAll(key, map);
			return true;
		} catch (Exception e) {
			log.error("Redis服务异常,hasKey:",e);
			return false;
		}
	}
	

	/**
	 * HashSet 并设置时间
	 * @param key 键
	 * @param map 对应多个键值
	 * @param time 时间(秒)
	 * @return true成功 false失败
	 */
	public boolean setMapMultiKeyValue(final String key, final Map<String, Object> map, final long time) {
		try {
			redisTemplate.opsForHash().putAll(key, map);
			if (time > 0) {
				expire(key, time);
			}
			return true;
		} catch (Exception e) {
			log.error("Redis服务异常,hasKey:",e);
			return false;
		}
	}
	
	
	
	/**
	 * 获得缓存的Map对应的所有键值
	 *
	 * @param key
	 * @return
	 */
	public <T> Map<String, T> getMap(final String key) {
		try {
			return redisTemplate.opsForHash().entries(key);
		} catch (Exception e) {
			log.error("Redis服务异常,hasKey:",e);
			throw new UtilsException("Redis服务异常："+e.getMessage());
		}
	}
	
	
	/**
	 * 获取Hash中的数据
	 *
	 * @param key  Redis键
	 * @param hashKey Hash键
	 * @return Hash中的对象
	 */
	public <T> T getMapValue(final String key, final String hashKey) {
		try {
			HashOperations<String, String, T> opsForHash = redisTemplate.opsForHash();
			return opsForHash.get(key, hashKey);
		} catch (Exception e) {
			log.error("Redis服务异常,hasKey:",e);
			throw new UtilsException("Redis服务异常："+e.getMessage());
		}
	}
	

	/**
	 * 获取多个Hash中的数据
	 *
	 * @param key   Redis键
	 * @param hKeys Hash键集合
	 * @return Hash对象集合
	 */
	public <T> List<T> getMapMultiValue(final String key, final Collection<String> hKeys) {
		try {
			return redisTemplate.opsForHash().multiGet(key, hKeys);
		} catch (Exception e) {
			log.error("Redis服务异常,hasKey:",e);
			throw new UtilsException("Redis服务异常："+e.getMessage());
		}
	}	
	

	/**
	 * 删除Hash中的数据
	 * 
	 * @param key
	 * @param hashKeys
	 */
	public void deleteMapValue(final String key, final Object ... hashKeys) {
		try {
			HashOperations hashOperations = redisTemplate.opsForHash();
			hashOperations.delete(key, hashKeys);
		} catch (Exception e) {
			log.error("Redis服务异常,hasKey:",e);
			throw new UtilsException("Redis服务异常："+e.getMessage());
		}
	}
	
	
	/**
	 * 判断hash表中是否有该项的值
	 * @param key 键 不能为null
	 * @param hashKey 项 不能为null
	 * @return true 存在 false不存在
	 */
	public boolean mapHasKey(final String key, final Object hashKey) {
		try {
			return redisTemplate.opsForHash().hasKey(key, hashKey);
		} catch (Exception e) {
			log.error("Redis服务异常,hasKey:",e);
			throw new UtilsException("Redis服务异常："+e.getMessage());
		}
	}
	

	/**
	 * hash递增 如果不存在,就会创建一个 并把新增后的值返回
	 * @param key 键
	 * @param hashKey 项
	 * @param delta 要增加几(大于0)
	 * @return
	 */
	public double mapIncr(final String key, final String hashKey, final double delta) {
		try {
			return redisTemplate.opsForHash().increment(key, hashKey, delta);
		} catch (Exception e) {
			log.error("Redis服务异常,hasKey:",e);
			throw new UtilsException("Redis服务异常："+e.getMessage());
		}
	}
	
	/**
	 * hash递减
	 * @param key 键
	 * @param hashKey 项
	 * @param delta 要减少多少(大于0)
	 * @return
	 */
	public double hdecr(final String key, final String hashKey, final double delta) {
		try {
			return redisTemplate.opsForHash().increment(key, hashKey, -delta);
		} catch (Exception e) {
			log.error("Redis服务异常,hasKey:",e);
			throw new UtilsException("Redis服务异常："+e.getMessage());
		}
	}
	
	
	// ================================Map=================================
	
	
	
	
	// ============================set=============================
	/**
	 * 缓存Set
	 *
	 * @param key     缓存键值
	 * @param dataSet 缓存的数据
	 * @return 缓存数据的对象
	 */
	public <T> BoundSetOperations<String, T> setSet(final String key, final Set<T> dataSet) {
		try {
			BoundSetOperations<String, T> setOperation = redisTemplate.boundSetOps(key);
			Iterator<T> it = dataSet.iterator();
			while (it.hasNext()) {
				setOperation.add(it.next());
			}
			return setOperation;
		} catch (Exception e) {
			log.error("Redis服务异常,hasKey:",e);
			throw new UtilsException("Redis服务异常："+e.getMessage());
		}
	}
	
	/**
	 * 将数据放入set缓存
	 * @param key 键
	 * @param values 值 可以是多个
	 * @return 成功个数
	 */
	public long setSet(final String key, final Object... values) {
		try {
			return redisTemplate.opsForSet().add(key, values);
		} catch (Exception e) {
			log.error("Redis服务异常,hasKey:",e);
			throw new UtilsException("Redis服务异常："+e.getMessage());
		}
	}

	/**
	 * 将set数据放入缓存
	 * @param key 键
	 * @param time 时间(秒)
	 * @param values 值 可以是多个
	 * @return 成功个数
	 */
	public long setSet(final String key, final long time, final Object... values) {
		boolean  add = false;
		Long count = null;
		try {
			count = redisTemplate.opsForSet().add(key, values);
			add = true;
		} catch (Exception e) {
			log.error("Redis服务异常,setSet:",e);
			throw new UtilsException("Redis服务异常："+e.getMessage());
		}
		
		if (add && time > 0) {
			expire(key, time);
		}
		return count;
	}
	
	
	
	

	/**
	 * 获得缓存的set(获取Set中的所有值)
	 *
	 * @param key
	 * @return
	 */
	public <T> Set<T> getSet(final String key) {
		try {
			return redisTemplate.opsForSet().members(key);
		} catch (Exception e) {
			log.error("Redis服务异常,getSet:",e);
			throw new UtilsException("Redis服务异常："+e.getMessage());
		}
	}
	
	/**
	 * 获取set缓存的长度
	 * @param key 键
	 * @return
	 */
	public long getSetSize(final String key) {
		try {
			return redisTemplate.opsForSet().size(key);
		} catch (Exception e) {
			log.error("Redis服务异常,getSetSize:",e);
			throw new UtilsException("Redis服务异常："+e.getMessage());
		}
	}
	
	
	/**
	 * 根据value从一个set中查询,是否存在
	 * @param key 键
	 * @param value 值
	 * @return true 存在 false不存在
	 */
	public boolean setHasValue(final String key, final Object value) {
		try {
			return redisTemplate.opsForSet().isMember(key, value);
		} catch (Exception e) {
			log.error("Redis服务异常,setHasValue:",e);
			throw new UtilsException("Redis服务异常："+e.getMessage());
		}
	}
	
	/**
	 * 移除值为value的
	 * @param key 键
	 * @param values 值 可以是多个
	 * @return 移除的个数
	 */
	public long setRemove(final String key, final Object... values) {
		try {
			Long count = redisTemplate.opsForSet().remove(key, values);
			return count == null ? 0 : count;
		} catch (Exception e) {
			log.error("Redis服务异常,setRemove:",e);
			throw new UtilsException("Redis服务异常："+e.getMessage());
		}
	}
	
	
	
	// ============================set=============================
	
	
	// ===============================list=================================
	
	/**
	 * 缓存List数据
	 *
	 * @param key      缓存的键值
	 * @param dataList 待缓存的List数据
	 * @return 缓存的对象
	 */
	public <T> long setList(final String key, final List<T> dataList) {
		try {
			Long count = redisTemplate.opsForList().rightPushAll(key, dataList);
			return count == null ? 0 : count;
		} catch (Exception e) {
			log.error("Redis服务异常,setList:",e);
			throw new UtilsException("Redis服务异常："+e.getMessage());
		}
	}
	
	/**
	 * 将list放入缓存
	 * @param key 键
	 * @param value 值
	 * @param time 时间(秒)
	 * @return
	 */
	public boolean setList(final String key, final List<Object> value, final long time) {
		boolean  rightPushAll = false;
		
		try {
			redisTemplate.opsForList().rightPushAll(key, value);
			rightPushAll =  true;
		} catch (Exception e) {
			log.error("Redis服务异常,setList:",e);
			throw new UtilsException("Redis服务异常："+e.getMessage());
		}
		
		if (rightPushAll && time > 0) {
			expire(key, time);
		}
		
		return rightPushAll;
	}	
	
	/**
	 * 将list放入缓存
	 * @param key 键
	 * @param value 值
	 * @return
	 */
	public boolean setList(final String key, final Object value) {
		try {
			redisTemplate.opsForList().rightPush(key, value);
			return true;
		} catch (Exception e) {
			log.error("Redis服务异常,setList:",e);
			throw new UtilsException("Redis服务异常："+e.getMessage());
		}
	}
	
	/**
	 * 将list放入缓存并刷新缓存
	 * @param key 键
	 * @param value 值
	 * @param time 时间(秒)
	 * @return
	 */
	public boolean setList(final String key, final Object value, final long time) {
		boolean  rightPush = false;
		try {
			redisTemplate.opsForList().rightPush(key, value);
			rightPush = true;
		} catch (Exception e) {
			log.error("Redis服务异常,setList:",e);
			throw new UtilsException("Redis服务异常："+e.getMessage());
		}
		
		if (rightPush && time > 0) {
			expire(key, time);
		}
		
		return rightPush;
	}
	

	/**
	 * 根据索引修改list中的某条数据
	 * @param key 键
	 * @param index 索引
	 * @param value 值
	 * @return
	 */
	public boolean updateListIndex(final String key, final long index, final Object value) {
		try {
			redisTemplate.opsForList().set(key, index, value);
			return  true;
		} catch (Exception e) {
			log.error("Redis服务异常,updateListIndex:",e);
			throw new UtilsException("Redis服务异常："+e.getMessage());
		}
	}	
	
	
	/**
	 * 移除N个值为value
	 * @param key 键
	 * @param count 移除多少个
	 * @param value 值
	 * @return 移除的个数
	 */
	public long listRemove(final String key, final long count, final Object value) {
		try {
			Long remove = redisTemplate.opsForList().remove(key, count, value);
			return remove;
		} catch (Exception e) {
			log.error("Redis服务异常,listRemove:",e);
			throw new UtilsException("Redis服务异常："+e.getMessage());
		}
	}
	
	
	/**
	 * 获得缓存的list对象
	 *
	 * @param key 缓存的键值
	 * @return 缓存键值对应的数据
	 */
	public <T> List<T> getList(final String key) {
		try {
			return redisTemplate.opsForList().range(key, 0, -1);
		} catch (Exception e) {
			log.error("Redis服务异常,getList:",e);
			throw new UtilsException("Redis服务异常："+e.getMessage());
		}
	} 

	/**
	 * 获取list缓存的内容
	 * @param key 键
	 * @param start 开始
	 * @param end 结束  0 到 -1代表所有值
	 * @return
	 */
	public <T> List<T> getList(final String key, final long start, final long end) {
		try {
			return redisTemplate.opsForList().range(key, start, end);
		} catch (Exception e) {
			log.error("Redis服务异常,getList:",e);
			throw new UtilsException("Redis服务异常："+e.getMessage());
		}
	}
	
	/**
	 * 通过索引 获取list中的值
	 * @param key 键
	 * @param index 索引  index>=0时， 0 表头，1 第二个元素，依次类推；index<0时，-1，表尾，-2倒数第二个元素，依次类推
	 * @return
	 */
	public <T> Object getListIndex(final String key, final long index) {
		try {
			return redisTemplate.opsForList().index(key, index);
		} catch (Exception e) {
			log.error("Redis服务异常,getListIndex:",e);
			throw new UtilsException("Redis服务异常："+e.getMessage());
		}
	}
	

	/**
	 * 获取list缓存的长度
	 * @param key 键
	 * @return
	 */
	public long getListSize(final String key) {
		try {
			return redisTemplate.opsForList().size(key);
		} catch (Exception e) {
			log.error("Redis服务异常,getListSize:",e);
			throw new UtilsException("Redis服务异常："+e.getMessage());
		}
	}
	
	/**
	 * 获得缓存的基本对象列表
	 *
	 * @param pattern 字符串前缀
	 * @return 对象列表
	 */
	public Collection<String> keys(final String pattern) {
		try {
			return redisTemplate.keys(pattern);
		} catch (Exception e) {
			log.error("Redis服务异常,keys:",e);
			throw new UtilsException("Redis服务异常："+e.getMessage());
		}
	}
}
