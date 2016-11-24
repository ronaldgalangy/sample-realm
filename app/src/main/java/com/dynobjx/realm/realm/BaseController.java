package com.dynobjx.realm.realm;

import android.os.Looper;

import com.dynobjx.realm.realm.model.Post;

import io.realm.Realm;
import io.realm.RealmList;
import io.realm.RealmObject;
import io.realm.RealmQuery;
import io.realm.RealmResults;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.schedulers.Schedulers;

/**
 * Created by root on 11/9/16.
 */

public abstract class BaseController<T extends RealmObject> {

    private Realm realm;
    private Class<T> clz;
    private IRealmTransactionCallback iRealmTransactionCallback = null;

    public BaseController(Class<T> clz, IRealmTransactionCallback iRealmTransactionCallback){
        this.clz = clz;
        this.iRealmTransactionCallback = iRealmTransactionCallback;
        this.realm = Realm.getDefaultInstance();
    }

    public IRealmTransactionCallback getiRealmTransactionCallback() {
        return iRealmTransactionCallback;
    }

    public void getAll() {
        final RealmQuery<T> query = realm.where(clz);
        query.findAllAsync().asObservable().observeOn(AndroidSchedulers.mainThread()).subscribeOn(Schedulers.io())
                .unsubscribeOn(AndroidSchedulers.mainThread())
                .subscribe(ts -> {
                    final RealmList<T> list = new RealmList<T>();
                    list.addAll(ts.subList(0, ts.size()));
                    getiRealmTransactionCallback().onSuccess(clz, list);
                }, throwable -> {
                    getiRealmTransactionCallback().onError(clz, throwable);
                });
    }
}
