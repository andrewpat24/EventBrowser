package com.example.andrewpat24.eventbrowser.activity;

import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.LinearLayout;
import android.widget.SearchView;
import android.widget.Toast;

import com.example.andrewpat24.eventbrowser.R;
import com.example.andrewpat24.eventbrowser.app.App;
import com.example.andrewpat24.eventbrowser.fragment.StoryListFragment;

import java.util.UUID;

public class StoryListActivity extends AppCompatActivity {

    private StoryListFragment storyFragmentObj;
    private boolean mDoubleBackToExitPressedOnce = false;
    private StoryListActivity mStoryListActivity;
    private UUID mUUID;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
        mStoryListActivity = this;
		super.onCreate(savedInstanceState);
        mUUID = UUID.randomUUID();
        App.setStoryListActivityUUID(mUUID);
		setContentView(R.layout.activity_storylist);
        Intent intent = getIntent();

        View view = findViewById(android.R.id.content);
        view.setOnTouchListener(new View.OnTouchListener(){
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                hideSoftKeyboard();
                return true;
            }
        });

        FragmentManager fragmentManager = getSupportFragmentManager();
        storyFragmentObj = (StoryListFragment) fragmentManager.findFragmentById(R.id.activity_main);

        SearchManager searchManager = (SearchManager) getSystemService(Context.SEARCH_SERVICE);
        final SearchView searchView = (SearchView) findViewById(R.id.search_view);
        searchView.setSearchableInfo(searchManager.getSearchableInfo(getComponentName()));
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener(){
            @Override
            public boolean onQueryTextSubmit(String search) {
                if(search.equals("")|| search.length()>1) {
                    updateRecycler(search);
                    return false;
                }
                else {
                    Toast.makeText(StoryListActivity.this, "Must provide more than one character", Toast.LENGTH_SHORT).show();
                    return true;
                }
            }
            @Override
            public boolean onQueryTextChange(String search) {
                if (storyFragmentObj == null)
                    return true;
                if(search.length() > 1 || search.equals(""))
                    updateRecycler(search);
                return true;
            }
        });
        if (Intent.ACTION_SEARCH.equals(intent.getAction())) {
            searchView.setQuery(intent.getStringExtra((SearchManager.QUERY)),false);

            storyFragmentObj =  StoryListFragment.newInstance(intent.getStringExtra(SearchManager.QUERY));
        }

        if(storyFragmentObj == null) {
            storyFragmentObj = StoryListFragment.newInstance("");
        }

        fragmentManager.beginTransaction().replace(R.id.card_list_container_fragment, storyFragmentObj).commit();

	}

    @Override
    protected void onResume() {
        super.onResume();
        hideSoftKeyboard();
    }

    protected void updateRecycler(String search) {
        storyFragmentObj.updateUI(search);
    }

    public void hideSoftKeyboard() {
        InputMethodManager imm = (InputMethodManager) StoryListActivity.this.getSystemService(Context.
                INPUT_METHOD_SERVICE);
        if (StoryListActivity.this.getCurrentFocus() != null) {
            imm.hideSoftInputFromWindow(StoryListActivity.this.getCurrentFocus().getWindowToken(), 0);
        }

        LinearLayout linearLayout = (LinearLayout) findViewById(R.id.activity_main);
        linearLayout.requestFocus();
    }

    @Override
    public void onBackPressed() {
        if (mDoubleBackToExitPressedOnce) {
            super.onBackPressed();
            finish();
            return;
        }

        this.mDoubleBackToExitPressedOnce = true;
        Toast.makeText(this, "Please click BACK again to exit", Toast.LENGTH_SHORT).show();

        new Handler().postDelayed(new Runnable() {

            @Override
            public void run() {
                mDoubleBackToExitPressedOnce=false;
            }
        }, 2000);
    }

    @Override
    protected void onStop() {
        super.onStop();
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                if(!App.getStoryListActivityUUID().equals(mUUID)) {
                    mStoryListActivity.finish();
                }
            }
        },2000);
    }
}
