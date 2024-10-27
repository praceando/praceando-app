package com.firstclass.praceando.API.postgresql.callbackInterfaces;

import com.firstclass.praceando.API.postgresql.entities.NicknameIsInUse;

public interface EmailExistsCallback {
    void onSuccess(NicknameIsInUse message);
    void onError(String errorMessage);
}
