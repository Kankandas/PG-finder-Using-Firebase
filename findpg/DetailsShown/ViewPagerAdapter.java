package com.example.kankan.findpg.DetailsShown;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.example.kankan.findpg.R;

public class ViewPagerAdapter extends PagerAdapter {
    private Context context;
    private boolean firstTime=true;
    private LayoutInflater inflater;
    private String homeUri,roomUri;
    public ViewPagerAdapter(Context context,String homeUri,String roomUri) {
        this.context = context;
        this.homeUri=homeUri;
        this.roomUri=roomUri;

    }

    @Override
    public int getCount() {
        return 2;
    }

    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
        return view==object;
        /*
        Determines whether a page View is associated with a specific key object as returned by instantiateItem(ViewGroup, int).
         */
    }

    @NonNull
    @Override
    public Object instantiateItem(@NonNull ViewGroup container, int position) {

        inflater=(LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view=inflater.inflate(R.layout.custom_layout,null);
        ImageView imageView=(ImageView)view.findViewById(R.id.customImage);
        //imageView.setImageResource(image[position]);
        if(firstTime)
        {
            Glide.with(context).load(homeUri).into(imageView);
            firstTime=false;
        }
        else {
            Glide.with(context).load(roomUri).into(imageView);
        }


        ViewPager vp=(ViewPager)container;
        vp.addView(view,0);
        return view;
    }

    @Override
    public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
        ViewPager vp=(ViewPager) container;
        View view=(View)object;
        vp.removeView(view);
    }
}
