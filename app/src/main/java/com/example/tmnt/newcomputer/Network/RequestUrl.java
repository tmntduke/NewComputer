package com.example.tmnt.newcomputer.Network;

import com.example.tmnt.newcomputer.Model.NewsInfo;

import okhttp3.Response;
import retrofit2.http.GET;
import retrofit2.http.Query;
import rx.Observable;

/**
 * Created by tmnt on 2017/3/7.
 */
public interface RequestUrl {

    @GET("it")
    Observable<NewsInfo> getITUrl(@Query("key") String key, @Query("num")String num);

}
