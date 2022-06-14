package com.example.haruhanjang;

import android.app.Activity;
import android.content.ContentResolver;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.viewpager2.widget.ViewPager2;

import java.io.File;
import java.util.ArrayList;
import java.util.Date;

public class ViewDiary extends AppCompatActivity {
    private ViewPager2 viewPager2;

    int diaryID, diaryIsFavorite;
    boolean isPosition0isFirst = true, isPosition1isFirst = true;
    TextView editDateTv, photoTitleTv, diaryTextTv;
    ImageView diaryPhotoImageView;
    Button deleteBtn, updateBtn, shareBtn, favoriteBtn;
    String diaryEditDate, diaryPhotoPATH, diaryTitle, diaryText;

    MyDBHelper myDBHelper;

    ArrayList<Fragment> fragments = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.view_diary);

        editDateTv = (TextView) findViewById(R.id.viewDiaryEditDateTv);
        deleteBtn = (Button) findViewById(R.id.deleteBtn);
        updateBtn = (Button) findViewById(R.id.updateBtn);
        shareBtn = (Button) findViewById(R.id.shareBtn);
        favoriteBtn = (Button) findViewById(R.id.favoriteBtn);

        diaryID = getIntent().getIntExtra("diaryID", 0);
        myDBHelper = new MyDBHelper(getApplicationContext());
        SQLiteDatabase sqlDB = myDBHelper.getReadableDatabase();
        Cursor cursor = sqlDB.rawQuery("SELECT * FROM diary WHERE diaryID = " + diaryID + ";", null);

        cursor.moveToNext();

        DiaryData diaryData = new DiaryData(cursor.getInt(0), cursor.getString(1), cursor.getString(2), cursor.getString(3), cursor.getString(4), cursor.getInt(5));
        sqlDB.close();

        diaryEditDate = diaryData.diaryEditDate;
        diaryPhotoPATH = diaryData.imagePath;
        diaryTitle = diaryData.photoTitle;
        diaryText = diaryData.diaryText;
        diaryIsFavorite = diaryData.isFavorite;

        fragments.add(ViewDiaryPhoto.newInstance(0));
        fragments.add(ViewDiaryText.newInstance(1));

        viewPager2 = (ViewPager2) findViewById(R.id.viewDiaryPager);

        ViewPager2Adapter viewPager2Adapter = new ViewPager2Adapter(this, fragments);
        viewPager2.registerOnPageChangeCallback(new ViewPager2.OnPageChangeCallback() {
            @Override
            public void onPageSelected(int position) {
                super.onPageSelected(position);

                if(position == 0) {
                    try {
                        if(isPosition0isFirst) {
                            diaryPhotoImageView = (ImageView) viewPager2.findViewById(R.id.viewDiaryPhotoImageView);
                            Bitmap bmImg = BitmapFactory.decodeFile(diaryPhotoPATH);
                            diaryPhotoImageView.setImageBitmap(bmImg);
                            photoTitleTv = (TextView) viewPager2.findViewById(R.id.viewDiaryPhotoTitleTv);
                            photoTitleTv.setText(diaryTitle);
                            isPosition0isFirst = false;
                        }
                    } catch (Exception e) {

                    }
                } else {
                    try {
                        if(isPosition1isFirst) {
                            diaryTextTv = (TextView) viewPager2.findViewById(R.id.viewDiaryTextTv);
                            diaryTextTv.setText(diaryText);
                            isPosition1isFirst = false;
                        }
                    } catch (Exception e) {

                    }
                }
            }
        });
        viewPager2.setAdapter(viewPager2Adapter);

        editDateTv.setText(diaryEditDate);

        deleteBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                MyDBHelper myDBHelper = new MyDBHelper(getApplicationContext());
                SQLiteDatabase sqlDB = myDBHelper.getWritableDatabase();

                sqlDB.execSQL("DELETE FROM diary WHERE diaryID = " + diaryID);
                File file = new File(diaryPhotoPATH);
                file.delete();

                sqlDB.close();

                finish();
            }
        });

        updateBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), UpdateDiary.class);
                intent.putExtra("diaryID", diaryID);
                startActivityForResult(intent, 0);
            }
        });

        shareBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Uri file = getUriFromPath(diaryPhotoPATH);
                Intent shareIntent = new Intent(android.content.Intent.ACTION_SEND);
                shareIntent.setType("image/*");
                shareIntent.putExtra(Intent.EXTRA_STREAM,file);
                shareIntent.setPackage("com.instagram.android");
                startActivity(shareIntent);
            }
        });

        favoriteBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                myDBHelper = new MyDBHelper(getApplicationContext());
                SQLiteDatabase sqlDB = myDBHelper.getWritableDatabase();

                if(diaryIsFavorite == 0) {
                    sqlDB.execSQL("UPDATE diary SET isFavorite = 1 WHERE diaryID = " + diaryID);
                    diaryIsFavorite = 1;
                } else {
                    sqlDB.execSQL("UPDATE diary SET isFavorite = 0 WHERE diaryID = " + diaryID);
                    diaryIsFavorite = 0;
                }

                sqlDB.close();
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        recreate();
    }

    private Uri getUriFromPath(String filePath) {
        long photoId;
        Uri photoUri = MediaStore.Images.Media.getContentUri("external");
        String[] projection = {MediaStore.Images.ImageColumns._ID};
        Cursor cursor = getContentResolver().query(photoUri, projection, MediaStore.Images.ImageColumns.DATA + " LIKE ?", new String[] { filePath }, null);
        cursor.moveToFirst();

        int columnIndex = cursor.getColumnIndex(projection[0]);
        photoId = cursor.getLong(columnIndex);

        cursor.close();
        return Uri.parse(photoUri.toString() + "/" + photoId);
    }

}

