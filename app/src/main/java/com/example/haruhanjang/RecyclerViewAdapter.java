package com.example.haruhanjang;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import android.content.Context;
import android.util.Log;

import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewHolder> {

    Context context;
    private ArrayList<DiaryData> mDataset;

    public RecyclerViewAdapter(Context context, ArrayList<DiaryData> myDataset) {
        super();
        this.context = context;
        this.mDataset = myDataset;
    }

    @Override
    public RecyclerViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.recyclerview_item, parent, false);  // recyclerview
        Log.d("hwang", "onCreateViewHolder");
        RecyclerViewHolder vh = new RecyclerViewHolder(v);
        return vh;
    }

    @Override
    public void onBindViewHolder(RecyclerViewHolder holder, int position) {
//        final int newpos = position;
        final Context mycontext = holder.itemView.getContext();

        Bitmap bmImg = BitmapFactory.decodeFile(mDataset.get(position).imagePath);
        holder.recyclerViewItemImage.setImageBitmap(bmImg);
        holder.recyclerViewItemTextView.setText(mDataset.get(position).diaryEditDate);
        holder.recyclerViewItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(view.getContext(), ViewDiary.class);
                intent.putExtra("diaryID", mDataset.get(position).diaryID);
                intent.putExtra("diaryEditDate", mDataset.get(position).diaryEditDate);
                intent.putExtra("imagePath", mDataset.get(position).imagePath);
                intent.putExtra("photoTitle", mDataset.get(position).photoTitle);
                intent.putExtra("diaryText", mDataset.get(position).diaryText);
                ((Activity)mycontext).startActivityForResult(intent, 0);
            }
        });
    }

    @Override
    public int getItemCount() {
        return mDataset.size(); //
    }

}