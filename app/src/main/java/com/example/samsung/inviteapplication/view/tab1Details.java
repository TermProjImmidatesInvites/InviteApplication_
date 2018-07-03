package com.example.samsung.inviteapplication.view;


import android.app.DatePickerDialog;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;

import com.example.samsung.inviteapplication.R;

import java.util.Calendar;

public class tab1Details extends Fragment {

    static boolean moveToContactsTab = false;

    private boolean venueSelected=  false, dateSelected = false;
    private static final String TAG = "MainActivity";
    private TextView mDisplayDate;
    private EditText venueChosen;
    private DatePickerDialog.OnDateSetListener mDateSetListener;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.tab1_details, container, false);


        venueChosen = (EditText) rootView.findViewById(R.id.venue_of_event);
        mDisplayDate = (TextView) rootView.findViewById(R.id.date_of_event);

        mDisplayDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Calendar cal = Calendar.getInstance();
                int year = cal.get(Calendar.YEAR);
                int month = cal.get(Calendar.MONTH);
                int day = cal.get(Calendar.DAY_OF_MONTH);

                DatePickerDialog dialog = new DatePickerDialog(
                        getActivity(),
                        android.R.style.Theme_Holo_Light_Dialog_MinWidth,
                        mDateSetListener,
                        year,month,day);
                dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                dialog.show();
            }
        });

        mDateSetListener = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int year, int month, int day) {
                month = month + 1;
                Log.d(TAG, "onDateSet: mm/dd/yyy: " + month + "/" + day + "/" + year);

                String date = month + "/" + day + "/" + year;
                mDisplayDate.setText(date);
            }
        };

        if( !(venueChosen.getText().toString().equals(null)) && !(mDisplayDate.getText().toString().equals(null)) ){
            moveToContactsTab = true;
        }


        FloatingActionButton fab = (FloatingActionButton) rootView.findViewById(R.id.tab1_fb);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(moveToContactsTab){
                    tab2Contacts fragment = new tab2Contacts();
                    getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.container, fragment).commit();
                }
                else {
                    Snackbar.make(view, "Select DATE and VENUE to proceed ", Snackbar.LENGTH_LONG)
                            .setAction("Action", null).show();
                }
            }
        });

        return rootView;
    }
}
