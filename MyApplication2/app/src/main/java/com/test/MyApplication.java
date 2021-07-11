package com.test;

import android.app.Application;
import android.widget.ImageView;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class MyApplication extends Application { //用于保存全局变量
    public User user;
    public Book book;
    //进入主页后，用于适配器
    public BookShelfList bookShelfList=new BookShelfList();
    //点击具体小说，进入具体介绍后，要更改的图片
    public ImageView imageView_change;

    //用于点击小说后展现具体数据
    public  BookShelf novel_bookshelf;

    //小说文本内容
    public String bookdata=null;

    //在出用搜索，并存储，用于适配器
    public BookShelfList bookShelfList_search;

    public List<Map<String,Object>> search_list=new ArrayList<>();

    //用于点击历史或玄幻存储数据
    public List<Map<String,Object>> all_book_list=new ArrayList<>();

    public int delet_bookid=0;  //要删除或者添加的书id
    public String book_delet_add;//表示删除或添加的字符串




}
