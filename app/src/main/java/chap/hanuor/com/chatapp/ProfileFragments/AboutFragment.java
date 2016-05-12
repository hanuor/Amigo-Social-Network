package chap.hanuor.com.chatapp.ProfileFragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.parse.FindCallback;
import com.parse.GetCallback;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.ParseUser;
import com.parse.SaveCallback;

import org.json.JSONArray;
import org.json.JSONException;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import chap.hanuor.com.chatapp.R;
import chap.hanuor.com.chatapp.TransparentProgressDialog;

public class AboutFragment extends Fragment {
    ImageView like;
    String statusFetcherId;
    RecyclerView activeRecyclerView;
    RecyclerView.Adapter activeAdapter;
    RecyclerView.LayoutManager activeLayoutManager;
    String drawerNames[] = {"Random Chat", "Logout"};
    ArrayList<String> likesUpdater;
    ArrayList<String> likesFetcher;
    int likesCounter;
    ArrayList<String> germany;
TransparentProgressDialog pd;
    String separator = "%3:11071995:11";
    List<String> list11;
    TextView liker;
    String res[];
    ArrayList<String> finalise;
    ArrayList<String> likesFe;
    ArrayList<String> upme;
    String LikesFetch;
    String currne;
    String ObjectIdofPost;
    TextView likeText;
    TextView texi;
    int ICONS[] = {R.drawable.ic_action_work, R.drawable.ic_stat_data};

    public AboutFragment() {

    }

