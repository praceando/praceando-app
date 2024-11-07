package com.firstclass.praceando.API.postgresql.entities;

public class CreateCompraResponse {
    private long idCompra;
    private String message;

    public CreateCompraResponse(long idCompra, String message) {
        this.idCompra = idCompra;
        this.message = message;
    }

    public long getIdCompra() {
        return idCompra;
    }

    public String getMessage() {
        return message;
    }
}
