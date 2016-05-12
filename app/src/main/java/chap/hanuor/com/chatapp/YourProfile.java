package chap.hanuor.com.chatapp;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.graphics.Palette;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Gravity;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.parse.FindCallback;
import com.parse.GetCallback;
import com.parse.GetDataCallback;
import com.parse.ParseException;
import com.parse.ParseFile;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.ParseUser;
import com.parse.SaveCallback;
import com.soundcloud.android.crop.Crop;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;


public class YourProfile extends AppCompatActivity {
    Toolbar toolbar;
    CollapsingToolbarLayout collapsingToolbar;
    RelativeLayout tagline;
    FloatingActionButton fab;
    String namesProfile[] = {"Username:","Email:"};
    int ico [] = {R.drawable.ic_profile44,R.drawable.ic_social113,R.drawable.ic_stat_note65,R.drawable.ic_stat_social,R.drawable.ic_stat_chat,R.drawable.ic_stat_redheart};
    RecyclerView recy;
    int mutedColor = R.color.colorPrimary;
    ArrayList<String> names;
    String tag;
    int prof;
    Dialog dialog;
    ParseFile imge;
    TextView taggy;
    private static final int CAMERA_REQUEST = 18381;
    private static final int ACTIVITY_SELECT_IMAGE= 5978;
    YourProfileAdapter adapter;
    ImageView header;
    ProgressDialog pd;
     @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_your_profile);
        pd = new ProgressDialog(this);
         pd.setMessage("Loading data...");
         pd.show();
        prof = 0;
         tagline = (RelativeLayout) findViewById(R.id.tagline);
        toolbar = (Toolbar) findViewById(R.id.anim_toolbar);
        fab = (FloatingActionButton) findViewById(R.id.fab);
         taggy = (TextView) findViewById(R.id.tag);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDefaultDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        header = (ImageView) findViewById(R.id.header);
        recy = (RecyclerView) findViewById(R.id.scrollableview);
        imge = (ParseFile) ParseUser.getCurrentUser().getParseFile("CoverPicture");
        displayCover(imge,header);

         //set tag hint


         ParseQuery<ParseUser> query = ParseUser.getQuery();
         query.whereEqualTo("objectId",ParseUser.getCurrentUser().getObjectId());
         query.findInBackground(new FindCallback<ParseUser>() {
             @Override
             public void done(List<ParseUser> list, ParseException e) {
                 if(e == null){
                     try {
                         tag = list.get(0).get("Tagline").toString();
                     } catch (Exception e1) {
                         e1.printStackTrace();
                     }

                 }else{
                     e.printStackTrace();
                 }
             }
         });
        if(tag!=null){
            taggy.setText(tag);
        }else{
            taggy.setText("Write your tagline now!");
        }


        recy.setHasFixedSize(true);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        recy.setLayoutManager(linearLayoutManager);

        names = new ArrayList<String>();
        names.add(0,"@"+ParseUser.getCurrentUser().getUsername().toString());

        if(ParseUser.getCurrentUser().getBoolean("emailVerified")==true){
              names.add(1,""+ParseUser.getCurrentUser().getEmail().toString());
        }else
        {
            names.add(1,"[Please verify your Email]");
        }
       fgh(ParseUser.getCurrentUser().getObjectId().toString());
        adapter = new YourProfileAdapter(this,namesProfile,names,ico);
        recy.setAdapter(adapter);

        header.setImageDrawable(getResources().getDrawable(R.drawable.lavida));
        Bitmap bitmap = BitmapFactory.decodeResource(getResources(),
                R.drawable.lavida);
        Palette.from(bitmap).generate(new Palette.PaletteAsyncListener() {
            @Override
            public void onGenerated(Palette palette) {

                Palette.Swatch vibrant = palette.getMutedSwatch();
                Palette.Swatch vib = palette.getDarkMutedSwatch();
                if (vibrant != null) {
                    collapsingToolbar = (CollapsingToolbarLayout) findViewById(R.id.collapsing_toolbar);
                    collapsingToolbar.setContentScrimColor(vibrant.getBodyTextColor());
                    collapsingToolbar.setStatusBarScrimColor(vib.getRgb());

                    collapsingToolbar.setExpandedTitleColor(vib.getTitleTextColor());

                    collapsingToolbar.setTitle(ParseUser.getCurrentUser().get("FirstName").toString());



                }


            }
        });


        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Crop.pickImage(YourProfile.this);
                //prof = 1;
                dialog = new Dialog(YourProfile.this , R.style.DialogSlideAnim);
                dialog.setContentView(R.layout.custom_dialog_photo);
                Button cam = (Button) dialog.findViewById(R.id.opencam);
                Button gal = (Button) dialog.findViewById(R.id.opengal);
                Button cancel = (Button) dialog.findViewById(R.id.butcancel);
                WindowManager.LayoutParams lp = new WindowManager.LayoutParams();
                lp.copyFrom(dialog.getWindow().getAttributes());
                lp.width = WindowManager.LayoutParams.MATCH_PARENT;
                lp.height = WindowManager.LayoutParams.WRAP_CONTENT;
                lp.gravity = Gravity.CENTER;

                dialog.getWindow().setAttributes(lp);
                cam.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Intent cameraIntent = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
                        startActivityForResult(cameraIntent, CAMERA_REQUEST);
                        dialog.dismiss();
                    }
                });
                gal.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Intent i = new Intent(Intent.ACTION_PICK,
                                android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                        startActivityForResult(i, ACTIVITY_SELECT_IMAGE);
                        dialog.dismiss();
                    }
                });
                cancel.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        dialog.dismiss();
                    }
                });
                dialog.show();
                Window window = dialog.getWindow();
                window.setLayout(WindowManager.LayoutParams.WRAP_CONTENT, WindowManager.LayoutParams.WRAP_CONTENT);
            }
        });

         tagline.setOnClickListener(new View.OnClickListener() {
             @Override
             public void onClick(View view) {

                 final Dialog dialog = new Dialog(YourProfile.this);
                 dialog.setContentView(R.layout.taglinedialog);
                 Button save = (Button) dialog.findViewById(R.id.button);

                 Button cancel = (Button) dialog.findViewById(R.id.button2);
                 final EditText text = (EditText) dialog.findViewById(R.id.et);
                 text.setHintTextColor(Color.parseColor("#9e9e9e"));
                 if(tag!=null){
                     text.setHint(""+tag);
                 }else{
                     text.setHint(" ");
                 }
                    save.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            final ProgressDialog p = new ProgressDialog(YourProfile.this);
                            p.setMessage("Saving...");
                            p.show();
                            //OR
                            final String value = text.getText().toString();
                            ParseQuery<ParseUser> query = ParseUser.getQuery();
                            query.getInBackground(ParseUser.getCurrentUser().getObjectId(), new GetCallback<ParseUser>() {
                                @Override
                                public void done(ParseUser user, ParseException e) {
                                    if(e == null){
                                        user.put("Tagline",value);
                                        user.saveInBackground(new SaveCallback() {
                                            @Override
                                            public void done(ParseException e) {
                                                if(e == null){
                                                    p.dismiss();
                                                }else{
                                                    e.printStackTrace();
                                                }
                                            }
                                        });

                                    }else{
                                        e.printStackTrace();
                                    }
                                }
                            });
                            dialog.dismiss();
                        }
                    });
