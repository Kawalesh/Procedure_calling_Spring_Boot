package com.rest.api;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
//@SpringBootApplication(exclude = { DataSourceAutoConfiguration.class })

public class ProcedureCallingApplication {

	public static void main(String[] args) {
		SpringApplication.run(ProcedureCallingApplication.class, args);
	}

}
