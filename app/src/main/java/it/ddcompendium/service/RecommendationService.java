package it.ddcompendium.service;

import java.util.List;

import it.ddcompendium.entities.Spell;
import it.ddcompendium.entities.Recommendation;
import it.ddcompendium.entities.User;
import it.ddcompendium.requests.Callback;
import it.ddcompendium.service.responses.Status;

public interface RecommendationService extends Service {
    void getAll(User to, Callback<List<Recommendation>> callback);

    void add(User by, User to, Spell spell, Callback<Status> callback);

    void delete(Recommendation recommendation, Callback<Status> callback);
}