    @Override
    public void onActivityCreated(@Nullable final Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        pd = new TransparentProgressDialog(getContext(),R.drawable.ic_amigo_icon);
        View v = getView();
        likeText = (TextView) v.findViewById(R.id.nubmo);
        texi = (TextView) v.findViewById(R.id.likeIds);
        liker = (TextView) v.findViewById(R.id.likesname);
        Current_Status(statusFetcherId);
        //setShareIds(statusFetcherId);
        currne = ParseUser.getCurrentUser().getObjectId().toString();
        likesUpdater = new ArrayList<String>();
        upme = new ArrayList<String>();
        likesFe = new ArrayList<String>();
        likesFetcher = new ArrayList<String>();
        germany = new ArrayList<String>();
        like = (ImageView) v.findViewById(R.id.imageView2);
        like.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                pd.show();
                if (likesCounter == 0) {
                    likesCounter = 1;
                    insertintoParse(statusFetcherId);
                    final String currentUser = ParseUser.getCurrentUser().getObjectId().toString();
                    like.setImageDrawable(getResources().getDrawable(R.drawable.ic_stat_redheart));
                    ParseQuery<ParseObject> qa = ParseQuery.getQuery("AmigoPosts");
                    qa.whereEqualTo("CreatorId", statusFetcherId);
                    qa.findInBackground(new FindCallback<ParseObject>() {
                                            @Override
                                            public void done(List<ParseObject> list, ParseException e) {
                                                if (list.size() != 0) {
                                                    final String am = list.get(0).getObjectId().toString();
                                                    String mi = list.get(0).get("LikesCounter").toString();
                                                    final double po = Double.parseDouble(mi);
                                                    ParseQuery<ParseObject> qw = ParseQuery.getQuery("AmigoPosts");
                                                    qw.getInBackground(am, new GetCallback<ParseObject>() {
                                                                @Override
                                                                public void done(ParseObject parseObject, ParseException e) {
                                                                    JSONArray jArray = parseObject.getJSONArray("ListLikes");
                                                                    //  likesFetcher = parseObject.getJSONArray("ListLikes");
                                                                    Log.d("nsamasnasSASAW", " " + jArray.length());
                                                                    //  likesFetcher = parseObject.getJSONArray("ListLikes");
                                                                    try {


                                                                        for (int i = 0; i <= jArray.length(); i++) {
                                                                            //listdata.add(jArray.get(i).toString());
                                                                            try {

                                                                                likesFetcher.add(jArray.getString(i));
                                                                                Log.d("gsdwe", " " + likesFetcher.get(i).toString());
                                                                            } catch (JSONException e1) {
                                                                                e1.printStackTrace();
                                                                            }
                                                                        }
                                                                        String ger = "";
                                                                        for (int y = 0; y < likesFetcher.size(); y++) {
                                                                            ger = likesFetcher.get(y).toString();

                                                                            // ArrayList<String> elephantList = Arrays.asList(ger.split(","));
                                                                            //String g = ger.substring(2, 12);
                                                                            //likesFe.add(y, g);

                                                                        }
                                                                        String[] animalsArray = ger.split(",");
                                                                        finalise = new ArrayList<String>();
                                                                        // Log.d("gsdweqwwsaqzzxsw", " " + animalsArray.length);
                                                                        for (int l = 0; l < animalsArray.length; l++) {
                                                                            String fer = animalsArray[l].toString();
                                                                            String result = fer.replaceAll("[^\\w\\s]", "");
                                                                            // finalise[l] = result;
                                                                            finalise.add(l, result);
                                                                            // Log.d("Make in indaia",result);
                                                                        }
                                                                        int yu = 0;
                                                                        for (int m = 0; m < finalise.size(); m++) {
                                                                            if (finalise.get(m).toString().equals(currentUser)) {
                                                                                yu = 0;
                                                                                break;
                                                                            } else {
                                                                                yu = 1;
                                                                            }
                                                                        }
                                                                        if (yu == 1) {


                                                                            finalise.add(currentUser);
                                                                            for (int p = 0; p < finalise.size(); p++) {
                                                                                Log.d(" FREWSRWWWS A", finalise.get(p).toString());
                                                                                //upme.add(upme.size(),currentUser);
                                                                            }

                                                                            // Likes_input_function(finalise);
                                                                            ParseQuery<ParseObject> qww = ParseQuery.getQuery("AmigoPosts");
                                                                            qww.getInBackground(am, new GetCallback<ParseObject>() {
                                                                                @Override
                                                                                public void done(ParseObject parseObject, ParseException e) {
                                                                                    int m = Integer.valueOf(parseObject.getString("Likes"));
                                                                                    m++;
                                                                                    String k = String.valueOf(m);
                                                                                    //likesUpdater.add(0, currentUser);
                                                                                    parseObject.put("ListLikes", Arrays.asList(finalise));
                                                                                    parseObject.put("Likes", k);
                                                                                    StringBuilder io = new StringBuilder();
                                                                                    io.append(""+(po+0.03));
                                                                                    String mes = io.toString();
                                                                                    parseObject.put("LikesCounter",mes);
                                                                                    if (m == 1){
                                                                                        liker.setText("Like");
                                                                                        likeText.setText(k);
                                                                                    }else{
                                                                                        liker.setText("Likes");
                                                                                        likeText.setText(k);
                                                                                    }

                                                                                    parseObject.saveInBackground(new SaveCallback() {
                                                                                        @Override
                                                                                        public void done(ParseException e) {
                                                                                            pd.dismiss();
                                                                                        }
                                                                                    });
                                                                                }
                                                                            });

                                                                        }
                                                                    } catch (Exception e8) {
                                                                        e8.printStackTrace();
                                                                    }


                                                                }
                                                            }

                                                    );
                                                }
                                            }
                                        }

                    );

              /*

               final ParseQuery<ParseUser> query = ParseUser.getQuery();
                query.getInBackground(statusFetcherId, new GetCallback<ParseUser>() {
                    @Override
                    public void done(ParseUser parseUser, ParseException e) {
                        String m = parseUser.get("StatusLikes").toString();

                        //int  ret = ((int) parseUser.get("StatusLikes"));
                        int ret = Integer.valueOf(m);
                        ret = ret+1;
                        likeText.setText("" + ret);
                        //final int finalRet = ret;
                        final String noman = String.valueOf(ret);
                        Toast.makeText(FriendsProfileActivty.this, " " + noman, Toast.LENGTH_SHORT).show();
                        ParseQuery<ParseUser> qu = ParseUser.getQuery();
                        qu.getInBackground(statusFetcherId, new GetCallback<ParseUser>() {
                            @Override
                            public void done(ParseUser parseUser, ParseException e) {
                                //    parseUser.put("UserStatus", getterstatus);

                                parseUser.put("StatusLikes","londa");

                                parseUser.saveInBackground();
                                Toast.makeText(FriendsProfileActivty.this, "Saved", Toast.LENGTH_SHORT).show();
                             }
                        });
                    }
                });*/
                } else if (likesCounter == 1) {
                    pd.show();
                    for (int b = 0; b < germany.size(); b++) {
                        if (germany.get(b).toString().equals(currne)) {
                            germany.remove(currne);
                        }
                    }
                    ParseQuery<ParseObject> qa = ParseQuery.getQuery("AmigoPosts");
                    qa.whereEqualTo("CreatorId", statusFetcherId);
                    qa.findInBackground(new FindCallback<ParseObject>() {
                        @Override
                        public void done(List<ParseObject> list, ParseException e) {
                            if (list.size() != 0) {
                                final String am = list.get(0).getObjectId().toString();
                                String lik = list.get(0).get("LikesCounter").toString();
                                final double po = Double.parseDouble(lik);
                                ParseQuery<ParseObject> qww = ParseQuery.getQuery("AmigoPosts");
                                qww.getInBackground(am, new GetCallback<ParseObject>() {
                                    @Override
                                    public void done(ParseObject parseObject, ParseException e) {
                                        //int m = Integer.valueOf(parseObject.getString("Likes"));
                                        int m = Integer.valueOf(parseObject.getString("Likes"));
                                        m--;
                                        String k = String.valueOf(m);
                                        if (m == 1){
                                            liker.setText("Like");
                                            likeText.setText(k);
                                        }else{
                                            liker.setText("Likes");
                                            likeText.setText(k);
                                        }
                                        StringBuilder io = new StringBuilder();
                                        io.append(""+(po-0.02));
                                        String mes = io.toString();
                                        //likesUpdater.add(0, currentUser);
                                        parseObject.put("ListLikes", Arrays.asList(germany));
                                        parseObject.put("Likes", k);
                                        parseObject.put("LikesCounter",mes);

                                        // likeText.setText(k);
                                        parseObject.saveInBackground(new SaveCallback() {
                                            @Override
                                            public void done(ParseException e) {
                                                pd.dismiss();
                                            }
                                        });
                                    }
                                });
                            }
                        }
                    });
                  /*  Log.d("LIRE",""+res.length);
                    for( int q = 0;q<res.length;q++){
                        StringBuilder mn = new StringBuilder();
                        mn.append("@");
                        mn.append(ParseUser.getCurrentUser().getUsername().toString());

                        String g = mn.toString();
                        Log.d("FRGGRGDEMON"," "+g);
                        if(res[q].contentEquals(g)){
                            Log.d("YRRES","popp");
                            res[q] = null;
                            break;
                        }
                    }
                    ParseQuery<ParseObject> qay = ParseQuery.getQuery("AmigoPosts");
                    qay.whereEqualTo("CreatorId",statusFetcherId);
                    qay.findInBackground(new FindCallback<ParseObject>() {
                        @Override
                        public void done(List<ParseObject> list, ParseException e) {
                            if(list.size() != 0){
                                try {
                                    String dex = list.get(0).getObjectId().toString();
                                    StringBuilder ex = new StringBuilder();
                                    for(int b = 0;b<res.length;b++){
                                        if(res.length<1){


                                        ex.append(res[b]);
                                       // ex.append(separator);
                                        }
                                        else{

                                            ex.append(res[b]);
                                             ex.append(separator);
                                        }
                                        }
                                    final String mob = ex.toString();
                                    ParseQuery<ParseObject> qr = ParseQuery.getQuery("AmigoPosts");
                                    qr.getInBackground(dex, new GetCallback<ParseObject>() {
                                        @Override
                                        public void done(ParseObject parseObject, ParseException e) {
                                            parseObject.put("LikesName",mob);
                                            parseObject.saveInBackground();
                                        }
                                    });


                                }catch (Exception v){
                                    v.printStackTrace();
                                }
                            }
                        }
                    });


*/
                    like.setImageDrawable(getResources().getDrawable(R.drawable.ic_stat_broken21));

                    likesCounter = 0;
                }
            }
        });
        //currentUserParse = ParseUser.getCurrentUser();

    }

    public void setShareIds(String hera) {
        ParseQuery<ParseObject> qa = ParseQuery.getQuery("AmigoPosts");
        qa.whereEqualTo("CreatorId", hera);
        qa.findInBackground(new FindCallback<ParseObject>() {
            @Override
            public void done(List<ParseObject> list, ParseException e) {
                if (list.size() != 0) {
                    String postObID = list.get(0).getObjectId().toString();
                    //String count = list.get(0).get("ShareCount").toString();
                    //no.setText(count);
                    try{

                        String getShareIds = list.get(0).get("LikesName").toString();
                        if (getShareIds!=null) {
                            String arr[] = getShareIds.split("\\%3:11071995:11");
                            comma(arr);
                            StringBuilder s = new StringBuilder();
                            for (int y = 0; y < arr.length; y++) {
                                if(arr.length>1){

                                    liker.setText("Likes");
                                    s.append(arr[y].toString());
                                    if(y != arr.length-1){
                                        s.append(", ");
                                    }else{
                                        s.append("");
                                    }

                                }
                                else{
                                    liker.setText("Like");
                                    s.append(arr[y].toString());
                                }
                            }
                            String str = s.toString();
                            texi.setText(str);
                        }
                        else{
                            texi.setText("");
                        }
                    }catch (Exception ew){
                        ew.printStackTrace();
                    }


                }
            }
        });
    }

    private void comma(String[] arr) {
        res = arr;
    }

    private void Likes_input_function(String men) {

        ObjectIdofPost = men;
    }

    private void insertintoParse(String her) {

        ParseQuery<ParseObject> qa = ParseQuery.getQuery("AmigoPosts");
        qa.whereEqualTo("CreatorId",her);
        qa.findInBackground(new FindCallback<ParseObject>() {
            @Override
            public void done(List<ParseObject> list, ParseException e) {
                if(list.size()!=0){
                    String postObID = list.get(0).getObjectId().toString();
                    String val = list.get(0).getObjectId().toString();
                    final int foo = Integer.parseInt(val);
                    StringBuilder nu = new StringBuilder();
                    nu.append(""+foo+1);
                    final String newval = nu.toString();
                    try {
                        final String shareIdes = list.get(0).get("LikesName").toString();
                        ParseQuery<ParseObject> qr = ParseQuery.getQuery("AmigoPosts");
                        qr.getInBackground(postObID, new GetCallback<ParseObject>() {
                            @Override
                            public void done(ParseObject parseObject, ParseException e) {

                                parseObject.put("LikesCounter",newval);
                                StringBuilder onma = new StringBuilder();
                                if(shareIdes!=null){
                                    onma.append(shareIdes);
                                    onma.append("@");
                                    onma.append(ParseUser.getCurrentUser().getUsername().toString());
                                    onma.append(separator);
                                    String ulo = onma.toString();
                                    parseObject.put("LikesName",ulo);
                                    parseObject.saveInBackground();

                                }

                                else{
                                    onma.append("@");
                                    onma.append(ParseUser.getCurrentUser().getUsername().toString());
                                    onma.append(separator);
                                    String hero = onma.toString();
                                    parseObject.put("LikesName",hero);
                                    parseObject.saveInBackground();
                                }
                            }
                        });
                    }catch (Exception er){
                        er.printStackTrace();
                    }

                }
            }
        });

    }

    private void Current_Status(String mos) {
        ParseQuery<ParseObject> qw = ParseQuery.getQuery("AmigoPosts");
        qw.whereEqualTo("CreatorId", statusFetcherId);
        qw.findInBackground(new FindCallback<ParseObject>() {
            @Override
            public void done(List<ParseObject> list, ParseException e) {
                if (list.size() != 0) {

                    // Toast.makeText(ListUsersActivity.this, "found", Toast.LENGTH_SHORT).show();
                    String objId = list.get(0).getObjectId().toString();
                    Likes_input_function(objId);
                    ParseQuery<ParseObject> qew = ParseQuery.getQuery("AmigoPosts");
                    qew.whereEqualTo("objectId", objId);
                    qew.findInBackground(new FindCallback<ParseObject>() {
                        @Override
                        public void done(List<ParseObject> list, ParseException e) {
                            if (list.size() != 0) {
                                // Toast.makeText(ListUsersActivity.this, "found", Toast.LENGTH_SHORT).show();
                                try {
                                    current_likes(list.get(0).get("Likes").toString());
                                    int foo = Integer.parseInt(list.get(0).get("Likes").toString());
                                    if(foo == 1){
                                        liker.setText("Like");
                                        likeText.setText(list.get(0).get("Likes").toString());
                                    }else{
                                        liker.setText("Likes");
                                        likeText.setText(list.get(0).get("Likes").toString());
                                    }

                                } catch (Exception e1) {
                                    e1.printStackTrace();
                                }

                            }
                        }
                    });


                } else {
                    // Toast.makeText(ListUsersActivity.this, "Not Found", Toast.LENGTH_SHORT).show();
                    //e.printStackTrace();
                }
            }
        });


        final String currentUser = ParseUser.getCurrentUser().getObjectId().toString();
        list11 = new ArrayList<String>();

        ParseQuery<ParseObject> qa = ParseQuery.getQuery("AmigoPosts");
        qa.whereEqualTo("CreatorId", statusFetcherId);
        qa.findInBackground(new FindCallback<ParseObject>() {
            @Override
            public void done(List<ParseObject> list, ParseException e) {
                if (list.size() != 0) {
                    String am = list.get(0).getObjectId().toString();
                   /* ParseQuery<ParseObject> qw2 = ParseQuery.getQuery("AmigoPosts");
                    qw2.whereEqualTo("objectId", am);
                    qw2.findInBackground(new FindCallback<ParseObject>() {
                        @Override
                        public void done(List<ParseObject> list, ParseException e) {
                            if (list.size() > 0) {
                                ArrayList<String> g = new ArrayList<String>();
                                ParseObject p = list.get(0);
                                if (p.getList("ListLikes") != null) {

                                    list11 = p.getList("ListLikes");
                                }
                                Log.d("Please Work", " " + list11.size());
                                for (int j = 0; j < list11.size(); j++) {

                                        g.add(j,list11.get(j).toString();
                                    Log.d("dssaw"," "+g.get(j).toString());
                                }
                            }
                        }
                    });
                   */
                    ParseQuery<ParseObject> qw = ParseQuery.getQuery("AmigoPosts");
                    qw.getInBackground(am, new GetCallback<ParseObject>() {
                        @Override
                        public void done(ParseObject parseObject, ParseException e) {
                            // likesFetcher = parseObject.getList("ListLikes");
                            JSONArray jArray = parseObject.getJSONArray("ListLikes");
                            //ArrayList<String> testStringArrayList = (ArrayList<String>)parseObject.get("ListLikes");
                            //Log.d("New Methdo"," "+testStringArrayList.size());
                            //String myString = testStringArrayList.get(1);
                            Log.d("nsamasnasSASAW", " " + jArray.length());
                            //  likesFetcher = parseObject.getJSONArray("ListLikes");
                            try {


                                for (int i = 0; i <= jArray.length(); i++) {
                                    //listdata.add(jArray.get(i).toString());
                                    try {

                                        likesFetcher.add(jArray.getString(i));
                                        Log.d("gsdwe", " " + likesFetcher.get(i).toString());
                                    } catch (JSONException e1) {
                                        e1.printStackTrace();
                                    }
                                }
                                String ger = "";
                                for (int y = 0; y < likesFetcher.size(); y++) {
                                    ger = likesFetcher.get(y).toString();

                                    // ArrayList<String> elephantList = Arrays.asList(ger.split(","));
                                    //String g = ger.substring(2, 12);
                                    //likesFe.add(y, g);

                                }
                                String[] animalsArray = ger.split(",");
                                ArrayList<String> finalise = new ArrayList<String>();
                                // Log.d("gsdweqwwsaqzzxsw", " " + animalsArray.length);
                                for (int l = 0; l < animalsArray.length; l++) {
                                    String fer = animalsArray[l].toString();
                                    String result = fer.replaceAll("[^\\w\\s]", "");

                                    finalise.add(l, result);

                                }
                                fetch_ids(finalise);
                                for (int h = 0; h < finalise.size(); h++) {
                                    if (finalise.get(h).toString().equalsIgnoreCase(currentUser)) {
                                        likesCounter = 1;
                                        like.setImageDrawable(getResources().getDrawable(R.drawable.ic_stat_redheart));
                                    }
                                }
                            } catch (Exception e2) {
                                e2.printStackTrace();
                            }
                        }
                    });

                }
            }
        });


    }



    private void fetch_ids(ArrayList<String> finalise) {
        germany = finalise;

    }


    private void current_likes(String likes) {
        LikesFetch = likes;
    }






    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        Bundle bundle = this.getArguments();
        statusFetcherId = bundle.getString("ID");
        return inflater.inflate(R.layout.about_fragment,container, false);

    }
}
