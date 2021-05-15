package com.example.demo.autowired;

import org.springframework.beans.factory.annotation.Autowired;

public class AutowiredClassB {

    @Autowired
    private AutowiredClassA autowiredClassA;
}
