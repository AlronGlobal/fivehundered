package com.example.allenthomasjoy.fivehundredpx;

import android.content.Context;
import android.content.Intent;
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

import java.io.Serializable;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private Context mContext;
    private ArrayList<DataImage> mImages;

    private RecyclerView recyclerImages;
    private StaggeredGridLayoutManager gaggeredGridLayoutManager;
    private ImageRecyclerAdapter imageRecyclerAdapter;
    private int currentPageIndex;
    private int totalPage;

    public interface OnImageClickListener {
        void onImageClick(int position);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mContext = getApplicationContext();
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        mImages = new ArrayList<>();
        initViews();
        currentPageIndex = 0;
        totalPage = 0;
        serviceRequest();
    }

    private void initViews() {
        recyclerImages = (RecyclerView) findViewById(R.id.recycler_view);
        gaggeredGridLayoutManager = new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL);
        recyclerImages.setLayoutManager(gaggeredGridLayoutManager);
        imageRecyclerAdapter = new ImageRecyclerAdapter(mContext, mImages, MainActivity.this, new OnImageClickListener() {
            @Override
            public void onImageClick(int position) {
                mImages.get(position);
                Intent intent = new Intent(mContext, DetailActivity.class);
                intent.putExtra(mContext.getString(R.string.data_image_detail), (Serializable) mImages.get(position));
                startActivity(intent);
            }
        });
        recyclerImages.setAdapter(imageRecyclerAdapter);
        recyclerImages.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                RecyclerView.LayoutManager layoutManager = recyclerView.getLayoutManager();
                int visibleItemCount = layoutManager.getChildCount();
                int totalItemCount = layoutManager.getItemCount();
                int[] pastVisibleItems =  gaggeredGridLayoutManager.findFirstCompletelyVisibleItemPositions(null);
                int pastVisibleItem = pastVisibleItems[0];
                if (pastVisibleItem + visibleItemCount >= totalItemCount) {
                    serviceRequest();
                }
            }
        });
    }

    private void serviceRequest() {
        if (currentPageIndex == 0 ||currentPageIndex < totalPage) {
            currentPageIndex++;
        }
        RequestPhotos requestPhotos = new RequestPhotos(mContext,currentPageIndex,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            currentPageIndex = new JsonParser().parse(response).getAsJsonObject().get("current_page").getAsInt();
                            totalPage = new JsonParser().parse(response).getAsJsonObject().get("total_pages").getAsInt();
                            JsonArray photosArray = new JsonParser().parse(response).getAsJsonObject().getAsJsonArray(mContext.getString(R.string.data_photos));
                            for(int i = 0; i < photosArray.size(); i++) {
                                JsonArray imagesArray = photosArray.get(i).getAsJsonObject().getAsJsonArray(mContext.getString(R.string.data_images));
                                JsonObject imageObject = imagesArray.get(0).getAsJsonObject();
                                DataImage image = new DataImage(mContext,imageObject);
                                image.setName(photosArray.get(i).getAsJsonObject().get("name").getAsString());
                                mImages.add(image);
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
