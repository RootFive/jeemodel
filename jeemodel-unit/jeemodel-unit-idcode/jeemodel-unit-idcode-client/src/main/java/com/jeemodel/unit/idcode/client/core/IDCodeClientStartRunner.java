package com.jeemodel.unit.idcode.client.core;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Component;

/**
 * @author Rootfive
 */
@Component
@ConditionalOnProperty(prefix = "jeemodel.unit.idcode", name = "deploy", havingValue = "client")
public class IDCodeClientStartRunner implements ApplicationRunner {

	@Autowired(required = false)
	private IDCodeClient iDcodeClient;

	@Override
	public void run(ApplicationArguments args) throws Exception {
		iDcodeClient.init();
		iDcodeClient.start();
	}

}
