package com.kenon.wikihero.fragments;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;

import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.kenon.wikihero.R;
import com.kenon.wikihero.adapters.CardViewAdapter;
import com.kenon.wikihero.models.Appearence;
import com.kenon.wikihero.models.Biography;
import com.kenon.wikihero.models.Connections;
import com.kenon.wikihero.models.PowerStats;
import com.kenon.wikihero.models.Superhero;
import com.kenon.wikihero.models.Work;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link HistoryFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class HistoryFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private View view;

    private RecyclerView recyclerView;
    private CardViewAdapter recyclerViewAdapter;
    private RecyclerView.LayoutManager recyclerViewLayoutManager;

    ArrayList <Superhero> superheroes = new ArrayList <>();
    String jsonRecents;

    public HistoryFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment HistoryFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static HistoryFragment newInstance(String param1, String param2) {
        HistoryFragment fragment = new HistoryFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if (isVisibleToUser) {
            getFragmentManager().beginTransaction().detach(this).attach(this).commit();
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_history, container, false);

        recyclerView = view.findViewById(R.id.recyclerView);
        recyclerViewLayoutManager = new LinearLayoutManager(getActivity());

        SharedPreferences pref = getActivity().getSharedPreferences("wikiheroRecents", 0);
        jsonRecents = pref.getString("wikiheroRecents", "[]");

        try {
            JSONArray jsonArray = new JSONArray(jsonRecents);
            if(jsonArray.length() > 0){
                new HistoryFragment.Search().execute();
            }else{
                view.findViewById(R.id.noHistory).setVisibility(View.VISIBLE);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return view;
    }

    private final class Search extends AsyncTask <Void, Void, String> {

        @RequiresApi(api = Build.VERSION_CODES.KITKAT)
        @Override
        protected String doInBackground(Void... params) {
            try{

                JSONArray jsonArray = new JSONArray(jsonRecents);

                superheroes.clear();

                for(int i = jsonArray.length() - 1; i >= 0; i--){
                    JSONObject superhero = new JSONObject(jsonArray.get(i).toString());

                    String id = superhero.getString("id");
                    String name = superhero.getString("name");
                    String json = superhero.toString();

                    Log.d("TAG", json);

                    PowerStats powerStats = new PowerStats(
                            superhero.getJSONObject("powerstats").getString("intelligence"),
                            superhero.getJSONObject("powerstats").getString("strength"),
                            superhero.getJSONObject("powerstats").getString("speed"),
                            superhero.getJSONObject("powerstats").getString("durability"),
                            superhero.getJSONObject("powerstats").getString("power"),
                            superhero.getJSONObject("powerstats").getString("combat")
                    );

                    List <String> aliases = new ArrayList <>();
                    if(superhero.getJSONObject("biography").getJSONArray("aliases").length() > 0){
                        for (int j = 0; j < superhero.getJSONObject("biography").getJSONArray("aliases").length(); j++) {
                            aliases.add(superhero.getJSONObject("biography").getJSONArray("aliases").get(j).toString());
                        }
                    }else{
                        aliases.add("No Aliases.");
                    }

                    Biography biography = new Biography(
                            superhero.getJSONObject("biography").getString("full-name"),
                            superhero.getJSONObject("biography").getString("alter-egos"),
                            aliases,
                            superhero.getJSONObject("biography").getString("place-of-birth"),
                            superhero.getJSONObject("biography").getString("first-appearance"),
                            superhero.getJSONObject("biography").getString("publisher"),
                            superhero.getJSONObject("biography").getString("alignment")
                    );

                    String height = superhero.getJSONObject("appearance").getJSONArray("height").get(0).toString() + " (" + superhero.getJSONObject("appearance").getJSONArray("height").get(1).toString() + ") ";
                    String weight = superhero.getJSONObject("appearance").getJSONArray("weight").get(0).toString() + " (" + superhero.getJSONObject("appearance").getJSONArray("weight").get(1).toString() + ") ";
                    Appearence appearence = new Appearence(
                            superhero.getJSONObject("appearance").getString("gender"),
                            superhero.getJSONObject("appearance").getString("race"),
                            height,
                            weight,
                            superhero.getJSONObject("appearance").getString("eye-color"),
                            superhero.getJSONObject("appearance").getString("hair-color")
                    );

                    Work work = new Work(
                            superhero.getJSONObject("work").getString("occupation"),
                            superhero.getJSONObject("work").getString("base")
                    );

                    Connections connections = new Connections(
                            superhero.getJSONObject("connections").getString("group-affiliation"),
                            superhero.getJSONObject("connections").getString("relatives")
                    );

                    String image = superhero.getJSONObject("image").getString("url");

                    superheroes.add(
                            new Superhero(id, name, powerStats, biography, appearence, work, connections, image, json)
                    );
                }

            } catch (JSONException e) {
                e.printStackTrace();
            }

            return "Executed";
        }

        @Override
        protected void onPostExecute(String result) {
            recyclerViewAdapter = new CardViewAdapter(superheroes);
            recyclerView.setLayoutManager(recyclerViewLayoutManager);
            recyclerView.setAdapter(recyclerViewAdapter);

            recyclerViewAdapter.setOnItemClickListener(new CardViewAdapter.OnItemClickListener() {
                @Override
                public void onItemClicked(int index) {
                    openSuperheroDetails(superheroes.get(index));
                }
            });

            view.findViewById(R.id.noHistory).setVisibility(View.GONE);
        }

        private void openSuperheroDetails(Superhero superhero){
            Intent intent = new Intent(getActivity(), com.kenon.wikihero.Superhero.class);
            intent.putExtra("superhero", superhero); //Optional parameters
            startActivity(intent);
        }
    }
}