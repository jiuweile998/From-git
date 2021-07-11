package com.example.springbootdemo.mapper;

import com.example.springbootdemo.model.Admin_book;
import com.example.springbootdemo.model.Book;
import com.example.springbootdemo.model.MyUser;

import java.util.List;

/**
 * @author wlg
 * @create_time 2021-06-21 下午 20:10
 * @function
 */
public interface MyUserMapper {
    MyUser selectUserByPhoneAndPassword(String phone,String password);
    Boolean InsertUser(String username,String userphone,String userpassword);
    MyUser selectUserByPhone(String phone);
    Boolean updateUser(String userphone,String username,String userpassword);
    Book selectBookByid(String bookid);
    List<Book> selectBookId(String userphone);
    String selectBookwhere(String bookid);
    List<Book> selectBookByNameOrAuthor(String name);
    Boolean deleteBook(String bookid);
    Boolean addbook(String bookid,String bookuserid);
    List<Book> selectBookBybookkind(String bookkind);
    Admin_book adminLogin(String username);
    List<MyUser> selectAllMyUser();
    List<Book> selectAllBook();
    List<Book> selectGeRenAllbook(String userphone);
    Boolean updataMyUserFromAdmin(String userphone,String username,String userpassword);
    Boolean UpdateBookFromAdmin(String bookid, String bookname, String bookauthor, String bookkind, String bookdscribe);
    Boolean deleteFromAdminTomyuser(String userphone);
    Boolean AddBookFromAdmin(String bookid, String bookname, String bookauthor, String bookkind,
                             String bookdscribe, String bookpicture, String bookwhere);

    Boolean registerAdmin(String adminname,String password);
}
