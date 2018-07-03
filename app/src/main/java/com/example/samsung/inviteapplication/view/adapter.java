package com.example.samsung.inviteapplication.view;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

public class adapter extends FragmentPagerAdapter {

    int fragments= 2;

    public adapter(FragmentManager fm) {

        super(fm);
    }

    @Override
    public Fragment getItem(int position) {
        Fragment current_frag= null;
        if(position==0)
            current_frag= new login_fragment();

        else if(position==1)
            current_frag= new signup_fragment();

        return current_frag;
    }


    @Override
    public CharSequence getPageTitle(int position) {
        if(position==0)
            return "Login";
        else if(position==1)
            return "SignUp";
        return super.getPageTitle(position);
    }

    @Override
    public int getCount() {
        return fragments;
    }
}


