package com.example.andrewpat24.eventbrowser;

import android.graphics.Rect;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

/**
 * Created by Ajinkya on 23/11/16.
 */

public class Adapter extends RecyclerView.Adapter<StoryHolder>{

    private List<Story> mStories;
    private Fragment story_list_fragment;

    public Adapter(List<Story> stories, Fragment story_list_fragment)
    {
        mStories = stories;
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
        //Continue from here
        holder.setFragment(story_list_fragment);
        holder.bindStory(story);
    }


    @Override
    public int getItemCount() {
        return mStories.size();
    }
}

/*
private class CrimeAdapter extends RecyclerView.Adapter<CrimeHolder> {
        private List<Crime> mCrimes;
        public CrimeAdapter(List<Crime> crimes) {
            mCrimes = crimes;
        }
        @Override
        public CrimeHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            LayoutInflater layoutInflater = LayoutInflater.from(getActivity());
            View view = layoutInflater.inflate(R.layout.list_item_crime, parent, false);
            return new CrimeHolder(view);
        }
        @Override
        public void onBindViewHolder(CrimeHolder holder, int position) {
            Crime crime = mCrimes.get(position);
            holder.bindCrime(crime);
        }
        @Override
        public int getItemCount() {
            return mCrimes.size();
        }
    }
 */