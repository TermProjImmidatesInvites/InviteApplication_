package com.example.samsung.inviteapplication.view;

import android.content.ContentValues;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.samsung.inviteapplication.R;
import static com.example.samsung.inviteapplication.view.MainActivity.pager;

public class signup_fragment extends Fragment {

    private Button signup_button;
    public signup_fragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState){
        // Inflate the layout for this fragment
        View view =  inflater.inflate(R.layout.fragment_signup_fragment, container, false);
        System.out.println("Register Fragment");

        signup_button = (Button)view.findViewById(R.id.signup_button);
        //register.setOnClickListener(this);
        signup_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                EditText Username=getView().findViewById(R.id.signup_username);
                EditText email=getView().findViewById(R.id.signup_mail);
                EditText pass =getView().findViewById(R.id.signup_password);

                String name=Username.getText().toString();
                String uemail=email.getText().toString();
                String password=pass.getText().toString();

                MyDb myDB=new MyDb(getActivity());
                SQLiteDatabase db=myDB.getWritableDatabase();

                ContentValues values=new ContentValues();
                values.put("username",name);
                values.put("email",uemail);
                values.put("pass",password);

                db.insert("User",null,values);
                Toast.makeText(getActivity(),"Data Insert SuccessFully!!" , Toast.LENGTH_LONG).show();


                login_fragment login_now  = new login_fragment();
                pager.setCurrentItem(0);

                Username.getText().clear();
                email.getText().clear();
                pass.getText().clear();

            }
        });


        return view;
    }
}



