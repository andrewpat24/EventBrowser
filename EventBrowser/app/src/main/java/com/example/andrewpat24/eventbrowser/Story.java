package com.example.andrewpat24.eventbrowser;

import java.util.ArrayList;
import java.util.UUID;

/**
 * Created by Ajinkya on 23/11/16.
 */

public class Story {

    private UUID mUUID;
    private String mName;
    private String mDescription;
    private int mImageResourceID;

    private String mImage;

    public  Story(String name, String description, String image){
        mUUID = UUID.randomUUID();
        mName = name;
        mDescription = description;
        mImage = image;
    }

    public Story(String name, String description, int imageResourceID){
        mUUID = UUID.randomUUID();
        mName = name;
        mDescription = description;
        mImageResourceID = imageResourceID;
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

    public int getImageResourceID() {
        return mImageResourceID;
    }

    public String getImage() {
        return mImage;
    }

    public static ArrayList<Story> processStoriesList(ArrayList<Story> storyList, String query){

        if(query.equals("")){
            return storyList;
        }

        ArrayList<Story> processedList = new ArrayList<Story>();
        for(Story story : storyList){
            if(story.getName().toLowerCase().contains(query.toLowerCase())){
                processedList.add(story);
            }
        }

        return processedList;
    }
}
