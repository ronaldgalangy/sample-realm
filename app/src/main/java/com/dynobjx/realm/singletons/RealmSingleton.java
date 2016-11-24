package com.dynobjx.realm.singletons;

import io.realm.Realm;

/**
 * Created by root on 11/24/16.
 */
public class RealmSingleton {
    private Realm realm = Realm.getDefaultInstance();

    private static RealmSingleton ourInstance = new RealmSingleton();

    public static RealmSingleton getInstance() {
        return ourInstance;
    }

    private RealmSingleton() {

    }

    public Realm getRealm() {
        return realm;
    }

}
