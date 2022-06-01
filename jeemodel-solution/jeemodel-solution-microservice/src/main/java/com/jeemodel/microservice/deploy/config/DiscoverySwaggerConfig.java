package com.jeemodel.microservice.deploy.config;

import java.util.Collections;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.HandlerMapping;

import com.google.common.base.Function;
import com.google.common.base.Optional;
import com.google.common.base.Predicate;
import com.nepxion.discovery.common.constant.DiscoveryConstant;
import com.nepxion.discovery.common.constant.DiscoverySwaggerConstant;

import springfox.documentation.RequestHandler;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.oas.annotations.EnableOpenApi;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.service.Parameter;
import springfox.documentation.service.SecurityScheme;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spi.service.contexts.SecurityContext;
import springfox.documentation.spring.web.plugins.Docket;

/**     
 * @author: Rootfive	2021年8月15日
 * @Description: 兼容Swagger3.0,原discovery使用低版本Swagger
 * @see com.nepxion.discovery.plugin.admincenter.configuration.SwaggerConfiguration        
 */
@SuppressWarnings("deprecation")
@Configuration
@EnableOpenApi
@ConditionalOnClass(HandlerMapping.class)
public class DiscoverySwaggerConfig {
	
	public static final String BASE_PACKAGE = "com.nepxion.discovery.plugin.admincenter.endpoint";
	public static final String DESCRIPTION = "Admin Center Restful APIs";

	@Value("${" + DiscoverySwaggerConstant.SWAGGER_SERVICE_BASE_GROUP + ":【Admin】-【微服务】}")
	private String baseGroup;

	@Value("${" + DiscoverySwaggerConstant.SWAGGER_SERVICE_SCAN_GROUP + ":}")
	private String scanGroup;

	@Value("${" + DiscoverySwaggerConstant.SWAGGER_SERVICE_SCAN_PACKAGES + ":}")
	private String scanPackages;

	@Value("${" + DiscoveryConstant.SPRING_APPLICATION_NAME + "}")
	private String serviceName;

	@Value("${" + DiscoverySwaggerConstant.SWAGGER_SERVICE_DESCRIPTION + ":" + DESCRIPTION + "}")
	private String description;

//	@Value("${" + DiscoverySwaggerConstant.SWAGGER_SERVICE_VERSION + ":" + DiscoveryConstant.DISCOVERY_VERSION + "}")
	@Value("${version}")
	private String version;

	@Value("${" + DiscoverySwaggerConstant.SWAGGER_SERVICE_LICENSE_NAME + ":" + DiscoverySwaggerConstant.SWAGGER_SERVICE_LICENSE_NAME_VALUE + "}")
	private String licenseName;

	@Value("${" + DiscoverySwaggerConstant.SWAGGER_SERVICE_LICENSE_URL + ":" + DiscoverySwaggerConstant.SWAGGER_SERVICE_LICENSE_URL_VALUE + "}")
	private String licenseUrl;

	@Value("${" + DiscoverySwaggerConstant.SWAGGER_SERVICE_CONTACT_NAME + ":Rootfive}")
	private String contactName;

	@Value("${" + DiscoverySwaggerConstant.SWAGGER_SERVICE_CONTACT_URL + ":https://gitee.com/jeemodel}")
	private String contactUrl;

	@Value("${" + DiscoverySwaggerConstant.SWAGGER_SERVICE_CONTACT_EMAIL + ":2236067977@qq.com}")
	private String contactEmail;

	@Value("${" + DiscoverySwaggerConstant.SWAGGER_SERVICE_TERMSOFSERVICE_URL + ":http://www.jeemodel.com}")
	private String termsOfServiceUrl;

	@Autowired(required = false)
	private List<Parameter> swaggerHeaderParameters;

	@Autowired(required = false)
	private List<SecurityScheme> swaggerSecuritySchemes;

	@Autowired(required = false)
	private List<SecurityContext> swaggerSecurityContexts;

	@Bean("discoveryDocket")
	public Docket discoveryDocket() {
//		return new Docket(DocumentationType.OAS_30).groupName(baseGroup).apiInfo(apiInfo()).select().apis(SwaggerConfiguration.basePackage(BASE_PACKAGE)) // 扫描该包下的所有需要在Swagger中展示的API，@ApiIgnore注解标注的除外
		return new Docket(DocumentationType.OAS_30).groupName(baseGroup).apiInfo(apiInfo()).select().apis(RequestHandlerSelectors.basePackage(BASE_PACKAGE)) // 扫描该包下的所有需要在Swagger中展示的API，@ApiIgnore注解标注的除外
				.paths(PathSelectors.any()).build().globalOperationParameters(swaggerHeaderParameters).securitySchemes(swaggerSecuritySchemes)
				.securityContexts(swaggerSecurityContexts != null ? swaggerSecurityContexts : Collections.emptyList());
	}

	@Bean("scanDocket")
	@ConditionalOnProperty(name = DiscoverySwaggerConstant.SWAGGER_SERVICE_SCAN_GROUP)
	public Docket scanDocket() {
//		return new Docket(DocumentationType.OAS_30).groupName(scanGroup).apiInfo(apiInfo()).select().apis(SwaggerConfiguration.basePackage(scanPackages)) // 扫描该包下的所有需要在Swagger中展示的API，@ApiIgnore注解标注的除外
		return new Docket(DocumentationType.OAS_30).groupName(scanGroup).apiInfo(apiInfo()).select().apis(RequestHandlerSelectors.basePackage(scanPackages)) // 扫描该包下的所有需要在Swagger中展示的API，@ApiIgnore注解标注的除外
				.paths(PathSelectors.any()).build().globalOperationParameters(swaggerHeaderParameters).securitySchemes(swaggerSecuritySchemes)
				.securityContexts(swaggerSecurityContexts != null ? swaggerSecurityContexts : Collections.emptyList());
	}

	private ApiInfo apiInfo() {
		return new ApiInfoBuilder()
				//服务名
				.title(serviceName)
				//接口描述
				.description(description)
				//版本
				.version(version)
				//开源协议
				.license(licenseName)
				//开源协议地址
				.licenseUrl(licenseUrl)
				//作者，联系地址、联系Email
				.contact(new Contact(contactName, contactUrl, contactEmail))
				//Term地址
				.termsOfServiceUrl(termsOfServiceUrl).build();
	}

	public static Predicate<RequestHandler> basePackage(String basePackage) {
		return new Predicate<RequestHandler>() {
			@Override
			public boolean apply(RequestHandler input) {
				return declaringClass(input).transform(handlerPackage(basePackage)).or(true);
			}
		};
	}

	private static Function<Class<?>, Boolean> handlerPackage(String basePackage) {
		return new Function<Class<?>, Boolean>() {
			@Override
			public Boolean apply(Class<?> input) {
				String[] subPackages = basePackage.split(DiscoveryConstant.SEPARATE);
				for (String subPackage : subPackages) {
					boolean matched = input.getPackage().getName().startsWith(subPackage.trim());
					if (matched) {
						return true;
					}
				}

				return false;
			}
		};
	}

	private static Optional<? extends Class<?>> declaringClass(RequestHandler input) {
		return Optional.fromNullable(input.declaringClass());
	}
}