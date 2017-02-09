package com.example;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.File;
import java.io.IOException;
import java.util.Random;

public class MyClass {
    public static void main(String[] args) {
        Document document = null;
        try {
           // document = Jsoup.parse(new File("/Users/zt-2010271/Downloads/test1.htm"),"gbk");
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
        int x=1+(int)(Math.random()*10);
        System.out.println("x="+x );
    }
}
