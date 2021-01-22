package org.ythalorossy.spring.clould;

import java.util.Arrays;
import java.util.List;
import java.util.function.Supplier;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.function.context.PollableBean;

@SpringBootApplication
public class SprinfDataflowTimeSourceApplication {

	public static void main(String[] args) {
		SpringApplication.run(SprinfDataflowTimeSourceApplication.class, args);
	}

	@PollableBean(splittable = true)
	public Supplier<List<String>> pullArticles() {
		return () -> Arrays.asList("article1", "article2", "article3", "article4");
	}

}
