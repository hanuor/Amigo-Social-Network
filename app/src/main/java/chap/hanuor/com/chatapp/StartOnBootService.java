package chap.hanuor.com.chatapp;

import android.app.IntentService;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.util.Log;
import android.widget.Toast;

import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.ParseUser;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * An {@link IntentService} subclass for handling asynchronous task requests in
 * a service on a separate handler thread.
 * <p/>
 * TODO: Customize class - update intent actions, extra parameters and static
 * helper methods.
 */
public class StartOnBootService extends Service {
    final static String ACTION = "NotifyServiceAction";
    final static String STOP_SERVICE = "";
    final static int RQS_STOP_SERVICE = 1;
    int figo;
    ArrayList<String> Notifier;

    BroadcastReceiver mbr;

    private static final int MY_NOTIFICATION_ID=1;
    private NotificationManager notificationManager;
    private Notification myNotification;
    String Karna;

    @Override
    public void onCreate() {
    Karna = ParseUser.getCurrentUser().getObjectId().toString();
    mbr = new BroadCastReceiver();
        super.onCreate();
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction(ACTION);
        registerReceiver(mbr, intentFilter);
        figo = 0;
        Log.d("Service Sat","sadas");
        setupFriendRequestMethod();
        if(figo!=0){
            notificationManager =
                    (NotificationManager)getSystemService(Context.NOTIFICATION_SERVICE);

            myNotification = new Notification(R.drawable.ic_amigo_icon,
                    "You have unread notification/s",
                    System.currentTimeMillis());
            Context context = getApplicationContext();
           String notificationTitle = "Exercise of Notification!";
            String notificationText = "http://android-er.blogspot.com/";
            Intent myIntent = new Intent(Intent.ACTION_VIEW);
            myIntent.setClass(this,MainActivity.class);
            PendingIntent pendingIntent
                    = PendingIntent.getActivity(getBaseContext(),
                    0, myIntent,PendingIntent.FLAG_ONE_SHOT);

            Notification noti = new Notification.Builder(this)
                    .setContentTitle("You have unread notification/s")

                    .setSmallIcon(R.drawable.ic_amigo_icon)

                    .build();
            notificationManager.notify(MY_NOTIFICATION_ID, noti);
        }
        //Send notification

        return super.onStartCommand(intent, flags, startId);
    }

    private  void setupFriendRequestMethod() {
        //To find the values stored in the column do this!!
        Toast.makeText(StartOnBootService.this, "SETUPMETHODREJSEVDF", Toast.LENGTH_SHORT).show();
        Notifier = new ArrayList<String>();
        //k=0;
        //final String ak;
        ParseQuery<ParseObject> friendquery = ParseQuery.getQuery("FriendRequests");
        friendquery.whereEqualTo("RequestReceiver", ParseUser.getCurrentUser().getObjectId().toString());
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
figo++;

                                                    String lam = Notifier.get(yu).toString().substring(0, 10);
                                                    String fam = Notifier.get(yu).toString().substring(10, 20);
                                                    // StringBuilder op1 = new StringBuilder();
                                                    StringBuilder Am = new StringBuilder();
                                                    Am.append(lam);
                                                    Am.append(fam);
                                                   // Amigo_Id_Fetcher.add(Am.toString());
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
                                                  //  k = k + 1;
                                                    figo++;
                                                    String lam = Notifier.get(yu).toString().substring(0, 10);
                                                    String fam = Notifier.get(yu).toString().substring(10, 20);

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
                           }

                    }else{
                    }
                } catch (Exception e1) {
                    e1.printStackTrace();
                }

            }
        });

    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
            return null;
    }
}
