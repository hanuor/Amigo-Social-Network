package chap.hanuor.com.chatapp;

import android.app.ProgressDialog;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.DecelerateInterpolator;
import android.widget.TextView;

import com.parse.FindCallback;
import com.parse.GetDataCallback;
import com.parse.ParseException;
import com.parse.ParseFile;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.ParseUser;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class NotorietyLeaderBoard extends AppCompatActivity {

    RecyclerView activeRecyclerView;
    RecyclerView.Adapter activeAdapter;
    RecyclerView.LayoutManager activeLayoutManager;
    ProgressDialog pd;
    Toolbar to;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notoriety_leader_board);
        to = (Toolbar) findViewById(R.id.toolbar);
        to.setTitle("Leaderboard");
        setSupportActionBar(to);

        getSupportActionBar().setDefaultDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        final Drawable upArrow = getResources().getDrawable(R.drawable.abc_ic_ab_back_mtrl_am_alpha);
        upArrow.setColorFilter(getResources().getColor(R.color.bg_glass), PorterDuff.Mode.SRC_ATOP);
        getSupportActionBar().setHomeAsUpIndicator(upArrow);
        pd = new ProgressDialog(this);
        pd.setMessage("Calculating some data and fetching information...");
        activeRecyclerView = (RecyclerView) findViewById(R.id.recyclerLeader);
        activeRecyclerView.setHasFixedSize(true);
        calculatefunction();
    }

    private void calculatefunction() {
        pd.show();
        final ArrayList<String> ham = new ArrayList<String>();
        final ArrayList<Double> mak = new ArrayList<Double>();
        final ArrayList<Double> original = new ArrayList<Double>();
        final ArrayList<String> usernames = new ArrayList<String>();
        final ArrayList<String> namesList = new ArrayList<String>();
        Log.d("KLKL","jk");
        final ArrayList<String> cocoon = new ArrayList<String>();
        final ArrayList<String> names = new ArrayList<String>();
        final Double doum[] ;
        ArrayList<Double> no = new ArrayList<Double>();
        ParseQuery<ParseObject> cf = ParseQuery.getQuery("AmigoPosts");
        cf.selectKeys(Arrays.asList("overall"));
        cf.findInBackground(new FindCallback<ParseObject>() {
            @Override
            public void done(List<ParseObject> list, ParseException e) {
                if(e == null){
                    Log.d("KLKL",""+list.size());

for(int s = 0;s< list.size();s++){
    ham.add(s,list.get(s).get("overall").toString());
//String mer[] = ham.get(s).split("\\s+");
    String mer[]= ham.get(s).split(" ");
    Log.d("Mer Size",""+mer.length+s);
    Double nn = Double.parseDouble(mer[0].toString());
    names.add(mer[1].toString());
    mak.add(nn);
    original.add(nn);


}
                    Arrays.sort(mak.toArray(), Collections.reverseOrder());
                    Collections.sort(mak);
                    Collections.reverse(mak);
                    Log.d("MakSixe",""+mak.size());
                    //mak is sorted in descending order..
                //cocoon is the sorted list
                    for(int y = 0;y<mak.size();y++){
                        //Log.d(" Sort"+" "+mak.get(i));
                        for(int k = 0; k < mak.size();k++) {


                            if (mak.get(y) == original.get(k)) {
                                cocoon.add(names.get(k).toString());

                            }
                        }
                    }
                    for(int h = 0;h<cocoon.size();h++){
                        Log.d("Cocoom",""+cocoon.get(h));
                        ParseQuery<ParseUser> query = ParseUser.getQuery();
                        query.whereMatches("objectId",cocoon.get(h).toString());

                        query.findInBackground(new FindCallback<ParseUser>() {
                            @Override
                            public void done(List<ParseUser> list, ParseException e) {
                                if(e == null){
                                    if(list.size()!=0){

                                        for(int j = 0;j<cocoon.size();j++){
                                        if(list.get(0).getObjectId().equals(cocoon.get(j))){

                                            namesList.add(list.get(0).get("FirstName").toString());
                                            usernames.add(list.get(0).getUsername().toString());
                                            pd.dismiss();
                                        }
                                        }
                                    }
                                    else
                                    {

                                    }
                                }
                            }
                        });


                        activeRecyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
                        activeRecyclerView.setAdapter(new NotorietyAdapter(getApplicationContext(),mak,cocoon,namesList,usernames));
                        //Log.d("Walk",""+mak.get(h)+""+cocoon.get(h));
                        pd.dismiss();
                    }

                    //recyclerView.setHasFixedSize(true);
                    //Display mak along with cocoon now :)


                }else{
                    Log.d("LKL","K");
                    e.printStackTrace();
                }
            }
        });



    }



    public class NotorietyAdapter extends RecyclerView.Adapter<NotorietyAdapter.ViewHolder> {
        ArrayList<Double> numbers;
        ArrayList<String> ids;
        ArrayList<String> names;
        ArrayList<String> usernames;

        public NotorietyAdapter(Context con,ArrayList<Double> numbers, ArrayList<String> ids,
                                ArrayList<String> usernames, ArrayList<String> names) {
            this.numbers = numbers;
            this.ids = ids;
            this.names = names;
            this.usernames = usernames;

        }

        // Provide a reference to the views for each data item
        // Complex data items may need more than one view per item, and
        // you provide access to all the views for a data item in a view holder
        public class ViewHolder extends RecyclerView.ViewHolder {
            TextView firstname;
            CircleImageView userimage;
            TextView amigoid;
            TextView overall;
            //  ImageView iv;
            // public CardView activeCV;

            public ViewHolder(View CV) {
                super(CV);
                firstname = (TextView) CV.findViewById(R.id.firstname);
                amigoid = (TextView) CV.findViewById(R.id.amigoid);
                userimage = (CircleImageView) CV.findViewById(R.id.userimage);
                overall = (TextView) CV.findViewById(R.id.overallcount);
                //iv = (ImageView) CV.findViewById(R.id.ab);

            }
        }

        // Create new views (invoked by the layout manager)
        @Override
        public NotorietyAdapter.ViewHolder onCreateViewHolder(ViewGroup parent,
                                                             int viewType) {
            // create a new view
            View v = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.leaderboarditem, parent, false);
            // set the view's size, margins, paddings and layout parameters

            ViewHolder vh = new ViewHolder(v);
            return vh;
        }

        /*@Override
        public void onBindViewHolder(CommentsAdapter.ViewHolder holder, int position) {

        }
*/
        // Replace the contents of a view (invoked by the layout manager)
        @Override
        public void onBindViewHolder(ViewHolder holder, int position) {
            // - get element from your dataset at this position
            // - replace the contents of the view with that element
            Log.d("sdsd","dsawdree");
            func(ids.get(position),holder);
           // holder.firstname.setText(names.get(position));
            //holder.amigoid.setText(usernames.get(position));
            holder.overall.setText(numbers.get(position).toString());
           // holder.userimage.setImageDrawable(null);


        }


        // Return the size of your dataset (invoked by the layout manager)
        @Override
        public int getItemCount() {
            return 10;
        }

        @Override
        public int getItemViewType(int position) {
            return position;
        }
    }

    private void func(String in, final NotorietyAdapter.ViewHolder vh) {
        ParseQuery<ParseUser> query = ParseUser.getQuery();
        query.whereEqualTo("objectId",in);
        query.findInBackground(new FindCallback<ParseUser>() {
            @Override
            public void done(List<ParseUser> list, ParseException e) {
                if(e == null){
                    if(list.size()!=0){
                        ParseFile pic = list.get(0).getParseFile("photo");
                        vh.firstname.setText(list.get(0).get("FirstName").toString());
                        vh.firstname.setTag(list.get(0).get("FirstName").toString());
                        vh.amigoid.setText("@"+list.get(0).getUsername().toString());
                        vh.amigoid.setTag(list.get(0).getUsername().toString());
                        //if(pic!=null) {

                        if(pic!=null) {
                            final String usrl;
                                usrl = list.get(0).getParseFile("photo").getUrl();


                            pic.getDataInBackground(new GetDataCallback() {
                                @Override
                                public void done(byte[] data, ParseException e) {
                                    if (e == null) {
                                        try {
                                            if (data.length == 0) {

                                                // data found, but nothing to extract. bad image or upload?
                                                return; // failing out
                                            }


                                            //Toast.makeText(ListUsersActivity.this, "Obejct Id fetched and photo" + objectIdOfUser, Toast.LENGTH_SHORT).show();
                                            Bitmap bmp = BitmapFactory.decodeByteArray(data, 0, data.length);
                                            Animation fadeIn = new AlphaAnimation(0, 1);
                                            fadeIn.setInterpolator(new DecelerateInterpolator()); //add this
                                            fadeIn.setDuration(1000);
                                            vh.userimage.setAnimation(fadeIn);

                                            if(usrl!=null) {
                                                Picasso.with(getBaseContext()).load(usrl).into(vh.userimage);

                                            }
                                           // vh.getImView().setImageBitmap(bmp);
                                            // SUCCESS
                                            // convert data and display in an imageview
                                        } catch (Exception e1) {
                                            e1.printStackTrace();
                                        }
                                    } else {
                                        e.printStackTrace();
                                        // ParseFile contained no data. data not added to ParseFile?
                                    }
                                }
                            });
                        }

                    }
                }
            }
        });

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            onBackPressed();
        }
        return super.onOptionsItemSelected(item);
    }
}
