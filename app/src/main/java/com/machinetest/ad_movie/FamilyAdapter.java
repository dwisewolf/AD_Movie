package com.machinetest.ad_movie;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.ComponentName;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;

import java.util.List;

public class FamilyAdapter extends RecyclerView.Adapter<FamilyAdapter.ViewHolder> {
    private final Activity activity;
    private Context _context;

    private final List<String> _listData_id; // header titles
    // child data in format of header title, child title
    private final List<String> listData_image;
    private final FamilyAdapter.ClickInterface onClickListener;
    public FamilyAdapter(Activity activity, List<String> listData_image,
                         List<String> _listData_id,
                         FamilyAdapter.ClickInterface onClickListener) {
        this.activity = activity;
        this._listData_id = _listData_id;
        this.listData_image = listData_image;
        this.onClickListener= onClickListener;

    }
    public interface ClickInterface {
        void ViewOnClick(View v, int position);

    }


    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        LayoutInflater inflater = activity.getLayoutInflater();
        View view = inflater.inflate(R.layout.item_cell, viewGroup, false);

        return new ViewHolder(view);
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(FamilyAdapter.ViewHolder viewHolder, final int position) {


        Picasso.get().load(listData_image.get(position))

            .into(viewHolder.item_imageView);



    }

    @Override
    public int getItemCount() {

       String activityname= activity.getClass().getSimpleName();
       if (activityname.equals("MainActivity")) {
           return 8;
       }
       else {
           return listData_image.size();
       }
    }


    protected   class ViewHolder extends RecyclerView.ViewHolder {


        private final ImageView item_imageView;

        public ViewHolder(View view) {
            super(view);
            item_imageView =  view.findViewById(R.id.thumb);
            item_imageView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    onClickListener.ViewOnClick(v, getAdapterPosition());

                }
            });
        }
    }
}
