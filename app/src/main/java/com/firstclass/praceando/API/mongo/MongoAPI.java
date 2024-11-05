package com.firstclass.praceando.API.mongo;


import com.firstclass.praceando.API.mongo.callbacksInterfaces.AvaliacaoCallback;
import com.firstclass.praceando.API.mongo.callbacksInterfaces.EventMeanInterface;
import com.firstclass.praceando.API.mongo.callbacksInterfaces.EventReviewsInterface;
import com.firstclass.praceando.API.mongo.callbacksInterfaces.RecorrenciaCallback;
import com.firstclass.praceando.API.mongo.callbacksInterfaces.UserGoalsCallback;
import com.firstclass.praceando.API.mongo.entities.Avaliacao;
import com.firstclass.praceando.API.mongo.entities.AvaliacoesUsuarios;
import com.firstclass.praceando.API.mongo.entities.ConquistaUser;
import com.firstclass.praceando.API.mongo.entities.Mean;
import com.firstclass.praceando.API.mongo.entities.Recorrencia;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MongoAPI {

    private Retrofit retrofit = new Retrofit.Builder()
            .baseUrl("https://praceando-api-mg.onrender.com/api/")
            .addConverterFactory(GsonConverterFactory.create())
            .build();

    private MongoAPIInterface mongoAPI = retrofit.create(MongoAPIInterface.class);

    public void getUserGoals(int userId, UserGoalsCallback callback) {
        MongoAPIInterface mongoAPI = retrofit.create(MongoAPIInterface.class);
        Call<List<ConquistaUser>> call = mongoAPI.getUserGoals(userId);

        call.enqueue(new Callback<List<ConquistaUser>>() {
            @Override
            public void onResponse(Call<List<ConquistaUser>> call, Response<List<ConquistaUser>> response) {
                if (response.isSuccessful() && response.body() != null) {
                    callback.onSuccess(response.body());
                } else {
                    callback.onError("Resposta falhou: " + response.message());
                }
            }

            @Override
            public void onFailure(Call<List<ConquistaUser>> call, Throwable t) {
                callback.onError(t.getMessage());
            }
        });
    }


    public void getEventReviews(long eventId, long userId, EventReviewsInterface callback) {
        Call<AvaliacoesUsuarios> call = mongoAPI.getEventReviews(eventId, userId);

        call.enqueue(new Callback<AvaliacoesUsuarios>() {
            @Override
            public void onResponse(Call<AvaliacoesUsuarios> call, Response<AvaliacoesUsuarios> response) {
                if (response.isSuccessful() && response.body() != null) {
                    callback.onSuccess(response.body());
                } else {
                    callback.onError("Resposta falhou: " + response.message());
                }
            }

            @Override
            public void onFailure(Call<AvaliacoesUsuarios> call, Throwable t) {
                callback.onError(t.getMessage());
            }
        });
    }

    public void getEventMean(long eventId, EventMeanInterface callback) {
        Call<Mean> call = mongoAPI.getEventMean(eventId);

        call.enqueue(new Callback<Mean>() {
            @Override
            public void onResponse(Call<Mean> call, Response<Mean> response) {
                if (response.isSuccessful() && response.body() != null) {
                    callback.onSuccess(response.body());
                } else {
                    callback.onError("Resposta falhou: " + response.message());
                }
            }

            @Override
            public void onFailure(Call<Mean> call, Throwable t) {
                callback.onError(t.getMessage());
            }
        });
    }

    public void createAvaliacao(Avaliacao avaliacao, AvaliacaoCallback callback) {
        Call<Avaliacao> call = mongoAPI.createAvaliacao(avaliacao);

        call.enqueue(new Callback<Avaliacao>() {
            @Override
            public void onResponse(Call<Avaliacao> call, Response<Avaliacao> response) {
                if (response.isSuccessful() && response.body() != null) {
                    callback.onSuccess(response.body());
                }
            }
            @Override
            public void onFailure(Call<Avaliacao> call, Throwable t) {
                callback.onError(t.getMessage());
            }
        });
    }

    public void createRecorrencia(Recorrencia recorrencia, RecorrenciaCallback callback) {
        Call<Recorrencia> call = mongoAPI.createRecorrencia(recorrencia);

        call.enqueue(new Callback<Recorrencia>() {
            @Override
            public void onResponse(Call<Recorrencia> call, Response<Recorrencia> response) {
                if (response.isSuccessful() && response.body() != null) {
                    callback.onSuccess(response.body());
                }
            }
            @Override
            public void onFailure(Call<Recorrencia> call, Throwable t) {
                callback.onError(t.getMessage());
            }
        });
    }

//    private void chamarApiRetrofitById() {
//
//        // Criar a chamada para a API
//        PhotoApi photoApi = retrofit.create(PhotoApi.class);
//        Call<Foto> call = photoApi.getById("12");
//
//        // Executar chamada assíncrona
//        call.enqueue(new Callback<Foto>() {
//            @Override
//            public void onResponse(Call<Foto> call, Response<Foto> response) {
//                if (response.isSuccessful() && response.body() != null) {
//                    Foto photo = response.body();
//                    // Atualizar RecyclerView
//                    fotoRecyclerView.setAdapter(new FotoAdapter(Collections.singletonList(photo)));
//                } else {
//                    Log.e("ERRO", "Resposta falhou: " + response.message());
//                }
//            }
//
//            @Override
//            public void onFailure(Call<Foto> call, Throwable t) {
//                Log.e("ERRO", t.getMessage());
//            }
//        });
//    }


//    private void updatePost() {
//        Post postAtualizado = new Post();
//        postAtualizado.setTitle("Título atualizado");
//        postAtualizado.setBody("Conteúdo atualizado");
//        postAtualizado.setUserId(1);
//
//        PhotoApi photoApi = retrofit.create(PhotoApi.class);
//        Call<Post> call = photoApi.updatePost(1, postAtualizado);
//        call.enqueue(new Callback<Post>() {
//            @Override
//            public void onResponse(Call<Post> call, Response<Post> response) {
//                if (response.isSuccessful()) {
//                    Post post = response.body();
//                }
//            }
//
//            @Override
//            public void onFailure(Call<Post> call, Throwable t) {
//            }
//        });
//
//    }

//    public void deletePost() {
//        PhotoApi photoApi = retrofit.create(PhotoApi.class);
//        Call<Void> call = photoApi.deletePost(1);
//        call.enqueue(new Callback<Void>() {
//            @Override
//            public void onResponse(Call<Void> call, Response<Void> response) {
//                if (response.isSuccessful()) {
//                }
//            }
//
//            @Override
//            public void onFailure(Call<Void> call, Throwable t) {
//            }
//        });
//
//    }
}


