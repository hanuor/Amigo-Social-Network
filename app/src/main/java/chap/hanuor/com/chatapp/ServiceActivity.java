package chap.hanuor.com.chatapp;

import android.content.ComponentName;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.parse.FindCallback;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.ParseUser;
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

/**
 * Created by Shantanu Johri on 05-11-2015.
 */
public class ServiceActivity extends AppCompatActivity {

    private String recipientId;
    private EditText messageBodyField;
    private String messageBody;
    private ServiceClass.MessageInterface messageService;
    private MessageAdapter messageAdapter;
    private ListView messagesList;
    private String currentUserId;
    String recepid = "abc";
    String def = "as";
    List<String> list;
    private ServiceConnection serviceConnection = new MyServiceConnection();
    private MessageClientListener messageClientListener = new MyMessageClientListener();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.messaging);

        bindService(new Intent(this, ServiceClass.class), serviceConnection, BIND_AUTO_CREATE);
       setConversationsList();

        Intent intent = getIntent();
        recipientId = intent.getStringExtra("RECIPIENT_ID");

        ParseUser u = ParseUser.getCurrentUser();

        //Boolean f = ParseUser.getCurrentUser().get("isgroup").equals(true);
        currentUserId = ParseUser.getCurrentUser().getObjectId();

       Toast.makeText(this, "P   " + recipientId, Toast.LENGTH_LONG).show();


        messagesList = (ListView) findViewById(R.id.listMessages);
        messageAdapter = new MessageAdapter(this);
        messagesList.setAdapter(messageAdapter);
        populateMessageHistory();

        messageBodyField = (EditText) findViewById(R.id.messageBodyField);

        findViewById(R.id.sendButton).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                sendMessage();
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

                    //usersListView = (ListView)findViewById(R.id.usersListView);
                    // namesArrayAdapter =
                    // new ArrayAdapter<String>(getApplicationContext(),
                    //        R.layout.user_list_item, names);
                    // usersListView.setAdapter(namesArrayAdapter);

                    //  usersListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    // @Override
                    //  public void onItemClick(AdapterView<?> a, View v, int i, long l) {
                    //      openConversation(names, i);
                    //   }
                    //  }//);
                    // Toast.makeText(MessagingActivity.this, "Loaded Users Succed", Toast.LENGTH_SHORT).show();

                } else {
                    Toast.makeText(getApplicationContext(),
                            "Error loading user list",
                            Toast.LENGTH_LONG).show();
                }
            }
        });
    }

    //get previous messages from parse & display
    private void populateMessageHistory() {
        String[] userIds = {currentUserId, recipientId};
        ParseQuery<ParseObject> query = ParseQuery.getQuery("ParseMessages");
        query.whereContainedIn("senderId", Arrays.asList(userIds));
        query.whereContainedIn("recipientId", Arrays.asList(userIds));
        query.orderByAscending("createdAt");
        query.findInBackground(new FindCallback<ParseObject>() {
            @Override
            public void done(List<ParseObject> messageList, com.parse.ParseException e) {
                if (e == null) {
                    for (int i = 0; i < messageList.size(); i++) {
                        WritableMessage message = new WritableMessage(messageList.get(i).get("recipientId").toString(), messageList.get(i).get("messageText").toString());
                        if (messageList.get(i).get("senderId").toString().equals(currentUserId)) {
                            messageAdapter.addMessage(message, MessageAdapter.DIRECTION_OUTGOING,recepid,def);
                        } else {
                            messageAdapter.addMessage(message, MessageAdapter.DIRECTION_INCOMING,recepid,def);
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
        //  list = new ArrayList<String>();
        // list.add("RmlE39tAlZ");
        //list.add("DzXx8SoEbb");


        messageService.sendMessage(recipientId,messageBody);
        messageBodyField.setText("");
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
            messageService = (ServiceClass.MessageInterface) iBinder;
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
            Toast.makeText(ServiceActivity.this, "Message failed to send."+failureInfo, Toast.LENGTH_LONG).show();
        }

        @Override
        public void onIncomingMessage(MessageClient client, final Message message) {
            if (message.getSenderId().equals(recipientId)) {
                final WritableMessage writableMessage = new WritableMessage(message.getRecipientIds().get(0), message.getTextBody());

                //only add message to parse database if it doesn't already exist there
                ParseQuery<ParseObject> query = ParseQuery.getQuery("ParseMessages");
                query.whereEqualTo("sinchId", message.getMessageId());
                query.findInBackground(new FindCallback<ParseObject>() {
                    @Override
                    public void done(List<ParseObject> messageList, com.parse.ParseException e) {
                        if (e == null) {
                            if (messageList.size() == 0) {
                                ParseObject parseMessage = new ParseObject("ParseMessages");
                                parseMessage.put("senderId", currentUserId);
                                parseMessage.put("recipientId", writableMessage.getRecipientIds().get(0));
                                parseMessage.put("messageText", writableMessage.getTextBody());
                                parseMessage.put("sinchId", message.getMessageId());
                                parseMessage.saveInBackground();

                                messageAdapter.addMessage(writableMessage, MessageAdapter.DIRECTION_INCOMING,recepid,def);
                            }
                        }
                    }
                });
            }
        }

        @Override
        public void onMessageSent(MessageClient client, Message message, String recipientId) {


            Toast.makeText(ServiceActivity.this, "Delivered"+client, Toast.LENGTH_SHORT).show();
            final WritableMessage writableMessage = new WritableMessage(message.getRecipientIds().get(0), message.getTextBody());
            messageAdapter.addMessage(writableMessage, MessageAdapter.DIRECTION_OUTGOING,recepid,def);
        }

        @Override
        public void onMessageDelivered(MessageClient client, MessageDeliveryInfo deliveryInfo) {}

        @Override
        public void onShouldSendPushData(MessageClient client, Message message, List<PushPair> pushPairs) {}
    }
}