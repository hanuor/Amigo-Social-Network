package chap.hanuor.com.chatapp;

import android.content.ComponentName;
import android.content.Intent;
import android.content.ServiceConnection;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.IBinder;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Toast;

import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.ParseUser;
import com.parse.SaveCallback;
import com.sinch.android.rtc.PushPair;
import com.sinch.android.rtc.messaging.Message;
import com.sinch.android.rtc.messaging.MessageClient;
import com.sinch.android.rtc.messaging.MessageClientListener;
import com.sinch.android.rtc.messaging.MessageDeliveryInfo;
import com.sinch.android.rtc.messaging.MessageFailureInfo;
import com.sinch.android.rtc.messaging.WritableMessage;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class MessagingActivity extends AppCompatActivity {

    private String recipientId;
    private EditText messageBodyField;
    private String messageBody;
    private MessageService.MessageServiceInterface messageService;
    private FriendMessageAdapter messageAdapter;
    private ListView messagesList;
    Toolbar tb;
    public String RECIPENTID;

    private String currentUserId;
    public  String AmigoId;

    String PrimaryKeyObjectId;
    List<String> list;
    public String SecondAmigoIder;
    String sumsup;
    ImageView addfriend;
    ImageView cancelor;
    private ServiceConnection serviceConnection = new MyServiceConnection();
    private MessageClientListener messageClientListener = new MyMessageClientListener();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_friend_messaging);
        tb = (Toolbar) findViewById(R.id.home_tool_bar);
        setSupportActionBar(tb);

        getSupportActionBar().setDefaultDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        final Drawable upArrow = getResources().getDrawable(R.drawable.abc_ic_ab_back_mtrl_am_alpha);
        upArrow.setColorFilter(getResources().getColor(R.color.bg_glass), PorterDuff.Mode.SRC_ATOP);
        getSupportActionBar().setHomeAsUpIndicator(upArrow);
       // addfriend = (ImageView) findViewById(R.id.access_profile);
        bindService(new Intent(this, MessageService.class), serviceConnection, BIND_AUTO_CREATE);
        setConversationsList();

        Intent intent = getIntent();
        recipientId = intent.getStringExtra("RECIPIENT_ID");
        RECIPENTID = intent.getStringExtra("RECIPIENT_ID");


        //Boolean f = ParseUser.getCurrentUser().get("isgroup").equals(true);
        currentUserId = ParseUser.getCurrentUser().getObjectId();
        //Normal
        StringBuilder AmigoID = new StringBuilder();
        AmigoID.append(currentUserId);
        AmigoID.append(RECIPENTID);
        AmigoId = AmigoID.toString();
        //Reverse
        StringBuilder SecondAmigoId = new StringBuilder();
        SecondAmigoId.append(RECIPENTID);
        SecondAmigoId.append(currentUserId);
        SecondAmigoIder = SecondAmigoId.toString();


        StringBuilder m = new StringBuilder();
        m.append(RECIPENTID);
        m.append(currentUserId);
        m.append("H");
        sumsup = m.toString();
        //Sumsup is the Notification manger and the Request Stack Id
        savingtheamigoId(AmigoId,SecondAmigoIder, sumsup);
        messagesList = (ListView) findViewById(R.id.listMessages);
        messageAdapter = new FriendMessageAdapter(this);
        messagesList.setAdapter(messageAdapter);
       // populateMessageHistory();

        messageBodyField = (EditText) findViewById(R.id.messageBodyField);

        findViewById(R.id.sendButton).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                sendMessage();
            }
        });
      /*  addfriend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });*/
     /*   cancelor.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Set the cancel algorithm
            }
        });
    */}

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if(item.getItemId()==R.id.add_friend){
            Intent hy= new Intent();
            hy.setClass(MessagingActivity.this,Voting_Profile.class);
            hy.putExtra("ForRequestStack",sumsup);
            hy.putExtra("ForObjectId",SecondAmigoIder);
            startActivity(hy);
            overridePendingTransition(R.anim.lefttoright,R.anim.righttoleft);

        }
        if (item.getItemId() == android.R.id.home) {

            onBackPressed();
            overridePendingTransition(R.anim.right_in, R.anim.right_out);
        }
        return super.onOptionsItemSelected(item);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_chat, menu);
