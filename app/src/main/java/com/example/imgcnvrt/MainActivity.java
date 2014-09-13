package com.example.imgcnvrt;


import android.app.Activity;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.view.View.OnClickListener;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.jabistudio.androidjhlabs.filter.BlockFilter;
import com.jabistudio.androidjhlabs.filter.CircleFilter;
import com.jabistudio.androidjhlabs.filter.ColorHalftoneFilter;
import com.jabistudio.androidjhlabs.filter.DiffusionFilter;
import com.jabistudio.androidjhlabs.filter.EmbossFilter;
import com.jabistudio.androidjhlabs.filter.GrayscaleFilter;
import com.jabistudio.androidjhlabs.filter.HSBAdjustFilter;
import com.jabistudio.androidjhlabs.filter.OilFilter;
import com.jabistudio.androidjhlabs.filter.QuantizeFilter;
import com.jabistudio.androidjhlabs.filter.RescaleFilter;
import com.jabistudio.androidjhlabs.filter.SmearFilter;
import com.jabistudio.androidjhlabs.filter.SolarizeFilter;
import com.jabistudio.androidjhlabs.filter.StampFilter;
import com.jabistudio.androidjhlabs.filter.SwimFilter;
import com.jabistudio.androidjhlabs.filter.ThresholdFilter;
import com.jabistudio.androidjhlabs.filter.TritoneFilter;
import com.jabistudio.androidjhlabs.filter.WaterFilter;
import com.jabistudio.androidjhlabs.filter.util.AndroidUtils;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.Random;

public class MainActivity extends Activity {

    private ImageView img;
    private Bitmap bmp, bmp1;
    private Bitmap destBitmap;
    private Bitmap operation;
    private static final int SELECT_PICTURE = 1;
    public int OIL_PAINT = 0;
    private String selectedImagePath;
    private int i = 0;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my);

        img = (ImageView) findViewById(R.id.imageView1);



//        String ps=getIntent().getStringExtra("imagePath");
//
//
//        img.setImageBitmap(BitmapFactory.decodeFile(ps));

        Intent intent = getIntent();

        if(SelectPic.CAMERA_REQUEST == 1) {

            Bitmap bitmap = (Bitmap) intent.getParcelableExtra("bitimage");

            img.setImageBitmap(bitmap);
        } else {//        Bundle extras = getIntent().getExtras();
//        byte[] byteArray = extras.getByteArray("imagePath");

//        final Bitmap bmp = BitmapFactory.decodeByteArray(byteArray, 0, byteArray.length);
            Uri myUri = intent.getParcelableExtra("imagePath");
            img.setImageURI(myUri);
        }
            BitmapDrawable abmp = (BitmapDrawable) img.getDrawable();
            bmp = abmp.getBitmap();

        //button click
        ((Button) findViewById(R.id.button2))
                .setOnClickListener(new OnClickListener() {
                    public void onClick(View arg0) {
                        i++;
                        String path = Environment.getExternalStorageDirectory().toString();
                        OutputStream fOut = null;
                        File file = new File(path, "myModifiedimage_"+i+".jpg");
                        try {
                            fOut = new FileOutputStream(file);
                        } catch (FileNotFoundException e) {
                            e.printStackTrace();
                        }
                        BitmapDrawable abmp1 = (BitmapDrawable) img.getDrawable();
                        bmp1 = abmp1.getBitmap();
                        bmp1.compress(Bitmap.CompressFormat.JPEG, 85, fOut);
                        try {
                            fOut.flush();
                            fOut.close();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }


                        try {
                            MediaStore.Images.Media.insertImage(getContentResolver(), file.getAbsolutePath(), file.getName(), file.getName());
                        } catch (FileNotFoundException e) {
                            e.printStackTrace();
                        }

                        Toast.makeText(getApplicationContext(), "Saved",
                                Toast.LENGTH_SHORT).show();
                    }
                });
