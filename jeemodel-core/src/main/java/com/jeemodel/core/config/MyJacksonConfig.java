package com.jeemodel.core.config;

import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.Date;
import java.util.TimeZone;

import org.springframework.boot.autoconfigure.jackson.Jackson2ObjectMapperBuilderCustomizer;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.Ordered;
import org.springframework.http.converter.json.Jackson2ObjectMapperBuilder;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.databind.ser.std.DateSerializer;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateDeserializer;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateTimeDeserializer;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalTimeDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateSerializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalTimeSerializer;
import com.jeemodel.core.constant.time.TimeHelper;
import com.jeemodel.core.utils.DateTimeUtils;

/**
 */
@Configuration
public class MyJacksonConfig implements Jackson2ObjectMapperBuilderCustomizer, Ordered {

	
	@Override
	public int getOrder() {
		return 1;
	}

	/**
	 *
	 */
	@Override
	public void customize(Jackson2ObjectMapperBuilder jacksonObjectMapperBuilder) {
		
		// 1、基础设置-序列化
		// 若POJO对象的属性值为null，序列化时不进行显示，等同于：jacksonObjectMapperBuilder.featuresToDisable(SerializationFeature.WRITE_NULL_MAP_VALUES);
		jacksonObjectMapperBuilder.serializationInclusion(JsonInclude.Include.NON_NULL);
		// 若POJO对象的属性值为""，序列化时不进行显示
		jacksonObjectMapperBuilder.serializationInclusion(JsonInclude.Include.NON_EMPTY);
		// value为null时key的正常输出 key：null
//		jacksonObjectMapperBuilder.serializationInclusion(JsonInclude.Include.ALWAYS );
		
		
		// 默认开启，空Collection集合类型输出空JSON串。关闭后取消显示。(已过时)
		// 推荐使用serializationInclusion(JsonInclude.Include.NON_EMPTY);
		//jacksonObjectMapperBuilder.featuresToEnable(SerializationFeature.WRITE_EMPTY_JSON_ARRAYS);		

		// 默认关闭，将char[]数组序列化为String类型。若开启后序列化为JSON数组。
		jacksonObjectMapperBuilder.featuresToEnable(SerializationFeature.WRITE_CHAR_ARRAYS_AS_JSON_ARRAYS);
		
		
		// 2、基础设置-反序列化
		// 默认关闭，当JSON字段为""(EMPTY_STRING)时，解析为普通的POJO对象抛出异常。开启后，该POJO的属性值为null。
		jacksonObjectMapperBuilder.featuresToEnable(DeserializationFeature.ACCEPT_EMPTY_STRING_AS_NULL_OBJECT);

		/*
		 * 默认开启，若POJO中不含有JSON中的属性，则抛出异常。
		 * 关闭后，不解析该字段，而不会抛出异常。反序列化依旧可以成功，
		 * 相当于配置：jacksonObjectMapperBuilder.failOnUnknownProperties(false);
		 */
		jacksonObjectMapperBuilder.featuresToDisable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES);
//		jacksonObjectMapperBuilder.failOnUnknownProperties(true);
		
		
		// 3、基础设置-序列化和反序列化
		// 序列化时的命名策略——小驼峰命名法
		jacksonObjectMapperBuilder.propertyNamingStrategy(PropertyNamingStrategy.LOWER_CAMEL_CASE);
		// 默认关闭，序列化单元素数组时不以数组来输出，当集合Collection或数组一个元素时返回："list":["a"]。开启后，"list":"a"
		// 需要注意，和DeserializationFeature.ACCEPT_SINGLE_VALUE_AS_ARRAY 配套使用，要么都开启，要么都关闭。
		//jacksonObjectMapperBuilder.featuresToEnable(SerializationFeature.WRITE_SINGLE_ELEM_ARRAYS_UNWRAPPED);
		//jacksonObjectMapperBuilder.featuresToEnable(DeserializationFeature.ACCEPT_SINGLE_VALUE_AS_ARRAY);
		
		// 4、开启
		// 默认关闭，在类上使用@JsonRootName(value="rootNode")注解时是否可以包裹Root元素。
		// (https://blog.csdn.net/blueheart20/article/details/52212221)
		//  jacksonObjectMapperBuilder.featuresToEnable(SerializationFeature.WRAP_ROOT_VALUE);
		
		
		// 5、关闭
		// 默认开启：如果一个类没有public的方法或属性时，会导致序列化失败。关闭后，会得到一个空JSON串。
		jacksonObjectMapperBuilder.featuresToDisable(SerializationFeature.FAIL_ON_EMPTY_BEANS);
		
		// 6、时间相关
		// 时区
		jacksonObjectMapperBuilder.timeZone(TimeZone.getTimeZone("Asia/Shanghai"));
		// 默认开启，将Date类型序列化为数字时间戳(毫秒表示)。关闭后，序列化为文本表现形式(2019-10-23T01:58:58.308+0000)
		// 若设置时间格式化。那么均输出格式化的时间类型。
		jacksonObjectMapperBuilder.featuresToDisable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);
		
		// 默认关闭，即以文本(ISO-8601)作为Key，开启后，以时间戳作为Key
//		jacksonObjectMapperBuilder.featuresToEnable(SerializationFeature.WRITE_DATE_KEYS_AS_TIMESTAMPS);
		
		// 针对于Date.class类型，文本格式化
		//Date方式1
