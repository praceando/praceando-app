package com.firstclass.praceando.firebase.database;

import com.firstclass.praceando.firebase.database.entities.Foto;

import java.util.List;

public interface FotosCallback {
    void onFotosReceived(List<String> fotos);
}
