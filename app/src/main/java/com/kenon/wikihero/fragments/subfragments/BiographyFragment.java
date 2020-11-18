package com.kenon.wikihero.fragments.subfragments;

import android.os.Build;
import android.os.Bundle;

import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Adapter;
import android.widget.ListAdapter;
import android.widget.ListView;

import com.kenon.wikihero.R;
import com.kenon.wikihero.adapters.SubFragmentListAdapter;
import com.kenon.wikihero.models.Superhero;

import org.json.JSONArray;
import org.json.JSONException;

import java.util.Arrays;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link BiographyFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class BiographyFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    ListView listView;
    Adapter adapter;
    com.kenon.wikihero.models.Superhero superhero;

    public BiographyFragment(Superhero superhero) {
        this.superhero = superhero;
    }

    public BiographyFragment() {}

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment BiographyFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static BiographyFragment newInstance(String param1, String param2) {
        BiographyFragment fragment = new BiographyFragment();
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

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_biography, container, false);

        HashMap<String, String> data = new HashMap<>();

        data.put("Name", superhero.getName());
        data.put("Alter Egos", superhero.getBiography().getAlterEgos());
        data.put("Aliases", String.join(", ", superhero.getBiography().getAliases()));
        data.put("Place of birth", superhero.getBiography().getPlaceOfBirth());
        data.put("First Appearance", superhero.getBiography().getFirstAppearence());
        data.put("Publisher", superhero.getBiography().getPublisher());
        data.put("Alignment", superhero.getBiography().getAlignment());

        com.kenon.wikihero.Superhero.viewPager.updateChildCount(data.size());

        List <String> keys = Arrays.asList(new String[]{"Name", "Alter Egos", "Aliases", "Place of birth", "First Appearance", "Publisher", "Alignment"});

        listView = view.findViewById(R.id.listView);
        adapter = new SubFragmentListAdapter(getActivity(), data, keys);
        listView.setAdapter((ListAdapter) adapter);

        return view;
    }
}