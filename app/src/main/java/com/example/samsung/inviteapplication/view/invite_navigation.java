package com.example.samsung.inviteapplication.view;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.samsung.inviteapplication.R;

public class invite_navigation extends AppCompatActivity {

    invite_adapter adapter_SL;
    static public ViewPager pager;
    MenuItem item;
    BottomNavigationView B_View;


    private TextView mTextMessage;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_invite_navigation);

        pager = findViewById(R.id.pager);

        adapter_SL= new invite_adapter(getSupportFragmentManager());
        pager.setAdapter(adapter_SL);

        B_View = findViewById(R.id.bottom_view);
        B_View.setOnNavigationItemSelectedListener(nav);

        pager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {

                if (item != null) {
                    item.setChecked(false);
                }
                else
                {
                    B_View.getMenu().getItem(0).setChecked(false);
                }

                B_View.getMenu().getItem(position).setChecked(true);
                item =  B_View.getMenu().getItem(position);
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });


    }
    // set navigation
    private BottomNavigationView.OnNavigationItemSelectedListener nav =
            new BottomNavigationView.OnNavigationItemSelectedListener() {
                @Override
                public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                    Fragment frag = null;

                    switch (item.getItemId()) {
                        case R.id.invite:
                           // frag = new invitation_frag();
                            pager.setCurrentItem(0);
                            break;
                        case R.id.next:
                            //frag = new next_frag();
                            pager.setCurrentItem(1);
                            break;

                    }
                    return true;

                }
            };


}