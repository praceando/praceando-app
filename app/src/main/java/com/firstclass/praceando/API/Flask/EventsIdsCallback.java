package com.firstclass.praceando.API.Flask;
import java.util.List;

public interface EventsIdsCallback {
    void onSuccess(EventsIdsResponse response);
    void onError(String errorMessage);
}
