package com.example.allenthomasjoy.fivehundredpx;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.allenthomasjoy.fivehundredpx.dto.DataImage;


public class DetailActivity extends AppCompatActivity {

    private ImageView imageViewDetail;
    private TextView  textView;
    private DataImage mDataImage;
    private Context mContext;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        mContext = getApplicationContext();

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        if(getIntent().getSerializableExtra(mContext.getString(R.string.data_image_detail))!=null) {
            mDataImage = (DataImage) getIntent().getSerializableExtra(mContext.getString(R.string.data_image_detail));
        }

        initViews();
        mapData();
    }

    private void initViews() {
        imageViewDetail = findViewById(R.id.image_detail);
        textView = findViewById(R.id.image_name);
    }

    private void mapData() {
        if (mDataImage != null) {
            String coverUrl = mDataImage.getUrl();
            Glide.with(this).load(coverUrl).into(imageViewDetail);
            textView.setText(mDataImage.getName());
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                onBackPressed();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}
