package it.ddcompendium.requests;

import it.ddcompendium.service.responses.Status;

public interface Callback<T> {
    void onSuccess(T t);

    void onFailure(Status status);
}
