package com.firstclass.praceando.firebase.database.entities;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class Foto {
    private List<String> fotosUrls; // Lista de URLs das fotos

    public Foto(List<String> fotosUrls) {
        this.fotosUrls = new ArrayList<>();
    }

    public List<String> getFotosUrls() {
        return fotosUrls;
    }

    public void setFotosUrls(List<String> fotosUrls) {
        this.fotosUrls = fotosUrls;
    }

    public void addFotoUrl(String fotoUrl) {
        this.fotosUrls.add(fotoUrl); // Método para adicionar uma URL à lista
    }

}
