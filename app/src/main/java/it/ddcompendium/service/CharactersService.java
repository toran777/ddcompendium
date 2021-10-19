package it.ddcompendium.service;

import java.util.List;

import it.ddcompendium.entities.Character;
import it.ddcompendium.entities.Status;
import it.ddcompendium.requests.Callback;

public interface CharactersService extends Service {
    void getAll(Integer id, Callback<List<Character>> callback);

    void insert(Character character, Callback<Status> callback);

    // update

    void delete(Integer id, Callback<Status> callback);
}
