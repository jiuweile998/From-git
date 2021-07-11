package com.test;




import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class Util {

    private static Object List;

    public static String[]  getStringToList(String str, String p){
        String[] split1=str.split(p);

        for(String string1:split1){
            System.out.println(string1);
            System.out.println("--切割后----");
        }
        System.out.println("===split1的长度=="+split1.length);
        return split1;
    }

    public static ArrayList<Map<String,Object>> replaceString(String json){

       ArrayList<Map<String,Object>> list_search = new ArrayList<>();


             try{


                 JSONArray jsonArray=JSONArray.fromObject(json);
                 for(int i=0;i<jsonArray.size();i++){
                     Map<String,Object> map=new HashMap<>();
                     Object obj=jsonArray.get(i);
                     JSONObject jsonObject= JSONObject.fromObject(obj);
                     map.put("image", R.drawable.shujia);//默认图片
                     map.put("author", jsonObject.getString("bookAuthor"));//作者
                     map.put("dec",jsonObject.getString("bookDscribe"));//描述
                     map.put("dele_id", jsonObject.getString("bookId"));//书的id
                     map.put("pictureURL",jsonObject.getString("bookPicture"));//具体图片
                     System.out.println("map===="+map);
                     list_search.add(map);
                 }
                 System.out.println("list_search的个数==="+list_search.size());
             }catch (Exception e){
                 e.printStackTrace();
             }
             return list_search;

    }
}
