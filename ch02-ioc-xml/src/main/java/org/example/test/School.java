package org.example.test;

public class School {
    private String name;
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
