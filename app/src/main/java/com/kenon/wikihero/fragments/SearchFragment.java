package com.kenon.wikihero.fragments;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Adapter;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.ListAdapter;
import android.widget.ListView;

import com.kenon.wikihero.internet.InternetConnection;
import com.kenon.wikihero.MainActivity;
import com.kenon.wikihero.R;
import com.kenon.wikihero.adapters.SearchResultsAdapter;
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
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link SearchFragment#newInstance} factory method to
 * create an instance of this fragment.
 */

public class SearchFragment extends Fragment implements MainActivity.SearchQueryListener {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    ArrayList <Superhero> superheroes = new ArrayList <>();

    Adapter adapter;
    ListView listView;
    LinearLayout loading;
    View view;

    private Search searchTask;

    public SearchFragment() {
        // Required empty public constructor
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        ((MainActivity) getActivity()).setSearchQueryListener(this);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        setHasOptionsMenu(true);
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment SearchFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static SearchFragment newInstance(String param1, String param2) {
        SearchFragment fragment = new SearchFragment();
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
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_search, container, false);

        listView = view.findViewById(R.id.listView);
        loading = view.findViewById(R.id.loading);

        ArrayAdapter<String> adapter = null;

        List<String> history = new ArrayList <>();
        try {
            history = getSearchHistory();
        } catch (JSONException e) {
            e.printStackTrace();
        }

        if(new InternetConnection(getContext()).isConnected()){
            if(history.size() > 0){
                adapter = new ArrayAdapter <String>(getActivity(), android.R.layout.simple_list_item_1, android.R.id.text1, history);
                listView.setAdapter(adapter);

                List <String> finalHistory = history;
                listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                        new Search(finalHistory.get(position)).execute();
                    }
                });
            }else{
                view.findViewById(R.id.noHistory).setVisibility(View.VISIBLE);
            }
        }else{
            view.findViewById(R.id.noConnection).setVisibility(View.VISIBLE);
        }

        return view;
    }

    @Override
    public void onRecieved(String query) {
        if(new InternetConnection(getContext()).isConnected()){
            loading.setVisibility(View.VISIBLE);
            view.findViewById(R.id.noHistory).setVisibility(View.INVISIBLE);

            searchTask = new Search(query);
            superheroes.clear();
            if(searchTask.getStatus() == AsyncTask.Status.RUNNING){
                searchTask.cancel(true);
            }
            searchTask.execute();
        }else{
            view.findViewById(R.id.noConnection).setVisibility(View.VISIBLE);
        }
    }

    private final class Search extends AsyncTask <Void, Void, Void> {
        String query;

        public Search(String query) {
            this.query = query;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            loading.setVisibility(View.VISIBLE);
        }

        @Override
        protected Void doInBackground(Void... params) {

            if (isCancelled()) {
                return null;
            }

            try{
                Document doc = null;
                doc = Jsoup.connect("https://www.superheroapi.com/api.php/868006950673602/search/" + query)
                        .method(Connection.Method.GET)
                        .ignoreContentType(true)
                        .header("Content-type", "application/json")
                        .get();

                JSONObject jsonObject = new JSONObject(doc.select("body").text());
                JSONArray jsonArray = jsonObject.getJSONArray("results");

                for(int i = 0; i < jsonArray.length() ; i++){
                    JSONObject superhero = jsonArray.getJSONObject(i);

                    String id = superhero.getString("id");
                    String name = superhero.getString("name");
                    String json = superhero.toString();

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

            } catch (IOException | JSONException e) {
                e.printStackTrace();
            }

            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            adapter = new SearchResultsAdapter(getActivity(), superheroes);
            listView.setAdapter((ListAdapter) adapter);

            listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    openSuperheroDetails(superheroes.get(position));
                }
            });

            loading.setVisibility(View.GONE);
        }

    }

    private void openSuperheroDetails(Superhero superhero){
        Intent intent = new Intent(getActivity(), com.kenon.wikihero.Superhero.class);
        intent.putExtra("superhero", superhero); //Optional parameters
        startActivity(intent);
    }

    private List<String> getSearchHistory() throws JSONException {
        SharedPreferences pref = getActivity().getSharedPreferences("wikiheroSearch", 0);
        SharedPreferences.Editor editor = pref.edit();

        JSONArray history = new JSONArray(pref.getString("wikiheroSearch", "[]"));

        ArrayList<String> listdata = new ArrayList<String>();

        for (int i=0 ; i < history.length() ; i++){
            listdata.add(history.getString(i));
        }
        return listdata;
    }
}