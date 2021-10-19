package it.ddcompendium.requests;

import it.ddcompendium.entities.Status;

public interface Callback<T> {
    void onSuccess(T t);

    void onFailure(Status status);
}
