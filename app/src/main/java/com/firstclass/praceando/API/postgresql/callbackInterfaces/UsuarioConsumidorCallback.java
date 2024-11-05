package com.firstclass.praceando.API.postgresql.callbackInterfaces;

import com.firstclass.praceando.API.postgresql.entities.UsuarioConsumidor;


public interface UsuarioConsumidorCallback {
    void onSuccess(UsuarioConsumidor usuarioConsumidor);
    void onError(String errorMessage);
}
