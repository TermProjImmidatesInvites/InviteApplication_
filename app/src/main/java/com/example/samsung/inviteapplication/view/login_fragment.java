package com.example.samsung.inviteapplication.view;
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.samsung.inviteapplication.R;
import com.facebook.FacebookSdk;
import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.auth.api.signin.GoogleSignInResult;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.mukeshsolanki.sociallogin.facebook.FacebookHelper;
import com.mukeshsolanki.sociallogin.facebook.FacebookListener;
import com.twitter.sdk.android.core.Callback;
import com.twitter.sdk.android.core.Result;
import com.twitter.sdk.android.core.Twitter;
import com.twitter.sdk.android.core.TwitterAuthToken;
import com.twitter.sdk.android.core.TwitterCore;
import com.twitter.sdk.android.core.TwitterException;
import com.twitter.sdk.android.core.TwitterSession;
import com.twitter.sdk.android.core.identity.TwitterLoginButton;


public class login_fragment extends Fragment   implements  GoogleApiClient.OnConnectionFailedListener, FacebookListener {

    private Button login_button;
    private ImageView fb_button;
    private ImageView gmail_button;
    private ImageView twitter_button;
    TwitterLoginButton tt;
    private GoogleApiClient googleApiClient;
    public static final int SIGN_IN_CODE=777;
    FacebookHelper mfb;
    public static String USERNAME="";

    public void login_fragment()
    {

    }
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        mfb.onActivityResult(requestCode,resultCode,data);
        if(requestCode==SIGN_IN_CODE){
            GoogleSignInResult result= Auth.GoogleSignInApi.getSignInResultFromIntent(data);
            handleSignInResult(result);
        }
        tt.onActivityResult(requestCode, resultCode, data);
    }
    @SuppressLint("ResourceAsColor")
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        System.out.println("login Fragment");
        Twitter.initialize(getActivity());
        View view =  inflater.inflate(R.layout.fragment_login_fragment, container, false);
        //...........................login with twitter

        //Initializing twitter instance

        tt = (TwitterLoginButton) view.findViewById(R.id.abc);
        tt.setCallback(new Callback<TwitterSession>() {
            @Override
            public void success(Result<TwitterSession> result) {
                // Do something with result, which provides a TwitterSession for making API calls
                TwitterSession session = TwitterCore.getInstance().getSessionManager().getActiveSession();
                TwitterAuthToken authToken = session.getAuthToken();
                String token = authToken.token;
                String secret = authToken.secret;
                //Calling login method and passing twitter session
                login(session);
            }

            @Override
            public void failure(TwitterException exception) {
                // Do something on failure
                Toast.makeText(getActivity(), "Authentication failed!", Toast.LENGTH_LONG).show();

            }
        });











        //........................................Login with facebook wala kaam..................................................

        FacebookSdk.setApplicationId(getResources().getString(R.string.facebook_app_id));
        FacebookSdk.sdkInitialize(getActivity());
        mfb=new FacebookHelper(login_fragment.this );

        fb_button=(ImageView) view.findViewById(R.id.OpenFb);
        fb_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mfb.performSignIn(getActivity());

            }
        });

        //...........................................................................

        login_button = (Button) view.findViewById(R.id.login_button);
        login_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                MyDb myDB = new MyDb(getActivity());
                SQLiteDatabase db = myDB.getReadableDatabase();
                EditText login_name = getView().findViewById(R.id.login_username);
                EditText login_password = getView().findViewById(R.id.login_password);
                //Create Coloums....
                String [] Columns = {"username", "pass" };
                String[] login_values = {login_name.getText().toString(), login_password.getText().toString()};

                Cursor cursor = db.query("User", Columns, "username=? AND pass=?", login_values, null, null, null);
                //Intent intent = new Intent(getActivity() , Grid_Activity.class);
                // startActivity(intent);
                if (cursor != null) {
                    if (cursor.moveToFirst()) {
                        USERNAME = login_name.getText().toString();
                        Intent myIntent = new Intent(getActivity().getApplicationContext(),Grid_Activity.class);
                        startActivity(myIntent);
                    }
                    else
                    {
                        Toast.makeText(getActivity(),"Username or Password incorrect!!",Toast.LENGTH_LONG).show();
                    }
                }
            }
        });

        //log in with Google-------------------------------------------------

        GoogleSignInOptions gso=new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestEmail()
                .build();



        googleApiClient=new GoogleApiClient.Builder(getActivity())
                .enableAutoManage(getActivity(),this)
                .addApi(Auth.GOOGLE_SIGN_IN_API,gso)
                .build();

        gmail_button=(ImageView) view.findViewById(R.id.OpenGmail);
        gmail_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=Auth.GoogleSignInApi.getSignInIntent(googleApiClient);
                startActivityForResult(intent,SIGN_IN_CODE);
            }
        });

        /////////////////Login with facebook
        fb_button=(ImageView) view.findViewById(R.id.OpenFb);
        fb_button.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View view) {
                mfb.performSignIn(login_fragment.this);
            }
        });

        return  view;

    }

    /**
     * @param session
     * This method will get username using session and start a new activity where username will be displayed
     */
    public void login(TwitterSession session)
    {
        String username = session.getUserName();
        Intent intent = new Intent(getActivity(), Grid_Activity.class);
        intent.putExtra("username", username);
        startActivity(intent);
    }




    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {

    }


    private void handleSignInResult(GoogleSignInResult result) {
        if(result.isSuccess())
        {
            goMainScreen();
        }
        else
        {
            Toast.makeText(getActivity(), "Unsucesfull attempt",Toast.LENGTH_SHORT).show();
        }
    }

    private void goMainScreen() {
        Toast.makeText(getActivity(),"Successfully",Toast.LENGTH_SHORT).show();
        Intent i=new Intent(getActivity(), Grid_Activity.class);
        i.addFlags(i.FLAG_ACTIVITY_CLEAR_TOP | i.FLAG_ACTIVITY_CLEAR_TASK | i.FLAG_ACTIVITY_NEW_TASK);
        startActivity(i);
    }
//..................................End of Login with google...........////////////


    public void onFbSignInFail(String errorMessage) {
        Toast.makeText(getActivity(),"Not LoggedIn with Facebook!"+errorMessage,Toast.LENGTH_SHORT).show();

    }


    public void onFbSignInSuccess(String authToken, String userId) {
        Toast.makeText(getActivity(),"Successfully Signed In! "+userId,Toast.LENGTH_SHORT).show();
        Intent myIntent1 = new Intent(getActivity().getApplicationContext(), Grid_Activity.class);
        startActivity(myIntent1);
    }


    public void onFBSignOut() {
        Toast.makeText(getActivity(),"Signed Out!",Toast.LENGTH_SHORT).show();
    }





}
