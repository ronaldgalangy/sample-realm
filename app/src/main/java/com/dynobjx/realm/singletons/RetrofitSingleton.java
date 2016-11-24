package com.dynobjx.realm.singletons;

import com.dynobjx.realm.interfaces.ApiInterface;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by rsbulanon on 8/30/16.
 */
public class RetrofitSingleton {

    private static RetrofitSingleton RETROFIT_SINGLETON = new RetrofitSingleton();
    private Retrofit retrofit;
    private ApiInterface apiInterface;

    private RetrofitSingleton() {

    }

    public static RetrofitSingleton getInstance() {
        return RETROFIT_SINGLETON;
    }

    public void initRetrofit(final String baseUrl, final OkHttpClient okHttpClient) {
        retrofit = new Retrofit.Builder()
                .baseUrl(baseUrl)
                .client(okHttpClient)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .build();

        apiInterface = retrofit.create(ApiInterface.class);

    }

    public Retrofit getRetrofit() {
        return retrofit;
    }

    public ApiInterface getApiInterface() {
        return apiInterface;
    }
}
