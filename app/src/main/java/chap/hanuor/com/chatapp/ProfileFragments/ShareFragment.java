package chap.hanuor.com.chatapp.ProfileFragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.parse.FindCallback;
import com.parse.GetCallback;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.ParseUser;

import java.util.Arrays;
import java.util.List;

import chap.hanuor.com.chatapp.R;


public class ShareFragment extends Fragment {
    TextView no;
    String FriendsId;
    TextView ts;
    Button share;
    TextView field;
    String res[];
    String separator = "%3:11071995:11";

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        Bundle bundle = this.getArguments();
        FriendsId = bundle.getString("Objec");
        return inflater.inflate(R.layout.fragment_share,container, false);

    }
public void setShareIds(String hera) {
    ParseQuery<ParseObject> qa = ParseQuery.getQuery("AmigoPosts");
    qa.whereEqualTo("CreatorId", hera);
    qa.findInBackground(new FindCallback<ParseObject>() {
        @Override
        public void done(List<ParseObject> list, ParseException e) {
            if (list.size() != 0) {
                String postObID = list.get(0).getObjectId().toString();
                String count = list.get(0).get("ShareCount").toString();
                no.setText(count);
                try{

                String getShareIds = list.get(0).get("SharesName").toString();
                if (getShareIds!=null) {
                    String arr[] = getShareIds.split("\\%3:11071995:11");
                    comma(arr);
                    StringBuilder s = new StringBuilder();
                    for (int y = 0; y < arr.length; y++) {
                        if(arr.length>1){

                            ts.setText("Shares");
                        s.append(arr[y].toString());
                        if(y != arr.length-1){
                            s.append(", ");
                        }else{
                            s.append("");
                        }

                        }
                        else if(arr.length==0){
                            ts.setText("Shares");
                            s.append(arr[y].toString());
                        }else{
                            ts.setText("Share");
                            if(arr[0]!=""){
                                s.append(arr[y].toString());
                            }
                        }
                        }
                    String str = s.toString();
                    if(str.equals("")){
                        field.setText(" ");
                    }else {
                        field.setText(str);
                    }
                }
                    else{
                    field.setText("");
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

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        View v = getView();
        setShareIds(FriendsId);
        field = (TextView) v.findViewById(R.id.shareIds);
        ts = (TextView) v.findViewById(R.id.shareText);
        no = (TextView) v.findViewById(R.id.sharenumber);
        share = (Button) v.findViewById(R.id.sharebut);
        share.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int co = 0;
                for(int p = 0 ;p<res.length;p++){
                    StringBuilder mn = new StringBuilder();
                    mn.append("@");
                    mn.append(ParseUser.getCurrentUser().getUsername().toString());

                    String g = mn.toString();
                    Log.d("FRGGRGDEMON"," "+g);
                    if(res[p].contentEquals(g)){
                        co = 1;
                        break;
                    }
                }
                if (co == 0) {


                insertintoParse(FriendsId);
                Current_Status(FriendsId);
                backgroundSharesCount(FriendsId);
                }else{
                    Snackbar.make(view,"You have already shared this pin",Snackbar.LENGTH_SHORT).show();
                }
                }


        });


    }

    private void insertintoParse(String her) {

        ParseQuery<ParseObject> qa = ParseQuery.getQuery("AmigoPosts");
        qa.whereEqualTo("CreatorId",her);
        qa.findInBackground(new FindCallback<ParseObject>() {
            @Override
            public void done(List<ParseObject> list, ParseException e) {
                if(list.size()!=0){
                    String postObID = list.get(0).getObjectId().toString();
try {
    final String shareIdes = list.get(0).get("SharesName").toString();
                    ParseQuery<ParseObject> qr = ParseQuery.getQuery("AmigoPosts");
                qr.getInBackground(postObID, new GetCallback<ParseObject>() {
                    @Override
                    public void done(ParseObject parseObject, ParseException e) {
StringBuilder onma = new StringBuilder();
                        if(shareIdes!=null){
                        onma.append(shareIdes);
                            onma.append("@");
                            onma.append(ParseUser.getCurrentUser().getUsername().toString());
                            onma.append(separator);
                            String ulo = onma.toString();
                            parseObject.put("SharesName",ulo);
                            parseObject.saveInBackground();

                        }

                        else{
                            onma.append("@");
                            onma.append(ParseUser.getCurrentUser().getUsername().toString());
                            onma.append(separator);
                            String hero = onma.toString();
                            parseObject.put("SharesName",hero);
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

    public void backgroundSharesCount(String fera){
        ParseQuery<ParseObject> qa = ParseQuery.getQuery("AmigoPosts");
        qa.whereEqualTo("CreatorId",fera);
        qa.findInBackground(new FindCallback<ParseObject>() {
            @Override
            public void done(List<ParseObject> list, ParseException e) {
                if(list.size()!=0){
                    String objectIdofthepost = list.get(0).getObjectId().toString();
                    String sh = list.get(0).get("SharesCounter").toString();
                    final double jo = Double.parseDouble(sh);
                    ParseQuery<ParseObject> qs= ParseQuery.getQuery("AmigoPosts");
                    qs.getInBackground(objectIdofthepost, new GetCallback<ParseObject>() {
                        @Override
                        public void done(ParseObject parseObject, ParseException e) {
                            if(e==null){
                                int m;
try {
    m = Integer.valueOf(parseObject.getString("ShareCount"));
                        m++;
                                String k = String.valueOf(m);
                                no.setText(k);
                                parseObject.put("ShareCount",k);
    StringBuilder io = new StringBuilder();
    io.append(""+(jo+0.07));
    String mes = io.toString();
                                parseObject.put("SharesCount",mes);
                                parseObject.saveInBackground();

}catch (Exception eq){
    eq.printStackTrace();
}

                            }
                        }
                    });

                }
            }
        });


    }
    private void Current_Status(String mos) {

        ParseQuery<ParseUser> query = ParseUser.getQuery();
        query.getInBackground(mos, new GetCallback<ParseUser>() {
            @Override
            public void done(ParseUser parseUser, ParseException e) {
                //  String heartCount = parseUser.get("StatusLikes").toString();
                //likeText.setText(heartCount);
                final String af = parseUser.get("UserStatus").toString();
                final String cureentuser = ParseUser.getCurrentUser().getObjectId().toString();
                ParseQuery<ParseUser> wer = ParseUser.getQuery();
                wer.getInBackground(cureentuser, new GetCallback<ParseUser>() {
                    @Override
                    public void done(ParseUser parseUser, ParseException e) {
                        parseUser.put("UserStatus",af);
                        parseUser.saveInBackground();
                        ParseQuery<ParseObject> qa = ParseQuery.getQuery("AmigoPosts");
                        qa.whereEqualTo("CreatorId", cureentuser);
                        qa.findInBackground(new FindCallback<ParseObject>() {
                            @Override
                            public void done(List<ParseObject> list, ParseException e) {
                                if(list.size()!=0){
                                    final String am = list.get(0).getObjectId().toString();
                                    ParseQuery<ParseObject> qww = ParseQuery.getQuery("AmigoPosts");
                                    qww.getInBackground(am, new GetCallback<ParseObject>() {
                                        @Override
                                        public void done(ParseObject parseObject, ParseException e) {
                                            //int m = Integer.valueOf(parseObject.getString("Likes"));

                                            StringBuilder tRex = new StringBuilder();
                                            tRex.append(cureentuser);
                                            tRex.append(FriendsId);
                                            String f = tRex.toString();
                                            parseObject.put("CreatorStatus",af);
                                            parseObject.put("ShareIds",f);
                                            parseObject.put("CreatorId",cureentuser);
                                            parseObject.put("Likes","0");
                                            parseObject.put("LikesName"," ");
                                            parseObject.put("ShareCount","0");
                                            parseObject.put("SharesName"," ");
                                            parseObject.put("ListLikes", Arrays.asList(""));
                                            parseObject.put("CommentsString","");
                                            parseObject.put("CommentIds","");
                                            parseObject.saveInBackground();
                                        }
                                    });
                                }
                                else{
                                    try {
                                       // final String am = list.get(0).getObjectId().toString();
                                        ParseObject parseObject = new ParseObject("AmigoPosts");
                                        StringBuilder tRex = new StringBuilder();
                                        tRex.append(cureentuser);
                                        tRex.append(FriendsId);
                                        String f = tRex.toString();
                                        parseObject.put("CreatorStatus",af);
                                        parseObject.put("ShareIds",f);
                                        parseObject.put("CreatorId",cureentuser);
                                        parseObject.put("Likes","0");
                                        parseObject.put("LikesName"," ");
                                        parseObject.put("ShareCount","0");
                                        parseObject.put("SharesName"," ");
                                        parseObject.put("ListLikes", Arrays.asList(""));
                                        parseObject.put("CommentsString","");
                                        parseObject.put("CommentIds","");
                                        parseObject.saveInBackground();
                                    } catch (Exception e1) {
                                        e1.printStackTrace();
                                    }


                                }
                            }
                        });
                    }
                });


              /*  status.setText(af);
                String bf = parseUser.getUsername().toString();
                StringBuilder sre = new StringBuilder();
                sre.append("- @");
                sre.append(bf);
                String cf = sre.toString();
                statusBy.setText(cf);*/
            }
        });
    }
}
