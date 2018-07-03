package com.example.samsung.inviteapplication.view;

import android.Manifest;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.telephony.SmsManager;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.samsung.inviteapplication.R;
import com.example.samsung.inviteapplication.models.invite;

public class smsActivity extends AppCompatActivity {

    Button send_btn;
    EditText msg, phone;
    String sms;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sms);

        if(ContextCompat.checkSelfPermission(smsActivity.this,
                Manifest.permission.SEND_SMS)!= PackageManager.PERMISSION_GRANTED) {

            if(ActivityCompat.shouldShowRequestPermissionRationale(smsActivity.this,
                    Manifest.permission.SEND_SMS)){
                ActivityCompat.requestPermissions(smsActivity.this,
                        new String[]{Manifest.permission.SEND_SMS},1);
            }else{
                ActivityCompat.requestPermissions(smsActivity.this,
                        new String[]{Manifest.permission.SEND_SMS},1);
            }
        }else{
            //do nothing
        }

        send_btn =(Button) findViewById(R.id.send_the_sms_when_clicked);
        msg = (EditText) findViewById(R.id.text_to_be_sent);
        phone =(EditText) findViewById(R.id.phoneNum);

        retieveData();


        send_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //String num = phone.getText().toString();
                // String arr[]= phone.getText().toString().split(" ");
                //String sms = msg.getText().toString();
/*
                sms = "Sending message from Immediate Invite app.";
                if(msg.getText().toString()!="")*/

                sms = msg.getText().toString();
                for(int i = 0 ; i < ContactActivity.contacts.size(); i++) {
                    try {
                        SmsManager smsManager = SmsManager.getDefault();
                        smsManager.sendTextMessage(ContactActivity.contacts.get(i), null, sms, null, null);
                        Toast.makeText(smsActivity.this, "SENT", Toast.LENGTH_SHORT).show();
                    } catch (Exception e) {
                        Toast.makeText(smsActivity.this, "FAILED", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        switch (requestCode){
            case 1:{
                if(grantResults.length>0 && grantResults[0] == PackageManager.PERMISSION_GRANTED)
                {
                    if(ContextCompat.checkSelfPermission(smsActivity.this,
                            Manifest.permission.SEND_SMS)== PackageManager.PERMISSION_GRANTED)
                        Toast.makeText(this, "PERMISSION GRANTED", Toast.LENGTH_SHORT).show();
                    else
                        Toast.makeText(this, " NO PERMISSION GRANTED", Toast.LENGTH_SHORT).show();

                }
            }
        }

    }

    public void retieveData()
    {
        Toast.makeText(this, " In retreive data function", Toast.LENGTH_SHORT).show();
        Cursor cursor = Grid_Activity.myInvite.getData("SELECT * FROM Invite");
        while (cursor.moveToNext()) {
            if (cursor.getString(1).equals(Grid_Activity.EVENT_NAME_TO_BE_PASSED_ON))
            {
               sms = cursor.getString(2);//msg
                Toast.makeText(this, sms, Toast.LENGTH_LONG).show();
                break;
            }
        }
        msg.setText(sms);
    }

}
