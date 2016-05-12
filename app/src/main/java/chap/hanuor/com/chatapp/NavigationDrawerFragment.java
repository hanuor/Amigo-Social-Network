package chap.hanuor.com.chatapp;


import android.app.ProgressDialog;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.DecelerateInterpolator;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.flaviofaria.kenburnsview.KenBurnsView;
import com.parse.GetDataCallback;
import com.parse.LogOutCallback;
import com.parse.ParseException;
import com.parse.ParseFile;
import com.parse.ParseUser;

import java.io.ByteArrayOutputStream;

import de.hdodenhof.circleimageview.CircleImageView;


/**
 * A simple {@link Fragment} subclass.
 */
public class NavigationDrawerFragment extends Fragment {
    private static final float BLUR_RADIUS = 11f;
    RecyclerView activeRecyclerView;
    RecyclerView.Adapter activeAdapter;
    RecyclerView.LayoutManager activeLayoutManager;
    int ICONS[] = {R.drawable.ic_stat_thought7,R.drawable.ic_stat_fun33,R.drawable.ic_stat_man117,R.drawable.ic_stat_profile15,R.drawable.ic_stat_gear39,R.drawable.ic_stat_logout13};
    String drawerNames[]={"Casual Chat","Notoriety Leaderboard","Current Statistics","Notoriety Window","Settings","Logout"};
    ParseUser currentUserParse;
    ParseFile imge;
    TextView tva;

    String assf;
    KenBurnsView kbv;

 private ActionBarDrawerToggle mdrawerToggle;
 private DrawerLayout mdrawerLayout;
    RelativeLayout ivy;
    CircleImageView userclick;
    public NavigationDrawerFragment() {
        // Required empty public constructor
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);


        activeRecyclerView = (RecyclerView) getActivity().findViewById(R.id.rec);
        activeLayoutManager = new LinearLayoutManager(getActivity().getApplicationContext());
        activeRecyclerView.setLayoutManager(activeLayoutManager);
        activeAdapter = new ActiveAdapter(drawerNames,ICONS);
        activeRecyclerView.setAdapter(activeAdapter);

        currentUserParse = ParseUser.getCurrentUser();

        imge = (ParseFile) currentUserParse.getParseFile("photo");
       // ParseUser ui = ParseUser.getCurrentUser();

        try {
            assf   = ParseUser.getCurrentUser().get("FirstName").toString();
        } catch (Exception e) {
            e.printStackTrace();
        }
        // Toast.makeText(getActivity(), " "+assf, Toast.LENGTH_SHORT).show();


