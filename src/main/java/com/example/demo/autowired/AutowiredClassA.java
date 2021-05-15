package com.example.demo.autowired;

import org.springframework.beans.factory.annotation.Autowired;

public class AutowiredClassA {

    @Autowired
    private AutowiredClassB autowiredClassB;
}
