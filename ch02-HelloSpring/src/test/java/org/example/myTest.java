package org.example;

import org.example.impl.SomeServiceImpl;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.util.Date;

public class myTest {
    @Test
    public void test1() {
        SomeService service = new SomeServiceImpl();
        service.dosome();
    }

    // 在创建容器时，会调用类的无参构造方法创建所有对象
    @Test
    public void test02() {
        // 使用spring容器创建对象
        // 1.指定spring配置文件的名称
        String config = "beans.xml";
        // 2.创建表示spring容器的对象，ApplicationContext
        // ApplicationContext就表示spring容器，通过这个容器获取对象了
        // ClassPathXmlApplicationContext：表示从类路径中加载spring的配置文件
        ApplicationContext ac = new ClassPathXmlApplicationContext(config);

        // 从容器中获取某个对象，你要调用对象的方法
        SomeService service = (SomeService) ac.getBean("someService");

        // 使用spring创建好的对象
        service.dosome();
    }

    /**
     * 获取spring容器中Java对象的信息
     */
    @Test
    public void test03() {
        String config = "beans.xml";
        ApplicationContext ac = new ClassPathXmlApplicationContext(config);
        // 获取容器中定义的对象的数量
        int num = ac.getBeanDefinitionCount();
        System.out.println("容器中定义的对象数量：" + num);
        // 容器中每个定义的对象的名称
        String[] names = ac.getBeanDefinitionNames();
        for (String s : names) System.out.println(s);
    }

    @Test
    public void test04() {
        String config = "beans.xml";
        // 获取容器中定义的对象的数量
        ApplicationContext ac = new ClassPathXmlApplicationContext(config);

        // 从容器中获取某个对象，你要调用对象的方法
        Date time = (Date) ac.getBean("data");

        // 使用spring创建好的对象
        System.out.println(time);
    }
}
