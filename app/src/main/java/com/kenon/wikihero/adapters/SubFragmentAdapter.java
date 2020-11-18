package com.kenon.wikihero.adapters;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import com.kenon.wikihero.fragments.subfragments.AppearanceFragment;
import com.kenon.wikihero.fragments.subfragments.BiographyFragment;
import com.kenon.wikihero.fragments.subfragments.ConnectionsFragment;
import com.kenon.wikihero.fragments.subfragments.PowerstatsFragment;
import com.kenon.wikihero.fragments.subfragments.WorkFragment;
import com.kenon.wikihero.models.Superhero;

public class SubFragmentAdapter extends FragmentPagerAdapter {

    int tabCount;
    com.kenon.wikihero.models.Superhero superhero;

    public SubFragmentAdapter(FragmentManager fm, Superhero superhero, int numberOfTabs) {
        super(fm);
        this.superhero = superhero;
        this.tabCount = numberOfTabs;
    }

    @Override
    public Fragment getItem(int position) {

        switch (position) {
            case 0:
                return new BiographyFragment(superhero);
            case 1:
                return new PowerstatsFragment(superhero);
            case 2:
                return new AppearanceFragment(superhero);
            case 3:
                return new WorkFragment(superhero);
            case 4:
                return new ConnectionsFragment(superhero);
            default:
                return null;
        }
    }

    @Override
    public int getCount() {
        return tabCount;
    }
}
