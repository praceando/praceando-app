package com.firstclass.praceando.API.postgresql.callbackInterfaces;

import com.firstclass.praceando.API.postgresql.entities.NicknameIsInUse;

public interface NicknameExistsCallback {
    void onSuccess(NicknameIsInUse message);
    void onError(String errorMessage);
}
