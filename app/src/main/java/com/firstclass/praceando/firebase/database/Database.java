package com.firstclass.praceando.firebase.database;
import static android.content.ContentValues.TAG;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.graphics.drawable.BitmapDrawable;
import android.media.ExifInterface;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.util.Log;
import android.widget.ImageView;
import androidx.annotation.Nullable;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.firstclass.praceando.Globals;
import com.firstclass.praceando.firebase.database.callback.AvatarCallback;
import com.firstclass.praceando.firebase.database.callback.AvatarsCallback;
import com.google.android.flexbox.FlexboxLayout;
import com.google.firebase.Timestamp;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FieldValue;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.SetOptions;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.squareup.picasso.Picasso;

public class Database {
    FirebaseFirestore db = FirebaseFirestore.getInstance();
    private FirebaseStorage storage = FirebaseStorage.getInstance();
    private CollectionReference fotosEventosRef = db.collection("fotos_eventos");


    public Database() {}

    public void listar(long eventoId, FotosCallback callback) {
        db.collection("fotos_eventos").document(String.valueOf(eventoId)).addSnapshotListener(new EventListener<DocumentSnapshot>() {
            @Override
            public void onEvent(@Nullable DocumentSnapshot document, @Nullable FirebaseFirestoreException error) {
                if (error != null) {
                    Log.w("FIREBASE", "Listen failed.", error);
                    return;
                }

                if (document != null && document.exists()) {
                    ArrayList<String> fotosUrls = (ArrayList<String>) document.get("fotos_urls");
                    callback.onFotosReceived(fotosUrls);
                } else {
                    Log.d("FIREBASE", "No such document");
                }
            }
        });
    }

    public void uploadFoto(Context context, String eventoId, ImageView foto, Uri imageUri) {
        Bitmap bitmap = ((BitmapDrawable) foto.getDrawable()).getBitmap();

        bitmap = corrigirOrientacao(context, bitmap, imageUri);

        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 70, baos);
        byte[] databyte = baos.toByteArray();

        StorageReference storageRef = storage.getReference().child("eventos/" + eventoId + "/foto_" + System.currentTimeMillis() + ".jpg");
        UploadTask uploadTask = storageRef.putBytes(databyte);