return  true;
    }

    private void savingtheamigoId(final String Idofboth, final String revboth, final String notifier) {

        ParseQuery<ParseObject> friendquery = ParseQuery.getQuery("FriendRequests");
        friendquery.whereContains("AmigoChatId", Idofboth);
        friendquery.findInBackground(new FindCallback<ParseObject>() {
            @Override
            public void done(List<ParseObject> list, ParseException e) {
                if (e == null) {
                    int getter = list.size();
                    if (getter == 0) {
                        //Idofboth did not match. Run another query containing revboth
                        ParseQuery<ParseObject> queryagain = ParseQuery.getQuery("FriendRequests");
                        queryagain.whereContains("AmigoChatId", revboth);
                        queryagain.findInBackground(new FindCallback<ParseObject>() {
                            @Override
                            public void done(List<ParseObject> list, ParseException e) {

                                if (e == null) {
                                    int getter = list.size();
                                    if (getter == 0) {

                                        //The Firstly created AmigoChatId will be of the reverse order
                                        //SAving the values in tje amigo database

                                        ParseObject frequest = new ParseObject("FriendRequests");
                                        frequest.put("AmigoChatId", revboth);
                                        frequest.put("NotificationManager","SessionCreatedCHELSEA");
                                        frequest.put("RequestStack","SessionCreatedCHELSEA");
                                        frequest.put("IdCounter", "NotFriends");
                                        frequest.saveInBackground(new SaveCallback() {
                                            @Override
                                            public void done(ParseException e) {
                                                if (e == null) {

                                           //   getCloud(revboth);
                                                }

                                            }
                                        });
                                    } else {
                                        //Because if the second user accesses this then
                                        // AmigoChatId will be of reverse order. I.E IdofBoth
                                      // getCloud(Idofboth);

                                        //Found the username!
                                    }
                                } else {
                                  ;
                                    Toast.makeText(MessagingActivity.this, "Something went wrong", Toast.LENGTH_SHORT).show();

                                  }
                            }


                        });

                    } else {

                    //  getCloud(Idofboth);

                        //Found the username!
                    }
                } else {
                  e.printStackTrace();
                }
            }


        });


    }




    private void setConversationsList() {
        currentUserId = ParseUser.getCurrentUser().getObjectId();
        list = new ArrayList<String>();

        ParseQuery<ParseUser> query = ParseUser.getQuery();
        query.whereNotEqualTo("objectId", currentUserId);
        query.findInBackground(new FindCallback<ParseUser>() {
            public void done(List<ParseUser> userList, com.parse.ParseException e) {
                if (e == null) {
                    for (int i = 0; i < userList.size(); i++) {
                        list.add(userList.get(i).getObjectId());
                    }
                } else {
                    Toast.makeText(getApplicationContext(),
                            "Error loading ",
                            Toast.LENGTH_LONG).show();
                }
            }
        });
    }

    //get previous messages from parse & display
    private void populateMessageHistory() {
        String[] userIds = {currentUserId, recipientId};
        ParseQuery<ParseObject> query = ParseQuery.getQuery("ParseMessage");
        query.whereContainedIn("senderId", Arrays.asList(userIds));
        query.whereContainedIn("recipientId", Arrays.asList(userIds));
        query.orderByAscending("createdAt");
        query.findInBackground(new FindCallback<ParseObject>() {
            @Override
            public void done(List<ParseObject> messageList, com.parse.ParseException e) {
                if (e == null) {
                    for (int i = 0; i < messageList.size(); i++) {
                                                                                      //recipient id0                                 //the message
                        WritableMessage message = new WritableMessage(messageList.get(i).get("recipientId").toString(), messageList.get(i).get("messageText").toString());
                        if (messageList.get(i).get("senderId").toString().equals(currentUserId)) {
                          //  messageAdapter.addMessage(message, MessageAdapter.DIRECTION_OUTGOING);
                        } else {
                           // messageAdapter.addMessage(message, MessageAdapter.DIRECTION_INCOMING,PrimaryKeyObjectId,sumsup);
                          //  messageAdapter.addMessage(message, MessageAdapter.DIRECTION_INCOMING);
                        }
                    }
                }
            }
        });
    }

    private void sendMessage() {
        messageBody = messageBodyField.getText().toString();
        if (messageBody.isEmpty()) {
            Toast.makeText(this, "Please enter a message", Toast.LENGTH_LONG).show();
            return;
        }
       // messageAdapter.setFakeMessage(messageBody,MessageAdapter.DIRECTION_OUTGOING);
        //messageAdapter.addOnMessage(messageBody, MessageAdapter.DIRECTION_OUTGOING, PrimaryKeyObjectId, sumsup);
       // messageAdapter.addSomeMessage(messageBody,MessageAdapter.DIRECTION_OUTGOING);
        messageService.sendMessage(recipientId, messageBody);

        messageBodyField.setText("");
        //messageAdapter.addMessage(messageBody, MessageAdapter.DIRECTION_OUTGOING, PrimaryKeyObjectId, sumsup);
//        messageAdapter.setFakeMessage(messageBody,MessageAdapter.DIRECTION_OUTGOING);
    }

    @Override
    public void onDestroy() {
        messageService.removeMessageClientListener(messageClientListener);
        unbindService(serviceConnection);
        super.onDestroy();
    }

    private class MyServiceConnection implements ServiceConnection {
        @Override
        public void onServiceConnected(ComponentName componentName, IBinder iBinder) {
            messageService = (MessageService.MessageServiceInterface) iBinder;
            messageService.addMessageClientListener(messageClientListener);
        }

        @Override
        public void onServiceDisconnected(ComponentName componentName) {
            messageService = null;
        }
    }

    private class MyMessageClientListener implements MessageClientListener {
        @Override
        public void onMessageFailed(MessageClient client, Message message,
                                    MessageFailureInfo failureInfo) {
           Toast.makeText(MessagingActivity.this, "Message failed to send."+failureInfo, Toast.LENGTH_LONG).show();
        }

        @Override
        public void onIncomingMessage(MessageClient client, final Message message) {
            if (message.getSenderId().equals(recipientId)) {
                final WritableMessage writableMessage = new WritableMessage(message.getRecipientIds().get(0), message.getTextBody());
                messageAdapter.addMessage(writableMessage, MessageAdapter.DIRECTION_INCOMING);
                //only add message to parse database if it doesn't already exist there
              /*  ParseQuery<ParseObject> query = ParseQuery.getQuery("ParseMessage");
                query.whereEqualTo("sinchId", message.getMessageId());
                query.findInBackground(new FindCallback<ParseObject>() {
                    @Override
                    public void done(List<ParseObject> messageList, com.parse.ParseException e) {
                        if (e == null) {
                            if (messageList.size() == 0) {
                                ParseObject parseMessage = new ParseObject("ParseMessage");
                                parseMessage.put("senderId", currentUserId);
                                parseMessage.put("recipientId", writableMessage.getRecipientIds().get(0));
                                parseMessage.put("messageText", writableMessage.getTextBody());
                                parseMessage.put("sinchId", message.getMessageId());
                                parseMessage.saveInBackground();


                            }
                        }
                    }
                });*/
            }
        }

        @Override
        public void onMessageSent(MessageClient client, Message message, String recipientId) {
             final WritableMessage writableMessage = new WritableMessage(message.getRecipientIds().get(0), message.getTextBody());
            messageAdapter.addMessage(writableMessage, MessageAdapter.DIRECTION_OUTGOING);
        }

        @Override
        public void onMessageDelivered(MessageClient client, MessageDeliveryInfo deliveryInfo) {

        }

        @Override
        public void onShouldSendPushData(MessageClient client, Message message, List<PushPair> pushPairs) {}
    }

}