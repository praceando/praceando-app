package com.firstclass.praceando.API.postgresql.callbackInterfaces;

import com.firstclass.praceando.entities.Product;

import java.util.List;

public interface ProductByIdCallback {
    void onSuccess(Product product);
    void onError(String errorMessage);
}
