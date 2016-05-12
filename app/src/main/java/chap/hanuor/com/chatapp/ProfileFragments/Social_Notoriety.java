package chap.hanuor.com.chatapp.ProfileFragments;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;

import java.util.List;

import chap.hanuor.com.chatapp.R;

/**
 * Created by Shantanu Johri on 27-11-2015.
 */
public class Social_Notoriety extends Fragment {
    String objectId;
    RecyclerView activeRecyclerView;
    RecyclerView.Adapter activeAdapter;
    RecyclerView.LayoutManager activeLayoutManager;
    EditText et;
    TextView count;
    TextView commenter;
    Button send;
    String hew;
    String major;
    ImageView buto;
    String naam;
    String lzor;
    String separator = "%3:11071995:11";
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        Bundle bundle = this.getArguments();
        objectId = bundle.getString("ObjectO");
        return inflater.inflate(R.layout.activity_comments_section,container, false);

    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        //Toast.makeText(getActivity(), " "+objectId, Toast.LENGTH_SHORT).show();

        View v = getView();
        count = (TextView) v.findViewById(R.id.count);
        buto = (ImageView) v.findViewById(R.id.buttondss);
        commenter = (TextView) v.findViewById(R.id.commenter);
        activeRecyclerView = (RecyclerView) v.findViewById(R.id.commentrecycler);
       // Toast.makeText(getActivity(), " ObjectO" + objectId, Toast.LENGTH_SHORT).show();
       // activeRecyclerView.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.HORIZONTAL_LIST));
        activeRecyclerView.setHasFixedSize(true);
        activeLayoutManager = new LinearLayoutManager(getContext());
        activeRecyclerView.setLayoutManager(activeLayoutManager);
        activeRecyclerView.setItemAnimator(new DefaultItemAnimator());
       // et = (EditText) v.findViewById(R.id.etcomms);
        //send = (Button) v.findViewById(R.id.send);

