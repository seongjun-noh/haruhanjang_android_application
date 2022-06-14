package com.example.haruhanjang;

public class DiaryData {
    int diaryID, isFavorite;
    String diaryEditDate, imagePath, photoTitle, diaryText;

    public DiaryData(int diaryID, String diaryEditDate, String imagePath, String photoTitle, String diaryText, int isFavorite) {
        this.diaryID = diaryID;
        this.diaryEditDate = diaryEditDate;
        this.imagePath = imagePath;
        this.photoTitle = photoTitle;
        this.diaryText = diaryText;
        this.isFavorite = isFavorite;
    }
}
