package org.daniils.vloerinspection.data.api;

import android.content.Context;

import com.android.volley.ParseError;
import com.android.volley.RequestQueue;
import com.android.volley.Response;

import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import com.android.volley.Request.Method;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.util.HashMap;

public class RequestsManager {

    public class Request<T> {
        private RequestsManager requestsManager;
        private HashMap<String, String> params = new HashMap<>();
        private String url;
        private int method = Method.GET;
        private TypeReference<T> type;

        Request(RequestsManager requestsManager, String url, TypeReference<T> type) {
            this.requestsManager = requestsManager;
            this.url = url;
            this.type = type;
        }

        public void perform(Response.Listener<T> listener, Response.ErrorListener errorListener) {
            requestsManager.performRequest(this, listener, errorListener);
        }

        public void setParameter(String key, String value) {
            params.put(key, value);
        }

        public void setParameter(String key, int value) {
            setParameter(key, Integer.toString(value));
        }

        public void setMethod(int method) {
            this.method = method;
        }
    }

    private RequestQueue requestQueue;
    private String host;

    public RequestsManager(Context context, String host) {
        this.requestQueue = Volley.newRequestQueue(context);
        this.host = host;
    }

    public <T> Request<T> newRequest(String method, TypeReference<T> type) {
        return new Request<>(this, method, type);
    }

    public <T> void performRequest(Request<T> request, Response.Listener<T> listener, Response.ErrorListener errorListener) {
        String url = URLGenerator.generateURL(this.host, request.url, request.params);
        StringRequest stringRequest = new StringRequest(request.method, url, response -> {
            try {
                T parsedObject = new ObjectMapper().readValue(response, request.type);
                if (parsedObject == null)
                    throw new NullPointerException();
                listener.onResponse(parsedObject);
            } catch (IOException | NullPointerException e) {
                errorListener.onErrorResponse(new ParseError(e));
            }
        }, errorListener);
        requestQueue.add(stringRequest);
    }
}
