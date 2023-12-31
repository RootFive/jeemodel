package com.jeemodel.solution.redis.config;

import org.springframework.cache.annotation.CachingConfigurerSupport;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.script.DefaultRedisScript;
import org.springframework.data.redis.serializer.Jackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.fasterxml.jackson.annotation.PropertyAccessor;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.jsontype.impl.LaissezFaireSubTypeValidator;
import com.jeemodel.core.utils.JacksonToObjectMapperUtils;

/**
 * redis配置
 * 
 * @author Rootfive
 */
@Configuration
@EnableCaching
public class RedisConfig extends CachingConfigurerSupport {
	
	
	@Bean
	public RedisTemplate<Object, Object> redisTemplate(RedisConnectionFactory factory) {
		RedisTemplate<Object, Object> redisTemplate = new RedisTemplate<>();
		redisTemplate.setConnectionFactory(factory);

		StringRedisSerializer stringRedisSerializer = new StringRedisSerializer();
		//默认情况下，RedisTemplate 使用该数据列化方式:org.springframework.data.redis.serializer.JdkSerializationRedisSerializer ，
		Jackson2JsonRedisSerializer<?> jackson2JsonRedisSerializer = new Jackson2JsonRedisSerializer<>(Object.class);

		

		ObjectMapper objectMapper = JacksonToObjectMapperUtils.newObjectMapper();
		objectMapper.setVisibility(PropertyAccessor.ALL, JsonAutoDetect.Visibility.ANY);
		objectMapper.activateDefaultTyping(LaissezFaireSubTypeValidator.instance, ObjectMapper.DefaultTyping.NON_FINAL,JsonTypeInfo.As.PROPERTY);
		
		// 添加此配置，解决返回的json字符串中的某个键值对无法在实体类中找到对应属性的问题
//		objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
		
		jackson2JsonRedisSerializer.setObjectMapper(objectMapper);
		
		
		// 使用StringRedisSerializer来序列化和反序列化redis的key值
		redisTemplate.setKeySerializer(stringRedisSerializer);
	    redisTemplate.setValueSerializer(jackson2JsonRedisSerializer);
	    // Hash的key也采用StringRedisSerializer的序列化方式
	    redisTemplate.setHashKeySerializer(stringRedisSerializer);
	    redisTemplate.setHashValueSerializer(jackson2JsonRedisSerializer);
		

	    redisTemplate.afterPropertiesSet();
		return redisTemplate;
	}

	
	
	
	
	@Bean
	public DefaultRedisScript<Long> limitScript() {
		DefaultRedisScript<Long> redisScript = new DefaultRedisScript<>();
		redisScript.setScriptText(limitScriptText());
		redisScript.setResultType(Long.class);
		return redisScript;
	}

	/**
	 * 限流脚本
	 */
	private String limitScriptText() {
		return "local key = KEYS[1]\n" + "local count = tonumber(ARGV[1])\n" + "local time = tonumber(ARGV[2])\n"
				+ "local current = redis.call('get', key);\n" + "if current and tonumber(current) > count then\n"
				+ "    return tonumber(current);\n" + "end\n" + "current = redis.call('incr', key)\n"
				+ "if tonumber(current) == 1 then\n" + "    redis.call('expire', key, time)\n" + "end\n"
				+ "return tonumber(current);";
	}
}
