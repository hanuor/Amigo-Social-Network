package chap.hanuor.com.chatapp;

import android.app.SearchManager;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.Point;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Display;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.DecelerateInterpolator;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.flaviofaria.kenburnsview.KenBurnsView;
import com.flaviofaria.kenburnsview.RandomTransitionGenerator;
import com.parse.FindCallback;
import com.parse.GetCallback;
import com.parse.GetDataCallback;
import com.parse.ParseException;
import com.parse.ParseFile;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.ParseUser;
import com.yayandroid.parallaxrecyclerview.ParallaxImageView;

import java.util.List;

public class SearchResultsActivity extends AppCompatActivity {
    TextView lab;
    RelativeLayout rew;
    ParseFile lmao;
ParallaxImageView iv;
    ImageView propic;
    String ds;
    TextView tv;
    ImageView ivy;
    Toolbar to;
    KenBurnsView kbv;
    View v;
    TextView display;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_results);
        tv = (TextView) findViewById(R.id.your);
        ivy = (ImageView) findViewById(R.id.icon);
        display = (TextView) findViewById(R.id.res);
        kbv = (KenBurnsView) findViewById(R.id.imageCover);
        AccelerateDecelerateInterpolator ACCELERATE_DECELERATE = new AccelerateDecelerateInterpolator();
        RandomTransitionGenerator generator = new RandomTransitionGenerator(10000, ACCELERATE_DECELERATE);
        kbv.setTransitionGenerator(generator); //set new transition on kbv
        to = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(to);
        getSupportActionBar().setDefaultDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        final Drawable upArrow = getResources().getDrawable(R.drawable.abc_ic_ab_back_mtrl_am_alpha);
        upArrow.setColorFilter(getResources().getColor(R.color.bg_glass), PorterDuff.Mode.SRC_ATOP);
        getSupportActionBar().setHomeAsUpIndicator(upArrow);
        lab = (TextView) findViewById(R.id.label);
        rew = (RelativeLayout) findViewById(R.id.relLay);
        v = findViewById(R.id.relLay);
        iv = (ParallaxImageView) findViewById(R.id.backgroundImage);
        propic = (ImageView) findViewById(R.id.imageView);

        handleIntent(getIntent());
        rew.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                ParseQuery<ParseUser> m= ParseUser.getQuery();
                m.whereEqualTo("username",ds);
                m.findInBackground(new FindCallback<ParseUser>() {
                    @Override
                    public void done(List<ParseUser> list, ParseException e) {

                        if(list.size()!=0) {
                            final String kami = list.get(0).getObjectId().toString();
                            if (kami.equals(ParseUser.getCurrentUser().getObjectId().toString())) {

                            }else{
                            ParseQuery<ParseObject> osum = ParseQuery.getQuery("FriendRequests");
                            //osum.whereEqualTo("IdCounter", "ActiveFriends");
                            osum.findInBackground(new FindCallback<ParseObject>() {
                                @Override
                                public void done(List<ParseObject> list, ParseException e) {
                                    if (list.size() != 0) {

                                        StringBuilder tr = new StringBuilder();

                                        tr.append(ParseUser.getCurrentUser().getObjectId().toString());
                                        tr.append(kami);
                                        final String ask = tr.toString();
                                        StringBuilder re = new StringBuilder();
                                        re.append(kami);
                                        re.append(ParseUser.getCurrentUser().getObjectId().toString());

                                        final String mska = re.toString();


                                        ParseQuery<ParseObject> osum = ParseQuery.getQuery("FriendRequests");
                                        osum.whereEqualTo("AmigoChatId", ask);
                                        osum.findInBackground(new FindCallback<ParseObject>() {
                                            @Override
                                            public void done(List<ParseObject> list, ParseException e) {
                                                if (list.size() != 0) {

                                                    ParseQuery<ParseObject> osum = ParseQuery.getQuery("FriendRequests");
                                                    osum.whereEqualTo("AmigoChatId", ask);
                                                    osum.whereEqualTo("IdCounter", "NotFriends");
                                                    osum.findInBackground(new FindCallback<ParseObject>() {
                                                        @Override
                                                        public void done(List<ParseObject> list, ParseException e) {
                                                            if (list.size() != 0) {

                                                                Snackbar.make(v, "You have already sent them a friend request!", Snackbar.LENGTH_SHORT).show();
                                                            } else {
                                                                Intent dexter = new Intent();
                                                                dexter.putExtra("ObjectID", kami);
                                                                dexter.setClass(SearchResultsActivity.this, FriendsProfileActivty.class);
                                                                startActivity(dexter);
                                                            }
                                                        }
                                                    });


                                                } else {
                                                    //search

                                                    ParseQuery<ParseObject> osumq = ParseQuery.getQuery("FriendRequests");
                                                    osumq.whereEqualTo("AmigoChatId", mska);
                                                    osumq.findInBackground(new FindCallback<ParseObject>() {
                                                        @Override
                                                        public void done(List<ParseObject> list, ParseException e) {
                                                            if (list.size() != 0) {
                                                                ParseQuery<ParseObject> osum = ParseQuery.getQuery("FriendRequests");
                                                                osum.whereEqualTo("AmigoChatId", mska);
                                                                osum.whereEqualTo("IdCounter", "NotFriends");
                                                                osum.findInBackground(new FindCallback<ParseObject>() {
                                                                    @Override
                                                                    public void done(List<ParseObject> list, ParseException e) {
                                                                        if (list.size() != 0) {

                                                                            Snackbar.make(v, "You have already sent them a friend request!", Snackbar.LENGTH_SHORT).show();
                                                                        } else {
                                                                            Intent dexter = new Intent();
                                                                            dexter.putExtra("ObjectID", kami);
                                                                            dexter.setClass(SearchResultsActivity.this, FriendsProfileActivty.class);
                                                                            startActivity(dexter);
                                                                        }
                                                                    }
                                                                });


                                                            } else {


                                                                AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(SearchResultsActivity.this);
                                                                alertDialogBuilder.setMessage("You are not friends with them. Send them a friend request to know more about them");

                                                                alertDialogBuilder.setPositiveButton("Yeah, sure", new DialogInterface.OnClickListener() {
                                                                    @Override
                                                                    public void onClick(DialogInterface arg0, int arg1) {
                                                                        //Toast.makeText(SearchResultsActivity.this,"You clicked yes button",Toast.LENGTH_LONG).show();
                                                                        StringBuilder h = new StringBuilder();

                                                                        h.append(ParseUser.getCurrentUser().getObjectId().toString());
                                                                        h.append(kami);
                                                                        String jo = h.toString();
                                                                        StringBuilder ho = new StringBuilder();

                                                                        ho.append(ParseUser.getCurrentUser().getObjectId().toString());
                                                                        ho.append(kami);
                                                                        ho.append("H");
                                                                        String joker = ho.toString();
                                                                        ParseObject frequest = new ParseObject("FriendRequests");
                                                                        frequest.put("AmigoChatId", jo);
                                                                        frequest.put("RequestReceiver", kami);
                                                                        frequest.put("RequestStack", joker);
                                                                        frequest.put("NotificationManager", joker);
                                                                        frequest.put("IdCounter", "NotFriends");
                                                                        frequest.saveInBackground();


                                                                    }
                                                                });

                                                                alertDialogBuilder.setNegativeButton("Not now", new DialogInterface.OnClickListener() {
                                                                    @Override
                                                                    public void onClick(DialogInterface dialog, int which) {
                                                                        finish();
                                                                    }
                                                                });

                                                                AlertDialog alertDialog = alertDialogBuilder.create();
                                                                alertDialog.show();

                                                            }
                                                        }
                                                    });

                                                }
                                            }
                                        });


                                    } else {
                                       /* */
                                    }
                                }
                            });
                        }
                        }
                    }
                });

            }

        });
    }
    public void getQuery(String qer){
ds = qer;
    }
  /*  @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_home, menu);
        SearchManager searchManager =
                (SearchManager) getSystemService(Context.SEARCH_SERVICE);
        final SearchView searchView =

                (SearchView) menu.findItem(R.id.searcher).getActionView();

        searchView.setSearchableInfo(
                searchManager.getSearchableInfo(getComponentName()));


        return true;
    }
*/
    @Override
    public boolean onOptionsItemSelected(MenuItem menuItem) {
        if (menuItem.getItemId() == android.R.id.home) {

            onBackPressed();
            overridePendingTransition(R.anim.right_in, R.anim.right_out);
        }
        return super.onOptionsItemSelected(menuItem);
    }
    @Override
    protected void onNewIntent(Intent intent) {
        handleIntent(intent);
    }

    private void handleIntent(Intent intent) {

        if (Intent.ACTION_SEARCH.equals(intent.getAction())) {
            final String query = intent.getStringExtra(SearchManager.QUERY);
            display.setText(query);
            getQuery(query);


            ParseQuery<ParseUser> m= ParseUser.getQuery();
            m.whereEqualTo("username",query);
         m.findInBackground(new FindCallback<ParseUser>() {
             @Override
             public void done(List<ParseUser> list, ParseException e) {
                 if(list.size()!=0){
                     String objectId = list.get(0).getObjectId().toString();
                     rew.setVisibility(View.VISIBLE);
                     Animation fadeIn = new AlphaAnimation(0, 1);
                     fadeIn.setInterpolator(new DecelerateInterpolator()); //add this
                     fadeIn.setDuration(1000);
                     lab.setAnimation(fadeIn);
                     lab.setText(query);
                     checkForLayout();

                     ParseQuery<ParseUser> query = ParseUser.getQuery();
                     query.getInBackground(objectId, new GetCallback<ParseUser>() {
                         @Override
                         public void done(ParseUser parseUser, ParseException e) {
                             if (e == null) {
                                 ParseFile picture = parseUser.getParseFile("photo");
                                 ParseFile cover = parseUser.getParseFile("CoverPicture");
                                 if (picture == null) {
                                     // no parseFile in column "profilePicture" in this user.
                                     return; // there will be no data to retrieve
                                 }
                                 try {
                                     cover.getDataInBackground(new GetDataCallback() {
                                         @Override
                                         public void done(byte[] bytes, ParseException e) {
                                             if(e == null){
                                                 if(bytes.length !=0){

                                                     Bitmap bmp = BitmapFactory.decodeByteArray(bytes, 0, bytes.length);
                                                     // vh.getImView().setImageBitmap(bmp);
                                                    // vh.getBackgroundImage().setImageBitmap(bmp);





                                                     Display display = getWindowManager().getDefaultDisplay();
                                                     Point size = new Point();
                                                     display.getSize(size);
                                                     int width = size.x;

                                                     Bitmap dstBmp;
                                                     if (bmp.getWidth() >= bmp.getHeight()){

                                                         dstBmp = Bitmap.createBitmap(
                                                                 bmp,
                                                                 bmp.getWidth()/2 - bmp.getHeight()/2,
                                                                 0,
                                                                 bmp.getHeight(),
                                                                 bmp.getHeight()
                                                         );

                                                     }else{

                                                         dstBmp = Bitmap.createBitmap(
                                                                 bmp,
                                                                 0,
                                                                 bmp.getHeight()/2 - bmp.getWidth()/2,
                                                                 bmp.getWidth(),
                                                                 bmp.getWidth()
                                                         );
                                                     }
                                                     int g = dstBmp.getHeight();
                                                     Bitmap nomans = Bitmap.createScaledBitmap(dstBmp,width,g,true);
                                                     Animation fadeIn = new AlphaAnimation(0, 1);
                                                     fadeIn.setInterpolator(new DecelerateInterpolator()); //add this
                                                     fadeIn.setDuration(1000);
                                                    iv.setAnimation(fadeIn);
                                                 iv.setImageBitmap(nomans);
                                                 }
                                                 else{
                                                     iv.setImageDrawable(getResources().getDrawable(R.drawable.abs));
                                                 }
                                             }

                                         }
                                     });
                                 } catch (Exception e1) {
                                     e1.printStackTrace();
                                 }
                                 picture.getDataInBackground(new GetDataCallback() {
                                     @Override
                                     public void done(byte[] data, ParseException e) {
                                         if (e == null) {
                                             if (data.length == 0) {
                                                // Toast.makeText(ListUsersActivity.this, " No file fetched", Toast.LENGTH_SHORT).show();
                                                 // data found, but nothing to extract. bad image or upload?
                                                 return; // failing out
                                             }

                                           //  Log.d("Photo", " " + objectIdOfUser);
                                             //Toast.makeText(ListUsersActivity.this, "Obejct Id fetched and photo" + objectIdOfUser, Toast.LENGTH_SHORT).show();
                                             Bitmap bmpw = BitmapFactory.decodeByteArray(data, 0, data.length);

                                             Animation fadeIn = new AlphaAnimation(0, 1);
                                             fadeIn.setInterpolator(new DecelerateInterpolator()); //add this
                                             fadeIn.setDuration(1000);
                                             propic.setAnimation(fadeIn);
                                             propic.setImageBitmap(bmpw);
                                             // SUCCESS
                                             // convert data and display in an imageview
                                         } else {
                                             // ParseFile contained no data. data not added to ParseFile?
                                         }
                                     }
                                 });
                             } else {
                                 // no users had this objectId
                             }
                         }
                     });

                 }
                 else{
                   Snackbar.make(v,"No such user exists!",Snackbar.LENGTH_SHORT).show();
                 }


         }
         });  //use the query to search
        }
    }

    private void checkForLayout() {
        ParseQuery<ParseObject> fad = ParseQuery.getQuery("SocialNotoriety");
        fad.whereEqualTo("IdCreator", ParseUser.getCurrentUser().getObjectId().toString());
        fad.findInBackground(new FindCallback<ParseObject>() {
            @Override
            public void done(List<ParseObject> list, ParseException e) {
                if(list.size()!=0){
                    //Entry is present

                    //Switch to layout two
                    String obj = list.get(0).getObjectId().toString();
                    String ver = list.get(0).get("VotedNotoriety").toString();
                    String Voters[] = ver.split("\\%3:11071995:11");
                    int size = Voters.length;

                   // recyclerView.setVisibility(View.GONE);
                    ///card.setVisibility(View.VISIBLE);
                    String voteini = list.get(0).get("UserChoice").toString();
                    if(voteini.equals("Nerd")){
                        tv.setText("Nerd");
                        tv.setTextColor(Color.parseColor("#9E9E9E"));
                        ivy.setImageDrawable(getResources().getDrawable(R.drawable.ic_stat_eyeglasses18));
                    }
                    else if(voteini.equals("Hot")){
                        tv.setTextColor(Color.parseColor("#E65100"));
                        tv.setText("Hot");
                        ivy.setImageDrawable(getResources().getDrawable(R.drawable.ic_stat_flames8));
                    }
                    else if(voteini.equals("Caring")){
                        tv.setTextColor(Color.parseColor("#AD1457"));
                        tv.setText("Caring");
                        ivy.setImageDrawable(getResources().getDrawable(R.drawable.ic_stat_small1));
                    }
                    else if(voteini.equals("Selfish")){
                        tv.setTextColor(Color.parseColor("#FFD600"));
                        tv.setText("Selfish");
                        ivy.setImageDrawable(getResources().getDrawable(R.drawable.ic_stat_speech155));
                    }
                    else{
                        tv.setTextColor(Color.parseColor("#00838F"));
                        tv.setText("Arcane");
                        ivy.setImageDrawable(getResources().getDrawable(R.drawable.ic_stat_floral115));
                    }
                    //Object Id is Fetched
                 //   pd.dismiss();
                }
                else{
                    //pd.dismiss();
                    //Let user enter the choice
                    //Switch to main Layout

                  //  recyclerView.setVisibility(View.VISIBLE);
                    //card.setVisibility(View.GONE);

                }
            }
        });

    }



}
