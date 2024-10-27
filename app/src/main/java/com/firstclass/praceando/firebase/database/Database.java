package com.firstclass.praceando.firebase.database;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.util.Log;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.firstclass.praceando.EventDetails.EventCreationBasicDatas;
import com.firstclass.praceando.firebase.database.entities.Foto;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.gms.tasks.Tasks;
import com.google.firebase.FirebaseApp;
import com.google.firebase.appcheck.FirebaseAppCheck;
import com.google.firebase.appcheck.playintegrity.PlayIntegrityAppCheckProviderFactory;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

public class Database {
    Map<String, Object> foto_id = new HashMap<>();
    //Abrir firebase
    FirebaseFirestore db = FirebaseFirestore.getInstance();
    String eventoFotosPath = "fotos_envento";
    int fotoId;
    int id;

    public Database() {}

    public void salvar(Foto foto){
        if(foto.getId() == 0) { //novo registro
            db.collection("counters").document("foto_id").get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                @Override
                public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                    int id = 1;
                    if (task.isSuccessful()) {
                        DocumentSnapshot document = task.getResult();
                        if (document.exists()) {
                            foto_id = document.getData();
                            id = Integer.parseInt(foto_id.get("chave").toString()) + 1;

                        }

                        foto_id.put("chave", id);
                        db.collection("counters").document("foto_id").set(foto_id);

                        //gravra a foto
                        foto.setId(id);
                        db.collection(eventoFotosPath).document(String.valueOf(id)).set(foto);
                    }
                }
            });

        } else{
            db.collection(eventoFotosPath).document(String.valueOf(foto.getId())).set(foto);
        }
    }

    public List<Foto> listar(int eventoId){
        List<Foto> eventoFotosList = new ArrayList<>();

        //Recuperar dados em tempo real
        db.collection(eventoFotosPath).addSnapshotListener(new EventListener<QuerySnapshot>() {
            @Override
            public void onEvent(@Nullable QuerySnapshot value, @Nullable FirebaseFirestoreException error) {
                for (DocumentSnapshot document : value.getDocuments()) {
                    Foto foto = document.toObject(Foto.class);
                    eventoFotosList.add(foto);
                }
            }
        });

        return eventoFotosList;
    }

    public void remover (Foto pessoa){
        db.collection("pessoas").document(String.valueOf(pessoa.getId())).delete();
    }


    public void uploadImagesAndSaveUrls(int eventId, List<Uri> imageUris, EventCreationBasicDatas context) {
        FirebaseApp.initializeApp(context);

        FirebaseFirestore db = FirebaseFirestore.getInstance();
        FirebaseStorage storage = FirebaseStorage.getInstance();
        CollectionReference countersRef = db.collection("counters");

        // Passo 1: Obter o próximo fotoId da coleção "counters"
        countersRef.document("foto_id").get().addOnCompleteListener(task -> {
            if (task.isSuccessful() && task.getResult().exists()) {
                DocumentSnapshot document = task.getResult();
                int fotoId = Integer.parseInt(document.get("chave").toString()) + 1; // Incrementa o ID da foto
                Toast.makeText(context, ""+fotoId, Toast.LENGTH_SHORT).show();
                // Passo 2: Iniciar o upload das imagens após o fotoId ser recuperado
                List<Task<Uri>> uploadTasks = new ArrayList<>();

                for (int i = 0; i < imageUris.size(); i++) {
                    Uri imageUri = imageUris.get(i);
                    String imageName = fotoId + "_" + i + ".jpg"; // Gera o nome da imagem
                    StorageReference storageRef = storage.getReference()
                            .child("eventos")
                            .child("foto_" + eventId)
                            .child(imageName);

                    // Faz o upload da imagem e armazena a tarefa do upload
                    Task<Uri> uploadTask = storageRef.putFile(imageUri)
                            .continueWithTask(taskSnapshot -> storageRef.getDownloadUrl()); // Após o upload, obter a URL
                    uploadTasks.add(uploadTask);
                }

                // Passo 3: Aguardar todos os uploads concluírem
                Tasks.whenAllComplete(uploadTasks).addOnCompleteListener(taskList -> {
                    List<Uri> imageUrisUploaded = new ArrayList<>();
                    for (Task<?> taskItem : uploadTasks) {
                        if (taskItem.isSuccessful()) {
                            imageUrisUploaded.add((Uri) taskItem.getResult());
                        }
                    }

                    // Verificar se todas as imagens foram carregadas
                    if (imageUrisUploaded.size() == imageUris.size()) {
                        // Passo 4: Salvar as URLs das imagens no Firestore
                        Map<String, Object> imageData = new HashMap<>();
                        imageData.put("evento_id", eventId);
                        imageData.put("fotos_urls", imageUrisUploaded);
                        imageData.put("data_atualizacao", new Date());

                        db.collection("eventos").document("" + eventId)
                                .collection("fotos")
                                .add(imageData)
                                .addOnSuccessListener(documentReference -> {
                                    Log.d("Firebase", "Imagens salvas com sucesso");
                                    Toast.makeText(context, "Imagens salvas com sucesso", Toast.LENGTH_SHORT).show();
                                })
                                .addOnFailureListener(e -> {
                                    Log.e("Firebase", "Erro ao salvar imagens: " + e.getMessage());
                                    Toast.makeText(context, "Erro ao salvar imagens", Toast.LENGTH_SHORT).show();
                                });
                    } else {
                        Log.e("Firebase", "Erro no upload de uma ou mais imagens");
                        Toast.makeText(context, "Erro no upload de imagens", Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });
    }

    public void teste(ImageView foto) {
        FirebaseStorage storage = FirebaseStorage.getInstance();

        db.collection("counters").document("foto_id").get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if (task.isSuccessful()) {
                    DocumentSnapshot document = task.getResult();
                    if (document.exists()) {
                        foto_id = document.getData();
                        id = Integer.parseInt(foto_id.get("chave").toString()) + 1;

                    }

                    foto_id.put("chave", id);
                    db.collection("counters").document("foto_id").set(foto_id);

                }
            }
        });

        Bitmap bitmap = ((BitmapDrawable) foto.getDrawable()).getBitmap();
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 70, baos);
        byte[] databyte = baos.toByteArray();

        storage.getReference("eventos").child("foto_.jpg")
                .putBytes(databyte).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                    @Override
                    public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                        taskSnapshot.getMetadata().getReference().getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                            @Override
                            public void onSuccess(Uri uri) {
                                Toast.makeText(foto.getContext(), "FOI?", Toast.LENGTH_SHORT).show();
                            }
                        });
                    }
                });

//        String imageName = fotoId + "_" + 1 + ".jpg"; // Gera o nome da imagem
//        StorageReference storageRef = storage.getReference()
//                .child("eventos")
//                .child("foto_" + String.valueOf(id))
//                .child(imageName);
//
//        // Faz o upload da imagem e armazena a tarefa do upload
//        Task<Uri> uploadTask = storageRef.putFile(uri)
//                .continueWithTask(taskSnapshot -> storageRef.getDownloadUrl()); // Após o upload, obter a URL

    }

}
