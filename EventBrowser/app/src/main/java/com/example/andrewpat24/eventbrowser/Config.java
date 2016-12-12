package com.example.andrewpat24.eventbrowser;

import android.net.Uri;

/**
 * Created by Ajinkya on 05/12/16.
 */

public class Config {


    //General configs
    private static int PAGE_SIZE = 50;

    //Meetup API configs
    private static String KEY_MEETUP = "1654194f297f7f4626dc757dc7a1d";
    private static String HIT_MEETUP = "https://api.meetup.com/find/groups?&sign=true&photo-host=secure";

    public static String getData(String query) {
        StringBuilder recommend = new StringBuilder(HIT_MEETUP);
        recommend.append("&page=");
        recommend.append(PAGE_SIZE);
        recommend.append("&key=");
        recommend.append(KEY_MEETUP);

        if(query.length() > 1) {
            recommend.append("&text=");
            recommend.append(Uri.encode(query));
        }
        return recommend.toString();
    }
}
