package com.kenon.wikihero;

import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.MenuItemCompat;
import androidx.viewpager.widget.PagerAdapter;
import androidx.viewpager.widget.ViewPager;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;

import com.google.android.material.tabs.TabLayout;
import com.kenon.wikihero.adapters.FragmentAdapter;

import org.json.JSONArray;
import org.json.JSONException;

public class MainActivity extends AppCompatActivity{

    private SearchQueryListener searchQueryListener;
    private SearchView searchView;
    private ViewPager viewPager;
    private PagerAdapter adapter;

    public void setSearchQueryListener(SearchQueryListener searchQueryListener){
        this.searchQueryListener = searchQueryListener;
    }

    public interface SearchQueryListener{
        void onRecieved(String query);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        //Toolbar configurations
        Toolbar toolbar = findViewById(R.id.toolbar);
        toolbar.inflateMenu(R.menu.toolbar_menu);
        setSupportActionBar(toolbar);

        //Tablayout configurations
        TabLayout tabLayout = (TabLayout) findViewById(R.id.tabLayout);
        tabLayout.addTab(tabLayout.newTab().setText("Explore"));
        tabLayout.addTab(tabLayout.newTab().setText("Search"));
        tabLayout.addTab(tabLayout.newTab().setText("Recent"));

        viewPager = (ViewPager) findViewById(R.id.pager);
        adapter = new FragmentAdapter(getSupportFragmentManager(), tabLayout.getTabCount());
        viewPager.setAdapter(adapter);

        viewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));
        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                viewPager.setCurrentItem(tab.getPosition());
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }

        });

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.toolbar_menu, menu);
        MenuItem menuItem = menu.findItem(R.id.search_button);

        searchView = (SearchView) menuItem.getActionView();
        searchView.setQueryHint("Find hero name...");

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @RequiresApi(api = Build.VERSION_CODES.KITKAT)
            @Override
            public boolean onQueryTextSubmit(String query) {
                if(searchQueryListener != null){
                    if(query != ""){
                        searchQueryListener.onRecieved(query);
                        TabLayout tabLayout = (TabLayout) findViewById(R.id.tabLayout);
                        TabLayout.Tab tab = tabLayout.getTabAt(1);
                        tab.select();

                        addToSearchHistory(query);
                    }
                }
                return true;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                return false;
            }
        });

        return super.onCreateOptionsMenu(menu);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
    }

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    public void addToSearchHistory(String query){
        SharedPreferences pref = getApplicationContext().getSharedPreferences("wikiheroSearch", 0);
        SharedPreferences.Editor editor = pref.edit();

        String jsonHistory = pref.getString("wikiheroSearch", "[]");

        Log.d("JSON---->", jsonHistory);

        try {
            JSONArray jsonArray = new JSONArray(jsonHistory);
            if(jsonArray.length() >= 10){
                jsonArray.remove(0);
            }
            jsonArray.put(query);
            editor.putString("wikiheroSearch", jsonArray.toString());
            editor.commit();
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
}