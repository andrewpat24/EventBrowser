package com.example.andrewpat24.eventbrowser.fragment;


import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.Html;
import android.text.SpannableStringBuilder;
import android.text.method.LinkMovementMethod;
import android.text.style.ClickableSpan;
import android.text.style.URLSpan;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.ImageRequest;
import com.example.andrewpat24.eventbrowser.R;
import com.example.andrewpat24.eventbrowser.controller.Story;
import com.example.andrewpat24.eventbrowser.interfaceAPI.WebMessenger;
import com.example.andrewpat24.eventbrowser.activity.WebViewActivity;

import java.util.UUID;

/**
 * Created by aculanay on 11/26/16.
 */

public class StoryFragment extends Fragment {

    public static final String ARG_STORY_ID = "story_id";
    private Story mStory;
    private ImageView mImageView;
    private View mView;

    public static StoryFragment newInstance(UUID mUuid){
        Bundle args = new Bundle();
        args.putSerializable(ARG_STORY_ID, mUuid);

        StoryFragment fragment = new StoryFragment();
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
        mStory = Story.getStory(storyID);

        TextView title = (TextView) mView.findViewById(R.id.story_title);
        title.setText(mStory.getName());

        TextView description = (TextView) mView.findViewById(R.id.story_description);
        setTextViewHTML(description, mStory.getDescription());

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

    protected void makeLinkClickable(SpannableStringBuilder strBuilder, final URLSpan span){
        int start = strBuilder.getSpanStart(span);
        int end = strBuilder.getSpanEnd(span);
        int flags = strBuilder.getSpanFlags(span);
        ClickableSpan clickable = new ClickableSpan() {
            @Override
            public void onClick(View view) {
                Bundle bundle = new Bundle();
                bundle.putString("url", span.getURL());
                Intent intent = new Intent(getContext(), WebViewActivity.class);
                intent.putExtras(bundle);
                startActivity(intent);
            }
        };

        strBuilder.setSpan(clickable, start, end, flags);
        strBuilder.removeSpan(span);
    }

    protected void setTextViewHTML(TextView t, String html){
        CharSequence sequence = Html.fromHtml(html);
        SpannableStringBuilder strBuilder = new SpannableStringBuilder(sequence);
        URLSpan[] urls = strBuilder.getSpans(0, sequence.length(), URLSpan.class);
        for(URLSpan span : urls){
            makeLinkClickable(strBuilder,span);
            t.setText(strBuilder);
            t.setMovementMethod(LinkMovementMethod.getInstance());
        }
    }




}