cancel.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View view) {
        dialog.dismiss();
    }
});






                 dialog.show();
             }
         });
    }

    private void displayCover(ParseFile im, final ImageView der) {
    if(im!=null){
        im.getDataInBackground(new GetDataCallback() {

            @Override
            public void done(byte[] data, ParseException e) {

                if (e == null) {
                    Bitmap bitmap = BitmapFactory.decodeByteArray(data, 0, data.length);
                    Bitmap bs = BitmapFactory.decodeByteArray(data, 0, data.length);
                    ByteArrayOutputStream blob = new ByteArrayOutputStream();
                    bitmap.compress(Bitmap.CompressFormat.PNG, 0 /*ignored for PNG*/, blob);

                    byte[] bitmapdata = blob.toByteArray();

                    BitmapDrawable ob = new BitmapDrawable(getResources(), bitmap);
                    //rotatedScaledMealImage.compress(Bitmap.CompressFormat.JPEG, 100, bos);
                    //Bitmap bmp = BitmapFactory.decodeByteArray(data, 0,
                    //  data.length);


                    if (bitmap != null) {

                        Log.e("parse file ok", " null");

                        der.setImageDrawable(ob);
                        pd.dismiss();
                        //myi.setBackgroundDrawable(ob);

                    }
                } else {

                }

            }
        });
    } else {

        Log.e("parse file", " null");


    }

    }
    public void fgh(String ohja){
        pd.show();
        ParseQuery<ParseObject> qw = ParseQuery.getQuery("AmigoPosts");
        qw.whereEqualTo("CreatorId",ohja);
        qw.findInBackground(new FindCallback<ParseObject>() {
            @Override
            public void done(List<ParseObject> list, ParseException e) {
                if(list.size()!=0){
                    String stat = list.get(0).get("CreatorStatus").toString();
                    names.add(2,""+stat);
                    String shaers = list.get(0).get("ShareCount").toString();
                    //vh.getsharetextView().setText(shaers);
                    //names.add(3,""+shaers);
                    if(shaers.contentEquals("1")){
                        names.add(3,""+shaers.toString()+"   Share");
                    }else{
                        names.add(3,""+shaers.toString()+"   Shares");
                    }


                    String amal = list.get(0).get("CommentsString"  ).toString();
                    String Comments[] = amal.split("\\%3:11071995:11");
                    if(Comments[0].equals(" ")) {
                      //  vh.getTextComment().setText(""+Comments.length);

                        names.add(4,""+0+"   Comments");
                        }
                    else{
                        if(Comments.length == 1){
                            names.add(4,""+Comments.length+"   Comment");
                        }else {
                            names.add(4, "" + Comments.length + "   Comments");
                        }

                    }
                    String g = list.get(0).get("Likes").toString();
                    if(g.contentEquals("1")){
                        names.add(5,""+g.toString()+"   Like");
                    }else{
                        names.add(5,""+g.toString()+"   Likes");
                    }



                }
                else{
                    // Toast.makeText(ListUsersActivity.this, "Not Found", Toast.LENGTH_SHORT).show();
                    //e.printStackTrace();
                }
            }
        });

        adapter = new YourProfileAdapter(this,namesProfile,names,ico);
        recy.setAdapter(adapter);
        pd.dismiss();
      //  gr.dismiss();
    }

    @Override
        protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == CAMERA_REQUEST && resultCode == RESULT_OK) {
            Bitmap photo = (Bitmap) data.getExtras().get("data");
            saveImage(photo);
            header.setImageBitmap(photo);
            //saveImage(photo);
            Palette.from(photo).generate(new Palette.PaletteAsyncListener() {
                @Override
                public void onGenerated(Palette palette) {

                    Palette.Swatch vibrant = palette.getMutedSwatch();
                    Palette.Swatch vib = palette.getDarkMutedSwatch();
                    if (vibrant != null) {
                        collapsingToolbar = (CollapsingToolbarLayout) findViewById(R.id.collapsing_toolbar);
                        collapsingToolbar.setContentScrimColor(vibrant.getBodyTextColor());
                        collapsingToolbar.setStatusBarScrimColor(vib.getPopulation());

                        // collapsingToolbar.setExpandedTitleColor(vib.getTitleTextColor());

                        // collapsingToolbar.setTitle(ParseUser.getCurrentUser().get("FirstName").toString());

                        //  mutedColor = palette.getMutedColor(getResources().getColor(R.color.colorPrimaryDark));


                  /*  // Set the background color of a layout based on the vibrant color
                    containerView.setBackgroundColor(vibrant.getRgb());
                    // Update the title TextView with the proper text color
                    titleView.setTextColor(vibrant.getTitleTextColor());*/
                    }


                }
            });
        }
        switch(requestCode) {
            case ACTIVITY_SELECT_IMAGE:
                if(resultCode == RESULT_OK){
                    Uri selectedImage = data.getData();
                    String[] filePathColumn = {MediaStore.Images.Media.DATA};

                    Cursor cursor = getContentResolver().query(
                            selectedImage, filePathColumn, null, null, null);
                    cursor.moveToFirst();

                    int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
                    String filePath = cursor.getString(columnIndex);
                    cursor.close();


                    Bitmap srcBmp = BitmapFactory.decodeFile(filePath);
                    Bitmap dstBmp;
                    if (srcBmp.getWidth() >= srcBmp.getHeight()){

                        dstBmp = Bitmap.createBitmap(
                                srcBmp,
                                srcBmp.getWidth()/2 - srcBmp.getHeight()/2,
                                0,
                                srcBmp.getHeight(),
                                srcBmp.getHeight()
                        );

                    }else{

                        dstBmp = Bitmap.createBitmap(
                                srcBmp,
                                0,
                                srcBmp.getHeight()/2 - srcBmp.getWidth()/2,
                                srcBmp.getWidth(),
                                srcBmp.getWidth()
                        );
                    }
                   // Bitmap finalBmp = Bitmap.createScaledBitmap(dstBmp,450,450,true);
                    saveImage(dstBmp);
                    header.setImageBitmap(dstBmp);
                    Palette.from(dstBmp).generate(new Palette.PaletteAsyncListener() {
                        @Override
                        public void onGenerated(Palette palette) {

                            Palette.Swatch vibrant = palette.getMutedSwatch();
                            Palette.Swatch vib = palette.getDarkMutedSwatch();
                            if (vibrant != null) {
                                collapsingToolbar = (CollapsingToolbarLayout) findViewById(R.id.collapsing_toolbar);
                                collapsingToolbar.setContentScrimColor(vibrant.getBodyTextColor());
                                collapsingToolbar.setStatusBarScrimColor(vib.getPopulation());

                                // collapsingToolbar.setExpandedTitleColor(vib.getTitleTextColor());

                                // collapsingToolbar.setTitle(ParseUser.getCurrentUser().get("FirstName").toString());

                                //  mutedColor = palette.getMutedColor(getResources().getColor(R.color.colorPrimaryDark));


                  /*  // Set the background color of a layout based on the vibrant color
                    containerView.setBackgroundColor(vibrant.getRgb());
                    // Update the title TextView with the proper text color
                    titleView.setTextColor(vibrant.getTitleTextColor());*/
                            }


                        }
                    });

                }
        }
        }
    private void beginCrop(Uri source) {
        Uri destination = Uri.fromFile(new File(getCacheDir(), "cropped"));
        Crop.of(source, destination).asSquare().start(YourProfile.this);
    }

    private void handleCrop(int resultCode, Intent result) {

        if (resultCode == RESULT_OK) {
            Uri imageUri = Crop.getOutput(result);
            Drawable yd;
            if(prof ==1) {
                    header.setImageDrawable(null);
                    header.setImageURI(imageUri);
                    try {
                       // Bitmap bi = MediaStore.Images.Media.getBitmap(YourProfile.this.getContentResolver(),Crop.getOutput(result));

                      //  my.setImageDrawable(null);

                        //my.setImageURI(Crop.getOutput(result));
                        Bitmap  biq;
                        try {

                            biq = MediaStore.Images.Media.getBitmap(YourProfile.this.getContentResolver(), imageUri);

                        ByteArrayOutputStream stream = new ByteArrayOutputStream();
                        biq.compress(Bitmap.CompressFormat.PNG, 0, stream);
                        byte[] image = stream.toByteArray();
                        final ParseFile photoFile = new ParseFile("currentuserCover.jpg", image);
                       // newtonCradleLoading.setVisibility(View.VISIBLE);
                        //newtonCradleLoading.start();

                        photoFile.saveInBackground(new SaveCallback() {

                            public void done(ParseException e) {

                                ParseUser.getCurrentUser().put("CoverPicture", photoFile);
                                ParseUser.getCurrentUser().saveInBackground();
                                if (e != null) {
                                    Toast.makeText(getApplicationContext(),
                                            "Error saving: " + e.getMessage(),
                                            Toast.LENGTH_LONG).show();
                                } else {
                                    //newtonCradleLoading.stop();
                                    //newtonCradleLoading.setVisibility(View.GONE);
                                    Toast.makeText(getApplicationContext(), "Blah", Toast.LENGTH_SHORT).show();
                                }
                            }
                        });

                      //  profilepic = 0;

                        Palette.from(biq).generate(new Palette.PaletteAsyncListener() {
                            @Override
                            public void onGenerated(Palette palette) {

                                Palette.Swatch vibrant = palette.getMutedSwatch();
                                Palette.Swatch vib = palette.getDarkMutedSwatch();
                                if (vibrant != null) {
                                    collapsingToolbar = (CollapsingToolbarLayout) findViewById(R.id.collapsing_toolbar);
                                    collapsingToolbar.setContentScrimColor(vibrant.getBodyTextColor());
                                    collapsingToolbar.setStatusBarScrimColor(vib.getPopulation());

                                    collapsingToolbar.setExpandedTitleColor(vib.getTitleTextColor());

                                    collapsingToolbar.setTitle(ParseUser.getCurrentUser().get("FirstName").toString());

                                  //  mutedColor = palette.getMutedColor(getResources().getColor(R.color.colorPrimaryDark));


                  /*  // Set the background color of a layout based on the vibrant color
                    containerView.setBackgroundColor(vibrant.getRgb());
                    // Update the title TextView with the proper text color
                    titleView.setTextColor(vibrant.getTitleTextColor());*/
                                }


                            }
                        });
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    } catch (Exception r) {
                        r.printStackTrace();
                    }

                prof = 0;
            }
        }
    }

    private void saveImage(Bitmap photo) {

        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        photo.compress(Bitmap.CompressFormat.PNG, 0, stream);
        byte[] image = stream.toByteArray();
        final ParseFile photoFile = new ParseFile("currentuserCover.jpg", image);
        // newtonCradleLoading.setVisibility(View.VISIBLE);
        //newtonCradleLoading.start();

        photoFile.saveInBackground(new SaveCallback() {

            public void done(ParseException e) {

                ParseUser.getCurrentUser().put("CoverPicture", photoFile);
                ParseUser.getCurrentUser().saveInBackground();
                if (e != null) {
                    Toast.makeText(getApplicationContext(),
                            "Error saving: " + e.getMessage(),
                            Toast.LENGTH_LONG).show();
                } else {
                    //newtonCradleLoading.stop();
                    //newtonCradleLoading.setVisibility(View.GONE);

                }
            }
        });
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem menuItem) {
        if (menuItem.getItemId() == android.R.id.home) {
            onBackPressed();
        }
        return super.onOptionsItemSelected(menuItem);
    }
}
