package it.ddcompendium.service.impl;

import android.content.Context;
import android.util.Log;

import com.android.volley.Request;
import com.android.volley.VolleyError;

import java.util.HashMap;
import java.util.List;

import it.ddcompendium.entities.Recommendation;
import it.ddcompendium.entities.User;
import it.ddcompendium.requests.Callback;
import it.ddcompendium.requests.RequestsCallback;
import it.ddcompendium.requests.RequestsType;
import it.ddcompendium.service.RecommendationService;
import it.ddcompendium.service.responses.Status;
import it.ddcompendium.service.responses.StatusResponse;
import it.ddcompendium.service.responses.SuggestionListStatusResponse;

public class RecommendationServiceImpl implements RecommendationService {
    private static final String TAG = RecommendationServiceImpl.class.getSimpleName();

    private final RequestsType requests;

    public RecommendationServiceImpl(Context context) {
        requests = new RequestsType(context);
    }

    @Override
    public void getAll(User to, Callback<List<Recommendation>> callback) {
        requests.get(SERVER_URL + "/recommend?id=" + to.getId(), new RequestsCallback() {
            @Override
            public void onResponse(String jsonString) {
                Log.d(TAG, "onResponse: " + jsonString);
                SuggestionListStatusResponse response = GSON.fromJson(jsonString, SuggestionListStatusResponse.class);

                if (response.getStatus().getCode() == 0)
                    callback.onSuccess(response.getData());
                else
                    callback.onFailure(response.getStatus());
            }

            @Override
            public void onError(VolleyError error) {
                Log.e(TAG, "onError: ", error);
                callback.onFailure(new Status(error.toString()));
            }
        });
    }

    @Override
    public void add(Integer by, Integer to, Integer spell, Callback<Status> callback) {
        HashMap<String, String> data = new HashMap<>();
        data.put("user_by", by + "");
        data.put("user_to", to + "");
        data.put("spell_id", spell + "");

        requests.post(Request.Method.POST, SERVER_URL + "/recommend", data, new RequestsCallback() {
            @Override
            public void onResponse(String jsonString) {
                Log.i(TAG, "onResponse: " + jsonString);
                StatusResponse statusResponse = GSON.fromJson(jsonString, StatusResponse.class);
                if (statusResponse.getStatus().getCode() == 0) {
                    callback.onSuccess(statusResponse.getStatus());
                } else {
                    callback.onFailure(statusResponse.getStatus());
                }
            }

            @Override
            public void onError(VolleyError error) {
                Log.e(TAG, "onError: ", error);
                callback.onFailure(new Status("Server Error, try again later!"));
            }
        });
    }

    @Override
    public void delete(Integer id, Callback<Status> callback) {
        requests.post(Request.Method.DELETE, SERVER_URL + "/recommend?id=" + id, null, new RequestsCallback() {
            @Override
            public void onResponse(String jsonString) {
                Log.i(TAG, "onResponse: " + jsonString);
                StatusResponse statusResponse = GSON.fromJson(jsonString, StatusResponse.class);
                if (statusResponse.getStatus().getCode() == 0) {
                    callback.onSuccess(statusResponse.getStatus());
                } else {
                    callback.onFailure(statusResponse.getStatus());
                }
            }

            @Override
            public void onError(VolleyError error) {
                Log.e(TAG, "onError: ", error);
                callback.onFailure(new Status("Server Error, try again later!"));
            }
        });
    }
}
