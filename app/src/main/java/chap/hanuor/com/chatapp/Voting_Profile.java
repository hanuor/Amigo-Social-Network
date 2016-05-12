package chap.hanuor.com.chatapp;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.parse.FindCallback;
import com.parse.GetCallback;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.ParseUser;

import java.util.Arrays;
import java.util.List;

public class Voting_Profile extends AppCompatActivity {
    Button addfriend;

    String counterFetch;
    TextView email;
    String IdofAdapter;
    TextView name;
    String mFetchId;
    int messageCounter = 0;
    String arkham;
    String PrimaryKeyObjectId;
    String one;
    String two;
    String RecieverId;
    Toolbar to;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setupFriendRequestMethod();
        setContentView(R.layout.random__chat_voting);
        to = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(to);
        getSupportActionBar().setDefaultDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        final Drawable upArrow = getResources().getDrawable(R.drawable.abc_ic_ab_back_mtrl_am_alpha);
        upArrow.setColorFilter(getResources().getColor(R.color.bg_glass), PorterDuff.Mode.SRC_ATOP);
        getSupportActionBar().setHomeAsUpIndicator(upArrow);
        name = (TextView) findViewById(R.id.name);
        email = (TextView) findViewById(R.id.email);
        addfriend = (Button) findViewById(R.id.addfriendbut);
        Intent intent = getIntent();
        IdofAdapter = intent.getStringExtra("ForObjectId");
        mFetchId = intent.getStringExtra("ForRequestStack");
        getCloud(IdofAdapter);
        String AmigoChat = IdofAdapter;
        one = AmigoChat.substring(0,10);
        two = AmigoChat.substring(10,20);
        if(ParseUser.getCurrentUser().getObjectId().toString().equals(one)){
                RecieverId = two;
        }else{
            RecieverId = one;
        }
        ParseQuery<ParseUser> m= ParseUser.getQuery();
        m.getInBackground(RecieverId, new GetCallback<ParseUser>() {
            @Override
            public void done(ParseUser parseUser, ParseException e) {
                name.setText(parseUser.get("FirstName").toString());
                String n = parseUser.getUsername().toString();
                StringBuilder f = new StringBuilder();
                f.append("@");
                f.append(n);
                String g = f.toString();
                email.setText(g);

            }
        });
        StringBuilder f = new StringBuilder();
        f.append("@");




        addfriend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(Voting_Profile.this);
                alertDialogBuilder.setMessage("Are you sure?");
                alertDialogBuilder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface arg0, int arg1) {
                        ParseQuery<ParseObject> qa = ParseQuery.getQuery("FriendRequests");
                        qa.getInBackground(PrimaryKeyObjectId, new GetCallback<ParseObject>() {
                            @Override
                            public void done(ParseObject parseObject, ParseException e) {
                                //For complete functionality add AmigoChatId here and in Listusersactivty do substring of this Id and then fetch id or recepient and sender
                                parseObject.put("RequestStack", mFetchId);
                                parseObject.put("NotificationManager", mFetchId);
                                parseObject.put("RequestReceiver", RecieverId);
                                parseObject.put("IdCounter", "NotFriends");
                                parseObject.put("AmigoChatId", IdofAdapter);
                                parseObject.saveInBackground();

                            }
                        });


                    }
                });

                alertDialogBuilder.setNegativeButton("Not now",new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        finish();
                    }
                });

                AlertDialog alertDialog = alertDialogBuilder.create();
                alertDialog.show();
            }
        });



}
    private void getCloud(String noti) {


        ParseQuery<ParseObject> query = ParseQuery.getQuery("FriendRequests");
        query.whereEqualTo("AmigoChatId", noti);
        query.findInBackground(new FindCallback<ParseObject>() {
            @Override
            public void done(List<ParseObject> list, ParseException e) {
                if (e == null) {
                    if(list.size()!=0){

                        PrimaryKeyObjectId = list.get(0).getObjectId();
                    }
                    else{

                        Toast.makeText(Voting_Profile.this, "Something went wrong", Toast.LENGTH_SHORT).show();
                    }

                    //Toast.makeText(MessagingActivity.this, "Size " + PrimaryKeyObjectId, Toast.LENGTH_SHORT).show();
                }
            }
        });

    }
    @Override
    public boolean onOptionsItemSelected(MenuItem menuItem) {
        if (menuItem.getItemId() == android.R.id.home) {

            onBackPressed();
            overridePendingTransition(R.anim.right_in, R.anim.right_out);
        }
        return super.onOptionsItemSelected(menuItem);
    }
    private void setupFriendRequestMethod() {
        //To find the values stored in the column do this!!
        ParseQuery<ParseObject> friendquery = ParseQuery.getQuery("FriendRequests");
        friendquery.selectKeys(Arrays.asList("IdCounter"));
        friendquery.findInBackground(new FindCallback<ParseObject>() {
            @Override
            public void done(List<ParseObject> list, ParseException e) {

                if(e == null){
                   int m =  list.size();
                    for(ParseObject lists :list){
                        arkham = lists.getString("IdCounter");
                    }

                }

            }
        });
    }
}
