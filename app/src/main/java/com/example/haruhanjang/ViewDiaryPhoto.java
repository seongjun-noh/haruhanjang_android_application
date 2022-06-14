package com.example.haruhanjang;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

public class ViewDiaryPhoto extends Fragment {

    ImageView viewDiaryPhotoImageView;
    TextView viewDiaryPhotoTitleTv;

    public static ViewDiaryPhoto newInstance(int number) {
        ViewDiaryPhoto fragment = new ViewDiaryPhoto();
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
        return LayoutInflater.from(inflater.getContext()).inflate(R.layout.fragment_view_diary_photo, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        viewDiaryPhotoImageView = (ImageView) getView().findViewById(R.id.viewDiaryPhotoImageView);
        viewDiaryPhotoTitleTv = (TextView) getView().findViewById(R.id.viewDiaryPhotoTitleTv);

        viewDiaryPhotoImageView.setImageResource(R.drawable.polaroid_camera);
        viewDiaryPhotoImageView.setScaleType(ImageView.ScaleType.FIT_XY);
    }
}
