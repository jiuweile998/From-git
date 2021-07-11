package com.example.springbootdemo;

import com.alibaba.fastjson.JSON;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

@SpringBootTest
class SpringbootDemoApplicationTests {

//    class ClassA {
//        public String no;
//        public String text;
//        public String getNo() {
//            return no;
//        }
//        public void setNo(String no) {
//            this.no = no;
//        }
//        public String getText() {
//            return text;
//        }
//        public void setText(String text) {
//            this.text = text;
//        }
//    }
    @Test
    void contextLoads() throws IOException {

        InputStreamReader ins = new InputStreamReader(new FileInputStream("D:\\text_tojson\\12.txt"));
        BufferedReader br = new BufferedReader(ins);
        //存放bean对象
       // List<ClassA> tlist = new ArrayList<ClassA>();

        //读取txt
        String line = null;
        List<String> list = new ArrayList<String>();
        while((line = br.readLine()) != null) {
            list.add(line);
        }
        br.close();

        String[] arrStr = new String[0];
        //txt的每一行相当于一条数据，split按空格作分隔符进行拆分。\\s+是正则表达式。
        for (String str : list) {

             arrStr = str.split("。");
            System.out.println("=============="+str);
//            ClassA classA = new ClassA();
//            classA.setNo(arrStr[0]);
//            classA.setText(arrStr[1]);
//            tlist.add(classA);
        }
        for(int i=0;i<arrStr.length;i++){
            System.out.println("------------"+arrStr[i]);
        }
        //JSON.toJSONString()方法：将对象数组（JSON格式的字符串也可以）转换成JSON数据。
//        String json = JSON.toJSONString(tlist);
//        System.out.println(json);
//
//        //创建新文件
//        File txtToJson = new File("D:\\text_tojson\\zh-cn.json");
//        txtToJson.createNewFile();
//        BufferedWriter out = new BufferedWriter(new FileWriter(txtToJson));
//        out.write(json);
//        out.flush(); // 把缓存区内容压入文件
//        out.close(); // 最后记得关闭文件

    }

}
