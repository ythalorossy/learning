package com.linkedin.linkedinbatch;

import java.util.Random;

import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.StepExecution;
import org.springframework.batch.core.job.flow.FlowExecutionStatus;
import org.springframework.batch.core.job.flow.JobExecutionDecider;

public class ReceiptDecider implements JobExecutionDecider {

    @Override
    public FlowExecutionStatus decide(JobExecution jobExecution, StepExecution stepExecution) {
        
        String result = new Random().nextFloat() > .70f ? "CORRECT" : "INCORRECT";
        System.out.println("The item delivered is: " + result);

        return new FlowExecutionStatus(result);
    }
    
}
