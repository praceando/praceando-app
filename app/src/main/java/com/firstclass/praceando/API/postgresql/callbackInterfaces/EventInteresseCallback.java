package com.firstclass.praceando.API.postgresql.callbackInterfaces;
import com.firstclass.praceando.API.postgresql.entities.InteresseResponse;

public interface EventInteresseCallback {
    void onSuccess(InteresseResponse interesseResponse);
    void onError(String errorMessage);
}
