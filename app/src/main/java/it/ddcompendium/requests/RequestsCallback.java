package it.ddcompendium.requests;

import com.android.volley.VolleyError;

public interface RequestsCallback {
    void onResponse(String jsonString);

    void onError(VolleyError error);
}
