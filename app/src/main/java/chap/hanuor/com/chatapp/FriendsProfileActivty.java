package chap.hanuor.com.chatapp;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.Point;
import android.graphics.PorterDuff;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.DecelerateInterpolator;
import android.widget.Button;
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
import com.parse.SaveCallback;

import org.json.JSONArray;
import org.json.JSONException;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import xyz.hanks.library.SmallBang;
import xyz.hanks.library.SmallBangListener;

/**
 * Created by Shantanu Johri on 27-11-2015.
 */
public class FriendsProfileActivty extends AppCompatActivity {

    ViewPager viewPager = null;
    String statusFetcherId;
    TextView status;

    List<String> list11;
    ArrayList<String> finalise;

    private int[] tabIcons = {
            R.drawable.ic_stat_heart,
            R.drawable.ic_stat_chat,
            R.drawable.ic_stat_social

    };
    ArrayList<String> likesUpdater;
    ArrayList<String> likesFetcher;
    ArrayList<String> likesFe;
    ArrayList<String> upme;
    TextView statusBy;
    int likesCounter;
    RelativeLayout addcomment;
    private TabLayout tabLayout;
    String currne;

    Toolbar toh;
String res[];
    String LikesFetch;
    String separator = "%3:11071995:11";
    KenBurnsView knv;

    ArrayList<String> germany;
    private Animator mCurrentAnimator;

    // The system "short" animation time duration, in milliseconds. This
    // duration is ideal for subtle animations or animations that occur
    // very frequently.
    private int mShortAnimationDuration;

    Button ids;
    String naam;
    String hew;
    String ObjectIdofPost;
    ImageView weheart;
    String lzor;
    SmallBang mdeselect;
    private SmallBang mSmallBang;
    View thumb1View;
    TextView commentcount;
    ImageView weshareit;
    TextView sharecount;
    TextView likecount;
    SmallBang sharebang;
    Bitmap maos= null;
    RecyclerView activeRecyclerView;
    RecyclerView.Adapter activeAdapter;
    int colors[]={Color.RED,Color.RED};
    int seccolors[] = {Color.WHITE,Color.WHITE};
    RecyclerView.LayoutManager activeLayoutManager;
    static String major;
    TransparentProgressDialog pd;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.duplicate_view_wall);
        pd = new TransparentProgressDialog(this,R.drawable.ic_amigo_icon);
        pd.show();
        toh = (Toolbar) findViewById(R.id.toolbar);
       setSupportActionBar(toh);
        mdeselect = SmallBang.attach2Window(this);
        mSmallBang = SmallBang.attach2Window(this);
        sharebang = SmallBang.attach2Window(this);
        sharebang.setColors(seccolors);
        mdeselect.setColors(seccolors);
       mSmallBang.setColors(colors);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        final Drawable upArrow = getResources().getDrawable(R.drawable.abc_ic_ab_back_mtrl_am_alpha);
        upArrow.setColorFilter(getResources().getColor(R.color.bg_glass), PorterDuff.Mode.SRC_ATOP);
        getSupportActionBar().setHomeAsUpIndicator(upArrow);
        Current_Status(statusFetcherId);
        knv = (KenBurnsView) findViewById(R.id.imageCover);
        thumb1View = findViewById(R.id.imageCover);
        //new
        addcomment = (RelativeLayout) findViewById(R.id.clickablewrite);
        commentcount = (TextView) findViewById(R.id.commentcount);
        weshareit = (ImageView) findViewById(R.id.weshareit);
        sharecount = (TextView) findViewById(R.id.sharecount);
        activeRecyclerView = (RecyclerView) findViewById(R.id.commentrecycler);
        // Toast.makeText(getActivity(), " ObjectO" + objectId, Toast.LENGTH_SHORT).show();
        // activeRecyclerView.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.HORIZONTAL_LIST));
        activeRecyclerView.setHasFixedSize(true);
        activeLayoutManager = new LinearLayoutManager(FriendsProfileActivty.this);
        activeRecyclerView.setLayoutManager(activeLayoutManager);

        activeRecyclerView.setItemAnimator(new DefaultItemAnimator());
        weheart = (ImageView) findViewById(R.id.weheartit);
        likecount = (TextView) findViewById(R.id.likecount);
        /// set click listeners
        weshareit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int co = 0;
                for(int p = 0 ;p<res.length;p++){
                    StringBuilder mn = new StringBuilder();
                    mn.append("@");
                    mn.append(ParseUser.getCurrentUser().getUsername().toString());

                    String g = mn.toString();
                    Log.d("FRGGRGDEMON"," "+g);
                    if(res[p].contentEquals(g)){
                        co = 1;
                        break;
                    }
                }
                if (co == 0) {

                    sharebang.bang(view);
                    sharebang.setmListener(new SmallBangListener() {
                        @Override
                        public void onAnimationStart() {
                        }

                        @Override
                        public void onAnimationEnd() {

                        }
                    });
                    weshareit.setImageDrawable(getResources().getDrawable(R.drawable.ic_stat_share15));

                    insertintoParse(statusFetcherId);
                    share_status(statusFetcherId);
                    backgroundSharesCount(statusFetcherId);
                }else{
                    Snackbar.make(view,"You have already shared this pin",Snackbar.LENGTH_SHORT).show();
                }
            }
        });
        weheart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                summerlife(view);
               // weheart.setImageResource(R.drawable.ic_stat_redheart);
                   }
        });
        status = (TextView) findViewById(R.id.status);
        statusBy = (TextView) findViewById(R.id.amigoId);

        ids = (Button) findViewById(R.id.dis);


        currne = ParseUser.getCurrentUser().getObjectId().toString();
        likesUpdater = new ArrayList<String>();
        upme = new ArrayList<String>();
        likesFe = new ArrayList<String>();
        mShortAnimationDuration = getResources().getInteger(
                android.R.integer.config_shortAnimTime);
        likesFetcher = new ArrayList<String>();
        Intent ac = getIntent();
        germany = new ArrayList<String>();
        statusFetcherId = ac.getStringExtra("ObjectID");
        likesCounter = 0;

        knv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               // zoomImageFromThumb(knv, R.drawable.lavida);
            }
        });
        addcomment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                addcomment.setBackgroundColor(Color.parseColor("#000000"));

                Intent ac = new Intent();
                ac.setClass(FriendsProfileActivty.this,Comments_Write.class);
                ac.putExtra("Objecto",statusFetcherId);
                ac.putExtra("HEW",hew);
                ac.putExtra("NAAM",naam);
                ac.putExtra("LAZOR",lzor);
                startActivity(ac);
                addcomment.setBackgroundColor(Color.parseColor("#212121"));
            }
        });

