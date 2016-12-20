package com.example.andrewpat24.eventbrowser.holder;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Typeface;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.ImageRequest;
import com.example.andrewpat24.eventbrowser.R;
import com.example.andrewpat24.eventbrowser.model.Story;
import com.example.andrewpat24.eventbrowser.activity.StoryActivity;
import com.example.andrewpat24.eventbrowser.interfaceAPI.WebMessenger;

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
        mStoryDescription.setTypeface(null, Typeface.NORMAL);
        mImageView = (ImageView) itemView.findViewById(R.id.list_story_image);

        itemView.setOnClickListener(this);
    }

    public void bindStory(Story story){
        mStory = story;

        mStoryName.setText(mStory.getName());
        String description = mStory.getDescription();
        description = description.replaceAll("<\\/?[bi]>", "");
        mStoryDescription.setText(Html.fromHtml(description).toString());

//      mImageView.setBackgroundResource(mStory.getImageResourceID());
        ImageRequest request = new ImageRequest(mStory.getImage(),
                new Response.Listener<Bitmap>() {
                    @Override
                    public void onResponse(Bitmap bitmap) {
                        mImageView.setImageBitmap(bitmap);
                    }
                }, 0, 0, null,
                new Response.ErrorListener() {
                    public void onErrorResponse(VolleyError error) {
                        mImageView.setImageResource(R.mipmap.placeholder);
                    }
                });
        WebMessenger.getInstance(mFragment.getContext()).addToRequestQueue(request);

    }

    @Override
    public void onClick(View view) {
        Context context = view.getContext();
        context.startActivity(StoryActivity.newIntent(mFragment.getActivity(), mStory.getUUID()));
    }

}
