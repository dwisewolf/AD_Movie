package com.machinetest.ad_movie.activity;

import android.app.AlertDialog;
import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.bottomsheet.BottomSheetBehavior;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.machinetest.ad_movie.R;
import com.machinetest.ad_movie.adapter.AdventureAdapter;
import com.machinetest.ad_movie.adapter.AnimAdapter;
import com.machinetest.ad_movie.adapter.ComdeyAdapter;
import com.machinetest.ad_movie.adapter.CrimeAdapter;
import com.machinetest.ad_movie.adapter.DocumAdapter;
import com.machinetest.ad_movie.adapter.DramaAdapter;
import com.machinetest.ad_movie.adapter.FamilyAdapter;
import com.machinetest.ad_movie.adapter.ListAdapter;
import com.machinetest.ad_movie.api.RetrofitClientInstance;
import com.machinetest.ad_movie.helper.AppConstants;
import com.machinetest.ad_movie.model.MovieModel;
import com.shasin.notificationbanner.Banner;
import com.squareup.picasso.Picasso;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class ViewAllActivity extends AppCompatActivity {

    RecyclerView viewallFilms;
    AlertDialog.Builder builder ;
    AlertDialog dialog;
    ImageView thumb;
    ListAdapter listAdapter;
    AdventureAdapter adventureAdapter;
    AnimAdapter animAdapter;
    ComdeyAdapter comdeyAdapter;
    CrimeAdapter crimeAdapter;
    DramaAdapter dramaAdapter;
    DocumAdapter documAdapter;
    FamilyAdapter familyAdapter;
    private LinearLayout bottom_sheet;
    private BottomSheetBehavior sheetBehavior;
    private TextView heading;
    private TextView play;
    private TextView release;
    private TextView details;


    ArrayList<MovieModel> movieList;
    MovieModel movieModel;
    int date_flag=0,pop_flag=0,rate_flag=0;
    Intent intent;
    ConstraintLayout constraintLayout;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_all);
        init();
        builBuilder();

          intent=getIntent();
        get_viewAll(intent.getStringExtra("value"));


        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle(intent.getStringExtra("name"));

        // Set an OnMenuItemClickListener to handle menu item clicks
        toolbar.setOnMenuItemClickListener(new Toolbar.OnMenuItemClickListener(){
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                // Handle the menu item
               if (item.toString().equals(getString(R.string.date))){
                   sortdate();
               }
               else if(item.toString().equals(getString(R.string.rate))) {

                   sortate();
               }
               else {
                   sortpop();
               }

                return true;
            }
        });

        // Inflate a menu to be displayed in the toolbar
        toolbar.inflateMenu(R.menu.menu);



    }

    private void sortdate() {

        try {
            Collections.sort(movieList, new Comparator<MovieModel>() {
                DateFormat f = new SimpleDateFormat("yyyy-MM-dd");
                @Override
                public int compare(MovieModel lhs, MovieModel rhs) {
                    try {
                        if (date_flag==0){
                            date_flag=1;
                            Banner.make(constraintLayout, ViewAllActivity.this,Banner.INFO,"sorted date new",Banner.TOP,2000).show();

                            return f.parse(lhs.getRelease_date()).compareTo(f.parse(rhs.getRelease_date()));}
                        else {
                            date_flag=0;
                            Banner.make(constraintLayout, ViewAllActivity.this,Banner.INFO,"sorted date old",Banner.TOP,2000).show();

                            return f.parse(rhs.getRelease_date()).compareTo(f.parse(lhs.getRelease_date()));}

                    } catch (ParseException e) {
                        throw new IllegalArgumentException(e);
                    }
                }
            });

        } catch (Exception e) {
            e.printStackTrace();
        }
        ArrayList<String> action_image_list=new ArrayList<>();
        ArrayList<String> action_name_list= new ArrayList<>();
        ArrayList<String> data_details= new ArrayList<>();
        ArrayList<String> data_release= new ArrayList<>();
        ArrayList<String> data_heading= new ArrayList<>();
        ArrayList<String> data_poster= new ArrayList<>();

        for (int i = 0; i < movieList.size(); i++) {
            action_image_list.add(movieList.get(i).getPoster_path());
            action_name_list.add(movieList.get(i).getId());
            data_details.add(movieList.get(i).getOverview());
            data_release.add(movieList.get(i).getRelease_date());
            data_heading.add(movieList.get(i).getOriginal_title());
            data_poster.add(movieList.get(i).getBackdrop_path());
        }
        switch(intent.getStringExtra("value")) {
            case "28":
                listAdapter = new ListAdapter(ViewAllActivity.this, action_image_list, action_name_list, new ListAdapter.ClickInterface() {
                    @Override
                    public void ViewOnClick(View v, int position) {
                        sheetBehavior.setState(BottomSheetBehavior.STATE_EXPANDED);
                        heading.setText(data_heading.get(position));
                        release.setText(data_release.get(position));
                        details.setText(data_details.get(position));

                        Picasso.get().load(data_poster.get(position))

                            .into(thumb);

                    }
                });
                viewallFilms.setAdapter(listAdapter);
                break;
            case "27":
                adventureAdapter = new AdventureAdapter(ViewAllActivity.this, action_image_list, action_name_list, new AdventureAdapter.ClickInterface() {
                    @Override
                    public void ViewOnClick(View v, int position) {
                        sheetBehavior.setState(BottomSheetBehavior.STATE_EXPANDED);
                        heading.setText(data_heading.get(position));
                        release.setText(data_release.get(position));
                        details.setText(data_details.get(position));

                        Picasso.get().load(data_poster.get(position))

                            .into(thumb);

                    }
                });
                viewallFilms.setAdapter(listAdapter);
                break;

            case "10752":
                animAdapter = new AnimAdapter(ViewAllActivity.this, action_image_list, action_name_list, new AnimAdapter.ClickInterface() {
                    @Override
                    public void ViewOnClick(View v, int position) {
                        sheetBehavior.setState(BottomSheetBehavior.STATE_EXPANDED);
                        heading.setText(data_heading.get(position));
                        release.setText(data_release.get(position));
                        details.setText(data_details.get(position));

                        Picasso.get().load(data_poster.get(position))

                            .into(thumb);

                    }
                });
                viewallFilms.setAdapter(listAdapter);
                break;

            case "35":
                comdeyAdapter = new ComdeyAdapter(ViewAllActivity.this, action_image_list, action_name_list, new ComdeyAdapter.ClickInterface() {
                    @Override
                    public void ViewOnClick(View v, int position) {
                        sheetBehavior.setState(BottomSheetBehavior.STATE_EXPANDED);
                        heading.setText(data_heading.get(position));
                        release.setText(data_release.get(position));
                        details.setText(data_details.get(position));

                        Picasso.get().load(data_poster.get(position))

                            .into(thumb);

                    }
                });
                viewallFilms.setAdapter(listAdapter);
                break;

            case "80":
                crimeAdapter = new CrimeAdapter(ViewAllActivity.this, action_image_list, action_name_list, new CrimeAdapter.ClickInterface() {
                    @Override
                    public void ViewOnClick(View v, int position) {
                        sheetBehavior.setState(BottomSheetBehavior.STATE_EXPANDED);
                        heading.setText(data_heading.get(position));
                        release.setText(data_release.get(position));
                        details.setText(data_details.get(position));

                        Picasso.get().load(data_poster.get(position))

                            .into(thumb);

                    }
                });
                viewallFilms.setAdapter(listAdapter);
                break;

            case "99":
                dramaAdapter = new DramaAdapter(ViewAllActivity.this, action_image_list, action_name_list, new DramaAdapter.ClickInterface() {
                    @Override
                    public void ViewOnClick(View v, int position) {
                        sheetBehavior.setState(BottomSheetBehavior.STATE_EXPANDED);
                        heading.setText(data_heading.get(position));
                        release.setText(data_release.get(position));
                        details.setText(data_details.get(position));

                        Picasso.get().load(data_poster.get(position))

                            .into(thumb);

                    }
                });
                viewallFilms.setAdapter(listAdapter);
                break;

            case "53":
                documAdapter = new DocumAdapter(ViewAllActivity.this, action_image_list, action_name_list, new DocumAdapter.ClickInterface() {
                    @Override
                    public void ViewOnClick(View v, int position) {
                        sheetBehavior.setState(BottomSheetBehavior.STATE_EXPANDED);
                        heading.setText(data_heading.get(position));
                        release.setText(data_release.get(position));
                        details.setText(data_details.get(position));

                        Picasso.get().load(data_poster.get(position))

                            .into(thumb);

                    }
                });
                viewallFilms.setAdapter(listAdapter);
                break;

            case "37":
                familyAdapter = new FamilyAdapter(ViewAllActivity.this, action_image_list, action_name_list, new FamilyAdapter.ClickInterface() {
                    @Override
                    public void ViewOnClick(View v, int position) {
                        sheetBehavior.setState(BottomSheetBehavior.STATE_EXPANDED);
                        heading.setText(data_heading.get(position));
                        release.setText(data_release.get(position));
                        details.setText(data_details.get(position));

                        Picasso.get().load(data_poster.get(position))

                            .into(thumb);

                    }
                });
                viewallFilms.setAdapter(listAdapter);
                break;
        }



    }

    private void sortate() {
        {

            try {
                Collections.sort(movieList, new Comparator<MovieModel>() {

                    @Override
                    public int compare(MovieModel lhs, MovieModel rhs) {
                        try {
                            if (rate_flag==0){
                                rate_flag=1;
                                Banner.make(constraintLayout, ViewAllActivity.this,Banner.INFO,"sorted Rate high",Banner.TOP,2000).show();

                                return Double.valueOf(lhs.getVote_average()).compareTo(Double.valueOf(rhs.getVote_average()));}
                            else {
                                Banner.make(constraintLayout, ViewAllActivity.this,Banner.INFO,"sorted Rate low",Banner.TOP,2000).show();

                                rate_flag=0;
                                return Double.valueOf(rhs.getVote_average()).compareTo(Double.valueOf(lhs.getVote_average()));}

                        } catch (Exception e) {
                            throw new IllegalArgumentException(e);
                        }
                    }
                });

            } catch (Exception e) {
                e.printStackTrace();
            }
            ArrayList<String> action_image_list=new ArrayList<>();
            ArrayList<String> action_name_list= new ArrayList<>();
            ArrayList<String> data_details= new ArrayList<>();
            ArrayList<String> data_release= new ArrayList<>();
            ArrayList<String> data_heading= new ArrayList<>();
            ArrayList<String> data_poster= new ArrayList<>();

            for (int i = 0; i < movieList.size(); i++) {
                action_image_list.add(movieList.get(i).getPoster_path());
                action_name_list.add(movieList.get(i).getId());
                data_details.add(movieList.get(i).getOverview());
                data_release.add(movieList.get(i).getRelease_date());
                data_heading.add(movieList.get(i).getOriginal_title());
                data_poster.add(movieList.get(i).getBackdrop_path());
            }
            switch(intent.getStringExtra("value")) {
                case "28":
                    listAdapter = new ListAdapter(ViewAllActivity.this, action_image_list, action_name_list, new ListAdapter.ClickInterface() {
                        @Override
                        public void ViewOnClick(View v, int position) {
                            sheetBehavior.setState(BottomSheetBehavior.STATE_EXPANDED);
                            heading.setText(data_heading.get(position));
                            release.setText(data_release.get(position));
                            details.setText(data_details.get(position));

                            Picasso.get().load(data_poster.get(position))

                                .into(thumb);

                        }
                    });
                    viewallFilms.setAdapter(listAdapter);
                    break;
                case "27":
                    adventureAdapter = new AdventureAdapter(ViewAllActivity.this, action_image_list, action_name_list, new AdventureAdapter.ClickInterface() {
                        @Override
                        public void ViewOnClick(View v, int position) {
                            sheetBehavior.setState(BottomSheetBehavior.STATE_EXPANDED);
                            heading.setText(data_heading.get(position));
                            release.setText(data_release.get(position));
                            details.setText(data_details.get(position));

                            Picasso.get().load(data_poster.get(position))

                                .into(thumb);

                        }
                    });
                    viewallFilms.setAdapter(listAdapter);
                    break;

                case "10752":
                    animAdapter = new AnimAdapter(ViewAllActivity.this, action_image_list, action_name_list, new AnimAdapter.ClickInterface() {
                        @Override
                        public void ViewOnClick(View v, int position) {
                            sheetBehavior.setState(BottomSheetBehavior.STATE_EXPANDED);
                            heading.setText(data_heading.get(position));
                            release.setText(data_release.get(position));
                            details.setText(data_details.get(position));

                            Picasso.get().load(data_poster.get(position))

                                .into(thumb);

                        }
                    });
                    viewallFilms.setAdapter(listAdapter);
                    break;

                case "35":
                    comdeyAdapter = new ComdeyAdapter(ViewAllActivity.this, action_image_list, action_name_list, new ComdeyAdapter.ClickInterface() {
                        @Override
                        public void ViewOnClick(View v, int position) {
                            sheetBehavior.setState(BottomSheetBehavior.STATE_EXPANDED);
                            heading.setText(data_heading.get(position));
                            release.setText(data_release.get(position));
                            details.setText(data_details.get(position));

                            Picasso.get().load(data_poster.get(position))

                                .into(thumb);

                        }
                    });
                    viewallFilms.setAdapter(listAdapter);
                    break;

                case "80":
                    crimeAdapter = new CrimeAdapter(ViewAllActivity.this, action_image_list, action_name_list, new CrimeAdapter.ClickInterface() {
                        @Override
                        public void ViewOnClick(View v, int position) {
                            sheetBehavior.setState(BottomSheetBehavior.STATE_EXPANDED);
                            heading.setText(data_heading.get(position));
                            release.setText(data_release.get(position));
                            details.setText(data_details.get(position));

                            Picasso.get().load(data_poster.get(position))

                                .into(thumb);

                        }
                    });
                    viewallFilms.setAdapter(listAdapter);
                    break;

                case "99":
                    dramaAdapter = new DramaAdapter(ViewAllActivity.this, action_image_list, action_name_list, new DramaAdapter.ClickInterface() {
                        @Override
                        public void ViewOnClick(View v, int position) {
                            sheetBehavior.setState(BottomSheetBehavior.STATE_EXPANDED);
                            heading.setText(data_heading.get(position));
                            release.setText(data_release.get(position));
                            details.setText(data_details.get(position));

                            Picasso.get().load(data_poster.get(position))

                                .into(thumb);

                        }
                    });
                    viewallFilms.setAdapter(listAdapter);
                    break;

                case "53":
                    documAdapter = new DocumAdapter(ViewAllActivity.this, action_image_list, action_name_list, new DocumAdapter.ClickInterface() {
                        @Override
                        public void ViewOnClick(View v, int position) {
                            sheetBehavior.setState(BottomSheetBehavior.STATE_EXPANDED);
                            heading.setText(data_heading.get(position));
                            release.setText(data_release.get(position));
                            details.setText(data_details.get(position));

                            Picasso.get().load(data_poster.get(position))

                                .into(thumb);

                        }
                    });
                    viewallFilms.setAdapter(listAdapter);
                    break;

                case "37":
                    familyAdapter = new FamilyAdapter(ViewAllActivity.this, action_image_list, action_name_list, new FamilyAdapter.ClickInterface() {
                        @Override
                        public void ViewOnClick(View v, int position) {
                            sheetBehavior.setState(BottomSheetBehavior.STATE_EXPANDED);
                            heading.setText(data_heading.get(position));
                            release.setText(data_release.get(position));
                            details.setText(data_details.get(position));

                            Picasso.get().load(data_poster.get(position))

                                .into(thumb);

                        }
                    });
                    viewallFilms.setAdapter(listAdapter);
                    break;
            }



        }

    }

    private void sortpop() {
        {

            try {
                Collections.sort(movieList, new Comparator<MovieModel>() {

                    @Override
                    public int compare(MovieModel lhs, MovieModel rhs) {
                        try {
                            if (pop_flag==0){
                                pop_flag=1;
                                Banner.make(constraintLayout, ViewAllActivity.this,Banner.INFO,"sorted Populatity high",Banner.TOP,2000).show();

                                return Double.valueOf(lhs.getPopularity()).compareTo(Double.valueOf(rhs.getPopularity()));}
                            else {
                                pop_flag=0;
                                Banner.make(constraintLayout, ViewAllActivity.this,Banner.INFO,"sorted Populatity low",Banner.TOP,2000).show();

                                return Double.valueOf(rhs.getPopularity()).compareTo(Double.valueOf(lhs.getPopularity()));}

                        } catch (Exception e) {
                            throw new IllegalArgumentException(e);
                        }
                    }
                });

            } catch (Exception e) {
                e.printStackTrace();
            }
            ArrayList<String> action_image_list=new ArrayList<>();
            ArrayList<String> action_name_list= new ArrayList<>();
            ArrayList<String> data_details= new ArrayList<>();
            ArrayList<String> data_release= new ArrayList<>();
            ArrayList<String> data_heading= new ArrayList<>();
            ArrayList<String> data_poster= new ArrayList<>();


            for (int i = 0; i < movieList.size(); i++) {
                action_image_list.add(movieList.get(i).getPoster_path());
                action_name_list.add(movieList.get(i).getId());
                data_details.add(movieList.get(i).getOverview());
                data_release.add(movieList.get(i).getRelease_date());
                data_heading.add(movieList.get(i).getOriginal_title());
                data_poster.add(movieList.get(i).getBackdrop_path());
            }
            switch(intent.getStringExtra("value")) {
                case "28":
                    listAdapter = new ListAdapter(ViewAllActivity.this, action_image_list, action_name_list, new ListAdapter.ClickInterface() {
                        @Override
                        public void ViewOnClick(View v, int position) {
                            sheetBehavior.setState(BottomSheetBehavior.STATE_EXPANDED);
                            heading.setText(data_heading.get(position));
                            release.setText(data_release.get(position));
                            details.setText(data_details.get(position));

                            Picasso.get().load(data_poster.get(position))

                                .into(thumb);

                        }
                    });
                    viewallFilms.setAdapter(listAdapter);
                    break;
                case "27":
                    adventureAdapter = new AdventureAdapter(ViewAllActivity.this, action_image_list, action_name_list, new AdventureAdapter.ClickInterface() {
                        @Override
                        public void ViewOnClick(View v, int position) {
                            sheetBehavior.setState(BottomSheetBehavior.STATE_EXPANDED);
                            heading.setText(data_heading.get(position));
                            release.setText(data_release.get(position));
                            details.setText(data_details.get(position));

                            Picasso.get().load(data_poster.get(position))

                                .into(thumb);

                        }
                    });
                    viewallFilms.setAdapter(listAdapter);
                    break;

                case "10752":
                    animAdapter = new AnimAdapter(ViewAllActivity.this, action_image_list, action_name_list, new AnimAdapter.ClickInterface() {
                        @Override
                        public void ViewOnClick(View v, int position) {
                            sheetBehavior.setState(BottomSheetBehavior.STATE_EXPANDED);
                            heading.setText(data_heading.get(position));
                            release.setText(data_release.get(position));
                            details.setText(data_details.get(position));

                            Picasso.get().load(data_poster.get(position))

                                .into(thumb);

                        }
                    });
                    viewallFilms.setAdapter(listAdapter);
                    break;

                case "35":
                    comdeyAdapter = new ComdeyAdapter(ViewAllActivity.this, action_image_list, action_name_list, new ComdeyAdapter.ClickInterface() {
                        @Override
                        public void ViewOnClick(View v, int position) {
                            sheetBehavior.setState(BottomSheetBehavior.STATE_EXPANDED);
                            heading.setText(data_heading.get(position));
                            release.setText(data_release.get(position));
                            details.setText(data_details.get(position));

                            Picasso.get().load(data_poster.get(position))

                                .into(thumb);

                        }
                    });
                    viewallFilms.setAdapter(listAdapter);
                    break;

                case "80":
                    crimeAdapter = new CrimeAdapter(ViewAllActivity.this, action_image_list, action_name_list, new CrimeAdapter.ClickInterface() {
                        @Override
                        public void ViewOnClick(View v, int position) {
                            sheetBehavior.setState(BottomSheetBehavior.STATE_EXPANDED);
                            heading.setText(data_heading.get(position));
                            release.setText(data_release.get(position));
                            details.setText(data_details.get(position));

                            Picasso.get().load(data_poster.get(position))

                                .into(thumb);

                        }
                    });
                    viewallFilms.setAdapter(listAdapter);
                    break;

                case "99":
                    dramaAdapter = new DramaAdapter(ViewAllActivity.this, action_image_list, action_name_list, new DramaAdapter.ClickInterface() {
                        @Override
                        public void ViewOnClick(View v, int position) {
                            sheetBehavior.setState(BottomSheetBehavior.STATE_EXPANDED);
                            heading.setText(data_heading.get(position));
                            release.setText(data_release.get(position));
                            details.setText(data_details.get(position));

                            Picasso.get().load(data_poster.get(position))

                                .into(thumb);

                        }
                    });
                    viewallFilms.setAdapter(listAdapter);
                    break;

                case "53":
                    documAdapter = new DocumAdapter(ViewAllActivity.this, action_image_list, action_name_list, new DocumAdapter.ClickInterface() {
                        @Override
                        public void ViewOnClick(View v, int position) {
                            sheetBehavior.setState(BottomSheetBehavior.STATE_EXPANDED);
                            heading.setText(data_heading.get(position));
                            release.setText(data_release.get(position));
                            details.setText(data_details.get(position));

                            Picasso.get().load(data_poster.get(position))

                                .into(thumb);

                        }
                    });
                    viewallFilms.setAdapter(listAdapter);
                    break;

                case "37":
                    familyAdapter = new FamilyAdapter(ViewAllActivity.this, action_image_list, action_name_list, new FamilyAdapter.ClickInterface() {
                        @Override
                        public void ViewOnClick(View v, int position) {
                            sheetBehavior.setState(BottomSheetBehavior.STATE_EXPANDED);
                            heading.setText(data_heading.get(position));
                            release.setText(data_release.get(position));
                            details.setText(data_details.get(position));

                            Picasso.get().load(data_poster.get(position))

                                .into(thumb);

                        }
                    });
                    viewallFilms.setAdapter(listAdapter);
                    break;
            }



        }

    }

    void  init(){
        viewallFilms = findViewById(R.id.viewallFilms);
        constraintLayout = findViewById(R.id.content);
        bottom_sheet = findViewById(R.id.bottom_sheet_);
        sheetBehavior = BottomSheetBehavior.from(bottom_sheet);
        sheetBehavior.setState(BottomSheetBehavior.STATE_HIDDEN);
        heading=findViewById(R.id.heading);
        play=findViewById(R.id.play);
        release=findViewById(R.id.release);
        details=findViewById(R.id.details);
        thumb=findViewById(R.id.thumbs);

        GridLayoutManager horizontalManager =
            new GridLayoutManager(ViewAllActivity.this, 3, GridLayoutManager.VERTICAL, false);
        viewallFilms.setLayoutManager(horizontalManager);
    }

    private void get_viewAll(String value) {
        try {
dialog.show();
            final RetrofitClientInstance.GetDataService service = RetrofitClientInstance.getRetrofitInstance().create(RetrofitClientInstance.GetDataService.class);
            Call<JsonObject> call = service.get_trending("https://api.themoviedb.org/3/list/"+value+"?api_key=6f0fd849f8d8034b2babde94a37fda97&language=en-US");
            call.enqueue(new Callback<JsonObject>() {
                @Override
                public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {
                    if (response != null) {
                        ArrayList<String> action_image_list=new ArrayList<>();
                        ArrayList<String> action_name_list= new ArrayList<>();

                        ArrayList<String> data_details= new ArrayList<>();
                        ArrayList<String> data_release= new ArrayList<>();
                        ArrayList<String> data_heading= new ArrayList<>();
                        ArrayList<String> data_poster= new ArrayList<>();


                        ArrayList<String> data_rate= new ArrayList<>();
                        ArrayList<String> data_pop= new ArrayList<>();
                        JsonObject object = response.body();

                        JsonArray results = object.getAsJsonArray("items");
                        movieList=new ArrayList<>();
                        List<String> list = new ArrayList<String>();





                        for (int i = 0; i < results.size(); i++) {
                            movieModel=new MovieModel();
                            JsonObject object1 = (JsonObject) results.get(i);



                            JsonElement success = object1.get("poster_path");
                            JsonElement original_id = object1.get("id");
                            JsonElement original_title = object1.get("original_title");
                            JsonElement overview = object1.get("overview");
                            JsonElement release_date = object1.get("release_date");
                            JsonElement backdrop_path = object1.get("backdrop_path");
                            JsonElement vote_average = object1.get("vote_average");
                            JsonElement vote_pop= object1.get("popularity");



                            action_image_list.add(AppConstants.IMAGE_URL45 + success.getAsString());
                            action_name_list.add(original_id.getAsString());

                            data_details.add(overview.getAsString());
                            data_release.add(release_date.getAsString());
                            data_heading.add(original_title.getAsString());
                            data_poster.add(AppConstants.IMAGE_URL45 +backdrop_path.getAsString());


                            movieModel.setPoster_path(AppConstants.IMAGE_URL45 + success.getAsString());
                            movieModel.setId(original_id.getAsString());
                            movieModel.setOriginal_title(original_title.getAsString());
                            movieModel.setOverview(overview.getAsString());
                            movieModel.setRelease_date(release_date.getAsString());
                            movieModel.setBackdrop_path(AppConstants.IMAGE_URL45 +backdrop_path.getAsString());
                            movieModel.setVote_average(vote_average.getAsString());
                            movieModel.setPopularity(vote_pop.getAsString());

                            movieList.add(movieModel);


                        }

                        switch(value) {
                            case "28":
                                listAdapter = new ListAdapter(ViewAllActivity.this, action_image_list, action_name_list, new ListAdapter.ClickInterface() {
                                    @Override
                                    public void ViewOnClick(View v, int position) {
                                        sheetBehavior.setState(BottomSheetBehavior.STATE_EXPANDED);
                                        heading.setText(data_heading.get(position));
                                        release.setText(data_release.get(position));
                                        details.setText(data_details.get(position));

                                        Picasso.get().load(data_poster.get(position))

                                            .into(thumb);

                                    }
                                });
                                viewallFilms.setAdapter(listAdapter);
                                break;
                            case "27":
                                adventureAdapter = new AdventureAdapter(ViewAllActivity.this, action_image_list, action_name_list, new AdventureAdapter.ClickInterface() {
                                    @Override
                                    public void ViewOnClick(View v, int position) {
                                        sheetBehavior.setState(BottomSheetBehavior.STATE_EXPANDED);
                                        heading.setText(data_heading.get(position));
                                        release.setText(data_release.get(position));
                                        details.setText(data_details.get(position));

                                        Picasso.get().load(data_poster.get(position))

                                            .into(thumb);

                                    }
                                });
                                viewallFilms.setAdapter(listAdapter);
                                break;

                            case "10752":
                                animAdapter = new AnimAdapter(ViewAllActivity.this, action_image_list, action_name_list, new AnimAdapter.ClickInterface() {
                                    @Override
                                    public void ViewOnClick(View v, int position) {
                                        sheetBehavior.setState(BottomSheetBehavior.STATE_EXPANDED);
                                        heading.setText(data_heading.get(position));
                                        release.setText(data_release.get(position));
                                        details.setText(data_details.get(position));

                                        Picasso.get().load(data_poster.get(position))

                                            .into(thumb);

                                    }
                                });
                                viewallFilms.setAdapter(listAdapter);
                                break;

                            case "35":
                                comdeyAdapter = new ComdeyAdapter(ViewAllActivity.this, action_image_list, action_name_list, new ComdeyAdapter.ClickInterface() {
                                    @Override
                                    public void ViewOnClick(View v, int position) {
                                        sheetBehavior.setState(BottomSheetBehavior.STATE_EXPANDED);
                                        heading.setText(data_heading.get(position));
                                        release.setText(data_release.get(position));
                                        details.setText(data_details.get(position));

                                        Picasso.get().load(data_poster.get(position))

                                            .into(thumb);

                                    }
                                });
                                viewallFilms.setAdapter(listAdapter);
                                break;

                            case "80":
                                crimeAdapter = new CrimeAdapter(ViewAllActivity.this, action_image_list, action_name_list, new CrimeAdapter.ClickInterface() {
                                    @Override
                                    public void ViewOnClick(View v, int position) {
                                        sheetBehavior.setState(BottomSheetBehavior.STATE_EXPANDED);
                                        heading.setText(data_heading.get(position));
                                        release.setText(data_release.get(position));
                                        details.setText(data_details.get(position));

                                        Picasso.get().load(data_poster.get(position))

                                            .into(thumb);

                                    }
                                });
                                viewallFilms.setAdapter(listAdapter);
                                break;

                            case "99":
                                dramaAdapter = new DramaAdapter(ViewAllActivity.this, action_image_list, action_name_list, new DramaAdapter.ClickInterface() {
                                    @Override
                                    public void ViewOnClick(View v, int position) {
                                        sheetBehavior.setState(BottomSheetBehavior.STATE_EXPANDED);
                                        heading.setText(data_heading.get(position));
                                        release.setText(data_release.get(position));
                                        details.setText(data_details.get(position));

                                        Picasso.get().load(data_poster.get(position))

                                            .into(thumb);

                                    }
                                });
                                viewallFilms.setAdapter(listAdapter);
                                break;

                            case "53":
                                documAdapter = new DocumAdapter(ViewAllActivity.this, action_image_list, action_name_list, new DocumAdapter.ClickInterface() {
                                    @Override
                                    public void ViewOnClick(View v, int position) {
                                        sheetBehavior.setState(BottomSheetBehavior.STATE_EXPANDED);
                                        heading.setText(data_heading.get(position));
                                        release.setText(data_release.get(position));
                                        details.setText(data_details.get(position));

                                        Picasso.get().load(data_poster.get(position))

                                            .into(thumb);

                                    }
                                });
                                viewallFilms.setAdapter(listAdapter);
                                break;

                            case "37":
                                familyAdapter = new FamilyAdapter(ViewAllActivity.this, action_image_list, action_name_list, new FamilyAdapter.ClickInterface() {
                                    @Override
                                    public void ViewOnClick(View v, int position) {
                                        sheetBehavior.setState(BottomSheetBehavior.STATE_EXPANDED);
                                        heading.setText(data_heading.get(position));
                                        release.setText(data_release.get(position));
                                        details.setText(data_details.get(position));

                                        Picasso.get().load(data_poster.get(position))

                                            .into(thumb);

                                    }
                                });
                                viewallFilms.setAdapter(listAdapter);
                                break;
                        }

                        dialog.cancel();


                        

                        
                    }
                }

                @Override
                public void onFailure(Call<JsonObject> call, Throwable t) {
                    String a = "";
                    //dialog.cancel();
                }
            });

            // Response<List<ClassVideo>> response=   call.execute();

        } catch (Exception e) {
            String a = "";
            //dialog.cancel();
        }
    }

    private void builBuilder() {
        builder  = new AlertDialog.Builder(ViewAllActivity.this);
        // create an alert builder
        builder.setCancelable(false);
        // set the custom layout
        final View customLayout = getLayoutInflater().inflate(R.layout.alert_loading, null);
        builder.setView(customLayout);
        // add a button
        // create and show the alert dialog
        dialog = builder.create();
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
    }


}