package com.linkedin.linkedinbatchv2;

import org.springframework.batch.core.SkipListener;

public class CustomSkipListener implements SkipListener<Order, TrackedOrder> {

    @Override
    public void onSkipInRead(Throwable t) {

    }

    @Override
    public void onSkipInWrite(TrackedOrder item, Throwable t) {

    }

    @Override
    public void onSkipInProcess(Order item, Throwable t) {
        System.out.println("Skipping processing the item with id: " + item.getOrderId());
    }

}
