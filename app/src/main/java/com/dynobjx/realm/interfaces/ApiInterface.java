package com.dynobjx.realm.interfaces;

import com.dynobjx.realm.base.AppConstants;
import com.dynobjx.realm.realm.model.Post;

import io.realm.RealmList;
import retrofit2.http.GET;
import rx.Observable;

/**
 * Created by rsbulanon on 9/5/16.
 */
public interface ApiInterface {

    @GET(AppConstants.POST)
    Observable<RealmList<Post>> getAllPost();
}