        uploadTask.addOnSuccessListener(taskSnapshot -> {
            storageRef.getDownloadUrl().addOnSuccessListener(uri -> {
                String fotoUrl = uri.toString();
                salvarFotoNoFirestore(eventoId, fotoUrl);
            });
        }).addOnFailureListener(e -> {
            e.printStackTrace();
        });
    }

    private Bitmap corrigirOrientacao(Context context, Bitmap bitmap, Uri imageUri) {
        try {
            InputStream inputStream = context.getContentResolver().openInputStream(imageUri);
            if (inputStream != null) {
                ExifInterface exif = new ExifInterface(inputStream);
                int orientation = exif.getAttributeInt(ExifInterface.TAG_ORIENTATION, ExifInterface.ORIENTATION_NORMAL);

                int rotation = 0;
                switch (orientation) {
                    case ExifInterface.ORIENTATION_ROTATE_90:
                        rotation = 90;
                        break;
                    case ExifInterface.ORIENTATION_ROTATE_180:
                        rotation = 180;
                        break;
                    case ExifInterface.ORIENTATION_ROTATE_270:
                        rotation = 270;
                        break;
                    default:
                        break;
                }

                if (rotation != 0) {
                    Matrix matrix = new Matrix();
                    matrix.postRotate(rotation);

                    bitmap = Bitmap.createBitmap(bitmap, 0, 0, bitmap.getWidth(), bitmap.getHeight(), matrix, true);
                }
                inputStream.close();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return bitmap;
    }




    private void salvarFotoNoFirestore (String eventoId, String fotoUrl){

            fotosEventosRef.document(eventoId).get().addOnSuccessListener(documentSnapshot -> {
                ArrayList<String> fotosUrls;

                if (documentSnapshot.exists()) {
                    fotosUrls = (ArrayList<String>) documentSnapshot.get("fotos_urls");
                    if (fotosUrls == null) fotosUrls = new ArrayList<>();
                } else {
                    fotosUrls = new ArrayList<>();
                }

                if (fotosUrls.size() <= 3) {
                    fotosUrls.add(fotoUrl);
                }

                Map<String, Object> data = new HashMap<>();
                data.put("fotos_urls", fotosUrls);
                data.put("dt_atualizacao", Timestamp.now());

                fotosEventosRef.document(eventoId).set(data, SetOptions.merge())
                        .addOnSuccessListener(aVoid -> {
                            Log.e("FIREBASE", "Fotos do evento atualizadas com sucesso!");
                        })
                        .addOnFailureListener(e -> {
                            Log.e("FIREBASE", e.getMessage());

                        });
            });
        }

    public void criarInventario(long usuarioId) {
        String urlPadrao = "https://firebasestorage.googleapis.com/v0/b/praceando-dbad6.appspot.com/o/avatars%2Fbelha.png?alt=media&token=252fa23d-8369-4cb0-a7f2-6a5c39e9d522";

        Map<String, Object> inventario = new HashMap<>();
        inventario.put("dt_atualizacao", FieldValue.serverTimestamp());
        inventario.put("avatar_atual", urlPadrao);

        List<String> avatares = new ArrayList<>();
        avatares.add(urlPadrao);
        inventario.put("avatares", avatares);

        db.collection("inventario").document(String.valueOf(usuarioId))
                .set(inventario)
                .addOnSuccessListener(aVoid -> Log.d("FIREBASE", "Inventário criado com sucesso!"))
                .addOnFailureListener(e -> Log.w("FIREBASE", "Erro ao criar inventário", e));
    }

    public void buscarAvatarAtual(long usuarioId, AvatarCallback callback) {
        FirebaseFirestore db = FirebaseFirestore.getInstance();

        db.collection("inventario").document(String.valueOf(usuarioId))
                .get()
                .addOnSuccessListener(documentSnapshot -> {
                    if (documentSnapshot.exists()) {
                        String avatarAtual = documentSnapshot.getString("avatar_atual");
                        Log.e("FIREBASE", "avatar atual: " + avatarAtual);
                        callback.onAvatarRetrieved(avatarAtual); // Passa o avatarAtual através do callback
                    } else {
                        String message = "Documento não encontrado para o ID: " + usuarioId;
                        Log.d("FIREBASE", message);
                        callback.onError(message);
                    }
                })
                .addOnFailureListener(e -> {
                    String message = "Erro ao buscar avatar atual";
                    Log.w("FIREBASE", message, e);
                    callback.onError(message);
                });
    }

    public void alterarAvatarAtual(long usuarioId, @NonNull String novaUrlAvatar) {
        db.collection("inventario").document(String.valueOf(usuarioId))
                .update("avatar_atual", novaUrlAvatar)
                .addOnSuccessListener(aVoid -> {
                    Log.d(TAG, "Avatar atualizado com sucesso no Firestore.");
                })
                .addOnFailureListener(e -> Log.w(TAG, "Erro ao atualizar avatar no Firestore", e));
    }

    public void buscarEAdicionarAvatares(long usuarioId, AvatarsCallback callback) {
        db.collection("inventario").document(String.valueOf(usuarioId))
                .get()
                .addOnSuccessListener(documentSnapshot -> {
                    if (documentSnapshot.exists()) {
                        List<String> avatares = (List<String>) documentSnapshot.get("avatares");
                        if (avatares != null && !avatares.isEmpty()) {
                            callback.onAvataresAdicionados(avatares);
                        } else {
                            Log.d(TAG, "Lista de avatares vazia ou não encontrada.");
                        }
                    } else {
                        Log.d(TAG, "Documento não encontrado para o ID: " + usuarioId);
                    }
                })
                .addOnFailureListener(e -> Log.w(TAG, "Erro ao buscar avatares", e));
    }

    public void adicionarAvatar(long usuarioId, @NonNull String novaUrlAvatar) {
        db.collection("inventario").document(String.valueOf(usuarioId))
                .update("avatares", FieldValue.arrayUnion(novaUrlAvatar))
                .addOnSuccessListener(aVoid -> Log.d(TAG, "Avatar adicionado com sucesso à lista de avatares."))
                .addOnFailureListener(e -> Log.e(TAG, "Erro ao adicionar avatar à lista de avatares", e));
    }

    public void criarAnotacao(long usuarioId) {
        Map<String, Object> anotacao = new HashMap<>();
        anotacao.put("dt_atualizacao", FieldValue.serverTimestamp());
        anotacao.put("anotacao", "");

        db.collection("anotacoes").document(String.valueOf(usuarioId))
                .set(anotacao)
                .addOnSuccessListener(aVoid -> Log.d("FIREBASE", "Anotação criado com sucesso!"))
                .addOnFailureListener(e -> Log.w("FIREBASE", "Erro ao criar anotação", e));
    }

    public void alterarAnotacao(long usuarioId, @NonNull String anotacaoNova) {
        db.collection("anotacoes").document(String.valueOf(usuarioId))
                .update("anotacao", anotacaoNova)
                .addOnSuccessListener(aVoid -> {
                    Log.d(TAG, "Anotação atualizada com sucesso no Firestore.");
                })
                .addOnFailureListener(e -> Log.w(TAG, "Erro ao atualizar anotação no Firestore", e));
    }

    public void buscarAnotacao(long usuarioId, AvatarCallback callback) {
        FirebaseFirestore db = FirebaseFirestore.getInstance();

        db.collection("anotacoes").document(String.valueOf(usuarioId))
                .get()
                .addOnSuccessListener(documentSnapshot -> {
                    if (documentSnapshot.exists()) {
                        String anotacao = documentSnapshot.getString("anotacao");
                        callback.onAvatarRetrieved(anotacao);
                    } else {
                        String message = "Documento não encontrado para o ID: " + usuarioId;
                        callback.onError(message);
                    }
                })
                .addOnFailureListener(e -> {
                    String message = "Erro ao buscar anotação ";
                    Log.w("FIREBASE", message, e);
                    callback.onError(message);
                });
    }

}

