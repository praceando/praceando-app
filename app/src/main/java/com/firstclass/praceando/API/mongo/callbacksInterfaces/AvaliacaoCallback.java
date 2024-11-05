package com.firstclass.praceando.API.mongo.callbacksInterfaces;

import com.firstclass.praceando.API.mongo.entities.Avaliacao;

public interface AvaliacaoCallback {
    void onSuccess(Avaliacao avaliacao);
    void onError(String errorMessage);
}
