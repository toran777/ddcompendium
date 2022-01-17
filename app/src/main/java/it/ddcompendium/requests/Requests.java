package it.ddcompendium.requests;

import android.annotation.SuppressLint;
import android.content.Context;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;

public class Requests {
    @SuppressLint("StaticFieldLeak")
    private static Requests instance;
    private final Context ctx;
    private RequestQueue requestQueue;

    private Requests(Context context) {
        ctx = context;
        requestQueue = getRequestQueue();
    }

    public static synchronized Requests getInstance(Context context) {
        if (instance == null) {
            instance = new Requests(context);
        }
        return instance;
    }

    public RequestQueue getRequestQueue() {
        if (requestQueue == null) {
            requestQueue = Volley.newRequestQueue(ctx.getApplicationContext());
        }
        return requestQueue;
    }

    public <T> void addToRequestQueue(Request<T> req) {
        getRequestQueue().add(req);
    }
}
