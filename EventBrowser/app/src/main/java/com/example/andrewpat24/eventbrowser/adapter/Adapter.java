package com.example.andrewpat24.eventbrowser.adapter;

import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.andrewpat24.eventbrowser.R;
import com.example.andrewpat24.eventbrowser.model.Story;
import com.example.andrewpat24.eventbrowser.holder.StoryHolder;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Ajinkya on 23/11/16.
 */

public class Adapter extends RecyclerView.Adapter<StoryHolder>{

    private List<Story> mStories;
    private Fragment story_list_fragment;

    public Adapter(Fragment story_list_fragment)
    {
        mStories = new ArrayList<>();
        this.story_list_fragment = story_list_fragment;
    }

    @Override
    public StoryHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(story_list_fragment.getActivity());
        View view = layoutInflater.inflate(R.layout.list_item_card, parent, false);
        return new StoryHolder(view);
    }

    @Override
    public void onBindViewHolder(StoryHolder holder, int position) {
        Story story = mStories.get(position);
        holder.setFragment(story_list_fragment);
        holder.bindStory(story);
    }
    
    @Override
    public int getItemCount() {
        return mStories.size();
    }

    public void updateDataSet(List<Story> stories) {
        this.mStories.clear();
        this.mStories.addAll(stories);
        notifyDataSetChanged();
    }
}