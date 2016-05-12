package chap.hanuor.com.chatapp;

import android.content.Intent;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.widget.Button;

import com.flaviofaria.kenburnsview.KenBurnsView;
import com.flaviofaria.kenburnsview.RandomTransitionGenerator;
import com.parse.FindCallback;
import com.parse.GetCallback;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.ParseUser;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

/**
 * Created by Shantanu Johri on 12-11-2015.
 */
public class RandomChat extends AppCompatActivity {

    int counterCount = 0;
    String currentUserId;

    private ScheduledExecutorService scheduleTaskExecutor;
    ScheduledExecutorService sc;
    Button b2;
    ArrayList<String> Compare_userList;
    String Secondopi;
    int x = 0, y = 0;
    Handler custo;
    Intent ui;
    Intent yu;
    Toolbar to;
    KenBurnsView kbv;
    int theUsual = 0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_random_chat);
        to = (Toolbar) findViewById(R.id.toolbar);
        //to.setTitle("Settings");
        setSupportActionBar(to);
        getSupportActionBar().setDefaultDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        final Drawable upArrow = getResources().getDrawable(R.drawable.abc_ic_ab_back_mtrl_am_alpha);
        upArrow.setColorFilter(getResources().getColor(R.color.bg_glass), PorterDuff.Mode.SRC_ATOP);
        getSupportActionBar().setHomeAsUpIndicator(upArrow);
        //b2 = (Button) findViewById(R.id.button2);
        // b2.setVisibility(View.GONE);
        kbv = (KenBurnsView) findViewById(R.id.imageCover);
        AccelerateDecelerateInterpolator ACCELERATE_DECELERATE = new AccelerateDecelerateInterpolator();
        RandomTransitionGenerator generator = new RandomTransitionGenerator(10000, ACCELERATE_DECELERATE);
        kbv.setTransitionGenerator(generator); //set new transition on kbv

        currentUserId = ParseUser.getCurrentUser().getObjectId().toString();
        Compare_userList = new ArrayList<String>();
        custo = new Handler();
        ui = new Intent();
        yu = new Intent();
        ui.setClass(RandomChat.this,MessagingActivity.class);
        sc = Executors.newSingleThreadScheduledExecutor();
        Check_the_values();

        scheduleTaskExecutor = Executors.newSingleThreadScheduledExecutor();

        //setConversationsList();
        // Checker_method();

    }

    private void callme() {
        startActivity(ui);
    }