       // Toast.makeText(getActivity(), "vavevava" + major, Toast.LENGTH_SHORT).show();
        Current_Status(objectId);
        buto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });
       /* send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(et.getText().equals("")){
                    Toast.makeText(getActivity(), "Please enter a message", Toast.LENGTH_SHORT).show();

                }
                else{
                    final String er = et.getText().toString();
                    Toast.makeText(getActivity(), "Comment added successfully", Toast.LENGTH_SHORT).show();
                    ParseQuery<ParseObject> qay = ParseQuery.getQuery("AmigoPosts");
                    qay.getInBackground(major, new GetCallback<ParseObject>() {
                        @Override
                        public void done(ParseObject parseObject, ParseException e) {
                            if(e == null){

                                StringBuilder nw = new StringBuilder();
                                StringBuilder ider = new StringBuilder();
                                Log.d("LOFERS"," "+hew);
                                if(hew!=null){

                                    Log.d("Here"," FLAJJSK");
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
                                activeAdapter.notifyDataSetChanged();
                                }
                                else{
                                    nw.append(er);
                                    nw.append(separator);
                                    Log.d("HereAgain"," FLAJJSK");
                                    ider.append(ParseUser.getCurrentUser().getUsername().toString());
                                    ider.append(separator);
                                    String nes = ider.toString();
                                    String mb = nw.toString();
                                   // activeAdapter.notifyItemChanged();
//                                    activeAdapter.notifyDataSetChanged();
                                    parseObject.put("CommentsString",mb);
                                    parseObject.put("CommentIds",nes);

                                    // String mb = nw.toString();
                                    // parseObject.put("CommentsString",mb);
                                    parseObject.saveInBackground();


                                }
                            }
                            else{
                                Log.d("jjhjjhhjhj","error");
                                e.printStackTrace();
                            }
                        }
                    });
                    et.setText("");
                }
            }
        });*/
    }
    private void Current_Status(String mosq) {

        ParseQuery<ParseObject> qw = ParseQuery.getQuery("AmigoPosts");
        qw.whereEqualTo("CreatorId", mosq);
        qw.findInBackground(new FindCallback<ParseObject>() {
            @Override
            public void done(List<ParseObject> list, ParseException e) {
                if (list.size() != 0) {


                    String objId = list.get(0).getObjectId().toString();
                    getComments(objId);
                    //ca

                    Likes_input_function(objId);


                } else {

                    //e.printStackTrace();
                }
            }
        });
    }
    private void Likes_input_function(String men) {
       // Toast.makeText(FriendsProfileActivty.this, " Everything at onbce" + men, Toast.LENGTH_SHORT).show();
        major = men;
       // ObjectIdofPost = men;
    }
    private void getComments(String obj) {

        ParseQuery<ParseObject> qa = ParseQuery.getQuery("AmigoPosts");
        qa.whereEqualTo("objectId",obj);
        qa.findInBackground(new FindCallback<ParseObject>() {
            @Override
            public void done(List<ParseObject> list, ParseException e) {
                if(e == null){
                    if(list.size()!=0){
                        try {

                            String amal = list.get(0).get("CommentsString").toString();
                            if(amal.contentEquals(" ")){
                                count.setText("0");
                                String majorLazer = list.get(0).getObjectId().toString();
                                String samba = list.get(0).get("CommentIds").toString();
                                String CommentIds[] = samba.split("\\%3:11071995:11");
                                String Comments[] = amal.split("\\%3:11071995:11");
                                Log.d("dextrqqwe", "" + Comments[0]);
                                capture_comms(amal, samba, majorLazer);
                            }
                            else {
                                String majorLazer = list.get(0).getObjectId().toString();
                                String samba = list.get(0).get("CommentIds").toString();
                                String CommentIds[] = samba.split("\\%3:11071995:11");
                                String Comments[] = amal.split("\\%3:11071995:11");
                                Log.d("dextre", "" + Comments[0]);
                                if(Comments[0].equals(" ")) {
                                    count.setText("0");
                                }
                                else{
                                    activeAdapter = new CommentsAdapter(Comments, CommentIds);
                                    activeRecyclerView.setAdapter(activeAdapter);
                                    if(Comments.length == 1){
                                        commenter.setText("Comment");

                                        count.setText("" + Comments.length);
                                    }
                                    else{
                                        commenter.setText("Comments");
                                        count.setText("" + Comments.length);
                                    }

                                }
                                    capture_comms(amal, samba, majorLazer);

                            }
                           /* if(amal.contains("%6`~!1107*]$"));
                            {

                                if(Comments.length==0){
                                   // Toast.makeText(CommentsSection.this, " No Comments Found", Toast.LENGTH_SHORT).show();
                                }

                            }*/
                        } catch (Exception e1) {
                            e1.printStackTrace();
                        }
                    }
                    else{
                       // Toast.makeText(CommentsSection.this, " No Comments Found", Toast.LENGTH_SHORT).show();
                    }
                }

            }
        });
    }

    private void capture_comms(String amal, String naasm,String ghi) {
        naam = naasm;
        hew = amal;
        lzor = ghi;
    }

    public class CommentsAdapter extends RecyclerView.Adapter<CommentsAdapter.ViewHolder> {
        String arr[];
        String mes[];


        public CommentsAdapter(String arr[],String mes[]) {
            this.arr = arr;
            this.mes = mes;

        }

        // Provide a reference to the views for each data item
        // Complex data items may need more than one view per item, and
        // you provide access to all the views for a data item in a view holder
        public class ViewHolder extends RecyclerView.ViewHolder {
            TextView tv;
            TextView idtv;
            //  ImageView iv;
            // public CardView activeCV;

            public ViewHolder(View CV) {
                super(CV);
                tv = (TextView) CV.findViewById(R.id.comm);
                idtv = (TextView) CV.findViewById(R.id.commid);
                //iv = (ImageView) CV.findViewById(R.id.ab);

            }
        }

        // Create new views (invoked by the layout manager)
        @Override
        public CommentsAdapter.ViewHolder onCreateViewHolder(ViewGroup parent,
                                                             int viewType) {
            // create a new view
            View v = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.comments_single_item, parent, false);
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
            holder.tv.setText(arr[position]);
            holder.idtv.setText(mes[position]);
            //holder.iv.setImageResource(ico[position]);
            //      .setText(arr[position]);
            //  ((ImageView)temp.findViewById(R.id.iconimage)).setImageResource(ico[position]);

        }


        // Return the size of your dataset (invoked by the layout manager)
        @Override
        public int getItemCount() {
            return arr.length;
        }
    }
}
