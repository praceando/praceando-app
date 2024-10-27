package com.firstclass.praceando.API.mongo;

import com.firstclass.praceando.API.mongo.entities.AvaliacoesUsuarios;
import com.firstclass.praceando.API.mongo.entities.ConquistaUser;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;

public interface MongoAPIInterface {

    @GET("conquista_user/read/{userId}")
    Call<List<ConquistaUser>> getUserGoals(@Path("userId") int id);

    @GET("avaliacao/read/{eventId}/{userId}")
    Call<AvaliacoesUsuarios> getEventReviews(@Path("eventId") long eventId, @Path("userId") int userId);
//
//    @POST("posts")
//    Call<Post> createPost(@Body Post post);
//
//    @PUT("posts/{id}")
//    Call<Post> updatePost(@Path("id") int id, @Body Post post);
//
//    @DELETE("posts/{id}")
//    Call<Void> deletePost(@Path("id") int id);

}
