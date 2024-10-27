package com.firstclass.praceando.API.mongo.callbacksInterfaces;

import com.firstclass.praceando.API.mongo.entities.ConquistaUser;

import java.util.List;

public interface UserGoalsCallback {
    void onSuccess(List<ConquistaUser> conquistaUsers);
    void onError(String errorMessage);
}
