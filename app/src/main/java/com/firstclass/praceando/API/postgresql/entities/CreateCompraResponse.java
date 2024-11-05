package com.firstclass.praceando.API.postgresql.entities;

public class CreateCompraResponse {
    private long idCompra;

    public CreateCompraResponse(long idCompra) {
        this.idCompra = idCompra;
    }

    public long getIdCompra() {
        return idCompra;
    }
}
