package org.example;

import org.example.ba06.student;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class MyTest06 {
    @Test
    public void test01(){
        String config = "applicationContest06.xml";
        ApplicationContext ac = new ClassPathXmlApplicationContext(config);
        student stu = (student) ac.getBean("myStudent");
        System.out.println(stu);
    }
}
