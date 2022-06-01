package com.jeemodel.unit.manage.core.config;

import java.util.HashMap;
import java.util.Map;
import javax.servlet.DispatcherType;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.jeemodel.core.filter.RepeatableFilter;
import com.jeemodel.core.utils.StringUtils;
import com.jeemodel.unit.manage.core.filter.XssFilter;

/**
 * Filter配置
 *
 * @author Rootfive
 */
@Configuration
public class FilterConfig {
	
	@Value("${jeemodel.unit.manage.core.xss.excludes}")
	private String excludes;

	@Value("${jeemodel.unit.manage.core.xss.urlPatterns}")
	private String urlPatterns;

	@SuppressWarnings({ "rawtypes", "unchecked" })
	@Bean
	@ConditionalOnProperty(value = "jeemodel.unit.manage.core.xss.enabled", havingValue = "true")
	public FilterRegistrationBean xssFilterRegistration() {
		FilterRegistrationBean registration = new FilterRegistrationBean();
		registration.setDispatcherTypes(DispatcherType.REQUEST);
		registration.setFilter(new XssFilter());
		registration.addUrlPatterns(StringUtils.split(urlPatterns, ","));
		registration.setName("xssFilter");
		registration.setOrder(FilterRegistrationBean.HIGHEST_PRECEDENCE);
		Map<String, String> initParameters = new HashMap<String, String>();
		initParameters.put("excludes", excludes);
		registration.setInitParameters(initParameters);
		return registration;
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	@Bean
	public FilterRegistrationBean someFilterRegistration() {
		FilterRegistrationBean registration = new FilterRegistrationBean();
		registration.setFilter(new RepeatableFilter());
		registration.addUrlPatterns("/*");
		registration.setName("repeatableFilter");
		registration.setOrder(FilterRegistrationBean.LOWEST_PRECEDENCE);
		return registration;
	}

}
