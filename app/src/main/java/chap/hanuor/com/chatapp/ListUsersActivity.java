package chap.hanuor.com.chatapp;

import android.animation.Animator;
import android.app.ProgressDialog;
import android.app.SearchManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.Point;
import android.graphics.drawable.LayerDrawable;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.design.widget.Snackbar;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v4.widget.DrawerLayout;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.PopupMenu;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.DecelerateInterpolator;
import android.view.animation.RotateAnimation;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.flaviofaria.kenburnsview.KenBurnsView;
import com.parse.FindCallback;
import com.parse.GetCallback;
import com.parse.GetDataCallback;
import com.parse.ParseException;
import com.parse.ParseFile;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.ParseUser;
import com.squareup.picasso.Picasso;
import com.yayandroid.parallaxrecyclerview.ParallaxRecyclerView;
import com.yayandroid.parallaxrecyclerview.ParallaxViewHolder;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class ListUsersActivity extends AppCompatActivity {

    private String currentUserId;
    private ArrayList<String> nickNames;
    private ArrayList<String> namesd;
    private ArrayList<String> Notifier;
    LinearLayout mainL;
    RelativeLayout defaultL;
    public int mNotificationsCount = 0;
    private TransparentProgressDialog pd;
    ArrayList<String> RequestStackId;
    LinearLayout toolbarContainer;
    int indexer = 0;
    int x =0;
    int check = 0;
    ArrayList<String> objector;
    int counterFlag = 0;
    boolean fadeToolbar = false;
    String secondary;
    String reverseOpinion;
    ProgressDialog mekia;
    ArrayList<String> war;
    ArrayList<String> Final_Object_Id_Fetcher;
int alontheway = 0;
    Toolbar homeToolbar;
    int toolbarHeight;
    TextView toolbarTitle;
    String cpu;
    SwipeRefreshLayout mSwipeRefreshLayout;
    SwipeRefreshLayout mSwipeRefreshLayout1;
    ArrayList<String> erray;
    ArrayList<String> FriendRequestObjectIdsKeys;
    ArrayList<String> Amigo_Id_Fetcher;
    private Animator mCurrentAnimator;

    // The system "short" animation time duration, in milliseconds. This
    // duration is ideal for subtle animations or animations that occur
    // very frequently.
    private int mShortAnimationDuration;
    String Karna;

    ArrayList<String> tolk ;                           // Declaring DrawerLayout
    ArrayList<String> was ;
    private ProgressDialog progressDialog;
    private BroadcastReceiver  receiver = null;
    ParallaxRecyclerView recyclerView;
    ImageView amigoImage;
    com.melnykov.fab.FloatingActionButton fab;
    int k;
    KenBurnsView kbv;
    int resumer;
    int sec;
    int z = 0;

    ArrayList<String> arry;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.mainacivity);
        mSwipeRefreshLayout = (SwipeRefreshLayout) findViewById(R.id.activity_main_swipe_refresh_layout);
        mSwipeRefreshLayout1 = (SwipeRefreshLayout) findViewById(R.id.activity_main_swipe_refresh_layout1);
        resumer = 0;

        //mekia = new ProgressDialog(ListUsersActivity.this);
        pd = new TransparentProgressDialog(this, R.drawable.ic_amigo_icon);
       // mekia.setMessage("Refreshing...");
        war = new ArrayList<String>();
        was = new ArrayList<String>();
        pd.show();
        //mekia.show();
       // mekia.setCanceledOnTouchOutside(false);
        cpu = ParseUser.getCurrentUser().getObjectId().toString();
        mainL = (LinearLayout) findViewById(R.id.LayoutMain);
        defaultL = (RelativeLayout) findViewById(R.id.DefaultMain);

        setupFriendRequestMethod();
