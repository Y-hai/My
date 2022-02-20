package org.example;

public class App {
    private String name;
    private int no;
    private Student stu;

    public void setStudent(Student stu) {
        this.stu = stu;
    }

    public App() {
    }

    public App(String name, int no) {
        this.name = name;
        this.no = no;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setNo(int no) {
        this.no = no;
    }

    @Override
    public String toString() {
        return "App{" +
                "name='" + name + '\'' +
                ", no=" + no +
                ", stu=" + stu +
                '}';
    }
}

