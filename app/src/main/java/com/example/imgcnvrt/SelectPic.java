package com.example.imgcnvrt;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.DocumentsContract;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;

/**
 * Created by Administrator on 8/22/2014.
 */
public class SelectPic extends Activity {

    public static  int SELECT_PICTURE = 0;
    public static int CAMERA_REQUEST = 0;

    private static final int RESULT_LOADIMAGE = 1;
    private static final int GALLERY_INTENT_CALLED=302;
    private TextView txta;
    private String selectedImagePath;
    private Cursor cursor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);

        //Gallery button click
        ((Button) findViewById(R.id.selbutton))
                .setOnClickListener(new View.OnClickListener() {
                    public void onClick(View arg0) {
                        SELECT_PICTURE = 1;
                        CAMERA_REQUEST = 0;
                        Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
                        intent.setType("image/*");
                        intent.setAction(Intent.ACTION_GET_CONTENT);
                        startActivityForResult(Intent.createChooser(intent, "Select Picture"), SELECT_PICTURE);
                   }
                });

        //Camera button click
        ((Button) findViewById(R.id.photoButton))
                .setOnClickListener(new View.OnClickListener() {
                    public void onClick(View arg0) {
                        CAMERA_REQUEST = 1;
                        SELECT_PICTURE = 0;
                        Intent cameraIntent = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
                        startActivityForResult(cameraIntent, CAMERA_REQUEST);
                    }
                });
    }
//
//        Button button =(Button)findViewById(R.id.selbutton);
//        button.setOnClickListener(new View.OnClickListener() {
//
//            @Override
//            public void onClick(View v) {
//                // TODO Auto-generated method stub
//                Intent i = new Intent(getApplicationContext(),MainActivity.class);
//                startActivity(i);
//            }
//        });


    protected void onActivityResult(int requestCode, int resultCode, Intent data)
    {

        if (resultCode == RESULT_OK) {
            if (requestCode == SELECT_PICTURE) {
                Uri selectedImageUri = data.getData();
                selectedImagePath = getPath(selectedImageUri);
                System.out.println("Image Path : " + selectedImagePath);
//                try {
//                    Bitmap bitmap = MediaStore.Images.Media.getBitmap(this.getContentResolver(), selectedImageUri);
//
//                    //bitmap to byte array
//                    ByteArrayOutputStream stream = new ByteArrayOutputStream();
//                    bitmap.compress(Bitmap.CompressFormat.PNG, 100, stream);
//                    byte[] byteArray = stream.toByteArray();


//                    intent.putExtra("imagePath",bitmap);
                    //pass byte array into intent
                    Intent  intent= new Intent(SelectPic.this,MainActivity.class);
                    intent.putExtra("imagePath",selectedImageUri);

//            Log.d("Picture Path", picturepath);
                    startActivity(intent);

//                } catch (IOException e) {
//                    e.printStackTrace();
//                }
            } else if (requestCode == CAMERA_REQUEST && resultCode == RESULT_OK)
            {

            System.out.println("===================Inside Camera1=========================");
//                Uri selectedImageUri = data.getData();
//                System.out.println("==================="+selectedImageUri+"=========================");
//                Intent  intent= new Intent(SelectPic.this,MainActivity.class);
//                intent.putExtra("imagePath",selectedImageUri);
                Bitmap photo = (Bitmap) data.getExtras().get("data");
//                ByteArrayOutputStream stream = new ByteArrayOutputStream();
//                photo.compress(Bitmap.CompressFormat.PNG, 90, stream);
//                byte[] image = stream.toByteArray();
                Intent  intent= new Intent(SelectPic.this,MainActivity.class);
                intent.putExtra("bitimage",photo);
                startActivity(intent);
//                imageView.setImageBitmap(photo);
            }
        }
//        BitmapDrawable abmp = (BitmapDrawable) img.getDrawable();
//        bmp = abmp.getBitmap();



        }


    public String getPath(Uri uri) {
        String[] projection = { MediaStore.Images.Media.DATA };
        Cursor cursor = managedQuery(uri, projection, null, null, null);
        int column_index = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
        cursor.moveToFirst();
        return cursor.getString(column_index);
    }

    }






