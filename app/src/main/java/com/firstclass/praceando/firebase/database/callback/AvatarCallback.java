package com.firstclass.praceando.firebase.database.callback;

public interface AvatarCallback {
        void onAvatarRetrieved(String avatarAtual);
        void onError(String errorMessage);
}
