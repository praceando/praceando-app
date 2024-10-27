package com.firstclass.praceando.API.postgresql.callbackInterfaces;
import com.firstclass.praceando.entities.User;

public interface UserByIdCallback {
    void onSuccess(User user);
    void onError(String errorMessage);
}
