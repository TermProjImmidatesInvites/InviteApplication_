package com.example.samsung.inviteapplication.view;


import android.Manifest;
import android.app.Activity;
import android.content.ContentResolver;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.provider.ContactsContract;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import com.example.samsung.inviteapplication.R;

import java.util.ArrayList;

public class tab2Contacts extends Fragment {

    boolean moveToMsgTab = true;

    private RecyclerView recyclerView;
    private contactAdapter mAdapter;
    private RecyclerView.LayoutManager layoutManager;
    private GridLayoutManager gridLayoutManager;
    ArrayList<item> itemList;

    static public ArrayList<String> contactsSelected = new ArrayList<>();
    private View rootView;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.tab2_contacts, container, false);

        if (ContextCompat.checkSelfPermission(getActivity().getApplicationContext(),
                Manifest.permission.READ_CONTACTS) != PackageManager.PERMISSION_GRANTED) {

            if (ActivityCompat.shouldShowRequestPermissionRationale(getActivity(),
                    Manifest.permission.READ_CONTACTS)) {
                ActivityCompat.requestPermissions((Activity) getActivity().getApplicationContext(),
                        new String[]{Manifest.permission.READ_CONTACTS}, 1);
            } else {
                ActivityCompat.requestPermissions(getActivity(),
                        new String[]{Manifest.permission.READ_CONTACTS}, 1);
            }


        } else {
            //do nothing
        }
        loadContacts();
        buildRecyclerView();

        // imageView = (ImageView)findViewById(R.id.checked);


        mAdapter.setOnItemClickListener(new contactAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(int position) {
                //itemList.get(position).setLine("New Line");
                if(itemList.get(position).getImgResource2() == R.drawable.ic_not_check) {
                    contactsSelected.add(itemList.get(position).getLine1());
                    itemList.get(position).setImgResource2(R.drawable.ic_check_circle);
                    mAdapter.notifyItemChanged(position);
                }else {
                    itemList.get(position).setImgResource2(R.drawable.ic_not_check);
                    mAdapter.notifyItemChanged(position);
                }
            }
        });

        if(contactsSelected.size()<1) moveToMsgTab = false;

        FloatingActionButton fab = (FloatingActionButton) rootView.findViewById(R.id.tab2_fb);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(moveToMsgTab){
                    tab3Msg fragment = new tab3Msg();
                    getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.container, fragment).commit();
                }
                else {
                    Snackbar.make(view, "Select at least 1 contact to proceed", Snackbar.LENGTH_LONG)
                            .setAction("Action", null).show();
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
                    if(ContextCompat.checkSelfPermission((Activity) getActivity().getApplicationContext(),
                            Manifest.permission.READ_CONTACTS)== PackageManager.PERMISSION_GRANTED)
                        Toast.makeText((Activity) getActivity().getApplicationContext(), "PERMISSION GRANTED", Toast.LENGTH_SHORT).show();
                    else
                        Toast.makeText((Activity) getActivity().getApplicationContext(), " NO PERMISSION GRANTED", Toast.LENGTH_SHORT).show();

                }
            }
        }
    }

    private  void loadContacts()
    {
        StringBuilder builder = new StringBuilder();
        itemList = new ArrayList<>();
        contactsSelected.add("03219455852");

        ContentResolver contentResolver = getActivity().getContentResolver();
        Cursor cursor = null;
        try {
            cursor = contentResolver.query(ContactsContract.Contacts.CONTENT_URI, null, null, null, null);
        }catch (Exception e){
            Log.e("Error in cursor", e.getMessage());
        }
        if(!(cursor.equals(null))) {
            if (cursor.getCount() > 0) {
                while (cursor.moveToNext()) {
                    String id = cursor.getString(cursor.getColumnIndex(ContactsContract.Contacts._ID));
                    String name = cursor.getString(cursor.getColumnIndex(ContactsContract.Contacts.DISPLAY_NAME));
                    int hasPhoneNumber = Integer.parseInt(cursor.getString(cursor.getColumnIndex(ContactsContract.Contacts.HAS_PHONE_NUMBER)));

                    if (hasPhoneNumber > 0) {
                        Cursor cursor1 = contentResolver.query(ContactsContract.CommonDataKinds.Phone.CONTENT_URI,
                                null,
                                ContactsContract.CommonDataKinds.Phone.CONTACT_ID + " = ?",
                                new String[]{id}, null);

                        while (cursor1.moveToNext()) {
                            String phoneNum = cursor1.getString(cursor1.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER));
                            builder.append("Contact: ").append(name).append(", Phone Number: ").append(phoneNum).append("\n\n");
                            itemList.add(new item(R.drawable.ic_contact, R.drawable.ic_not_check, name, phoneNum));
                        }
                        cursor1.close();
                    }
                }
            }
            cursor.close();
            //contacts_list.setText(builder.toString());
        }
    }

    public void buildRecyclerView()
    {
        recyclerView = (RecyclerView) rootView.findViewById(R.id.recycler_view);
        //layoutManager = new LinearLayoutManager(this);
        gridLayoutManager = new GridLayoutManager(getActivity(),1);
        mAdapter = new contactAdapter(itemList);
        recyclerView.setLayoutManager(gridLayoutManager);
        recyclerView.setAdapter(mAdapter);
    }

}
