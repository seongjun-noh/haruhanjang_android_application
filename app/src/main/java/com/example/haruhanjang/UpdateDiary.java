package com.example.haruhanjang;


import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;

import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.viewpager2.widget.ViewPager2;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class UpdateDiary extends AppCompatActivity {
    private ViewPager2 viewPager2;

    TextView editDateTv;
    EditText updateDiaryPhotoTitleEdt, updateDiaryTextEdt;
    ImageView updatePhotoImageView;
    Button saveBtn, cancelBtn;
    Uri selectedImageUri = null;
    int diaryID;
    String photoTitle = "", diaryText = "";
    boolean isPosition0isFirst = true, isPosition1isFirst = true;

    ArrayList<Fragment> fragments = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.update_diary);

        fragments.add(UpdateDiaryPhoto.newInstance(0));
        fragments.add(UpdateDiaryText.newInstance(1));

        viewPager2 = (ViewPager2) findViewById(R.id.updateDiaryPager);

        ViewPager2Adapter viewPager2Adapter = new ViewPager2Adapter(this, fragments);

        diaryID = getIntent().getIntExtra("diaryID", 0);
        MyDBHelper myDBHelper = new MyDBHelper(getApplicationContext());
        SQLiteDatabase sqlDB = myDBHelper.getReadableDatabase();
        Cursor cursor = sqlDB.rawQuery("SELECT * FROM diary WHERE diaryID = " + diaryID + ";", null);

        cursor.moveToNext();

        DiaryData diaryData = new DiaryData(cursor.getInt(0), cursor.getString(1), cursor.getString(2), cursor.getString(3), cursor.getString(4), cursor.getInt(5));
        sqlDB.close();

        viewPager2.registerOnPageChangeCallback(new ViewPager2.OnPageChangeCallback() {
            @Override
            public void onPageSelected(int position) {
                super.onPageSelected(position);
                if(position == 0) {
                    try {
                        if(isPosition0isFirst) {
                            updatePhotoImageView = (ImageView) viewPager2.findViewById(R.id.updateDiaryPhotoImageView);
                            Bitmap bmImg = BitmapFactory.decodeFile(diaryData.imagePath);
                            updatePhotoImageView.setImageBitmap(bmImg);
                            updateDiaryPhotoTitleEdt = (EditText) viewPager2.findViewById(R.id.updateDiaryPhotoTitleEdt);
                            updateDiaryPhotoTitleEdt.setText(diaryData.photoTitle);
                            isPosition0isFirst = false;
                        }
                        updateDiaryTextEdt = (EditText) viewPager2.findViewById(R.id.updateDiaryTextEdt);
                        diaryText = updateDiaryTextEdt.getText().toString();
                    } catch (Exception e) {

                    }
                } else {
                    try {
                        if(isPosition1isFirst) {
                            updateDiaryTextEdt = (EditText) viewPager2.findViewById(R.id.updateDiaryTextEdt);
                            updateDiaryTextEdt.setText(diaryData.diaryText);
                            isPosition1isFirst = false;
                        }
                        updateDiaryPhotoTitleEdt = (EditText) viewPager2.findViewById(R.id.updateDiaryPhotoTitleEdt);
                        photoTitle = updateDiaryPhotoTitleEdt.getText().toString();
                    } catch (Exception e) {

                    }
                }
            }
        });
        viewPager2.setAdapter(viewPager2Adapter);

        editDateTv = (TextView) findViewById(R.id.updateEditDateTv);
        saveBtn = (Button) findViewById(R.id.updateDiarySaveBtn);
        cancelBtn = (Button) findViewById(R.id.updateDiaryCancelBtn);

        long now = System.currentTimeMillis();
        Date mDate = new Date(now);
        SimpleDateFormat simpleDate = new SimpleDateFormat("yyyy.MM.dd");
        String getTime = simpleDate.format(mDate);
        editDateTv.setText(getTime);

        saveBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String editDateStr = editDateTv.getText().toString();
                if(viewPager2.getCurrentItem() == 0) {
                    updateDiaryPhotoTitleEdt = (EditText) viewPager2.findViewById(R.id.updateDiaryPhotoTitleEdt);
                    photoTitle = updateDiaryPhotoTitleEdt.getText().toString();
                } else {
                    updateDiaryTextEdt = (EditText) viewPager2.findViewById(R.id.updateDiaryTextEdt);
                    diaryText = updateDiaryTextEdt.getText().toString();
                }

                MyDBHelper myDBHelper = new MyDBHelper(getApplicationContext());
                SQLiteDatabase sqlDB = myDBHelper.getWritableDatabase();

                sqlDB.execSQL("UPDATE diary SET diaryTitle = '" + photoTitle + "', diaryTEXT = '" + diaryText + "' WHERE diaryID = " + diaryID);

                sqlDB.close();

                finish();
            }
        });

        cancelBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }
}
