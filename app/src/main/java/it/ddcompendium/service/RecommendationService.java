package it.ddcompendium.service;

import java.util.List;

import it.ddcompendium.entities.Recommendation;
import it.ddcompendium.entities.User;
import it.ddcompendium.requests.Callback;
import it.ddcompendium.service.responses.Status;

public interface RecommendationService extends Service {
    void getAll(User to, Callback<List<Recommendation>> callback);

    void add(Integer by, Integer to, Integer spell, Callback<Status> callback);

    void delete(Integer id, Callback<Status> callback);
}
