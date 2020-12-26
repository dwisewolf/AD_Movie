package com.machinetest.ad_movie.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.ViewPager;

import android.app.AlertDialog;
import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.android.material.bottomsheet.BottomSheetBehavior;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.machinetest.ad_movie.R;
import com.machinetest.ad_movie.adapter.AdventureAdapter;
import com.machinetest.ad_movie.adapter.AnimAdapter;
import com.machinetest.ad_movie.adapter.BannerViewpageAdapter;
import com.machinetest.ad_movie.adapter.ComdeyAdapter;
import com.machinetest.ad_movie.adapter.CrimeAdapter;
import com.machinetest.ad_movie.adapter.DocumAdapter;
import com.machinetest.ad_movie.adapter.DramaAdapter;
import com.machinetest.ad_movie.adapter.FamilyAdapter;
import com.machinetest.ad_movie.adapter.ListAdapter;
import com.machinetest.ad_movie.api.RetrofitClientInstance;
import com.machinetest.ad_movie.helper.AppConstants;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {

    RecyclerView ppular_longList;
    int page_position = 0;

    AlertDialog.Builder builder ;
    AlertDialog dialog;
    private ViewPager vp_slider;

    BannerViewpageAdapter bannerViewpageAdapter;
    ArrayList<String> slider_image_list;
    ArrayList<String> slider_name_list;

    private LinearLayout bottom_sheet;
    private BottomSheetBehavior sheetBehavior;



    private RecyclerView action_recycler;
    private RecyclerView adventure_recycler;
    private RecyclerView animation_recycler;
    private RecyclerView comdey_recycler;
    private RecyclerView crime_recycler;
    private RecyclerView documentry_recycler;
    private RecyclerView drama_recycler;
    private RecyclerView family_recycler;

    private TextView action_viewAll;
    private TextView adventure_viewAll;
    private TextView animation_viewAll;
    private TextView comdey_viewAll;
    private TextView crime_viewAll;
    private TextView documentry_viewAll;
    private TextView drama_viewAll;
    private TextView family_viewAll;
    private TextView heading;
    private TextView play;
    private TextView release;
    private TextView details;

    ImageView thumb;
    ListAdapter listAdapter;
    AdventureAdapter adventureAdapter;
    AnimAdapter animAdapter;
    ComdeyAdapter comdeyAdapter;
    CrimeAdapter crimeAdapter;
    DramaAdapter dramaAdapter;
    DocumAdapter documAdapter;
    FamilyAdapter familyAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        init();

        final Handler handler = new Handler();

        final Runnable update = new Runnable() {
            public void run() {
                if (page_position == slider_image_list.size()) {
                    page_position = 0;
                } else {
                    page_position = page_position + 1;
                }

                vp_slider.setCurrentItem(page_position, true);
            }
        };

        new Timer().schedule(new TimerTask() {

            @Override
            public void run() {
                handler.post(update);
            }
        }, 100, 5000);

        builBuilder();

        get_Banner();

        play.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                sheetBehavior.setState(BottomSheetBehavior.STATE_HIDDEN);
            }
        });

        Intent intent=new Intent(MainActivity.this, ViewAllActivity.class);

        action_viewAll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                intent.putExtra("name","Action");
                intent.putExtra("value","28");
                startActivity(intent);


            }
        });

        adventure_viewAll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                intent.putExtra("name","Adventure");
                intent.putExtra("value","27");
                startActivity(intent);
            }
        });

        animation_viewAll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                intent.putExtra("name","Animation");
                intent.putExtra("value","10752");
                startActivity(intent);
            }
        });

        comdey_viewAll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                intent.putExtra("name","Comedy");
                intent.putExtra("value","35");
                startActivity(intent);
            }
        });

        crime_viewAll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                intent.putExtra("name","Crime");
                intent.putExtra("value","80");
                startActivity(intent);
            }
        });

        drama_viewAll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                intent.putExtra("name","Drama");
                intent.putExtra("value","99");
                startActivity(intent);
            }
        });

        documentry_viewAll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                intent.putExtra("name","Documentry");
                intent.putExtra("value","53");
                startActivity(intent);
            }
        });

        family_viewAll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                intent.putExtra("name","Family");
                intent.putExtra("value","37");
                startActivity(intent);
            }
        });
    }

    void init(){


        vp_slider = findViewById(R.id.home_viewPager);

        slider_image_list = new ArrayList<>();
        slider_name_list = new ArrayList<>();


        action_recycler=findViewById(R.id.action_recycler_id);
        adventure_recycler=findViewById(R.id.adventure_recycler);
        animation_recycler=findViewById(R.id.animation_recycle);
        comdey_recycler=findViewById(R.id.comedy_recycle);
        crime_recycler=findViewById(R.id.crime_recycle);
        documentry_recycler=findViewById(R.id.documetry_recycle);
        drama_recycler=findViewById(R.id.drama_recycle);
        family_recycler=findViewById(R.id.family_recycle);
        thumb=findViewById(R.id.thumbs);

        action_viewAll=findViewById(R.id.action);
        adventure_viewAll=findViewById(R.id.adventure);
        animation_viewAll=findViewById(R.id.animation);
        comdey_viewAll=findViewById(R.id.comedy);
        crime_viewAll=findViewById(R.id.crime);
        documentry_viewAll=findViewById(R.id.documentry);
        drama_viewAll=findViewById(R.id.drama);
        family_viewAll=findViewById(R.id.family);

        heading=findViewById(R.id.heading);
        play=findViewById(R.id.play);
        release=findViewById(R.id.release);
        details=findViewById(R.id.details);


        LinearLayoutManager horizontalManager1 = new LinearLayoutManager(MainActivity.this, LinearLayoutManager.HORIZONTAL, false);
        action_recycler.setLayoutManager(horizontalManager1);


        LinearLayoutManager horizontalManager2 = new LinearLayoutManager(MainActivity.this, LinearLayoutManager.HORIZONTAL, false);
        adventure_recycler.setLayoutManager(horizontalManager2);

        LinearLayoutManager horizontalManager3 = new LinearLayoutManager(MainActivity.this, LinearLayoutManager.HORIZONTAL, false);
        crime_recycler.setLayoutManager(horizontalManager3);

        LinearLayoutManager horizontalManager4 = new LinearLayoutManager(MainActivity.this, LinearLayoutManager.HORIZONTAL, false);
        animation_recycler.setLayoutManager(horizontalManager4);

        LinearLayoutManager horizontalManager5 = new LinearLayoutManager(MainActivity.this, LinearLayoutManager.HORIZONTAL, false);
        comdey_recycler.setLayoutManager(horizontalManager5);

        LinearLayoutManager horizontalManager6 = new LinearLayoutManager(MainActivity.this, LinearLayoutManager.HORIZONTAL, false);
        drama_recycler.setLayoutManager(horizontalManager6);

        LinearLayoutManager horizontalManager7 = new LinearLayoutManager(MainActivity.this, LinearLayoutManager.HORIZONTAL, false);
        documentry_recycler.setLayoutManager(horizontalManager7);

        LinearLayoutManager horizontalManager8 = new LinearLayoutManager(MainActivity.this, LinearLayoutManager.HORIZONTAL, false);
        family_recycler.setLayoutManager(horizontalManager8);

        bottom_sheet = findViewById(R.id.bottom_sheet_);
        sheetBehavior = BottomSheetBehavior.from(bottom_sheet);
        sheetBehavior.setState(BottomSheetBehavior.STATE_HIDDEN);
        
        
    }
    private void builBuilder() {
        builder  = new AlertDialog.Builder(MainActivity.this);
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

    private void get_Banner() {
        try {
            dialog.show();
            final RetrofitClientInstance.GetDataService service = RetrofitClientInstance.getRetrofitInstance().create(RetrofitClientInstance.GetDataService.class);
            Call<JsonObject> call = service.get_trending("https://api.themoviedb.org/3/trending/movie/day?api_key=6f0fd849f8d8034b2babde94a37fda97");
            call.enqueue(new Callback<JsonObject>() {
                @Override
                public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {
                    try {


                        JsonObject object = response.body();

                        JsonArray results = object.getAsJsonArray("results");
                        List<String> list = new ArrayList<String>();




                        for(int i = 0 ; i < results.size() ; i++){

                            JsonObject object1=(JsonObject)results.get(i);
                            JsonElement success = object1.get("backdrop_path");
                            JsonElement original_title = object1.get("original_title");
                            slider_image_list.add(AppConstants.IMAGE_URL+success.getAsString());
                            slider_name_list.add( original_title.getAsString());
                            String a = "";

                        }

                        bannerViewpageAdapter = new BannerViewpageAdapter(MainActivity.this, slider_image_list,slider_name_list);
                        vp_slider.setAdapter(bannerViewpageAdapter);
                        dialog.cancel();
                       get_action_viewAll();


                        vp_slider.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
                            @Override
                            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

                            }

                            @Override
                            public void onPageSelected(int position) {

                            }

                            @Override
                            public void onPageScrollStateChanged(int state) {

                            }
                        });


                    }
                    catch (Exception e) {
                        e.printStackTrace();
                    }



                }

                @Override
                public void onFailure(Call<JsonObject> call, Throwable t) {

                }
            });

            // Response<List<ClassVideo>> response=   call.execute();

        } catch (Exception e) {
            String a = "";
            dialog.cancel();
        }
    }

    private void get_action_viewAll() {
        try {

            final RetrofitClientInstance.GetDataService service = RetrofitClientInstance.getRetrofitInstance().create(RetrofitClientInstance.GetDataService.class);
            Call<JsonObject> call = service.get_trending("https://api.themoviedb.org/3/list/28?api_key=6f0fd849f8d8034b2babde94a37fda97&language=en-US");
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
                        JsonObject object = response.body();

                        JsonArray results = object.getAsJsonArray("items");
                        List<String> list = new ArrayList<String>();


                        for (int i = 0; i < results.size(); i++) {

                            JsonObject object1 = (JsonObject) results.get(i);
                            JsonElement success = object1.get("poster_path");
                            JsonElement original_id = object1.get("id");
                            JsonElement original_title = object1.get("original_title");
                            JsonElement overview = object1.get("overview");
                            JsonElement release_date = object1.get("release_date");
                            JsonElement backdrop_path = object1.get("backdrop_path");

                            action_image_list.add(AppConstants.IMAGE_URL45 + success.getAsString());
                            action_name_list.add(original_id.getAsString());

                            data_details.add(overview.getAsString());
                            data_release.add(release_date.getAsString());
                            data_heading.add(original_title.getAsString());
                            data_poster.add(AppConstants.IMAGE_URL45 +backdrop_path.getAsString());
                            String a = "";

                        }


                        listAdapter = new ListAdapter(MainActivity.this, action_image_list, action_name_list, new ListAdapter.ClickInterface() {
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
                        action_recycler.setAdapter(listAdapter);

                        get_adventure_viewAll();
                    }
                }

                @Override
                public void onFailure(Call<JsonObject> call, Throwable t) {
                    String a = "";
                    dialog.cancel();
                }
            });

            // Response<List<ClassVideo>> response=   call.execute();

        } catch (Exception e) {
            String a = "";
            dialog.cancel();
        }
    }

    private void get_adventure_viewAll() {
        try {

            final RetrofitClientInstance.GetDataService service = RetrofitClientInstance.getRetrofitInstance().create(RetrofitClientInstance.GetDataService.class);
            Call<JsonObject> call = service.get_trending("https://api.themoviedb.org/3/list/27?api_key=6f0fd849f8d8034b2babde94a37fda97&language=en-US");
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

                        JsonObject object = response.body();

                        JsonArray results = object.getAsJsonArray("items");
                        List<String> list = new ArrayList<String>();


                        for (int i = 0; i < results.size(); i++) {

                            JsonObject object1 = (JsonObject) results.get(i);
                            JsonElement success = object1.get("poster_path");
                            JsonElement original_id = object1.get("id");
                            JsonElement original_title = object1.get("original_title");
                            JsonElement overview = object1.get("overview");
                            JsonElement release_date = object1.get("release_date");
                            JsonElement backdrop_path = object1.get("backdrop_path");

                            action_image_list.add(AppConstants.IMAGE_URL45 + success.getAsString());
                            action_name_list.add(original_id.getAsString());

                            data_details.add(overview.getAsString());
                            data_release.add(release_date.getAsString());
                            data_heading.add(original_title.getAsString());
                            data_poster.add(AppConstants.IMAGE_URL45 +backdrop_path.getAsString());
                            String a = "";

                        }


                        adventureAdapter = new AdventureAdapter(MainActivity.this, action_image_list, action_name_list, new AdventureAdapter.ClickInterface() {
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
                        adventure_recycler.setAdapter(adventureAdapter);
                        get_animation_viewAll();

                    }
                }

                @Override
                public void onFailure(Call<JsonObject> call, Throwable t) {
                    String a = "";
                    dialog.cancel();
                }
            });

            // Response<List<ClassVideo>> response=   call.execute();

        } catch (Exception e) {
            String a = "";
            dialog.cancel();
        }
    }

    private void get_animation_viewAll() {
        try {

            final RetrofitClientInstance.GetDataService service = RetrofitClientInstance.getRetrofitInstance().create(RetrofitClientInstance.GetDataService.class);
            Call<JsonObject> call = service.get_trending("https://api.themoviedb.org/3/list/10752?api_key=6f0fd849f8d8034b2babde94a37fda97&language=en-US");
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

                        JsonObject object = response.body();

                        JsonArray results = object.getAsJsonArray("items");
                        List<String> list = new ArrayList<String>();


                        for (int i = 0; i < results.size(); i++) {

                            JsonObject object1 = (JsonObject) results.get(i);
                            JsonElement success = object1.get("poster_path");
                            JsonElement original_id = object1.get("id");
                            JsonElement original_title = object1.get("original_title");
                            JsonElement overview = object1.get("overview");
                            JsonElement release_date = object1.get("release_date");
                            JsonElement backdrop_path = object1.get("backdrop_path");

                            action_image_list.add(AppConstants.IMAGE_URL45 + success.getAsString());
                            action_name_list.add(original_id.getAsString());

                            data_details.add(overview.getAsString());
                            data_release.add(release_date.getAsString());
                            data_heading.add(original_title.getAsString());
                            data_poster.add(AppConstants.IMAGE_URL45 +backdrop_path.getAsString());
                            String a = "";

                        }


                        animAdapter = new AnimAdapter(MainActivity.this, action_image_list, action_name_list, new AnimAdapter.ClickInterface() {
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
                        animation_recycler.setAdapter(animAdapter);
                        get_comdey_viewAll();

                    }
                }

                @Override
                public void onFailure(Call<JsonObject> call, Throwable t) {
                    String a = "";
                    dialog.cancel();
                }
            });

            // Response<List<ClassVideo>> response=   call.execute();

        } catch (Exception e) {

            dialog.cancel();
        }
    }

    private void get_comdey_viewAll() {
        try {

            final RetrofitClientInstance.GetDataService service = RetrofitClientInstance.getRetrofitInstance().create(RetrofitClientInstance.GetDataService.class);
            Call<JsonObject> call = service.get_trending("https://api.themoviedb.org/3/list/35?api_key=6f0fd849f8d8034b2babde94a37fda97&language=en-US");
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

                        JsonObject object = response.body();

                        JsonArray results = object.getAsJsonArray("items");
                        List<String> list = new ArrayList<String>();


                        for (int i = 0; i < results.size(); i++) {

                            JsonObject object1 = (JsonObject) results.get(i);
                            JsonElement success = object1.get("poster_path");
                            JsonElement original_id = object1.get("id");
                            JsonElement original_title = object1.get("original_title");
                            JsonElement overview = object1.get("overview");
                            JsonElement release_date = object1.get("release_date");
                            JsonElement backdrop_path = object1.get("backdrop_path");

                            action_image_list.add(AppConstants.IMAGE_URL45 + success.getAsString());
                            action_name_list.add(original_id.getAsString());

                            data_details.add(overview.getAsString());
                            data_release.add(release_date.getAsString());
                            data_heading.add(original_title.getAsString());
                            data_poster.add(AppConstants.IMAGE_URL45 +backdrop_path.getAsString());
                            String a = "";

                        }


                        comdeyAdapter = new ComdeyAdapter(MainActivity.this, action_image_list, action_name_list, new ComdeyAdapter.ClickInterface() {
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
                        comdey_recycler.setAdapter(comdeyAdapter);

                        get_crime_viewAll();
                    }
                }

                @Override
                public void onFailure(Call<JsonObject> call, Throwable t) {
                    String a = "";
                    dialog.cancel();
                }
            });

            // Response<List<ClassVideo>> response=   call.execute();

        } catch (Exception e) {
            String a = "";
            dialog.cancel();
        }
    }

    private void get_crime_viewAll() {
        try {

            final RetrofitClientInstance.GetDataService service = RetrofitClientInstance.getRetrofitInstance().create(RetrofitClientInstance.GetDataService.class);
            Call<JsonObject> call = service.get_trending("https://api.themoviedb.org/3/list/80?api_key=6f0fd849f8d8034b2babde94a37fda97&language=en-US");
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

                        JsonObject object = response.body();

                        JsonArray results = object.getAsJsonArray("items");
                        List<String> list = new ArrayList<String>();


                        for (int i = 0; i < results.size(); i++) {

                            JsonObject object1 = (JsonObject) results.get(i);
                            JsonElement success = object1.get("poster_path");
                            JsonElement original_id = object1.get("id");
                            JsonElement original_title = object1.get("original_title");
                            JsonElement overview = object1.get("overview");
                            JsonElement release_date = object1.get("release_date");
                            JsonElement backdrop_path = object1.get("backdrop_path");

                            action_image_list.add(AppConstants.IMAGE_URL45 + success.getAsString());
                            action_name_list.add(original_id.getAsString());

                            data_details.add(overview.getAsString());
                            data_release.add(release_date.getAsString());
                            data_heading.add(original_title.getAsString());
                            data_poster.add(AppConstants.IMAGE_URL45 +backdrop_path.getAsString());
                            String a = "";

                        }


                        crimeAdapter= new CrimeAdapter(MainActivity.this, action_image_list, action_name_list, new CrimeAdapter.ClickInterface() {
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
                        crime_recycler.setAdapter(crimeAdapter);
                        get_docum_viewAll();

                    }
                }

                @Override
                public void onFailure(Call<JsonObject> call, Throwable t) {
                    String a = "";
                    dialog.cancel();
                }
            });

            // Response<List<ClassVideo>> response=   call.execute();

        } catch (Exception e) {
            String a = "";
            dialog.cancel();
        }
    }

    private void get_docum_viewAll() {
        try {

            final RetrofitClientInstance.GetDataService service = RetrofitClientInstance.getRetrofitInstance().create(RetrofitClientInstance.GetDataService.class);
            Call<JsonObject> call = service.get_trending("https://api.themoviedb.org/3/list/99?api_key=6f0fd849f8d8034b2babde94a37fda97&language=en-US");
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

                        JsonObject object = response.body();

                        JsonArray results = object.getAsJsonArray("items");
                        List<String> list = new ArrayList<String>();


                        for (int i = 0; i < results.size(); i++) {

                            JsonObject object1 = (JsonObject) results.get(i);
                            JsonElement success = object1.get("poster_path");
                            JsonElement original_id = object1.get("id");
                            JsonElement original_title = object1.get("original_title");
                            JsonElement overview = object1.get("overview");
                            JsonElement release_date = object1.get("release_date");
                            JsonElement backdrop_path = object1.get("backdrop_path");

                            action_image_list.add(AppConstants.IMAGE_URL45 + success.getAsString());
                            action_name_list.add(original_id.getAsString());

                            data_details.add(overview.getAsString());
                            data_release.add(release_date.getAsString());
                            data_heading.add(original_title.getAsString());
                            data_poster.add(AppConstants.IMAGE_URL45 +success.getAsString());
                            String a = "";

                        }


                        documAdapter = new DocumAdapter(MainActivity.this, action_image_list, action_name_list, new DocumAdapter.ClickInterface() {
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
                        documentry_recycler.setAdapter(documAdapter);
                        get_drama_viewAll();

                    }
                }

                @Override
                public void onFailure(Call<JsonObject> call, Throwable t) {
                    String a = "";
                    dialog.cancel();
                }
            });

            // Response<List<ClassVideo>> response=   call.execute();

        } catch (Exception e) {
            String a = "";
            dialog.cancel();
        }
    }

    private void get_drama_viewAll() {
        try {

            final RetrofitClientInstance.GetDataService service = RetrofitClientInstance.getRetrofitInstance().create(RetrofitClientInstance.GetDataService.class);
            Call<JsonObject> call = service.get_trending("https://api.themoviedb.org/3/list/53?api_key=6f0fd849f8d8034b2babde94a37fda97&language=en-US");
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

                        JsonObject object = response.body();

                        JsonArray results = object.getAsJsonArray("items");
                        List<String> list = new ArrayList<String>();


                        for (int i = 0; i < results.size(); i++) {

                            JsonObject object1 = (JsonObject) results.get(i);
                            JsonElement success = object1.get("poster_path");
                            JsonElement original_id = object1.get("id");
                            JsonElement original_title = object1.get("original_title");
                            JsonElement overview = object1.get("overview");
                            JsonElement release_date = object1.get("release_date");
                            JsonElement backdrop_path = object1.get("backdrop_path");

                            action_image_list.add(AppConstants.IMAGE_URL45 + success.getAsString());
                            action_name_list.add(original_id.getAsString());

                            data_details.add(overview.getAsString());
                            data_release.add(release_date.getAsString());
                            data_heading.add(original_title.getAsString());
                            data_poster.add(AppConstants.IMAGE_URL45 +backdrop_path.getAsString());
                            String a = "";

                        }


                        dramaAdapter = new DramaAdapter(MainActivity.this, action_image_list, action_name_list, new DramaAdapter.ClickInterface() {
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
                        drama_recycler.setAdapter(dramaAdapter);
                        get_family_viewAll();

                    }
                }

                @Override
                public void onFailure(Call<JsonObject> call, Throwable t) {
                    String a = "";
                    dialog.cancel();
                }
            });

            // Response<List<ClassVideo>> response=   call.execute();

        } catch (Exception e) {
            String a = "";
            dialog.cancel();
        }
    }

    private void get_family_viewAll() {
        try {

            final RetrofitClientInstance.GetDataService service = RetrofitClientInstance.getRetrofitInstance().create(RetrofitClientInstance.GetDataService.class);
            Call<JsonObject> call = service.get_trending("https://api.themoviedb.org/3/list/37?api_key=6f0fd849f8d8034b2babde94a37fda97&language=en-US");
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

                        JsonObject object = response.body();

                        JsonArray results = object.getAsJsonArray("items");
                        List<String> list = new ArrayList<String>();


                        for (int i = 0; i < results.size(); i++) {

                            JsonObject object1 = (JsonObject) results.get(i);
                            JsonElement success = object1.get("poster_path");
                            JsonElement original_id = object1.get("id");
                            JsonElement original_title = object1.get("original_title");
                            JsonElement overview = object1.get("overview");
                            JsonElement release_date = object1.get("release_date");
                            JsonElement backdrop_path = object1.get("backdrop_path");

                            action_image_list.add(AppConstants.IMAGE_URL45 + success.getAsString());
                            action_name_list.add(original_id.getAsString());

                            data_details.add(overview.getAsString());
                            data_release.add(release_date.getAsString());
                            data_heading.add(original_title.getAsString());
                            data_poster.add(AppConstants.IMAGE_URL45 +backdrop_path.getAsString());
                            String a = "";

                        }


                        familyAdapter = new FamilyAdapter(MainActivity.this, action_image_list, action_name_list, new FamilyAdapter.ClickInterface() {
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
                        family_recycler.setAdapter(familyAdapter);


                    }
                }

                @Override
                public void onFailure(Call<JsonObject> call, Throwable t) {
                    String a = "";
                    dialog.cancel();
                }
            });

            // Response<List<ClassVideo>> response=   call.execute();

        } catch (Exception e) {
            String a = "";
            dialog.cancel();
        }
    }



}