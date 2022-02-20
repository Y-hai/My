package org.example.test;

public class aaa {
    private String name;
    private int no;
    private School school;

    public aaa() {
    }

    public aaa(String name, int no, School school) {
        this.name = name;
        this.no = no;
        this.school = school;
    }

    @Override
    public String toString() {
        return "aaa{" +
                "name='" + name + '\'' +
                ", no=" + no +
                ", school=" + school +
                '}';
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setNo(int no) {
        this.no = no;
    }

    public void setSchool(School school) {
        this.school = school;
    }
}
