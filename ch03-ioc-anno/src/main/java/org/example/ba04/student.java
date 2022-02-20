package org.example.ba04;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

/**
 * @Component：创建对象，等同于<bean> 写在类上面，创建对象后放在容器里
 */
@Component("myStudent")
public class student {
    @Value("张三")
    private String name;
    @Value("123")
    private Integer no;
    @Autowired
    @Qualifier("mySchool")
    private School school;

    public student() {
        System.out.println("无参构造方法被调用了");
    }

    public student(String name, Integer no) {
        this.name = name;
        this.no = no;
    }

    @Override
    public String toString() {
        return "student{" +
                "name='" + name + '\'' +
                ", no=" + no +
                ", school=" + school +
                '}';
    }

//    public void setName(String name) {
//        this.name = name;
//    }
//
//    public void setNo(Integer no) {
//        this.no = no;
//    }
}