       // userclick.setImageDrawable(null);
     //   displayImage(imge, userclick);

    }

    @Override
    public void onResume() {
        super.onResume();
       // userclick.setImageDrawable(null);
        displayImage(imge, userclick);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_navigation_drawer, container, false);

        //ivy = (RelativeLayout) v.findViewById(R.id.image);


       /* Bitmap bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.circlesabe);
        Bitmap blurredBitmap = blur(bitmap);

        BitmapDrawable ar = new BitmapDrawable(blurredBitmap);
       */ //ivy.setBackground(ar);
        try{
            assf   = ParseUser.getCurrentUser().get("FirstName").toString();
        }catch (Exception er){
            er.printStackTrace();
        }
        Animation fadeIn = new AlphaAnimation(0, 1);
        fadeIn.setInterpolator(new DecelerateInterpolator()); //add this
        fadeIn.setDuration(1000);


        tva = (TextView) v.findViewById(R.id.texi);
        userclick = (CircleImageView) v.findViewById(R.id.userimage);
        tva.setAnimation(fadeIn);
        tva.setText(assf);
        userclick.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent ac = new Intent(getActivity(), ProfileView.class);
                startActivity(ac);
            }
        });
       // ivy.setImageBitmap(blurredBitmap);


        return v;
    }
    private void displayImage(ParseFile thumbnail, final CircleImageView myi) {
        if (thumbnail != null) {

            thumbnail.getDataInBackground(new GetDataCallback() {

                @Override
                public void done(byte[] data, ParseException e) {

                    if (e == null) {
                        try {
                            Bitmap bitmap = BitmapFactory.decodeByteArray(data, 0, data.length);
                            ByteArrayOutputStream blob = new ByteArrayOutputStream();
                            bitmap.compress(Bitmap.CompressFormat.PNG, 0 /*ignored for PNG*/, blob);

                            byte[] bitmapdata = blob.toByteArray();
                            BitmapDrawable ob = new BitmapDrawable(getResources(), bitmap);
                            //rotatedScaledMealImage.compress(Bitmap.CompressFormat.JPEG, 100, bos);
                            //Bitmap bmp = BitmapFactory.decodeByteArray(data, 0,
                            //  data.length);

                            if (bitmap != null) {

                                Log.e("parse file ok", " null");
                                Animation fadeIn = new AlphaAnimation(0, 1);
                                fadeIn.setInterpolator(new DecelerateInterpolator()); //add this
                                fadeIn.setDuration(1000);
                                myi.setAnimation(fadeIn);
                                myi.setImageDrawable(ob);
                                //myi.setBackgroundDrawable(ob);

                            }
                            else{
                                Animation fadeIn = new AlphaAnimation(0, 1);
                                fadeIn.setInterpolator(new DecelerateInterpolator()); //add this
                                fadeIn.setDuration(1000);
                                myi.setAnimation(fadeIn);
                                myi.setImageDrawable(getResources().getDrawable(R.drawable.default_pic));
                            }
                        } catch (Resources.NotFoundException e1) {
                            e1.printStackTrace();
                        }
                    } else {

                    }

                }
            });
        } else {

            Log.e("parse file", " null");

        }

    }


    public void setUp(DrawerLayout dLayout, Toolbar toolBar) {
        mdrawerLayout = dLayout;
        mdrawerLayout.setScrimColor(Color.parseColor("#80000000"));

        mdrawerToggle = new ActionBarDrawerToggle(getActivity(),dLayout,toolBar,R.string.opendrawer,R.string.closedrawer){
            @Override
            public void onDrawerOpened(View drawerView) {
                super.onDrawerOpened(drawerView);
                               // userclick.setImageDrawable(null);
                displayImage(imge, userclick);
            }

            @Override
            public void onDrawerClosed(View drawerView) {
                super.onDrawerClosed(drawerView);
              //  userclick.setImageDrawable(null);
                displayImage(imge, userclick);
            }
        };

        mdrawerLayout.setDrawerListener(mdrawerToggle);
        mdrawerLayout.post(new Runnable() {
            @Override
            public void run() {
                mdrawerToggle.syncState();
            }
        });
    }

    public class ActiveAdapter extends RecyclerView.Adapter<ActiveAdapter.ViewHolder> {
      String arr[];
    int ico[];


    public ActiveAdapter(String arr[],int ico[]) {
        this.arr = arr;
        this.ico = ico;
    }

        // Provide a reference to the views for each data item
        // Complex data items may need more than one view per item, and
        // you provide access to all the views for a data item in a view holder
        public class ViewHolder extends RecyclerView.ViewHolder {
            TextView tv;
            ImageView iv;
            RelativeLayout rel;
           // public CardView activeCV;

            public ViewHolder(View CV) {
                super(CV);
                rel  = (RelativeLayout) CV.findViewById(R.id.relItem);
                tv = (TextView) CV.findViewById(R.id.icondetails);
                iv = (ImageView) CV.findViewById(R.id.iconimage);
                rel.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        rel.setBackgroundColor(Color.parseColor("#99FAFAFA"));

                    }
                });
                CV.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        int pos = getLayoutPosition();
                        if (pos == 0) {
                            Intent ac = new Intent(getActivity(),RandomChat.class);
                            startActivity(ac);

                        }
                        else if(pos==5)
                        {

                            final ProgressDialog m = new ProgressDialog(getContext());
                            m.setMessage("Logging you out...");
                            m.show();
                            getActivity().stopService(new Intent(getActivity(), MessageService.class));
                           // ParseUser.getCurrentUser().logOut();
                            ParseUser.logOutInBackground(new LogOutCallback() {
                                @Override
                                public void done(ParseException e) {
                                    m.dismiss();
                                    getActivity().finish();
                                }
                            });

                            //Intent intent = new Intent(getActivity(), LoginActivity.class);
                            //startActivity(intent);
                        }  else if(pos == 1){
                            Intent jo = new Intent();
                            jo.setClass(getActivity(),NotorietyLeaderBoard.class);
                            startActivity(jo);

                        }

                        else if(pos == 3){
                            Intent jo = new Intent();
                            jo.setClass(getActivity(),SocialNotorietyActivity.class);
                            startActivity(jo);
                        }
                        else if (pos == 2){
                            Intent ms = new Intent();
                            ms.setClass(getActivity(),YourProfile.class);
                            startActivity(ms);
                        }
                        else if(pos == 4){
                            Intent set = new Intent();
                            set.setClass(getContext(),Settings.class);
                            startActivity(set);
                        }
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
                    .inflate(R.layout.navigation_single_item, parent, false);
            // set the view's size, margins, paddings and layout parameters


            ViewHolder vh = new ViewHolder(v);
            return vh;
        }

        // Replace the contents of a view (invoked by the layout manager)
        @Override
        public void onBindViewHolder(final ViewHolder holder, int position) {
            // - get element from your dataset at this position
            // - replace the contents of the view with that element
            Animation fadeIn = new AlphaAnimation(0, 1);
            fadeIn.setInterpolator(new DecelerateInterpolator()); //add this
            fadeIn.setDuration(1000);
holder.tv.setAnimation(fadeIn);
            holder.tv.setText(arr[position]);
holder.iv.setAnimation(fadeIn);
            holder.iv.setImageResource(ico[position]);
            //      .setText(arr[position]);
          //  ((ImageView)temp.findViewById(R.id.iconimage)).setImageResource(ico[position]);

        }


        // Return the size of your dataset (invoked by the layout manager)
        @Override
        public int getItemCount() {
            return 6;
        }
    }

/*
public interface onClick{
    public
}*/
}
