package it.ddcompendium.service;

import it.ddcompendium.entities.Status;
import it.ddcompendium.entities.User;
import it.ddcompendium.requests.Callback;

public interface UsersService extends Service {

    void login(String username, String password, Callback<User> callback);

    void signUp(String username, String email, String password, Callback<Status> callback);

    void hideKeyboard();
}
