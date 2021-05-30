package Project.Models;

import java.util.ArrayList;
import java.util.List;

public class Student {

    private Long membershipId;
    private String name;
    private Integer age;
    private Character gender;
    private final List<Course> courses = new ArrayList<>();

    public Student() {
    }

    public Student(String name, Integer age, Character gender, Long membershipId) {
        this.name = name;
        this.age = age;
        this.gender = gender;
        this.membershipId = membershipId;
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getAge() {
        return this.age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    public Long getMembershipId() {
        return membershipId;
    }

    public void setMembershipId(Long membershipId) {
        this.membershipId = membershipId;
    }

    public Character getGender() {
        return this.gender;
    }

    public void setGender(Character gender) {
        this.gender = gender;
    }

    public List<Course> getCourses() {
        return this.courses;
    }

}
