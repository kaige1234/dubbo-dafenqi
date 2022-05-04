package com.xl.cms.Main;

import org.springframework.context.support.ClassPathXmlApplicationContext;

public class Run {

	public static void main(String[] args)throws Exception {
		String configPath="classpath*:spring/*.xml,classpath*:dubbo/*.xml";
		new ClassPathXmlApplicationContext(configPath.split("[,\\s]+")).start();
		Thread.currentThread().join();
	}

}
