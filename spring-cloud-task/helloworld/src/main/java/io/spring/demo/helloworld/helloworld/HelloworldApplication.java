package io.spring.demo.helloworld.helloworld;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.task.listener.annotation.BeforeTask;
import org.springframework.cloud.task.listener.TaskExecutionListener;
import org.springframework.cloud.task.listener.annotation.AfterTask;
import org.springframework.cloud.task.listener.annotation.FailedTask;
import org.springframework.cloud.task.listener.annotation.TaskListenerExecutor;
import org.springframework.cloud.task.repository.TaskExecution;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

@SpringBootApplication
public class HelloworldApplication {

	@Bean
	public CommandLineRunner commandLineRunner() {
		return new HelloWorldCommandLineRunner();
	}

	public static void main(String[] args) {
		SpringApplication.run(HelloworldApplication.class, args);
	}

	public static class HelloWorldCommandLineRunner implements CommandLineRunner {

		@Override
		public void run(String... strings) throws Exception {
			System.out.println("Hello, World!");
		}
	}

@Bean
public TaskExecutionListener el() {
	return new MyBean();
}

	public class MyBean implements TaskExecutionListener{

		// @BeforeTask
		// public void methodA(TaskExecution taskExecution) {
		// 	System.out.println("Before");
		// }

		// @AfterTask
		// public void methodB(TaskExecution taskExecution) {
		// 	System.out.println("After");
		// 	taskExecution.setExitMessage("AFTER EXIT MESSAGE");
		// }


		// @FailedTask
		// public void methodC(TaskExecution taskExecution, Throwable throwable) {
		// 	System.out.println("Failed: " + throwable.getMessage() );
		// }

		@Override
		public void onTaskEnd(TaskExecution taskExecution) {
			System.out.println("After");
			taskExecution.setExitMessage("AFTER EXIT MESSAGE");

		}

		@Override
		public void onTaskFailed(TaskExecution taskExecution, Throwable throwable) {
			System.out.println("Failed: " + throwable.getMessage() );

		}

		@Override
		public void onTaskStartup(TaskExecution taskExecution) {
			System.out.println("Before");

		}
	}
}
