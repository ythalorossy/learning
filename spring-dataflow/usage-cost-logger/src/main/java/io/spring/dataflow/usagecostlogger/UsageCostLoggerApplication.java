package io.spring.dataflow.usagecostlogger;

import java.util.function.Consumer;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class UsageCostLoggerApplication {

	public static void main(String[] args) {
		SpringApplication.run(UsageCostLoggerApplication.class, args);
	}

	@Bean
	public Consumer<UsageCostDetail> sink() {
		return System.out::println;
	}
}
