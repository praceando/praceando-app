package com.firstclass.praceando.API.postgresql.callbackInterfaces;

import com.firstclass.praceando.entities.Locale;

import java.util.List;

public interface LocalesCallback {
    void onSuccess(List<Locale> locales);
    void onError(String errorMessage);
}
