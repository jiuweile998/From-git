package com.example.springbootdemo.service.impl;

import com.example.springbootdemo.mapper.MyUserMapper;
import com.example.springbootdemo.model.Admin_book;
import com.example.springbootdemo.model.Book;
import com.example.springbootdemo.model.MyUser;
import com.example.springbootdemo.service.MyUserService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author wlg
 * @create_time 2021-06-21 下午 20:41
 * @function
 */
@Service(value = "myUserService")
public class MyUserServiceImpl implements MyUserService {
    @Resource
    private MyUserMapper myUserMapper;


    @Override
    public MyUser selectUserByPhoneAndPassword(String phone,String password) {
        return myUserMapper.selectUserByPhoneAndPassword(phone,password);
    }

    @Override
    public Boolean Inseruser( String username,String userphone,String userpassword) {
        return myUserMapper.InsertUser(username,userphone,userpassword);
    }
    @Override
    public MyUser selectUserByPhone(String phone) {
        return myUserMapper.selectUserByPhone(phone);
    }

    @Override
    public Boolean updateUser(String userphone, String username, String userpassword) {
        return myUserMapper.updateUser(userphone,username,userpassword);
    }

    @Override
    public Book selectBookByid(String userphone) {
        return myUserMapper.selectBookByid(userphone);
    }

    @Override
    public List<Book> selectBookId(String userphone) {
        return myUserMapper.selectBookId(userphone);
    }

    @Override
    public String selectBookforwhere(String bookid) {
        return myUserMapper.selectBookwhere(bookid);
    }

    @Override
    public List<Book> selectBookByNameOrAuthor(String name) {
        return myUserMapper.selectBookByNameOrAuthor(name);
    }

    @Override
    public Boolean deleteBook(String bookid) {
        return myUserMapper.deleteBook(bookid);
    }

    @Override
    public Boolean addBook(String bookid,String bookuserid) {
        return myUserMapper.addbook(bookid,bookuserid);
    }

    @Override
    public List<Book> selectBookBybookkind(String bookkind) {
        return myUserMapper.selectBookBybookkind(bookkind);
    }

    @Override
    public Admin_book adminLogin(String username) {
        return myUserMapper.adminLogin(username);
    }

    @Override
    public List<MyUser> selectAllMyUser() {
        return myUserMapper.selectAllMyUser();
    }

    @Override
    public List<Book> selectAllBook() {
        return myUserMapper.selectAllBook();
    }

    @Override
    public List<Book> selectGeRenAllbook(String userphone) {

        return myUserMapper.selectGeRenAllbook(userphone);
    }

    @Override
    public Boolean updataMyUserFromAdmin(String userphone, String username, String userpassword) {
        return myUserMapper.updataMyUserFromAdmin(userphone,username,userpassword);
    }

    @Override
    public Boolean UpdateBookFromAdmin(String bookid, String bookname, String bookauthor, String bookkind, String bookdscribe) {
        return myUserMapper.UpdateBookFromAdmin(bookid,bookname,bookauthor,bookkind,bookdscribe);
    }

    @Override
    public Boolean deleteFromAdminTomyuser(String userphone) {
        return myUserMapper.deleteFromAdminTomyuser(userphone);
    }

    @Override
    public Boolean AddBookFromAdmin(String bookid, String bookname, String bookauthor, String bookkind,
                                    String bookdscribe, String bookpicture, String bookwhere) {
        return myUserMapper.AddBookFromAdmin(bookid,bookname,bookauthor,bookkind,bookdscribe,bookpicture,bookwhere);
    }

    @Override
    public Boolean registerAdmin(String adminname, String password) {
        return myUserMapper.registerAdmin(adminname,password);
    }
}
