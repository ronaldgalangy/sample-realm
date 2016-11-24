package com.dynobjx.realm.interfaces;


/**
 * Created by rsbulanon on 9/5/16.
 */
public interface OnApiRequestListener {

    /**
     * triggered when an api request starts
     *
     * @param apiAction identification of which api request is starting
     * */
    void onApiRequestStart(final ApiAction apiAction);

    /**
     * triggered when an api request fails
     *
     * @param apiAction identification of which api request fails
     * @param t contains the cause of api request failure
     * */
    void onApiRequestFailed(final ApiAction apiAction, final Throwable t);

    /**
     * triggered when an api request was successfully finished
     *
     * @param apiAction identification of which api request succeed
     * @param result contains the result/response of api
     * */
    void onApiRequestSuccess(final ApiAction apiAction, final Object result);
}
