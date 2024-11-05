package com.firstclass.praceando.API.postgresql.callbackInterfaces;
import com.firstclass.praceando.entities.Tag;

import java.util.List;

public interface TagsCallback {
    void onSuccess(List<Tag> tags);
    void onError(String errorMessage);
}
