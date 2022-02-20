package org.example;

public class Student {
    private String a;
    private int b;

    public void setA(String a) {
        this.a = a;
    }

    public void setB(int b) {
        this.b = b;
    }

    @Override
    public String toString() {
        return "Student{" +
                "a='" + a + '\'' +
                ", b=" + b +
                '}';
    }
}
