package com.example.demo;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.ImportResource;

@SpringBootApplication
@ImportResource("classpath:/beans.xml")
public class DemoApplication {

	private static final Logger LOGGER = LoggerFactory.getLogger(DemoApplication.class);

	public static void main(String[] args) {
		final ConfigurableApplicationContext cac = SpringApplication.run(DemoApplication.class, args);
		LOGGER.error("---- BEAN CYCLES ----");
		DepGraph.buildDependencyGraph(cac).calculateCycles().forEach(LOGGER::error);
		LOGGER.error("---- END OF BEAN CYCLES ----");
		System.exit(1);
	}

}
