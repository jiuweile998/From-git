package com.example.springbootdemo.model;

/**
 * @author wlg
 * @create_time 2021-06-24 下午 15:19
 * @function
 */
public class Book {
    private String bookId; //主键
    private String bookName;//书名
    private String bookKind;//种类
    private String bookAuthor;//作者
    private String bookDscribe;//描述
    private String bookPicture;//图片路径
    private String bookWhere;//书的实体内容数据
    private String bookuserid;

    public String getBookId() {
        return bookId;
    }

    public void setBookId(String bookId) {
        this.bookId = bookId;
    }

    public String getBookName() {
        return bookName;
    }

    public void setBookName(String bookName) {
        this.bookName = bookName;
    }

    public String getBookKind() {
        return bookKind;
    }

    public void setBookKind(String bookKind) {
        this.bookKind = bookKind;
    }

    public String getBookAuthor() {
        return bookAuthor;
    }

    public void setBookAuthor(String bookAuthor) {
        this.bookAuthor = bookAuthor;
    }

    public String getBookDscribe() {
        return bookDscribe;
    }

    public void setBookDscribe(String bookDscribe) {
        this.bookDscribe = bookDscribe;
    }

    public String getBookPicture() {
        return bookPicture;
    }

    public void setBookPicture(String bookPicture) {
        this.bookPicture = bookPicture;
    }

    public String getBookWhere() {
        return bookWhere;
    }

    public void setBookWhere(String bookWhere) {
        this.bookWhere = bookWhere;
    }

    public String getBookuserid() {
        return bookuserid;
    }

    public void setBookuserid(String bookuserid) {
        this.bookuserid = bookuserid;
    }

    public Book() {
    }

    @Override
    public String toString() {
        return "Book{" +
                "bookId='" + bookId + '\'' +
                ", bookName='" + bookName + '\'' +
                ", bookKind='" + bookKind + '\'' +
                ", bookAuthor='" + bookAuthor + '\'' +
                ", bookDscribe='" + bookDscribe + '\'' +
                ", bookPicture='" + bookPicture + '\'' +
                ", bookWhere='" + bookWhere + '\'' +
                ", bookuserid='" + bookuserid + '\'' +
                '}';
    }
}
