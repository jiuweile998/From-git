package com.test;

import java.util.ArrayList;

public class BookShelfList {

    public ArrayList<BookShelf> bookShelves=new ArrayList<>();//定义一个用于存放菜品的数组列表

    //定义获取菜品列表长度的方法
    public int getDishQuantity(){
        return bookShelves.size();
    }

    //定义根据菜品的索引值获取菜品的方法
    public BookShelf getDishByIndex(int index){
        return bookShelves.get(index);
    }

    //定义根据菜名获取菜品的方法
//    public BookShelf getDishByName(String dishName){
//        for(int i=0;i<bookShelves.size();i++){  //遍历整个菜品列表
//            BookShelf bookShelf = bookShelves.get(i);
//            if(dishName.equals(bookShelf.bookShelf_a)) //匹配要查找菜品名称与列表中菜名
//                return theDish;
//        }
//        return null;
//    }

}
