package com.guagua.knowledge.Network;

import android.content.Context;
import android.text.TextUtils;
import android.util.Log;

import com.guagua.knowledge.Bean.GankNews;
import com.guagua.knowledge.Bean.GuaArticle;
import com.guagua.knowledge.Callback.HtmlCallback;
import com.guagua.knowledge.Callback.RetrofitCallBack;
import com.guagua.knowledge.GankUrl;
import com.guagua.knowledge.MyApplication;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import okhttp3.Cache;
import okhttp3.CacheControl;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;


/**
 * Created by tusalin on 9/6/2016.
 */

public class GankRetrofit {

     static Interceptor interceptor = new Interceptor() {
         @Override
         public okhttp3.Response intercept(Chain chain) throws IOException {
             CacheControl cacheControl = new CacheControl.Builder()
                     .maxAge(4, TimeUnit.HOURS)
                     .maxStale(4,TimeUnit.HOURS)
                     .build();
             Request request = chain.request();
//             request.newBuilder()
//                     .cacheControl(cacheControl)
//                     .build();
             okhttp3.Response response = chain.proceed(request);

             return response.newBuilder()
                     .header("Cache-Control","public,max-age="+60*60*24)
                     .removeHeader("Pragma")
                     .build();
         }
     };

    static Cache cache = new Cache(new File(MyApplication.getContext().getCacheDir(),"picasso-cache"),
            1024 * 1024 * 10);

    static HttpLoggingInterceptor httpLoggingInterceptor = new HttpLoggingInterceptor()
            .setLevel(HttpLoggingInterceptor.Level.HEADERS);


   static OkHttpClient okHttpClient = new OkHttpClient.Builder()
           .addNetworkInterceptor(interceptor)
           .addInterceptor(httpLoggingInterceptor)
           .cache(cache)
           .build();

//    public static Retrofit retrofit = new Retrofit.Builder()
//            .baseUrl(GankUrl.GANK_API_URL)
//            .addConverterFactory(GsonConverterFactory.create())
//            .client(okHttpClient)
//            .build();

    public static Retrofit retrofitHtml = new Retrofit.Builder()
            .baseUrl(GankUrl.GuaGua_API_URL)
            .addConverterFactory(new ToStringConverterFactory("utf-8"))
            .client(okHttpClient)
            .build();
    public static Retrofit retrofitHtmlXIUQQ = new Retrofit.Builder()
            .baseUrl(GankUrl.GuaGua_API_XIUQQ)
            .addConverterFactory(new ToStringConverterFactory("gbk"))
            .client(okHttpClient)
            .build();

    public static Retrofit modongRetro = new Retrofit.Builder()
            .baseUrl(GankUrl.Modong_URL)
            .addConverterFactory(new ToStringConverterFactory("gbk"))
            .client(okHttpClient)
            .build();

//    public static void getAllResult(final String keyType, final int count, final int pageIndex,
//                                    final RetrofitCallBack<GankNews> retrofitCallBack){
//        Log.d("allResull ","keyType = "+keyType+",count="+count+",pageIndex="+pageIndex);
//        Call<GankNews> call = retrofit.create(GankService.class).getGankNews(keyType,count,pageIndex);
//        call.enqueue(new Callback<GankNews>() {
//            @Override
//            public void onResponse(Call<GankNews> call, Response<GankNews> response) {
//                if(response.isSuccessful()){
//                    retrofitCallBack.retrofitSuccess(keyType,response.body());
//                }else {
//                    retrofitCallBack.retrofitFailure(keyType,"call.enqueue onSuccess but response not isSuccesssful");
//                }
//            }
//
//            @Override
//            public void onFailure(Call<GankNews> call, Throwable t) {
//                retrofitCallBack.retrofitFailure(keyType,"onFailure:" + t.getMessage());
//            }
//        });
//    }

