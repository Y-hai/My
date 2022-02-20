package org.example;

import org.example.ba03.student;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class MyTest03 {
    @Test
    public void test01(){
        String config = "applicationContest03.xml";
        ApplicationContext ac = new ClassPathXmlApplicationContext(config);
        student stu = (student) ac.getBean("myStudent");
        System.out.println(stu);
    }
}
