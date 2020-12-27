package com.linkedin.linkedinbatchv2;

import java.util.UUID;

import org.springframework.batch.item.ItemProcessor;

public class TrackedOrderItemProcessor implements ItemProcessor<Order, TrackedOrder> {

	@Override
	public TrackedOrder process(Order order) throws Exception {

        System.out.println("Processing order with id: " + order.getOrderId());
        System.out.println("Processing with thread " + Thread.currentThread().getName());
        
        TrackedOrder trackedOrder = new TrackedOrder(order);
        trackedOrder.setTrackingNumber(getTrackingNumber());
		return trackedOrder;
	}

    private String getTrackingNumber() throws OrderProcessingException {

        if (Math.random() < .05) {
            throw new OrderProcessingException();
        }

        return UUID.randomUUID().toString();
    }

}
