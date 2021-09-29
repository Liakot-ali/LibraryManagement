package com.liakot.librarymanagement;

public class RequestBookClass {
    private String bookName, authorName, studentName, studentId, studentEmail, studentPhone, studentDepartment;

    public  RequestBookClass()
    {

    }

    public RequestBookClass(String bookName, String authorName, String studentName, String studentId, String studentEmail, String studentPhone, String studentDepartment) {
        this.bookName = bookName;
        this.authorName = authorName;
        this.studentName = studentName;
        this.studentId = studentId;
        this.studentEmail = studentEmail;
        this.studentPhone = studentPhone;
        this.studentDepartment = studentDepartment;
    }


    public String getBookName() {
        return bookName;
    }

    public void setBookName(String bookName) {
        this.bookName = bookName;
    }

    public String getAuthorName() {
        return authorName;
    }

    public void setAuthorName(String authorName) {
        this.authorName = authorName;
    }

    public String getStudentName() {
        return studentName;
    }

    public void setStudentName(String studentName) {
        this.studentName = studentName;
    }

    public String getStudentId() {
        return studentId;
    }

    public void setStudentId(String studentId) {
        this.studentId = studentId;
    }

    public String getStudentEmail() {
        return studentEmail;
    }

    public void setStudentEmail(String studentEmail) {
        this.studentEmail = studentEmail;
    }

    public String getStudentPhone() {
        return studentPhone;
    }

    public void setStudentPhone(String studentPhone) {
        this.studentPhone = studentPhone;
    }

    public String getStudentDepartment() {
        return studentDepartment;
    }

    public void setStudentDepartment(String studentDepartment) {
        this.studentDepartment = studentDepartment;
    }
}
