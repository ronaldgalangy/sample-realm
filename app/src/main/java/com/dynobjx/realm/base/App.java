package com.dynobjx.realm.base;

import android.app.Application;

import com.dynobjx.realm.helpers.LogHelper;
import com.dynobjx.realm.singletons.RetrofitSingleton;

import java.io.File;

import io.realm.Realm;
import okhttp3.Cache;
import okhttp3.OkHttpClient;
import okhttp3.Request;

/**
 * Created by root on 11/2/16.
 */
public class App extends Application {

    @Override
    public void onCreate() {
        super.onCreate();

        /** initialize okhttp client */
        File cacheDir = getExternalCacheDir();
        if (cacheDir == null) {
            cacheDir = getCacheDir();
        }
        final Cache cache = new Cache(cacheDir, 10 * 1024 * 1024);
        final OkHttpClient okHttpClient = new OkHttpClient.Builder()
                .addInterceptor(
                        chain -> {
                            Request request = chain.request();
                            LogHelper.log("api","performing url --> " + request.url());
                            return chain.proceed(request);
                        })
                .cache(cache)
                .build();

        /** initialize picasso */
        /*final PicassoSingleton picassoSingleton = PicassoSingleton.getInstance();
        picassoSingleton.initPicasso(this, okHttpClient);*/

        /** initialize retrofit */
        final RetrofitSingleton retrofitSingleton = RetrofitSingleton.getInstance();
        retrofitSingleton.initRetrofit(AppConstants.HOST_URL, okHttpClient);
        Realm.init(this);
    }
}
