package chap.hanuor.com.chatapp;

/**
 * Created by Shantanu Johri on 05-12-2015.
 */

import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by Shantanu Johri on 01-12-2015.
 */
public class YourProfileAdapter extends RecyclerView.Adapter<YourProfileAdapter.ViewHolder> {
    String arr[];
    int ico[];
    String so[];
    ArrayList<String> n;
    Dialog dialog;
    Context c;
    RadioButton rv;


    public YourProfileAdapter(Context c,String so[], ArrayList<String> n, int ico[]) {
        this.arr = arr;
        this.ico = ico;
        this.n = n;
        this.so = so;
        this.c = c;
    }

    // Provide a reference to the views for each data item
    // Complex data items may need more than one view per item, and
    // you provide access to all the views for a data item in a view holder
    public class ViewHolder extends RecyclerView.ViewHolder {
        ImageView tv;
        TextView sv;
        View fg;
        ImageButton but;

        Button btnDisplay;
        // public CardView activeCV;

        public ViewHolder(View CV) {
            super(CV);
            tv = (ImageView) CV.findViewById(R.id.imag);
            sv = (TextView) CV.findViewById(R.id.details);
            btnDisplay = (Button) CV.findViewById(R.id.btnDisplay);
            fg = (View) CV.findViewById(R.id.textView6);
            but = (ImageButton) CV.findViewById(R.id.imageButton);


        }
    }

    // Create new views (invoked by the layout manager)
    @Override
    public YourProfileAdapter.ViewHolder onCreateViewHolder(ViewGroup parent,
                                                            int viewType) {
        // create a new view
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.user_list_item, parent, false);
        // set the view's size, margins, paddings and layout parameters

        ViewHolder vh = new ViewHolder(v);
        return vh;
    }

    // Replace the contents of a view (invoked by the layout manager)
    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        // - get element from your dataset at this position
        // - replace the contents of the view with that element

        holder.tv.setImageResource(ico[position]);
      /*  if(n.get(position).contentEquals("gender")){
                    holder.sv.setText("Not Selected");
            holder.but.setVisibility(View.VISIBLE);
            holder.but.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    dialog = new Dialog(view.getContext(), R.style.DialogSlideAnim);
                    dialog.setContentView(R.layout.dialog_gender_select);


                    final RadioGroup radioSexGroup;
                    radioSexGroup = (RadioGroup) view.findViewById(R.id.radioSex);

                    holder.btnDisplay.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            int selectedId = radioSexGroup.getCheckedRadioButtonId();
                            RadioButton radioSexButton;
                            // find the radiobutton by returned id
                            radioSexButton = (RadioButton) view.findViewById(selectedId);
                            //holder.sv.setText();
                        callme(radioSexButton);
                            holder.sv.setText(radioSexButton.getText());
                            holder.but.setVisibility(View.GONE);
                        }
                    });
                }
            });


        }*/
        if(position == 1){
   if(n.get(position).toString().equals("[Please verify your Email]")){
    holder.sv.setTextColor(Color.parseColor("#424242"));


    }  else{
       holder.fg.setVisibility(View.GONE);
   }


        }
        holder.sv.setText(n.get(position));

        if(position == 2 || position == 3 || position == 4 || position == 5){
            holder.sv.setTextSize(TypedValue.COMPLEX_UNIT_PX,c.getResources().getDimension(R.dimen.statusdim));


        }

      //  holder.iv.setImageResource(ico[position]);
        //      .setText(arr[position]);
        //  ((ImageView)temp.findViewById(R.id.iconimage)).setImageResource(ico[position]);

    }

    private void callme(RadioButton radioSexButton) {
        rv = radioSexButton;
    }


    // Return the size of your dataset (invoked by the layout manager)
    @Override
    public int getItemCount() {
        return n.size();
    }
}
