package com.example.andrewpat24.eventbrowser.activity;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.view.View;

import com.example.andrewpat24.eventbrowser.R;
import com.example.andrewpat24.eventbrowser.model.Story;
import com.example.andrewpat24.eventbrowser.fragment.StoryFragment;

import java.util.UUID;

/**
 * Created by aculanay on 11/26/16.
 */

public class StoryActivity extends FragmentActivity {

    private static final String EXTRA_STORY_ID = "StoryUUID";

    private UUID mStoryID;
    protected Fragment createFragment() {
         mStoryID = (UUID) getIntent().getSerializableExtra(EXTRA_STORY_ID);

        return StoryFragment.newInstance(mStoryID);
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

		com.example.andrewpat24.eventbrowser.fragment.MapFragment mapFragment = (com.example.andrewpat24.eventbrowser.fragment.MapFragment) getSupportFragmentManager().findFragmentById(R.id.map_fragment_container);

		if(mapFragment == null) {
			mapFragment = com.example.andrewpat24.eventbrowser.fragment.MapFragment.newInstance();
			getSupportFragmentManager().beginTransaction().add(R.id.map_fragment_container, mapFragment).commit();
		}

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
        Story story = Story.getStory(mStoryID);
        String longitude =  Double.toString(story.getLongitude());
        String latitude = Double.toString(story.getLatitude());
        Uri gmmIntentUri = Uri.parse("google.navigation:q=" + latitude + "," +longitude);
        Intent mapIntent = new Intent(Intent.ACTION_VIEW, gmmIntentUri);
        mapIntent.setPackage("com.google.android.apps.maps");
        startActivity(mapIntent);

    }
}
