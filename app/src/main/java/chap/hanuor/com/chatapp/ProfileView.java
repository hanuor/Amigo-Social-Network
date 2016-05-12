package chap.hanuor.com.chatapp;

import android.app.Dialog;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.graphics.PorterDuff;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.flaviofaria.kenburnsview.KenBurnsView;
import com.flaviofaria.kenburnsview.RandomTransitionGenerator;
import com.parse.GetDataCallback;
import com.parse.ParseException;
import com.parse.ParseFile;
import com.parse.ParseUser;
import com.parse.SaveCallback;
import com.soundcloud.android.crop.Crop;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;

import de.hdodenhof.circleimageview.CircleImageView;

public class ProfileView extends AppCompatActivity {
    CircleImageView my;
    RelativeLayout rv1;
    Button but;
    Bitmap bi;
    private static final int CAMERA_REQUEST = 10581;
    String cu;
    Dialog dialog;
    Toolbar to;
    ParseUser currentUserParse;
    ParseFile imge;

    Bitmap bmp;
int coveric ;
    int profilepic;
    Button eC;
TextView id;
        TextView username;
    ParseFile mx;
    KenBurnsView kbv;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        try {
            cu = ParseUser.getCurrentUser().get("FirstName").toString();
        } catch (Exception e) {
            e.printStackTrace();
        }
        setContentView(R.layout.profile_view);
        to = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(to);


        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        final Drawable upArrow = getResources().getDrawable(R.drawable.abc_ic_ab_back_mtrl_am_alpha);
        upArrow.setColorFilter(getResources().getColor(R.color.bg_glass), PorterDuff.Mode.SRC_ATOP);
        getSupportActionBar().setHomeAsUpIndicator(upArrow);
        currentUserParse = ParseUser.getCurrentUser();
        mx = ParseUser.getCurrentUser().getParseFile("photo");
        //newtonCradleLoading = (RotateLoading) findViewById(R.id.rotateloading);
        coveric = 0;
        profilepic = 0;
        username = (TextView) findViewById(R.id.name);
        id = (TextView) findViewById(R.id.tagLineUsername);
        username.setText(cu);
        id.setText(ParseUser.getCurrentUser().getUsername().toString());
        imge = (ParseFile) currentUserParse.getParseFile("photo");

        my = (CircleImageView) findViewById(R.id.iv);
        displayImage(imge,my);
        kbv = (KenBurnsView) findViewById(R.id.imageCover);
        //rv1 = (RelativeLayout) findViewById(R.id.rv1);

       but = (Button) findViewById(R.id.editimage);

        AccelerateDecelerateInterpolator ACCELERATE_DECELERATE = new AccelerateDecelerateInterpolator();
        RandomTransitionGenerator generator = new RandomTransitionGenerator(10000, ACCELERATE_DECELERATE);

