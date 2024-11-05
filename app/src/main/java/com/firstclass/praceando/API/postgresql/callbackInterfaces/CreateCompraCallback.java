package com.firstclass.praceando.API.postgresql.callbackInterfaces;
import com.firstclass.praceando.API.postgresql.entities.CreateCompraResponse;

public interface CreateCompraCallback {
    void onSuccess(CreateCompraResponse createCompra);
    void onError(String errorMessage);
}
