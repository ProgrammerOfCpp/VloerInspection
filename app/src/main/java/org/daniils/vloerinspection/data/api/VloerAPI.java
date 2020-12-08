package org.daniils.vloerinspection.data.api;

import android.content.Context;

import com.android.volley.Response;
import com.fasterxml.jackson.core.type.TypeReference;

import org.daniils.vloerinspection.R;
import org.daniils.vloerinspection.data.model.Job;
import org.daniils.vloerinspection.data.model.Store;
import org.daniils.vloerinspection.data.model.User;
import java.util.List;

public class VloerAPI {
    private RequestsManager requestsManager;

    public VloerAPI(Context context) {
        requestsManager = new RequestsManager(context, context.getString(R.string.host_url));
    }

    public void getUser(String username,
                        String password,
                        Response.Listener<User> listener,
                        Response.ErrorListener errorListener) {
        RequestsManager.Request<User> request = requestsManager.newRequest("get_lowes.php", new TypeReference<User>() {});
        request.setParameter("username", username);
        request.setParameter("password", password);
        requestsManager.performJSONRequest(request, listener, errorListener);
    }

    public void getUserStores(int storeId,
                              Response.Listener<List<Store>> listener,
                              Response.ErrorListener errorListener) {

        RequestsManager.Request<List<Store>> request = requestsManager.newRequest(
                "get_userstoress.php",
                new TypeReference<List<Store>>() {});
        request.setParameter("store_id", storeId);
        requestsManager.performJSONRequest(request, listener, errorListener);
    }

    public void getLocalInstallJobs(int installerId,
                                    Response.Listener<List<Job>> listener,
                                    Response.ErrorListener errorListener) {
        RequestsManager.Request<List<Job>> request = requestsManager.newRequest(
                "get_localinstalljobs.php",
                new TypeReference<List<Job>>() {});
        //request.setParameter("installer_id", installerId);
        requestsManager.performJSONRequest(request, listener, errorListener);
    }
}
