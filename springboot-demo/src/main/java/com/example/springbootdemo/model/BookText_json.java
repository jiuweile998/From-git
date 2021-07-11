package com.example.springbootdemo.model;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

/**
 * @author wlg
 * @create_time 2021-06-26 上午 10:51
 * @function
 */
public class BookText_json {

    public static String MakeTxtToJson(String url) throws IOException {
        InputStreamReader ins = new InputStreamReader(new FileInputStream(url));
        BufferedReader br = new BufferedReader(ins);
        //存放bean对象
        // List<ClassA> tlist = new ArrayList<ClassA>();

        //读取txt
        String line = null;
       // List<String> list = new ArrayList<String>();
        String list="";
        while((line = br.readLine()) != null) {
            //list.add(line);
            list=list+line;
        }
        br.close();
        System.out.println("============list文本内容======="+list);
        //txt的每一行相当于一条数据，split按空格作分隔符进行拆分。\\s+是正则表达式。
//        for (String str : list) {
//
//            //String[] arrStr = str.split("。");
//            System.out.println("=============="+str);
//
////            ClassA classA = new ClassA();
////            classA.setNo(arrStr[0]);
////            classA.setText(arrStr[1]);
////            tlist.add(classA);
//        }
        return list;
    }

}
