package com.firstclass.praceando.API.postgresql;
import com.firstclass.praceando.API.postgresql.callbackInterfaces.CreateCompraCallback;
import com.firstclass.praceando.API.postgresql.callbackInterfaces.CreateEventoCallback;
import com.firstclass.praceando.API.postgresql.callbackInterfaces.EmailExistsCallback;
import com.firstclass.praceando.API.postgresql.callbackInterfaces.EventByIdCallback;
import com.firstclass.praceando.API.postgresql.callbackInterfaces.EventsCallback;
import com.firstclass.praceando.API.postgresql.callbackInterfaces.FraseSustentavelCallback;
import com.firstclass.praceando.API.postgresql.callbackInterfaces.GendersCallback;
import com.firstclass.praceando.API.postgresql.callbackInterfaces.LocalesCallback;
import com.firstclass.praceando.API.postgresql.callbackInterfaces.NicknameExistsCallback;
import com.firstclass.praceando.API.postgresql.callbackInterfaces.ProductsCallback;
import com.firstclass.praceando.API.postgresql.callbackInterfaces.TagsCallback;
import com.firstclass.praceando.API.postgresql.callbackInterfaces.UserByIdCallback;
import com.firstclass.praceando.API.postgresql.callbackInterfaces.UsuarioAnuncianteCallback;
import com.firstclass.praceando.API.postgresql.callbackInterfaces.UsuarioConsumidorCallback;
import com.firstclass.praceando.API.postgresql.entities.CreateCompra;
import com.firstclass.praceando.API.postgresql.entities.CreateEventoResponse;
import com.firstclass.praceando.API.postgresql.entities.EmailIsInUse;
import com.firstclass.praceando.API.postgresql.entities.Evento;
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
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class PostgresqlAPI {

    private Retrofit retrofit = new Retrofit.Builder()
            .baseUrl("https://praceando-api-pg.onrender.com/api/")
            .addConverterFactory(GsonConverterFactory.create())
            .build();

    private final PostgresqlAPIInterface postgresqApi = retrofit.create(PostgresqlAPIInterface.class);


    public void getFraseSustentavel(int id, FraseSustentavelCallback callback) {
        Call<FraseSustentavel> call = postgresqApi.getFraseSustentavel(id);

        call.enqueue(new Callback<FraseSustentavel>() {
            @Override
            public void onResponse(Call<FraseSustentavel> call, Response<FraseSustentavel> response) {
                if (response.isSuccessful() && response.body() != null) {
                    callback.onSuccess(response.body());
                } else {
                    callback.onError("Resposta falhou: " + response.message());
                }
            }

            @Override
            public void onFailure(Call<FraseSustentavel> call, Throwable t) {
                callback.onError(t.getMessage());
            }
        });
    }

    public void getTags(TagsCallback callback) {
        Call<List<Tag>> call = postgresqApi.getTags();

        call.enqueue(new Callback<List<Tag>>() {
            @Override
            public void onResponse(Call<List<Tag>> call, Response<List<Tag>> response) {
                if (response.isSuccessful() && response.body() != null) {
                    callback.onSuccess(response.body());
                } else {
                    callback.onError("Resposta falhou: " + response.message());
                }
            }

            @Override
            public void onFailure(Call<List<Tag>> call, Throwable t) {
                callback.onError(t.getMessage());
            }
        });
    }

    public void getLocales(LocalesCallback callback) {
        Call<List<Locale>> call = postgresqApi.getLocales();

        call.enqueue(new Callback<List<Locale>>() {
            @Override
            public void onResponse(Call<List<Locale>> call, Response<List<Locale>> response) {
                if (response.isSuccessful() && response.body() != null) {
                    callback.onSuccess(response.body());
                } else {
                    callback.onError("Resposta falhou: " + response);
                }
            }

            @Override
            public void onFailure(Call<List<Locale>> call, Throwable t) {
                callback.onError(t.getMessage());
            }
        });
    }

    public void getGenders(GendersCallback callback) {
        Call<List<Gender>> call = postgresqApi.getGenders();

        call.enqueue(new Callback<List<Gender>>() {
            @Override
            public void onResponse(Call<List<Gender>> call, Response<List<Gender>> response) {
                if (response.isSuccessful() && response.body() != null) {
                    callback.onSuccess(response.body());
                } else {
                    callback.onError("Resposta falhou: " + response);
                }
            }

            @Override
            public void onFailure(Call<List<Gender>> call, Throwable t) {
                callback.onError(t.getMessage());
            }
        });
    }

    public void getProducts(ProductsCallback callback) {
        Call<List<Product>> call = postgresqApi.getProducts();

        call.enqueue(new Callback<List<Product>>() {
            @Override
            public void onResponse(Call<List<Product>> call, Response<List<Product>> response) {
                if (response.isSuccessful() && response.body() != null) {
                    callback.onSuccess(response.body());
                } else {
                    callback.onError("Resposta falhou: " + response);
                }
            }

            @Override
            public void onFailure(Call<List<Product>> call, Throwable t) {
                callback.onError(t.getMessage());
            }
        });
    }

    public void nicknameExists(String nickname, NicknameExistsCallback callback) {
        Call<NicknameIsInUse> call = postgresqApi.existsByNickname(nickname);

        call.enqueue(new Callback<NicknameIsInUse>() {
            @Override
            public void onResponse(Call<NicknameIsInUse> call, Response<NicknameIsInUse> response) {
                if (response.isSuccessful() && response.body() != null) {
                    callback.onSuccess(response.body());
                } else {
                    callback.onError("Resposta falhou: " + response);
                }
            }

            @Override
            public void onFailure(Call<NicknameIsInUse> call, Throwable t) {
                callback.onError(t.getMessage());
            }
        });
    }

    public void emailExists(String email, EmailExistsCallback callback) {
        Call<EmailIsInUse> call = postgresqApi.existsByEmail(email);

        call.enqueue(new Callback<EmailIsInUse>() {
            @Override
            public void onResponse(Call<EmailIsInUse> call, Response<EmailIsInUse> response) {
                if (response.isSuccessful() && response.body() != null) {
                    callback.onSuccess(response.body());
                } else {
                    callback.onError("Resposta falhou: " + response);
                }
            }

            @Override
            public void onFailure(Call<EmailIsInUse> call, Throwable t) {
                callback.onError(t.getMessage());
            }
        });
    }

    public void getEventById(long id, EventByIdCallback callback) {
        Call<EventoCompleto> call = postgresqApi.getEventById(id);

        call.enqueue(new Callback<EventoCompleto>() {
            @Override
            public void onResponse(Call<EventoCompleto> call, Response<EventoCompleto> response) {
                if (response.isSuccessful() && response.body() != null) {
                    callback.onSuccess(response.body());
                } else {
                    callback.onError("Resposta falhou: " + response);
                }
            }

            @Override
            public void onFailure(Call<EventoCompleto> call, Throwable t) {
                callback.onError(t.getMessage());
            }
        });
    }

    public void getEvents(EventsCallback callback) {
        Call<List<EventoFeed>> call = postgresqApi.getEventsForFeed();

        call.enqueue(new Callback<List<EventoFeed>>() {
            @Override
            public void onResponse(Call<List<EventoFeed>> call, Response<List<EventoFeed>> response) {
                if (response.isSuccessful() && response.body() != null) {
                    callback.onSuccess(response.body());
                } else {
                    callback.onError("Resposta falhou: " + response);
                }
            }

            @Override
            public void onFailure(Call<List<EventoFeed>> call, Throwable t) {
                callback.onError(t.getMessage());
            }
        });
    }

    public void getEventsByUserId(Long userId, EventsCallback callback) {
        Call<List<EventoFeed>> call = postgresqApi.getEventsForFeedByUserId(userId);

        call.enqueue(new Callback<List<EventoFeed>>() {
            @Override
            public void onResponse(Call<List<EventoFeed>> call, Response<List<EventoFeed>> response) {
                if (response.isSuccessful() && response.body() != null) {
                    callback.onSuccess(response.body());
                } else {
                    callback.onError("Resposta falhou: " + response);
                }
            }

            @Override
            public void onFailure(Call<List<EventoFeed>> call, Throwable t) {
                callback.onError(t.getMessage());
            }
        });
    }

    public void getEventsByTagId(long tagId, EventsCallback callback) {
        Call<List<EventoFeed>> call = postgresqApi.getEventsByTagId(tagId);

        call.enqueue(new Callback<List<EventoFeed>>() {
            @Override
            public void onResponse(Call<List<EventoFeed>> call, Response<List<EventoFeed>> response) {
                if (response.isSuccessful() && response.body() != null) {
                    callback.onSuccess(response.body());
                } else {
                    callback.onError("Resposta falhou: " + response);
                }
            }

            @Override
            public void onFailure(Call<List<EventoFeed>> call, Throwable t) {
                callback.onError(t.getMessage());
            }
        });
    }

    public void getEventsByDate(String date, EventsCallback callback) {
        Call<List<EventoFeed>> call = postgresqApi.getEventsByDate(date);

        call.enqueue(new Callback<List<EventoFeed>>() {
            @Override
            public void onResponse(Call<List<EventoFeed>> call, Response<List<EventoFeed>> response) {
                if (response.isSuccessful() && response.body() != null) {
                    callback.onSuccess(response.body());
                } else {
                    callback.onError("Resposta falhou: " + response);
                }
            }

            @Override
            public void onFailure(Call<List<EventoFeed>> call, Throwable t) {
                callback.onError(t.getMessage());
            }
        });
    }

    public void getUserById(long id, UserByIdCallback callback) {
        Call<User> call = postgresqApi.getUserById(id);

        call.enqueue(new Callback<User>() {
            @Override
            public void onResponse(Call<User> call, Response<User> response) {
                if (response.isSuccessful() && response.body() != null) {
                    callback.onSuccess(response.body());
                } else {
                    callback.onError("Resposta falhou: " + response);
                }
            }

            @Override
            public void onFailure(Call<User> call, Throwable t) {
                callback.onError(t.getMessage());
            }
        });
    }

    public void getUserByEmail(String email, UserByIdCallback callback) {
        Call<User> call = postgresqApi.getUserByEmail(email);

        call.enqueue(new Callback<User>() {
            @Override
            public void onResponse(Call<User> call, Response<User> response) {
                if (response.isSuccessful() && response.body() != null) {
                    callback.onSuccess(response.body());
                } else {
                    callback.onError("Resposta falhou: " + response);
                }
            }

            @Override
            public void onFailure(Call<User> call, Throwable t) {
                callback.onError(t.getMessage());
            }
        });
    }

    public void createUsuarioConsumidor(UsuarioConsumidor usuarioConsumidor, UsuarioConsumidorCallback callback) {
        Call<UsuarioConsumidor> call = postgresqApi.createUsuarioConsumidor(usuarioConsumidor);

        call.enqueue(new Callback<UsuarioConsumidor>() {
            @Override
            public void onResponse(Call<UsuarioConsumidor> call, Response<UsuarioConsumidor> response) {
                if (response.isSuccessful() && response.body() != null) {
                    callback.onSuccess(response.body());
                }
            }
            @Override
            public void onFailure(Call<UsuarioConsumidor> call, Throwable t) {
                callback.onError(t.getMessage());
            }
        });
    }

    public void createUsuarioAnunciante(UsuarioAnunciante usuarioAnunciante, UsuarioAnuncianteCallback callback) {
        Call<UsuarioAnunciante> call = postgresqApi.createUsuarioAnunciante(usuarioAnunciante);

        call.enqueue(new Callback<UsuarioAnunciante>() {
            @Override
            public void onResponse(Call<UsuarioAnunciante> call, Response<UsuarioAnunciante> response) {
                if (response.isSuccessful() && response.body() != null) {
                    callback.onSuccess(response.body());
                }
            }
            @Override
            public void onFailure(Call<UsuarioAnunciante> call, Throwable t) {
                callback.onError(t.getMessage());
            }
        });
    }

    public void createEvento(Evento2 evento, CreateEventoCallback callback) {
        Call<CreateEventoResponse> call = postgresqApi.createEvento(evento);

        call.enqueue(new Callback<CreateEventoResponse>() {
            @Override
            public void onResponse(Call<CreateEventoResponse> call, Response<CreateEventoResponse> response) {
                if (response.isSuccessful() && response.body() != null) {
                    callback.onResponse(response.body());
                }
            }
            @Override
            public void onFailure(Call<CreateEventoResponse> call, Throwable t) {
                callback.onError(t.getMessage());
            }
        });
    }

    public void createCompra(CreateCompra createCompra, CreateCompraCallback callback) {
        Call<CreateCompra> call = postgresqApi.createCompra(createCompra);

        call.enqueue(new Callback<CreateCompra>() {
            @Override
            public void onResponse(Call<CreateCompra> call, Response<CreateCompra> response) {}
            @Override
            public void onFailure(Call<CreateCompra> call, Throwable t) {
                callback.onError(t.getMessage());
            }
        });
    }

    public void pagamento(long cdCompra) {
        postgresqApi.pagamento(cdCompra);
    }

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

