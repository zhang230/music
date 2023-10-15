package com.music.dao;

public class User {
    private Integer Id;
    private String UserName;
    private String UserPwd;
    private String Name;
    private Integer Age;
    private String Gender;
    private String Email;
    private Integer Role;
    private Integer Delete;

    public Integer getId() {
        return Id;
    }

    public void setId(Integer id) {
        Id = id;
    }

    public String getUserName() {
        return UserName;
    }

    public void setUserName(String userName) {
        UserName = userName;
    }

    public String getUserPwd() {
        return UserPwd;
    }

    public void setUserPwd(String userPwd) {
        UserPwd = userPwd;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public Integer getAge() {
        return Age;
    }

    public void setAge(Integer age) {
        Age = age;
    }

    public String getGender() {
        return Gender;
    }

    public void setGender(String gender) {
        Gender = gender;
    }

    public String getEmail() {
        return Email;
    }

    public void setEmail(String email) {
        Email = email;
    }

    public Integer getRole() {
        return Role;
    }

    public void setRole(Integer role) {
        Role = role;
    }

    public Integer getDelete() {
        return Delete;
    }

    public void setDelete(Integer delete) {
        Delete = delete;
    }

    @Override
    public String toString() {
        return "User{" +
                "Id=" + Id +
                ", UserName='" + UserName + '\'' +
                ", UserPwd='" + UserPwd + '\'' +
                ", Name='" + Name + '\'' +
                ", Age=" + Age +
                ", Gender='" + Gender + '\'' +
                ", Email='" + Email + '\'' +
                ", Role=" + Role +
                ", Delete=" + Delete +
                '}';
    }

}
