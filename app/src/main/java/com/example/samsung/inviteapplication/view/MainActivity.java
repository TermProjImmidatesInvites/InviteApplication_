package com.example.samsung.inviteapplication.view;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.widget.TextView;

import com.example.samsung.inviteapplication.R;
import android.support.design.widget.BottomNavigationView;

public class MainActivity extends AppCompatActivity {

    adapter adapter_SL;
    static  public ViewPager pager;
    MenuItem item;
    BottomNavigationView B_View;

    private TextView mTextMessage;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        pager = findViewById(R.id.pager);

        adapter_SL= new adapter(getSupportFragmentManager());

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
                        case R.id.navigation_login:
                            //frag = new login_Fragment();
                            pager.setCurrentItem(0);
                            break;
                        case R.id.navigation_signup:
                            // frag = new Registor_Fragment();
                            pager.setCurrentItem(1);
                            break;

                    }
                    return true;

                }
            };


}