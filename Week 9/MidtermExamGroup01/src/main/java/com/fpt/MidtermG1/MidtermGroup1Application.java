package com.fpt.MidtermG1;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;

@SpringBootApplication
@EntityScan(basePackages = "com.fpt.MidtermG1")
public class MidtermGroup1Application {

	public static void main(String[] args) {
		SpringApplication.run(MidtermGroup1Application.class, args);
	}

}
