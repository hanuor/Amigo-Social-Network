package chap.hanuor.com.chatapp;

import android.animation.ValueAnimator;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.DecelerateInterpolator;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.flaviofaria.kenburnsview.KenBurnsView;
import com.parse.FindCallback;
import com.parse.GetCallback;
import com.parse.ParseException;
import com.parse.ParseFile;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.ParseUser;
import com.parse.SaveCallback;
import com.squareup.picasso.Picasso;
import com.yayandroid.parallaxrecyclerview.ParallaxRecyclerView;
import com.yayandroid.parallaxrecyclerview.ParallaxViewHolder;

import java.text.DecimalFormat;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class ParallelSocialNotoriety extends AppCompatActivity {

    String returnedUser;
    CardView card;
    ProgressDialog pd;
    Toolbar to;
    int f =0;
RelativeLayout mActivate;
    RelativeLayout viewprofile;
    RelativeLayout vote;
    ImageView iv;
    TextView textbio;

    CircleImageView userimage;
    TextView tvpub;
    int nerd,care,hot,sel,arc;
    TextView fullname,idtext;
    TextView np;
    int finalvalue;
    TextView overallrating;
    String names[] = {"Nerd","Hot","Caring","Selfish","Arcane"};
    String separator = "%3:11071995:11";
    int ico[] = {R.drawable.nerd,R.drawable.hot,R.drawable.caring,R.drawable.selfish,R.drawable.mystery};
    ParallaxRecyclerView recyclerView;
    KenBurnsView kbv;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_parallel_social_notoriety);

       textbio = (TextView) findViewById(R.id.textbio);
        iv = (ImageView) findViewById(R.id.icon);
        card = (CardView) findViewById(R.id.card1);
        userimage = (CircleImageView) findViewById(R.id.userImage);
        idtext = (TextView) findViewById(R.id.idtext);
        viewprofile = (RelativeLayout) findViewById(R.id.viewprofile);
        np = (TextView) findViewById(R.id.notorietypoints);
        overallrating = (TextView) findViewById(R.id.overall);
        //mActivate = (RelativeLayout) findViewById(R.id.Activate);
        tvpub = (TextView) findViewById(R.id.notorietyvalue);
        fullname = (TextView) findViewById(R.id.fullname);




        nerd=hot=sel=arc=care=0;



        Intent sk = getIntent();
        returnedUser = sk.getStringExtra("Object");
        pd = new ProgressDialog(this);
        pd.setMessage("Hold on a moment...");
        pd.show();
        to = (Toolbar) findViewById(R.id.toolbar);

        setSupportActionBar(to);
        getSupportActionBar().setDefaultDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        final Drawable upArrow = getResources().getDrawable(R.drawable.abc_ic_ab_back_mtrl_am_alpha);
        upArrow.setColorFilter(getResources().getColor(R.color.bg_glass), PorterDuff.Mode.SRC_ATOP);
        getSupportActionBar().setHomeAsUpIndicator(upArrow);
        recyclerView = (ParallaxRecyclerView) findViewById(R.id.usersListView);
        checkValue(returnedUser);

        final LinearLayoutManager layoutManager
                = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
        recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));

        recyclerView.setAdapter(new notorietyAdapter(getApplicationContext(),names,ico));
    }

    private void checkValue(String returnedUser) {

        //Display the profile picture

       // String objectId = ParseUser.getCurrentUser().getObjectId();
        ParseQuery<ParseUser> query = ParseUser.getQuery();
        query.getInBackground(returnedUser, new GetCallback<ParseUser>() {
            @Override
            public void done(ParseUser user, ParseException e) {
                if(e == null){
                    String fulln = user.get("FirstName").toString();
                    String bio = user.get("Tagline").toString();
                    String idname = user.getUsername();
                    //Set fullname
                    fullname.setText(""+fulln);
                    if(bio!=null){
                        textbio.setText(bio);
                    }else{
                        textbio.setText("...");
                    }

                    //Set Id
                    StringBuilder m = new StringBuilder();
                    m.append("@");
                    m.append(idname);
                    String idset = m.toString();
                    idtext.setText(""+idset);

                    ParseFile picture = user.getParseFile("photo");
                    if(picture != null){
                        String url = user.getParseFile("photo").getUrl();
                        Animation fadeIn = new AlphaAnimation(0, 1);
                        fadeIn.setInterpolator(new DecelerateInterpolator()); //add this
                        fadeIn.setDuration(1000);
                        userimage.setAnimation(fadeIn);
                        Picasso.with(getBaseContext()).load(url).into(userimage);
                    }

                }else{
                    e.printStackTrace();
                }
            }
        });

//Start

        try {
            ParseQuery<ParseObject> fad = ParseQuery.getQuery("SocialNotoriety");
            fad.whereEqualTo("IdCreator", returnedUser);
            fad.findInBackground(new FindCallback<ParseObject>() {
                @Override
                public void done(List<ParseObject> list, ParseException e) {
                    if (e == null) {


                        if (list.size() != 0) {
                            //Entry is present

                            //Switch to layout two
                            String notorietyp = list.get(0).get("notorietypoints").toString();

                            String obj = list.get(0).getObjectId().toString();
                            String mesId = list.get(0).get("VoterIds").toString();
                            Log.d("Sal2","Hi");
                            String VotersId[] = mesId.split("\\%3:11071995:11");
                            for(int y =0;y<VotersId.length;y++){
                                Log.d("Salinas",""+VotersId[y]);
                            }
                            for(int h= 0;h<VotersId.length;h++) {
                                Log.d("Salinas",""+VotersId[h]);
                                if(VotersId[h].equals(ParseUser.getCurrentUser().getObjectId())) {
                                    recyclerView.setVisibility(View.GONE);
                                    viewprofile.setVisibility(View.VISIBLE);
                                    pd.dismiss();
                                    f = 101;
                                    break;
                                }
                            }
                            Log.d("HGU",""+f);
                               if(f!=101) {
                                viewprofile.setVisibility(View.GONE);
                                recyclerView.setVisibility(View.VISIBLE);
                                pd.dismiss();

                            }
                                                         String ver = list.get(0).get("VotedNotoriety").toString();
                            String Voters[] = ver.split("\\%3:11071995:11");
                            int size = Voters.length;
                            for (int g = 0; g < size; g++) {

                                if (Voters[g].equals("Nerd")) {
                                    nerd++;
                                } else if (Voters[g].equals("Hot")) {
                                    hot++;
                                } else if (Voters[g].equals("Caring")) {
                                    care++;
                                } else if (Voters[g].equals("Selfish")) {
                                    sel++;
                                } else {
                                    arc++;
                                }

                            }
                            if (nerd > hot && nerd > care && nerd > arc && nerd > sel) {
                                StringBuilder m = new StringBuilder();
                                m.append("(" + nerd);
                                m.append(" out of " + Voters.length + ")");
                                String j = m.toString();
                                finalvalue = 1;
                               //count.setText(j);
                                tvpub.setText("Nerd");
                                tvpub.setTextColor(Color.parseColor("#9E9E9E"));
                                //ivpub.setImageDrawable(getResources().getDrawable(R.drawable.ic_stat_eyeglasses18));
                            } else if (hot > sel && hot > care && hot > arc && nerd < hot) {
                                StringBuilder m = new StringBuilder();
                                m.append("(" + hot);
                                m.append(" out of " + Voters.length + ")");
                                finalvalue = 2;
                                String j = m.toString();
                                tvpub.setTextColor(Color.parseColor("#E65100"));
                                ///count.setText(j);
                                tvpub.setText("Hot");
                                //ivpub.setImageDrawable(getResources().getDrawable(R.drawable.ic_stat_flames8));
                            } else if (care > sel && hot < care && care > arc && nerd < care) {
                                StringBuilder m = new StringBuilder();
                                m.append("(" + care);
                                m.append(" out of " + Voters.length + ")");
                                String j = m.toString();
                                finalvalue = 3;
                                tvpub.setTextColor(Color.parseColor("#AD1457"));
                               /// count.setText(j);
                                tvpub.setText("Caring");
                                //i/vpub.setImageDrawable(getResources().getDrawable(R.drawable.ic_stat_small1));
                            } else if (sel > hot && sel > care && sel > arc && nerd < sel) {
                                StringBuilder m = new StringBuilder();
                                m.append("(" + sel);
                                m.append(" out of " + Voters.length + ")");
                                String j = m.toString();
                               // count.setText(j);
                                finalvalue = 4;
                                tvpub.setTextColor(Color.parseColor("#FFD600"));
                                tvpub.setText("Selfish");
                               // ivpub.setImageDrawable(getResources().getDrawable(R.drawable.ic_stat_speech155));
                            } else if (arc > nerd && arc > care && arc > sel && arc > hot) {
                                StringBuilder m = new StringBuilder();
                                m.append("(" + arc);
                                m.append(" out of " + Voters.length + ")");
                                String j = m.toString();
                               // count.setText(j);
                                finalvalue = 5;
                                tvpub.setTextColor(Color.parseColor("#00838F"));
                                tvpub.setText("Arcane");
                                //ivpub.setImageDrawable(getResources().getDrawable(R.drawable.ic_stat_floral115));
                            } else if (nerd == hot || nerd == care || nerd == arc || nerd == sel) {
                                String mes = list.get(0).get("UserChoice").toString();
                                if (mes.equals("Nerd")) {
                                    StringBuilder m = new StringBuilder();
                                    m.append("(" + nerd);
                                    m.append(" out of " + Voters.length + ")");
                                    finalvalue = 1;
                                    String j = m.toString();
                                  //  count.setText(j);
                                    tvpub.setText("Nerd");
                                    tvpub.setTextColor(Color.parseColor("#9E9E9E"));
                                    ///ivpub.setImageDrawable(getResources().getDrawable(R.drawable.ic_stat_eyeglasses18));
                                } else {
                                    if (nerd == hot) {
                                        StringBuilder m = new StringBuilder();
                                        m.append("(" + hot);
                                        m.append(" out of " + Voters.length + ")");
                                        String j = m.toString();
                                       // count.setText(j);
                                        tvpub.setText("Hot");
                                        finalvalue = 2;
                                        tvpub.setTextColor(Color.parseColor("#E65100"));
                                        //ivpub.setImageDrawable(getResources().getDrawable(R.drawable.ic_stat_flames8));

                                    } else if (nerd == arc) {
                                        StringBuilder m = new StringBuilder();
                                        m.append("(" + arc);
                                        m.append(" out of " + Voters.length + ")");
                                        String j = m.toString();
                                        finalvalue = 5;
                                        ///count.setText(j);
                                        tvpub.setText("Arcane");
                                        tvpub.setTextColor(Color.parseColor("#00838F"));
                                        //ivpub.setImageDrawable(getResources().getDrawable(R.drawable.ic_stat_floral115));

                                    } else if (nerd == sel) {
                                        StringBuilder m = new StringBuilder();
                                        m.append("(" + nerd);
                                        m.append(" out of " + Voters.length + ")");
                                        String j = m.toString();
                                        //count.setText(j);
                                        tvpub.setText("Nerd");
                                        finalvalue = 1;
                                        tvpub.setTextColor(Color.parseColor("#9E9E9E"));
                                        //ivpub.setImageDrawable(getResources().getDrawable(R.drawable.ic_stat_eyeglasses18));

                                    } else if (nerd == care) {
                                        StringBuilder m = new StringBuilder();
                                        m.append("(" + care);
                                        m.append(" out of " + Voters.length + ")");
                                        String j = m.toString();
                                        finalvalue = 3;
                                        //count.setText(j);
                                        tvpub.setText("Caring");
                                        tvpub.setTextColor(Color.parseColor("#AD1457"));
                                        //ivpub.setImageDrawable(getResources().getDrawable(R.drawable.ic_stat_small1));

                                    }
                                }


                            }
                            //1.
                            else if (hot == nerd || hot == care || hot == arc || hot == sel) {
                                String mes = list.get(0).get("UserChoice").toString();
                                if (mes.equals("Hot")) {
                                    StringBuilder m = new StringBuilder();
                                    m.append("(" + hot);
                                    m.append(" out of " + Voters.length + ")");
                                    String j = m.toString();
                                    //count.setText(j);
                                    finalvalue = 2;
                                    tvpub.setText("Hot");

                                    tvpub.setTextColor(Color.parseColor("#E65100"));
                                    ///ivpub.setImageDrawable(getResources().getDrawable(R.drawable.ic_stat_flames8));
                                } else {
                                    if (hot == nerd) {
                                        StringBuilder m = new StringBuilder();
                                        m.append("(" + hot);
                                        m.append(" out of " + Voters.length + ")");
                                        String j = m.toString();
                                       // count.setText(j);
                                        tvpub.setText("Hot");
                                        finalvalue = 2;
                                        tvpub.setTextColor(Color.parseColor("#E65100"));
                                       // ivpub.setImageDrawable(getResources().getDrawable(R.drawable.ic_stat_flames8));

                                    } else if (hot == arc) {
                                        StringBuilder m = new StringBuilder();
                                        m.append("(" + arc);
                                        m.append(" out of " + Voters.length + ")");
                                        String j = m.toString();
                                        //count.setText(j);
                                        tvpub.setText("Arcane");
                                        finalvalue = 5;
                                        tvpub.setTextColor(Color.parseColor("#00838F"));
                                        //ivpub.setImageDrawable(getResources().getDrawable(R.drawable.ic_stat_floral115));

                                    } else if (hot == sel) {
                                        StringBuilder m = new StringBuilder();
                                        m.append("(" + hot);
                                        m.append(" out of " + Voters.length + ")");
                                        String j = m.toString();
                                        //count.setText(j);
                                        tvpub.setText("Hot");
                                        finalvalue = 2;
                                        tvpub.setTextColor(Color.parseColor("#E65100"));
                                        //ivpub.setImageDrawable(getResources().getDrawable(R.drawable.ic_stat_flames8));

                                    } else if (hot == care) {
                                        StringBuilder m = new StringBuilder();
                                        m.append("(" + care);
                                        m.append(" out of " + Voters.length + ")");
                                        String j = m.toString();
                                       // count.setText(j);
                                        tvpub.setText("Caring");
                                        finalvalue = 3;
                                        tvpub.setTextColor(Color.parseColor("#AD1457"));
                                        //ivpub.setImageDrawable(getResources().getDrawable(R.drawable.ic_stat_small1));

                                    }
                                }


                            }
                            //2.
                            else if (care == hot || care == nerd || care == arc || care == sel) {
                                String mes = list.get(0).get("UserChoice").toString();
                                if (mes.equals("Caring")) {
                                    StringBuilder m = new StringBuilder();
                                    m.append("(" + care);
                                    m.append(" out of " + Voters.length + ")");
                                    String j = m.toString();
                             //       count.setText(j);
                                    finalvalue = 3;
                                    tvpub.setText("Caring");
                                    tvpub.setTextColor(Color.parseColor("#AD1457"));
                               //     ivpub.setImageDrawable(getResources().getDrawable(R.drawable.ic_stat_small1));
                                } else {
                                    if (care == hot) {
                                        StringBuilder m = new StringBuilder();
                                        m.append("(" + care);
                                        m.append(" out of " + Voters.length + ")");
                                        String j = m.toString();
                                 //       count.setText(j);
                                        finalvalue = 3;
                                        tvpub.setText("Caring");
                                        tvpub.setTextColor(Color.parseColor("#AD1457"));
                                   //     ivpub.setImageDrawable(getResources().getDrawable(R.drawable.ic_stat_small1));

                                    } else if (nerd == arc) {
                                        StringBuilder m = new StringBuilder();
                                        m.append("(" + arc);
                                        m.append(" out of " + Voters.length + ")");
                                        String j = m.toString();
                                     //   count.setText(j);
                                        finalvalue = 5;
                                        tvpub.setText("Arcane");
                                        tvpub.setTextColor(Color.parseColor("#00838F"));
                                       // ivpub.setImageDrawable(getResources().getDrawable(R.drawable.ic_stat_floral115));

                                    } else if (care == sel) {
                                        StringBuilder m = new StringBuilder();
                                        m.append("(" + care);
                                        m.append(" out of " + Voters.length + ")");
                                        String j = m.toString();
                                        //count.setText(j);
                                        finalvalue = 3;
                                        tvpub.setText("Caring");
                                        tvpub.setTextColor(Color.parseColor("#AD1457"));
                                        //ivpub.setImageDrawable(getResources().getDrawable(R.drawable.ic_stat_small1));
                                    } else if (care == nerd) {
                                        StringBuilder m = new StringBuilder();
                                        m.append("(" + care);
                                        m.append(" out of " + Voters.length + ")");
                                        String j = m.toString();
                                       // count.setText(j);
                                        finalvalue = 3;
                                        tvpub.setText("Caring");
                                        tvpub.setTextColor(Color.parseColor("#AD1457"));
//                                        ivpub.setImageDrawable(getResources().getDrawable(R.drawable.ic_stat_small1));

                                    }
                                }


                            }
                            //3.
                            else if (arc == hot || arc == care || arc == nerd || arc == sel) {
                                String mes = list.get(0).get("UserChoice").toString();
                                if (mes.equals("Arcane")) {
                                    StringBuilder m = new StringBuilder();
                                    m.append("(" + arc);
                                    m.append(" out of " + Voters.length + ")");
                                    String j = m.toString();
                                    finalvalue = 5;
  //                                  count.setText(j);
                                    tvpub.setText("Arcane");

                                    tvpub.setTextColor(Color.parseColor("#00838F"));
    //                                ivpub.setImageDrawable(getResources().getDrawable(R.drawable.ic_stat_floral115));
                                } else {
                                    if (arc == hot) {
                                        StringBuilder m = new StringBuilder();
                                        m.append("(" + arc);
                                        m.append(" out of " + Voters.length + ")");
                                        String j = m.toString();
                                        finalvalue = 5;
      //                                  count.setText(j);
                                        tvpub.setText("Arcane");
                                        tvpub.setTextColor(Color.parseColor("#00838F"));
        //                                ivpub.setImageDrawable(getResources().getDrawable(R.drawable.ic_stat_floral115));
                                    } else if (arc == nerd) {
                                        StringBuilder m = new StringBuilder();
                                        m.append("(" + arc);
                                        m.append(" out of " + Voters.length + ")");
                                        String j = m.toString();
          //                              count.setText(j);
                                        finalvalue = 5;
                                        tvpub.setText("Arcane");
                                        tvpub.setTextColor(Color.parseColor("#00838F"));
            //                            ivpub.setImageDrawable(getResources().getDrawable(R.drawable.ic_stat_floral115));

                                    } else if (arc == sel) {
                                        StringBuilder m = new StringBuilder();
                                        m.append("(" + arc);
                                        m.append(" out of " + Voters.length + ")");
                                        String j = m.toString();
              //                          count.setText(j);
                                        finalvalue = 5;
                                        tvpub.setText("Arcane");
                                        tvpub.setTextColor(Color.parseColor("#00838F"));
                //                        ivpub.setImageDrawable(getResources().getDrawable(R.drawable.ic_stat_floral115));
                                    } else if (arc == care) {
                                        StringBuilder m = new StringBuilder();
                                        m.append("(" + arc);
                                        m.append(" out of " + Voters.length + ")");
                                        String j = m.toString();
                  //                      count.setText(j);
                                        finalvalue = 5;
                                        tvpub.setText("Arcane");
                                        tvpub.setTextColor(Color.parseColor("#00838F"));
                    //                    ivpub.setImageDrawable(getResources().getDrawable(R.drawable.ic_stat_floral115));
                                    }
                                }


                            }
                            //4.
                            else if (sel == hot || sel == care || sel == arc || sel == nerd) {
                                String mes = list.get(0).get("UserChoice").toString();
                                if (mes.equals("Selfish")) {
                                    StringBuilder m = new StringBuilder();
                                    m.append("(" + sel);
                                    finalvalue = 4;
                                    m.append(" out of " + Voters.length + ")");
                                    String j = m.toString();
                      //              count.setText(j);
                                    tvpub.setText("Selfish");
                                    tvpub.setTextColor(Color.parseColor("#FFD600"));
                        //            ivpub.setImageDrawable(getResources().getDrawable(R.drawable.ic_stat_speech155));
                                } else {
                                    if (sel == hot) {
                                        StringBuilder m = new StringBuilder();
                                        m.append("(" + hot);
                                        m.append(" out of " + Voters.length + ")");
                                        String j = m.toString();
                          //              count.setText(j);
                                        finalvalue = 2;
                                        tvpub.setText("Hot");
                                        tvpub.setTextColor(Color.parseColor("#E65100"));
                            //            ivpub.setImageDrawable(getResources().getDrawable(R.drawable.ic_stat_flames8));

                                    } else if (sel == arc) {
                                        StringBuilder m = new StringBuilder();
                                        m.append("(" + arc);
                                        m.append(" out of " + Voters.length + ")");
                                        String j = m.toString();
                                        finalvalue = 5;
                              //          count.setText(j);
                                        tvpub.setText("Arcane");
                                        tvpub.setTextColor(Color.parseColor("#00838F"));
                                //        ivpub.setImageDrawable(getResources().getDrawable(R.drawable.ic_stat_floral115));

                                    } else if (sel == nerd) {
                                        StringBuilder m = new StringBuilder();
                                        m.append("(" + nerd);
                                        m.append(" out of " + Voters.length + ")");
                                        String j = m.toString();
                                  //      count.setText(j);
                                        tvpub.setText("Nerd");
                                        finalvalue = 1;
                                        tvpub.setTextColor(Color.parseColor("#9E9E9E"));
//                                        ivpub.setImageDrawable(getResources().getDrawable(R.drawable.ic_stat_eyeglasses18));

                                    } else if (sel == care) {
                                        StringBuilder m = new StringBuilder();
                                        m.append("(" + care);
                                        m.append(" out of " + Voters.length + ")");
                                        String j = m.toString();
  //                                      count.setText(j);
                                        tvpub.setText("Caring");
                                        finalvalue = 3;
                                        tvpub.setTextColor(Color.parseColor("#AD1457"));
    //                                    ivpub.setImageDrawable(getResources().getDrawable(R.drawable.ic_stat_small1));

                                    }
                                }


                            }


                            //recyclerView.setVisibility(View.GONE);
                            //card.setVisibility(View.VISIBLE);

                            //Object Id is Fetched
                            notorietypoints(notorietyp);
                            pd.dismiss();
                        } else {
                            pd.dismiss();
                            //Let user enter the choice
                            //Switch to main Layout

                            //recyclerView.setVisibility(View.VISIBLE);
                            //card.setVisibility(View.GONE);

                        }
                    }else{

                        e.printStackTrace();
                    }
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }




    }
    //Set notoriety points
    private void notorietypoints(String pointsre) {

        double p = Double.parseDouble(pointsre);


        if(finalvalue == 1){
            //nerd
            float points = 6.1f;
            float setval = points  * 7.3f;
            // np.setText(""+setval);

            startCountAnimation(setval,p);
        }
        else if(finalvalue == 2 ){
            //hot
            float points = 7.1f;

            float setval = points  * 7.3f;
            startCountAnimation(setval,p);
            //np.setText(""+setval);

        }else if(finalvalue == 3){
//caring
            float points = 6.6f;

            float setval = points  * 7.3f;
            startCountAnimation(setval,p);

        }else if(finalvalue == 4){
//selfish
            float points = 4.7f;

            float setval = points  * 7.3f;
            startCountAnimation(setval,p);

        }else if(finalvalue == 5){
            //arcane
            float points = 7.4f;

            float setval = points  * 7.3f;
//            np.setText(""+setval);
            startCountAnimation(setval,p);
        }
    }

    private void startCountAnimation(float terminate, Double po) {

        final double te =  terminate;
        double difft = te- po;
        ParseQuery<ParseObject> fad = ParseQuery.getQuery("SocialNotoriety");
        fad.whereEqualTo("IdCreator", returnedUser);
        fad.findInBackground(new FindCallback<ParseObject>() {
            @Override
            public void done(List<ParseObject> list, ParseException e) {
                if(list.size()!=0){
                    String buf = list.get(0).getObjectId().toString();
                    ParseQuery<ParseObject> fa = ParseQuery.getQuery("SocialNotoriety");
                    fa.getInBackground(buf, new GetCallback<ParseObject>() {
                        @Override
                        public void done(ParseObject parseObject, ParseException e) {
                            StringBuilder m = new StringBuilder();
                            m.append(""+te);
                            String bn = m.toString();

                            parseObject.put("notorietypoints",bn);
                            parseObject.saveInBackground();
                        }
                    });
                }
            }
        });

        ValueAnimator animator = new ValueAnimator();

        animator.setFloatValues( 0.0f,terminate);
        //animator.setObjectValues(0, terminate);
        animator.setDuration(1000);
        final Animation fadeIn2 = new AlphaAnimation(0, 1);
        fadeIn2.setInterpolator(new DecelerateInterpolator()); //add this
        fadeIn2.setDuration(1000);
        animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            public void onAnimationUpdate(ValueAnimator animation) {
                np.setAnimation(fadeIn2);

                np.setText("" + (float) animation.getAnimatedValue());
            }
        });
        animator.start();
        Animation fadeIn = new AlphaAnimation(0, 1);
        fadeIn.setInterpolator(new DecelerateInterpolator()); //add this
        fadeIn.setDuration(1000);
        DecimalFormat twoDForm = new DecimalFormat("#.##");
        double ski = Double.valueOf(twoDForm.format(difft));
        setOverallRating(terminate);
    }

    public void setOverallRating(final float ter){
//claculate overall rating

//starts from 0
        ParseQuery<ParseObject> fad = ParseQuery.getQuery("AmigoPosts");
        fad.whereEqualTo("CreatorId", returnedUser);
        fad.findInBackground(new FindCallback<ParseObject>() {
            @Override
            public void done(List<ParseObject> list, ParseException e) {
                if(list.size()!=0){
                    String overallLikes = list.get(0).get("LikesCounter").toString();
                    String overallShares = list.get(0).get("SharesCounter").toString();
                    String overallComms = list.get(0).get("CommentsCounter").toString();
                    String overalls = list.get(0).get("overall").toString();
                    String immershal[] = overalls.split("\\s+");
                    float ig = Float.parseFloat(immershal[0]);
                    float ol = Float.parseFloat(overallLikes);
                    float oc = Float.parseFloat(overallComms);
                    float os = Float.parseFloat(overallShares);
                    float overall = (ol) + (oc) + (os);

                    final float finval = (0.11f * ter) + overall;
                    double difft = finval - ig;
                    ParseQuery<ParseObject> fad = ParseQuery.getQuery("AmigoPosts");
                    fad.whereEqualTo("CreatorId", returnedUser);
                    fad.findInBackground(new FindCallback<ParseObject>() {
                        @Override
                        public void done(List<ParseObject> list, ParseException e) {
                            if(list.size()!=0){
                                String buf = list.get(0).getObjectId().toString();
                                ParseQuery<ParseObject> fa = ParseQuery.getQuery("AmigoPosts");
                                fa.getInBackground(buf, new GetCallback<ParseObject>() {
                                    @Override
                                    public void done(ParseObject parseObject, ParseException e) {
                                        StringBuilder m = new StringBuilder();
                                        m.append(""+finval+" "+returnedUser);
                                        String bn = m.toString();

                                        parseObject.put("overall",bn);
                                        parseObject.saveInBackground();
                                    }
                                });
                            }
                        }
                    });


                    //   double difft = ;
                    //overallrating.setText(""+finval);

                    double roundOff = (double) Math.round(finval * 100) / 100;

                    ValueAnimator animator = new ValueAnimator();

                    animator.setFloatValues( 0.0f,finval);
                    //animator.setObjectValues(0, terminate);
                    animator.setDuration(1000);
                    animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
                        public void onAnimationUpdate(ValueAnimator animation) {
                            float m =(float) animation.getAnimatedValue();
                            double roundOff = (double) Math.round(m * 100) / 100;
                            if(roundOff <0){
                                overallrating.setTextColor(Color.parseColor("#B71C1C"));
                            }
                            else if(roundOff< 10.00 && roundOff >=0){
                                overallrating.setTextColor(Color.parseColor("#BCAAA4"));
                            }else if(roundOff >=10.00 && roundOff <20.00){
                                overallrating.setTextColor(Color.parseColor("#FFF59D"));
                            }else if(roundOff >=20.00 && roundOff < 45.00){
                                overallrating.setTextColor(Color.parseColor("#FFE082"));
                            }else if(roundOff >= 45.00 && roundOff < 75){
                                overallrating.setTextColor(Color.parseColor("#FFCC80"));
                            }else if(roundOff >= 75){
                                overallrating.setTextColor(Color.parseColor("#C5E1A5"));
                            }
                            overallrating.setText("" + roundOff );
                        }
                    });
                    animator.start();
                    DecimalFormat twoDForm = new DecimalFormat("#.##");
                    double ski = Double.valueOf(twoDForm.format(difft));

                }
            }
        });


    }



    private class notorietyAdapter extends RecyclerView.Adapter <notorietyAdapter.ViewHolder>{

        String esk[];
        int con[];
        public notorietyAdapter(Context applicationContext, String esk[], int con[]) {
            this.esk = esk;
            this.con = con;
        }

        @Override
        public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.notoriety_single,parent,false);
            return new ViewHolder(v);
        }

        @Override
        public void onBindViewHolder(ViewHolder holder, final int position) {

            holder.getBackgroundImage().setImageResource(con[position%con.length]);
            holder.getTextView().setText(esk[position]);


            holder.getBackgroundImage().setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    final ProgressDialog mo = new ProgressDialog(ParallelSocialNotoriety.this);
                    mo.setMessage("Submitting your vote...");
                    mo.show();
                    ParseQuery<ParseObject> fad = ParseQuery.getQuery("SocialNotoriety");
                    fad.whereEqualTo("IdCreator",returnedUser);
                    fad.findInBackground(new FindCallback<ParseObject>() {
                        @Override
                        public void done(List<ParseObject> list, ParseException e) {
                            if(e == null){
                                if(list.size()!=0){
                                    String fetObj = list.get(0).getObjectId().toString();
                                    String Vote = list.get(0).get("VotedNotoriety").toString();
                                    String VoterIds = list.get(0).get("VoterIds").toString();
                                    StringBuilder hu = new StringBuilder();
                                    hu.append(Vote);
                                    hu.append(esk[position]);
                                    hu.append(separator);
                                    final String fi = hu.toString();
                                    StringBuilder jo = new StringBuilder();
                                    jo.append(VoterIds);
                                    jo.append(ParseUser.getCurrentUser().getObjectId().toString());
                                    jo.append(separator);
                                    final String as = jo.toString();
                                    ParseQuery<ParseObject> lor = ParseQuery.getQuery("SocialNotoriety");
                                    lor.getInBackground(fetObj, new GetCallback<ParseObject>() {
                                        @Override
                                        public void done(ParseObject parseObject, ParseException e) {
                                            parseObject.put("VotedNotoriety",fi);
                                            parseObject.put("VoterIds",as);
                                            parseObject.saveInBackground(new SaveCallback() {
                                                @Override
                                                public void done(ParseException e) {
                                                    mo.dismiss();
                                                    Toast.makeText(ParallelSocialNotoriety.this,"Submitted successfully",Toast.LENGTH_SHORT).show();
                                                    finish();
                                                }
                                            });

                                        }
                                    });

                                }
                            }
                        }
                    });

                }
            });

            holder.getBackgroundImage().reuse();
        }

        @Override
        public int getItemCount() {
            return esk.length;
        }
        public  class ViewHolder extends ParallaxViewHolder {

            private final TextView textView;
            public ViewHolder(View v) {
                super(v);
                textView = (TextView) v.findViewById(R.id.setText);

            }

            @Override
            public int getParallaxImageId() {
                return R.id.backgroundImage;
            }

            public TextView getTextView() {
                return textView;
            }
        }
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem menuItem) {
        if (menuItem.getItemId() == android.R.id.home) {
            onBackPressed();
        }
        return super.onOptionsItemSelected(menuItem);
    }

}
