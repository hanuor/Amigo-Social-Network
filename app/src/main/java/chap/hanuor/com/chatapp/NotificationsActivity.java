package chap.hanuor.com.chatapp;

import android.content.Intent;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.flaviofaria.kenburnsview.KenBurnsView;
import com.flaviofaria.kenburnsview.RandomTransitionGenerator;
import com.parse.FindCallback;
import com.parse.GetCallback;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.ParseUser;

import java.util.ArrayList;
import java.util.List;

public class NotificationsActivity extends AppCompatActivity {
    ArrayList<String> ObjectID_fetcher;
    ArrayList<String> ReceivedAmigoID;
    ArrayList<String> Array_senders;
    RecyclerView activeRecyclerView;
    String currentUser;
    ArrayList<String> bad;
    Toolbar po;
    RecyclerView.Adapter activeAdapter;
    RecyclerView.LayoutManager activeLayoutManager;
    public int mNotificationsCount = 0;
    ArrayList<String> Recepient_Id;
    RelativeLayout mNo;
    KenBurnsView kbv;
    ArrayList<String> gf;
    ArrayList<String> user_Objects;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notifications);
        po = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(po);
        getSupportActionBar().setDefaultDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        final Drawable upArrow = getResources().getDrawable(R.drawable.abc_ic_ab_back_mtrl_am_alpha);
        upArrow.setColorFilter(getResources().getColor(R.color.bg_glass), PorterDuff.Mode.SRC_ATOP);
        getSupportActionBar().setHomeAsUpIndicator(upArrow);
        kbv = (KenBurnsView) findViewById(R.id.imageCover);
        AccelerateDecelerateInterpolator ACCELERATE_DECELERATE = new AccelerateDecelerateInterpolator();
        RandomTransitionGenerator generator = new RandomTransitionGenerator(10000, ACCELERATE_DECELERATE);
        kbv.setTransitionGenerator(generator); //set new transition on kbv
        mNo = (RelativeLayout) findViewById(R.id.DefaultMain);
        activeRecyclerView = (RecyclerView) findViewById(R.id.recy);
        activeLayoutManager = new LinearLayoutManager(this);
        activeRecyclerView.setLayoutManager(activeLayoutManager);
        activeRecyclerView.setHasFixedSize(false);
        user_Objects = new ArrayList<String>();
        Recepient_Id = new ArrayList<String>();
        bad = new ArrayList<String>();
        ObjectID_fetcher = new ArrayList<String>();
        ReceivedAmigoID = new ArrayList<String>();
        gf = new ArrayList<String>();
        Array_senders = new ArrayList<String>();
        Intent i = getIntent();
        currentUser = ParseUser.getCurrentUser().getObjectId().toString();
        ObjectID_fetcher = i.getStringArrayListExtra("FetcherID");
        Log.d("OBJECTO",""+ObjectID_fetcher);
        ReceivedAmigoID = i.getStringArrayListExtra("AmigoID");
        updateNotificationsBadge(0);
        //Toast.makeText(NotificationsActivity.this, " Object Id "+ObjectID_fetcher.get(0).toString(), Toast.LENGTH_SHORT).show();
        display_noti_list();
    }

    public void arrayOfusers(ArrayList<String> ghi){

            bad = ghi;

    }

    private void display_noti_list() {
        String receiverID;
        String senderID;
        String masnat = null;

        for(int h = 0;h<ReceivedAmigoID.size();h++){
            receiverID = ReceivedAmigoID.get(h).toString().substring(0,10);
            Log.d("RECE",""+receiverID);
            senderID = ReceivedAmigoID.get(h).toString().substring(10,20);
            Log.d("SEND",""+senderID);
            if(receiverID.contentEquals(ParseUser.getCurrentUser().getObjectId().toString())) {
                Recepient_Id.add(h, senderID);
                masnat = senderID;
            }else{
               masnat = receiverID
               ;
                Recepient_Id.add(h,receiverID);
            }
            ParseQuery<ParseUser> query = ParseUser.getQuery();
            query.whereEqualTo("objectId", masnat);
            query.findInBackground(new FindCallback<ParseUser>() {
                @Override
                public void done(List<ParseUser> list, ParseException e) {
                    if (e == null) {
                        if (list.size() != 0) {
                            for (int jk = 0; jk < list.size(); jk++) {
                                Array_senders.add(jk, list.get(jk).get("FirstName").toString());
                                user_Objects.add(jk,list.get(jk).getObjectId().toString());
                                gf.add(jk, list.get(jk).get("FirstName").toString());
                            }
                            arrayOfusers(gf);

                            activeAdapter = new ActiveAdapter(Array_senders,user_Objects);
                            activeRecyclerView.setAdapter(activeAdapter);
                        }
                    }
                }
            });

        }


    }
    @Override
    public boolean onOptionsItemSelected(MenuItem menuItem) {
        if (menuItem.getItemId() == android.R.id.home) {
            onBackPressed();
        }
        return super.onOptionsItemSelected(menuItem);
    }
    public class ActiveAdapter extends RecyclerView.Adapter<ActiveAdapter.ViewHolder> {
        ArrayList<String> arr;
        ArrayList<String> Ob_Ids;


        public ActiveAdapter(ArrayList<String> arr,ArrayList<String> Ob_Ids) {
            this.arr = arr;
            this.Ob_Ids = Ob_Ids;

        }

        // Provide a reference to the views for each data item
        // Complex data items may need more than one view per item, and
        // you provide access to all the views for a data item in a view holder
        public class ViewHolder extends RecyclerView.ViewHolder {
            TextView tv;
            Button acceptor;
            Button rejector;

            // public CardView activeCV;

            public ViewHolder(View CV) {
                super(CV);
                tv = (TextView) CV.findViewById(R.id.singleton);
                acceptor = (Button) CV.findViewById(R.id.acceptreq);
                rejector = (Button) CV.findViewById(R.id.rejectreq);
             /*   tv.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Snackbar.make(view,"Open Profile",Snackbar.LENGTH_SHORT).show();

                    }
                });*/
                acceptor.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        final int postion = getPosition();
                      //  deleteItem(postion);

                        Snackbar.make(view,"Friend request accepted",Snackbar.LENGTH_SHORT).show();
                        //Toast.makeText(NotificationsActivity.this, "Request has been accepted", Toast.LENGTH_SHORT).show();

                        StringBuilder ask = new StringBuilder();
                        ask.append(Ob_Ids.get(postion).toString());
                        ask.append(currentUser);

                        String Am_ID = ask.toString();

                        ParseQuery<ParseObject> query = ParseQuery.getQuery("FriendRequests");
                        query.whereEqualTo("AmigoChatId",Am_ID);
                        query.findInBackground(new FindCallback<ParseObject>() {
                            @Override
                            public void done(List<ParseObject> list, ParseException e) {
                                if (e == null) {

   if(list.size()!=0){
    String Ob_ID_ofthatAmigo = list.get(0).getObjectId().toString();
    ParseQuery<ParseObject> qa = ParseQuery.getQuery("FriendRequests");
    qa.getInBackground(Ob_ID_ofthatAmigo, new GetCallback<ParseObject>() {
        @Override
        public void done(ParseObject parseObject, ParseException e) {
            //For complete functionality add AmigoChatId here and in Listusersactivty do substring of this Id and then fetch id or recepient and sender
            parseObject.put("IdCounter", "ActiveFriends");
            parseObject.put("RequestReceiver","friends");
            parseObject.put("NotificationManager", "AmigoNULL");
            parseObject.saveInBackground();
        }
    });

}
                                    else{

       StringBuilder ask = new StringBuilder();
       ask.append(currentUser);
       ask.append(Ob_Ids.get(postion).toString());


       String Am_ID = ask.toString();
       ParseQuery<ParseObject> query = ParseQuery.getQuery("FriendRequests");
       query.whereEqualTo("AmigoChatId",Am_ID);
       query.findInBackground(new FindCallback<ParseObject>() {
           @Override
           public void done(List<ParseObject> list, ParseException e) {
               if(list.size()!=0){
                   String Ob_ID_ofthatAmigo = list.get(0).getObjectId().toString();
                   ParseQuery<ParseObject> qa = ParseQuery.getQuery("FriendRequests");
                   qa.getInBackground(Ob_ID_ofthatAmigo, new GetCallback<ParseObject>() {
                       @Override
                       public void done(ParseObject parseObject, ParseException e) {
                           //For complete functionality add AmigoChatId here and in Listusersactivty do substring of this Id and then fetch id or recepient and sender
                           parseObject.put("IdCounter", "ActiveFriends");
                           parseObject.put("RequestReceiver","friends");
                           parseObject.put("NotificationManager", "AmigoNULL");
                           parseObject.saveInBackground();
                       }
                       });

               }
           }
       });


       //Toast.makeText(NotificationsActivity.this, "REverse the id", Toast.LENGTH_SHORT).show();
   }
                                }

                            }


                        });

                        Array_senders.remove(getPosition());
                        notifyItemRemoved(getPosition());
                        if((Array_senders.size()==0)){
                            mNo.setVisibility(View.VISIBLE);
                        }



                        //Toast.makeText(NotificationsActivity.this, " "+getPosition(), Toast.LENGTH_SHORT).show();

                    }
                });
                rejector.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Snackbar.make(view,"Friend request rejected",Snackbar.LENGTH_SHORT).show();

                       // Toast.makeText(NotificationsActivity.this, "Request has been rejected", Toast.LENGTH_SHORT).show();
                        StringBuilder ask2 = new StringBuilder();
                        ask2.append(currentUser);
                        ask2.append(Ob_Ids.get(getPosition()).toString());
                        String Am_ID = ask2.toString();
                        ParseQuery<ParseObject> ghk = ParseQuery.getQuery("FriendRequests");
                        ghk.whereEqualTo("AmigoChatId", Am_ID);
                        ghk.findInBackground(new FindCallback<ParseObject>() {
                            @Override
                            public void done(List<ParseObject> list, ParseException e) {
                                if (e == null) {
                                    if (list.size() != 0) {
                                        String fds = list.get(0).getObjectId().toString();
                                        ParseQuery<ParseObject> query = ParseQuery.getQuery("FriendRequests");
                                        query.getInBackground(fds, new GetCallback<ParseObject>() {
                                            @Override
                                            public void done(ParseObject parseObject, ParseException e) {
                                                try {
                                                    parseObject.delete();
                                                    parseObject.saveInBackground();
                                                } catch (ParseException e1) {
                                                    e1.printStackTrace();
                                                }
                                            }
                                        });
                                    }
                                }
                            }
                        });


