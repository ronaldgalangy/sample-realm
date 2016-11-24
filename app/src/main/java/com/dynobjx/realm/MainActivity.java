package com.dynobjx.realm;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.Button;

import com.dynobjx.realm.helpers.ApiRequestHelper;
import com.dynobjx.realm.helpers.LogHelper;
import com.dynobjx.realm.interfaces.ApiAction;
import com.dynobjx.realm.interfaces.OnApiRequestListener;
import com.dynobjx.realm.realm.IRealmTransactionCallback;
import com.dynobjx.realm.realm.controller.PostController;
import com.dynobjx.realm.realm.model.Post;
import com.google.gson.Gson;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import io.realm.Realm;
import io.realm.RealmList;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        new ApiRequestHelper(new OnApiRequestListener() {
            @Override
            public void onApiRequestStart(ApiAction apiAction) {
                System.out.println(apiAction);
            }

            @Override
            public void onApiRequestFailed(ApiAction apiAction, Throwable t) {
                t.printStackTrace();
            }

            @Override
            public void onApiRequestSuccess(ApiAction apiAction, Object result) {
                Realm realm = Realm.getDefaultInstance();
                RealmList<Post> posts = (RealmList<Post>) result;
                realm.beginTransaction();
                realm.copyToRealmOrUpdate(posts);
                realm.commitTransaction();
                realm.close();
            }
        }).getAllPost();
    }

    @OnClick(R.id.btn_show_post)
    public void onClick() {
        PostController postController = new PostController(Post.class, new IRealmTransactionCallback() {
            @Override
            public void onStart(Class clz) {

            }

            @Override
            public void onSuccess(Class clz, Object result) {
                LogHelper.log("post", "onSuccess");
                final RealmList<Post> posts = (RealmList<Post>) result;
                for (Post post : posts) {
                    LogHelper.log("post", new Gson().toJson(post));
                }
            }

            @Override
            public void onError(Class clz, Throwable t) {
                t.printStackTrace();
                LogHelper.log("post", "onError : " + t.getMessage());
            }
        });
        postController.getAll();
        /*final RealmList<Post> posts = postController.getAll();
        if (posts != null) {
            LogHelper.log("post", "not null");
            for (Post post : posts) {
                LogHelper.log("post", "click " + new Gson().toJson(post));
            }
        } else {
            LogHelper.log("post", "no data in db.");
        }*/
    }
}
