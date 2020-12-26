package com.machinetest.ad_movie.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.viewpager.widget.PagerAdapter;


import com.machinetest.ad_movie.R;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class BannerViewpageAdapter extends PagerAdapter {
    private LayoutInflater layoutInflater;

    ArrayList<String> image_arraylist;
    ArrayList<String> name_arraylist;
    private final Context context;



    public BannerViewpageAdapter(Context context, ArrayList<String> image_arraylist,ArrayList<String> name_arraylist) {
        this.context = context;
        this.image_arraylist = image_arraylist;
        this.name_arraylist = name_arraylist;
    }

    @Override
    public int getCount() {
        return image_arraylist.size();
    }



    @Override
    public Object instantiateItem(ViewGroup container, final int position) {

        layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = layoutInflater.inflate(R.layout.banner_viewer, null);
        ImageView imageView = view.findViewById(R.id.imageView);
        TextView head = view.findViewById(R.id.item_text);
        Picasso.get()
                .load(image_arraylist.get(position))
                .into(imageView);
        head.setText(name_arraylist.get(position));


        container.addView(view);

        return view;

    }

    @Override
    public boolean isViewFromObject(View view, Object obj) {
        return view == obj;
    }


    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        View view = (View) object;
        container.removeView(view);
    }
}
