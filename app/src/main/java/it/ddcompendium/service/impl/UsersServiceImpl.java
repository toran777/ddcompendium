package it.ddcompendium.service.impl;

import android.app.Activity;
import android.content.Context;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;

import com.android.volley.Request;
import com.android.volley.VolleyError;

import java.util.HashMap;

import it.ddcompendium.entities.User;
import it.ddcompendium.requests.Callback;
import it.ddcompendium.requests.RequestsCallback;
import it.ddcompendium.requests.RequestsType;
import it.ddcompendium.service.UsersService;
import it.ddcompendium.service.responses.Status;
import it.ddcompendium.service.responses.UserStatusResponse;

public class UsersServiceImpl implements UsersService {
    private static final String TAG = UsersServiceImpl.class.getSimpleName();

    private final RequestsType mRequests;
    private final Context mContext;

    public UsersServiceImpl(Context context) {
        mContext = context;
        mRequests = new RequestsType(context);
    }

    @Override
    public void login(String username, String password, Callback<User> callback) {
        HashMap<String, String> data = new HashMap<>();
        data.put("username", username);
        data.put("password", password);
        data.put("action", "login");

        mRequests.post(Request.Method.POST, SERVER_URL + "/login", data, new RequestsCallback() {
            @Override
            public void onResponse(String jsonString) {
                Log.d(TAG, "onResponse: " + jsonString);
                UserStatusResponse userStatus = GSON.fromJson(jsonString, UserStatusResponse.class);

                if (userStatus.getStatus().getCode() == 0)
                    callback.onSuccess(userStatus.getData());
                else
                    callback.onFailure(userStatus.getStatus());
            }

            @Override
            public void onError(VolleyError error) {
                Log.e(TAG, "onError: ", error);
                callback.onFailure(new Status(error.toString()));
            }
        });
    }

    @Override
    public void signUp(String username, String email, String password, Callback<Status> callback) {
        HashMap<String, String> data = new HashMap<>();
        data.put("username", username);
        data.put("email", email);
        data.put("password", password);
        data.put("action", "registration");

        mRequests.post(Request.Method.POST, SERVER_URL + "/register", data, new RequestsCallback() {
            @Override
            public void onResponse(String jsonString) {
                Log.d(TAG, "onResponse: " + jsonString);
                UserStatusResponse userStatus = GSON.fromJson(jsonString, UserStatusResponse.class);

                if (userStatus.getStatus().getCode() == 0)
                    callback.onSuccess(userStatus.getStatus());
                else
                    callback.onFailure(userStatus.getStatus());
            }

            @Override
            public void onError(VolleyError error) {
                Log.e(TAG, "onError: ", error);
                callback.onFailure(new Status(error.toString()));
            }
        });
    }

    @Override
    public void findUser(String username, Callback<User> callback) {
        HashMap<String, String> data = new HashMap<>();
        data.put("query", username);

        mRequests.post(Request.Method.POST, SERVER_URL + "/queryUser", data, new RequestsCallback() {
            @Override
            public void onResponse(String jsonString) {
                Log.d(TAG, "onResponse: " + jsonString);
                UserStatusResponse userStatus = GSON.fromJson(jsonString, UserStatusResponse.class);

                if (userStatus.getStatus().getCode() == 0)
                    callback.onSuccess(userStatus.getData());
                else
                    callback.onFailure(userStatus.getStatus());
            }

            @Override
            public void onError(VolleyError error) {
                Log.e(TAG, "onError: ", error);
                callback.onFailure(new Status(error.toString()));
            }
        });
    }

    @Override
    public void hideKeyboard() {
        InputMethodManager imm = (InputMethodManager) mContext.getSystemService(Activity.INPUT_METHOD_SERVICE);
        //Find the currently focused view, so we can grab the correct window token from it.
        View view = ((Activity) mContext).getCurrentFocus();
        //If no view currently has focus, create a new one, just so we can grab a window token from it
        if (view == null) {
            view = new View(mContext);
        }
        imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
    }
}