        kbv.setTransitionGenerator(generator); //set new transition on kbv
    but.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View view) {
         //   Crop.pickImage(ProfileView.this);
          //  profilepic = 1;

            dialog = new Dialog(ProfileView.this , R.style.DialogSlideAnim);
            dialog.setContentView(R.layout.custom_dialog_photo);
            Button cam = (Button) dialog.findViewById(R.id.opencam);
            Button gal = (Button) dialog.findViewById(R.id.opengal);
            Button cancel = (Button) dialog.findViewById(R.id.butcancel);
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
                      Crop.pickImage(ProfileView.this);
                      profilepic = 1;
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
/*
        eC.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                coveric =1;
                Crop.pickImage(ProfileView.this);

            }
        });
*/

    }
    @Override
    public boolean onOptionsItemSelected(MenuItem menuItem) {
        if (menuItem.getItemId() == android.R.id.home) {
            onBackPressed();
        }
        return super.onOptionsItemSelected(menuItem);
    }


    @Override
    protected void onResume() {
        super.onResume();

    }

    private void displayImage(ParseFile thumbnail, final CircleImageView myi) {
        if (thumbnail != null) {
          //  newtonCradleLoading.setVisibility(View.VISIBLE);
            //newtonCradleLoading.start();
            thumbnail.getDataInBackground(new GetDataCallback() {

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
                        //Bitmap blurred = blur(bs);

                       // BitmapDrawable ard = new BitmapDrawable(blurred);
                       // rv1.setBackground(ard);

                        if (bitmap != null) {

                            Log.e("parse file ok", " null");

                            myi.setImageDrawable(ob);
                            //myi.setBackgroundDrawable(ob);
                           // newtonCradleLoading.stop();
                            //newtonCradleLoading.setVisibility(View.GONE);
                        }
                    } else {

                    }

                }
            });
        } else {

            Log.e("parse file", " null");

        }

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == Crop.REQUEST_PICK && resultCode == RESULT_OK) {
            beginCrop(data.getData());
        } else if (requestCode == Crop.REQUEST_CROP) {
            handleCrop(resultCode, data);
        }

        if (requestCode == CAMERA_REQUEST && resultCode == RESULT_OK){
            Bitmap photo = (Bitmap) data.getExtras().get("data");
            my.setImageBitmap(photo);
            savInback(photo);
        }
    }

    private void savInback(Bitmap photo) {

        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        photo.compress(Bitmap.CompressFormat.PNG, 0, stream);
        byte[] image = stream.toByteArray();
        final ParseFile photoFile = new ParseFile("currentuserProfileImage.jpg", image);
        photoFile.saveInBackground(new SaveCallback() {

            public void done(ParseException e) {
                currentUserParse.put("photo", photoFile);
                currentUserParse.saveInBackground();
                if (e != null) {
                    Toast.makeText(getApplicationContext(),
                            "Error saving: " + e.getMessage(),
                            Toast.LENGTH_LONG).show();
                } else {
                    // newtonCradleLoading.stop();
                    //newtonCradleLoading.setVisibility(View.GONE);

                }
            }
        });

    }


    private void beginCrop(Uri source) {
        Uri destination = Uri.fromFile(new File(getCacheDir(), "cropped"));
        Crop.of(source, destination).asSquare().start(ProfileView.this);
    }

    private void handleCrop(int resultCode, Intent result) {

        if (resultCode == RESULT_OK) {
            Uri imageUri = Crop.getOutput(result);
            //relativeBackground(imageUri);
            if (profilepic == 1) {


               // my.setImageDrawable(null);
                try {
                    Bitmap n= MediaStore.Images.Media.getBitmap(ProfileView.this.getContentResolver(), imageUri);
                    Bitmap l = getResizedBitmap(n,400,400);
                    my.setImageBitmap(l);
                    //Drawable draw = new BitmapDrawable(getResources(),n);
                   /* ByteArrayOutputStream bos = new ByteArrayOutputStream();
                    n.compress(Bitmap.CompressFormat.PNG, 0 *//*ignored for PNG*//*, bos);
                    byte[] bitmapdata = bos.toByteArray();
                    ByteArrayInputStream bs = new ByteArrayInputStream(bitmapdata);
                    BitmapFactory.Options options = new BitmapFactory.Options();
                    options.inJustDecodeBounds = true;
                   // BitmapFactory.de
                    //BitmapFactory.decodeResource(getResources(), getResources().getIdentifier("draw","drawable",this.getPackageName()), options);
                    BitmapFactory.decodeStream(bs, null, options);
                    int imageHeight = options.outHeight;
                    int imageWidth = options.outWidth;
                    String imageType = options.outMimeType;*/

                 //   my.setImageBitmap(decodeSampledBitmapFromResource(getResources(),bs,400,400));
                } catch (IOException e) {
                    e.printStackTrace();
                }


                try {
                    bi = MediaStore.Images.Media.getBitmap(ProfileView.this.getContentResolver(), imageUri);
                } catch (IOException e) {
                    e.printStackTrace();
                }
                ByteArrayOutputStream stream = new ByteArrayOutputStream();
                bi.compress(Bitmap.CompressFormat.PNG, 0, stream);
                byte[] image = stream.toByteArray();
                final ParseFile photoFile = new ParseFile("currentuserProfileImage.jpg", image);
                //newtonCradleLoading.setVisibility(View.VISIBLE);
                //newtonCradleLoading.start();

                photoFile.saveInBackground(new SaveCallback() {

                    public void done(ParseException e) {
                        currentUserParse.put("photo", photoFile);
                        currentUserParse.saveInBackground();
                        if (e != null) {
                            Toast.makeText(getApplicationContext(),
                                    "Error saving: " + e.getMessage(),
                                    Toast.LENGTH_LONG).show();
                        } else {
                            // newtonCradleLoading.stop();
                            //newtonCradleLoading.setVisibility(View.GONE);

                        }
                    }
                });

                profilepic = 0;
            } else if (resultCode == Crop.RESULT_ERROR) {
                Toast.makeText(this, Crop.getError(result).getMessage(), Toast.LENGTH_SHORT).show();
            }
        }

    }
    public Bitmap getResizedBitmap(Bitmap bm, int newWidth, int newHeight) {
        int width = bm.getWidth();
        int height = bm.getHeight();
        float scaleWidth = ((float) newWidth) / width;
        float scaleHeight = ((float) newHeight) / height;
        // CREATE A MATRIX FOR THE MANIPULATION
        Matrix matrix = new Matrix();
        // RESIZE THE BIT MAP
        matrix.postScale(scaleWidth, scaleHeight);

        // "RECREATE" THE NEW BITMAP
        Bitmap resizedBitmap = Bitmap.createBitmap(
                bm, 0, 0, width, height, matrix, false);
        bm.recycle();
        return resizedBitmap;
    }
    public static Bitmap decodeSampledBitmapFromResource(Resources res, ByteArrayInputStream resId,
                                                         int reqWidth, int reqHeight) {

        // First decode with inJustDecodeBounds=true to check dimensions
        final BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;
        BitmapFactory.decodeStream(resId, null, options);

        // Calculate inSampleSize
        int y = options.inSampleSize = calculateInSampleSize(options, reqWidth, reqHeight);
        options.inSampleSize = calculateInSampleSize(options, reqWidth, reqHeight);
        Log.d("AMAMA",""+y);

        // Decode bitmap with inSampleSize set
        options.inJustDecodeBounds = false;
        return BitmapFactory.decodeStream(resId,null,options);
        //return BitmapFactory.decodeResource(res, resId, options);
    }
    public static int calculateInSampleSize(
            BitmapFactory.Options options, int reqWidth, int reqHeight) {
        // Raw height and width of image
        final int height = options.outHeight;
        final int width = options.outWidth;
        int inSampleSize = 1;

        if (height > reqHeight || width > reqWidth) {

            final int halfHeight = height / 2;
            final int halfWidth = width / 2;

            // Calculate the largest inSampleSize value that is a power of 2 and keeps both
            // height and width larger than the requested height and width.
            while ((halfHeight / inSampleSize) > reqHeight
                    && (halfWidth / inSampleSize) > reqWidth) {
                inSampleSize *= 2;
            }
        }

        return inSampleSize;
    }
    private void relativeBackground(Uri uri) {
        try {
            bmp = MediaStore.Images.Media.getBitmap(ProfileView.this.getContentResolver(), uri);
        } catch (IOException e) {
            e.printStackTrace();
        }
      //  Bitmap blurredBitmap = blur(bmp);

      //  BitmapDrawable ar = new BitmapDrawable(blurredBitmap);
//        rv1.setBackground(ar);

    }
}
