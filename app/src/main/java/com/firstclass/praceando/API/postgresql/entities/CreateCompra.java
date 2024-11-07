package com.firstclass.praceando.API.postgresql.entities;

public class CreateCompra {
    private long cdUsuario;
    private Long cdProduto;
    private Long cdEvento;
    private double vlTotal;

    public CreateCompra(long cdUsuario, long cdEvento, double vlTotal) {
        this.cdEvento = cdEvento;
        this.cdUsuario = cdUsuario;
        this.vlTotal = vlTotal;
        this.cdProduto = null;
    }

    public CreateCompra(long cdUsuario, double vlTotal, long cdProduto) {
        this.cdProduto = cdProduto;
        this.cdUsuario = cdUsuario;
        this.vlTotal = vlTotal;
        this.cdEvento = null;
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
