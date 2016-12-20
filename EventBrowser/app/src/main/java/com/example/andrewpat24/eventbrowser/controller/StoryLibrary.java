package com.example.andrewpat24.eventbrowser.controller;

import android.util.Log;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.example.andrewpat24.eventbrowser.Config;
import com.example.andrewpat24.eventbrowser.interfaceAPI.WebMessenger;
import com.example.andrewpat24.eventbrowser.app.App;
import com.example.andrewpat24.eventbrowser.model.Story;
import com.example.andrewpat24.eventbrowser.requestHandler.JsonArrayRequest;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Ajinkya on 23/11/16.
 */
public class  StoryLibrary {

    private final int TAG = 100;
    private OnResponseListener mResponseListener;
    private List<Story> mStories;
    private static StoryLibrary sStoryLibrary;

    public StoryLibrary(OnResponseListener responseListener) {
        mResponseListener = responseListener;
        mStories = new ArrayList<>();
    }

    public void sendRequest(String query){

        int method = Request.Method.GET;
        String url = Config.getData(query);
        Log.i("API Url", url);

        JsonArrayRequest request
                = new JsonArrayRequest(
                method,
                url,
                new Response.Listener<List<Story>>() {
                    @Override
                    public void onResponse(List<Story> stories) {
                        mResponseListener.onSuccess(stories);
                        mStories.clear();
                        mStories.addAll(stories);
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        mResponseListener.onFailure(error.getMessage());
                    }
                }
        );

        request.setTag(TAG);
        WebMessenger.getInstance(App.getContext()).addToRequestQueue(request);
    }

    public void cancelAllRequests() {
        WebMessenger.getInstance(App.getContext()).cancelAllRequests(TAG);
    }

    public interface OnResponseListener {
        void onSuccess(List<Story> stories);
        void onFailure(String errorMessage);
    }

    public static synchronized StoryLibrary getStoryLibrary(OnResponseListener responseListener){
        if(sStoryLibrary == null) sStoryLibrary = new StoryLibrary(responseListener);
        else sStoryLibrary.mResponseListener = responseListener;
        return sStoryLibrary;
    }

    public static StoryLibrary getStoryLibrary(){
        return sStoryLibrary;
    }

    public List<Story> getStories(){
        return mStories;
    }

}
