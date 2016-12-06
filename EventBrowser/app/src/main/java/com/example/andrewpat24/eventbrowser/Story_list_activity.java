package com.example.andrewpat24.eventbrowser;

import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.SearchView;
import android.widget.Toast;

public class Story_list_activity extends AppCompatActivity {

    private Story_list_fragment storyFragmentObj;
    private String SEARCHKEY = "Query";
    private boolean mDoubleBackToExitPressedOnce = false;


	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
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
        storyFragmentObj = (Story_list_fragment) fragmentManager.findFragmentById(R.id.activity_main);

        SearchManager searchManager = (SearchManager) getSystemService(Context.SEARCH_SERVICE);
        final SearchView searchView = (SearchView) findViewById(R.id.search_view);
        searchView.setSearchableInfo(searchManager.getSearchableInfo(getComponentName()));
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener(){
            @Override
            public boolean onQueryTextSubmit(String search) {
                updateRecycler(search);
                return true;
            }
            @Override
            public boolean onQueryTextChange(String search) {
                if (storyFragmentObj == null)
                    return true;

                updateRecycler(search);
                return true;
            }
        });

        Button btnSearch = (Button) findViewById(R.id.btn_search);
        btnSearch.setOnClickListener( new View.OnClickListener(){

            @Override
            public void onClick(View view) {

                SearchView searchView = (SearchView) findViewById(R.id.search_view);
                hideSoftKeyboard();
                searchView.setQuery(searchView.getQuery(),true);
            }
        });

        if (Intent.ACTION_SEARCH.equals(intent.getAction())) {
            searchView.setQuery(intent.getStringExtra((SearchManager.QUERY)),false);
            storyFragmentObj =  Story_list_fragment.newInstance(intent.getStringExtra((SearchManager.QUERY)));
        }

        if(storyFragmentObj == null) {
            storyFragmentObj = Story_list_fragment.newInstance("");
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
        InputMethodManager imm = (InputMethodManager) Story_list_activity.this.getSystemService(Context.
                INPUT_METHOD_SERVICE);
        if (Story_list_activity.this.getCurrentFocus() != null) {
            imm.hideSoftInputFromWindow(Story_list_activity.this.getCurrentFocus().getWindowToken(), 0);
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
}