sec = 0;
        homeToolbar = (android.support.v7.widget.Toolbar) findViewById(R.id.home_tool_bar);
        toolbarTitle = (TextView) findViewById(R.id.home_toolbar_title);
        homeToolbar.animate().translationY(-homeToolbar.getHeight()).setInterpolator(new AccelerateInterpolator(2)).start();
        homeToolbar.animate().translationY(0).setInterpolator(new DecelerateInterpolator(2)).start();

        setSupportActionBar(homeToolbar);
        amigoImage = (ImageView) findViewById(R.id.amigoToolbarImage);
        RotateAnimation rotateAnimation = new RotateAnimation(180, 720,
                Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
        rotateAnimation.setDuration((long) 2*1500);

        amigoImage.setAnimation(rotateAnimation);
        recyclerView = (ParallaxRecyclerView) findViewById(R.id.usersListView);
      /*  kbv = (KenBurnsView) findViewById(R.id.imageCover);
        AccelerateDecelerateInterpolator ACCELERATE_DECELERATE = new AccelerateDecelerateInterpolator();
        RandomTransitionGenerator generator = new RandomTransitionGenerator(10000, ACCELERATE_DECELERATE);

        kbv.setTransitionGenerator(generator); //set new transition on kbv*/

        homeToolbar.setNavigationIcon(R.mipmap.ic_drawer);
        toolbarContainer = (LinearLayout) findViewById(R.id.fabhide_toolbar_container);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        toolbarTitle = (TextView) findViewById(R.id.home_toolbar_title);
        final LinearLayoutManager layoutManager
                = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
        fab = (com.melnykov.fab.FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent st = new Intent();
                st.setClass(ListUsersActivity.this,AddStatus.class);
                startActivity(st);
                View v;
                Snackbar.make(view,"Setting up your new status",Snackbar.LENGTH_SHORT).show();
            }
        });

        mSwipeRefreshLayout.setColorSchemeResources(R.color.blue,R.color.orange, R.color.green);
        mSwipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {

                int alontheway = 0;
                setConversationsList();

                Amigo_Id_Fetcher = new ArrayList<String>();
                FriendRequestObjectIdsKeys = new ArrayList<String>();
                Final_Object_Id_Fetcher = new ArrayList<String>();
                setupFriendRequestMethod();
                //
                //recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
                //recyclerView.setHasFixedSize(true);
                //recyclerView.setAdapter(new TestRecyclerAdapter(getApplicationContext(),was,war));

              //  int alontheway = 0;
                Log.d("SAD","fdfd");
                Log.d("MERAHERA",""+was.size());
                mSwipeRefreshLayout.setRefreshing(false);
            }

        });
        mSwipeRefreshLayout1.setColorSchemeResources(R.color.blue,R.color.orange, R.color.green);
        mSwipeRefreshLayout1.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {

                setConversationsList();

                Amigo_Id_Fetcher = new ArrayList<String>();
                FriendRequestObjectIdsKeys = new ArrayList<String>();
                Final_Object_Id_Fetcher = new ArrayList<String>();
                setupFriendRequestMethod();
                //
                //recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
                //recyclerView.setHasFixedSize(true);
                //recyclerView.setAdapter(new TestRecyclerAdapter(getApplicationContext(),was,war));

                int alontheway = 0;
                Log.d("SAD","fdfd");
                Log.d("MERAHERA",""+was.size());
                mSwipeRefreshLayout1.setRefreshing(false);
            }

        });

        k=0;
        tolk = new ArrayList<String>();
        //usersListView = (ListView) findViewById(R.id.usersListView);

        recyclerView.setItemAnimator(new DefaultItemAnimator());
        fab.attachToRecyclerView(recyclerView);
        fab.setColorNormal(Color.parseColor("#212121"));
        fab.setColorPressed(Color.parseColor("#212121"));
        erray = new ArrayList<String>();
            StringBuilder am = new StringBuilder();
        am.append(cpu);

        Karna = am.toString();

        setConversationsList();

        Amigo_Id_Fetcher = new ArrayList<String>();
        FriendRequestObjectIdsKeys = new ArrayList<String>();
        Final_Object_Id_Fetcher = new ArrayList<String>();

        NavigationDrawerFragment drawerFragment = (NavigationDrawerFragment) getSupportFragmentManager().findFragmentById(R.id.fragment_navigation_drawer);
        drawerFragment.setUp((DrawerLayout) findViewById(R.id.DrawerLayout),homeToolbar);


        int alontheway = 0;
        Log.d("SAD","fdfd");
        Log.d("MERAHERA",""+was.size());
        showSpinner();
       // Log.d("AlongthewayIheardsss",""+alontheway);
    // mekia.dismiss();
    }


    private  void setupFriendRequestMethod() {
        //To find the values stored in the column do this!!
        Notifier = new ArrayList<String>();
        k=0;
        //final String ak;
        ParseQuery<ParseObject> friendquery = ParseQuery.getQuery("FriendRequests");
        friendquery.whereEqualTo("RequestReceiver",ParseUser.getCurrentUser().getObjectId().toString());
        friendquery.selectKeys(Arrays.asList("NotificationManager"));
        friendquery.findInBackground(new FindCallback<ParseObject>() {
            @Override
            public void done(List<ParseObject> list, ParseException e) {

                //inner class. Cannot access the values that are initialized here!
                try {
                    if (list.size() != 0) {


                    if (e == null) {
                        // Log.d("Dasvidaniya","" + list.size());
                        for (int p = 0; p < list.size(); p++) {

                            Notifier.add(list.get(p).get("NotificationManager").toString());

                            //Temporary notifications Bug fixer. Change it!
                            if (RequestStackId.size() > list.size()) {
                                break;
                            }
                        }

                        String lame;
                        String funny;
                        int Id_Fetcher_Counter = 0;

                        //Functioning to generate the notification badge

                        for (int q = 0; q < Notifier.size(); q++) {

                            if (Notifier.get(q).toString().length() == 21) {
                                final int yu = q;
                                lame = Notifier.get(q).toString().substring(0, 10);


                                funny = Notifier.get(q).toString().substring(10, 20);


                                if (Karna.contentEquals(lame)) {
                                    ParseQuery<ParseObject> query = ParseQuery.getQuery("FriendRequests");
                                    query.whereEqualTo("RequestReceiver", ParseUser.getCurrentUser().getObjectId().toString());
                                    query.findInBackground(new FindCallback<ParseObject>() {
                                        @Override
                                        public void done(List<ParseObject> list, ParseException e) {
                                            if (list.size() != 0) {

                                                k = k + 1;
                                                String lam = Notifier.get(yu).toString().substring(0, 10);
                                                String fam = Notifier.get(yu).toString().substring(10, 20);
                                                updateNotificationsBadge(k);
                                                // StringBuilder op1 = new StringBuilder();
                                                StringBuilder Am = new StringBuilder();
                                                Am.append(lam);
                                                Am.append(fam);
                                                Amigo_Id_Fetcher.add(Am.toString());
                                                //Id_Fetcher_Counter = Id_Fetcher_Counter + 1;
                                            } else {
                                                //This guy sent them a request!
                                            }
                                        }
                                    });

                                    // RequestStackId=null;

                                } else if (Karna.contentEquals(funny)) {
                                    ParseQuery<ParseObject> query = ParseQuery.getQuery("FriendRequests");
                                    query.whereEqualTo("RequestReceiver", ParseUser.getCurrentUser().getObjectId().toString());
                                    query.findInBackground(new FindCallback<ParseObject>() {
                                        @Override
                                        public void done(List<ParseObject> list, ParseException e) {
                                            if (list.size() != 0) {
                                                k = k + 1;
                                                String lam = Notifier.get(yu).toString().substring(0, 10);
                                                String fam = Notifier.get(yu).toString().substring(10, 20);
                                                updateNotificationsBadge(k);
                                                // StringBuilder op1 = new StringBuilder();
                                                StringBuilder Am = new StringBuilder();
                                                Am.append(lam);
                                                Am.append(fam);
                                                Amigo_Id_Fetcher.add(Am.toString());
                                                //Id_Fetcher_Counter = Id_Fetcher_Counter + 1;
                                            } else {
                                                //This guy sent them a request!
                                            }
                                        }
                                    });
                                }
                                //if no notifications then display the original value of k
                               /* else {
                                    updateNotificationsBadge(0);
                                }*/
                            } else {
                                //updateNotificationsBadge(0);
                                //Toast.makeText(ListUsersActivity.this, "AmigoNULL", Toast.LENGTH_SHORT).show();
                            }

                        }

                        //Storing the ObjectIds of all the notification generator AmigoChatIds so that later on we can change the 'Notfirends' value to "ActiveFriends" value
                        for (int v = 0; v < Amigo_Id_Fetcher.size(); v++) {


                            ParseQuery<ParseObject> query = ParseQuery.getQuery("FriendRequests");
                            query.whereEqualTo("AmigoChatId", Amigo_Id_Fetcher.get(v).toString());
                            query.findInBackground(new FindCallback<ParseObject>() {
                                @Override
                                public void done(List<ParseObject> list, ParseException e) {
                                    if (e == null) {

                                        if (list.size() != 0) {
                                            FriendRequestObjectIdsKeys.add(list.get(0).getObjectId());
                                            HandlingFriendRequestMethod(FriendRequestObjectIdsKeys.get(x));
                                        }
                                    }

                                }


                            });

                        }
                    }

                }else{
                        updateNotificationsBadge(0);
                    }
                } catch (Exception e1) {
                    e1.printStackTrace();
                }

            }
        });

    }
    public void HandlingFriendRequestMethod(String namespace){
        x =x +1;

        //Use this anywhere you want
        Final_Object_Id_Fetcher.add(counterFlag, namespace);
        //Log.d("Sunlo Duniya walo", Final_Object_Id_Fetcher.get(counterFlag));
        counterFlag = counterFlag + 1;


    }



    //display clickable a list of all users
    private void setConversationsList() {
        //mekia.show();
        objector = new ArrayList<String>();
        final ArrayList Req = new ArrayList<String>();
        RequestStackId = new ArrayList<String>();
        currentUserId = ParseUser.getCurrentUser().getObjectId();
        //Toast.makeText(ListUsersActivity.this, "Bamlasj sda erd", Toast.LENGTH_SHORT).show();

        ParseQuery<ParseObject> friendquer = ParseQuery.getQuery("FriendRequests");
        friendquer.selectKeys(Arrays.asList("RequestStack"));

        friendquer.findInBackground(new FindCallback<ParseObject>() {
            @Override
            public void done(List<ParseObject> list, ParseException e) {

                //inner class. Cannot access the values that are initialized here!
                if (e == null) {

                    for (int p = 0; p < list.size(); p++) {
                        RequestStackId.add(list.get(p).get("RequestStack").toString());
                        if (RequestStackId.size() > list.size()) {
                            break;
                        }
                    }

                    String First_Fetcher;
                    String Second_Fetcher;
                    // int Id_Fetcher_Counter = 0;

                    //if(RequestStackId.size())
                    if (RequestStackId.size() != 0) {


                    for (int q = 0; q < RequestStackId.size(); q++) {
                        First_Fetcher = RequestStackId.get(q).toString().substring(0, 10);

                        //Toast.makeText(ListUsersActivity.this, "sdsd sdooj n" + First_Fetcher, Toast.LENGTH_SHORT).show();

                        Second_Fetcher = RequestStackId.get(q).toString().substring(10, 20);

                        if (currentUserId.contentEquals(First_Fetcher)) {
                            // Second Fetcher is the user of another..run a query here
                            alontheway = alontheway + 1;
                            Req.add(Second_Fetcher);
                            CheckerOfActiveFriends(First_Fetcher, Second_Fetcher, 1);
                            // Toast.makeText(ListUsersActivity.this, "Manjhi "+q, Toast.LENGTH_SHORT).show();

                        } else if (currentUserId.contentEquals(Second_Fetcher)) {
                            Req.add(First_Fetcher);
                            alontheway++;
                            CheckerOfActiveFriends(Second_Fetcher, First_Fetcher, 1);
                            // Toast.makeText(ListUsersActivity.this, "Mountain "+q, Toast.LENGTH_SHORT).show();
                        }
                        else{
                            pd.dismiss();
                        }
                        // Toast.makeText(ListUsersActivity.this, "funny  " + funny, Toast.LENGTH_SHORT).show();
                    }

                }
                    Log.d("JOKERALA",""+namesd.size());
         Log.d("HELLO","R0 " + Req.size());
                    mSwipeRefreshLayout.setRefreshing(false);
                    mSwipeRefreshLayout1.setRefreshing(false);
                    // Toast.makeText(ListUsersActivity.this, "Amigo Couldn't find any friends of yours.Please add some.Amigo loves you <3 ", Toast.LENGTH_SHORT).show();
                }
                else{
                    e.printStackTrace();
                }
            }
        });

    }

    private void CheckerOfActiveFriends(final String first, final String second, int ni) {
        mSwipeRefreshLayout.setRefreshing(false);
        StringBuilder cmpunk = new StringBuilder();
        namesd = new ArrayList<String>();
        nickNames = new ArrayList<String>();
        arry = new ArrayList<String>();
        cmpunk.append(first);
        cmpunk.append(second);
        String Resultant_Id = cmpunk.toString();
        StringBuilder ck = new StringBuilder();
        ck.append(second);
        ck.append(first);
        final String re2 = ck.toString();

        if(Karna.contentEquals(first) && ni ==1){
            check++;
            secondary = second;
            ParseFile im;


        ParseQuery<ParseObject> osum = ParseQuery.getQuery("FriendRequests");
        osum.whereEqualTo("IdCounter","ActiveFriends");

            osum.whereEqualTo("AmigoChatId",Resultant_Id);
            osum.findInBackground(new FindCallback<ParseObject>() {
                @Override
                public void done(List<ParseObject> userList, ParseException e) {
                    if (e == null) {
                        // Toast.makeText(ListUsersActivity.this, "Size should be 1 but its " + userList.size(), Toast.LENGTH_SHORT).show();
                        if (userList.size() == 0) {

                            ParseQuery<ParseObject> osum = ParseQuery.getQuery("FriendRequests");
                            osum.whereEqualTo("IdCounter","ActiveFriends");

                            osum.whereEqualTo("AmigoChatId",re2);
                            osum.findInBackground(new FindCallback<ParseObject>() {
                                @Override
                                public void done(List<ParseObject> userList, ParseException e) {
                                    if (e == null) {
                                        // Toast.makeText(ListUsersActivity.this, "Size should be 1 but its " + userList.size(), Toast.LENGTH_SHORT).show();
                                        if (userList.size() == 0) {

                                            // Toast.makeText(ListUsersActivity.this, "Not active friends", Toast.LENGTH_SHORT).show();
                                        } else {

                                            // Toast.makeText(ListUsersActivity.this, "Found the list six", Toast.LENGTH_SHORT).show();
                                            ParseQuery<ParseUser> query = ParseUser.getQuery();
                                            query.whereEqualTo("objectId", second);
                                            query.addDescendingOrder("updatedAt");
                                            query.findInBackground(new FindCallback<ParseUser>() {
                                                @Override
                                                public void done(List<ParseUser> list, ParseException e) {
                                                    if (e == null) {
                                                        if (list.size() == 0) {

                                                        } else {
                                                            //Setting the adapter :D
                                                            // Log.d("List bug occuring here", " " + list.size());
                                                            z= z+1;
                                                            for (int i = 0; i < list.size(); i++) {
                                                                //     if (namesd.size() <= list.size()) {
                                                                nickNames.add(list.get(i).getUsername().toString());
                                                                namesd.add(list.get(i).get("FirstName").toString());

                                                                objector.add(list.get(i).getObjectId().toString());


                                                                // check++;


                                                                // SetTheAdapter(namesd.get(i).toString());
                                                            }

                                                            getTheObjectIdofAmigoFriends(objector,namesd);
                                                            try {
                                                                mainL.animate().alpha(1.0f);
                                                                mainL.setVisibility(View.VISIBLE);
                                                                defaultL.setVisibility(View.GONE);
                                                                recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
                                                                recyclerView.setHasFixedSize(true);
                                                                recyclerView.setAdapter(new TestRecyclerAdapter(getApplicationContext(),namesd,objector));
                                                            }
                                                            catch(Exception e1){
                                                                e1.printStackTrace();
                                                            }

                                                        }
                                                    }
                                                    else{
                                                        e.printStackTrace();
                                                    }
                                                }
                                            });
                                        }

                                    }

                                    else

                                    {
                                        e.printStackTrace();
                                        Toast.makeText(getApplicationContext(),
                                                "Error loading the pins",
                                                Toast.LENGTH_LONG).show();
                                    }
                                }
                            });

                            // Toast.makeText(ListUsersActivity.this, "Not active friends", Toast.LENGTH_SHORT).show();
                        } else {

                            // Toast.makeText(ListUsersActivity.this, "Found the list six", Toast.LENGTH_SHORT).show();
                            ParseQuery<ParseUser> query = ParseUser.getQuery();
                            query.whereEqualTo("objectId", second);
                            query.addDescendingOrder("updatedAt");
                            query.findInBackground(new FindCallback<ParseUser>() {
                                @Override
                                public void done(List<ParseUser> list, ParseException e) {
                                    if (e == null) {
                                        if (list.size() == 0) {

                                        } else {
                                            //Setting the adapter :D
                                           // Log.d("List bug occuring here", " " + list.size());
                                            z= z+1;
                                            for (int i = 0; i < list.size(); i++) {
                                           //     if (namesd.size() <= list.size()) {
                                                    nickNames.add(list.get(i).getUsername().toString());
                                                    namesd.add(list.get(i).get("FirstName").toString());

                                                objector.add(list.get(i).getObjectId().toString());


                                               // check++;


                                               // SetTheAdapter(namesd.get(i).toString());
                                            }

                                            getTheObjectIdofAmigoFriends(objector,namesd);
                                            try {
                                                mainL.animate().alpha(1.0f);
                                                mainL.setVisibility(View.VISIBLE);
                                                defaultL.setVisibility(View.GONE);
                                                defaultL.animate().alpha(0.0f);
                                                recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
                                                recyclerView.setHasFixedSize(true);
                                                recyclerView.setAdapter(new TestRecyclerAdapter(getApplicationContext(),namesd,objector));
                                            }
                                            catch(Exception e1){
                                            e1.printStackTrace();
                                        }

                                    }
                                }
                            }
                        });
                    }

                }

                else

                {
                    e.printStackTrace();
                    Toast.makeText(getApplicationContext(),
                            "Error loading pins",
                            Toast.LENGTH_LONG).show();
                }
            }
        });

        }
        else {
            check++;
            reverseOpinion = second;
            ParseQuery<ParseObject> osum = ParseQuery.getQuery("FriendRequests");
            osum.whereEqualTo("IdCounter","ActiveFriends");
            osum.whereEqualTo("AmigoChatId", Resultant_Id);
            osum.findInBackground(new FindCallback<ParseObject>() {
                @Override
                public void done(List<ParseObject> userList, ParseException e) {
                    if (e == null) {
                        // Toast.makeText(ListUsersActivity.this, "Size should be 1 but its " + userList.size(), Toast.LENGTH_SHORT).show();
                        if (userList.size() == 0) {
                            // Toast.makeText(ListUsersActivity.this, "Not active friends", Toast.LENGTH_SHORT).show();
                        } else {
                            // Toast.makeText(ListUsersActivity.this, "Found the list six", Toast.LENGTH_SHORT).show();
                            ParseQuery<ParseUser> query = ParseUser.getQuery();
                            query.whereEqualTo("objectId", reverseOpinion);
                            query.addAscendingOrder("updatedAt");
                            query.findInBackground(new FindCallback<ParseUser>() {
                                @Override
                                public void done(List<ParseUser> list, ParseException e) {
                                    if (e == null) {
                                        if (list.size() == 0) {

                                        } else {
                                            //Setting the adapter :D
                                            // Log.d("List bug occuring here", " " + list.size());
                                            z = z + 1;
                                            for (int i = 0; i < list.size(); i++) {
                                                //     if (namesd.size() <= list.size()) {
                                                nickNames.add(list.get(i).getUsername().toString());
                                                namesd.add(list.get(i).get("FirstName").toString());
                                                objector.add(list.get(i).getObjectId().toString());


                                                // SetTheAdapter(namesd.get(i).toString());
                                            }

                                                    getTheObjectIdofAmigoFriends(objector,namesd);
                                            try {

                                                recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
                                                recyclerView.setHasFixedSize(true);
                                                recyclerView.setAdapter(new TestRecyclerAdapter(getApplicationContext(), namesd, objector));
                                            } catch (Exception e1) {
                                                e1.printStackTrace();
                                            }

                                        }
                                    }
                                }
                            });
                        }

                    } else

                    {
                        e.printStackTrace();
                        Toast.makeText(getApplicationContext(),
                                "Error loading the pins",
                                Toast.LENGTH_LONG).show();
                    }
                }
            });
           // Toast.makeText(ListUsersActivity.this, "Reverse Opinion", Toast.LENGTH_SHORT).show();
        }
       // mekia.dismiss();
        pd.dismiss();
    }

    private void getTheObjectIdofAmigoFriends(ArrayList<String> objector,ArrayList<String> namesdr) {
was = namesdr;
        Log.d("FFD",""+namesdr.size());
war = objector;


    }

    private void updateNotificationsBadge(int count) {

        mNotificationsCount = count;

        // force the ActionBar to relayout its MenuItems.
        // onCreateOptionsMenu(Menu) will be called again.
        invalidateOptionsMenu();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_home, menu);

        // Get the notifications MenuItem and
        // its LayerDrawable (layer-list)
        MenuItem item = menu.findItem(R.id.action_notifications);
        LayerDrawable icon = (LayerDrawable) item.getIcon();
        SearchManager searchManager =
                (SearchManager) getSystemService(Context.SEARCH_SERVICE);
        SearchView searchView =
                (SearchView) menu.findItem(R.id.searcher).getActionView();

        // Update LayerDrawable's BadgeDrawable
        Utils.setBadgeCount(this, icon, mNotificationsCount);
        searchView.setSearchableInfo(
                searchManager.getSearchableInfo(getComponentName()));
        LinearLayout ll = (LinearLayout)searchView.getChildAt(0);
        LinearLayout ll2 = (LinearLayout)ll.getChildAt(2);
        LinearLayout ll3 = (LinearLayout)ll2.getChildAt(1);
        SearchView.SearchAutoComplete autoComplete = (SearchView.SearchAutoComplete)ll3.getChildAt(0);
         // set the hint text color
        autoComplete.setHint("Amigo name");

        autoComplete.setHintTextColor(Color.parseColor("#BDBDBD"));
         // set the text color
        autoComplete.setTextColor(Color.WHITE);
        return true;
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        if (item.getItemId() == R.id.action_notifications) {
            // TODO: display unread notifications.


            //Right Now It'll Accept all of the friend Request!!
            //Also Put some other value in the RequestStack to prevent the notibadge from generating the notifications on runtime :)
        if(k!=0) {


            Intent rogue = new Intent();
            rogue.setClass(ListUsersActivity.this, NotificationsActivity.class);
            try {
                rogue.putStringArrayListExtra("FetcherID", Final_Object_Id_Fetcher);
                rogue.putStringArrayListExtra("AmigoID", Amigo_Id_Fetcher);
            } catch (Exception e) {
                e.printStackTrace();
            }

            startActivity(rogue);
        }
            else{
            Toast.makeText(ListUsersActivity.this, "No new Notifications", Toast.LENGTH_SHORT).show();
        }
            return true;
        }
        else{

        }
        return super.onOptionsItemSelected(item);
    }
    //open a conversation with one person
    public void openConversation(ArrayList<String> names, int pos) {
        ParseQuery<ParseUser> query = ParseUser.getQuery();
        //query.whereEqualTo("isgroup",true);
        query.whereEqualTo("FirstName", names.get(pos));
        query.findInBackground(new FindCallback<ParseUser>() {
            public void done(List<ParseUser> user, com.parse.ParseException e) {
                if (e == null) {
                    Intent intent = new Intent(getApplicationContext(), FriendMessagingActivity.class);
                    intent.putExtra("RECIPIENT_ID", user.get(0).getObjectId());
                    startActivity(intent);
                } else {
                    e.printStackTrace();
                    Toast.makeText(getApplicationContext(),
                            "Error finding that user",
                            Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    //show a loading spinner while the sinch client starts
    private void showSpinner() {

        receiver = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                Boolean success = intent.getBooleanExtra("success", false);
               // progressDialog.dismiss();
                if (!success) {
                    Toast.makeText(getApplicationContext(), "Messaging service failed to start", Toast.LENGTH_LONG).show();
                }
            }
        };

        LocalBroadcastManager.getInstance(this).registerReceiver(receiver, new IntentFilter(" chap.hanuor.com.chatapp.ListUsersActivity"));
    }

    @Override
    public void onResume() {
        //To start the add status activity for the forst time only.
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(getBaseContext());
        boolean previouslyStarted = prefs.getBoolean(getString(R.string.pref_previously_started), false);
        if(!previouslyStarted) {
            SharedPreferences.Editor edit = prefs.edit();
            edit.putBoolean(getString(R.string.pref_previously_started), Boolean.TRUE);
            edit.commit();
           Intent fp = new Intent();
            fp.setClass(ListUsersActivity.this,AddStatus.class);
            startActivity(fp);
            // showHelp();
        }
        //over
        if(resumer == 0) {
            RotateAnimation rotateAnimation = new RotateAnimation(180, 360,
                    Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
            rotateAnimation.setDuration((long) 2 * 1000);

            amigoImage.setAnimation(rotateAnimation);
            resumer = 1;
        }
        else{


      //  setConversationsList();
        }
            //k=0;
        //Amigo_Id_Fetcher = new ArrayList<String>();
        //setupFriendRequestMethod();
        super.onResume();
    }

    private class TestRecyclerAdapter extends RecyclerView.Adapter <TestRecyclerAdapter.ViewHolder>{
        private LayoutInflater inflater;
        ArrayList<String> nam;
        ArrayList<String> nickNamesReceiver;
        ArrayList<String> obj;
        Bitmap mop;

        int[] profileImages = new int[10];
        public TestRecyclerAdapter(Context context, ArrayList<String> nam, ArrayList<String> obj) {
            this.inflater = LayoutInflater.from(context);
            this.nam = nam;
            this.obj = obj;

            int s = nam.size();



        }

        @Override
        public ViewHolder onCreateViewHolder(final ViewGroup parent, final int viewType) {
            View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.duplicate_item_parallax,parent,false);
           v.setOnClickListener(new View.OnClickListener() {
               @Override
               public void onClick(View view) {
                   int pos = recyclerView.getChildPosition(view);
                   String mList = obj.get(pos);
                   Intent dexter = new Intent();
                   dexter.putExtra("ObjectID",mList);
                   dexter.setClass(ListUsersActivity.this,FriendsProfileActivty.class);
                   startActivity(dexter);
                   overridePendingTransition(R.anim.lefttoright,R.anim.righttoleft);
                   //openConversation(nickName
                   // sReceiver,pos);

               }
           });
            return new ViewHolder(v);
           // return new ViewHolder(inflater.inflate(R.layout.item_parallex, parent, false));
        }

        @Override
        public void onBindViewHolder(ViewHolder viewHolder, int position) {
            //fetch_the_Likes(viewHolder,position,obj.get(position));
            fetch_the_profil_photo(viewHolder, position, obj.get(position),viewHolder.getImView().getId());
            increment_the_likes(viewHolder, position, obj.get(position));
            fetch_the_Status(viewHolder, position, obj.get(position));
           // viewHolder.getBackgroundImage().setImageResource(imageIds[position % imageIds.length]);
            Animation fadeIn = new AlphaAnimation(0, 1);
            fadeIn.setInterpolator(new DecelerateInterpolator()); //add this
            fadeIn.setDuration(1000);
            viewHolder.getTextView().setAnimation(fadeIn);
            viewHolder.getTextView().setText(" " + nam.get(position));
           // viewHolder.getImView().setImageResource(imageIdsp[position%imageIdsp.length]);



            // # CAUTION:
            // Important to call this method
            viewHolder.getBackgroundImage().reuse();
        }


        @Override
        public int getItemCount() {
            return nam.size();
        }
        public  class ViewHolder extends ParallaxViewHolder {

            private final TextView textView;
            private final TextView heart;
            private final ImageView imager;
            private final TextView statusView;
            private final TextView commentnumber;
            private final ImageView heartImage;
            private final ImageView optionsImage;
            private final TextView sharetext;
            public ViewHolder(View v) {
                super(v);

                textView = (TextView) v.findViewById(R.id.label);
                commentnumber = (TextView) v.findViewById(R.id.commentText);
                heart = (TextView) v.findViewById(R.id.heartText);
                sharetext = (TextView) v.findViewById(R.id.shareText);
                statusView = (TextView) v.findViewById(R.id.statusText);
                imager = (ImageView) v.findViewById(R.id.imageView);
                final View thumb1View = findViewById(R.id.imageView);
                heartImage = (ImageView) v.findViewById(R.id.imageView2);
                optionsImage = (ImageView) v.findViewById(R.id.optionsSingle);
                optionsImage.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        //Toast.makeText(ListUsersActivity.this, "vds", Toast.LENGTH_SHORT).show();
                        PopupMenu popup = new PopupMenu(ListUsersActivity.this,optionsImage);
                        popup.getMenuInflater().inflate(R.menu.popup_menu,popup.getMenu());
                        popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener(){

                            @Override
                            public boolean onMenuItemClick(MenuItem item) {
                                was.remove(getPosition());
                                notifyItemRemoved(getPosition());
                                final int posy = getPosition();
                                final StringBuilder ask2 = new StringBuilder();
                                String currentUser = ParseUser.getCurrentUser().getObjectId().toString();
                                ask2.append(currentUser);
                                ask2.append(obj.get(posy).toString());
                                String Am_ID = ask2.toString();
                                ParseQuery<ParseObject> ghk = ParseQuery.getQuery("FriendRequests");
                                ghk.whereEqualTo("AmigoChatId", Am_ID);
                                ghk.findInBackground(new FindCallback<ParseObject>() {
                                    @Override
                                    public void done(List<ParseObject> list, ParseException e) {
                                        if (e == null) {
                                            if (list.size() != 0) {
                                                String fds = list.get(0).getObjectId().toString();
                                                ParseQuery<ParseObject> query = ParseQuery.getQuery("FriendRequests");
                                                query.getInBackground(fds, new GetCallback<ParseObject>() {
                                                    @Override
                                                    public void done(ParseObject parseObject, ParseException e) {
                                                        try {
                                                            parseObject.delete();
                                                            parseObject.saveInBackground();
                                                        } catch (ParseException e1) {
                                                            e1.printStackTrace();
                                                        }
                                                    }
                                                });
                                            }else{

                                                StringBuilder ask3 = new StringBuilder();
                                                String currentUser = ParseUser.getCurrentUser().getObjectId().toString();
                                                ask3.append(obj.get(posy).toString());
                                                ask3.append(currentUser);

                                                String Am_ID = ask3.toString();
                                                ParseQuery<ParseObject> ghk = ParseQuery.getQuery("FriendRequests");
                                                ghk.whereEqualTo("AmigoChatId", Am_ID);
                                                ghk.findInBackground(new FindCallback<ParseObject>() {
                                                    @Override
                                                    public void done(List<ParseObject> list, ParseException e) {
                                                        if (e == null) {
                                                            if (list.size() != 0) {
                                                                String fds = list.get(0).getObjectId().toString();
                                                                ParseQuery<ParseObject> query = ParseQuery.getQuery("FriendRequests");
                                                                query.getInBackground(fds, new GetCallback<ParseObject>() {
                                                                    @Override
                                                                    public void done(ParseObject parseObject, ParseException e) {
                                                                        try {
                                                                            parseObject.delete();
                                                                            parseObject.saveInBackground();
                                                                        } catch (ParseException e1) {
                                                                            e1.printStackTrace();
                                                                        }
                                                                    }
                                                                });
                                                            }else{

                                                            }
                                                        }
                                                    }
                                                });


                                            }
                                        }
                                        else{
                                            e.printStackTrace();
                                        }
                                    }
                                });





                                return true;
                            }
                        });
                        popup.show();

                    }
                });
                final int counterStrike = 0;

                final int initialWidther = imager.getWidth();
                final int initialHeighter = imager.getHeight();
                imager.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        int pos = getPosition();
                        Intent sek = new Intent();
                        sek.setClass(ListUsersActivity.this,ParallelSocialNotoriety.class);
                        sek.putExtra("Object",obj.get(pos));
                        startActivity(sek);
 }
                });


            }

            @Override
            public int getParallaxImageId() {
                return R.id.backgroundImage;
            }

            public TextView getTextView() {
                return textView;
            }
            public TextView getheartView() {
                return heart;
            }
            public TextView getTextComment(){return commentnumber;}
            public TextView getsharetextView(){return sharetext;}
            public ImageView getheartImageView() {
                return heartImage;
            }
            public TextView getstatusTextView() {
                return statusView;
            }
            public ImageView getImView() {
                return imager;
            }
            public ImageView getImOptionsView() {
                return optionsImage;
            }
        }
    }


    private int  mosley() {
        int kelo = sec;
        sec++;
        return kelo;

    }

    private void increment_the_likes(final TestRecyclerAdapter.ViewHolder vh,int posi,String ohja) {

        final String currentUser = ParseUser.getCurrentUser().getObjectId().toString();

        ParseQuery<ParseObject> qw = ParseQuery.getQuery("AmigoPosts");
        qw.whereEqualTo("CreatorId",ohja);
        qw.findInBackground(new FindCallback<ParseObject>() {
            @Override
            public void done(List<ParseObject> list, ParseException e) {
                if(e == null){

                try {
                    if (list.size() != 0) {
                        String shaers = list.get(0).get("ShareCount").toString();
                        vh.getsharetextView().setText(shaers);
                        String amal = list.get(0).get("CommentsString").toString();
                        String Comments[] = amal.split("\\%3:11071995:11");
                        Log.v("Bodsl", "" + Comments[0]);
                        if (Comments[0].equals(" ")) {
                            vh.getTextComment().setText("0");
                        } else {
                            vh.getTextComment().setText("" + Comments.length);
                        }

                        String objId = list.get(0).getObjectId().toString();
                        ParseQuery<ParseObject> qew = ParseQuery.getQuery("AmigoPosts");
                        qew.whereEqualTo("objectId", objId);
                        qew.findInBackground(new FindCallback<ParseObject>() {
                            @Override
                            public void done(List<ParseObject> list, ParseException e) {
                                if (e == null) {
                                    if (list.size() != 0) {
                                        // Toast.makeText(ListUsersActivity.this, "found", Toast.LENGTH_SHORT).show();
                                        try {
                                            vh.getheartView().setText(list.get(0).get("Likes").toString());
                                        } catch (Exception e1) {
                                            e1.printStackTrace();
                                        }

                                    }
                                }else{
                                    e.printStackTrace();
                                }
                            }
                        });


                    } else {
                        // Toast.makeText(ListUsersActivity.this, "Not Found", Toast.LENGTH_SHORT).show();
                        //e.printStackTrace();
                    }
                } catch (Exception e1) {
                    e1.printStackTrace();
                }
            }
                else{
                    e.printStackTrace();
                }
            }
        });


    }


    void fetch_the_Status(final TestRecyclerAdapter.ViewHolder vh,int posi,String ohja){
        ParseQuery<ParseUser> query = ParseUser.getQuery();
        query.getInBackground(ohja, new GetCallback<ParseUser>() {
            @Override
            public void done(ParseUser parseUser, ParseException e) {
                if (e == null) {
                    try {

                        String af = parseUser.get("UserStatus").toString();
                        Animation fadeIn = new AlphaAnimation(0, 1);
                        fadeIn.setInterpolator(new DecelerateInterpolator()); //add this
                        fadeIn.setDuration(1000);
                        vh.getstatusTextView().setAnimation(fadeIn);
                        vh.getstatusTextView().setText(af);
                    } catch (Exception t) {
                        t.printStackTrace();
                    }
                    //  String heartCount = parseUser.get("StatusLikes").toString();
                    // vh.getheartView().setText(heartCount);

                }
                else{
                    e.printStackTrace();
                }
            }
        });
    }

    private void fetch_the_profil_photo(final TestRecyclerAdapter.ViewHolder vh, int posi, String ohja, final int imID) {
        //Fetch the profile picture
        final String objectIdOfUser = ohja;
        Log.v("Okjl",""+ohja);
        ParseQuery<ParseUser> query = ParseUser.getQuery();
        query.getInBackground(objectIdOfUser, new GetCallback<ParseUser>() {
            @Override
            public void done(final ParseUser parseUser, ParseException e) {
                if (e == null) {
                    ParseFile picture = parseUser.getParseFile("photo");
                   // final String url = parseUser.getParseFile("photo").getUrl();
                //    if (url!= null) {

                        if(picture!=null) {
                            final String url = parseUser.getParseFile("photo").getUrl();
                            picture.getDataInBackground(new GetDataCallback() {
                                @Override
                                public void done(byte[] data, ParseException e) {
                                    if (e == null) {
                                        try {
                                            if (data.length == 0) {

                                                // data found, but nothing to extract. bad image or upload?
                                                return; // failing out
                                            }


                                            //Toast.makeText(ListUsersActivity.this, "Obejct Id fetched and photo" + objectIdOfUser, Toast.LENGTH_SHORT).show();
                                            Bitmap bmp = BitmapFactory.decodeByteArray(data, 0, data.length);
                                            Animation fadeIn = new AlphaAnimation(0, 1);
                                            fadeIn.setInterpolator(new DecelerateInterpolator()); //add this
                                            fadeIn.setDuration(1000);
                                            vh.getImView().setAnimation(fadeIn);
                                             Picasso.with(getBaseContext()).load(url).into(vh.getImView());
                                            //vh.getImView().setImageBitmap(bmp);
                                            // SUCCESS
                                            // convert data and display in an imageview
                                        } catch (Exception e1) {
                                            e1.printStackTrace();
                                        }
                                    } else {
                                        e.printStackTrace();
                                        // ParseFile contained no data. data not added to ParseFile?
                                    }
                                }
                            });
                        }



                        //holder.chatImageView.setVisibility(View.VISIBLE);
                        //String chatImageFile = message.getParseFile("image").getUrl();

//                        Picasso.with(mContext)
  //                              .load(chatImageFile)
    //                            .into(holder.chatImageView);

                   // } else {

                       // holder.chatImageView.setVisibility(View.GONE);
                  //  }
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
                                    try {
                                        if(bytes.length !=0){
                                            String usl = parseUser.getParseFile("CoverPicture").getUrl();
                                            Bitmap bmp = BitmapFactory.decodeByteArray(bytes, 0, bytes.length);

                                            Display display = getWindowManager().getDefaultDisplay();
                                            Point size = new Point();
                                            display.getSize(size);
                                            int width = size.x;

                                            Bitmap dstBmp;
                                            try {
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
                                           // vh.getImView().setImageBitmap(bmp);
                                            Animation fadeIn = new AlphaAnimation(0, 1);
                                            fadeIn.setInterpolator(new DecelerateInterpolator()); //add this
                                            fadeIn.setDuration(1000);
                                            vh.getBackgroundImage().setAnimation(fadeIn);
                                                Picasso.with(getBaseContext()).load(usl).fit().into(vh.getBackgroundImage());
                                           // vh.getBackgroundImage().setImageBitmap(nomans);
                                            } catch (Exception e1) {
                                                e1.printStackTrace();
                                            }
                                        }
                                        else{
                                            Animation fadeIn = new AlphaAnimation(0, 1);
                                            fadeIn.setInterpolator(new DecelerateInterpolator()); //add this
                                            fadeIn.setDuration(1000);
                                            vh.getBackgroundImage().setAnimation(fadeIn);
                                            vh.getBackgroundImage().setImageDrawable(getResources().getDrawable(R.drawable.abs));
                                        }
                                    } catch (Resources.NotFoundException e1) {
                                        e1.printStackTrace();
                                    }
                                }else{
                                    e.printStackTrace();
                                }

                            }
                        });
                    } catch (Exception e1) {
                        e1.printStackTrace();
                    }

                } else {
                    e.printStackTrace();
                    // no users had this objectId
                }
            }
        });

    }

}
