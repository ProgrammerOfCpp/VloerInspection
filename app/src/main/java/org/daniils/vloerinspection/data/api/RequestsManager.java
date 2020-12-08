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

    public static class Request<T> {
        private HashMap<String, String> params = new HashMap<>();
        private String url;
        private int method = Method.GET;
        private TypeReference<T> type;

        Request(String url, TypeReference<T> type) {
            this.url = url;
            this.type = type;
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
        return new Request<>(method, type);
    }

    public <T> void performJSONRequest(Request<T> request, Response.Listener<T> listener, Response.ErrorListener errorListener) {
        String url = URLGenerator.generateURL(this.host, request.url, request.params);
        StringRequest stringRequest = new StringRequest(request.method, url, response -> {
            try {
                T parsedObject = new ObjectMapper().readValue(filterResponse(response), request.type);
                System.out.println(response);
                if (parsedObject == null)
                    throw new NullPointerException();
                listener.onResponse(parsedObject);
            } catch (IOException | NullPointerException e) {
                e.printStackTrace();
                errorListener.onErrorResponse(new ParseError(e));
            }
        }, errorListener);
        requestQueue.add(stringRequest);
    }

    public void performStringRequest(Request<String> request, Response.Listener<String> listener, Response.ErrorListener errorListener) {
        String url = URLGenerator.generateURL(this.host, request.url, request.params);
        StringRequest stringRequest = new StringRequest(request.method, url, listener, errorListener);
        requestQueue.add(stringRequest);
    }

    private String filterResponse(String response) {
        StringBuilder stringBuilder = new StringBuilder();
        for (String line : response.split("\n")) {
            if (!line.startsWith("<")) {
                stringBuilder.append(line).append("\n");
            }
        }
        return stringBuilder.toString();
    }
}
