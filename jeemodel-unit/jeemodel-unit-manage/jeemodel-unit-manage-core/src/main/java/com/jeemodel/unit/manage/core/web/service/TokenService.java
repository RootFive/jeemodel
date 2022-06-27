package com.jeemodel.unit.manage.core.web.service;

import java.nio.charset.StandardCharsets;
import java.security.Key;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import javax.crypto.SecretKey;
import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.jeemodel.core.utils.BlankUtils;
import com.jeemodel.core.utils.ServletUtils;
import com.jeemodel.core.utils.StringUtils;
import com.jeemodel.core.utils.id.IdUtils;
import com.jeemodel.core.utils.ip.IpUtils;
import com.jeemodel.solution.redis.core.cache.RedisCacheHelper;
import com.jeemodel.unit.manage.core.bean.model.LoginUser;
import com.jeemodel.unit.manage.core.constant.ManageConstants;

import eu.bitwalker.useragentutils.UserAgent;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import lombok.extern.slf4j.Slf4j;

/**
 * token验证处理
 *
 * @author Rootfive
 * 
 * StringBoot 整合JJWT https://www.panziye.com/java/1349.html
 * JJWT的官方github: https://github.com/jwtk/jjwt
 * 
 */
@Slf4j
@Component
public class TokenService {
	// 令牌自定义标识
	@Value("${jeemodel.unit.manage.core.token.header}")
	private String header;

	// 令牌秘钥
	@Value("${jeemodel.unit.manage.core.token.secret}")
	private String secret;

	// 令牌有效期（默认30分钟）
	@Value("${jeemodel.unit.manage.core.token.expireTime}")
	private int expireTime;

	protected static final long MILLIS_SECOND = 1000;

	protected static final long MILLIS_MINUTE = 60 * MILLIS_SECOND;

	private static final Long MILLIS_MINUTE_TEN = 20 * 60 * 1000L;

	@Autowired
	private RedisCacheHelper redisCacheHelper;

	/**
	 * 获取用户身份信息
	 *
	 * @return 用户信息
	 */
	public LoginUser getLoginUser(HttpServletRequest request) {
		// 获取请求携带的令牌
		String token = getToken(request);
		if (StringUtils.isNotEmpty(token)) {
			try {
				Claims claims = parseToken(token);
				// 解析对应的权限以及用户信息
				String userKey = (String) claims.get(ManageConstants.LOGIN_USER_KEY);
				String userCacheKey = getTokenKey(userKey);
				LoginUser user = redisCacheHelper.getObject(userCacheKey);
				return user;
			} catch (Exception e) {
				log.error("getLoginUser find a Exception:",e);
			}
		}
		return null;
	}

	/**
	 * 设置用户身份信息
	 */
	public void setLoginUser(LoginUser loginUser) {
		if (BlankUtils.isNotNull(loginUser) && StringUtils.isNotEmpty(loginUser.getToken())) {
			refreshToken(loginUser);
		}
	}

	/**
	 * 删除用户身份信息
	 */
	public void delLoginUser(String token) {
		if (StringUtils.isNotEmpty(token)) {
			String userKey = getTokenKey(token);
			redisCacheHelper.deleteObject(userKey);
		}
	}

	/**
	 * 创建令牌
	 *
	 * @param loginUser 用户信息
	 * @return 令牌
	 */
	public String createToken(LoginUser loginUser) {
		String token = IdUtils.fastUUID();
		loginUser.setToken(token);
		setUserAgent(loginUser);
		refreshToken(loginUser);

		Map<String, Object> claims = new HashMap<>();
		claims.put(ManageConstants.LOGIN_USER_KEY, token);
		return createToken(claims);
	}

	/**
	 * 验证令牌有效期，相差不足20分钟，自动刷新缓存
	 *
	 * @param loginUser
	 * @return 令牌
	 */
	public void verifyToken(LoginUser loginUser) {
		long expireTime = loginUser.getExpireTime();
		long currentTime = System.currentTimeMillis();
		if (expireTime - currentTime <= MILLIS_MINUTE_TEN) {
			refreshToken(loginUser);
		}
	}

	/**
	 * 刷新令牌有效期
	 *
	 * @param loginUser 登录信息
	 */
	public void refreshToken(LoginUser loginUser) {
		loginUser.setLoginTime(System.currentTimeMillis());
		loginUser.setExpireTime(loginUser.getLoginTime() + expireTime * MILLIS_MINUTE);
		// 根据uuid将loginUser缓存
		String userKey = getTokenKey(loginUser.getToken());
		redisCacheHelper.setObject(userKey, loginUser, expireTime, TimeUnit.MINUTES);
	}

	/**
	 * 设置用户代理信息
	 *
	 * @param loginUser 登录信息
	 */
	public void setUserAgent(LoginUser loginUser) {
		UserAgent userAgent = UserAgent.parseUserAgentString(ServletUtils.getRequest().getHeader("User-Agent"));
		String ip = IpUtils.getIpAddr(ServletUtils.getRequest());
		loginUser.setIpaddr(ip);
		loginUser.setLoginLocation(IpUtils.getRealAddressByIP(ip));
		loginUser.setBrowser(userAgent.getBrowser().getName());
		loginUser.setOs(userAgent.getOperatingSystem().getName());
	}

	/**
	 * 从数据声明生成令牌
	 * 
	 * @param claims 数据声明
	 * @return 令牌
	 */
	private String createToken(Map<String, Object> claims) {
		String token = Jwts.builder().setClaims(claims).signWith(encryptSecretKey(),SignatureAlgorithm.HS512).compact();
		return token;
	}
	
	
	private  Key encryptSecretKey(){
        final SecretKey key = Keys.hmacShaKeyFor(secret.getBytes(StandardCharsets.UTF_8));
        return key;
    }

	/**
	 * 从令牌中获取数据声明
	 *
	 * @param token 令牌
	 * @return 数据声明
	 */
	private Claims parseToken(String token) {
		return Jwts.parserBuilder().setSigningKey(encryptSecretKey()).build().parseClaimsJws(token).getBody();
	}

	/**
	 * 从令牌中获取用户名
	 *
	 * @param token 令牌
	 * @return 用户名
	 */
	public String getUsernameFromToken(String token) {
		Claims claims = parseToken(token);
		return claims.getSubject();
	}

	/**
	 * 获取请求token
	 *
	 * @param request
	 * @return token
	 */
	private String getToken(HttpServletRequest request) {
		String token = request.getHeader(header);
		if (StringUtils.isNotEmpty(token) && token.startsWith(ManageConstants.TOKEN_PREFIX)) {
			token = token.replace(ManageConstants.TOKEN_PREFIX, "");
		}
		return token;
	}

	private String getTokenKey(String uuid) {
		return ManageConstants.LOGIN_TOKEN_KEY + uuid;
	}
}
