package chap.hanuor.com.chatapp;

import android.app.Service;
import android.content.Intent;
import android.os.Binder;
import android.os.IBinder;
import android.support.v4.content.LocalBroadcastManager;
import android.widget.Toast;

import com.parse.FindCallback;
import com.parse.ParseQuery;
import com.parse.ParseUser;
import com.sinch.android.rtc.ClientRegistration;
import com.sinch.android.rtc.Sinch;
import com.sinch.android.rtc.SinchClient;
import com.sinch.android.rtc.SinchClientListener;
import com.sinch.android.rtc.SinchError;
import com.sinch.android.rtc.messaging.MessageClient;
import com.sinch.android.rtc.messaging.MessageClientListener;
import com.sinch.android.rtc.messaging.WritableMessage;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Shantanu Johri on 05-11-2015.
 */
public class ServiceClass extends Service implements SinchClientListener {
    private static final String APP_KEY = "1f9d3150-2a3a-4fbc-afdf-d571106ef931";
    private static final String APP_SECRET = "xQZ8YjIN70i0iv4MsK32kw==";
    private static final String ENVIRONMENT = "sandbox.sinch.com";
    private final MessageInterface serviceInterface = new MessageInterface();
    private SinchClient sinchClient = null;
    private MessageClient messageClient = null;
    private String currentUserId;
    private ArrayList<String> nama;
//    String recept;
    //Sinch Client is not Starting!

    List<String> list;
    private LocalBroadcastManager broadcaster;
    private Intent broadcastIntent = new Intent("chap.hanuor.com.chatapp.PrivateUsers");

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        //setConversationsList();
        //recept = "LOLXX";


        currentUserId = ParseUser.getCurrentUser().getObjectId();

        if (currentUserId != null && !isSinchClientStarted()) {
            startSinchClient(currentUserId);
        }

        broadcaster = LocalBroadcastManager.getInstance(this);

        return super.onStartCommand(intent, flags, startId);
    }

    //display clickable a list of all users
    private void setConversationsList() {
        currentUserId = ParseUser.getCurrentUser().getObjectId();
        nama = new ArrayList<String>();

        ParseQuery<ParseUser> query = ParseUser.getQuery();
        query.whereNotEqualTo("objectId", currentUserId);
        query.findInBackground(new FindCallback<ParseUser>() {
            public void done(List<ParseUser> userList, com.parse.ParseException e) {
                if (e == null) {
                    for (int i = 0; i < userList.size(); i++) {
                        nama.add(userList.get(i).getUsername().toString());
                    }


                } else {
                    Toast.makeText(getApplicationContext(),
                            "Error loading user list",
                            Toast.LENGTH_LONG).show();
                }
            }
        });
    }
    public void startSinchClient(String username) {
        sinchClient = Sinch.getSinchClientBuilder().context(this).userId(username).applicationKey(APP_KEY)
                .applicationSecret(APP_SECRET).environmentHost(ENVIRONMENT).build();

        sinchClient.addSinchClientListener(this);

        sinchClient.setSupportMessaging(true);
        sinchClient.setSupportActiveConnectionInBackground(true);

        sinchClient.checkManifest();
        sinchClient.start();
    }

    private boolean isSinchClientStarted() {
        return sinchClient != null && sinchClient.isStarted();
    }

    @Override
    public void onClientFailed(SinchClient client, SinchError error) {
        broadcastIntent.putExtra("success", false);
        broadcaster.sendBroadcast(broadcastIntent);

        sinchClient = null;
    }

    @Override
    public void onClientStarted(SinchClient client) {
        broadcastIntent.putExtra("success", true);
        broadcaster.sendBroadcast(broadcastIntent);

        client.startListeningOnActiveConnection();
        messageClient = client.getMessageClient();
    }

    @Override
    public void onClientStopped(SinchClient client) {
        sinchClient = null;
    }

    @Override
    public IBinder onBind(Intent intent) {
        return serviceInterface;
    }

    @Override
    public void onLogMessage(int level, String area, String message) {
    }

    @Override
    public void onRegistrationCredentialsRequired(SinchClient client, ClientRegistration clientRegistration) {
    }

    public void sendMessage(String receptor,String textBody) {

        if (messageClient != null) {
            WritableMessage message1 = new WritableMessage(receptor,textBody);
            // WritableMessage message2 = new WritableMessage(recep,textBody);
            messageClient.send(message1);
            //   messageClient.send(message2);
            // Toast.makeText(getApplicationContext(),"notARRRGH!",Toast.LENGTH_SHORT).show();
        }

    }

    public void addMessageClientListener(MessageClientListener listener) {
        if (messageClient != null) {
            messageClient.addMessageClientListener(listener);
        }
    }

    public void removeMessageClientListener(MessageClientListener listener) {
        if (messageClient != null) {
            messageClient.removeMessageClientListener(listener);
        }
    }

    @Override
    public void onDestroy() {
        sinchClient.stopListeningOnActiveConnection();
        sinchClient.terminate();
    }

    public class MessageInterface extends Binder {
        public void sendMessage(String receptor,String textBody) {
            ServiceClass.this.sendMessage(receptor,textBody);
        }

        public void addMessageClientListener(MessageClientListener listener) {
            ServiceClass.this.addMessageClientListener(listener);
        }

        public void removeMessageClientListener(MessageClientListener listener) {
            ServiceClass.this.removeMessageClientListener(listener);
        }

        public boolean isSinchClientStarted() {
            return ServiceClass.this.isSinchClientStarted();
        }
    }
}