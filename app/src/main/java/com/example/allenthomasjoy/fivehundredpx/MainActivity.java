package com.example.allenthomasjoy.fivehundredpx;

import android.content.Context;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.example.allenthomasjoy.fivehundredpx.adapters.ImageRecyclerAdapter;
import com.example.allenthomasjoy.fivehundredpx.dto.DataImage;
import com.example.allenthomasjoy.fivehundredpx.network.RequestPhotos;
import com.example.allenthomasjoy.fivehundredpx.singleton.SingletonRequestQueue;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private Context mContext;
    private ArrayList<DataImage> mImage;

    private RecyclerView recyclerImages;
    private StaggeredGridLayoutManager gaggeredGridLayoutManager;
    private ImageRecyclerAdapter imageRecyclerAdapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mContext = getApplicationContext();
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        mImage = new ArrayList<>();
        initViews();
        intializeRequest();
    }

    private void initViews() {
        recyclerImages = (RecyclerView) findViewById(R.id.recycler_view);
        gaggeredGridLayoutManager = new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL);
        recyclerImages.setLayoutManager(gaggeredGridLayoutManager);
        imageRecyclerAdapter = new ImageRecyclerAdapter(mContext, mImage, MainActivity.this);
        recyclerImages.setAdapter(imageRecyclerAdapter);
    }

    private void intializeRequest() {
        mImage.clear();
        RequestPhotos requestPhotos = new RequestPhotos(mContext,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JsonArray photosArray = new JsonParser().parse(response).getAsJsonObject().getAsJsonArray(mContext.getString(R.string.data_photos));
                            for(int i = 0; i < photosArray.size(); i++) {
                                JsonArray imagesArray = photosArray.get(i).getAsJsonObject().getAsJsonArray(mContext.getString(R.string.data_images));
                                JsonObject imageObject = imagesArray.get(0).getAsJsonObject();
                                DataImage image = new DataImage(mContext,imageObject);
                                mImage.add(image);
                            }
                            imageRecyclerAdapter.notifyDataSetChanged();
                        } catch (Exception e) {
                            imageRecyclerAdapter.notifyDataSetChanged();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                    }
                });
        SingletonRequestQueue.getInstance(mContext).addToRequestQueue(requestPhotos);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
