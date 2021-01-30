package com.linkedin.linkedinbatchv2;

import java.util.List;

import org.springframework.batch.core.ItemWriteListener;
import org.springframework.stereotype.Component;

@Component
public class CustomItemWriteListerner implements ItemWriteListener<TrackedOrder> {

    @Override
    public void beforeWrite(List<? extends TrackedOrder> items) {
        System.out.println("Before write items : " + items.size() + " items");

    }

    @Override
    public void afterWrite(List<? extends TrackedOrder> items) {
        System.out.println("Writing : " + items.size() + " items");

    }

    @Override
    public void onWriteError(Exception exception, List<? extends TrackedOrder> items) {
        System.out.println("Write exception : " + exception.getMessage());
    }


    
}
