package chap.hanuor.com.chatapp;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.util.Log;
import android.util.Pair;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.parse.GetDataCallback;
import com.parse.ParseException;
import com.parse.ParseFile;
import com.parse.ParseUser;
import com.sinch.android.rtc.messaging.WritableMessage;


import java.io.ByteArrayOutputStream;
import java.util.ArrayList;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class MessageAdapter extends BaseAdapter {

    public static final int DIRECTION_INCOMING = 0;
    public static final int DIRECTION_OUTGOING = 1;

    private List<Pair<WritableMessage, Integer>> messages;
    private List<Pair<String , Integer>> mausaji;

    private LayoutInflater layoutInflater;
    ParseUser currentUser;
    CircleImageView po;
    String PreviewMessage;
    ParseFile mge;
    String ReceivedId;
    String passedAmigoId;
    Activity av;
    public MessageAdapter(Activity activity) {
        layoutInflater = activity.getLayoutInflater();
        av = activity;

        messages = new ArrayList<Pair<WritableMessage, Integer>>();
        mausaji = new ArrayList<Pair<String, Integer>>();
    }
//public void addMessage(WritableMessage message, int direction,String AmigoChatId,String ReceiveId)
    public void addMessage(WritableMessage message, int direction) {


        //passedAmigoId = ReceiveId;
        //ReceivedId = AmigoChatId;
       // passedAmigoId = ReceiveId;
        //ReceivedId = AmigoChatId;
       // messageReloader.stop();
        //messageReloader.setVisibility(View.GONE);
        messages.add(new Pair(message, direction));
       notifyDataSetChanged();
    }



    @Override
    public int getCount() {
        return mausaji.size();
    }

    @Override
    public Object getItem(int i) {
        return mausaji.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public int getViewTypeCount() {
        return 2;
    }

    @Override
    public int getItemViewType(int i) {
        return mausaji.get(i).second;
    }
    private void displayImage(ParseFile thumbnail, final CircleImageView myi) {
        if (thumbnail != null) {

            thumbnail.getDataInBackground(new GetDataCallback() {

                @Override
                public void done(byte[] data, ParseException e) {

                    if (e == null) {
                        Bitmap bitmap = BitmapFactory.decodeByteArray(data, 0, data.length);
                        Bitmap bs = BitmapFactory.decodeByteArray(data, 0, data.length);
                        ByteArrayOutputStream blob = new ByteArrayOutputStream();
                        bitmap.compress(Bitmap.CompressFormat.PNG, 0 /*ignored for PNG*/, blob);



                        BitmapDrawable ob = new BitmapDrawable(av.getResources(), bitmap);
                 if (bitmap != null) {

                            Log.e("parse file ok", " null");

                            myi.setImageDrawable(ob);
                            //myi.setBackgroundDrawable(ob);

                        }
                    } else {

                    }

                }
            });
        } else {

            Log.e("parse file", " null");

        }

    }


    @Override
    public View getView(int i, View convertView, ViewGroup viewGroup) {
        int direction = getItemViewType(i);

        //show message on left or right, depending on if
        //it's incoming or outgoing
        

        if (convertView == null) {
            int res = 0;
            if (direction == DIRECTION_INCOMING) {
                res = R.layout.message_right;
              //  messageReloader = (RotateLoading) convertView.findViewById(R.id.messageLoading);
            } else if (direction == DIRECTION_OUTGOING) {
                res = R.layout.message_left;
                //messageReloader = (RotateLoading) convertView.findViewById(R.id.messageLoading);
            }
            convertView = layoutInflater.inflate(res, viewGroup, false);
        }



       //
        PreviewMessage = mausaji.get(i).first;

       // WritableMessage message = messages.get(i).first;
        //

        TextView txtMessage = (TextView) convertView.findViewById(R.id.txtMessage);
       /* po = (CircleImageView) convertView.findViewById(R.id.profile);
        currentUser = ParseUser.getCurrentUser();
        mge = (ParseFile) currentUser.getParseFile("photo");
        displayImage(mge, po);

        po.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent ask = new Intent(av, Voting_Profile.class);
                ask.putExtra("AmigoChatIdHanuor", ReceivedId);
                ask.putExtra("RecepId", passedAmigoId);
                av.startActivity(ask);
                Toast.makeText(av, "Profile Clicked!", Toast.LENGTH_SHORT).show();
            }
        });*/
       // Toast.makeText(convertView.getContext(), " Preview " + PreviewMessage, Toast.LENGTH_SHORT).show();
        txtMessage.setText(PreviewMessage);
       // txtMessage.setText(message.getTextBody());

        return convertView;
    }

}