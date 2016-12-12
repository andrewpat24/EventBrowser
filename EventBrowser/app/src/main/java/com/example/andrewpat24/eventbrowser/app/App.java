package com.example.andrewpat24.eventbrowser.app;

import android.app.Application;
import android.content.Context;

import java.util.UUID;

/**
 * Created by Ajinkya on 10/12/16.
 */

public class App extends Application {

    private static App application;
    private static UUID sStoryListActivity;

    @Override
    public void onCreate() {
        application = this;
        super.onCreate();
    }

    public static Context getContext(){
        return application.getApplicationContext();
    }

    public static UUID getStoryListActivityUUID(){return sStoryListActivity;}

    public static void setStoryListActivityUUID(UUID current){sStoryListActivity = current;}

}
