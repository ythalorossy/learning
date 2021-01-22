package io.spring.dataflow.sample.usagecostprocessor;

import java.util.function.Function;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.function.context.PollableBean;

@SpringBootApplication
public class UsageCostProcessorRabbitApplication {

	public static void main(String[] args) {
		SpringApplication.run(UsageCostProcessorRabbitApplication.class, args);
	}

	private double ratePerSecond = 0.1;

	private double ratePerMB = 0.05;

	@PollableBean
	public Function<UsageDetail, UsageCostDetail> passThru() {
		return usageDetail -> {
			UsageCostDetail usageCostDetail = new UsageCostDetail();
			usageCostDetail.setUserId(usageDetail.getUserId());
			usageCostDetail.setCallCost(usageDetail.getDuration() * this.ratePerSecond);
			usageCostDetail.setDataCost(usageDetail.getData() * this.ratePerMB);
			System.out.println("Processing : " + usageCostDetail);
			return usageCostDetail;
		};
	}
}
