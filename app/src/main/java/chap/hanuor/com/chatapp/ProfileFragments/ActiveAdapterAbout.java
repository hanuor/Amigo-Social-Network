package chap.hanuor.com.chatapp.ProfileFragments;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import chap.hanuor.com.chatapp.R;

/**
 * Created by Shantanu Johri on 01-12-2015.
 */
public class ActiveAdapterAbout extends RecyclerView.Adapter<ActiveAdapterAbout.ViewHolder> {
    String arr[];
    int ico[];


    public ActiveAdapterAbout(String arr[],int ico[]) {
        this.arr = arr;
        this.ico = ico;
    }

    // Provide a reference to the views for each data item
    // Complex data items may need more than one view per item, and
    // you provide access to all the views for a data item in a view holder
    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView tv;
        ImageView iv;
        // public CardView activeCV;

        public ViewHolder(View CV) {
            super(CV);
            tv = (TextView) CV.findViewById(R.id.texi);
            iv = (ImageView) CV.findViewById(R.id.ab);

        }
    }

    // Create new views (invoked by the layout manager)
    @Override
    public ActiveAdapterAbout.ViewHolder onCreateViewHolder(ViewGroup parent,
                                                       int viewType) {
        // create a new view
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_about_fragment, parent, false);
        // set the view's size, margins, paddings and layout parameters

        ViewHolder vh = new ViewHolder(v);
        return vh;
    }

    // Replace the contents of a view (invoked by the layout manager)
    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        // - get element from your dataset at this position
        // - replace the contents of the view with that element
        holder.tv.setText(arr[position]);
        holder.iv.setImageResource(ico[position]);
        //      .setText(arr[position]);
        //  ((ImageView)temp.findViewById(R.id.iconimage)).setImageResource(ico[position]);

    }


    // Return the size of your dataset (invoked by the layout manager)
    @Override
    public int getItemCount() {
        return 2;
    }
}