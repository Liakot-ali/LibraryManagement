package com.liakot.librarymanagement;

public class AddBookClass {

    private String bookName;
    private String authorName;
    private String edition;
    private String page;
    private String department;
    private String quantity;
    private String position;
    String date, time;

    public AddBookClass()
    {

    }

    public AddBookClass(String bookName, String authorName, String edition, String page, String department, String quantity, String position, String date, String time) {
        this.bookName = bookName;
        this.authorName = authorName;
        this.edition = edition;
        this.page = page;
        this.department = department;
        this.quantity = quantity;
        this.position = position;
        this.date = date;
        this.time = time;
    }

    public AddBookClass(String bookName, String authorName, String edition, String page, String department, String quantity, String position) {
        this.bookName = bookName;
        this.authorName = authorName;
        this.edition = edition;
        this.page = page;
        this.department = department;
        this.quantity = quantity;
        this.position = position;
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

    public String getEdition() {
        return edition;
    }

    public void setEdition(String edition) {
        this.edition = edition;
    }

    public String getPage() {
        return page;
    }

    public void setPage(String page) {
        this.page = page;
    }

    public String getDepartment() {
        return department;
    }

    public void setDepartment(String department) {
        this.department = department;
    }

    public String getQuantity() {
        return quantity;
    }

    public void setQuantity(String quantity) {
        this.quantity = quantity;
    }

    public String getPosition() {
        return position;
    }

    public void setPosition(String position) {
        this.position = position;
    }
    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }
}
