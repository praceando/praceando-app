package com.firstclass.praceando.API.mongo.callbacksInterfaces;

import com.firstclass.praceando.API.mongo.entities.Mean;

public interface EventMeanInterface {
    void onSuccess(Mean mean);
    void onError(String errorMessage);
}
