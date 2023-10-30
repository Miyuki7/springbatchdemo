package com.miyuki.learn.spring.batch.dto;

/**
 * @author: miyuki
 * @description: dto
 * @date: 2023/10/25 15:47
 * @version: 1.0
 */
public class PersonDTO {
    private String firstName;
    private String lastName;
    private Integer age;

    @Override
    public String toString() {
        return "personDTO{" +
                "firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", age=" + age +
                '}';
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }
}
