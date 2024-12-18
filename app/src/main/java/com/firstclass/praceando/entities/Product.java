package com.firstclass.praceando.entities;

public class Product {
    private long id;
    private String nmProduto;
    private String dsProduto;
    private double vlPreco;
    private String nmCategoria;
    private String urlImagem;

    public Product(long id, String description, double price, String title, String imageUrl, String nmCategoria) {
        this.id = id;
        this.vlPreco = price;
        this.nmProduto = title;
        this.dsProduto = description;
        this.urlImagem = imageUrl;
        this.nmCategoria = nmCategoria;
    }

    public String getTitle() {
        return nmProduto;
    }

    public void setTitle(String title) {
        this.nmProduto = title;
    }

    public String getDescription() {
        return dsProduto;
    }

    public void setDescription(String description) {
        this.dsProduto = description;
    }

    public double getPrice() {
        return vlPreco;
    }

    public void setPrice(double price) {
        this.vlPreco = price;
    }

    public String getImageUrl() {
        return urlImagem;
    }

    public void setImageUrl(String imageUrl) {
        this.urlImagem = imageUrl;
    }

    public long getId() {
        return id;
    }

    public String getNmCategoria() {
        return nmCategoria;
    }

    @Override
    public String toString() {
        return "Product{" +
                "id=" + id +
                ", nmProduto='" + nmProduto + '\'' +
                ", dsProduto='" + dsProduto + '\'' +
                ", vlPreco=" + vlPreco +
                ", urlImagem='" + urlImagem + '\'' +
                ", nmCategoria='" + nmCategoria + '\'' +
                '}';
    }
}
