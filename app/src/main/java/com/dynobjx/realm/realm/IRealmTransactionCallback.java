package com.dynobjx.realm.realm;

import io.realm.RealmObject;

/**
 * Created by root on 11/24/16.
 */

public interface IRealmTransactionCallback<T extends RealmObject> {
    void onStart(Class<T> clz);
    void onSuccess(Class<T> clz, Object result);
    void onError(Class<T> clz, Throwable t);
}
