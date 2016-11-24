package com.dynobjx.realm.realm.controller;

import com.dynobjx.realm.realm.BaseController;
import com.dynobjx.realm.realm.IRealmTransactionCallback;
import com.dynobjx.realm.realm.model.Post;

/**
 * Created by root on 11/24/16.
 */

public class PostController extends BaseController<Post> {

    public PostController(Class<Post> clz, IRealmTransactionCallback iRealmTransactionCallback) {
        super(clz, iRealmTransactionCallback);
    }


    @Override
    public void getAll() {
        super.getAll();
    }
}
