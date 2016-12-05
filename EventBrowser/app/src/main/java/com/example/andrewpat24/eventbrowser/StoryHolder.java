package com.example.andrewpat24.eventbrowser;

import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.media.Image;
import android.net.Uri;
import android.provider.Settings;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import org.w3c.dom.Text;

/**
 * Created by Ajinkya on 23/11/16.
 */

public class StoryHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

    private Story mStory;


    private TextView mStoryName;
    private TextView mStoryDescription;
    private ImageView mImageView;
    private Fragment mFragment;

    public  void setFragment(Fragment fragment){
        mFragment = fragment;
    }



    public StoryHolder(View itemView) {
        super(itemView);

        mStoryName = (TextView) itemView.findViewById(R.id.list_story_name);
        mStoryDescription = (TextView) itemView.findViewById(R.id.list_story_description);
        mImageView = (ImageView) itemView.findViewById(R.id.list_story_image);

        itemView.setOnClickListener(this);
    }

    public void bindStory(Story story){
        mStory = story;

        mStoryName.setText(mStory.getName());
        mStoryDescription.setText(mStory.getDescription());
        mImageView.setBackgroundResource(mStory.getImageResourceID());

    }

    @Override
    public void onClick(View view) {
        Context context = view.getContext();
        context.startActivity(StoryActivity.newIntent(mFragment.getActivity(), mStory.getUUID()));
    }

}
