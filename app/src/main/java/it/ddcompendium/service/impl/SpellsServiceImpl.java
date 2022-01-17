package it.ddcompendium.service.impl;

import android.content.Context;
import android.util.Log;

import com.android.volley.Request;
import com.android.volley.VolleyError;

import java.util.HashMap;
import java.util.List;

import it.ddcompendium.entities.Spell;
import it.ddcompendium.requests.Callback;
import it.ddcompendium.requests.RequestsCallback;
import it.ddcompendium.requests.RequestsType;
import it.ddcompendium.service.SpellsService;
import it.ddcompendium.service.responses.SpellListStatus;
import it.ddcompendium.service.responses.Status;
import it.ddcompendium.service.responses.StatusResponse;

public class SpellsServiceImpl implements SpellsService {
    private static final String TAG = SpellsServiceImpl.class.getSimpleName();

    private final RequestsType requests;

    public SpellsServiceImpl(Context context) {
        requests = new RequestsType(context);
    }

    @Override
    public void getAll(int offset, Callback<List<Spell>> callback) {
        requests.get(SERVER_URL + "/Spell?offset=" + offset, new RequestsCallback() {
            @Override
            public void onResponse(String jsonString) {
                Log.d(TAG, "onResponse: " + jsonString);
                SpellListStatus response = GSON.fromJson(jsonString, SpellListStatus.class);

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
    public void delete(Integer idCharacter, Integer idSpell, Callback<Status> callback) {
        requests.post(Request.Method.DELETE, SERVER_URL + "/Spell?idCharacter=" + idCharacter + "&idSpell=" + idSpell, null, new RequestsCallback() {
            @Override
            public void onResponse(String jsonString) {
                Log.d(TAG, "onResponse: " + jsonString);
                StatusResponse response = GSON.fromJson(jsonString, StatusResponse.class);

                if (response.getStatus().getCode() == 0)
                    callback.onSuccess(response.getStatus());
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
    public void insert(Integer idCharacter, Integer idSpell, Callback<Status> callback) {
        HashMap<String, String> data = new HashMap<>();
        data.put("idCharacter", idCharacter + "");
        data.put("idSpell", idSpell + "");

        requests.post(Request.Method.POST, SERVER_URL + "/Spell", data, new RequestsCallback() {
            @Override
            public void onResponse(String jsonString) {
                Log.d(TAG, "onResponse: " + jsonString);
                StatusResponse response = GSON.fromJson(jsonString, StatusResponse.class);

                if (response.getStatus().getCode() == 0)
                    callback.onSuccess(response.getStatus());
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
    public void search(String query, Callback<List<Spell>> callback) {
        HashMap<String, String> data = new HashMap<>();
        data.put("query", query);

        requests.post(Request.Method.POST, SERVER_URL + "/QuerySpells", data, new RequestsCallback() {
            @Override
            public void onResponse(String jsonString) {
                Log.d(TAG, "onResponse: " + jsonString);
                SpellListStatus response = GSON.fromJson(jsonString, SpellListStatus.class);

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
}
