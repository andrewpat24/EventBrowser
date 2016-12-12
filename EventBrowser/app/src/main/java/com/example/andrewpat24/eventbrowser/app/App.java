package com.example.andrewpat24.eventbrowser.app;

import android.app.Application;
import android.content.Context;

/**
 * Created by Ajinkya on 10/12/16.
 */

public class App extends Application {

    private static App application;

    @Override
    public void onCreate() {
        application = this;
        super.onCreate();
    }

    public static Context getContext(){
        return application.getApplicationContext();
    }
}
