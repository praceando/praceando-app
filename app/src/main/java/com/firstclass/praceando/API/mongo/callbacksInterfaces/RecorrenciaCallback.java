package com.firstclass.praceando.API.mongo.callbacksInterfaces;
import com.firstclass.praceando.API.mongo.entities.Recorrencia;

public interface RecorrenciaCallback {
    void onSuccess(Recorrencia recorrencia);
    void onError(String errorMessage);
}
