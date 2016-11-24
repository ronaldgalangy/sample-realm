package com.dynobjx.realm.helpers;

import com.dynobjx.realm.interfaces.ApiAction;
import com.dynobjx.realm.interfaces.OnApiRequestListener;
import com.dynobjx.realm.realm.model.Post;
import com.dynobjx.realm.singletons.RetrofitSingleton;

import io.realm.RealmList;
import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by rsbulanon on 9/5/16.
 */
public class ApiRequestHelper {

    private OnApiRequestListener onApiRequestListener;
    private RetrofitSingleton retrofitSingleton;

    /**
     * initialize ApiRequestHelper
     */
    public ApiRequestHelper(OnApiRequestListener onApiRequestListener) {
        this.onApiRequestListener = onApiRequestListener;
        this.retrofitSingleton = RetrofitSingleton.getInstance();
    }

    /**
     * handle api result using lambda
     *
     * @param action     identification of the current api request
     * @param observable actual process of the api request
     */
    private void handleObservableResult(final ApiAction action, final Observable observable) {
        observable.observeOn(AndroidSchedulers.mainThread()).subscribeOn(Schedulers.io())
                .subscribe(result -> onApiRequestListener.onApiRequestSuccess(action, result),
                        throwable -> onApiRequestListener.onApiRequestFailed(action, (Throwable) throwable),
                        () -> LogHelper.log("api", "api request completed --> " + action));
    }

    public void getAllPost() {
        onApiRequestListener.onApiRequestStart(ApiAction.GET_POST);
        final Observable<RealmList<Post>> observable = this.retrofitSingleton.getApiInterface().getAllPost();
        handleObservableResult(ApiAction.GET_POST, observable);
    }
}
