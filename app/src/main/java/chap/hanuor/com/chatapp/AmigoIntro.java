package chap.hanuor.com.chatapp;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;

import com.vlonjatg.android.apptourlibrary.AppTour;
import com.vlonjatg.android.apptourlibrary.MaterialSlide;

/**
 * Created by Shantanu Johri on 15-11-2015.
 */
public class AmigoIntro extends AppTour {
    @Override
    public void init(Bundle savedInstanceState) {
        int firstColor = Color.parseColor("#EEEEEE");
        int secondColor = Color.parseColor("#263238");
        int customSlideColor = Color.parseColor("#009866");
        Fragment firstSlide = MaterialSlide.newInstance(R.drawable.photo, "Welcome to Amigo",
                "Get Ready to experince a totally unique and a beautiful social networking experience.", Color.BLACK, Color.BLACK);

        Fragment secondSlide = MaterialSlide.newInstance(R.drawable.amigos5, "Face the Unknown",
                "Chat with a random person. Make friends, vote them, and finally #knowwhoyouare.Ready to explore yourself? Let's begin!", Color.WHITE, Color.WHITE);

        //Add slides
        addSlide(firstSlide, firstColor);
        addSlide(secondSlide, secondColor);

        //Custom slide
        //addSlide(new CustomSlide(), customSlideColor);

        //Customize tour
        setSkipButtonTextColor(Color.WHITE);
        setNextButtonColorToWhite();
        setDoneButtonTextColor(Color.WHITE);
    }

    @Override
    public void onSkipPressed() {

    }

    @Override
    public void onDonePressed() {
        Intent xc = new Intent();
        xc.setClass(this,LoginActivity.class);
        startActivity(xc);
    }
}
