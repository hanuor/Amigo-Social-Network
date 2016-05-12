package chap.hanuor.com.chatapp;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

import com.flaviofaria.kenburnsview.KenBurnsView;
import com.flaviofaria.kenburnsview.RandomTransitionGenerator;
import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseQuery;
import com.parse.ParseUser;
import com.parse.SignUpCallback;

import java.util.List;

public class SignupActivity extends AppCompatActivity {


    private Button signUpButton;
    private Button loginButton;
    private EditText usernameField;
    private EditText passwordField;
    private String username;
    private String password;
    String ems;
    ProgressDialog imp;
    Toolbar to;
    private Intent intent;
    EditText email;
    EditText Fullname;
    CheckBox cb;
    Boolean as = false;
    String getUser;
    View snack;
String fullname;
    KenBurnsView kbv;
    private Intent serviceIntent;
    private Intent privateservice;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        imp = new ProgressDialog(SignupActivity.this);


        imp.setMessage("Setting up...");

        intent = new Intent(getApplicationContext(), ListUsersActivity.class);
        serviceIntent = new Intent(getApplicationContext(), MessageService.class);
        // privateservice = new Intent(getApplicationContext(), ServiceClass.class);
        ParseUser currentUser = ParseUser.getCurrentUser();
        if (currentUser != null) {
            startActivity(intent);
            startService(serviceIntent);
            //startService(privateservice);

        }

        setContentView(R.layout.sign_up_screen);
        kbv = (KenBurnsView) findViewById(R.id.imageCover);
        AccelerateDecelerateInterpolator ACCELERATE_DECELERATE = new AccelerateDecelerateInterpolator();
        RandomTransitionGenerator generator = new RandomTransitionGenerator(10000, ACCELERATE_DECELERATE);
        kbv.setTransitionGenerator(generator); //set new transition on kbv
        to = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(to);
        getSupportActionBar().setDefaultDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        final Drawable upArrow = getResources().getDrawable(R.drawable.abc_ic_ab_back_mtrl_am_alpha);
        upArrow.setColorFilter(getResources().getColor(R.color.bg_glass), PorterDuff.Mode.SRC_ATOP);
        getSupportActionBar().setHomeAsUpIndicator(upArrow);

        //loginButton = (Button) findViewById(R.id.loginButton);
        cb  = (CheckBox) findViewById(R.id.checkBox);
        cb.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(cb.isChecked()){
                    as = true;
                }
                else{
                    as = false;
                }
            }
        });
        signUpButton = (Button) findViewById(R.id.signupButton);
        usernameField = (EditText) findViewById(R.id.amigoname);
        passwordField = (EditText) findViewById(R.id.input_password);
        email = (EditText) findViewById(R.id.input_email);
        snack  = findViewById(R.id.comp);
        Fullname = (EditText) findViewById(R.id.input_name);
       // Fullname.setHintTextColor(Color.parseColor("#FFFFFF"));

        /*loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                username = usernameField.getText().toString();
                password = passwordField.getText().toString();
                ems = email.getText().toString();


                ParseUser.logInInBackground(username, password, new LogInCallback() {
                    public void done(ParseUser user, com.parse.ParseException e) {
                        if (user != null) {
                            startActivity(intent);
                            startService(serviceIntent);
                            ///startService(privateservice);

                        } else {
                            Toast.makeText(getApplicationContext(),
                                    "Wrong username/password combo",
                                    Toast.LENGTH_LONG).show();
                        }
                    }
                });
            }
        });*/

        signUpButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                username = usernameField.getText().toString();
                password = passwordField.getText().toString();
                fullname = Fullname.getText().toString();
                ems = email.getText().toString();
                try {
                    ParseQuery<ParseUser> query = ParseUser.getQuery();
                    query.whereEqualTo("username",username);
                    query.findInBackground(new FindCallback<ParseUser>() {
                        @Override
                        public void done(List<ParseUser> list, ParseException e) {
                            if(list.size()!=0){
                                Snackbar.make(snack,"Username already taken",Snackbar.LENGTH_SHORT).show();
                            }
                            else{


                                if(username.length()!=0 && password.length()!=0 && fullname.length()!=0 && ems.length()!=0 && as == true)
                                {


                                    ems = email.getText().toString();
                                    ParseUser user = new ParseUser();

                                    user.setUsername(username);
                                    user.setPassword(password);
                                    user.setEmail(ems);
                                    // user.setObjectId("LOLXx12345");
                                    //user.put("groupid","LOLX2");
    imp.show();
                                    user.signUpInBackground(new SignUpCallback() {
                                        public void done(ParseException e) {
                                            if (e == null) {
                                                imp.dismiss();
                                                startActivity(intent);
                                                startService(serviceIntent);

                                                ParseUser user = ParseUser.getCurrentUser();
                                                user.put("FirstName",fullname);
                                                user.put("UserStatus","Noin");
                                                user.saveInBackground();

                                                //startService(privateservice);
                                            } else {
                                                imp.dismiss();
                                                Toast.makeText(getApplicationContext(),
                                                        "There was an error signing up."
                                                        , Toast.LENGTH_LONG).show();
                                            }
                                        }
                                    });
                                }else {
                                    if(username.length()==0){
                                        usernameField.setError("Enter a username");
                                    }
                                    else if(password.length()==0){
                                        passwordField.setError("Enter a password");
                                    }
                                    else if(ems.length()==0){
                                        email.setError("Enter your E-mail");
                                    }
                                    else if(fullname.length()==0){
                                        Fullname.setError("Enter your name");
                                    }
                                    else {
                                        Snackbar.make(snack,"You forgot to check the terms and conditions",Snackbar.LENGTH_SHORT).show();
                                    }

                                    //display error
                                }


                            }
                        }
                    });
                } catch (Exception e) {
                    e.printStackTrace();
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

    @Override
    public void onDestroy() {
        // stopService(new Intent(this, ServiceClass.class));
        stopService(new Intent(this, MessageService.class));
        super.onDestroy();
    }
}

