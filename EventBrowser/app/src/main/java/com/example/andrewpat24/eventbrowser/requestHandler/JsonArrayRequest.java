package com.example.andrewpat24.eventbrowser.requestHandler;

import android.util.Log;

import com.android.volley.NetworkResponse;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.example.andrewpat24.eventbrowser.model.Story;
import org.json.JSONArray;
import org.json.JSONException;

import java.util.List;

/**
 * Created by Ajinkya on 10/12/16.
 */

public class JsonArrayRequest extends Request<List<Story>> {

    private Response.Listener<List<Story>> successListener;


    public JsonArrayRequest( int method,
                        String url,
                        Response.Listener<List<Story>> successListener,
                        Response.ErrorListener errorListener) {
        super(method, url, errorListener);
        this.successListener = successListener;
    }

    @Override
    protected Response<List<Story>> parseNetworkResponse(NetworkResponse response) {
        String jsonString = new String(response.data);
        List<Story> stories;
        JSONArray jsonArray;
        Log.i("JSON Response from API", jsonString);
        try {
            jsonArray = new JSONArray(jsonString);
            stories = Story.parseJASON(jsonArray);
        }
        catch (JSONException e) {
            e.printStackTrace();
            return Response.error(new VolleyError("Failed to process the request"));
        }
        return Response.success(stories, getCacheEntry());
    }

    @Override
    protected void deliverResponse(List<Story> stories) {
        successListener.onResponse(stories);
    }
}
