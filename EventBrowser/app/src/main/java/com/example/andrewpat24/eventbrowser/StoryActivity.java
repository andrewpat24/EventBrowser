package com.example.andrewpat24.eventbrowser;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.view.View;

import java.util.UUID;

/**
 * Created by aculanay on 11/26/16.
 */

public class StoryActivity extends FragmentActivity {

    private static final String EXTRA_STORY_ID = "com.example.andrewpat24.eventbrowser.story_id";

    protected Fragment createFragment() {
        UUID storyID = (UUID) getIntent().getSerializableExtra(EXTRA_STORY_ID);

        return Story_fragment.newInstance(storyID);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_story);

        FragmentManager fragmentManager = getSupportFragmentManager();

        Fragment storyFragmentObj = fragmentManager.findFragmentById(R.id.activity_story);
        if(storyFragmentObj == null)
            storyFragmentObj =  createFragment();

        fragmentManager.beginTransaction().add(R.id.card_container_fragment, storyFragmentObj).commit();

    }

    public static Intent newIntent(Context packageContext, UUID storyID){
        Intent intent = new Intent(packageContext, StoryActivity.class);
        intent.putExtra(EXTRA_STORY_ID, storyID);
        return intent;
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
        return;

    }

    public void getDirections(View view){
        // TODO: Concatenate location of event so that maps opens to event location once data is available

        String url = "https://www.google.com/maps";

        Intent intent = new Intent(Intent.ACTION_VIEW);

        intent.setData(Uri.parse(url));
        startActivity(intent);

    }
}
