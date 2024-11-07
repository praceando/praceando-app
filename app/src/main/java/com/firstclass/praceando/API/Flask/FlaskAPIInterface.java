package com.firstclass.praceando.API.Flask;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface FlaskAPIInterface {

    @GET("recomendation/{userId}")
    Call<EventsIdsResponse> getEventsIds(@Path("userId") long id);
}
