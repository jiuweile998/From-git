package com.example.springbootdemo.service;

import com.example.springbootdemo.model.Admin_book;
import com.example.springbootdemo.model.Book;
import com.example.springbootdemo.model.MyUser;

import java.util.List;

/**
 * @author wlg
 * @create_time 2021-06-21 下午 20:04
 * @function
 */
public interface MyUserService {
    MyUser selectUserByPhoneAndPassword(String phone,String password);
    Boolean Inseruser(String username,String userphone,String userpassword);
    MyUser selectUserByPhone(String phone);
    Boolean updateUser(String userphone,String username,String userpassword);
    Book selectBookByid(String bookid);
    List<Book> selectBookId(String userphone);
    String selectBookforwhere(String bookid);

    List<Book> selectBookByNameOrAuthor(String name);

    Boolean deleteBook(String bookid);
    Boolean addBook(String bookid,String bookuserid);
    List<Book> selectBookBybookkind(String bookkind);

    Admin_book adminLogin(String username);
    //用于管理员，查询所有用户
    List<MyUser> selectAllMyUser();
    //用于管理员，查询所有小说
    List<Book> selectAllBook();

    //用于管理员,查询个人的用户的小说
    List<Book> selectGeRenAllbook(String userphone);
    //管理员修改用户信息
    Boolean updataMyUserFromAdmin(String userphone,String username,String userpassword);
    //管理员修改小说信息
    Boolean UpdateBookFromAdmin(String bookid,String bookname,String bookauthor,String bookkind,String bookdscribe);
   //管理员删除用户
    Boolean deleteFromAdminTomyuser(String userphone);
   //管理员增加小说
    Boolean AddBookFromAdmin(String bookid,String bookname,String bookauthor,String bookkind,
                             String bookdscribe,String bookpicture,String bookwhere);

    //管理员注册
    Boolean registerAdmin(String adminname,String password);

}
