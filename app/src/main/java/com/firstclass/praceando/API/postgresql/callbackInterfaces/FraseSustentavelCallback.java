package com.firstclass.praceando.API.postgresql.callbackInterfaces;
import com.firstclass.praceando.API.postgresql.entities.FraseSustentavel;

public interface FraseSustentavelCallback {
    void onSuccess(FraseSustentavel fraseSustentavel);
    void onError(String errorMessage);
}
