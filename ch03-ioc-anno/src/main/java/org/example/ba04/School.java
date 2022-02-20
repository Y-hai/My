package org.example.ba04;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component("mySchool")
public class School {
    @Value("hh大学")
    private String name;
    @Value("2222222")
    private int no;

    public School() {
    }

    public School(String name, int no) {
        this.name = name;
        this.no = no;
    }

    @Override
    public String toString() {
        return "School{" +
                "name='" + name + '\'' +
                ", no=" + no +
                '}';
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setNo(int no) {
        this.no = no;
    }
}
