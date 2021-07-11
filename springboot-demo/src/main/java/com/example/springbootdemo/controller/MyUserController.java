package com.example.springbootdemo.controller;

import com.example.springbootdemo.model.*;
import com.example.springbootdemo.service.MyUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.io.IOException;
import java.util.List;

/**
 * @author wlg
 * @create_time 2021-06-21 下午 18:34
 * @function
 */
@Controller
@RequestMapping(value = "/MyUser")
public class MyUserController {
  @Autowired
  private MyUserService myUserService;

  @ResponseBody
  @RequestMapping(value = "/login")
  public MyUser getUser(@RequestParam(name = "phone")String phone,
                        @RequestParam(name = "password")String password){

    if(myUserService.selectUserByPhoneAndPassword(phone,password)!=null){
      System.out.println(myUserService.selectUserByPhoneAndPassword(phone,password));
      return myUserService.selectUserByPhoneAndPassword(phone,password);
    }else {
      System.out.println("else------"+myUserService.selectUserByPhoneAndPassword(phone,password));
      return null;
    }


  }
  @ResponseBody
  @RequestMapping(value = "/insertUser")
  public MyUser insertUser(@RequestParam(name = "username")String username,
                        @RequestParam(name = "userphone")String userphone,
                           @RequestParam(name = "userpassword")String userpassword){

     Boolean b=myUserService.Inseruser(username,userphone,userpassword);
    if(b){
      System.out.println("---------------"+myUserService.selectUserByPhone(userphone).toString());
      return myUserService.selectUserByPhone(userphone);
    }else {
     return null;
    }
  }

  @ResponseBody
  @RequestMapping(value = "/updataUser")
  public MyUser updateUser(@RequestParam(name = "username")String username,
                           @RequestParam(name = "userphone")String userphone,
                           @RequestParam(name = "userpassword")String userpassword) {
    System.out.println("------updata--"+username+userphone+userpassword);
      Boolean b=myUserService.updateUser(userphone,username,userpassword);
      if(b){
        return myUserService.selectUserByPhone(userphone);
      }else{
        return null;
      }
  }

  //通过bookid查询book，返回一个书
  @ResponseBody
  @RequestMapping(value = "/selectbook")
  public Book selectBook(@RequestParam(name = "bookid")String bookid) {

    return myUserService.selectBookByid(bookid);
  }
  //通过userphone查询到用户的书对象集合，再返回bookid这个字段
  @ResponseBody
  @RequestMapping(value = "/selectBybookid")
  public String selectBookid(@RequestParam(name = "userphone")String phone) {
    String data="";
    List<Book> list= myUserService.selectBookId(phone);
     for(int i=0;i<list.size();i++){

      Book book=list.get(i);
       System.out.println(book.toString());
      data=data+ book.getBookId()+",";
     }

    return data;
  }

  //通过bookid查询book，返回一个书文本的具体地址
  @ResponseBody
  @RequestMapping(value = "/Forbooktxt")
  public String selectBookwhere(@RequestParam(name = "bookid")String bookid) throws IOException {

    String url=myUserService.selectBookforwhere(bookid);
    System.out.println("===书的文本具体地址="+url);
   String data=BookText_json.MakeTxtToJson(url);
    //String bookdata=BookText_json.MakeTxtToJson(url);
    return data;
  }

  //查询书，通过书名或者作者，返回一个list json串
  @ResponseBody
  @RequestMapping(value = "/selectbookbyname")
  public List<Book> selectBookByNameOrAuthor(@RequestParam(name = "name")String name) throws IOException {


    List<Book> list= myUserService.selectBookByNameOrAuthor(name);
    if(list.isEmpty()){
      return null;
    }else {
      return list;
    }


  }

  //逻辑删除书
  @ResponseBody
  @RequestMapping(value = "/delete")
  public String deletebook(@RequestParam(name = "bookid")String bookid) throws IOException {

    System.out.println("删除的bookid是"+bookid);
       Boolean b=myUserService.deleteBook(bookid);
    System.out.println("删除结果===="+b);
    return null;
  }
  //逻辑增加书
  @ResponseBody
  @RequestMapping(value = "/add")
  public String updatabook(@RequestParam(name = "bookid")String bookid,
                           @RequestParam(name = "bookuserid")String bookuserid
                                  ) throws IOException {

    Boolean b=myUserService.addBook(bookid,bookuserid);
    System.out.println("添加结果===="+b);
    return null;
  }

  //查询书，通过书的种类，返回list
  @ResponseBody
  @RequestMapping(value = "/bookkind")
  public List<Book> selectBookBybookkind(@RequestParam(name = "bookkind_name")String bookkind) throws IOException {


    List<Book> list= myUserService.selectBookBybookkind(bookkind);
    if(list.isEmpty()){
      return null;
    }else {
      return list;
    }
  }

  //管理员登录，并查询所有小说用户，返回list用户信息，查询所有小说，返回list小说信息
  //@ResponseBody
  @RequestMapping(value = "/adminlogin")
  public String adminlogin(@RequestParam(name = "userName")String userName,
                               @RequestParam(name = "password")String password,
                           Model model
                               ) throws IOException {

   Admin_book admin_book= myUserService.adminLogin(userName);

   List<MyUser> myUsers=myUserService.selectAllMyUser();
    model.addAttribute("MyUsers", myUsers);

    List<Book> allBooks=myUserService.selectAllBook();
    model.addAttribute("AllBooks",allBooks);

    System.out.println("=+++==="+myUsers);

    if(admin_book!=null){
     // System.out.println("======admin==="+admin_book.getPassword());
      return "hello";
    }else {
      return "error";
    }


  }

