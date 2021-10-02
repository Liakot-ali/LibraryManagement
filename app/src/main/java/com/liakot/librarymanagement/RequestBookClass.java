package com.liakot.librarymanagement;

public class RequestBookClass {
    private String bookName, authorName, studentName, studentId, bookEdition, bookPosition, bookQuantity, BookPage, bookDepartment, bookId, userId;

    public  RequestBookClass()
    {

    }

    public RequestBookClass(String bookName, String authorName, String studentName, String studentId, String bookEdition, String bookPosition, String bookQuantity, String bookPage, String bookDepartment, String bookId, String userId) {
        this.bookName = bookName;
        this.authorName = authorName;
        this.studentName = studentName;
        this.studentId = studentId;
        this.bookEdition = bookEdition;
        this.bookPosition = bookPosition;
        this.bookQuantity = bookQuantity;
        this.BookPage = bookPage;
        this.bookDepartment = bookDepartment;
        this.bookId = bookId;
        this.userId = userId;
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

    public String getBookEdition() {
        return bookEdition;
    }

    public void setBookEdition(String bookEdition) {
        this.bookEdition = bookEdition;
    }

    public String getBookPosition() {
        return bookPosition;
    }

    public void setBookPosition(String bookPosition) {
        this.bookPosition = bookPosition;
    }

    public String getBookQuantity() {
        return bookQuantity;
    }

    public void setBookQuantity(String bookQuantity) {
        this.bookQuantity = bookQuantity;
    }

    public String getBookPage() {
        return BookPage;
    }

    public void setBookPage(String bookPage) {
        BookPage = bookPage;
    }

    public String getBookDepartment() {
        return bookDepartment;
    }

    public void setBookDepartment(String bookDepartment) {
        this.bookDepartment = bookDepartment;
    }

    public String getBookId() {
        return bookId;
    }

    public void setBookId(String bookId) {
        this.bookId = bookId;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }
}
