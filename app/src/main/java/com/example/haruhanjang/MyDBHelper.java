package com.example.haruhanjang;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;
import android.widget.Toast;

public class MyDBHelper extends SQLiteOpenHelper {
    Context context;

    public MyDBHelper(Context context) {
        super(context, "diaryDB", null, 1);
        this.context = context;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE diary (diaryID INTEGER PRIMARY KEY AUTOINCREMENT, diaryEditDate TEXT, diaryPhotoPATH TEXT, diaryTitle TEXT, diaryTEXT TEXT, isFavorite INTEGER)");
//        db.execSQL("CREATE TABLE favorite (favoriteDiaryID INT PRIMARY KEY, FOREIGN KEY(diaryID) REFERENCES diary(diaryID))");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int i1) {
        db.execSQL("DROP TABLE IF EXISTS diary");
        db.execSQL("DROP TABLE IF EXISTS favorite");
        onCreate(db);
    }
}
