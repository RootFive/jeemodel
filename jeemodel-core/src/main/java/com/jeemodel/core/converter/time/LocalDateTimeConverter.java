package com.jeemodel.core.converter.time;

import java.io.IOException;
import java.time.LocalDateTime;
import java.time.ZoneOffset;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;

/**
 * 
 * @author Rootfive
 * 将LocalDateTime字段以时间戳的方式返回给前端 添加日期转化类，
 * 并在LocalDateTime字段上添加@JsonSerialize(using = LocalDateTimeConverter.class)注解，如下：
 *--------------------------------------------------------------
 * 		@JsonSerialize(using = LocalDateTimeConverter.class)
 * 		protected LocalDateTime createTime;
 * -------------------------------------------------------------
 * 
 */
public class LocalDateTimeConverter extends JsonSerializer<LocalDateTime> {

    @Override
    public void serialize(LocalDateTime localDateTime, JsonGenerator jsonGenerator, SerializerProvider serializers) throws IOException {
    	jsonGenerator.writeNumber(localDateTime.toInstant(ZoneOffset.of("+8")).toEpochMilli());
    }
}
