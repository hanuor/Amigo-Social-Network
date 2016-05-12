package chap.hanuor.com.chatapp;

import android.app.ProgressDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.greenfrvr.rubberloader.RubberLoaderView;
import com.parse.FindCallback;
import com.parse.ParseQuery;
import com.parse.ParseUser;
import com.trncic.library.DottedProgressBar;
import com.victor.loading.newton.NewtonCradleLoading;

import java.util.ArrayList;
import java.util.List;

import jp.wasabeef.recyclerview.animators.SlideInUpAnimator;

public class PrivateUsers extends AppCompatActivity {

    private String currentUserId;
    RecyclerView activeRecyclerView;
    RecyclerView.Adapter activeAdapter;
    RecyclerView.LayoutManager activeLayoutManager;
    private ArrayAdapter<String> namesArrayAdapter;
    private ArrayList<String> names;
    Toolbar mtoolbar;
    private ArrayList<String> perso;
    private ListView usersListView;
    private Button logoutButton;
    ArrayList<String> usernamesList;
    private ProgressDialog progressDialog;
    private BroadcastReceiver rece = null;
    RubberLoaderView loader;
    private NewtonCradleLoading newtonCradleLoading;
    FrameLayout fl;
    DottedProgressBar progressBar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_private_users);
        mtoolbar = (Toolbar) findViewById(R.id.settings_tool_bar);
        setSupportActionBar(mtoolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        currentUserId = ParseUser.getCurrentUser().getObjectId();
        names = new ArrayList<String>();
        Boolean say = names.isEmpty();
        Toast.makeText(PrivateUsers.this, "Saying " + say, Toast.LENGTH_SHORT).show();
        perso = new ArrayList<String>();
        activeRecyclerView = (RecyclerView) findViewById(R.id.rec_view);
        activeLayoutManager = new LinearLayoutManager(getApplicationContext());
        activeRecyclerView.setLayoutManager(activeLayoutManager);
        setconvo();
        setConversationsList();
        newtonCradleLoading = (NewtonCradleLoading) findViewById(R.id.newton_cradle_loading);
        //loader = (RubberLoaderView) findViewById(R.id.loader);
        //fl = (FrameLayout) findViewById(R.id.frameLayout);
        showSpinner();



    }
    @Override
    public boolean onOptionsItemSelected(MenuItem menuItem) {
        if (menuItem.getItemId() == android.R.id.home) {
            onBackPressed();
        }
        return super.onOptionsItemSelected(menuItem);
    }

    //display clickable a list of all users
    private void setConversationsList() {

        ParseQuery<ParseUser> query = ParseUser.getQuery();
        query.whereNotEqualTo("objectId", currentUserId);

        query.findInBackground(new FindCallback<ParseUser>() {
            public void done(List<ParseUser> userList, com.parse.ParseException e) {
                if (e == null) {
                    for (int i = 0; i < userList.size(); i++) {

                        try {
                            names.add(userList.get(i).get("FirstName").toString());
                            perso.add(userList.get(i).get("personality").toString());
                        } catch (Exception e1) {
                            e1.printStackTrace();
                            Log.d("Problem Occuring"," "+e1);
                        }

                    }
                    activeAdapter = new ActiveAdapter(names, perso,usernamesList);
                    activeRecyclerView.setAdapter(activeAdapter);
                    activeRecyclerView.setItemAnimator(new SlideInUpAnimator());
                    } else {
                    Toast.makeText(getApplicationContext(),
                            "Error loading user list",
                            Toast.LENGTH_LONG).show();
                }
            }
        });
    }

    //open a conversation with one person
   public void openConversation(ArrayList<String> names, int posi,ArrayList<String> usernames) {
        ParseQuery<ParseUser> query = ParseUser.getQuery();
        query.whereEqualTo("username", usernames.get(posi));
        query.findInBackground(new FindCallback<ParseUser>() {
            public void done(List<ParseUser> user, com.parse.ParseException e) {
                if (e == null) {
                    String id =  user.get(0).getObjectId();
                    //Toast.makeText(PrivateUsers.this, "Id "+ id, Toast.LENGTH_SHORT).show();
                   Intent intent = new Intent(getApplicationContext(), MessagingActivity.class);
                    intent.putExtra("RECIPIENT_ID", id);
                    startActivity(intent);
                } else {
                    Toast.makeText(getApplicationContext(),
                            "Error finding that user",
                            Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    //show a loading spinner while the sinch client starts
    private void showSpinner() {

        newtonCradleLoading.start();
        rece = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                Boolean success = intent.getBooleanExtra("success", false);
               // View v = loader.getRootView();
              //  fl.setVisibility(View.GONE);
                //progressDialog.dismiss();
                //progressBar.stopProgress();
                newtonCradleLoading.stop();
                if (!success) {
                    Toast.makeText(getApplicationContext(), "Messaging service failed to start", Toast.LENGTH_LONG).show();
                }
            }
        };

        LocalBroadcastManager.getInstance(PrivateUsers.this).registerReceiver(rece, new IntentFilter("chap.hanuor.com.chatapp.PrivateUsers"));
    }

    @Override
    public void onResume() {
        setConversationsList();
        setconvo();
        super.onResume();
    }
    private void setconvo() {
        currentUserId = ParseUser.getCurrentUser().getObjectId();
        usernamesList = new ArrayList<String>();
       // perso = new ArrayList<String>();
        ParseQuery<ParseUser> query = ParseUser.getQuery();
        query.whereNotEqualTo("objectId", currentUserId);

        query.findInBackground(new FindCallback<ParseUser>() {
            public void done(List<ParseUser> userList, com.parse.ParseException e) {
                if (e == null) {
                    for (int i = 0; i < userList.size(); i++) {
                        usernamesList.add(userList.get(i).getUsername());
                       // perso.add(userList.get(i).get("personality").toString());
                    }
                   } else {
                    Toast.makeText(getApplicationContext(),
                            "Error loading user list",
                            Toast.LENGTH_LONG).show();
                }
            }
        });
    }



    public class ActiveAdapter extends RecyclerView.Adapter<ActiveAdapter.ViewHolder> {
        ArrayList<String> sum;
        ArrayList<String> usernames;
        ArrayList<String> persondef;

        public ActiveAdapter(ArrayList<String> sum, ArrayList<String> persondef,ArrayList<String> usernames) {
            this.sum = sum;
            this.persondef = persondef;
            this.usernames = usernames;

        }


        // Provide a reference to the views for each data item
        // Complex data items may need more than one view per item, and
        // you provide access to all the views for a data item in a view holder
        public class ViewHolder extends RecyclerView.ViewHolder {

            public CardView activeCV;
            CardView ceevee;
            public ViewHolder(View CV) {
                super(CV);
                ceevee = (CardView) CV.findViewById(R.id.card_view);

                activeCV = (CardView)CV;
                ceevee.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {

                        final int pos = getAdapterPosition();
                      openConversation(names, pos, usernames);

                    }
                });
            }
        }

        // Create new views (invoked by the layout manager)
        @Override
        public ActiveAdapter.ViewHolder onCreateViewHolder(ViewGroup parent,
                                                           int viewType) {
            // create a new view
            View v = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.single_item, parent, false);
            // set the view's size, margins, paddings and layout parameters

            ViewHolder vh = new ViewHolder(v);
            return vh;
        }

        // Replace the contents of a view (invoked by the layout manager)
        @Override
        public void onBindViewHolder(ViewHolder holder, int position) {
            // - get element from your dataset at this position
            // - replace the contents of the view with that element
            View temp = holder.activeCV.getChildAt(0);
            ((TextView)temp.findViewById(R.id.username)).setText(sum.get(position));
            ((TextView)temp.findViewById(R.id.type)).setText(persondef.get(position));


        }


        // Return the size of your dataset (invoked by the layout manager)
        @Override
        public int getItemCount() {

            //This line is generating error 08-11-15
            return sum.size();
        }
    }


}
