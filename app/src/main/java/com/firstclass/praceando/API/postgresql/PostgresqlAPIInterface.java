package com.firstclass.praceando.API.postgresql;

import com.firstclass.praceando.API.postgresql.entities.CreateCompra;
import com.firstclass.praceando.API.postgresql.entities.CreateEventoResponse;
import com.firstclass.praceando.API.postgresql.entities.Evento;
import com.firstclass.praceando.API.postgresql.entities.EmailIsInUse;
import com.firstclass.praceando.API.postgresql.entities.Evento2;
import com.firstclass.praceando.API.postgresql.entities.EventoCompleto;
import com.firstclass.praceando.API.postgresql.entities.EventoFeed;
import com.firstclass.praceando.API.postgresql.entities.FraseSustentavel;
import com.firstclass.praceando.API.postgresql.entities.NicknameIsInUse;
import com.firstclass.praceando.API.postgresql.entities.UsuarioAnunciante;
import com.firstclass.praceando.API.postgresql.entities.UsuarioConsumidor;
import com.firstclass.praceando.entities.Gender;
import com.firstclass.praceando.entities.Locale;
import com.firstclass.praceando.entities.Product;
import com.firstclass.praceando.entities.Tag;
import com.firstclass.praceando.entities.User;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface PostgresqlAPIInterface {

    @GET("fraseSustentavel/find/{id}")
    Call<FraseSustentavel> getFraseSustentavel(@Path("id") int id);

    @GET("tag/read")
    Call<List<Tag>> getTags();

    @GET("local/read")
    Call<List<Locale>> getLocales();

    @GET("genero/read")
    Call<List<Gender>> getGenders();

    @GET("produto/read")
    Call<List<Product>> getProducts();

    @GET("consumidor/existsByNickname/{nickname}")
    Call<NicknameIsInUse> existsByNickname(@Path("nickname") String nickname);

    @GET("usuario/existsByEmail/{email}")
    Call<EmailIsInUse> existsByEmail(@Path("email") String nickname);

    @GET("evento/find/{id}")
    Call<EventoCompleto> getEventById(@Path("id") long id);

    @GET("evento/read")
    Call<List<EventoFeed>> getEventsForFeed();

    @GET("evento/findByAnunciante/{userId}")
    Call<List<EventoFeed>> getEventsForFeedByUserId(@Path("userId") long userId);

    @GET("evento/findByTag/{tagId}")
    Call<List<EventoFeed>> getEventsByTagId(@Path("tagId") long tagId);

    @GET("usuario/find/{userId}")
    Call<User> getUserById(@Path("userId") long userId);

    @GET("usuario/findByEmail/{email}")
    Call<User> getUserByEmail(@Path("email") String email);

    @GET("evento/findByDate")
    Call<List<EventoFeed>> getEventsByDate(@Query("data") String date);

    @POST("consumidor/create")
    Call<UsuarioConsumidor> createUsuarioConsumidor(@Body UsuarioConsumidor usuarioConsumidor);

    @POST("anunciante/create")
    Call<UsuarioAnunciante> createUsuarioAnunciante(@Body UsuarioAnunciante usuarioAnunciante);

    @POST("evento/create")
    Call<CreateEventoResponse> createEvento(@Body Evento2 evento);

    @POST("compra/create")
    Call<CreateCompra> createCompra(@Body CreateCompra  compra);

    @POST("pagamento/complete-purchase/{cdCompra}")
    void pagamento(@Path("cdCompra") long cdCompra);

//
//    @PUT("posts/{id}")
//    Call<Post> updatePost(@Path("id") int id, @Body Post post);
//
//    @DELETE("posts/{id}")
//    Call<Void> deletePost(@Path("id") int id);

}
