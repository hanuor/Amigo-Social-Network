package chap.hanuor.com.chatapp;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

/**
 * Created by Shantanu Johri on 26-12-2015.
 */
public class BroadCastReceiver extends BroadcastReceiver {
    static final String ACTION = "android.intent.action.BOOT_COMPLETED";
    @Override
    public void onReceive(Context context, Intent intent) {

        if (intent.getAction().equals(ACTION)) {
            //Service
            Intent serviceIntent = new Intent(context, StartOnBootService.class);
            context.startService(serviceIntent);
        }
    }
}
