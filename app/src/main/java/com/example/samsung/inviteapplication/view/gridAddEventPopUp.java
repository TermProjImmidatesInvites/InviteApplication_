package com.example.samsung.inviteapplication.view;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.DisplayMetrics;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import com.example.samsung.inviteapplication.R;

import java.io.ByteArrayOutputStream;

public class gridAddEventPopUp extends AppCompatActivity {

    public static final String MESSAGE_KEY="msg";
    ImageView iv;
    Integer REQUEST_CAMERA = 1, SELECT_FILE = 0;
    public static byte[] imgFromUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_grid_add_event_pop_up);

        DisplayMetrics dm = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(dm);

        int width = dm.widthPixels;
        int height = dm.heightPixels;

        getWindow().setLayout((int)(width*0.8), (int)(height*0.5));

        iv = (ImageView)findViewById(R.id.ivImage);
        //final Button addImage = findViewById(R.id.addImg);
        //addImage
        iv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SelectImage();
            }

        });

         final EditText addNewEvent = (EditText)findViewById(R.id.newEvent);
        addNewEvent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                addNewEvent.setText("");
            }
        });

        //addEvent_button
        final Button addEvent;
        addEvent = (Button) findViewById(R.id.addEvent_button);
        addEvent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                    Bitmap bitmap = ((BitmapDrawable)iv.getDrawable()).getBitmap();
                    ByteArrayOutputStream stream = new ByteArrayOutputStream();
                    bitmap.compress(Bitmap.CompressFormat.PNG,100,stream);
                    imgFromUser =stream.toByteArray();

                EditText new_Event = (EditText)findViewById(R.id.newEvent);
                String message = new_Event.getText().toString();
                Intent intent = new Intent();
                intent.putExtra(MESSAGE_KEY,message);
                setResult(RESULT_OK,intent);
                System.out.println(message);
                finish();
                //Intent intent = new Intent(gridAddEventPopUp.this, make_invitation.class);
                //startActivity(intent);
            }
        });
    }

    private void SelectImage()
    {
        final CharSequence[] items = {"Camera", "Gallery", "Cancel"};

        AlertDialog.Builder builder = new AlertDialog.Builder(gridAddEventPopUp.this);
        builder.setTitle("Add Image");
        builder.setItems(items, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int i) {
                if(items[i].equals("Camera")){

                    Intent cameraintent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                    if (cameraintent.resolveActivity(getPackageManager()) != null) {
                        startActivityForResult(cameraintent, REQUEST_CAMERA);
                    }

                }else if(items[i].equals("Gallery")){

                    Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                    intent.setType("image/*");
                    startActivityForResult(intent.createChooser(intent,"Select File"), SELECT_FILE);

                }else if(items[i].equals("Cancel")) {

                    dialog.dismiss();
                }
            }
        });
        builder.show();
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data )
    {
        super.onActivityResult(requestCode,resultCode,data);
        if(resultCode== Activity.RESULT_OK) {

            if (requestCode == REQUEST_CAMERA) {

                Bundle bundle = data.getExtras();
                final Bitmap bmp = (Bitmap)bundle.get("data");
                iv.setImageBitmap(bmp);

            } else if (requestCode == SELECT_FILE) {
                Uri selectedImageUri = data.getData();
                iv.setImageURI(selectedImageUri);
            }
        }
    }

}