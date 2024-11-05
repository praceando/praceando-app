package com.firstclass.praceando.API.postgresql.callbackInterfaces;
import com.firstclass.praceando.entities.Product;
import java.util.List;

public interface ProductsCallback {
    void onSuccess(List<Product> products);
    void onError(String errorMessage);
}
