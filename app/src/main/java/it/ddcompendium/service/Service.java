package it.ddcompendium.service;

import com.google.gson.Gson;

public interface Service {
    Gson GSON = new Gson();
    String SERVER_URL = "http://192.168.1.72:8080/DDCompendium";
}
