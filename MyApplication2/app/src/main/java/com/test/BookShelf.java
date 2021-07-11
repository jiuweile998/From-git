package com.test;

public class BookShelf {
    public int bookShelf_image; //书图片
    public   String bookShelf_author;//书的作者
    public String bookShelf_des;//书的描述
    public int bookShelf_dele;//书的ID号
    public String pictureURL;

    public int getBookShelf_image() {
        return bookShelf_image;
    }

    public void setBookShelf_image(int bookShelf_image) {
        this.bookShelf_image = bookShelf_image;
    }

    public String getBookShelf_author() {
        return bookShelf_author;
    }

    public void setBookShelf_author(String bookShelf_author) {
        this.bookShelf_author = bookShelf_author;
    }

    public String getBookShelf_des() {
        return bookShelf_des;
    }

    public void setBookShelf_des(String bookShelf_des) {
        this.bookShelf_des = bookShelf_des;
    }

    public int getBookShelf_dele() {
        return bookShelf_dele;
    }

    public void setBookShelf_dele(int bookShelf_dele) {
        this.bookShelf_dele = bookShelf_dele;
    }

    public String getPictureURL() {
        return pictureURL;
    }

    public void setPictureURL(String pictureURL) {
        this.pictureURL = pictureURL;
    }

    public BookShelf() {
    }

    public BookShelf(int bookShelf_image, String bookShelf_author, String bookShelf_des, int bookShelf_dele, String pictureURL) {
        this.bookShelf_image = bookShelf_image;
        this.bookShelf_author = bookShelf_author;
        this.bookShelf_des = bookShelf_des;
        this.bookShelf_dele = bookShelf_dele;
        this.pictureURL = pictureURL;
    }

}