    /**
     *  请求散文列表
     */
    public static void requestProseList(String sanwen,
                                        String titleIndex,
                                     final int pageIndex ,final HtmlCallback  callback){



        Call<String> call = retrofitHtml.create(GankService.class).getProseList(sanwen,titleIndex,pageIndex);
        call.enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {

                String body = response.body();
                //Log.d("","body = "+body);
                callback.onSuccessParseHtml(parseHtml(body));

            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {

                callback.onFailedParseHtml(t.toString());

            }
        });

    }
    public  static void requestModong(){
        Call<String> call = modongRetro.create(GankService.class).getModong("modong0321","modong170321");
        call.enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {

                String body = response.body();
                Log.d("","body = "+body);


            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {
              Log.d("",t.getLocalizedMessage());

            }
        });


    }
    /**
     * 散文详情
     * @param callback
     */
    public static void requestProseDetail(String id,
                                        final HtmlCallback  callback){

        Call<String> call = retrofitHtml.create(GankService.class).getProseDetail(id);
        call.enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {

                String body = response.body();
                Log.d("","body = "+body);

                callback.onSuccessParseHtml(parseDetail(body));

            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {

                callback.onFailedParseHtml(t.toString());

            }
        });

    }

    /**
     * 经典文章
     * @param jingdian
     * @param page
     * @param callback
     */
    public static void requestArticleList(String jingdian,String page,final HtmlCallback callback){
        Call<String> call = retrofitHtmlXIUQQ.create(GankService.class).getArtcleList(jingdian,page);
        call.enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {

                String body = response.body();
               // Log.d("","body = "+body);
                callback.onSuccessParseHtml(parseArticleList(body));

            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {

                callback.onFailedParseHtml(t.toString());

            }
        });
    }

    public static void requestArticleDetail(String jingdian,final HtmlCallback callback){
        Call<String> call = retrofitHtmlXIUQQ.create(GankService.class).getArtcleDetail(jingdian);
        call.enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {

                String body = response.body();
                // Log.d("","body = "+body);
                callback.onSuccessParseHtml(parseArticleDetail(body));

            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {

                callback.onFailedParseHtml(t.toString());

            }
        });
    }


    //列表
    public static List<GuaArticle> parseHtml(final String response){
        Document document = Jsoup.parse(response);
        Elements elements = document.select("ul.alist");
        //  System.out.println(document.body().toString());
        Elements elementsLi =  elements.select("li");
        //List<GuaArticle> list = parseElementsToList(elementsLi);
        List<GuaArticle> list = new ArrayList<>();
        if ( elementsLi != null && elementsLi.size() > 0) {
            for (int i = 0; i < elementsLi.size(); i++) {
                GuaArticle guaArticle = new GuaArticle();

                Element element = elementsLi.get(i);

                if (element.hasClass("page")) break;//过滤页数

                Elements elementsA = element.select("a");

                if (elementsA != null && elementsA.size()>0){
                    for (int j = 0; j <elementsA.size() ; j++) {
                        Element elementTitle = elementsA.get(j);
                        if (elementTitle.hasAttr("target")){
                            System.out.print(" 标题：" + elementTitle.text()+",");
                            guaArticle.setTitle(elementTitle.text());
                            guaArticle.setDetailUrl(elementTitle.attr("href").toString());
                            System.out.println("url = "+elementTitle.attr("href").toString());
                        }
                    }
                }

                Elements elementsContent = element.select("div").select(".desc");
                Elements elementDate = element.select("div").select(".info");
                System.out.print(" 日期=" + elementDate.text()+"\n");

                guaArticle.setContent(elementsContent.toString());
                guaArticle.setTime(elementDate.text());

                list.add(guaArticle);
            }

        }
        Log.e("parse"," 解析html ："+list.size());
        return list;
    }

    public static List<GuaArticle>  parseArticleList(String response){
        if (TextUtils.isEmpty(response))return null;
        Document document = null;
        try {
            document = Jsoup.parse(response);
        } catch (Exception e) {
            e.printStackTrace();
        }
        Elements elements  = document.select("div.listbox");
        if (elements == null || elements.size()<=0)return null;

        Elements elementsContent = elements.select("ul.e2").select("li");
        System.out.println(" 集合前："+elementsContent.size());
        List<GuaArticle> list = new ArrayList<>();
        for (int i = 0; i <elementsContent.size() ; i++) {
            GuaArticle guaArticle = new GuaArticle();
            if (i== elementsContent.size()-1) break;
            Element  element =  elementsContent.get(i);
            Elements elementTitle = element.select("span.title").select("a");
            String url = elementTitle.attr("href");
            Elements  elementInfo =  element.select("p.intro1");
            //System.out.print("标题 \n： "+elementTitle+"\n");
            //System.out.print("内容 \n： "+elementInfo+"\n");

            guaArticle.setDetailUrl(url);
            guaArticle.setTitle(elementTitle.toString());
            guaArticle.setContent(elementInfo.toString());

            list.add(guaArticle);
        }
        return list;
    }
    //秀文网 文章详情
    public static List<GuaArticle>  parseArticleDetail(String response){
        if (TextUtils.isEmpty(response))return null;
        Document document = null;
        try {
            document = Jsoup.parse(response);
        } catch (Exception e) {
            e.printStackTrace();
        }
        Elements elements  = document.select("div.viewbox");
        Elements elementsContent = elements.select("div.content");
        elementsContent.select("script").remove();
        elementsContent.select("div.gg").remove();
        elementsContent.select("div#bdshare").remove();
        elementsContent.select("div.bds_more").remove();
        elementsContent.select("span").remove();
        Elements elementsTitle = elements.select("div.title");

        List<GuaArticle> list = new ArrayList<>();
        GuaArticle guaArticle = new GuaArticle();

        guaArticle.setTitle(elementsTitle.toString());
        guaArticle.setContent(elementsContent.toString());

        list.add(guaArticle);

        return list;
    }

    public static List<GuaArticle> parseDetail(final String response){

        if (TextUtils.isEmpty(response))return null;


        List<GuaArticle> listGua = new ArrayList<>();
        GuaArticle gua = new GuaArticle();
        Document document = Jsoup.parse(response);
        Elements elements  = document.select("div#article");

        elements.select("div.likeitem").remove();
        Elements elementsContent = elements.select("div.content");
        Elements elementsTitle = elements.select("h1");
        Elements elementsAuthor = elements.select("div.info").select("a");


        Elements elementImgs = elementsContent.select("div").select("img");
        if (elementImgs != null && elementImgs.size()>0){
            String img = elementImgs.attr("src").toString();
            gua.setImgUrl(img);
            elementsContent.select("div").select("img").remove();
        }

        System.out.println("标题："+elementsTitle.toString());
        System.out.println("作者："+elementsAuthor.toString());

        System.out.println("内容：\n"+elementsContent.toString());

        gua.setTitle(elementsTitle.toString());
        gua.setContent(elementsContent.toString());
        gua.setAuthor("作者："+elementsAuthor.toString());
        listGua.add(gua);
        return listGua;
    }

}
