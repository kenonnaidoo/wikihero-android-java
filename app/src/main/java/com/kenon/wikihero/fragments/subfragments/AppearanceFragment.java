package com.kenon.wikihero.fragments.subfragments;

import android.os.Build;
import android.os.Bundle;

import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Adapter;
import android.widget.ListAdapter;
import android.widget.ListView;

import com.kenon.wikihero.R;
import com.kenon.wikihero.adapters.SubFragmentListAdapter;
import com.kenon.wikihero.models.Superhero;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link AppearanceFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class AppearanceFragment extends Fragment {

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

    public AppearanceFragment(Superhero superhero) {
        this.superhero = superhero;
    }

    public AppearanceFragment() {}

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment AppearanceFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static AppearanceFragment newInstance(String param1, String param2) {
        AppearanceFragment fragment = new AppearanceFragment();
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
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_appearance, container, false);

        HashMap <String, String> data = new HashMap<>();

        data.put("Gender", superhero.getAppearence().getGender());
        data.put("Race", superhero.getAppearence().getRace());
        data.put("Height", superhero.getAppearence().getHeight());
        data.put("Weight", superhero.getAppearence().getWeight());
        data.put("Eye Color", superhero.getAppearence().getEyeColor());
        data.put("Hair Color", superhero.getAppearence().getHairColor());

        com.kenon.wikihero.Superhero.viewPager.updateChildCount(data.size());

        List <String> keys = Arrays.asList(new String[]{"Gender", "Race", "Height", "Weight", "Eye Color", "Hair Color"});

        listView = view.findViewById(R.id.listView);
        adapter = new SubFragmentListAdapter(getActivity(), data, keys);
        listView.setAdapter((ListAdapter) adapter);

        return view;
    }
}