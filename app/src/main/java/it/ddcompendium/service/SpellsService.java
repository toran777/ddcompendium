package it.ddcompendium.service;

import java.util.List;

import it.ddcompendium.entities.Spell;
import it.ddcompendium.entities.Status;
import it.ddcompendium.requests.Callback;

public interface SpellsService extends Service {
    void getAll(Callback<List<Spell>> callback);

    void delete(Integer idCharacter, Integer idSpell, Callback<Status> callback);

    void insert(Integer idCharacter, Integer idSpell, Callback<Status> callback);
}
