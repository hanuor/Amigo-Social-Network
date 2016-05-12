package chap.hanuor.com.chatapp;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;

import java.util.List;

public class CommentsSection extends AppCompatActivity {
    String objectId;
    RecyclerView activeRecyclerView;
    RecyclerView.Adapter activeAdapter;
    RecyclerView.LayoutManager activeLayoutManager;
    EditText et;
    Button send;
    ImageView sd;
    String hew;
    Button tkv;
    String naam;
    String separator = "%3:11071995:11";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_comments_section);

        sd = (ImageView) findViewById(R.id.im);
        activeRecyclerView = (RecyclerView) findViewById(R.id.commentrecycler);
        //activeRecyclerView.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.HORIZONTAL_LIST));
        activeRecyclerView.setHasFixedSize(true);
        activeLayoutManager = new LinearLayoutManager(this);
        activeRecyclerView.setLayoutManager(activeLayoutManager);
        activeRecyclerView.setItemAnimator(new DefaultItemAnimator());

        sd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(CommentsSection.this, "SMSDKEJEJEASSSD", Toast.LENGTH_SHORT).show();
            }
        });
        //et = (EditText) findViewById(R.id.etcomms);
        //send = (Button) findViewById(R.id.send);
        Intent mo=getIntent();
        objectId = mo.getStringExtra("ObjectId");

        getComments(objectId);

      /*  tkv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(CommentsSection.this,"clicked",Toast.LENGTH_SHORT).show();
                Intent ac = new Intent();
                ac.setClass(CommentsSection.this,Comments_Write.class);
                ac.putExtra("Objecto",objectId);
                ac.putExtra("HEW",hew);
                ac.putExtra("NAAM",naam);
                startActivity(ac);

            }
        });
      */  /*send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(et.getText().equals("")){
                    Toast.makeText(CommentsSection.this, "Please enter a message", Toast.LENGTH_SHORT).show();

                }
                else{
                    final String er = et.getText().toString();
                    Toast.makeText(CommentsSection.this, "Comment added successfully", Toast.LENGTH_SHORT).show();
                    ParseQuery<ParseObject> qay = ParseQuery.getQuery("AmigoPosts");
                    qay.getInBackground(objectId, new GetCallback<ParseObject>() {
                        @Override
                        public void done(ParseObject parseObject, ParseException e) {
                            if(e == null){

                                StringBuilder nw = new StringBuilder();
                                StringBuilder ider = new StringBuilder();
                                if(hew!=null){


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
                                }
                                else{
                                    nw.append(er);
                                    nw.append(separator);
                                    ider.append(ParseUser.getCurrentUser().getUsername().toString());
                                    ider.append(separator);
                                    String nes = ider.toString();
                                    String mb = nw.toString();
                                    parseObject.put("CommentsString",mb);
                                    parseObject.put("CommentIds",nes);

                                   // String mb = nw.toString();
                                   // parseObject.put("CommentsString",mb);
                                    parseObject.saveInBackground();
                                }
                            }
                        }
                    });
                    et.setText("");
                }
            }
        });*/
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
                            String amal = list.get(0).get("CommentsString"  ).toString();
                            String samba = list.get(0).get("CommentIds").toString();
                            String CommentIds[] = samba.split("\\%3:11071995:11");
                            String Comments[] = amal.split("\\%3:11071995:11");

                            capture_comms(amal,samba);
                            activeAdapter = new CommentsAdapter(Comments,CommentIds);
                            activeRecyclerView.setAdapter(activeAdapter);
                            if(amal.contains("%6`~!1107*]$"));
                            {

                                if(Comments.length==0){
                                    Toast.makeText(CommentsSection.this, " No Comments Found", Toast.LENGTH_SHORT).show();
                                }

                            }
                        } catch (Exception e1) {
                            e1.printStackTrace();
                        }
                    }
                    else{
                        Toast.makeText(CommentsSection.this, " No Comments Found", Toast.LENGTH_SHORT).show();
                    }
                }

            }
        });
    }

    private void capture_comms(String amal, String naasm) {
        naam = naasm;
        hew = amal;
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
