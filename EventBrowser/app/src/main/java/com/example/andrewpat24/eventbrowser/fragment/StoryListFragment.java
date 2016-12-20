package com.example.andrewpat24.eventbrowser.fragment;

import android.content.Context;
import android.graphics.Rect;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.TextView;
import android.widget.Toast;

import com.example.andrewpat24.eventbrowser.R;
import com.example.andrewpat24.eventbrowser.model.Story;
import com.example.andrewpat24.eventbrowser.controller.StoryLibrary;
import com.example.andrewpat24.eventbrowser.adapter.Adapter;

import java.util.List;

public class StoryListFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    private static final String ARG_PARAM1 = "param1";
    private RecyclerView mRecyclerView;
    private View mView;
    private Adapter mAdapter;
    private SwipeRefreshLayout mSwipeContainer;
    private TextView mTextView;
    private String mSearchQuery;

    public StoryListFragment() {
        // Required empty public constructor
    }

    public static StoryListFragment newInstance(String param1) {
        StoryListFragment fragment = new StoryListFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        mView = inflater.inflate(R.layout.story_list_fragment_layout, container, false);
        mRecyclerView = (RecyclerView) mView.findViewById(R.id.card_recycler_view);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        mRecyclerView.addItemDecoration(new VerticalSpaceItemDecorator(20));
        mRecyclerView.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                InputMethodManager imm = (InputMethodManager) getActivity().getSystemService(Context.
                        INPUT_METHOD_SERVICE);
                if (getActivity().getCurrentFocus() != null) {
                    imm.hideSoftInputFromWindow(getActivity().getCurrentFocus().getWindowToken(), 0);
                }
                return false;
            }
        });

        mView.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                InputMethodManager imm = (InputMethodManager) getActivity().getSystemService(Context.
                        INPUT_METHOD_SERVICE);
                if (getActivity().getCurrentFocus() != null) {
                    imm.hideSoftInputFromWindow(getActivity().getCurrentFocus().getWindowToken(), 0);
                }
                return false;
            }
        });

        mSwipeContainer = (SwipeRefreshLayout) mView.findViewById(R.id.swipeContainer);
        mSwipeContainer.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                fetchLatest();
            }
        });

        mTextView = (TextView) mView.findViewById(R.id.tvEmptyRecyclerView);
        mTextView.setVisibility(View.GONE);

        mSearchQuery = "";
        updateUI(getArguments().getString(ARG_PARAM1));

        return mView;
    }

    public void updateUI(String query){
        mSearchQuery = query;
        if(mAdapter == null)
            mAdapter = new Adapter(this);

        StoryLibrary storyLibrary = StoryLibrary.getStoryLibrary(new StoryLibrary.OnResponseListener() {
                @Override
                public void onSuccess(List<Story> stories) {
                    if (stories.size() > 0) {
                        mTextView.setVisibility(View.GONE);
                        mRecyclerView.setVisibility(View.VISIBLE);
                        mRecyclerView.invalidate();
                        mAdapter.updateDataSet(stories);
                        mRecyclerView.setAdapter(mAdapter);

                    }
                    else{
                        mTextView.setVisibility(View.VISIBLE);
                        mRecyclerView.setVisibility(View.GONE);

                    }
                    mSwipeContainer.setRefreshing(false);
                }

                @Override
                public void onFailure(String errorMessage) {
                    mTextView.setVisibility(View.VISIBLE);
                    mRecyclerView.setVisibility(View.GONE);
                    Toast.makeText(StoryListFragment.this.getActivity(), "Failed to retrieve data", Toast.LENGTH_SHORT).show();
                    mSwipeContainer.setRefreshing(false);
                }
            });
            storyLibrary.cancelAllRequests();
            storyLibrary.sendRequest(query);
    }

    protected void fetchLatest(){
        updateUI(mSearchQuery);
    }

}

class VerticalSpaceItemDecorator extends RecyclerView.ItemDecoration {
    private final int spacer;
//    private final int horizontalSpacer;

    public VerticalSpaceItemDecorator(int spacer) {
        this.spacer = spacer;
//        this.horizontalSpacer
    }

    @Override
    public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
        super.getItemOffsets(outRect, view, parent, state);
        outRect.top = spacer;
        outRect.right = spacer;
        outRect.left = spacer;
    }
}
