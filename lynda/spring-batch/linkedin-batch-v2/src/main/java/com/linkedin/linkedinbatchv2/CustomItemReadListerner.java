package com.linkedin.linkedinbatchv2;

import org.springframework.batch.core.ItemReadListener;
import org.springframework.stereotype.Component;

@Component
public class CustomItemReadListerner implements ItemReadListener<Order>{

    @Override
    public void beforeRead() {
        System.out.println("Before read");

    }

    @Override
    public void afterRead(Order item) {
        System.out.println("Readed item : " + item.getItemId());
    }

    @Override
    public void onReadError(Exception ex) {
        System.out.println("Read exception : " + ex.getMessage());
    }

}
