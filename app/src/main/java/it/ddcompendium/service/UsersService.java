package it.ddcompendium.service;

import it.ddcompendium.entities.User;
import it.ddcompendium.requests.Callback;
import it.ddcompendium.service.responses.Status;

public interface UsersService extends Service {

    void login(String username, String password, Callback<User> callback);

    void signUp(String username, String email, String password, Callback<Status> callback);

    void findUser(String username, Callback<User> callback);

    void hideKeyboard();
}
