package chap.hanuor.com.chatapp;

import android.animation.ValueAnimator;
import android.app.ProgressDialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Handler;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.DecelerateInterpolator;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextSwitcher;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ViewSwitcher;

import com.facebook.shimmer.ShimmerFrameLayout;
import com.flaviofaria.kenburnsview.KenBurnsView;
import com.flaviofaria.kenburnsview.RandomTransitionGenerator;
import com.parse.FindCallback;
import com.parse.GetCallback;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.ParseUser;
import com.parse.SaveCallback;
import com.yayandroid.parallaxrecyclerview.ParallaxRecyclerView;
import com.yayandroid.parallaxrecyclerview.ParallaxViewHolder;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class SocialNotorietyActivity extends AppCompatActivity {

    KenBurnsView kbv;
    ParallaxRecyclerView recyclerView;
    Toolbar to;
    CardView card;
    RelativeLayout mActivate;
    ImageView iv;
    TextView tv;
    ImageView ivpub;
    ShimmerFrameLayout sh;
    TextView overallrating;
    TextView count;
    ImageView downanim;
    TextView diff;
    TextView ti;
    ImageView immy;
    TextView tvpub;
    ShimmerFrameLayout shimmy;
    ArrayList<String> Switchers;
    int nerd,care,hot,sel,arc;
    TextView np;
 int begin;
    ImageView arrow;
    final double notorietyp = 7.3;
    int finalvalue;
    int currentIndex=-1;
    TextSwitcher mSwitcher;
    TransparentProgressDialog pd;
    String names[] = {"Nerd","Hot","Caring","Selfish","Arcane"};
    String separator = "%3:11071995:11";
    int ico[] = {R.drawable.nerd,R.drawable.hot,R.drawable.caring,R.drawable.selfish,R.drawable.mystery};
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_social_notoriety);

        sh =
                (ShimmerFrameLayout) findViewById(R.id.shimmerpoints);
        shimmy =
                (ShimmerFrameLayout) findViewById(R.id.shimmy);
        mSwitcher = (TextSwitcher) findViewById(R.id.ts);

        Switchers = new ArrayList<String>();
        Switchers.add(0,"Your public vote defines your personality");
        Switchers.add(1,"Arcane - You are not a blabbermouth. People literally crave for your presence. Personalities like this are feared as well as loved.");
        Switchers.add(2,"Hot - Yes, that's right. You are categorized as a sexy being. Your physical appearance is like 'WOW'. People love you for your hotness, and maybe that's why everyone wishes to have a personality like you.");
        Switchers.add(3,"Caring - You are good natured, polite and do your work conscientiously. You are born talented, helping and supporting others is like your daily routine. Such quality and endangered traits make you the most lovable and helpful personalities.");
        Switchers.add(4,"Selfish - In general people don't like you. You are self-centered and maybe greedy. There's not much to say, the word is self-explanatory. Maybe these type of personalities should bring a change in their attitude.");
        Switchers.add(5,"Nerd - You are one of a kind. In general people consider you as an intelligent being and maybe an introvert too. You live in your own imaginary world and maybe that's why you are considered as a trendsetter.");
       begin = Switchers.size();
        tv = (TextView) findViewById(R.id.your);
        iv = (ImageView) findViewById(R.id.icon);
        diff = (TextView) findViewById(R.id.diff);
        //mActivate = (RelativeLayout) findViewById(R.id.Activate);
        count = (TextView) findViewById(R.id.countpub);
        arrow = (ImageView) findViewById(R.id.arrow);
        np = (TextView) findViewById(R.id.np);
        tvpub = (TextView) findViewById(R.id.publicyour);
        ti = (TextView) findViewById(R.id.diffy);
       immy = (ImageView) findViewById(R.id.arrowoverall);
        overallrating = (TextView) findViewById(R.id.overallrating);
      //  ((AnimationDrawable) downanim.getBackground()).start();
        // downanim.setImageResource(R.drawable.down_anim);

        ivpub = (ImageView) findViewById(R.id.pubicon);
        nerd=hot=sel=arc=care=0;
        card = (CardView) findViewById(R.id.card1);
        pd = new TransparentProgressDialog(SocialNotorietyActivity.this,R.drawable.ic_amigo_icon);
        pd.show();
        to = (Toolbar) findViewById(R.id.toolbar);
        to.setTitle("Settings");
        setSupportActionBar(to);
        getSupportActionBar().setDefaultDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        final Drawable upArrow = getResources().getDrawable(R.drawable.abc_ic_ab_back_mtrl_am_alpha);
        upArrow.setColorFilter(getResources().getColor(R.color.bg_glass), PorterDuff.Mode.SRC_ATOP);
        getSupportActionBar().setHomeAsUpIndicator(upArrow);
        recyclerView = (ParallaxRecyclerView) findViewById(R.id.usersListView);
        checkForLayout();

        kbv = (KenBurnsView) findViewById(R.id.imageCover);
        AccelerateDecelerateInterpolator ACCELERATE_DECELERATE = new AccelerateDecelerateInterpolator();
        RandomTransitionGenerator generator = new RandomTransitionGenerator(10000, ACCELERATE_DECELERATE);
        kbv.setTransitionGenerator(generator); //set new transition on kbv
        final LinearLayoutManager layoutManager
                = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
        recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));

        recyclerView.setAdapter(new notorietyAdapter(getApplicationContext(),names,ico));
        mSwitcher.setFactory(new ViewSwitcher.ViewFactory() {
            @Override
            public View makeView() {
                TextView myText = new TextView(SocialNotorietyActivity.this);
                //myText.setGravity(Gravity.TOP | Gravity.CENTER_HORIZONTAL);
                myText.setTextSize(13);
                myText.setTextColor(Color.GRAY);
                return myText;
            }
        });
        Animation fadeIn = new AlphaAnimation(0, 1);
        fadeIn.setInterpolator(new DecelerateInterpolator()); //add this
        fadeIn.setDuration(2000);

        Animation fadeOut = new AlphaAnimation(1, 0);
        fadeOut.setInterpolator(new DecelerateInterpolator()); //add this
        fadeOut.setDuration(3000);
        mSwitcher.setInAnimation(fadeIn);
        mSwitcher.setOutAnimation(fadeOut);

        final Handler h = new Handler();
        final int delay = 9999; //milliseconds

        h.postDelayed(new Runnable(){
            public void run(){
                //do something
                Random r = new Random();
                int i1 = r.nextInt(6 - 0) + 0;
                mSwitcher.setText(Switchers.get(i1));
                h.postDelayed(this, delay);
            }
        }, delay);
    }
    public void setOverallRating(final float ter){
//claculate overall rating

//starts from 0
        shimmy.setRepeatDelay(1000);
        shimmy.setBaseAlpha(0.0f);
        shimmy.startShimmerAnimation();
        final double likepoints = 0.03;
        final double commentpoints = 0.04;
        double dislikepoints = 0.02;
        final double sharepoints = 0.07;
        ParseQuery<ParseObject> fad = ParseQuery.getQuery("AmigoPosts");
        fad.whereEqualTo("CreatorId", ParseUser.getCurrentUser().getObjectId().toString());
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
                    fad.whereEqualTo("CreatorId", ParseUser.getCurrentUser().getObjectId().toString());
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
                                        m.append(""+finval+" "+ParseUser.getCurrentUser().getObjectId().toString());
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
                    if(difft>0){
                        immy.setImageDrawable(getResources().getDrawable(R.drawable.ic_stat_direction200));
                       ti.setText("+"+ski+"%");

                    }else if(difft<0){
                        ti.setText(""+ski+"%");
                        immy.setImageDrawable(getResources().getDrawable(R.drawable.ic_stat_directional25));
                    }else{
                       ti.setText("");
                       // immy.setImageDrawable(getResources().getDrawable(R.drawable.ic_stat_equal2));
                    }

                }
            }
        });


    }
    private void startCountAnimation(float terminate, Double po) {

        sh.setRepeatDelay(1000);
        sh.setBaseAlpha(0.0f);
        sh.startShimmerAnimation();
        final double te =  terminate;
        double difft = te- po;
        ParseQuery<ParseObject> fad = ParseQuery.getQuery("SocialNotoriety");
        fad.whereEqualTo("IdCreator", ParseUser.getCurrentUser().getObjectId().toString());
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
        if(difft>0){
            arrow.setImageDrawable(getResources().getDrawable(R.drawable.ic_stat_direction200));
            diff.setAnimation(fadeIn);
            diff.setText("+"+ski+"%");

        }else if(difft<0){
            diff.setAnimation(fadeIn);
            diff.setText(""+ski+"%");
            arrow.setImageDrawable(getResources().getDrawable(R.drawable.ic_stat_directional25));
        }else{
            diff.setAnimation(fadeIn);
            diff.setText("");
           // arrow.setImageDrawable(getResources().getDrawable(R.drawable.ic_stat_equal2));
        }
       // ((AnimationDrawable) upanim.getBackground()).start();
        setOverallRating(terminate);
    }


    private void checkForLayout() {

        try {
            ParseQuery<ParseObject> fad = ParseQuery.getQuery("SocialNotoriety");
            fad.whereEqualTo("IdCreator", ParseUser.getCurrentUser().getObjectId().toString());
            fad.findInBackground(new FindCallback<ParseObject>() {
                @Override
                public void done(List<ParseObject> list, ParseException e) {
                    if (e == null) {


                        if (list.size() != 0) {
                            //Entry is present

                            //Switch to layout two
                            String notorietyp = list.get(0).get("notorietypoints").toString();

                            String obj = list.get(0).getObjectId().toString();
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
                                count.setText(j);
                                tvpub.setText("Nerd");
                                tvpub.setTextColor(Color.parseColor("#9E9E9E"));
                                ivpub.setImageDrawable(getResources().getDrawable(R.drawable.ic_stat_eyeglasses18));
                            } else if (hot > sel && hot > care && hot > arc && nerd < hot) {
                                StringBuilder m = new StringBuilder();
                                m.append("(" + hot);
                                m.append(" out of " + Voters.length + ")");
                                finalvalue = 2;
                                String j = m.toString();
                                tvpub.setTextColor(Color.parseColor("#E65100"));
                                count.setText(j);
                                tvpub.setText("Hot");
                                ivpub.setImageDrawable(getResources().getDrawable(R.drawable.ic_stat_flames8));
                            } else if (care > sel && hot < care && care > arc && nerd < care) {
                                StringBuilder m = new StringBuilder();
                                m.append("(" + care);
                                m.append(" out of " + Voters.length + ")");
                                String j = m.toString();
                                finalvalue = 3;
                                tvpub.setTextColor(Color.parseColor("#AD1457"));
                                count.setText(j);
                                tvpub.setText("Caring");
                                ivpub.setImageDrawable(getResources().getDrawable(R.drawable.ic_stat_small1));
                            } else if (sel > hot && sel > care && sel > arc && nerd < sel) {
                                StringBuilder m = new StringBuilder();
                                m.append("(" + sel);
                                m.append(" out of " + Voters.length + ")");
                                String j = m.toString();
                                count.setText(j);
                                finalvalue = 4;
                                tvpub.setTextColor(Color.parseColor("#FFD600"));
                                tvpub.setText("Selfish");
                                ivpub.setImageDrawable(getResources().getDrawable(R.drawable.ic_stat_speech155));
                            } else if (arc > nerd && arc > care && arc > sel && arc > hot) {
                                StringBuilder m = new StringBuilder();
                                m.append("(" + arc);
                                m.append(" out of " + Voters.length + ")");
                                String j = m.toString();
                                count.setText(j);
                                finalvalue = 5;
                                tvpub.setTextColor(Color.parseColor("#00838F"));
                                tvpub.setText("Arcane");
                                ivpub.setImageDrawable(getResources().getDrawable(R.drawable.ic_stat_floral115));
                            } else if (nerd == hot || nerd == care || nerd == arc || nerd == sel) {
                                String mes = list.get(0).get("UserChoice").toString();
                                if (mes.equals("Nerd")) {
                                    StringBuilder m = new StringBuilder();
                                    m.append("(" + nerd);
                                    m.append(" out of " + Voters.length + ")");
                                    finalvalue = 1;
                                    String j = m.toString();
                                    count.setText(j);
                                    tvpub.setText("Nerd");
                                    tvpub.setTextColor(Color.parseColor("#9E9E9E"));
                                    ivpub.setImageDrawable(getResources().getDrawable(R.drawable.ic_stat_eyeglasses18));
                                } else {
                                    if (nerd == hot) {
                                        StringBuilder m = new StringBuilder();
                                        m.append("(" + hot);
                                        m.append(" out of " + Voters.length + ")");
                                        String j = m.toString();
                                        count.setText(j);
                                        tvpub.setText("Hot");
                                        finalvalue = 2;
                                        tvpub.setTextColor(Color.parseColor("#E65100"));
                                        ivpub.setImageDrawable(getResources().getDrawable(R.drawable.ic_stat_flames8));

                                    } else if (nerd == arc) {
                                        StringBuilder m = new StringBuilder();
                                        m.append("(" + arc);
                                        m.append(" out of " + Voters.length + ")");
                                        String j = m.toString();
                                        finalvalue = 5;
                                        count.setText(j);
                                        tvpub.setText("Arcane");
                                        tvpub.setTextColor(Color.parseColor("#00838F"));
                                        ivpub.setImageDrawable(getResources().getDrawable(R.drawable.ic_stat_floral115));

                                    } else if (nerd == sel) {
                                        StringBuilder m = new StringBuilder();
                                        m.append("(" + nerd);
                                        m.append(" out of " + Voters.length + ")");
                                        String j = m.toString();
                                        count.setText(j);
                                        tvpub.setText("Nerd");
                                        finalvalue = 1;
                                        tvpub.setTextColor(Color.parseColor("#9E9E9E"));
                                        ivpub.setImageDrawable(getResources().getDrawable(R.drawable.ic_stat_eyeglasses18));

                                    } else if (nerd == care) {
                                        StringBuilder m = new StringBuilder();
                                        m.append("(" + care);
                                        m.append(" out of " + Voters.length + ")");
                                        String j = m.toString();
                                        finalvalue = 3;
                                        count.setText(j);
                                        tvpub.setText("Caring");
                                        tvpub.setTextColor(Color.parseColor("#AD1457"));
                                        ivpub.setImageDrawable(getResources().getDrawable(R.drawable.ic_stat_small1));

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
                                    count.setText(j);
                                    finalvalue = 2;
                                    tvpub.setText("Hot");

                                    tvpub.setTextColor(Color.parseColor("#E65100"));
                                    ivpub.setImageDrawable(getResources().getDrawable(R.drawable.ic_stat_flames8));
                                } else {
                                    if (hot == nerd) {
                                        StringBuilder m = new StringBuilder();
                                        m.append("(" + hot);
                                        m.append(" out of " + Voters.length + ")");
                                        String j = m.toString();
                                        count.setText(j);
                                        tvpub.setText("Hot");
                                        finalvalue = 2;
                                        tvpub.setTextColor(Color.parseColor("#E65100"));
                                        ivpub.setImageDrawable(getResources().getDrawable(R.drawable.ic_stat_flames8));

                                    } else if (hot == arc) {
                                        StringBuilder m = new StringBuilder();
                                        m.append("(" + arc);
                                        m.append(" out of " + Voters.length + ")");
                                        String j = m.toString();
                                        count.setText(j);
                                        tvpub.setText("Arcane");
                                        finalvalue = 5;
                                        tvpub.setTextColor(Color.parseColor("#00838F"));
                                        ivpub.setImageDrawable(getResources().getDrawable(R.drawable.ic_stat_floral115));

                                    } else if (hot == sel) {
                                        StringBuilder m = new StringBuilder();
                                        m.append("(" + hot);
                                        m.append(" out of " + Voters.length + ")");
                                        String j = m.toString();
                                        count.setText(j);
                                        tvpub.setText("Hot");
                                        finalvalue = 2;
                                        tvpub.setTextColor(Color.parseColor("#E65100"));
                                        ivpub.setImageDrawable(getResources().getDrawable(R.drawable.ic_stat_flames8));

                                    } else if (hot == care) {
                                        StringBuilder m = new StringBuilder();
                                        m.append("(" + care);
                                        m.append(" out of " + Voters.length + ")");
                                        String j = m.toString();
                                        count.setText(j);
                                        tvpub.setText("Caring");
                                        finalvalue = 3;
                                        tvpub.setTextColor(Color.parseColor("#AD1457"));
                                        ivpub.setImageDrawable(getResources().getDrawable(R.drawable.ic_stat_small1));

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
                                    count.setText(j);
                                    finalvalue = 3;
                                    tvpub.setText("Caring");
                                    tvpub.setTextColor(Color.parseColor("#AD1457"));
                                    ivpub.setImageDrawable(getResources().getDrawable(R.drawable.ic_stat_small1));
                                } else {
                                    if (care == hot) {
                                        StringBuilder m = new StringBuilder();
                                        m.append("(" + care);
                                        m.append(" out of " + Voters.length + ")");
                                        String j = m.toString();
                                        count.setText(j);
                                        finalvalue = 3;
                                        tvpub.setText("Caring");
                                        tvpub.setTextColor(Color.parseColor("#AD1457"));
                                        ivpub.setImageDrawable(getResources().getDrawable(R.drawable.ic_stat_small1));

                                    } else if (nerd == arc) {
                                        StringBuilder m = new StringBuilder();
                                        m.append("(" + arc);
                                        m.append(" out of " + Voters.length + ")");
                                        String j = m.toString();
                                        count.setText(j);
                                        finalvalue = 5;
                                        tvpub.setText("Arcane");
                                        tvpub.setTextColor(Color.parseColor("#00838F"));
                                        ivpub.setImageDrawable(getResources().getDrawable(R.drawable.ic_stat_floral115));

                                    } else if (care == sel) {
                                        StringBuilder m = new StringBuilder();
                                        m.append("(" + care);
                                        m.append(" out of " + Voters.length + ")");
                                        String j = m.toString();
                                        count.setText(j);
                                        finalvalue = 3;
                                        tvpub.setText("Caring");
                                        tvpub.setTextColor(Color.parseColor("#AD1457"));
                                        ivpub.setImageDrawable(getResources().getDrawable(R.drawable.ic_stat_small1));
                                    } else if (care == nerd) {
                                        StringBuilder m = new StringBuilder();
                                        m.append("(" + care);
                                        m.append(" out of " + Voters.length + ")");
                                        String j = m.toString();
                                        count.setText(j);
                                        finalvalue = 3;
                                        tvpub.setText("Caring");
                                        tvpub.setTextColor(Color.parseColor("#AD1457"));
                                        ivpub.setImageDrawable(getResources().getDrawable(R.drawable.ic_stat_small1));

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
                                    count.setText(j);
                                    tvpub.setText("Arcane");

                                    tvpub.setTextColor(Color.parseColor("#00838F"));
                                    ivpub.setImageDrawable(getResources().getDrawable(R.drawable.ic_stat_floral115));
                                } else {
                                    if (arc == hot) {
                                        StringBuilder m = new StringBuilder();
                                        m.append("(" + arc);
                                        m.append(" out of " + Voters.length + ")");
                                        String j = m.toString();
                                        finalvalue = 5;
                                        count.setText(j);
                                        tvpub.setText("Arcane");
                                        tvpub.setTextColor(Color.parseColor("#00838F"));
                                        ivpub.setImageDrawable(getResources().getDrawable(R.drawable.ic_stat_floral115));
                                    } else if (arc == nerd) {
                                        StringBuilder m = new StringBuilder();
                                        m.append("(" + arc);
                                        m.append(" out of " + Voters.length + ")");
                                        String j = m.toString();
                                        count.setText(j);
                                        finalvalue = 5;
                                        tvpub.setText("Arcane");
                                        tvpub.setTextColor(Color.parseColor("#00838F"));
                                        ivpub.setImageDrawable(getResources().getDrawable(R.drawable.ic_stat_floral115));

                                    } else if (arc == sel) {
                                        StringBuilder m = new StringBuilder();
                                        m.append("(" + arc);
                                        m.append(" out of " + Voters.length + ")");
                                        String j = m.toString();
                                        count.setText(j);
                                        finalvalue = 5;
                                        tvpub.setText("Arcane");
                                        tvpub.setTextColor(Color.parseColor("#00838F"));
                                        ivpub.setImageDrawable(getResources().getDrawable(R.drawable.ic_stat_floral115));
                                    } else if (arc == care) {
                                        StringBuilder m = new StringBuilder();
                                        m.append("(" + arc);
                                        m.append(" out of " + Voters.length + ")");
                                        String j = m.toString();
                                        count.setText(j);
                                        finalvalue = 5;
                                        tvpub.setText("Arcane");
                                        tvpub.setTextColor(Color.parseColor("#00838F"));
                                        ivpub.setImageDrawable(getResources().getDrawable(R.drawable.ic_stat_floral115));
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
                                    count.setText(j);
                                    tvpub.setText("Selfish");
                                    tvpub.setTextColor(Color.parseColor("#FFD600"));
                                    ivpub.setImageDrawable(getResources().getDrawable(R.drawable.ic_stat_speech155));
                                } else {
                                    if (sel == hot) {
                                        StringBuilder m = new StringBuilder();
                                        m.append("(" + hot);
                                        m.append(" out of " + Voters.length + ")");
                                        String j = m.toString();
                                        count.setText(j);
                                        finalvalue = 2;
                                        tvpub.setText("Hot");
                                        tvpub.setTextColor(Color.parseColor("#E65100"));
                                        ivpub.setImageDrawable(getResources().getDrawable(R.drawable.ic_stat_flames8));

                                    } else if (sel == arc) {
                                        StringBuilder m = new StringBuilder();
                                        m.append("(" + arc);
                                        m.append(" out of " + Voters.length + ")");
                                        String j = m.toString();
                                        finalvalue = 5;
                                        count.setText(j);
                                        tvpub.setText("Arcane");
                                        tvpub.setTextColor(Color.parseColor("#00838F"));
                                        ivpub.setImageDrawable(getResources().getDrawable(R.drawable.ic_stat_floral115));

                                    } else if (sel == nerd) {
                                        StringBuilder m = new StringBuilder();
                                        m.append("(" + nerd);
                                        m.append(" out of " + Voters.length + ")");
                                        String j = m.toString();
                                        count.setText(j);
                                        tvpub.setText("Nerd");
                                        finalvalue = 1;
                                        tvpub.setTextColor(Color.parseColor("#9E9E9E"));
                                        ivpub.setImageDrawable(getResources().getDrawable(R.drawable.ic_stat_eyeglasses18));

                                    } else if (sel == care) {
                                        StringBuilder m = new StringBuilder();
                                        m.append("(" + care);
                                        m.append(" out of " + Voters.length + ")");
                                        String j = m.toString();
                                        count.setText(j);
                                        tvpub.setText("Caring");
                                        finalvalue = 3;
                                        tvpub.setTextColor(Color.parseColor("#AD1457"));
                                        ivpub.setImageDrawable(getResources().getDrawable(R.drawable.ic_stat_small1));

                                    }
                                }


                            }


                            recyclerView.setVisibility(View.GONE);
                            card.setVisibility(View.VISIBLE);
                            String voteini = list.get(0).get("UserChoice").toString();
                            if (voteini.equals("Nerd")) {
                                tv.setText("Nerd");
                                tv.setTextColor(Color.parseColor("#9E9E9E"));
                                iv.setImageDrawable(getResources().getDrawable(R.drawable.ic_stat_eyeglasses18));
                            } else if (voteini.equals("Hot")) {
                                tv.setTextColor(Color.parseColor("#E65100"));
                                tv.setText("Hot");
                                iv.setImageDrawable(getResources().getDrawable(R.drawable.ic_stat_flames8));
                            } else if (voteini.equals("Caring")) {
                                tv.setTextColor(Color.parseColor("#AD1457"));
                                tv.setText("Caring");
                                iv.setImageDrawable(getResources().getDrawable(R.drawable.ic_stat_small1));
                            } else if (voteini.equals("Selfish")) {
                                tv.setTextColor(Color.parseColor("#FFD600"));
                                tv.setText("Selfish");
                                iv.setImageDrawable(getResources().getDrawable(R.drawable.ic_stat_speech155));
                            } else {
                                tv.setTextColor(Color.parseColor("#00838F"));
                                tv.setText("Arcane");
                                iv.setImageDrawable(getResources().getDrawable(R.drawable.ic_stat_floral115));
                            }
                            //Object Id is Fetched
                            notorietypoints(notorietyp);
                            pd.dismiss();
                        } else {
                            pd.dismiss();
                            //Let user enter the choice
                            //Switch to main Layout

                            recyclerView.setVisibility(View.VISIBLE);
                            card.setVisibility(View.GONE);

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


    private class notorietyAdapter extends RecyclerView.Adapter <notorietyAdapter.ViewHolder>{

        String esk[];
        int con[];
        public notorietyAdapter(Context applicationContext, String esk[],int con[]) {
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
                        final ProgressDialog mo = new ProgressDialog(SocialNotorietyActivity.this);
                        mo.setMessage("Submitting your vote...");
                        mo.show();

                        StringBuilder fe = new StringBuilder();
                        fe.append(esk[position]);
                        fe.append(separator);
                        String ger = fe.toString();
                        StringBuilder mak = new StringBuilder();
                        mak.append(ParseUser.getCurrentUser().getObjectId());
                        mak.append(separator);
                        String ju = mak.toString();

                        ParseObject feliz = new ParseObject("SocialNotoriety");
                        if(esk[position].equals("Nerd")){
                            double m = 6.1 * 7.3;
                            String n = Double.toString(m);
                            feliz.put("notorietypoints",n);

                        }else if(esk[position].equals("Hot")){
                            double m = 7.1 * 7.3;
                            String n = Double.toString(m);
                            feliz.put("notorietypoints",n);

                        }else if(esk[position].equals("Caring")){
                            double m = 6.6 * 7.3;
                            String n = Double.toString(m);
                            feliz.put("notorietypoints",n);

                        }else if(esk[position].equals("Selfish")){
                            double m = 4.7 * 7.3;
                            String n = Double.toString(m);
                            feliz.put("notorietypoints",n);

                        }else if(esk[position].equals("Arcane")){
                            double m = 7.4 * 7.3;
                            String n = Double.toString(m);
                            feliz.put("notorietypoints",n);

                        }
                        feliz.put("IdCreator", ParseUser.getCurrentUser().getObjectId().toString());
                        feliz.put("UserChoice",""+esk[position]);
                        feliz.put("VotedNotoriety",ger);
                        feliz.put("VoterIds",ju);
                        feliz.saveInBackground(new SaveCallback() {
                            @Override
                            public void done(ParseException e) {
                                mo.dismiss();
                                Toast.makeText(SocialNotorietyActivity.this,"Your vote has been submitted successfully",Toast.LENGTH_SHORT).show();
                                finish();
                            }
                        });
                        Snackbar.make(view,""+esk[position]+" Selected",Snackbar.LENGTH_SHORT).show();
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
