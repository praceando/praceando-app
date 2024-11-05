package com.firstclass.praceando.API.postgresql.callbackInterfaces;

import com.firstclass.praceando.API.postgresql.entities.EventoCompleto;

public interface EventByIdCallback {
    void onSuccess(EventoCompleto evento);
    void onError(String errorMessage);
}
