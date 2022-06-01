package com.jeemodel;

import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.jeemodel.base.appstart.JeeModelAppStart;

/**
 * 启动程序
 * 
 */
@SpringBootApplication
public class JeeModelApplication extends JeeModelAppStart{
	public static void main(String[] args) {
		start(true, true, true, JeeModelApplication.class, args);
	}
}