public void Check_the_values(){
        sc.scheduleAtFixedRate(new Runnable() {
    @Override
    public void run() {
        ParseQuery<ParseObject> qery = ParseQuery.getQuery("Randomly");
        qery.whereEqualTo("TriggerValue", "RandomChatRequest");
        qery.whereNotEqualTo("SenderID",currentUserId);
        qery.findInBackground(new FindCallback<ParseObject>() {
            @Override
            public void done(List<ParseObject> list, ParseException e) {
//try
                if (list.size() != 0) {
                    //sEARCH successfyll put accept request and start messaging activity
                    //Start Intent
                    final String obj;
                    try {
                        obj = list.get(0).getObjectId().toString();
                        ParseQuery<ParseObject> reqw = ParseQuery.getQuery("Randomly");
                        reqw.getInBackground(obj, new GetCallback<ParseObject>() {
                            @Override
                            public void done(ParseObject parseObject, ParseException e) {

                                parseObject.put("TriggerValue", "AcceptRequest");
                                parseObject.put("RecieverID",currentUserId);
                                parseObject.saveInBackground();
                            }
                        });

                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            counterCount++;
                            if(counterCount<=1) {
                                //Random
                                Log.d("Counter Running"," "+counterCount);
                                ParseQuery<ParseObject> rheq = ParseQuery.getQuery("Randomly");
                                rheq.whereEqualTo("objectId",obj);
                                rheq.findInBackground(new FindCallback<ParseObject>() {
                                    @Override
                                    public void done(List<ParseObject> list, ParseException e) {
                                        if(list.size()!=0){
                                            String re = list.get(0).get("SenderID").toString();
                                            Intent weak = new Intent();

                                            weak.setClass(RandomChat.this, MessagingActivity.class);
                                            weak.putExtra("RECIPIENT_ID",re);
                                            startActivity(weak);
                                            finish();
                                        }
                                    }
                                });

                               // startActivity(yu);

                                //Delete_the_Created_Row();

                            }
                            sc.shutdown();
                            sc.shutdownNow();
                            return;
                        }
                    });
                    } catch (Exception e1) {
                        e1.printStackTrace();
                    }

                } else {
                    //listsize is zero
                    //Exit this function!
                    counterCount++;
                    if (counterCount <= 1) {

                        Initialize_Table_Values();

                    }
                    sc.shutdown();
                    sc.shutdownNow();
                    return;
                }
            }
        });
    }
}, 0, 100, TimeUnit.MILLISECONDS);

}
    public void Initialize_Table_Values() {
        ParseQuery<ParseObject> qa = ParseQuery.getQuery("Randomly");
        qa.whereEqualTo("TriggerValue", "RandomChatRequest");
        qa.findInBackground(new FindCallback<ParseObject>() {
            @Override
            public void done(List<ParseObject> list, ParseException e) {
                if (e == null) {
                    if (list.size() == 0) {
                        //Enable the put option.This means that no requestoption is found. Put the request option in the database along with the request sender ID.
                        ParseObject firki = new ParseObject("Randomly");
                        firki.put("SenderID", currentUserId);
                        firki.put("TriggerValue", "RandomChatRequest");
                        firki.put("RecieverID","Waiting");
                        firki.saveInBackground();
                       // String metador_object  = list.get(0).getObjectId().toString();
                       // Object_Id_OF_Randomly(metador_object);
/*

                        String putterObject = list.get(0).getObjectId().toString();
                        ParseQuery<ParseObject> qqa = ParseQuery.getQuery("Randomly");
                        qqa.whereEqualTo("objectId", putterObject);
                        qqa.findInBackground(new FindCallback<ParseObject>() {
                            @Override
                            public void done(List<ParseObject> list, ParseException e) {
                                if (e == null) {
                                    if (list.size() == 0) {
                                        ParseObject firki = new ParseObject("Randomly");
                                        firki.put("SenderID", currentUserId);
                                        firki.put("TriggerValue", "RandomChatRequest");
                                        firki.saveInBackground();

                                    }
                                } else {
                                    e.printStackTrace();
                                }
                            }
                        });
*/

                    }
                }
            }
        });
        After_Initialize_check_Again();
    }
    public void After_Initialize_check_Again(){
        scheduleTaskExecutor.scheduleWithFixedDelay(new Runnable() {
            @Override
            public void run() {
                ParseQuery<ParseObject> qery = ParseQuery.getQuery("Randomly");
                qery.whereEqualTo("TriggerValue", "AcceptRequest");
                qery.findInBackground(new FindCallback<ParseObject>() {
                    @Override
                    public void done(final List<ParseObject> list, ParseException e) {
                        if (e == null) {


                            try {
                                if (list.size() != 0) {
                                    scheduleTaskExecutor.shutdownNow();
                                    scheduleTaskExecutor.shutdown();

                                    runOnUiThread(new Runnable() {
                                        public void run() {

                                            theUsual++;
                                            if (theUsual <= 1) {
                                                String myso = list.get(0).getObjectId().toString();
                                                Log.d("Call ne maybe"," "+theUsual);
                                                ParseQuery<ParseObject> rheq = ParseQuery.getQuery("Randomly");
                                                rheq.whereEqualTo("objectId",myso);
                                                rheq.findInBackground(new FindCallback<ParseObject>() {
                                                    @Override
                                                    public void done(List<ParseObject> list, ParseException e) {
                                                        if(list.size()!=0){
                                                            String re = list.get(0).get("RecieverID").toString();

                                                            Intent Creator = new Intent();

                                                            Creator.setClass(RandomChat.this, FriendMessagingActivity.class);
                                                            Creator.putExtra("RECIPIENT_ID",re);
                                                            Log.d("Chal gaya"," geyeyeyeye");
                                                            startActivity(Creator);
                                                            finish();
                                                        }
                                                    }
                                                });

                                                //Delete the row after some time like 200ms
                                                // Delete_the_Created_Row();
                                                // Initialize_Table_Values();
                                            }

                                        }
                                    });
                                }
                            } catch (Exception e1) {
                                e1.printStackTrace();
                            }
                        } else {
                            e.printStackTrace();
                          /*  try {

                                //Wait the scheduler 300ms so that it doesn't generate error when value in the database changes
                               // scheduleTaskExecutor.notify();
                             //   scheduleTaskExecutor.wait(300);
                            } catch (InterruptedException e1) {
                                e1.printStackTrace();
                            }*/
                        }
                    }

                });

            }
        }, 0, 200, TimeUnit.MILLISECONDS);


    }
    public void Delete_the_Created_Row(){
        ParseQuery<ParseObject> ghk = ParseQuery.getQuery("Randomly");
        ghk.whereEqualTo("SenderID",currentUserId);
        ghk.findInBackground(new FindCallback<ParseObject>() {
            @Override
            public void done(List<ParseObject> list, ParseException e) {
                if(e == null){
                    if(list.size()!=0){
                        String fds = list.get(0).getObjectId().toString();
                        ParseQuery<ParseObject> query = ParseQuery.getQuery("Randomly");
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
                    }
                }
            }
        });

    }

    private void Object_Id_OF_Randomly(String stringer) {
        Secondopi = stringer;

    }
    @Override
    public void onBackPressed() {
        // do something on back.
        scheduleTaskExecutor.shutdownNow();
        scheduleTaskExecutor.shutdown();

        //deleting the object as the random search has been exited!
Delete_the_Created_Row();

        super.onBackPressed();
        //return;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem menuItem) {
        if (menuItem.getItemId() == android.R.id.home) {
            scheduleTaskExecutor.shutdownNow();
            scheduleTaskExecutor.shutdown();

            //deleting the object as the random search has been exited!
            Delete_the_Created_Row();
            onBackPressed();
        }
        return super.onOptionsItemSelected(menuItem);
    }

}

 /*   private void Checker_method() {
        //Check the trigger value
        ParseQuery<ParseObject> qa = ParseQuery.getQuery("Randomly");
        qa.whereEqualTo("TriggerValue","RandomChatRequest");
        qa.findInBackground(new FindCallback<ParseObject>() {
            @Override
            public void done(List<ParseObject> list, ParseException e) {
                if (e == null) {
                    if (list.size() == 0) {
                        //Enable the put option.This means that no requestoption is found. Put the request option in the database along with the request sender ID.
                        ParseObject firki = new ParseObject("Randomly");
                        firki.put("SenderID", currentUserId);
                        firki.put("TriggerValue", "RandomChatRequest");
                        firki.saveInBackground();

                        *//*String putterObject = list.get(0).getObjectId().toString();
                        ParseQuery<ParseObject> qqa = ParseQuery.getQuery("Randomly");
                        qqa.whereEqualTo("objectId",putterObject);
                        qqa.findInBackground(new FindCallback<ParseObject>() {
                            @Override
                            public void done(List<ParseObject> list, ParseException e) {
                                if(e == null){
                                if(list.size()==0){
                                    ParseObject firki = new ParseObject("Randomly");
                                    firki.put("SenderID",currentUserId);
                                    firki.put("TriggerValue","RandomChatRequest");
                                    firki.saveInBackground();

                                }
                                }
                                else{
                                    e.printStackTrace();
                                }
                            }
                        });
*//*
                    } else {
                        //query the sender ID
                        //Start the messaging activtiy using the sender ID as the recipinet.

                        String mast = list.get(0).getObjectId().toString();
                      //  String name = list.get(0).get("FirstName").toString();
                       // Toast.makeText(RandomChat.this, "Connected with = " + name, Toast.LENGTH_SHORT).show();
                        ParseQuery<ParseObject> req = ParseQuery.getQuery("Randomly");
                        req.whereEqualTo("objectId", mast);

                        req.findInBackground(new FindCallback<ParseObject>() {
                            @Override
                            public void done(List<ParseObject> list, ParseException e) {
                                if (e == null) {
                                    if (list.size() != 0) {
                                        String The_Sender_Id = list.get(0).get("SenderID").toString();
                                        //Start the messaging activity
                                        b2.setVisibility(View.VISIBLE);
                                        Toast.makeText(RandomChat.this, "Found a recipent " + The_Sender_Id, Toast.LENGTH_SHORT).show();
                                    } else {
                                        Toast.makeText(RandomChat.this, "No random stranger found!", Toast.LENGTH_SHORT).show();
                                    }

                                } else {
                                    e.printStackTrace();
                                }
                            }
                        });
                    }
                } else {
                    e.printStackTrace();
                }
            }
        });


    }


*/
  /*
    private void setConversationsList() {

        ParseQuery<ParseUser> query = ParseUser.getQuery();
        query.whereNotEqualTo("objectId", currentUserId);

        query.findInBackground(new FindCallback<ParseUser>() {
            public void done(List<ParseUser> userList, com.parse.ParseException e) {
                if (e == null) {
                    counterCount = userList.size();
                    for (int i = 0; i < counterCount; i++) {
                        Compare_userList.add(userList.get(i).getObjectId().toString());
                        Log.d("Users Primary Key", Compare_userList.get(i));
                    }

                    Random r = new Random();
                    int randomCounter = r.nextInt((counterCount ) - 0) + 0;
                    SetupRandomUser(randomCounter);
                    //Toast.makeText(RandomChat.this, "Random Number " + i1, Toast.LENGTH_SHORT).show();
                    //Toast.makeText(RandomChat.this, "Count of users" + counterCount, Toast.LENGTH_SHORT).show();


                } else {
                    Toast.makeText(getApplicationContext(),
                            "Error loading user list",
                            Toast.LENGTH_LONG).show();
                }
            }
        });
    }

    private void SetupRandomUser(int receiveCounter) {
    int res = receiveCounter;
       String ObjectIdFet =  Compare_userList.get(res).toString();
       //Reverse
        StringBuilder a1 = new StringBuilder();
        a1.append(ObjectIdFet);
        a1.append(currentUserId);
        String option1 = a1.toString();
        //Normal
        StringBuilder a2 = new StringBuilder();
        a1.append(currentUserId);
        a1.append(ObjectIdFet);
        String option2 = a1.toString();
        Object_Id_Fetcher(option2);
        Toast.makeText(RandomChat.this, " Object Id Created " + option1, Toast.LENGTH_SHORT).show();
        //query AmigoChatId
        ParseQuery<ParseObject> qa = ParseQuery.getQuery("FriendRequests");
        qa.whereEqualTo("AmigoChatId",option1);
        qa.findInBackground(new FindCallback<ParseObject>() {
            @Override
            public void done(List<ParseObject> list, ParseException e) {
                if(list.size() == 0){
                    SecondOpinion();

                }
                else{
                    Toast.makeText(RandomChat.this, "Already chatted", Toast.LENGTH_SHORT).show();
                }
            }
        });

    }

    private void Object_Id_Fetcher(String option2) {
        Secondopi = option2;
    }

    public void SecondOpinion(){

    ParseQuery<ParseObject> qa = ParseQuery.getQuery("FriendRequests");
    qa.whereEqualTo("AmigoChatId",Secondopi);
        qa.findInBackground(new FindCallback<ParseObject>() {
            @Override
            public void done(List<ParseObject> list, ParseException e) {
                if(list.size()==0){
                    // Absolutely valid Random Chat

                    Toast.makeText(RandomChat.this, "No Chat session has been created b/w these two.That's Valid", Toast.LENGTH_SHORT).show();
                }
                else{
                    Toast.makeText(RandomChat.this, "Option 2 also failed.Try again", Toast.LENGTH_SHORT).show();
                }
            }
        });
}*/


