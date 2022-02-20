package org.example;

import org.example.test.aaa;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class AppTest {
    @Test
    public void test01() {
        String config = "applicationContext.xml";
        ApplicationContext ac = new ClassPathXmlApplicationContext(config);
        App student = (App) ac.getBean("app");
        System.out.println(student);
    }

    @Test
    public void test02() {
        String config = "applicationContext.xml";
        ApplicationContext ac = new ClassPathXmlApplicationContext(config);
        aaa student = (aaa) ac.getBean("aaa");
        System.out.println(student);
    }

    @Test
    public void test03() {
        String config = "applicationContext.xml";
        ApplicationContext ac = new ClassPathXmlApplicationContext(config);
        aaa student = (aaa) ac.getBean("ccc");
        System.out.println(student);
    }
}
