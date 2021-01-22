package io.spring.dataflow.sample.usagedetailsender;

import java.util.Random;
import java.util.function.Supplier;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.function.context.PollableBean;

import io.spring.dataflow.sample.UsageDetail;

@SpringBootApplication
public class UsageDetailSenderApplication {

	public static void main(String[] args) {
		SpringApplication.run(UsageDetailSenderApplication.class, args);
	}

	private String[] users = { "USER_1", "USER_2", "USER_3", "USER_4", "USER_5" };

	@PollableBean
	public Supplier<UsageDetail> source1Supplier() {
		return () -> {
			UsageDetail usageDetail = new UsageDetail();
			usageDetail.setUserId(this.users[new Random().nextInt(5)]);
			usageDetail.setDuration(new Random().nextInt(300));
			usageDetail.setData(new Random().nextInt(700));
			System.out.println("Suppling : " + usageDetail);
			return usageDetail;
		};
	}
}