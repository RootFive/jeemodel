package com.jeemodel.core.utils.http;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.Charset;

import javax.servlet.ServletRequest;

import org.apache.commons.lang3.exception.ExceptionUtils;

import lombok.extern.slf4j.Slf4j;

/**
 * 通用http工具封装
 * 
 * @author Rootfive
 */
@Slf4j
public class HttpHelper {

	public static String getBodyString(ServletRequest request) {
		StringBuilder sb = new StringBuilder();
		BufferedReader reader = null;
		try (InputStream inputStream = request.getInputStream()) {
			reader = new BufferedReader(new InputStreamReader(inputStream, Charset.forName("UTF-8")));
			String line = "";
			while ((line = reader.readLine()) != null) {
				sb.append(line);
			}
		} catch (IOException e) {
			log.warn("getBodyString出现问题！");
		} finally {
			if (reader != null) {
				try {
					reader.close();
				} catch (IOException e) {
					log.error(ExceptionUtils.getMessage(e));
				}
			}
		}
		return sb.toString();
	}
}
