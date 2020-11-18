package com.kenon.wikihero;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.viewpager.widget.PagerAdapter;
import androidx.viewpager.widget.ViewPager;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.material.tabs.TabLayout;
import com.kenon.wikihero.adapters.FragmentAdapter;
import com.kenon.wikihero.adapters.SubFragmentAdapter;
import com.kenon.wikihero.customWidgets.CustomViewPager;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class Superhero extends AppCompatActivity {
    com.kenon.wikihero.models.Superhero superhero;

    public static CustomViewPager viewPager;

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_superhero);

        Intent i = getIntent();
        superhero = (com.kenon.wikihero.models.Superhero) i.getSerializableExtra("superhero");

        SharedPreferences pref = getApplicationContext().getSharedPreferences("wikiheroRecents", 0);
        SharedPreferences.Editor editor = pref.edit();

        String jsonRecents = pref.getString("wikiheroRecents", "[]");

        Log.d("JSON---->", jsonRecents);

        try {
            JSONArray jsonArray = new JSONArray(jsonRecents);
            if(jsonArray.length() >= 10){
                jsonArray.remove(0);
            }
            jsonArray.put(superhero.getJson());
            editor.putString("wikiheroRecents", jsonArray.toString());
            editor.commit();
        } catch (JSONException e) {
            e.printStackTrace();
        }

        //Tablayout configurations
        TabLayout tabLayout = (TabLayout) findViewById(R.id.tabLayout);
        tabLayout.addTab(tabLayout.newTab().setText("Biography"));
        tabLayout.addTab(tabLayout.newTab().setText("Powerstats"));
        tabLayout.addTab(tabLayout.newTab().setText("Appearance"));
        tabLayout.addTab(tabLayout.newTab().setText("Work"));
        tabLayout.addTab(tabLayout.newTab().setText("Connections"));

        TextView title = findViewById(R.id.textView);
        TextView subtitle = findViewById(R.id.textView2);
        ImageView imageView = findViewById(R.id.imageView);
        ImageButton backButton = findViewById(R.id.backButton);

        title.setText(superhero.getName());
        subtitle.setText(superhero.getBiography().getPublisher());
        Picasso.get()
                .load(superhero.getImageUrl())
                .error(R.drawable.dummy1)
                .placeholder(R.drawable.dummy1)
                .into(imageView);
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        viewPager = (CustomViewPager) findViewById(R.id.pager);
        final PagerAdapter adapter = new SubFragmentAdapter(getSupportFragmentManager(), superhero, tabLayout.getTabCount());
        viewPager.setAdapter(adapter);
        viewPager.setOffscreenPageLimit(0);

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
                viewPager.setCurrentItem(tab.getPosition());
            }

        });

    }
}