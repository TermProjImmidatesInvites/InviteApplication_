package com.example.samsung.inviteapplication.view;

import android.content.Intent;
import android.database.Cursor;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.database.sqlite.SQLiteDatabase;
import android.widget.Toast;

import com.example.samsung.inviteapplication.R;
import com.example.samsung.inviteapplication.models.invite;
import com.example.samsung.inviteapplication.view.customAdapter;
import com.example.samsung.inviteapplication.view.gridItems;

import java.util.ArrayList;

public class Grid_Activity extends AppCompatActivity {

    public static boolean byteImageFromUser = false;
    public static boolean ifDefaultInsertedAlready = false;

    public static String EVENT_NAME_TO_BE_PASSED_ON=" ";

    private RecyclerView recyclerView;
    private customAdapter mAdapter;
    private GridLayoutManager gridLayoutManager;
    ArrayList<gridItems> itemList;
    private static final int REQUEST_CODE = 100;
    public static final String MESSAGE_KEY="msg";
    private String eventName = "New Event";
    private int addAtThisIndex = 0;

    public static MyInvite myInvite;
    public String[] eventNames ;   String[] msgs; int[] imgsIDArray;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_grid_);

        initArrays();

        //insert_to_db();
        //myInvite.deleteRecord();

        insert_to_db();
        itemList = new ArrayList<>();
        getFromDb();
       // createList();
        buildRecyclerView();


        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar = (Toolbar)findViewById(R.id.toolbar);
        toolbar.setTitle("invite");
        setSupportActionBar(toolbar);


        mAdapter.setOnItemClickListener(new customAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(int position) {
                if(position==itemList.size()-1)
                {
                    Intent intent = new Intent(getApplicationContext(),gridAddEventPopUp.class);
                    intent.putExtra(MESSAGE_KEY,eventName);
                    startActivityForResult(intent,REQUEST_CODE);
                    addAtThisIndex = position;
                }
                else {
                    EVENT_NAME_TO_BE_PASSED_ON = "Birthday";// eventNames[position];
                    Intent intent = new Intent(Grid_Activity.this,invite_navigation.class);
                    startActivity(intent);
                }

            }
        });



    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data )
    {
        if(requestCode==REQUEST_CODE)
        {
            if(resultCode==RESULT_OK)
            {
                System.out.println(eventName);
                eventName = data.getStringExtra(MESSAGE_KEY);
                System.out.println("received: " + eventName);
                addItem(addAtThisIndex);
            }
        }
        super.onActivityResult(requestCode,resultCode,data);
    }

    public void addItem(int position)
    {
        itemList.add(position,new gridItems(gridAddEventPopUp.imgFromUser, eventName));
        byteImageFromUser = true;
        // mAdapter.notifyDataSetChanged();
        mAdapter.notifyItemInserted(position);
    }

    public void removeItem(int position)
    {
        itemList.remove(position);
        //mAdapter.notifyDataSetChanged();
        mAdapter.notifyItemRemoved(position);
    }

    public void createList() {
        /*
        itemList = new ArrayList<>();/*
        itemList.add(new gridItems(R.mipmap.as_bday, "Birthday"));
        itemList.add(new gridItems(R.mipmap.as_christmas, "Christmas"));
        itemList.add(new gridItems(R.mipmap.as_wed, "Wedding"));
        itemList.add(new gridItems(R.mipmap.as_newyear, "New Year"));
        itemList.add(new gridItems(R.mipmap.as_teaparty, "Tea Party"));
        itemList.add(new gridItems(R.mipmap.as_anniversary, "Anniversary"));
        itemList.add(new gridItems(R.mipmap.as_eid, "Eid"));
        itemList.add(new gridItems(R.mipmap.as_addnew, "Add Event"));*/

        for(int i = 0;i<8;i++) {
            itemList.add(new gridItems(imgsIDArray[i], eventNames[i]));
        }
    }
    public void initArrays(){
        /*imgsIDArray = new int[]{R.mipmap.as_bday, R.mipmap.as_christmas, R.mipmap.as_wed,
                R.mipmap.as_newyear, R.mipmap.as_teaparty, R.mipmap.as_anniversary, R.mipmap.as_eid, R.mipmap.as_addnew
            };*/

        imgsIDArray = new int[]{R.drawable.f_bday, R.drawable.f_christmas, R.drawable.f_wedding,
        R.drawable.f_newyear, R.drawable.f_tea_party,R.drawable.f_anniversary, R.drawable.f_eid,
                R.drawable.f_add_new
        };
        eventNames = new String[]{"Birthday", "Christmas", "Wedding", "New Year", "Tea Party", "Anniversary", "Eid", "Add Event"};
        msgs = new String[]{
                "Its Birthday Bash!! Cake and food so appetizing and sweet oh, we're having a party so you're invited for a treat! Games, party hats and balloons will make your day fun, so we want you here when the party's begun!",

                "Pack a bag and join us ! You are invited to eat, drink and be merry! Come and celebrate the holiday season with us ",

                "Together with our families we invite you to share in their joy at the celebration of marriage.",

                "We’re throwing a party and you’ll have a blast as we ring in the new year and say goodbye to the last! Join us New Year’s Eve as we ring in  2019 ",

                "I’d like to invite you over for tea, It’s going to be delicious – you’ll see! There will be delicious desserts and plenty to chat about, So come and have a drink from my tea pot’s spout!",

                "We'd like to share with you the joy and excitement of our wedding anniversary. To celebrate this special moment, we invite you to join us  and make it memorable for us!",

                "Join us in our Eid festivity and make the occasion joyous With your delightful presence Eid Mubarak!!!"

            };
    }

    public void buildRecyclerView()
    {
        recyclerView = (RecyclerView) findViewById(R.id.gif_recycler_view);
        gridLayoutManager = new GridLayoutManager(Grid_Activity.this,2);
        mAdapter = new customAdapter(itemList);
        recyclerView.setLayoutManager(gridLayoutManager);
        recyclerView.setAdapter(mAdapter);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.grid_menu,menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId())
        {
            case R.id.addEventMenu:
                Intent intent = new Intent(getApplicationContext(),gridAddEventPopUp.class);
                intent.putExtra(MESSAGE_KEY,eventName);
                startActivityForResult(intent,REQUEST_CODE);
                addAtThisIndex = itemList.size()-1;
                break;
            case R.id.delEventMenu:

                break;
        }
        return super.onOptionsItemSelected(item);
    }

    public void insert_to_db()
    {
        myInvite = new MyInvite(this, "InviteDB.sqlite",null,1);
        myInvite.queryData("CREATE TABLE IF NOT EXISTS Invite(Id INTEGER PRIMARY KEY AUTOINCREMENT, Name VARCHAR NOT NULL, Msg  VARCHAR NOT NULL, usn VARCHAR NOT NULL, Image BLOG, ImageInt INTEGER)");

        Cursor cursor = myInvite.getData("SELECT * FROM Invite");
        if(!(cursor.moveToNext()) || cursor.equals(null)) {
            int i = 0;
            while (i < 7) {
                try {
                    myInvite.insertData(
                            eventNames[i],
                            msgs[i],
                            "default",
                            imgsIDArray[i]
                    );
                    i++;
                    Toast.makeText(getApplicationContext(), "Data Inserted GRID_ACTIVITY!", Toast.LENGTH_LONG).show();
                    ifDefaultInsertedAlready = true;
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
    }


    public void getFromDb()
    {
        Cursor cursor = myInvite.getData("SELECT * FROM Invite");
        while (cursor.moveToNext()) {
            if (cursor.getString(3).equals("default"))
            {
              invite i = new invite(
                        cursor.getInt(0),//id
                        cursor.getString(1),//name
                        cursor.getString(2),//msg
                        cursor.getString(3),//usn
                        cursor.getInt(5)//int image
                        );
                itemList.add(new gridItems(i.getImg(), i.getName()));
            }
        }
        itemList.add(new gridItems(imgsIDArray[7],"Add Event"));
    }
}
