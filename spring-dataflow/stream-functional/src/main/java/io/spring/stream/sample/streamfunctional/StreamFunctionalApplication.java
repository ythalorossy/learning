package io.spring.stream.sample.streamfunctional;

import java.util.function.Consumer;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class StreamFunctionalApplication {
	public static void main(String[] args) {
		SpringApplication.run(StreamFunctionalApplication.class,
					//    "--spring.cloud.stream.function.routing.enabled=true",
					   "--spring.cloud.function.routing-expression=T(java.lang.System).currentTimeMillis() % 2 == 0 ? 'even' : 'odd'");
	}

	@Bean
	public Consumer<String> even() {
		return value -> {
			System.out.println("EVEN: " + value);
		};
	}

	@Bean
	public Consumer<String> odd() {
		return value -> {
			System.out.println("ODD: " + value);
		};
    }
}
