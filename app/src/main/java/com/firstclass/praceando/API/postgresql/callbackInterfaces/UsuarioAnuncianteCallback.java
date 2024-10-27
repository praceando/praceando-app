package com.firstclass.praceando.API.postgresql.callbackInterfaces;

import com.firstclass.praceando.API.postgresql.entities.UsuarioAnunciante;


public interface UsuarioAnuncianteCallback {
    void onSuccess(UsuarioAnunciante usuarioAnunciante);
    void onError(String errorMessage);
}
