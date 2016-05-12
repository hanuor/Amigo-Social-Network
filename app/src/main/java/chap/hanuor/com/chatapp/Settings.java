package chap.hanuor.com.chatapp;

import android.content.Context;
import android.content.Intent;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.widget.TextView;

import com.flaviofaria.kenburnsview.KenBurnsView;
import com.flaviofaria.kenburnsview.RandomTransitionGenerator;
import com.yayandroid.parallaxrecyclerview.ParallaxRecyclerView;
import com.yayandroid.parallaxrecyclerview.ParallaxViewHolder;

public class Settings extends AppCompatActivity {
    KenBurnsView kbv;
    ParallaxRecyclerView recyclerView;
    Toolbar to;
    String arr[] = {"Help!","Version Code   -     1.1.1","Hanuor"};
    int ico[] = {R.drawable.ic_round67,R.drawable.ic_amigo_icon_large,R.drawable.ic_logogog};
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);
        to = (Toolbar) findViewById(R.id.toolbar);
        to.setTitle("Settings");
        setSupportActionBar(to);
        getSupportActionBar().setDefaultDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        final Drawable upArrow = getResources().getDrawable(R.drawable.abc_ic_ab_back_mtrl_am_alpha);
        upArrow.setColorFilter(getResources().getColor(R.color.bg_glass), PorterDuff.Mode.SRC_ATOP);
        getSupportActionBar().setHomeAsUpIndicator(upArrow);
        recyclerView = (ParallaxRecyclerView) findViewById(R.id.usersListView);

        kbv = (KenBurnsView) findViewById(R.id.imageCover);
        AccelerateDecelerateInterpolator ACCELERATE_DECELERATE = new AccelerateDecelerateInterpolator();
        RandomTransitionGenerator generator = new RandomTransitionGenerator(10000, ACCELERATE_DECELERATE);
        kbv.setTransitionGenerator(generator); //set new transition on kbv
        final LinearLayoutManager layoutManager
                = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
        recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));

        recyclerView.setAdapter(new SettingsAdapter(getApplicationContext(),arr,ico));


    }

    private class SettingsAdapter extends RecyclerView.Adapter <SettingsAdapter.ViewHolder>{

        String esk[];
        int con[];
        public SettingsAdapter(Context applicationContext,String esk[],int con[]) {
            this.esk = esk;
            this.con = con;
        }

        @Override
        public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.settings_single,parent,false);
            return new ViewHolder(v);
        }

        @Override
        public void onBindViewHolder(ViewHolder holder, int position) {

            holder.getBackgroundImage().setImageResource(con[position]);
            holder.getTextView().setText(esk[position]);

            if(position == 0) {
                holder.getBackgroundImage().setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Intent tr = new Intent();
                        tr.setClass(Settings.this,Help.class);
                        startActivity(tr);
                        overridePendingTransition(R.anim.lefttoright,R.anim.righttoleft);

                    }
                });
            }
            holder.getBackgroundImage().reuse();
        }

        @Override
        public int getItemCount() {
            return esk.length;
        }
        public  class ViewHolder extends ParallaxViewHolder {

            private final TextView textView;
            public ViewHolder(View v) {
                super(v);
            textView = (TextView) v.findViewById(R.id.setText);

            }

            @Override
            public int getParallaxImageId() {
                return R.id.backgroundImage;
            }

            public TextView getTextView() {
                return textView;
            }
         }
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem menuItem) {
        if (menuItem.getItemId() == android.R.id.home) {
            onBackPressed();
        }
        return super.onOptionsItemSelected(menuItem);
    }
}
