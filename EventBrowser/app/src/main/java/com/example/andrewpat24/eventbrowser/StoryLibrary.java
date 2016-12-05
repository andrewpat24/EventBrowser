package com.example.andrewpat24.eventbrowser;

import android.content.Context;

import java.util.ArrayList;
import java.util.UUID;

/**
 * Created by Ajinkya on 23/11/16.
 */
public class StoryLibrary {

    private  ArrayList<Story> mStories;

    private static StoryLibrary ourInstance = new StoryLibrary();

    public static StoryLibrary getInstance() {
        return ourInstance;
    }

    private StoryLibrary() {

        mStories = new ArrayList<>();

        Story naturalSensations = new Story("Natural Sensations", "Snacky snacks!!",R.drawable.naturalsensations);
        mStories.add(naturalSensations);
        Story cafe101 = new Story("Cafe 101", "Cafe for all, haha!",R.drawable.cafe);
        mStories.add(cafe101);
        Story tBar = new Story("Tostada Bar", "We make tostada happen",R.drawable.tostada);
        mStories.add(tBar);
        Story goldCoast = new Story("Gold Coast", "Pay by weight buffet",R.drawable.goldcoast);
        mStories.add(goldCoast);
        Story hatienCove = new Story("Hatien Cove", "Lets cove it!",R.drawable.hatien);
        mStories.add(hatienCove);
        Story ikePlace = new Story("Ike's Place", "Lets ike it maybe?",R.drawable.ikes);
        mStories.add(ikePlace);
        Story iNoodles = new Story("iNoodles", "We love em",R.drawable.inoodles);
        mStories.add(iNoodles);
        Story nizarios = new Story("Nizario's", "Pizza's great!",R.drawable.nizarios);
        mStories.add(nizarios);

    }


    public ArrayList<Story> getStories() {
        return mStories;
    }

    public Story getStory(UUID storyID){

        for(Story story : mStories) {
            if (story.getUUID().equals(storyID)) {
                return story;
            }
        }

        return null;

    }

}
