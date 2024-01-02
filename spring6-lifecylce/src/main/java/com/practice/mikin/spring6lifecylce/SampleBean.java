package com.practice.mikin.spring6lifecylce;

import org.springframework.stereotype.Component;

@Component
public class SampleBean {
    public SampleBean() {
        System.out.println("SampleBean Contructor");
    }
}
