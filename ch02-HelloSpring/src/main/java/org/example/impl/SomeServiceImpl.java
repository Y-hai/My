package org.example.impl;

import org.example.SomeService;

public class SomeServiceImpl implements SomeService {

    public SomeServiceImpl() {
        System.out.println("SomeServiceImpl的无参构造方法执行了");
    }

    @Override
    public void dosome() {
        System.out.println("执行了SomeServiceImpl的dosome");
    }
}
