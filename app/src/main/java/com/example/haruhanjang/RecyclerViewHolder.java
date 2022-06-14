package com.example.haruhanjang;

import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

public class RecyclerViewHolder extends RecyclerView.ViewHolder {

    LinearLayout recyclerViewItem;
    TextView recyclerViewItemTextView;
    ImageView recyclerViewItemImage;

    public RecyclerViewHolder(View itemView) {
        super(itemView);

        recyclerViewItemImage = (ImageView) itemView.findViewById(R.id.recyclerViewItemImage);
        recyclerViewItemTextView = (TextView) itemView.findViewById(R.id.recyclerViewItemTextView);
        recyclerViewItem = (LinearLayout) itemView.findViewById(R.id.recyclerViewItem);
    }

}