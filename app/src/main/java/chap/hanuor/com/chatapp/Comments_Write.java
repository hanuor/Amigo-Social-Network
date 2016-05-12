package chap.hanuor.com.chatapp;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.widget.EditText;
import android.widget.Toast;

import com.flaviofaria.kenburnsview.KenBurnsView;
import com.flaviofaria.kenburnsview.RandomTransitionGenerator;
import com.parse.GetCallback;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.ParseUser;

public class Comments_Write extends AppCompatActivity {
Toolbar to;
    EditText et;
    String objectId;
    String naam;
    String hew;
    KenBurnsView kbv;
    String major;
    ProgressDialog k;
    String separator = "%3:11071995:11";
    @Override

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_comments__write);
        k = new ProgressDialog(Comments_Write.this);
        k.setMessage("Rewriting your comment on the board. Hang on!");
        to = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(to);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        getSupportActionBar().setDefaultDisplayHomeAsUpEnabled(true);
        final Drawable upArrow = getResources().getDrawable(R.drawable.abc_ic_ab_back_mtrl_am_alpha);
        upArrow.setColorFilter(getResources().getColor(R.color.bg_glass), PorterDuff.Mode.SRC_ATOP);
        getSupportActionBar().setHomeAsUpIndicator(upArrow);
        et = (EditText) findViewById(R.id.et);
        Intent i = getIntent();
        objectId = i.getStringExtra("Objecto");


        naam = i.getStringExtra("NAAM");
        major = i.getStringExtra("LAZOR");

        hew = i.getStringExtra("HEW");
        kbv = (KenBurnsView) findViewById(R.id.imageCover);
        AccelerateDecelerateInterpolator ACCELERATE_DECELERATE = new AccelerateDecelerateInterpolator();
        RandomTransitionGenerator generator = new RandomTransitionGenerator(10000, ACCELERATE_DECELERATE);

        kbv.setTransitionGenerator(generator); //set new transition on kbv


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_comment, menu);
        return  true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if(item.getItemId()==R.id.send){
            if(et.getText().equals("")){
                Toast.makeText(Comments_Write.this, "Please enter a message", Toast.LENGTH_SHORT).show();

            }
            else if(et.getText().length()==0){
                Toast.makeText(Comments_Write.this, "Please enter a message", Toast.LENGTH_SHORT).show();
            }
            else if(et.getText().length()!=0){

                final String er = et.getText().toString();

                k.show();

                ParseQuery<ParseObject> qay = ParseQuery.getQuery("AmigoPosts");
                qay.getInBackground(major, new GetCallback<ParseObject>() {
                    @Override
                    public void done(ParseObject parseObject, ParseException e) {
                        if(e == null){
                            String sh = parseObject.get("CommentsCounter").toString();
                            final double jo = Double.parseDouble(sh);
                            StringBuilder nw = new StringBuilder();
                            StringBuilder ider = new StringBuilder();

                            StringBuilder io = new StringBuilder();
                            io.append(""+(jo+0.04));
                            String mes = io.toString();
                            parseObject.put("CommentsCounter",mes);
                            parseObject.saveInBackground();
                            if(hew.equals(" ")){

                                nw.append(er);
                                nw.append(separator);
                                ider.append(ParseUser.getCurrentUser().getUsername().toString());
                                ider.append(separator);
                                String nes = ider.toString();
                                String mb = nw.toString();
                                parseObject.put("CommentsString",mb);

                                parseObject.put("CommentIds",nes);
                                parseObject.saveInBackground();
                                k.dismiss();

                            }
                            else{



                                nw.append(hew);
                                nw.append(er);
                                nw.append(separator);

                                ider.append(naam);
                                ider.append(ParseUser.getCurrentUser().getUsername().toString());
                                ider.append(separator);
                                String nes = ider.toString();
                                String mb = nw.toString();
                                parseObject.put("CommentsString",mb);
                                parseObject.put("CommentIds",nes);
                                parseObject.saveInBackground();
                                k.dismiss();
                            }
                        }
                    }
                });
                et.setText("");
            }
        }
        if (item.getItemId() == android.R.id.home) {
            onBackPressed();
        }
        return super.onOptionsItemSelected(item);

    }

}
