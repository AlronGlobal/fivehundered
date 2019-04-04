package com.example.allenthomasjoy.fivehundredpx.adapters;


import android.app.Activity;
import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.example.allenthomasjoy.fivehundredpx.MainActivity;
import com.example.allenthomasjoy.fivehundredpx.R;
import com.example.allenthomasjoy.fivehundredpx.dto.DataImage;
import com.example.allenthomasjoy.fivehundredpx.viewholders.ViewHolderImage;

import java.util.List;

public class ImageRecyclerAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private Context mContext;
    private List<DataImage> mDataImageList;
    private Activity myActivity;

    public ImageRecyclerAdapter(Context context, List<DataImage> dataImageList, MainActivity mainActivity) {
        this.mContext = context;
        this.mDataImageList = dataImageList;
        this.myActivity = mainActivity;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        return new ViewHolderImage(LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_image, viewGroup, false));
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder viewHolder, int i) {
        if (viewHolder instanceof  ViewHolderImage) {
            ((ViewHolderImage)viewHolder).bind(this.mContext, this.myActivity, this.mDataImageList.get(i));
        }
    }

    @Override
    public int getItemCount() {
        return this.mDataImageList.size();
    }
}
