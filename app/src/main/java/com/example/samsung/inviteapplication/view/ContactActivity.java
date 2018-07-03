package com.example.samsung.inviteapplication.view;

import android.Manifest;
import android.content.ContentResolver;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.provider.ContactsContract;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.samsung.inviteapplication.R;
import com.example.samsung.inviteapplication.view.contactAdapter;
import com.example.samsung.inviteapplication.view.item;

import java.util.ArrayList;

public class ContactActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private contactAdapter mAdapter;
    private RecyclerView.LayoutManager layoutManager;
    private GridLayoutManager gridLayoutManager;
    ArrayList<item> itemList;

    static public ArrayList<String> contacts = new ArrayList<>();
    private Button send_btn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contact);

        if(ContextCompat.checkSelfPermission(ContactActivity.this,
                Manifest.permission.READ_CONTACTS)!= PackageManager.PERMISSION_GRANTED) {

            if(ActivityCompat.shouldShowRequestPermissionRationale(ContactActivity.this,
                    Manifest.permission.READ_CONTACTS)){
                ActivityCompat.requestPermissions(ContactActivity.this,
                        new String[]{Manifest.permission.READ_CONTACTS},1);
            }else{
                ActivityCompat.requestPermissions(ContactActivity.this,
                        new String[]{Manifest.permission.READ_CONTACTS},1);
            }


        }else{
            //do nothing
        }
        loadContacts();
        buildRecyclerView();

        // imageView = (ImageView)findViewById(R.id.checked);
        send_btn = (Button) findViewById(R.id.to_sms_activity);

        send_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ContactActivity.this, smsActivity.class);
                startActivity(intent);
            }
        });

        mAdapter.setOnItemClickListener(new contactAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(int position) {
                //itemList.get(position).setLine("New Line");
                contacts.add(itemList.get(position).getLine1());
                itemList.get(position).setImgResource2(R.drawable.ic_check_circle);
                mAdapter.notifyItemChanged(position);
            }
/*
            @Override
            public void onChecked(int position) {
//                imageView.setImageResource(R.drawable.ic_check_circle);
                contacts.add(itemList.get(position).getLine1());
                itemList.get(position).setImgResource(R.drawable.ic_check_circle);
                mAdapter.notifyItemChanged(position);
            }*/
        });
    }


    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        switch (requestCode){
            case 1:{
                if(grantResults.length>0 && grantResults[0] == PackageManager.PERMISSION_GRANTED)
                {
                    if(ContextCompat.checkSelfPermission(ContactActivity.this,
                            Manifest.permission.READ_CONTACTS)== PackageManager.PERMISSION_GRANTED)
                        Toast.makeText(this, "PERMISSION GRANTED", Toast.LENGTH_SHORT).show();
                    else
                        Toast.makeText(this, " NO PERMISSION GRANTED", Toast.LENGTH_SHORT).show();

                }
            }
        }
    }

    private  void loadContacts()
    {
        StringBuilder builder = new StringBuilder();
        itemList = new ArrayList<>();
        contacts.add("03219455852");

        ContentResolver contentResolver = getContentResolver();
        Cursor cursor = null;
        try {
            cursor = contentResolver.query(ContactsContract.Contacts.CONTENT_URI, null, null, null, null);
        }catch (Exception e){
            Log.e("Error in cursor", e.getMessage());
        }

        if(cursor.getCount()>0)
        {
            while(cursor.moveToNext())
            {
                String id = cursor.getString(cursor.getColumnIndex(ContactsContract.Contacts._ID));
                String name = cursor.getString(cursor.getColumnIndex(ContactsContract.Contacts.DISPLAY_NAME));
                int hasPhoneNumber = Integer.parseInt(cursor.getString(cursor.getColumnIndex(ContactsContract.Contacts.HAS_PHONE_NUMBER)));

                if(hasPhoneNumber>0)
                {
                    Cursor cursor1 = contentResolver.query(ContactsContract.CommonDataKinds.Phone.CONTENT_URI,
                            null,
                            ContactsContract.CommonDataKinds.Phone.CONTACT_ID + " = ?",
                            new String[]{id}, null  );

                    while(cursor1.moveToNext())
                    {
                        String phoneNum = cursor1.getString(cursor1.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER));
                        builder.append("Contact: ").append(name).append(", Phone Number: ").append(phoneNum).append("\n\n");
                        itemList.add(new item(R.drawable.ic_contact,  R.drawable.ic_not_check, name, phoneNum));
                    }
                    cursor1.close();
                }
            }
        }
        cursor.close();
        //contacts_list.setText(builder.toString());
    }

    public void buildRecyclerView()
    {
        recyclerView = (RecyclerView) findViewById(R.id.recycler_view);
        //layoutManager = new LinearLayoutManager(this);
        gridLayoutManager = new GridLayoutManager(ContactActivity.this,1);
        mAdapter = new contactAdapter(itemList);
        recyclerView.setLayoutManager(gridLayoutManager);
        recyclerView.setAdapter(mAdapter);
    }
}
