package bean;

import annotation.PropertyListener;

/**
 * Created by Shannon,chen on 16/6/12.
 * 一个测试bean
 */
public class Student {
    private  String name;
    private int age;
    private String gender;
    private String desc;

    public String getName() {
        return name;
    }

    @PropertyListener(value="name")
    public void setName(String name) {
        this.name = name;
    }

    public int getAge() {
        return age;
    }

    @PropertyListener(value="age")
    public void setAge(int age) {
        this.age = age;
    }

    public String getGender() {
        return gender;
    }

    @PropertyListener(value="gender")
    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }
}
