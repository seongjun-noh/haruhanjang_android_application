package com.example.haruhanjang;

import android.content.Intent;
import android.media.Image;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

public class UpdateDiaryPhoto extends Fragment {
    private final int GET_GALLERY_IMAGE = 200;

    ImageView UpdateDiaryPhotoImageView;
//    EditText addDiaryPhotoTitleEdt;

    public static UpdateDiaryPhoto newInstance(int number) {
        UpdateDiaryPhoto fragment = new UpdateDiaryPhoto();
        Bundle bundle = new Bundle();
        bundle.putInt("number", number);
        fragment.setArguments(bundle);

        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            int num = getArguments().getInt("number");
        }
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return LayoutInflater.from(inflater.getContext()).inflate(R.layout.fragment_update_diary_photo, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        UpdateDiaryPhotoImageView = (ImageView) getView().findViewById(R.id.updateDiaryPhotoImageView);
//        addDiaryPhotoTitleEdt = (EditText) getView().findViewById(R.id.addDiaryPhotoTitleEdt);

        UpdateDiaryPhotoImageView.setImageResource(R.drawable.add_diary_photo_plus);
        UpdateDiaryPhotoImageView.setScaleType(ImageView.ScaleType.FIT_XY);
    }
}


