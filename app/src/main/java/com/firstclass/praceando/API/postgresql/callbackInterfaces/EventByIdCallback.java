package com.firstclass.praceando.API.postgresql.callbackInterfaces;

import com.firstclass.praceando.API.postgresql.entities.Evento;

public interface EventByIdCallback {
    void onSuccess(Evento evento);
    void onError(String errorMessage);
}