/*
        viewPager = (ViewPager) findViewById(R.id.pager);
        FragAdapter adapter = new FragAdapter(getSupportFragmentManager());
        //
        Bundle bundle = new Bundle();
        bundle.putString("ID",statusFetcherId);
        AboutFragment meka= new AboutFragment();
        meka.setArguments(bundle);
        //
        Bundle bundle2 = new Bundle();

        bundle2.putString("ObjectO",statusFetcherId);
        Social_Notoriety meka2= new Social_Notoriety();
        meka2.setArguments(bundle2);
        //
        Bundle bundle3 = new Bundle();

        bundle3.putString("Objec",statusFetcherId);
        ShareFragment meka3= new ShareFragment();
        meka3.setArguments(bundle3);


        adapter.addFragment(meka,"About");
        adapter.addFragment(meka2,"Social Notoriety");
        adapter.addFragment(meka3,"Share");
        viewPager.setAdapter(adapter);
        tabLayout = (TabLayout) findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(viewPager);
        tabLayout.getTabAt(0).setIcon(tabIcons[0]);
        tabLayout.getTabAt(1).setIcon(tabIcons[1]);
        tabLayout.getTabAt(2).setIcon(tabIcons[2]);
*/


    }
    public void backgroundSharesCount(String fera){
        ParseQuery<ParseObject> qa = ParseQuery.getQuery("AmigoPosts");
        qa.whereEqualTo("CreatorId",fera);
        qa.findInBackground(new FindCallback<ParseObject>() {
            @Override
            public void done(List<ParseObject> list, ParseException e) {
                if(list.size()!=0){
                    String objectIdofthepost = list.get(0).getObjectId().toString();
                    String sh = list.get(0).get("SharesCounter").toString();
                    final double jo = Double.parseDouble(sh);
                    ParseQuery<ParseObject> qs= ParseQuery.getQuery("AmigoPosts");
                    qs.getInBackground(objectIdofthepost, new GetCallback<ParseObject>() {
                        @Override
                        public void done(ParseObject parseObject, ParseException e) {
                            if(e==null){
                                int m;
                                try {
                                    m = Integer.valueOf(parseObject.getString("ShareCount"));
                                    m++;
                                    String k = String.valueOf(m);
                                    sharecount.setText(k);
                                    parseObject.put("ShareCount",k);
                                    StringBuilder io = new StringBuilder();
                                    io.append(""+(jo+0.07));
                                    String mes = io.toString();
                                    parseObject.put("SharesCounter",mes);
                                    parseObject.saveInBackground();

                                }catch (Exception eq){
                                    eq.printStackTrace();
                                }

                            }
                        }
                    });

                }
            }
        });


    }
    private void share_status(String mos) {
        ParseQuery<ParseUser> query = ParseUser.getQuery();
        query.getInBackground(mos, new GetCallback<ParseUser>() {
            @Override
            public void done(ParseUser parseUser, ParseException e) {
                //  String heartCount = parseUser.get("StatusLikes").toString();
                //likeText.setText(heartCount);
                final String af = parseUser.get("UserStatus").toString();
                final String cureentuser = ParseUser.getCurrentUser().getObjectId().toString();
                ParseQuery<ParseUser> wer = ParseUser.getQuery();
                wer.getInBackground(cureentuser, new GetCallback<ParseUser>() {
                    @Override
                    public void done(ParseUser parseUser, ParseException e) {
                        parseUser.put("UserStatus",af);
                        parseUser.saveInBackground();
                        ParseQuery<ParseObject> qa = ParseQuery.getQuery("AmigoPosts");
                        qa.whereEqualTo("CreatorId", cureentuser);
                        qa.findInBackground(new FindCallback<ParseObject>() {
                            @Override
                            public void done(List<ParseObject> list, ParseException e) {
                                if(e == null){
                                if (list.size() != 0) {
                                    final String am = list.get(0).getObjectId().toString();
                                    ParseQuery<ParseObject> qww = ParseQuery.getQuery("AmigoPosts");
                                    qww.getInBackground(am, new GetCallback<ParseObject>() {
                                        @Override
                                        public void done(ParseObject parseObject, ParseException e) {
                                            //int m = Integer.valueOf(parseObject.getString("Likes"));

                                            StringBuilder tRex = new StringBuilder();
                                            tRex.append(cureentuser);
                                            tRex.append(statusFetcherId);
                                            String f = tRex.toString();
                                            parseObject.put("CreatorStatus", af);
                                            parseObject.put("ShareIds", f);
                                            parseObject.put("CreatorId", cureentuser);
                                            parseObject.put("Likes", "0");
                                            parseObject.put("LikesName", " ");
                                            parseObject.put("ShareCount", "0");
                                            parseObject.put("SharesName", " ");
                                            parseObject.put("ListLikes", Arrays.asList(""));
                                            parseObject.put("CommentsString", "");
                                            parseObject.put("CommentIds", "");
                                            parseObject.saveInBackground();
                                        }
                                    });
                                } else {
                                    try {
                                        // final String am = list.get(0).getObjectId().toString();
                                        ParseObject parseObject = new ParseObject("AmigoPosts");
                                        StringBuilder tRex = new StringBuilder();
                                        tRex.append(cureentuser);
                                        tRex.append(statusFetcherId);
                                        String f = tRex.toString();
                                        parseObject.put("CreatorStatus", af);
                                        parseObject.put("ShareIds", f);
                                        parseObject.put("CreatorId", cureentuser);
                                        parseObject.put("Likes", "0");
                                        parseObject.put("LikesName", " ");
                                        parseObject.put("ShareCount", "0");
                                        parseObject.put("SharesName", " ");
                                        parseObject.put("ListLikes", Arrays.asList(""));
                                        parseObject.put("CommentsString", "");
                                        parseObject.put("CommentIds", "");
                                        parseObject.saveInBackground();
                                    } catch (Exception e1) {
                                        e1.printStackTrace();
                                    }


                                }
                            }else{
                                    e.printStackTrace();
                                }
                            }
                        });
                    }
                });


              /*  status.setText(af);
                String bf = parseUser.getUsername().toString();
                StringBuilder sre = new StringBuilder();
                sre.append("- @");
                sre.append(bf);
                String cf = sre.toString();
                statusBy.setText(cf);*/
            }
        });

    }

    private void insertintoParse(String her) {
        ParseQuery<ParseObject> qa = ParseQuery.getQuery("AmigoPosts");
        qa.whereEqualTo("CreatorId",her);
        qa.findInBackground(new FindCallback<ParseObject>() {
            @Override
            public void done(List<ParseObject> list, ParseException e) {
                if(e == null){
                if (list.size() != 0) {
                    String postObID = list.get(0).getObjectId().toString();
                    try {
                        final String shareIdes = list.get(0).get("SharesName").toString();
                        ParseQuery<ParseObject> qr = ParseQuery.getQuery("AmigoPosts");
                        qr.getInBackground(postObID, new GetCallback<ParseObject>() {
                            @Override
                            public void done(ParseObject parseObject, ParseException e) {
                                StringBuilder onma = new StringBuilder();
                                if (shareIdes != null) {
                                    onma.append(shareIdes);
                                    onma.append("@");
                                    onma.append(ParseUser.getCurrentUser().getUsername().toString());
                                    onma.append(separator);
                                    String ulo = onma.toString();
                                    parseObject.put("SharesName", ulo);
                                    parseObject.saveInBackground();

                                } else {
                                    onma.append("@");
                                    onma.append(ParseUser.getCurrentUser().getUsername().toString());
                                    onma.append(separator);
                                    String hero = onma.toString();
                                    parseObject.put("SharesName", hero);
                                    parseObject.saveInBackground();
                                }
                            }
                        });
                    } catch (Exception er) {
                        er.printStackTrace();
                    }

                }
            }else{
                    e.printStackTrace();
                }
            }
        });

    }
    private void summerlife(View v) {
        if (likesCounter == 0) {
            mSmallBang.bang(v);
            mSmallBang.setmListener(new SmallBangListener() {
                @Override
                public void onAnimationStart() {
                }

                @Override
                public void onAnimationEnd() {

                }
            });

            weheart.setImageDrawable(getResources().getDrawable(R.drawable.ic_stat_redheart));
            likesCounter = 1;
           // insertintoParse(statusFetcherId);
            final String currentUser = ParseUser.getCurrentUser().getObjectId().toString();
          pd.show();
            ParseQuery<ParseObject> qa = ParseQuery.getQuery("AmigoPosts");
            qa.whereEqualTo("CreatorId", statusFetcherId);
            qa.findInBackground(new FindCallback<ParseObject>() {
                                    @Override
                                    public void done(List<ParseObject> list, ParseException e) {
                                        if (e == null) {


                                            if (list.size() != 0) {
                                                final String am = list.get(0).getObjectId().toString();
                                                String mi = list.get(0).get("LikesCounter").toString();
                                                final double po = Double.parseDouble(mi);
                                                ParseQuery<ParseObject> qw = ParseQuery.getQuery("AmigoPosts");
                                                qw.getInBackground(am, new GetCallback<ParseObject>() {
                                                            @Override
                                                            public void done(ParseObject parseObject, ParseException e) {
                                                                JSONArray jArray = parseObject.getJSONArray("ListLikes");
                                                                //  likesFetcher = parseObject.getJSONArray("ListLikes");
                                                                Log.d("nsamasnasSASAW", " " + jArray.length());
                                                                //  likesFetcher = parseObject.getJSONArray("ListLikes");
                                                                try {


                                                                    for (int i = 0; i <= jArray.length(); i++) {
                                                                        //listdata.add(jArray.get(i).toString());
                                                                        try {

                                                                            likesFetcher.add(jArray.getString(i));
                                                                            Log.d("gsdwe", " " + likesFetcher.get(i).toString());
                                                                        } catch (JSONException e1) {
                                                                            e1.printStackTrace();
                                                                        }
                                                                    }
                                                                    String ger = "";
                                                                    for (int y = 0; y < likesFetcher.size(); y++) {
                                                                        ger = likesFetcher.get(y).toString();

                                                                        // ArrayList<String> elephantList = Arrays.asList(ger.split(","));
                                                                        //String g = ger.substring(2, 12);
                                                                        //likesFe.add(y, g);

                                                                    }
                                                                    String[] animalsArray = ger.split(",");
                                                                    finalise = new ArrayList<String>();
                                                                    // Log.d("gsdweqwwsaqzzxsw", " " + animalsArray.length);
                                                                    for (int l = 0; l < animalsArray.length; l++) {
                                                                        String fer = animalsArray[l].toString();
                                                                        String result = fer.replaceAll("[^\\w\\s]", "");
                                                                        // finalise[l] = result;
                                                                        finalise.add(l, result);
                                                                        // Log.d("Make in indaia",result);
                                                                    }
                                                                    int yu = 0;
                                                                    for (int m = 0; m < finalise.size(); m++) {
                                                                        if (finalise.get(m).toString().equals(currentUser)) {
                                                                            yu = 0;
                                                                            break;
                                                                        } else {
                                                                            yu = 1;
                                                                        }
                                                                    }
                                                                    if (yu == 1) {


                                                                        finalise.add(currentUser);
                                                                        for (int p = 0; p < finalise.size(); p++) {
                                                                            Log.d(" FREWSRWWWS A", finalise.get(p).toString());
                                                                            //upme.add(upme.size(),currentUser);
                                                                        }

                                                                        // Likes_input_function(finalise);
                                                                        ParseQuery<ParseObject> qww = ParseQuery.getQuery("AmigoPosts");
                                                                        qww.getInBackground(am, new GetCallback<ParseObject>() {
                                                                            @Override
                                                                            public void done(ParseObject parseObject, ParseException e) {
                                                                                int m = Integer.valueOf(parseObject.getString("Likes"));
                                                                                m++;
                                                                                String k = String.valueOf(m);
                                                                                //likesUpdater.add(0, currentUser);
                                                                                parseObject.put("ListLikes", Arrays.asList(finalise));
                                                                                parseObject.put("Likes", k);
                                                                                StringBuilder io = new StringBuilder();
                                                                                io.append("" + (po + 0.03));
                                                                                String mes = io.toString();
                                                                                parseObject.put("LikesCounter", mes);
                                                                                if (m == 1) {

                                                                                    likecount.setText(k);
                                                                                } else {
//                                                                                liker.setText("Likes");
                                                                                    likecount.setText(k);
                                                                                }

                                                                                parseObject.saveInBackground(new SaveCallback() {
                                                                                    @Override
                                                                                    public void done(ParseException e) {
                                                                                        pd.dismiss();
                                                                                    }
                                                                                });
                                                                            }
                                                                        });

                                                                    }
                                                                } catch (Exception e8) {
                                                                    e8.printStackTrace();
                                                                }


                                                            }
                                                        }

                                                );
                                            }
                                        }
                                        else{
                                            e.printStackTrace();
                                        }
                                    }

            }

            );


        } else if (likesCounter == 1) {
            weheart.setImageDrawable(getResources().getDrawable(R.drawable.ic_stat_broken21));
            mdeselect.bang(v);
            mdeselect.setmListener(new SmallBangListener() {
                @Override
                public void onAnimationStart() {
                }

                @Override
                public void onAnimationEnd() {

                }
            });

            pd.show();
            for (int b = 0; b < germany.size(); b++) {
                if (germany.get(b).toString().equals(currne)) {
                    germany.remove(currne);
                }
            }
            ParseQuery<ParseObject> qa = ParseQuery.getQuery("AmigoPosts");
            qa.whereEqualTo("CreatorId", statusFetcherId);
            qa.findInBackground(new FindCallback<ParseObject>() {
                @Override
                public void done(List<ParseObject> list, ParseException e) {
                    if(e==null) {


                        if (list.size() != 0) {
                            final String am = list.get(0).getObjectId().toString();
                            String lik = list.get(0).get("LikesCounter").toString();
                            final double po = Double.parseDouble(lik);
                            ParseQuery<ParseObject> qww = ParseQuery.getQuery("AmigoPosts");
                            qww.getInBackground(am, new GetCallback<ParseObject>() {
                                @Override
                                public void done(ParseObject parseObject, ParseException e) {
                                    //int m = Integer.valueOf(parseObject.getString("Likes"));
                                    int m = Integer.valueOf(parseObject.getString("Likes"));
                                    m--;
                                    String k = String.valueOf(m);
                                    if (m == 1) {
                                        //liker.setText("Like");
                                        likecount.setText(k);
                                    } else {
                                        //liker.setText("Likes");
                                        likecount.setText(k);
                                    }
                                    StringBuilder io = new StringBuilder();
                                    io.append("" + (po - 0.02));
                                    String mes = io.toString();
                                    //likesUpdater.add(0, currentUser);
                                    parseObject.put("ListLikes", Arrays.asList(germany));
                                    parseObject.put("Likes", k);
                                    parseObject.put("LikesCounter", mes);

                                    // likeText.setText(k);
                                    parseObject.saveInBackground(new SaveCallback() {
                                        @Override
                                        public void done(ParseException e) {
                                            pd.dismiss();
                                        }
                                    });
                                }
                            });
                        }
                    }else{
                        e.printStackTrace();
                    }
                }
            });


            likesCounter = 0;
        }



    }


    private void Likes_input_function(String men) {

        major = men;
    ObjectIdofPost = men;
    }
    private void getComments(String obj) {

        ParseQuery<ParseObject> qa = ParseQuery.getQuery("AmigoPosts");
        qa.whereEqualTo("objectId",obj);
        qa.findInBackground(new FindCallback<ParseObject>() {
            @Override
            public void done(List<ParseObject> list, ParseException e) {
                if(e == null){
                    if(list.size()!=0){
                        try {
                            final String am = list.get(0).getObjectId().toString();



                            String count = list.get(0).get("ShareCount").toString();
                            sharecount.setText(count);
                            String getShareIds = list.get(0).get("SharesName").toString();
                            String arr1[] = getShareIds.split("\\%3:11071995:11");
                            comma(arr1);
                            String amal = list.get(0).get("CommentsString").toString();
                            likecount.setText(list.get(0).get("Likes").toString());
                            if(amal.contentEquals(" ")){
                                commentcount.setText("0");
                                String majorLazer = list.get(0).getObjectId().toString();
                                String samba = list.get(0).get("CommentIds").toString();
                                String CommentIds[] = samba.split("\\%3:11071995:11");
                                String Comments[] = amal.split("\\%3:11071995:11");
                                Log.d("dextrqqwe", "" + Comments[0]);
                                capture_comms(amal, samba, majorLazer);
                            }
                            else {
                                String majorLazer = list.get(0).getObjectId().toString();
                                String samba = list.get(0).get("CommentIds").toString();
                                String CommentIds[] = samba.split("\\%3:11071995:11");
                                String Comments[] = amal.split("\\%3:11071995:11");
                                Log.d("dextre", "" + Comments[0]);
                                if(Comments[0].equals(" ")) {
                                    commentcount.setText("0");
                                }
                                else{
                                    activeAdapter = new CommentsAdapter(Comments, CommentIds);
                                    activeRecyclerView.setAdapter(activeAdapter);
                                    if(Comments.length == 1){
                                      //  commenter.setText("Comment");

                                        commentcount.setText("" + Comments.length);
                                    }
                                    else{
                                       // commenter.setText("Comments");
                                        commentcount.setText("" + Comments.length);
                                    }

                                }
                                capture_comms(amal, samba, majorLazer);

                            }
                           /* if(amal.contains("%6`~!1107*]$"));
                            {

                                if(Comments.length==0){
                                   // Toast.makeText(CommentsSection.this, " No Comments Found", Toast.LENGTH_SHORT).show();
                                }

                            }*/
                        } catch (Exception e1) {
                            e1.printStackTrace();
                        }
                    }
                    else{
                        // Toast.makeText(CommentsSection.this, " No Comments Found", Toast.LENGTH_SHORT).show();
                    }
                }

            }
        });
    }

    private void comma(String[] arr1) {
        res = arr1;
    }

    private void capture_comms(String amal, String naasm,String ghi) {
        naam = naasm;
        hew = amal;
        lzor = ghi;
    }

    public class CommentsAdapter extends RecyclerView.Adapter<CommentsAdapter.ViewHolder> {
        String arr[];
        String mes[];


        public CommentsAdapter(String arr[],String mes[]) {
            this.arr = arr;
            this.mes = mes;

        }

        // Provide a reference to the views for each data item
        // Complex data items may need more than one view per item, and
        // you provide access to all the views for a data item in a view holder
        public class ViewHolder extends RecyclerView.ViewHolder {
            TextView tv;
            TextView idtv;
            //  ImageView iv;
            // public CardView activeCV;

            public ViewHolder(View CV) {
                super(CV);
                tv = (TextView) CV.findViewById(R.id.comm);
                idtv = (TextView) CV.findViewById(R.id.commid);
                //iv = (ImageView) CV.findViewById(R.id.ab);

            }
        }

        // Create new views (invoked by the layout manager)
        @Override
        public CommentsAdapter.ViewHolder onCreateViewHolder(ViewGroup parent,
                                                             int viewType) {
            // create a new view
            View v = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.comments_single_item, parent, false);
            // set the view's size, margins, paddings and layout parameters

            ViewHolder vh = new ViewHolder(v);
            return vh;
        }

        /*@Override
        public void onBindViewHolder(CommentsAdapter.ViewHolder holder, int position) {

        }
*/
        // Replace the contents of a view (invoked by the layout manager)
        @Override
        public void onBindViewHolder(ViewHolder holder, int position) {
            // - get element from your dataset at this position
            // - replace the contents of the view with that element
            holder.tv.setText(arr[position]);
            holder.idtv.setText(mes[position]);
            //holder.iv.setImageResource(ico[position]);
            //      .setText(arr[position]);
            //  ((ImageView)temp.findViewById(R.id.iconimage)).setImageResource(ico[position]);

        }


        // Return the size of your dataset (invoked by the layout manager)
        @Override
        public int getItemCount() {
            return arr.length;
        }
    }

    @Override
    protected void onResume() {
        Current_Status(statusFetcherId);

        super.onResume();
    }

    private void Current_Status(final String mos) {
        ParseQuery<ParseUser> query = ParseUser.getQuery();
        query.getInBackground(mos, new GetCallback<ParseUser>() {
            @Override
            public void done(ParseUser parseUser, ParseException e) {
if(e == null) {


    //  String heartCount = parseUser.get("StatusLikes").toString();
    //likeText.setText(heartCount);
    try {
        ParseFile fi = parseUser.getParseFile("CoverPicture");
        fi.getDataInBackground(new GetDataCallback() {
            @Override
            public void done(byte[] bytes, ParseException e) {
                if (bytes.length != 0) {
                    Bitmap bmp = BitmapFactory.decodeByteArray(bytes, 0, bytes.length);
                    maos = bmp;
                    // vh.getImView().setImageBitmap(bmp);

                    Animation fadeIn = new AlphaAnimation(0, 1);
                    fadeIn.setInterpolator(new DecelerateInterpolator()); //add this
                    fadeIn.setDuration(1000);
                    knv.setAnimation(fadeIn);
                    knv.setImageBitmap(bmp);
                    AccelerateDecelerateInterpolator ACCELERATE_DECELERATE = new AccelerateDecelerateInterpolator();
                    RandomTransitionGenerator generator = new RandomTransitionGenerator(10000, ACCELERATE_DECELERATE);

                    knv.setTransitionGenerator(generator); //set new transition on kbv
                }
            }
        });

        String af = parseUser.get("UserStatus").toString();
        status.setText(af);
        String bf = parseUser.getUsername().toString();
        StringBuilder sre = new StringBuilder();
        sre.append("- @");
        sre.append(bf);
        String cf = sre.toString();
        statusBy.setText(cf);
        ParseQuery<ParseObject> sa = ParseQuery.getQuery("AmigoPosts");
        sa.whereEqualTo("CreatorId", mos);
        sa.findInBackground(new FindCallback<ParseObject>() {
            @Override
            public void done(List<ParseObject> list, ParseException e) {
                if (list.size() != 0) {
                    String sum = list.get(0).getObjectId().toString();
                    getComments(sum);

                    ParseQuery<ParseObject> am = ParseQuery.getQuery("AmigoPosts");
                    am.whereEqualTo("objectId", sum);
                    am.findInBackground(new FindCallback<ParseObject>() {
                        @Override
                        public void done(List<ParseObject> list, ParseException e) {
                            if(e == null) {

                                if (list.size() != 0) {
                                    JSONArray jArray = list.get(0).getJSONArray("ListLikes");
                                    try {


                                        for (int i = 0; i <= jArray.length(); i++) {
                                            //listdata.add(jArray.get(i).toString());
                                            try {

                                                likesFetcher.add(jArray.getString(i));
                                                Log.d("gsdwe", " " + likesFetcher.get(i).toString());
                                            } catch (JSONException e1) {
                                                e1.printStackTrace();
                                            }
                                        }
                                        String ger = "";
                                        for (int y = 0; y < likesFetcher.size(); y++) {
                                            ger = likesFetcher.get(y).toString();

                                            // ArrayList<String> elephantList = Arrays.asList(ger.split(","));
                                            //String g = ger.substring(2, 12);
                                            //likesFe.add(y, g);

                                        }
                                        String[] animalsArray = ger.split(",");
                                        ArrayList<String> finalise = new ArrayList<String>();
                                        // Log.d("gsdweqwwsaqzzxsw", " " + animalsArray.length);
                                        for (int l = 0; l < animalsArray.length; l++) {
                                            String fer = animalsArray[l].toString();
                                            String result = fer.replaceAll("[^\\w\\s]", "");

                                            finalise.add(l, result);

                                        }
                                        fetch_ids(finalise);
                                        final String currentUser = ParseUser.getCurrentUser().getObjectId().toString();
                                        for (int h = 0; h < finalise.size(); h++) {
                                            if (finalise.get(h).toString().equalsIgnoreCase(currentUser)) {
                                                likesCounter = 1;
                                                weheart.setImageDrawable(getResources().getDrawable(R.drawable.ic_stat_redheart));
                                            }
                                        }
                                    } catch (Exception e2) {
                                        e2.printStackTrace();
                                    }
                                    try {
                                        String getShareIds = list.get(0).get("ShareIds").toString();
                                        if (getShareIds.length() > 0) {
                                            ids.setVisibility(View.VISIBLE);


                                        }
                                    } catch (Exception ew) {
                                        ew.printStackTrace();
                                    }
                                }

                            }
                        }
                    });
                }
                }
        });

           } catch (Exception e1) {
             e1.printStackTrace();
             }
           }else{
           e.printStackTrace();
          }
            }
        });

        pd.dismiss();
        }

    private void fetch_ids(ArrayList<String> finalise) {
        germany = finalise;

    }
    @Override
    public boolean onOptionsItemSelected(MenuItem menuItem) {
        if (menuItem.getItemId() == android.R.id.home) {
            onBackPressed();
            overridePendingTransition(R.anim.right_in, R.anim.right_out);
        }

        if(menuItem.getItemId() == R.id.camera){

            zoomImageFromThumb(thumb1View);

        }
        return super.onOptionsItemSelected(menuItem);
    }

    private void zoomImageFromThumb(final View thumbView) {

        final TransparentProgressDialog pd = new TransparentProgressDialog(this,R.drawable.ic_amigo_icon);
        pd.show();
        // If there's an animation in progress, cancel it
        // immediately and proceed with this one.
        if (mCurrentAnimator != null) {
            mCurrentAnimator.cancel();
        }

        // Load the high-resolution "zoomed-in" image.
        final ImageView expandedImageView;
        expandedImageView = (ImageView) findViewById(
                R.id.imageZoom);
        ParseQuery<ParseUser> query = ParseUser.getQuery();
        query.getInBackground(statusFetcherId, new GetCallback<ParseUser>() {
            @Override
            public void done(ParseUser parseUser, ParseException e) {
                if(e == null) {


                    //  String heartCount = parseUser.get("StatusLikes").toString();
                    //likeText.setText(heartCount);

                    try {
                        ParseFile fi = parseUser.getParseFile("CoverPicture");
                        fi.getDataInBackground(new GetDataCallback() {
                            @Override
                            public void done(byte[] bytes, ParseException e) {
                                if (bytes.length != 0) {
                                    Bitmap bmp = BitmapFactory.decodeByteArray(bytes, 0, bytes.length);

                                    maos = bmp;
                                    // vh.getImView().setImageBitmap(bmp);
                                    pd.dismiss();
                                    Animation fadeIn = new AlphaAnimation(0, 1);
                                    fadeIn.setInterpolator(new DecelerateInterpolator()); //add this
                                    fadeIn.setDuration(1000);
                                    expandedImageView.setAnimation(fadeIn);
                                    expandedImageView.setImageBitmap(bmp);


                                } else {

                                    Animation fadeIn = new AlphaAnimation(0, 1);
                                    fadeIn.setInterpolator(new DecelerateInterpolator()); //add this
                                    fadeIn.setDuration(1000);
                                    expandedImageView.setAnimation(fadeIn);
                                    expandedImageView.setImageDrawable(getResources().getDrawable(R.drawable.abs));
                                }
                            }
                        });
                    } catch (Exception e1) {
                        e1.printStackTrace();
                    } finally {

                        Animation fadeIn = new AlphaAnimation(0, 1);
                        fadeIn.setInterpolator(new DecelerateInterpolator()); //add this
                        fadeIn.setDuration(1000);
                        expandedImageView.setAnimation(fadeIn);
                        expandedImageView.setImageDrawable(getResources().getDrawable(R.drawable.abs));
                        pd.dismiss();
                    }
                }else{
                    e.printStackTrace();
                }
            }
        });



        // Calculate the starting and ending bounds for the zoomed-in image.
        // This step involves lots of math. Yay, math.
        final Rect startBounds = new Rect();
        final Rect finalBounds = new Rect();
        final Point globalOffset = new Point();

        // The start bounds are the global visible rectangle of the thumbnail,
        // and the final bounds are the global visible rectangle of the container
        // view. Also set the container view's offset as the origin for the
        // bounds, since that's the origin for the positioning animation
        // properties (X, Y).
        thumbView.getGlobalVisibleRect(startBounds);
        findViewById(R.id.container)
                .getGlobalVisibleRect(finalBounds, globalOffset);
        startBounds.offset(-globalOffset.x, -globalOffset.y);
        finalBounds.offset(-globalOffset.x, -globalOffset.y);

        // Adjust the start bounds to be the same aspect ratio as the final
        // bounds using the "center crop" technique. This prevents undesirable
        // stretching during the animation. Also calculate the start scaling
        // factor (the end scaling factor is always 1.0).
        float startScale;
        if ((float) finalBounds.width() / finalBounds.height()
                > (float) startBounds.width() / startBounds.height()) {
            // Extend start bounds horizontally
            startScale = (float) startBounds.height() / finalBounds.height();
            float startWidth = startScale * finalBounds.width();
            float deltaWidth = (startWidth - startBounds.width()) / 2;
            startBounds.left -= deltaWidth;
            startBounds.right += deltaWidth;
        } else {
            // Extend start bounds vertically
            startScale = (float) startBounds.width() / finalBounds.width();
            float startHeight = startScale * finalBounds.height();
            float deltaHeight = (startHeight - startBounds.height()) / 2;
            startBounds.top -= deltaHeight;
            startBounds.bottom += deltaHeight;
        }

        // Hide the thumbnail and show the zoomed-in view. When the animation
        // begins, it will position the zoomed-in view in the place of the
        // thumbnail.
        thumbView.setAlpha(0f);
        expandedImageView.setVisibility(View.VISIBLE);

        // Set the pivot point for SCALE_X and SCALE_Y transformations
        // to the top-left corner of the zoomed-in view (the default
        // is the center of the view).
        expandedImageView.setPivotX(0f);
        expandedImageView.setPivotY(0f);

        // Construct and run the parallel animation of the four translation and
        // scale properties (X, Y, SCALE_X, and SCALE_Y).
        AnimatorSet set = new AnimatorSet();
        set
                .play(ObjectAnimator.ofFloat(expandedImageView, View.X,
                        startBounds.left, finalBounds.left))
                .with(ObjectAnimator.ofFloat(expandedImageView, View.Y,
                        startBounds.top, finalBounds.top))
                .with(ObjectAnimator.ofFloat(expandedImageView, View.SCALE_X,
                        startScale, 1f)).with(ObjectAnimator.ofFloat(expandedImageView,
                View.SCALE_Y, startScale, 1f));
        set.setDuration(mShortAnimationDuration);
        set.setInterpolator(new DecelerateInterpolator());
        set.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                mCurrentAnimator = null;
            }

            @Override
            public void onAnimationCancel(Animator animation) {
                mCurrentAnimator = null;
            }
        });
        set.start();
        mCurrentAnimator = set;

        // Upon clicking the zoomed-in image, it should zoom back down
        // to the original bounds and show the thumbnail instead of
        // the expanded image.
        final float startScaleFinal = startScale;
        expandedImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (mCurrentAnimator != null) {
                    mCurrentAnimator.cancel();
                }

                // Animate the four positioning/sizing properties in parallel,
                // back to their original values.
                AnimatorSet set = new AnimatorSet();
                set.play(ObjectAnimator
                        .ofFloat(expandedImageView, View.X, startBounds.left))
                        .with(ObjectAnimator
                                .ofFloat(expandedImageView,
                                        View.Y,startBounds.top))
                        .with(ObjectAnimator
                                .ofFloat(expandedImageView,
                                        View.SCALE_X, startScaleFinal))
                        .with(ObjectAnimator
                                .ofFloat(expandedImageView,
                                        View.SCALE_Y, startScaleFinal));
                set.setDuration(mShortAnimationDuration);
                set.setInterpolator(new DecelerateInterpolator());
                set.addListener(new AnimatorListenerAdapter() {
                    @Override
                    public void onAnimationEnd(Animator animation) {
                        thumbView.setAlpha(1f);
                        expandedImageView.setVisibility(View.GONE);
                        mCurrentAnimator = null;
                    }

                    @Override
                    public void onAnimationCancel(Animator animation) {
                        thumbView.setAlpha(1f);
                        expandedImageView.setVisibility(View.GONE);
                        mCurrentAnimator = null;
                    }
                });
                set.start();
                mCurrentAnimator = set;
            }
        });
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_camera, menu);
        return  true;
    }

    private void current_likes(String likes) {
            LikesFetch = likes;
    }

}




