package com.example.demospringbatch;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class DemoSpringBatchApplication {

	public static void main(String[] args) {
		
		String startDate = System.getProperty("startDate");
		String duration = System.getProperty("duration");
		String threshold = System.getProperty("threshold");
		System.out.println(startDate);
		System.out.println(duration);
		System.out.println(threshold);
		startDate = args[0];
		duration = args[1];
		threshold = args[2];
		System.out.println(startDate);
		System.out.println(duration);
		System.out.println(threshold);
		SpringApplication.run(DemoSpringBatchApplication.class, args);
	}
}
