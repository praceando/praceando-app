package com.firstclass.praceando.API.postgresql.callbackInterfaces;


import com.firstclass.praceando.API.postgresql.entities.CreateEventoResponse;

public interface CreateEventoCallback {
    void onResponse(CreateEventoResponse createEvento);
    void onError(String errorMessage);
}