//paint button
        ((Button) findViewById(R.id.button1))
                .setOnClickListener(new OnClickListener() {
                    public void onClick(View arg0) {

                        AsyncTaskRunner runner = new AsyncTaskRunner();
                        runner.execute();

//                        img.setVisibility(View.INVISIBLE);
//                        myPB.setVisibility(View.VISIBLE);


//                        StampFilter solarFilter = new StampFilter();
//                        int[] src = AndroidUtils.bitmapToIntArray(bmp);
//
//                        int width = bmp.getWidth();
//                        int height = bmp.getHeight();
//                        int[] dest = solarFilter.filter(src, width, height);
//
//                        Bitmap destBitmap = Bitmap.createBitmap(dest, width, height, Bitmap.Config.ARGB_8888);
//                        img.setImageBitmap(destBitmap);

                        // Show webview and hide progress bar
//                            img.setVisibility(View.VISIBLE);
//                            myPB.setVisibility(View.GONE);

                    }
                });



        ((Button) findViewById(R.id.button3))
                .setOnClickListener(new OnClickListener() {
                    public void onClick(View arg0) {

                        OIL_PAINT = 1;

                        AsyncTaskRunner runner = new AsyncTaskRunner();
                        runner.execute();
                    }
                });

    }


    private class AsyncTaskRunner extends AsyncTask<String, Boolean, Boolean> {

        ProgressDialog dialog = new ProgressDialog(MainActivity.this);
        Button button = (Button) findViewById(R.id.button1);
        Button button2 = (Button) findViewById(R.id.button2);///

        @Override
        protected void onPreExecute() {
            button.setVisibility(View.VISIBLE);
            this.dialog.setMessage("Image Processing...");
            this.dialog.show();
            dialog.setCanceledOnTouchOutside(false);
        }

        @Override
        protected Boolean doInBackground(String... params) {

            try {
                // Do your long operations here and return the result
                if(OIL_PAINT != 1)
                {
                    StampFilter solarFilter = new StampFilter();
                    int[] src = AndroidUtils.bitmapToIntArray(bmp);
                    int width = bmp.getWidth();
                    int height = bmp.getHeight();
                    int[] dest = solarFilter.filter(src, width, height);
                    Bitmap destBitmap = Bitmap.createBitmap(dest, width, height, Bitmap.Config.ARGB_8888);
                    img.setImageBitmap(destBitmap);
                    return true;
                }else
                {
                    OilFilter solarFilter = new OilFilter();
                    int[] src = AndroidUtils.bitmapToIntArray(bmp);
                    int width = bmp.getWidth();
                    int height = bmp.getHeight();
                    int[] dest = solarFilter.filter(src, width, height);
                    Bitmap destBitmap = Bitmap.createBitmap(dest, width, height, Bitmap.Config.ARGB_8888);
                    img.setImageBitmap(destBitmap);
                    return true;
                }

            } catch (Exception e) {
                e.printStackTrace();
                return false;
            }

        }


        protected void onPostExecute(final Boolean success) {

            if (dialog.isShowing()) {
                dialog.dismiss();
            }
            button.setVisibility(View.INVISIBLE);
            button2.setVisibility(View.VISIBLE);


        }

        /*
         * (non-Javadoc)
         *
         * @see android.os.AsyncTask#onPostExecute(java.lang.Object)
         */
//        @Override
//        protected void onPostExecute(String result) {
//            // execution of result of Long time consuming operation
//            finalResult.setText(result);
//        }
        //on activity result

//    public void onActivityResult(int requestCode, int resultCode, Intent data) {
//        if (resultCode == RESULT_OK) {
//            if (requestCode == SELECT_PICTURE) {
//                Uri selectedImageUri = data.getData();
//                selectedImagePath = getPath(selectedImageUri);
//                System.out.println("Image Path : " + selectedImagePath);
//                img.setImageURI(selectedImageUri);
//            }
//        }
//        BitmapDrawable abmp = (BitmapDrawable) img.getDrawable();
//        bmp = abmp.getBitmap();
//    }

//    public void gray(View view) {
//
////        OilFilter solarFilter = new OilFilter();
//        StampFilter solarFilter = new StampFilter();
//        int[] src = AndroidUtils.bitmapToIntArray(bmp);
//
//        int width = bmp.getWidth();
//        int height = bmp.getHeight();
//        int[] dest = solarFilter.filter(src, width, height);
//
//        Bitmap destBitmap = Bitmap.createBitmap(dest, width, height, Bitmap.Config.ARGB_8888);
//        img.setImageBitmap(destBitmap);
//    }

        //getpath

//    public String getPath(Uri uri) {
//        String[] projection = { MediaStore.Images.Media.DATA };
//        Cursor cursor = managedQuery(uri, projection, null, null, null);
//        int column_index = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
//        cursor.moveToFirst();
//        return cursor.getString(column_index);
//    }

    }
}