Array_senders.remove(getPosition());
                        notifyItemRemoved(getPosition());

                        if((Array_senders.size()==0)){
                            mNo.setVisibility(View.VISIBLE);
                        }

                        //  Log.d("Tiffany Reject", " " + Ob_Ids.get(getPosition()).toString());
                       // Toast.makeText(NotificationsActivity.this, " "+getPosition(), Toast.LENGTH_SHORT).show();
                       /* */
                    }

                });
                //   activeCV = (CardView)CV;
            }
        }


        // Create new views (invoked by the layout manager)
        @Override
        public ActiveAdapter.ViewHolder onCreateViewHolder(ViewGroup parent,
                                                           int viewType) {
            // create a new view
            View v = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.notification_single_item, parent, false);
            // set the view's size, margins, paddings and layout parameters

            ViewHolder vh = new ViewHolder(v);
            return vh;
        }

        // Replace the contents of a view (invoked by the layout manager)
        @Override
        public void onBindViewHolder(ViewHolder holder, int position) {
            // - get element from your dataset at this position
            // - replace the contents of the view with that element
            holder.tv.setText(arr.get(position));

            //      .setText(arr[position]);
            //  ((ImageView)temp.findViewById(R.id.iconimage)).setImageResource(ico[position]);

        }


        // Return the size of your dataset (invoked by the layout manager)
        @Override
        public int getItemCount() {
            return arr.size();
        }
    }

    private void deleteItem(int postion) {

    }

    private void updateNotificationsBadge(int count) {

        mNotificationsCount = count;

        // force the ActionBar to relayout its MenuItems.
        // onCreateOptionsMenu(Menu) will be called again.
        invalidateOptionsMenu();
    }

}
