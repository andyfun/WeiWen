package com.guagua.knowledge.Network;

import com.guagua.knowledge.Bean.GankNews;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

/**
 * Created by tusalin on 9/6/2016.
 */

public interface GankService {
    @GET("data/{type}/{count}/{pageIndex}")
    Call<GankNews> getGankNews(@Path("type") String type,
                               @Path("count") int count,
                               @Path("pageIndex") int pageIndex
    );

    @GET("{sanwen}/{jd}/")
    Call<String>   getProseList(@Path("sanwen") String sanwen,
                                @Path("jd") String titleIndex,
                                        @Query("p") int  page

    );//获取散文分类 （散文，诗歌等）

    @GET("subject/{id}/")
    Call<String>  getProseDetail(@Path("id")String id);

    @GET("{jingdian}/list_{page}.html")
    Call<String>  getArtcleList(@Path("jingdian")String jingdian,
                                @Path("page") String page);

    @GET("{jinpath}")
    Call<String>  getArtcleDetail(@Path("jinpath")String jingdian);
}
