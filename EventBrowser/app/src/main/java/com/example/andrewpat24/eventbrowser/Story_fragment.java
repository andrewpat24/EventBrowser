package com.example.andrewpat24.eventbrowser;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.media.Image;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.ImageRequest;

import java.util.UUID;

/**
 * Created by aculanay on 11/26/16.
 */

public class Story_fragment extends Fragment {

    public static final String ARG_STORY_ID = "story_id";
    private Story mStory;
    private ImageView mImageView;
    private View mView;

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
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState){

        mView = inflater.inflate(R.layout.fragment_story, container, false);
        UUID storyID = (UUID)getArguments().getSerializable(ARG_STORY_ID);
        mStory = StoryLibrary.getInstance(mView.getContext()).getStory(storyID);

        TextView title = (TextView) mView.findViewById(R.id.story_title);
        title.setText(mStory.getName());

        TextView description = (TextView) mView.findViewById(R.id.story_description);
        description.setText(mStory.getDescription());

        mImageView= (ImageView) mView.findViewById(R.id.story_image);
        ImageRequest request = new ImageRequest(mStory.getImage(),
                new Response.Listener<Bitmap>() {
                    @Override
                    public void onResponse(Bitmap bitmap) {
                        mImageView.setImageBitmap(bitmap);
                    }
                }, 0, 0, null,
                new Response.ErrorListener() {
                    public void onErrorResponse(VolleyError error) {
                        //mImageView.setImageResource(R.drawable.image_load_error);
                    }
                });
        WebMessenger.getInstance(mView.getContext()).addToRequestQueue(request);
        //imageView.setBackgroundResource(mStory.getImageResourceID());

        return mView;
    }




}
