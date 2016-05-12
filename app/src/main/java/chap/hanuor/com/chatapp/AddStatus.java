package chap.hanuor.com.chatapp;

import android.app.Dialog;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.graphics.Palette;
import android.support.v7.widget.Toolbar;
import android.view.Gravity;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.flaviofaria.kenburnsview.KenBurnsView;
import com.flaviofaria.kenburnsview.RandomTransitionGenerator;
import com.parse.FindCallback;
import com.parse.GetCallback;
import com.parse.ParseException;
import com.parse.ParseFile;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.ParseUser;
import com.parse.SaveCallback;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class AddStatus extends AppCompatActivity {
    String cu;
    EditText sta;
    ArrayList<String> neus;
    Button sub;
    FloatingActionButton fab;
    CollapsingToolbarLayout collapsingToolbar;
    Dialog dialog;
    KenBurnsView kbv;
    ImageView header;
    Toolbar toolbar;
    private static final int CAMERA_REQUEST = 1881;
    private static final int ACTIVITY_SELECT_IMAGE= 1978;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_status);
        toolbar = (Toolbar) findViewById(R.id.anim_toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDefaultDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        final Drawable upArrow = getResources().getDrawable(R.drawable.abc_ic_ab_back_mtrl_am_alpha);
        upArrow.setColorFilter(getResources().getColor(R.color.bg_glass), PorterDuff.Mode.SRC_ATOP);
        getSupportActionBar().setHomeAsUpIndicator(upArrow);

        header = (ImageView) findViewById(R.id.header);
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

collapsingToolbar.setTitleEnabled(false);
                    //mutedColor = palette.getMutedColor(getResources().getColor(R.color.colorPrimaryDark));


                  /*  // Set the background color of a layout based on the vibrant color
                    containerView.setBackgroundColor(vibrant.getRgb());
                    // Update the title TextView with the proper text color
                    titleView.setTextColor(vibrant.getTitleTextColor());*/
                }


            }
        });

        fab = (FloatingActionButton) findViewById(R.id.fab);
        kbv = (KenBurnsView) findViewById(R.id.imageCover);
        AccelerateDecelerateInterpolator ACCELERATE_DECELERATE = new AccelerateDecelerateInterpolator();
        RandomTransitionGenerator generator = new RandomTransitionGenerator(10000, ACCELERATE_DECELERATE);

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog = new Dialog(AddStatus.this , R.style.DialogSlideAnim);
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
                window.setLayout(WindowManager.LayoutParams.FILL_PARENT, WindowManager.LayoutParams.WRAP_CONTENT);
            }
        });
        sta = (EditText) findViewById(R.id.getStatus);
        sub = (Button) findViewById(R.id.submit);
        neus = new ArrayList<String>();
        cu = ParseUser.getCurrentUser().getObjectId().toString();
        sub.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (sta.getText().toString().contentEquals("")) {
                    Snackbar.make(view, "Please enter a thought", Snackbar.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(AddStatus.this, " Submitted successfully", Toast.LENGTH_SHORT).show();
                    final String getterstatus = sta.getText().toString();
                    ParseQuery<ParseObject> qa = ParseQuery.getQuery("AmigoPosts");
                    qa.whereEqualTo("CreatorId",ParseUser.getCurrentUser().getObjectId().toString());
                    qa.findInBackground(new FindCallback<ParseObject>() {
                        @Override
                        public void done(List<ParseObject> list, ParseException e) {
                            if(e==null){

                                if(list.size()!=0) {


                                    String objAmigoPost = list.get(0).getObjectId().toString();
                                    try {
                                        ParseQuery<ParseObject> fin = ParseQuery.getQuery("AmigoPosts");
                                        fin.getInBackground(objAmigoPost, new GetCallback<ParseObject>() {
                                            @Override
                                            public void done(ParseObject frequest, ParseException e) {

                                                frequest.put("CreatorId", cu);
                                                frequest.put("CreatorStatus", getterstatus);
                                                frequest.put("Likes", "0");
                                                frequest.put("SharesName", " ");
                                                frequest.put("CommentsString", " ");
                                                frequest.put("CommentIds", " ");
                                                frequest.put("ShareCount", "0");
                                                frequest.put("SharesName", " ");
                                                frequest.put("LikesName", " ");
                                                frequest.put("ListLikes", Arrays.asList(neus));
                                                frequest.saveInBackground();


                                            }
                                        });
                                    } catch (Exception e1) {
                                        e1.printStackTrace();
                                    }
                                }
                                else{
                                    ParseObject frequest = new ParseObject("AmigoPosts");
                                    frequest.put("CreatorId", cu);
                                    frequest.put("LikesCounter","0");
                                    frequest.put("CommentsCounter","0");
                                    frequest.put("SharesCounter","0");
                                    frequest.put("CreatorStatus", getterstatus);
                                    frequest.put("Likes", "0");
                                    frequest.put("ShareIds", "");
                                    frequest.put("CommentsString", " ");
                                    frequest.put("CommentIds", " ");
                                    frequest.put("ShareCount", "0");
                                    frequest.put("SharesName", " ");
                                    frequest.put("LikesName", " ");
                                    frequest.put("ListLikes", Arrays.asList(neus));
                                    frequest.saveInBackground();



                                }
                            }else{
                                e.printStackTrace();
                            }
                        }
                    });
                    //ParseObject frequest = new ParseObject("AmigoPosts");

                    ParseQuery<ParseUser> query = ParseUser.getQuery();
                    query.getInBackground(cu, new GetCallback<ParseUser>() {
                        @Override
                        public void done(ParseUser parseUser, ParseException e) {
                            if (e == null) {
                                parseUser.put("UserStatus", getterstatus);
                                parseUser.put("StatusLikes", "0");
                                parseUser.put("StatusComments", 0);
                                parseUser.put("StatusShare", 0);

                                parseUser.saveInBackground();
                                Bitmap icon = BitmapFactory.decodeResource(getApplicationContext().getResources(),
                                        R.drawable.abs);
                                ByteArrayOutputStream stream = new ByteArrayOutputStream();

                                icon.compress(Bitmap.CompressFormat.PNG, 0, stream);
                                byte[] image = stream.toByteArray();
                                final ParseFile photoFile = new ParseFile("currentuserCover.jpg", image);
                                photoFile.saveInBackground(new SaveCallback() {

                                    public void done(ParseException e) {

                                        ParseUser.getCurrentUser().put("CoverPicture", photoFile);
                                        ParseUser.getCurrentUser().saveInBackground();
                                        if (e != null) {
                                            Toast.makeText(getApplicationContext(),
                                                    "Error saving: " + e.getMessage(),
                                                    Toast.LENGTH_LONG).show();
                                        } else {

                                        }
                                    }
                                });
                            }
                        }
                    });
                    finish();

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
                        collapsingToolbar.setTitleEnabled(false);

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
                    Bitmap finalBmp = Bitmap.createScaledBitmap(dstBmp,450,450,true);
                    saveImage(finalBmp);
                    header.setImageBitmap(finalBmp);
                    Palette.from(finalBmp).generate(new Palette.PaletteAsyncListener() {
                        @Override
                        public void onGenerated(Palette palette) {

                            Palette.Swatch vibrant = palette.getMutedSwatch();
                            Palette.Swatch vib = palette.getDarkMutedSwatch();
                            if (vibrant != null) {
                                collapsingToolbar = (CollapsingToolbarLayout) findViewById(R.id.collapsing_toolbar);
                                collapsingToolbar.setContentScrimColor(vibrant.getBodyTextColor());
                                collapsingToolbar.setStatusBarScrimColor(vib.getPopulation());

                                collapsingToolbar.setExpandedTitleColor(vib.getTitleTextColor());

                                collapsingToolbar.setTitleEnabled(false);

                            }


                        }
                    });

                }
        }
    }


    private void saveImage(Bitmap photo) {

        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        photo.compress(Bitmap.CompressFormat.PNG, 0, stream);
        byte[] image = stream.toByteArray();
        final ParseFile photoFile = new ParseFile("currentuserCover.jpg", image);
      photoFile.saveInBackground(new SaveCallback() {

            public void done(ParseException e) {

                ParseUser.getCurrentUser().put("CoverPicture", photoFile);
                ParseUser.getCurrentUser().saveInBackground();
                if (e != null) {
                    Toast.makeText(getApplicationContext(),
                            "Error saving: " + e.getMessage(),
                            Toast.LENGTH_LONG).show();
                } else {

                }
            }
        });
    }


}
