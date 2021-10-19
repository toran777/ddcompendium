package it.ddcompendium.service.impl;

import android.content.Context;
import android.util.Log;

import com.android.volley.Request;
import com.android.volley.VolleyError;
import com.google.gson.JsonSyntaxException;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

import it.ddcompendium.entities.Character;
import it.ddcompendium.entities.Status;
import it.ddcompendium.requests.Callback;
import it.ddcompendium.requests.RequestsCallback;
import it.ddcompendium.requests.RequestsType;
import it.ddcompendium.service.CharactersService;
import it.ddcompendium.service.responses.CharactersListResponse;
import it.ddcompendium.service.responses.StatusResponse;

public class CharactersServiceImpl implements CharactersService {
    private static final String TAG = CharactersServiceImpl.class.getSimpleName();

    private final RequestsType mRequests;

    public CharactersServiceImpl(Context context) {
        mRequests = new RequestsType(context);
    }

    @Override
    public void getAll(Integer id, Callback<List<Character>> callback) {
        mRequests.get(SERVER_URL + "/Character?id=" + id, new RequestsCallback() {
            @Override
            public void onResponse(String jsonString) {
                Log.i(TAG, "onResponse: " + jsonString);
                try {
                    CharactersListResponse response = GSON.fromJson(jsonString, CharactersListResponse.class);
                    if (response.getStatus().getCode() == 0) {
                        List<Character> characters = new ArrayList<>(Arrays.asList(response.getCharacters()));
                        callback.onSuccess(characters);
                    } else {
                        callback.onFailure(response.getStatus());
                    }
                } catch (JsonSyntaxException e) {
                    Log.e(TAG, "onResponse: ", e);
                    callback.onFailure(new Status("Parsing"));
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
    public void insert(Character character, Callback<Status> callback) {
        HashMap<String, String> data = new HashMap<>();
        data.put("name", character.getName());
        data.put("class", character.getClasse());
        data.put("idUser", character.getIdUser() + "");

        mRequests.post(Request.Method.POST, SERVER_URL + "/Character", data, new RequestsCallback() {
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
        mRequests.post(Request.Method.DELETE, SERVER_URL + "/Character?id=" + id, null, new RequestsCallback() {
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
