package com.firstclass.praceando.API.postgresql.entities;

public class CreateCompra {
    private long cdUsuario;
    private long cdProduto;
    private long cdEvento;
    private double vlTotal;

    public CreateCompra(long cdUsuario, long cdEvento, double vlTotal) {
        this.cdEvento = cdEvento;
        this.cdUsuario = cdUsuario;
        this.vlTotal = vlTotal;
    }

    public CreateCompra(long cdUsuario, double vlTotal, long cdProduto) {
        this.cdProduto = cdProduto;
        this.cdUsuario = cdUsuario;
        this.vlTotal = vlTotal;
    }

    public double getVlTotal() {
        return vlTotal;
    }

    public long getCdEvento() {
        return cdEvento;
    }

    public long getCdProduto() {
        return cdProduto;
    }

    public long getCdUsuario() {
        return cdUsuario;
    }

    @Override
    public String toString() {
        return "CreateCompra{" +
                "cdUsuario=" + cdUsuario +
                ", cdProduto=" + cdProduto +
                ", cdEvento=" + cdEvento +
                ", vlTotal=" + vlTotal +
                '}';
    }
}