//		jacksonObjectMapperBuilder.simpleDateFormat(TimeHelper.DATETIME_FORMAT);
		//Date方式2
		//序列化
		jacksonObjectMapperBuilder.serializerByType(Date.class, new DateSerializer(false, TimeHelper.simpleDateFormat));
		//反序列化
		jacksonObjectMapperBuilder.deserializerByType(Date.class, new JsonDeserializer<Date>() {
			@Override
			public Date deserialize(JsonParser jsonParser, DeserializationContext deserializationContext) throws IOException {
				String date = jsonParser.getText();
				try {
					return DateTimeUtils.parseDate(date);
				} catch (Exception e) {
					throw new RuntimeException(e);
				}
			}
		});
		
		
		
		
		// 针对于JDK新时间类。自定义格式化字符串 方式一：
//		JavaTimeModule javaTimeModule = new JavaTimeModule();
//		// 针对于LocalDateTime.class类型，JDK新时间类。序列化时带有T的问题，自定义格式化字符串
//		javaTimeModule.addSerializer(LocalDateTime.class,new LocalDateTimeSerializer(TimeHelper.DATETIME_FORMATTER));		
//		javaTimeModule.addDeserializer(LocalDateTime.class,	new LocalDateTimeDeserializer(TimeHelper.DATETIME_FORMATTER));
//		
//		// 针对于LocalDate.class类型，JDK新时间类。自定义格式化字符串
//		javaTimeModule.addSerializer(LocalDate.class,new LocalDateSerializer(TimeHelper.DATE_FORMATTER));		
//		javaTimeModule.addDeserializer(LocalDate.class,	new LocalDateDeserializer(TimeHelper.DATE_FORMATTER));
//		
//		// 针对于LocalTime.class类型，JDK新时间类。自定义格式化字符串
//		javaTimeModule.addSerializer(LocalTime.class,new LocalTimeSerializer(TimeHelper.TIME_FORMATTER));		
//		javaTimeModule.addDeserializer(LocalTime.class,	new LocalTimeDeserializer(TimeHelper.TIME_FORMATTER));
//		
//		jacksonObjectMapperBuilder.modules(javaTimeModule);
		
		// 针对于JDK新时间类。自定义格式化字符串 方式二：
		//序列化
		jacksonObjectMapperBuilder.serializerByType(LocalDateTime.class,new LocalDateTimeSerializer(TimeHelper.DATETIME_FORMATTER));
		jacksonObjectMapperBuilder.serializerByType(LocalDate.class, new LocalDateSerializer(TimeHelper.DATE_FORMATTER));
		jacksonObjectMapperBuilder.serializerByType(LocalTime.class, new LocalTimeSerializer(TimeHelper.TIME_FORMATTER));
		
		//反序列化
		jacksonObjectMapperBuilder.deserializerByType(LocalDateTime.class,new LocalDateTimeDeserializer(TimeHelper.DATETIME_FORMATTER));
		jacksonObjectMapperBuilder.deserializerByType(LocalDate.class,new LocalDateDeserializer(TimeHelper.DATE_FORMATTER));
		jacksonObjectMapperBuilder.deserializerByType(LocalTime.class,new LocalTimeDeserializer(TimeHelper.TIME_FORMATTER));


		
		// 7、枚举
		/*
		 * 将 WRITE_ENUMS_USING_INDEX 设置为 true 则序列化为 Enum.ordinal() 的返回值（及该枚举值的索引），这时值是 int 型。 Enum.ordinal() 不可以重写。
		 * 将 WRITE_ENUMS_USING_TO_STRING 设置为 true , 将序列化为 Enum.toString() 的返回值。而 Enum.toString() 方法可以重写。仅当WRITE_ENUMS_USING_INDEX为禁用时(默认禁用)，该配置生效
		 * 两个同时设置为 true 时， WRITE_ENUMS_USING_INDEX 拥有较高的优先级。
		 */
		// 默认禁用，禁用情况下，需考虑WRITE_ENUMS_USING_TO_STRING配置。启用后，ENUM序列化为数字
		//jacksonObjectMapperBuilder.featuresToEnable(SerializationFeature.WRITE_ENUMS_USING_INDEX);		
		// 默认关闭，枚举类型序列化方式，默认情况下使用Enum.name()。开启后，使用Enum.toString()。注：需重写Enum的toString方法;
		jacksonObjectMapperBuilder.featuresToEnable(SerializationFeature.WRITE_ENUMS_USING_TO_STRING);
		
		
		//数字
		/*
		 * 默认关闭，即使用BigDecimal.toString()序列化。开启后，使用BigDecimal.toPlainString序列化，不输出科学计数法的值。
		 * 已弃用：SerializationFeature.WRITE_BIGDECIMAL_AS_PLAIN 
		 * 推荐用：JsonGenerator.Feature.WRITE_BIGDECIMAL_AS_PLAIN配置
		 */
		jacksonObjectMapperBuilder.featuresToEnable(JsonGenerator.Feature.WRITE_BIGDECIMAL_AS_PLAIN);
		// 序列化成json时，将所有的Long变成string，以解决js中的精度丢失。
//		jacksonObjectMapperBuilder.serializerByType(Long.class, ToStringSerializer.instance);
//		jacksonObjectMapperBuilder.serializerByType(Long.TYPE, ToStringSerializer.instance);
		
		
		/**
		 * JsonGenerator.Feature的相关参数（JSON生成器）
		 */
		// 默认关闭，即序列化Number类型及子类为{"amount1":1.1}。开启后，序列化为String类型，即{"amount1":"1.1"}
		/*
		 * 虽然JsonGenerator.Feature.WRITE_NUMBERS_AS_STRINGS已经过期，但是jacksonObjectMapperBuilder.featuresToEnable()参数类型有限制
		 * 所以不能用 JsonWriteFeature.WRITE_NUMBERS_AS_STRINGS)
		 */
//		jacksonObjectMapperBuilder.featuresToEnable(JsonGenerator.Feature.WRITE_NUMBERS_AS_STRINGS);


	}


}
