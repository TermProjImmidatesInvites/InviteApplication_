package com.example.samsung.inviteapplication.view;


import android.Manifest;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.telephony.SmsManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.Toast;

import com.example.samsung.inviteapplication.R;

public class tab3Msg extends Fragment {

    String sms = "";
    EditText msgToBeSent;
    FloatingActionButton fab;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.tab3_msg, container, false);

        //get permissions
        if(ContextCompat.checkSelfPermission(getActivity().getApplicationContext(),
                Manifest.permission.SEND_SMS)!= PackageManager.PERMISSION_GRANTED) {

            if(ActivityCompat.shouldShowRequestPermissionRationale(getActivity(),
                    Manifest.permission.SEND_SMS)){
                ActivityCompat.requestPermissions(getActivity(),
                        new String[]{Manifest.permission.SEND_SMS},1);
            }else{
                ActivityCompat.requestPermissions(getActivity(),
                        new String[]{Manifest.permission.SEND_SMS},1);
            }
        }else{
            //do nothing
        }

        msgToBeSent = (EditText) rootView.findViewById(R.id.msg_to_be_sent);
        retieveData();

        fab = (FloatingActionButton) rootView.findViewById(R.id.tab3_fb);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                sms = msgToBeSent.getText().toString();
                for (int i = 0; i < tab2Contacts.contactsSelected.size(); i++) {
                    try {
                        SmsManager smsManager = SmsManager.getDefault();
                        smsManager.sendTextMessage(tab2Contacts.contactsSelected.get(i), null, sms, null, null);
                       // Toast.makeText(getActivity().getApplicationContext(), "SENT", Toast.LENGTH_SHORT).show();
                        Snackbar.make(view, "Sent " + i, Snackbar.LENGTH_LONG)
                                .setAction("Action", null).show();
                    } catch (Exception e) {
                       // Toast.makeText(getActivity().getApplicationContext(), "FAILED", Toast.LENGTH_SHORT).show();
                        Snackbar.make(view, "Failed", Snackbar.LENGTH_LONG)
                                .setAction("Action", null).show();
                    }
                }
            }
        });

        return rootView;
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        switch (requestCode){
            case 1:{
                if(grantResults.length>0 && grantResults[0] == PackageManager.PERMISSION_GRANTED)
                {
                    if(ContextCompat.checkSelfPermission(getActivity().getApplicationContext(),
                            Manifest.permission.SEND_SMS)== PackageManager.PERMISSION_GRANTED)
                        Toast.makeText(getActivity(), "PERMISSION GRANTED", Toast.LENGTH_SHORT).show();
                    else
                        Toast.makeText(getActivity(), " NO PERMISSION GRANTED", Toast.LENGTH_SHORT).show();
                }
            }
        }

    }

    public void retieveData()
    {
        Cursor cursor = Grid_Activity.myInvite.getData("SELECT * FROM Invite");
        while (cursor.moveToNext()) {
            if (cursor.getString(1).equals(Grid_Activity.EVENT_NAME_TO_BE_PASSED_ON))
            {
                sms = cursor.getString(2);//msg
                break;
            }
        }
        msgToBeSent.setText(sms);
    }

}
