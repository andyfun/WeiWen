package com.example;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.serializer.SerializerFeature;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import jdk.nashorn.internal.ir.debug.JSONWriter;

public class MyClass {
    public static void main(String[] args) {
        Document document = null;
        try {
            document = Jsoup.parse(new File("/Users/zt-2010271/Documents/cc.htm"),"utf-8");
        } catch (Exception e) {
            e.printStackTrace();
        }

//##################### 内容
//        Elements elements  = document.select("div#article");
//
//        elements.select("div.likeitem").remove();
//        Elements elementsContent = elements.select("div.content");
//        Elements elementsTitle = elements.select("h1");
//        Elements elementsAuthor = elements.select("div.info").select("a");
//
//        System.out.println(" 集合前："+elementsContent.size());
//        System.out.println("标题："+elementsTitle.toString());
//        System.out.println("作者："+elementsAuthor.toString());
//
//       System.out.println("内容：\n"+elementsContent.toString());
//       System.out.println("图片：\n"+elementsContent.select("div").select("img").attr("src").toString());

//######################详情

//        Elements elements = document.select("ul.alist");
//        Elements elementsLi =  elements.select("li");
//
//        System.out.println("元素集合："+elementsLi.size());
//        for (int i = 0; i < elementsLi.size(); i++) {
//            Element element = elementsLi.get(i);
//
//            if (element.hasClass("page")) return;//过滤页数
//
//            Elements elementsA = element.select("a");
//            if (elementsA != null && elementsA.size()>0){
//                for (int j = 0; j <elementsA.size() ; j++) {
//                    Element elementTitle = elementsA.get(j);
//                    if (elementTitle.hasAttr("target")){
//                       // System.out.print(" 标题：" + elementTitle.text()+",");
//                        System.out.println("detailurl= "+ elementTitle.attr("abs:href"));
//                    }
//                }
//            }
//            Elements elementsContent = element.select("div").select(".desc");
//            Elements elementDate = element.select("div").select(".info");
//           // System.out.print(" 日期=" + elementDate.text()+"\n");
//            System.out.print(" 内容=" + elementsContent.toString()+"\n");
//        }
//
//
//        String url = "http://www.sanwen8.cn/subject/1413619/";
//        String[] str = url.split("/");
//        for (int i = 0; i <str.length ; i++) {
//
//            System.out.println(i+"="+str[i]);
//        }

        //
//        Elements elements  = document.select("div.listbox");
//        Elements elementsContent = elements.select("ul.e2").select("li");
//        System.out.println(" 集合前："+elementsContent.size());
//
//        for (int i = 0; i <elementsContent.size() ; i++) {
//            if (i== elementsContent.size()-1)break;
//            Element  element =  elementsContent.get(i);
//            Elements elementTitle = element.select("span.title").select("a");
//            String url = elementTitle.attr("href");
//            Elements  elementintro1 =  element.select("p.intro1");
//            Elements  elementClickNum =  element.select("span").select(".intro1");
//            if (elementTitle.text() != null){
//                System.out.print("标题 ： "+elementTitle.text()+"\n");
//                //System.out.print("URL ： "+url+"\n");
//                System.out.print("内容 ： "+elementintro1+"\n");
//            }
//
//        }
        //        Elements elements  = document.select("div#article");
//

//        Elements elements  = document.select("div.viewbox");
//        Elements elementsContent = elements.select("div.content");
//        elementsContent.select("script").remove();
//        elementsContent.select("div.gg").remove();
//        elementsContent.select("div#bdshare").remove();
//        elementsContent.select("div.bds_more").remove();
//        elementsContent.select("span").remove();
//        Elements elementsTitle = elements.select("div.title");
//        //Elements elementsAuthor = elements.select("div.info").select("a");
//
//        System.out.println(" 集合前："+elementsContent.size());
//        System.out.println("标题："+elementsTitle.toString());
//        //System.out.println("作者："+elementsAuthor.toString());
//
//       System.out.println("内容：\n"+elementsContent.toString());
     //   int x=1+(int)(Math.random()*10);
       // System.out.println("x="+x );

        //######################数据报表
        int  money = 0;
        Elements elements  = document.select("table").select("tbody");
        Elements elementsContent = elements.get(0).select("tr");
        System.out.println(" 集合前："+elementsContent.size());
        List<GameDate> gameDateList = new ArrayList<GameDate>();
        for (int i = 0; i <elementsContent.size() ; i++) {
            //if (i== elementsContent.size()-1)break;
            Element  element =  elementsContent.get(i);
            Elements elementTh = element.select("td");
            GameDate gameDate = new GameDate();
            String line = "";
            for (int j=0;j<elementTh.size();j++){
               Element  th = elementTh.get(j);
               //System.out.print("td = "+th.text());
                line += th.text()+",";

            }
            line +="\n";
            String[] strs = line.split("\\,");

            gameDate.setChannelID(Integer.valueOf(strs[0]));
            gameDate.setDataTime(strs[1]);
            gameDate.setProductIDName(strs[2]);
            gameDate.setApkPackageID(Integer.valueOf(strs[3]));
            gameDate.setApkPackageName(strs[4]);
            gameDate.setClickNum(Integer.valueOf(strs[5]));
            gameDate.setAcceleratorNum(Integer.valueOf(strs[6]));
            gameDate.setApkActivationNumber(Integer.valueOf(strs[7]));
            gameDate.setNewRegisterNumber(Integer.valueOf(strs[8]));
            gameDate.setMoneyDay(Integer.valueOf(strs[9]));

            //过滤都是0的
//            if (gameDate.getAcceleratorNum()==0 && gameDate.getApkActivationNumber()==0&&gameDate.getNewRegisterNumber()==0&&gameDate.getMoneyDay()==0){
//                System.out.println("都是0");
//            }else {
//                gameDateList.add(gameDate);
//                money += gameDate.getMoneyDay();
//            }

            //过滤都是830 和831
            if (gameDate.getApkPackageID()==830 || gameDate.getApkPackageID()==831){

                gameDateList.add(gameDate);
                money += gameDate.getMoneyDay();
            }else {
                System.out.println("apkID="+gameDate.getApkPackageID());
            }


        }
        System.out.println("money:"+money);
        System.out.println("集合大小："+gameDateList.size());
        System.out.println("{\"results\":[");

        for (GameDate gameDate : gameDateList){
             String str =   JSON.toJSONString(gameDate);
            System.out.println(str+",");
        }
        System.out.println("]}");
    }
}
