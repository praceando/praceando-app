package com.firstclass.praceando.API.Flask;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class FlaskAPI {

    private Retrofit retrofit = new Retrofit.Builder()
            .baseUrl("http://ec2-18-214-214-79.compute-1.amazonaws.com:5002/")
            .addConverterFactory(GsonConverterFactory.create())
            .build();

    private FlaskAPIInterface flaskApi = retrofit.create(FlaskAPIInterface.class);

    public void getEventsIds(long userId, EventsIdsCallback callback) {
        Call<EventsIdsResponse> call = flaskApi.getEventsIds(userId);

        call.enqueue(new Callback<EventsIdsResponse>() {
            @Override
            public void onResponse(Call<EventsIdsResponse> call, Response<EventsIdsResponse> response) {
                if (response.isSuccessful() && response.body() != null) {
                    callback.onSuccess(response.body());
                } else {
                    callback.onError("Resposta falhou: " + response.message());
                }
            }

            @Override
            public void onFailure(Call<EventsIdsResponse> call, Throwable t) {
                callback.onError(t.getMessage());
            }
        });
    }

}


