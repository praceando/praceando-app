package com.firstclass.praceando.API.mongo.callbacksInterfaces;

import com.firstclass.praceando.API.mongo.entities.AvaliacoesUsuarios;
import com.firstclass.praceando.API.mongo.entities.ConquistaUser;

public interface EventReviewsInterface {
    void onSuccess(AvaliacoesUsuarios avaliacoesUsuarios);
    void onError(String errorMessage);
}
