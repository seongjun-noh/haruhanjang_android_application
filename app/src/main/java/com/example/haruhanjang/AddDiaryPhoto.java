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

public class AddDiaryPhoto extends Fragment {
    private final int GET_GALLERY_IMAGE = 200;

    ImageView addDiaryPhotoImageView;
//    EditText addDiaryPhotoTitleEdt;

    public static AddDiaryPhoto newInstance(int number) {
        AddDiaryPhoto fragment = new AddDiaryPhoto();
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
        return LayoutInflater.from(inflater.getContext()).inflate(R.layout.fragment_add_diary_photo, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        addDiaryPhotoImageView = (ImageView) getView().findViewById(R.id.addDiaryPhotoImageView);
//        addDiaryPhotoTitleEdt = (EditText) getView().findViewById(R.id.addDiaryPhotoTitleEdt);

        addDiaryPhotoImageView.setImageResource(R.drawable.add_diary_photo_plus);
//        addDiaryPhotoImageView.setScaleType(ImageView.ScaleType.FIT_XY);

        addDiaryPhotoImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Intent.ACTION_PICK);
                intent.setDataAndType(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, "image/*");
                getActivity().startActivityForResult(intent, GET_GALLERY_IMAGE);
            }
        });
    }

    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        if(requestCode == GET_GALLERY_IMAGE && resultCode == -1 && data != null && data.getData() != null) {
            Uri selectedImageUri = data.getData();
            addDiaryPhotoImageView.setImageURI(selectedImageUri);
        }
    }
}


