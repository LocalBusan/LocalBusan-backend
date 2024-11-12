package com.LocalBusan.LocalBusan;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;

@SpringBootApplication(exclude = SecurityAutoConfiguration.class)
public class LocalBusanApplication {

	public static void main(String[] args) {
		SpringApplication.run(LocalBusanApplication.class, args);
	}

}
