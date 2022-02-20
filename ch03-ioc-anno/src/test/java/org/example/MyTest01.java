package org.example;

import org.example.ba01.student;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class MyTest01 {
    @Test
    public void test01(){
        String config = "applicationContest.xml";
        ApplicationContext ac = new ClassPathXmlApplicationContext(config);
        student stu = (student) ac.getBean("myStudent");
        System.out.println(stu);
    }
}
