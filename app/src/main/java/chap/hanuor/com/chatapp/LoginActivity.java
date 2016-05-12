package chap.hanuor.com.chatapp;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.facebook.AccessToken;
import com.facebook.CallbackManager;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.facebook.HttpMethod;
import com.flaviofaria.kenburnsview.KenBurnsView;
import com.flaviofaria.kenburnsview.RandomTransitionGenerator;
import com.parse.LogInCallback;
import com.parse.ParseException;
import com.parse.ParseFacebookUtils;
import com.parse.ParseFile;
import com.parse.ParseUser;

import org.json.JSONException;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class LoginActivity extends AppCompatActivity {

    private Button signUpButton;
    private Button loginButton;
    private EditText usernameField;
    private EditText passwordField;
    private String username;
    public static final List<String> mPermissions = new ArrayList<String>() {{
        add("public_profile");
        add("email");
    }};
    private String password;
    String ems;
    ProgressDialog imp;
    String emailFacebook;
    String nameFacebook;
    private Intent intent;
    EditText email;
    ParseUser parseUser;
    EditText Fullname;
    Button signin;
    private Intent serviceIntent;
    private Intent privateservice;
    CallbackManager callbackManager;
    KenBurnsView kbv;
    RelativeLayout gh;
    RelativeLayout fr;
    View snack;
    Button me;
    TextView farcry;
    String names[] = {"Hate","Love","Forgive","Selfish","Cute","Kill","Laugh","Sad","Happy","Nerd","Handsome","Pretty","Caring","Arcane","Amigo"};
    Button loginButtonF;
    Handler mHandler = new Handler();
    List<String> permissions = Arrays.asList("public_profile", "email");
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
       // FacebookSdk.sdkInitialize(getApplicationContext());
        callbackManager = CallbackManager.Factory.create();
        //Parse.initialize(this, "tuynVqQztQvNw3w9LTM59ketoGY5gBliONtclFIn", "cGVBWCp20zW4puDHHIP7gQdnTMuk3YBVuA2lpA0w");
//        ParseFacebookUtils.initialize(this);
        setContentView(R.layout.main_login);
        loginButtonF = (Button) findViewById(R.id.login_button);
        gh = (RelativeLayout) findViewById(R.id.hack);

        fr = (RelativeLayout) findViewById(R.id.intro_background);
        snack  = findViewById(R.id.comp);
        intent = new Intent(getApplicationContext(), ListUsersActivity.class);
        serviceIntent = new Intent(getApplicationContext(), MessageService.class);
       // privateservice = new Intent(getApplicationContext(), ServiceClass.class);
        ParseUser currentUser = ParseUser.getCurrentUser();
        if (currentUser != null) {
            startActivity(intent);
            startService(serviceIntent);
            fr.setVisibility(View.GONE);
            gh.setVisibility(View.VISIBLE);

          //  moveTaskToBack(true);
            //startService(privateservice);
           // finish();

        }
        imp = new ProgressDialog(LoginActivity.this);


        imp.setMessage("Setting up your Amigo");

        kbv = (KenBurnsView) findViewById(R.id.imageCover);
        final Handler h = new Handler();
        final int delay = 300; //milliseconds

       /* h.postDelayed(new Runnable(){
            public void run(){
                //do something
                Random r = new Random();
                int i1 = r.nextInt(6 - 0) + 0;
                int reg = r.nextInt(14);
                if(i1 == 0){
                    farcry.setText(names[reg]);
                    farcry.setTextSize(r.nextInt(75));
                    farcry.setGravity(Gravity.RIGHT);
                }
                else if (i1 == 1){

                    farcry.setText(names[reg]);
                    farcry.setTextSize(r.nextInt(75));
                    farcry.setGravity(Gravity.LEFT);
                }

                else if (i1 == 2){

                    farcry.setText(names[reg]);
                    farcry.setTextSize(r.nextInt(75));
                    farcry.setGravity(Gravity.CENTER);
                }

                else if (i1 == 3){

                    farcry.setText(names[reg]);
                    farcry.setTextSize(r.nextInt(75));
                    farcry.setGravity(Gravity.BOTTOM);
                }
                else if(i1 == 4){
                    farcry.setText(names[reg]);
                    farcry.setTextSize(r.nextInt(75));
                    farcry.setGravity(Gravity.LEFT | Gravity.BOTTOM);
                }
                else if (i1 == 5){
                    farcry.setText(names[reg]);
                    farcry.setTextSize(r.nextInt(75));
                    farcry.setGravity(Gravity.BOTTOM | Gravity.RIGHT);
                }
                h.postDelayed(this, delay);
            }
        }, delay);*/
        AccelerateDecelerateInterpolator ACCELERATE_DECELERATE = new AccelerateDecelerateInterpolator();
        RandomTransitionGenerator generator = new RandomTransitionGenerator(10000, ACCELERATE_DECELERATE);

        kbv.setTransitionGenerator(generator); //set new transition on kbv
        loginButton = (Button) findViewById(R.id.signinButton);
        signUpButton = (Button) findViewById(R.id.signupButton);
        usernameField = (EditText) findViewById(R.id.amigoname);
        passwordField = (EditText) findViewById(R.id.input_password);
        passwordField.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                passwordField.setError(null);
            }
        });
        usernameField.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                usernameField.setError(null);
            }
        });
        //email = (EditText) findViewById(R.id.input_email);
       // Fullname = (EditText) findViewById(R.id.input_name);
        //Fullname.setHintTextColor(Color.parseColor("#FFFFFF"));