  //用于管理员，查询个人小说
  @RequestMapping(value = "/geren")
  public String selectGeRenAllbook(
          @RequestParam(name = "userphone")String userphone,
          Model model) throws IOException {
    System.out.println("???");
      List<Book> list=myUserService.selectGeRenAllbook(userphone);
    System.out.println("gereshujia==="+list);
      model.addAttribute("Books",list);
    if(list.isEmpty()){
      return "error";
    }else {
      return "gerenshujia";
    }

  }


  //修改用户的个人信息
  //@ResponseBody
  @RequestMapping(value = "/UpdateFromAdmin")
  public String updateMyUserInfo(@RequestParam(name = "userphone") String userphone,
                                     @RequestParam(name = "username") String username,
                                     @RequestParam(name = "userpassword") String userpassword
                                     ) throws IOException {


    Boolean b=myUserService.updataMyUserFromAdmin(userphone,username,userpassword);
    if(b){
      return "success";
    }else {
      return "error";
    }

  }
  @RequestMapping(value = "/deleteFromAdminTomyuser")
  public String deleteFromAdminTomyuser(@RequestParam(name = "userphone") String userphone
  ) throws IOException {

//    System.out.println("userid删除的用户==="+userphone);

    Boolean b=myUserService.deleteFromAdminTomyuser(userphone);
    if(b){
      return "success";
    }else {
      return "error";
    }

  }

  //管理员对小说的修改 UpdateBookFromAdmin
  @RequestMapping(value = "/UpdateBookFromAdmin")
  public String UpdateBookFromAdmin(@RequestParam(name = "bookid") String bookid,
                                 @RequestParam(required=false,name = "bookname") String bookname,
                                 @RequestParam(required=false,name = "bookauthor") String bookauthor,
                                    @RequestParam(required=false,name = "bookkind") String bookkind,
                                    @RequestParam(required=false,name = "bookdscribe") String bookdscribe
  ) throws IOException {

      //根据书id得到对应书的参数，在判断传递过来的bookname，bookauthor，bookkind，bookdscrib是否null
      //是null，就把原本的参数赋值上，不是，就修改小说参数
    Book book=myUserService.selectBookByid(bookid);
    String new_bookname,new_bookauthor,new_bookkind,new_bookdscribe;

      new_bookname=bookname;
      new_bookauthor=bookauthor;
      new_bookkind=bookkind;
      new_bookdscribe=bookdscribe;


    //传递参数不为空
    if(bookname.equals("")){
        System.out.println("bookname=="+bookname);
        new_bookname=book.getBookName();
    }
      if(bookauthor.equals("")){
          System.out.println("bookauthor=="+bookauthor);
          new_bookauthor=book.getBookAuthor();
      }
      if(bookkind.equals("")){
          System.out.println("bookkind==="+bookkind);

          new_bookkind=book.getBookKind();
      }
      if(bookdscribe.equals("")){
          System.out.println("bookdscribe=="+bookdscribe);
          new_bookdscribe=book.getBookDscribe();
      }

      System.out.println("new_bookname=="+new_bookname);
      System.out.println("new_bookauthor=="+new_bookauthor);
      System.out.println("new_bookkind=="+new_bookkind);
      System.out.println("new_bookdscribe=="+new_bookdscribe);
    //执行更新
      Boolean b=myUserService.UpdateBookFromAdmin(bookid,new_bookname,new_bookauthor,new_bookkind,new_bookdscribe);

     if(b){
          return "success";
      }else {
          return "error";
      }

  }

  //AddBookFromAdmin,管理员增加小说
  @RequestMapping(value = "/AddBookFromAdmin")
  public String AddBookFromAdmin(@RequestParam(name = "bookid") String bookid,
                                    @RequestParam(required=false,name = "bookname") String bookname,
                                    @RequestParam(required=false,name = "bookauthor") String bookauthor,
                                    @RequestParam(required=false,name = "bookkind") String bookkind,
                                    @RequestParam(required=false,name = "bookdscribe") String bookdscribe,
                                 @RequestParam(required=false,name = "bookpicture") String bookpicture,
                                 @RequestParam(required=false,name = "bookwhere") String bookwhere
  ) throws IOException {

    System.out.println("bookid==="+bookid);
    System.out.println("bookname==="+bookname);
    System.out.println("bookauthor==="+bookauthor);
    System.out.println("bookkind==="+bookkind);
    System.out.println("bookdscribe==="+bookdscribe);
    System.out.println("bookpicture==="+bookpicture);
    System.out.println("bookwhere==="+bookwhere);

    Boolean b=myUserService.AddBookFromAdmin(bookid,bookname,bookauthor,bookkind,
            bookdscribe,bookpicture,bookwhere);

    if(b){
      return "success";
    }else {
      return "error";
    }
  }

  @RequestMapping(value = "/register")
  public String register(){

      return "register";
  }
    @RequestMapping(value = "/zhuce")
    public String zhuce(@RequestParam(name = "adminname") String adminname,
                        @RequestParam(name = "password") String password,
                        @RequestParam(name = "mpassword") String mpassword
                        ){

        System.out.println("adminname"+adminname);
        if(password.equals(mpassword)){
          myUserService.registerAdmin(adminname,password);
          return "success";
        }else {
          return "error";
        }


    }


}
