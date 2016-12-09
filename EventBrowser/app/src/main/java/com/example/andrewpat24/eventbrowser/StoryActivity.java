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

    private UUID mStoryID;
    protected Fragment createFragment() {
         mStoryID = (UUID) getIntent().getSerializableExtra(EXTRA_STORY_ID);

        return Story_fragment.newInstance(mStoryID);
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
        Story story = StoryLibrary.getInstance(view.getContext()).getStory(mStoryID);
        String longitude =  Double.toString(story.getLongitude());
        String latitude = Double.toString(story.getLatitude());
        Uri gmmIntentUri = Uri.parse("google.navigation:q=" + latitude + "," +longitude);
        Intent mapIntent = new Intent(Intent.ACTION_VIEW, gmmIntentUri);
        mapIntent.setPackage("com.google.android.apps.maps");
        startActivity(mapIntent);

    }
}
