package com.jeemodel.unit.manage.security.filter;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import com.jeemodel.core.utils.BlankUtils;
import com.jeemodel.unit.manage.core.bean.model.LoginUser;
import com.jeemodel.unit.manage.core.utils.SecurityUtils;
import com.jeemodel.unit.manage.core.web.service.TokenService;

/**
 * token过滤器 验证token有效性
 * 
 * @author Rootfive
 */
@Component
public class JwtAuthenticationTokenFilter extends OncePerRequestFilter {
	@Autowired
	private TokenService tokenService;

	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain)
			throws ServletException, IOException {
		LoginUser loginUser = tokenService.getLoginUser(request);
		if (BlankUtils.isNotNull(loginUser) && BlankUtils.isNull(SecurityUtils.getAuthentication())) {
			tokenService.verifyToken(loginUser);
			UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(loginUser,
					null, loginUser.getAuthorities());
			authenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
			SecurityContextHolder.getContext().setAuthentication(authenticationToken);
		}
		chain.doFilter(request, response);
	}
}
