package com.example.ilias.ntgemployeeiosystem.data.source.remote;

/**
 * Created by ilias on 24/02/2018.
 */

public interface SuccessfulResponseWithResultCallback<T> {
    void onSuccess(T result);
}
