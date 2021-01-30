package com.linkedin.linkedinbatchv2;

import java.math.BigDecimal;

import org.springframework.batch.item.ItemProcessor;

public class FreeShippingItemProcessor implements ItemProcessor<TrackedOrder, TrackedOrder> {

    @Override
    public TrackedOrder process(TrackedOrder item) throws Exception {
        
        if (item.getCost().compareTo(new BigDecimal("50")) == 1) {
            item.setFreeShipping(true);
        }

        return item.isFreeShipping() ? item : null; // returning null the Item will be removed before move to next processor or item writer
    }
}
