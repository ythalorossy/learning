package com.example.loggingconsumer;

import java.util.function.Supplier;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;

import reactor.core.publisher.EmitterProcessor;
import reactor.core.publisher.Flux;

@SpringBootApplication
@Controller
public class LoggingConsumerApplication {

	public static void main(String[] args) {
		SpringApplication.run(LoggingConsumerApplication.class);
	}

	EmitterProcessor<String> processor = EmitterProcessor.create();

	@RequestMapping
	@ResponseStatus(code = HttpStatus.ACCEPTED)
	public void delegateToSupplier(@RequestBody String body) {
		processor.onNext(body);
	}

	@Bean
	public Supplier<Flux<String>> supplier() {
		return () -> this.processor;
	}

	
}
