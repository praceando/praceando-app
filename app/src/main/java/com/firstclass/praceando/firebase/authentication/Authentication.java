package com.firstclass.praceando.firebase.authentication;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class Authentication {

    public String getCurrentUserId() {
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        return user != null ? user.getUid() : "";
    }

    public boolean hasUser() {
        return FirebaseAuth.getInstance().getCurrentUser() != null;
    }

    public interface AuthCallback {
        void onSuccess();
        void onFailure(Exception exception);
    }

    public void signIn(String email, String password, AuthCallback callback) {
        FirebaseAuth.getInstance()
                .signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        // Notifica o sucesso
                        callback.onSuccess();
                    } else {
                        // Notifica a falha e envia a exceção para o callback
                        callback.onFailure(task.getException());
                    }
                });
    }

    public void signUp(String email, String password, AuthCallback callback) {
        FirebaseAuth.getInstance()
                .createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        callback.onSuccess();
                    } else {
                        callback.onFailure(task.getException());
                    }
                });
    }


    public void signOut() {
        FirebaseAuth.getInstance().signOut();
    }

}
