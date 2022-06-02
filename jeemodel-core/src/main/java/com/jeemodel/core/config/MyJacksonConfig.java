package com.jeemodel.core.config;

import org.springframework.boot.autoconfigure.jackson.Jackson2ObjectMapperBuilderCustomizer;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.Ordered;
import org.springframework.http.converter.json.Jackson2ObjectMapperBuilder;

import com.jeemodel.core.utils.ObjectMapperUtils;

/**
 */
@Configuration
public class MyJacksonConfig implements Jackson2ObjectMapperBuilderCustomizer, Ordered {

	
	@Override
	public int getOrder() {
		return 1;
	}

	@Override
	public void customize(Jackson2ObjectMapperBuilder jacksonObjectMapperBuilder) {
		ObjectMapperUtils.customize(jacksonObjectMapperBuilder);
		
	}
}
