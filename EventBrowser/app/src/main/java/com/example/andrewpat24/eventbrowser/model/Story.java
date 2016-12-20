package com.example.andrewpat24.eventbrowser.model;

import android.util.Log;

import com.example.andrewpat24.eventbrowser.controller.StoryLibrary;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import static com.android.volley.VolleyLog.TAG;

/**
 * Created by Ajinkya on 23/11/16.
 */

public class Story {

    private UUID mUUID;
    private String mName;
    private String mDescription;
    private String mImage;
    private int mID;
    private double mLatitude;
    private double mLongitude;

    public  Story(String name, String description, String image){
        mUUID = UUID.randomUUID();
        mName = name;
        mDescription = description;
        mImage = image;
    }

    public String getDescription() {
        return mDescription;
    }

    public String getName() {
        return mName;
    }

    public UUID getUUID() {
        return mUUID;
    }


    public String getImage() {
        return mImage;
    }

    public void setID(int ID) {
        mID = ID;
    }

    public double getLatitude() {
        return mLatitude;
    }

    public double getLongitude() {
        return mLongitude;
    }

    public void setLatitude(double latitude) {
        mLatitude = latitude;
    }

    public void setLongitude(double longitude) {
        mLongitude = longitude;
    }

    public static List<Story> parseJASON(JSONArray jasonData){
        List<Story> stories = new ArrayList<>();
        try{
            Story story;
            JSONObject object;
            String image="";
            final int n = jasonData.length();
            for (int i = 0; i < n; ++i) {
                object = jasonData.getJSONObject(i);
                if(object.has("key_photo")){
                    if(((JSONObject)object.get("key_photo")).get("photo_link")!=null) {
                        image = ((JSONObject) object.get("key_photo")).getString("photo_link");
                    }
                }
                else if(object.has("photos")) {
                    if(((JSONArray)object.get("photos")).getJSONObject(0).has("photo_link")) {
                        image = ((JSONArray)object.get("photos")).getJSONObject(0).getString("photo_link");
                    }
                }

                story = new Story(object.getString("name"),object.getString("description"),image);

                if(object.has("id"))
                    story.setID(object.getInt("id"));
                if(object.has("lat"))
                    story.setLatitude(object.getDouble("lat"));
                if(object.has("lon"))
                    story.setLongitude(object.getDouble("lon"));

                stories.add(story);
            }
        }
        catch (JSONException e){
            Log.e(TAG, e.getMessage());
        }
        return stories;

    }

    public static Story getStory(UUID storyUUID){

        List<Story> stories = StoryLibrary.getStoryLibrary().getStories();
        for(Story story : stories){
            if(story.getUUID().equals(storyUUID))
                return story;
        }

        return null;
    }


}
