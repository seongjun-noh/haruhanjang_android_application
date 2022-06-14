package com.example.haruhanjang;

import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.viewpager2.widget.ViewPager2;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class AddDiary extends AppCompatActivity {
    private ViewPager2 viewPager2;

    TextView editDateTv;
    EditText addDiaryPhotoTitleEdt, addDiaryTextEdt;
    Button saveBtn, cancelBtn;
    Uri selectedImageUri = null;
    String photoTitle = "", diaryText = "", photoPATH = "", photoName = "";

    ArrayList<Fragment> fragments = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_diary);

        fragments.add(AddDiaryPhoto.newInstance(0));
        fragments.add(AddDiaryText.newInstance(1));

        viewPager2 = (ViewPager2) findViewById(R.id.addDiaryPager);

        ViewPager2Adapter viewPager2Adapter = new ViewPager2Adapter(this, fragments);
        viewPager2.registerOnPageChangeCallback(new ViewPager2.OnPageChangeCallback() {
            @Override
            public void onPageSelected(int position) {
                super.onPageSelected(position);

                if(position == 0) {
                    try {
                        addDiaryTextEdt = (EditText) viewPager2.findViewById(R.id.addDiaryTextEdt);
                        diaryText = addDiaryTextEdt.getText().toString();
                    } catch (Exception e) {

                    }
                } else {
                    try {
                        addDiaryPhotoTitleEdt = (EditText) viewPager2.findViewById(R.id.addDiaryPhotoTitleEdt);
                        photoTitle = addDiaryPhotoTitleEdt.getText().toString();
                    } catch (Exception e) {

                    }
                }
            }
        });
        viewPager2.setAdapter(viewPager2Adapter);

        editDateTv = (TextView) findViewById(R.id.addDiaryEditDateTv);
        saveBtn = (Button) findViewById(R.id.addDiarySaveBtn);
        cancelBtn = (Button) findViewById(R.id.addDiaryCancelBtn);

        long now = System.currentTimeMillis();
        Date mDate = new Date(now);
        SimpleDateFormat simpleDate = new SimpleDateFormat("yyyy.MM.dd");
        String getTime = simpleDate.format(mDate);
        editDateTv.setText(getTime);

        saveBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String editDateStr = editDateTv.getText().toString();
//                String picturePath = Environment.getExternalStorageDirectory().getAbsolutePath() + "/Pictures";
                if(viewPager2.getCurrentItem() == 0) {
                    addDiaryPhotoTitleEdt = (EditText) viewPager2.findViewById(R.id.addDiaryPhotoTitleEdt);
                    photoTitle = addDiaryPhotoTitleEdt.getText().toString();
                } else {
                    addDiaryTextEdt = (EditText) viewPager2.findViewById(R.id.addDiaryTextEdt);
                    diaryText = addDiaryTextEdt.getText().toString();
                }

                MyDBHelper myDBHelper = new MyDBHelper(getApplicationContext());
                SQLiteDatabase sqlDB = myDBHelper.getWritableDatabase();

//                sqlDB.execSQL("INSERT INTO diary VALUES (NULL, '" + editDateStr + "', '', '" + photoTitle + "', '" + diaryText + "', 0);");
                ContentValues values = new ContentValues();

                values.put("diaryEditDate", editDateStr);
                values.put("diaryPhotoPATH", "");
                values.put("diaryTitle", photoTitle);
                values.put("diaryText", diaryText);
                values.put("isFavorite", 0);

                long insertedID = sqlDB.insert("diary", null, values);
                photoName = "photo" + insertedID + ".png";
                photoPATH = Environment.getExternalStorageDirectory().getAbsolutePath() + "/Pictures/" + photoName;
                try {
                    sqlDB.execSQL("UPDATE diary SET diaryPhotoPath = '" + photoPATH + "' WHERE diaryID = " + insertedID + ";");
                }catch (Exception e) {

                }

                ContentResolver resolver = getContentResolver();
                try {
                    InputStream instream = resolver.openInputStream(selectedImageUri);
                    Bitmap imgBitmap = BitmapFactory.decodeStream(instream);
                    instream.close();   // 스트림 닫아주기
                    saveBitmapToJpeg(imgBitmap, photoName);    // 내부 저장소에 저장
//                    Toast.makeText(getApplicationContext(), "파일 불러오기 성공", Toast.LENGTH_SHORT).show();
                } catch (Exception e) {
//                    Toast.makeText(getApplicationContext(), "파일 불러오기 실패", Toast.LENGTH_SHORT).show();
                }

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

    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        Fragment targetFragment = fragments.get(0);
        if (targetFragment == null) {
        } else {
            selectedImageUri = data.getData();
            targetFragment.onActivityResult(requestCode & 0xffff, resultCode, data);
        }
    }

    public void saveBitmapToJpeg(Bitmap bitmap, String imgName) {   // 선택한 이미지 내부 저장소에 저장
        File tempFile = new File(Environment.getExternalStorageDirectory().getAbsolutePath() + "/Pictures", imgName);    // 파일 경로와 이름 넣기
        try {
            tempFile.createNewFile();   // 자동으로 빈 파일을 생성하기
            FileOutputStream out = new FileOutputStream(tempFile);  // 파일을 쓸 수 있는 스트림을 준비하기
            bitmap.compress(Bitmap.CompressFormat.PNG, 100, out);   // compress 함수를 사용해 스트림에 비트맵을 저장하기
            out.close();    // 스트림 닫아주기
//            Toast.makeText(getApplicationContext(), "파일 저장 성공", Toast.LENGTH_SHORT).show();
        } catch (Exception e) {
//            Toast.makeText(getApplicationContext(), "파일 저장 실패", Toast.LENGTH_SHORT).show();
        }
    }
}
