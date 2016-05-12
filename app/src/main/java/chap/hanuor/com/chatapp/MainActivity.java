package chap.hanuor.com.chatapp;

import android.app.Application;

import com.facebook.FacebookSdk;
import com.parse.Parse;
import com.parse.ParseFacebookUtils;
import com.parse.ParseUser;

public class MainActivity extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        Parse.enableLocalDatastore(this);
        Parse.initialize(this, "tuynVqQztQvNw3w9LTM59ketoGY5gBliONtclFIn", "cGVBWCp20zW4puDHHIP7gQdnTMuk3YBVuA2lpA0w");
        ParseUser.enableRevocableSessionInBackground();
       FacebookSdk.sdkInitialize(getApplicationContext());
            ParseFacebookUtils.initialize(this);
       // ParseFacebookUtils.initialize(this);


    }
}
