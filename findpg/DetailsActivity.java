package com.example.kankan.findpg;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v4.content.ContextCompat;
import android.support.v4.graphics.drawable.RoundedBitmapDrawable;
import android.support.v4.graphics.drawable.RoundedBitmapDrawableFactory;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.kankan.findpg.DetailsShown.CalenderDialong;
import com.example.kankan.findpg.DetailsShown.ViewPagerAdapter;

import org.w3c.dom.Text;

import java.util.Timer;
import java.util.TimerTask;

public class DetailsActivity extends AppCompatActivity implements View.OnClickListener,CalenderDialong.MyOwnCustomInterface{



    private ViewPager vPager;
    private LinearLayout sliderDots;
    private int dotsCount;
    private ImageView []dots;
    private String homeUri,roomUri,typeOfRoom,noOfBed,state,district,locality,pincode;
    private ImageView imgLocation,imgCalender;
    private TextView txtDate,txtShowACard,payalKankan;
    private CalenderDialong dialong;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);

        Bundle bundle=getIntent().getExtras();
        homeUri=bundle.getString("URIHOME");
        roomUri=bundle.getString("URIROOM");
        typeOfRoom=bundle.getString("TYPE_ROOM");
        noOfBed=bundle.getString("NO_OF_BED");
        state=bundle.getString("STATE_NAME");
        district=bundle.getString("DISTRICT_NAME");
        locality=bundle.getString("LOCALITY_NAME");
        pincode=bundle.getString("PINCODE");

        vPager=(ViewPager)findViewById(R.id.vPager);
        sliderDots=(LinearLayout)findViewById(R.id.sliderDots);

        ViewPagerAdapter viewPagerAdapter=new ViewPagerAdapter(DetailsActivity.this,homeUri,roomUri);

        vPager.setAdapter(viewPagerAdapter);

        dotsCount=viewPagerAdapter.getCount();
        dots=new ImageView[dotsCount];

        for(int i=0;i<dotsCount;i++)
        {
            dots[i]=new ImageView(this);
            dots[i].setImageDrawable(ContextCompat.getDrawable(getApplicationContext(),R.drawable.non_active_dot));
            LinearLayout.LayoutParams params=new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
            params.setMargins(8,0,8,0);
            sliderDots.addView(dots[i],params);

        }
        dots[0].setImageDrawable(ContextCompat.getDrawable(getApplicationContext(),R.drawable.active_dot));

        vPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                    for(int i=0;i<dotsCount;i++)
                    {
                        dots[i].setImageDrawable(ContextCompat.getDrawable(getApplicationContext(),R.drawable.non_active_dot));
                    }
                    dots[position].setImageDrawable(ContextCompat.getDrawable(getApplicationContext(),R.drawable.active_dot));
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
        //For Location .....
        imgLocation=(ImageView)findViewById(R.id.imgDetailsLocation);

        Bitmap bm= BitmapFactory.decodeResource(getResources(),R.drawable.location);
        RoundedBitmapDrawable roundedBitmapDrawable= RoundedBitmapDrawableFactory.create(getResources(),bm);
        roundedBitmapDrawable.setCircular(true);
        imgLocation.setImageDrawable(roundedBitmapDrawable);

        //for Room Details....
        txtShowACard=(TextView)findViewById(R.id.txtShowAcard);
        int no=Integer.parseInt(noOfBed);
        if(no>1)
        {
            txtShowACard.setText(typeOfRoom +" with "+noOfBed+" Bed per Room");
        }
        else
        {
            txtShowACard.setText("Single Bed Room");
        }

        // for Date Of Book....
        txtDate=(TextView)findViewById(R.id.txtDate);
        imgCalender=(ImageView)findViewById(R.id.imgCalender);

        Bitmap bm1=BitmapFactory.decodeResource(getResources(),R.drawable.calender);
        RoundedBitmapDrawable roundedBitmapDrawable1=RoundedBitmapDrawableFactory.create(getResources(),bm1);
        roundedBitmapDrawable1.setCircular(true);
        imgCalender.setImageDrawable(roundedBitmapDrawable1);
        imgCalender.setOnClickListener(DetailsActivity.this);
        imgLocation.setOnClickListener(DetailsActivity.this);
        payalKankan=findViewById(R.id.payalKankan);
        payalKankan.setText(state+"\n"+locality+"\n"+"\n"+pincode+"\n"+district);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId())
        {
            case R.id.imgCalender:
                openDialog();

                break;
            case R.id.imgDetailsLocation:
                Intent intent=new Intent(DetailsActivity.this,LocationMapsActivity.class);
                intent.putExtra("MAPS_STATE",state);
                intent.putExtra("MAPS_DISTERICT",district);
                intent.putExtra("MAPS_LOCALITY",locality);
                intent.putExtra("MAPS_PINCODE",pincode);
                startActivity(intent);
                //state,district,locality,pincode;

                break;
        }
    }
    public void openDialog()
    {
        dialong=new CalenderDialong();
        dialong.show(getSupportFragmentManager(),"");

    }

    @Override
    public void getDate(String date) {
        txtDate.setText("Date Of Book:   "+date);
        dialong.dismiss();
    }
}
