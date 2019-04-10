package com.example.allenthomasjoy.fivehundredpx.viewholders;

import android.app.Activity;
import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TableRow;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.example.allenthomasjoy.fivehundredpx.MainActivity;
import com.example.allenthomasjoy.fivehundredpx.R;
import com.example.allenthomasjoy.fivehundredpx.dto.DataImage;

public class ViewHolderImage extends RecyclerView.ViewHolder {

    public ImageView image;

    public ViewHolderImage(View itemView) {
        super(itemView);
        image = (ImageView) itemView.findViewById(R.id.item_image);
    }

    public void bind(final Context context, Activity myActivity, DataImage dataImage, final int i, final MainActivity.OnImageClickListener listener) {
        DisplayMetrics dm = new DisplayMetrics();
        myActivity.getWindowManager().getDefaultDisplay().getMetrics(dm);
        float density = context.getResources().getDisplayMetrics().density;
        int height = (int) (dm.widthPixels / 2.0);

        RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(TableRow.LayoutParams.MATCH_PARENT, height); // (width, height)
        image.setLayoutParams(params);
        if (dataImage.getUrl()!= null) {
            try{
                Glide.with(context)
                        .load(dataImage.getUrl())
                        .apply(RequestOptions.placeholderOf(null).error(R.drawable.ic_launcher_background))
                        .into(image);
            }catch (Exception e) {

            }
        }
        image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listener.onImageClick(i);
            }
        });
    }
}
