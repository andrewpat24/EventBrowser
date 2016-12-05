package com.example.andrewpat24.eventbrowser;

import android.content.res.Resources;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.UUID;

/**
 * Created by aculanay on 11/26/16.
 */

public class Story_fragment extends Fragment {

    public static final String ARG_STORY_ID = "story_id";
    private Story mStory;
    private EditText mTitleField;

    public static Story_fragment newInstance(UUID mUuid){
        Bundle args = new Bundle();
        args.putSerializable(ARG_STORY_ID, mUuid);

        Story_fragment fragment = new Story_fragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        UUID storyID = (UUID)getArguments().getSerializable(ARG_STORY_ID);
        mStory = StoryLibrary.getInstance().getStory(storyID);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState){

        View v = inflater.inflate(R.layout.fragment_story, container, false);

        TextView title = (TextView) v.findViewById(R.id.story_title);
        title.setText(mStory.getName());

        TextView description = (TextView) v.findViewById(R.id.story_description);
        description.setText(mStory.getDescription());

        LinearLayout imageView = (LinearLayout) v.findViewById(R.id.story_image);
        imageView.setBackgroundResource(mStory.getImageResourceID());

        return v;
    }




}
