package com.liakot.librarymanagement;

public class UserProfileClass {

    private String firstName, lastName, studentId, email, phone, faculty, department, picture, password;

    public UserProfileClass() {

    }

    public UserProfileClass(String firstName, String studentId, String email, String password) {
        this.firstName = firstName;
        this.studentId = studentId;
        this.email = email;
        this.password = password;
    }

    public UserProfileClass(String firstName, String lastName, String studentId, String email, String phone, String faculty, String department, String picture, String password) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.studentId = studentId;
        this.email = email;
        this.phone = phone;
        this.faculty = faculty;
        this.department = department;
        this.picture = picture;
        this.password = password;
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

    public String getStudentId() {
        return studentId;
    }

    public void setStudentId(String studentId) {
        this.studentId = studentId;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getFaculty() {
        return faculty;
    }

    public void setFaculty(String faculty) {
        this.faculty = faculty;
    }

    public String getDepartment() {
        return department;
    }

    public void setDepartment(String department) {
        this.department = department;
    }

    public String getPicture() {
        return picture;
    }

    public void setPicture(String picture) {
        this.picture = picture;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
