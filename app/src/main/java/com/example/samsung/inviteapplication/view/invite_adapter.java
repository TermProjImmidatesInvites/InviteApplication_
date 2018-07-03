package com.example.samsung.inviteapplication.view;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

public class invite_adapter extends FragmentPagerAdapter {

    int fragments= 2;

    public invite_adapter(FragmentManager fm) {

        super(fm);
    }

    @Override
    public Fragment getItem(int position) {
        Fragment current_frag= null;
        if(position==0)
            current_frag= new invitation_frag();

        else if(position==1)
            current_frag= new next_frag();

        return current_frag;
    }


    @Override
    public CharSequence getPageTitle(int position) {
        if(position==0)
            return "Invite_Navigation";
        else if(position==1)
            return "Next_Activity";
        return super.getPageTitle(position);
    }

    @Override
    public int getCount() {
        return fragments;
    }
}


