package com.firstclass.praceando.API.postgresql.callbackInterfaces;
import com.firstclass.praceando.API.postgresql.entities.EventoFeed;
import java.util.List;

public interface EventsCallback {
    void onSuccess(List<EventoFeed> events);
    void onError(String errorMessage);
}
