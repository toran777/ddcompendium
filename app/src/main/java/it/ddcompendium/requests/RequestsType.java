package it.ddcompendium.requests;

import android.content.Context;

import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;

public class RequestsType {
    private final Context mContext;

    public RequestsType(Context context) {
        this.mContext = context;
    }

    public void get(String url, RequestsCallback callback) {
        StringRequest request = new StringRequest(url, callback::onResponse, callback::onError);
        Requests.getInstance(mContext).addToRequestQueue(request);
    }

    public void post(int requestType, String url, HashMap<String, String> data, RequestsCallback callback) {
        StringRequest request = new StringRequest(requestType, url, callback::onResponse, callback::onError) {
            @Override
            public byte[] getBody() {
                return data.toString().getBytes();
            }

            @Override
            public String getBodyContentType() {
                return "application/json";
            }
        };

        Requests.getInstance(mContext).addToRequestQueue(request);
    }
}
