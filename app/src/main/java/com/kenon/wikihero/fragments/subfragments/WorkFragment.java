package com.kenon.wikihero.fragments.subfragments;

import android.os.Bundle;

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
 * Use the {@link WorkFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class WorkFragment extends Fragment {

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

    public WorkFragment(Superhero superhero) {
        this.superhero = superhero;
    }

    public WorkFragment() {}

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment WorkFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static WorkFragment newInstance(String param1, String param2) {
        WorkFragment fragment = new WorkFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if (isVisibleToUser) {
            getFragmentManager().beginTransaction().detach(this).attach(this).commit();
        }
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
        View view = inflater.inflate(R.layout.fragment_work, container, false);

        HashMap <String, String> data = new HashMap<>();

        data.put("Occupation", superhero.getWork().getOccupation());
        data.put("Base", superhero.getWork().getBase());

        com.kenon.wikihero.Superhero.viewPager.updateChildCount(data.size());

        List <String> keys = Arrays.asList(new String[]{"Occupation", "Base"});

        listView = view.findViewById(R.id.listView);
        adapter = new SubFragmentListAdapter(getActivity(), data, keys);
        listView.setAdapter((ListAdapter) adapter);

        return view;
    }
}