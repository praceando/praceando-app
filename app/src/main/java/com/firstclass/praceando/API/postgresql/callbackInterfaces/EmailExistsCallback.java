package com.firstclass.praceando.API.postgresql.callbackInterfaces;

import com.firstclass.praceando.API.postgresql.entities.EmailIsInUse;

public interface EmailExistsCallback {
    void onSuccess(EmailIsInUse message);
    void onError(String errorMessage);
}
