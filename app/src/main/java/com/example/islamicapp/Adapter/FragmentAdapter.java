package com.example.islamicapp.Adapter;


import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import com.example.islamicapp.Fragment.AboutFragment;
import com.example.islamicapp.Fragment.ProgramScheduleFragment;
import com.example.islamicapp.Fragment.VideoPlayerFragment;

public class FragmentAdapter extends FragmentPagerAdapter {
    public FragmentAdapter(FragmentManager fm) {
        super(fm);
    }

    public Fragment getItem(int pos) {
        switch (pos) {
            case 0:
                return VideoPlayerFragment.newInstance("", "");
            case 1:
                return ProgramScheduleFragment.newInstance("", "");
            case 2:
                return AboutFragment.newInstance("", "");
            default:
                return VideoPlayerFragment.newInstance("", "");
        }
    }

    public int getCount() {
        return 3;
    }
}
