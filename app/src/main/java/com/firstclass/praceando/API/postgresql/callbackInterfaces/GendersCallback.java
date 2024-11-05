package com.firstclass.praceando.API.postgresql.callbackInterfaces;

import com.firstclass.praceando.entities.Gender;

import java.util.List;

public interface GendersCallback {
    void onSuccess(List<Gender> genders);
    void onError(String errorMessage);
}
