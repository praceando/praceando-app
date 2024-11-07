package com.firstclass.praceando.API.postgresql;

import com.firstclass.praceando.API.postgresql.entities.CreateCompra;
import com.firstclass.praceando.API.postgresql.entities.CreateCompraResponse;
import com.firstclass.praceando.API.postgresql.entities.CreateEventoResponse;
import com.firstclass.praceando.API.postgresql.entities.Evento;
import com.firstclass.praceando.API.postgresql.entities.EmailIsInUse;
import com.firstclass.praceando.API.postgresql.entities.Evento2;
import com.firstclass.praceando.API.postgresql.entities.EventoCompleto;
import com.firstclass.praceando.API.postgresql.entities.EventoFeed;
import com.firstclass.praceando.API.postgresql.entities.EventoReadBody;
import com.firstclass.praceando.API.postgresql.entities.FraseSustentavel;
import com.firstclass.praceando.API.postgresql.entities.Interesse;
import com.firstclass.praceando.API.postgresql.entities.InteresseResponse;
import com.firstclass.praceando.API.postgresql.entities.NicknameIsInUse;
import com.firstclass.praceando.API.postgresql.entities.ProfileUser;
import com.firstclass.praceando.API.postgresql.entities.UsuarioAnunciante;
import com.firstclass.praceando.API.postgresql.entities.UsuarioConsumidor;
import com.firstclass.praceando.entities.Gender;
import com.firstclass.praceando.entities.Locale;
import com.firstclass.praceando.entities.Product;
import com.firstclass.praceando.entities.Tag;
import com.firstclass.praceando.entities.User;

import java.util.List;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;
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

    @GET("produto/find/{id}")
    Call<Product> getProductById(@Path("id") int id);

    @GET("consumidor/existsByNickname/{nickname}")
    Call<NicknameIsInUse> existsByNickname(@Path("nickname") String nickname);

    @GET("usuario/existsByEmail/{email}")
    Call<EmailIsInUse> existsByEmail(@Path("email") String nickname);

    @GET("evento/find/{id}")
    Call<EventoCompleto> getEventById(@Path("id") long id);

    @POST("evento/read")
    Call<List<EventoFeed>> getEventsForFeed(@Body()EventoReadBody body);

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

    @GET("evento/find-interesse")
    Call<InteresseResponse> getEventInteresses(@Query("idEvento") long eventoId, @Query("idUsuario") long userId);

    @POST("consumidor/create")
    Call<UsuarioConsumidor> createUsuarioConsumidor(@Body UsuarioConsumidor usuarioConsumidor);

    @POST("anunciante/create")
    Call<UsuarioAnunciante> createUsuarioAnunciante(@Body UsuarioAnunciante usuarioAnunciante);

    @POST("evento/create")
    Call<CreateEventoResponse> createEvento(@Body Evento2 evento);

    @POST("compra/create")
    Call<CreateCompraResponse> createCompra(@Body CreateCompra  compra);

    @POST("pagamento/complete-purchase/{cdCompra}")
    Call<ResponseBody> pagamento(@Path("cdCompra") long cdCompra);

    @PUT("evento/add-interesse")
    Call<ResponseBody> addInteresse(@Body()Interesse interesse);

    @PUT("usuario/update-profile")
    Call<ProfileUser> updateProfile(@Body ProfileUser profileUser);

}