loginButtonF.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View view) {


        Toast.makeText(LoginActivity.this, "Still in development", Toast.LENGTH_SHORT).show();
        ParseFacebookUtils.logInWithReadPermissionsInBackground(LoginActivity.this, mPermissions, new LogInCallback() {
            @Override
            public void done(ParseUser user, ParseException e) {

                if (user == null) {
                    Log.d("MyApp", "Uh oh. The user cancelled the Facebook login.");
                } else if (user.isNew()) {
                    Log.d("MyApp", "User signed up and logged in through Facebook!");
                    getUserDetailsFromFB();
                } else {
                    Log.d("MyApp", "User logged in through Facebook!");
                    getUserDetailsFromParse();
                }

            }
        });
    }
});

        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                imp.show();
                username = usernameField.getText().toString();
                password = passwordField.getText().toString();


                if(username.equals("")){
                    imp.dismiss();
                    usernameField.setError("Please enter a valid username");
                }
                else if(password.equals("")){
                 imp.dismiss();
                    passwordField.setError("Invalid Password");
                }
               // ems = email.getText().toString();
                else {


                    ParseUser.logInInBackground(username, password, new LogInCallback() {
                        public void done(ParseUser user, com.parse.ParseException e) {
                            if (user != null) {
                                startActivity(intent);
                                imp.dismiss();
                                startService(serviceIntent);
                                ///startService(privateservice);

                                new Thread(new Runnable() {
                                    @Override
                                    public void run() {
                                        // TODO Auto-generated method stub
                                        while (true) {
                                            try {
                                                Thread.sleep(5000);
                                                mHandler.post(new Runnable() {

                                                    @Override
                                                    public void run() {
                                                        // TODO Auto-generated method stub
                                                        // Write your code here to update the UI.
                                                        fr.setVisibility(View.GONE);
                                                        gh.setVisibility(View.VISIBLE);
                                                    }
                                                });
                                            } catch (Exception e) {
                                                // TODO: handle exception
                                            }
                                        }
                                    }
                                }).start();

                                //finish();

                            } else {
                                Toast.makeText(getApplicationContext(),
                                        "Wrong username/password combo",
                                        Toast.LENGTH_LONG).show();
                            }
                        }
                    });
                }
                usernameField.setText("");
                passwordField.setText("");


            }
        });

        signUpButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                usernameField.setText("");
                passwordField.setText("");
            Intent f = new Intent(LoginActivity.this,SignupActivity.class);
                //f.setFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
                startActivity(f);
                overridePendingTransition(R.anim.lefttoright,R.anim.righttoleft);



            }
        });
    }


    private void getUserDetailsFromFB() {
        new GraphRequest(
                AccessToken.getCurrentAccessToken(),
                "/me",
                null,
                HttpMethod.GET,
                new GraphRequest.Callback() {
                    public void onCompleted(GraphResponse response) {
           /* handle the result */
                        try {
                            emailFacebook = response.getJSONObject().getString("email");
                           // mEmailID.setText(email);
                            nameFacebook = response.getJSONObject().getString("name");
                            Toast.makeText(LoginActivity.this, " "+nameFacebook, Toast.LENGTH_SHORT).show();
                           // mUsername.setText(name);
                            saveNewUser();
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }
        ).executeAsync();
        //ProfilePhotoAsync profilePhotoAsync = new ProfilePhotoAsync(mFbProfile);
        //profilePhotoAsync.execute();
    }

    private void saveNewUser() {

        parseUser = ParseUser.getCurrentUser();
        parseUser.setUsername(nameFacebook);
        parseUser.setEmail(emailFacebook);
//        Saving profile photo as a ParseFile
      /*  ByteArrayOutputStream stream = new ByteArrayOutputStream();
        Bitmap bitmap = ((BitmapDrawable) mProfileImage.getDrawable()).getBitmap();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 70, stream);
        byte[] data = stream.toByteArray();
        String thumbName = parseUser.getUsername().replaceAll("\\s+", "");
        final ParseFile parseFile = new ParseFile(thumbName + "_thumb.jpg", data);
        parseFile.saveInBackground(new SaveCallback() {
            @Override
            public void done(ParseException e) {
                parseUser.put("photo", parseFile);
                //Finally save all the user details
                parseUser.saveInBackground(new SaveCallback() {
                    @Override
                    public void done(ParseException e) {
                        Toast.makeText(LoginActivity.this, "New user:" + nameFacebook + " Signed up", Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });*/
    }


    private void getUserDetailsFromParse() {
        parseUser = ParseUser.getCurrentUser();
//Fetch profile photo
        try {
            ParseFile parseFile = parseUser.getParseFile("profileThumb");
            byte[] data = parseFile.getData();
            Bitmap bitmap = BitmapFactory.decodeByteArray(data, 0, data.length);
           // mProfileImage.setImageBitmap(bitmap);
        } catch (Exception e) {
            e.printStackTrace();
        }
       // mEmailID.setText(parseUser.getEmail());
       // mUsername.setText(parseUser.getUsername());
       Toast.makeText(LoginActivity.this,"Welcome Back" + parseUser.getUsername(), Toast.LENGTH_SHORT).show();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem menuItem) {
        if (menuItem.getItemId() == android.R.id.home) {
            onBackPressed();
        }
        return super.onOptionsItemSelected(menuItem);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        ParseFacebookUtils.onActivityResult(requestCode, resultCode, data);
    }
    @Override
    public void onDestroy() {
       // stopService(new Intent(this, ServiceClass.class));
        stopService(new Intent(this, MessageService.class));
        super.onDestroy();
    }
}