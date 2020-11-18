package com.kenon.wikihero.adapters;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import com.kenon.wikihero.fragments.ExploreFragment;
import com.kenon.wikihero.fragments.HistoryFragment;
import com.kenon.wikihero.fragments.SearchFragment;

public class FragmentAdapter extends FragmentPagerAdapter {

    int tabCount;

    public FragmentAdapter(FragmentManager fm, int numberOfTabs) {
        super(fm);
        this.tabCount = numberOfTabs;
    }

    @Override
    public Fragment getItem(int position) {

        switch (position) {
            case 0:
                return new ExploreFragment();
            case 1:
                return new SearchFragment();
            case 2:
                return new HistoryFragment();
            case 3:
                return new HistoryFragment();
            case 4:
                return new HistoryFragment();
            default:
                return null;
        }
    }

    @Override
    public int getCount() {
        return tabCount;
    }
}

