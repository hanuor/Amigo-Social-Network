package chap.hanuor.com.chatapp;

import android.app.Activity;
import android.support.v4.util.Pair;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.sinch.android.rtc.messaging.WritableMessage;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

/**
 * Created by Shantanu Johri on 17-11-2015.
 */
public class FriendMessageAdapter extends BaseAdapter {
    Calendar c = Calendar.getInstance();
    public static final int DIRECTION_INCOMING = 0;
    public static final int DIRECTION_OUTGOING = 1;

    private List<Pair<WritableMessage, Integer>> messages;
    private LayoutInflater layoutInflater;

    public FriendMessageAdapter(Activity activity) {

        layoutInflater = activity.getLayoutInflater();
        messages = new ArrayList<Pair<WritableMessage, Integer>>();
    }

    public void addMessage(WritableMessage message, int direction) {

        messages.add(new Pair(message, direction));
        notifyDataSetChanged();
    }

    public void addSomeMessage(String message, int direction) {

        messages.add(new Pair(message, direction));
        notifyDataSetChanged();
    }

    @Override
    public int getCount() {
        return messages.size();
    }

    @Override
    public Object getItem(int i) {
        return messages.get(i);
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
        return messages.get(i).second;
    }

    @Override
    public View getView(int i, View convertView, ViewGroup viewGroup) {
        int seconds = c.get(Calendar.SECOND);
        int min = c.get(Calendar.MINUTE);
        StringBuilder  s = new StringBuilder();
        s.append(""+min);
        s.append(":");
        s.append(""+seconds);
        String v = s.toString();
        int direction = getItemViewType(i);

        //show message on left or right, depending on if
        //it's incoming or outgoing
        if (convertView == null) {
            int res = 0;
            if (direction == DIRECTION_INCOMING) {
                res = R.layout.message_right;
            } else if (direction == DIRECTION_OUTGOING) {
                res = R.layout.message_left;
            }
            convertView = layoutInflater.inflate(res, viewGroup, false);
        }
       WritableMessage message = messages.get(i).first;

        TextView txtMessage = (TextView) convertView.findViewById(R.id.txtMessage);
       // TextView txtTime = (TextView) convertView.findViewById(R.id.time);
//        txtTime.setText(v);
        txtMessage.setText(message.getTextBody());

        return convertView;
    }



}

