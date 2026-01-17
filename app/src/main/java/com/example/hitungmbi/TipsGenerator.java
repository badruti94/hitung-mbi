package com.example.hitungmbi;

import android.widget.TextView;

import java.util.Random;

public class TipsGenerator {

    public static final String[] tipsList = new String[]{
            "Minum air putih minimal 8 gelas sehari.",
            "Usahakan tidur 7–9 jam setiap malam.",
            "Kurangi minuman manis dan perbanyak makan buah.",
            "Lakukan olahraga ringan 20–30 menit setiap hari.",
            "Jangan skip sarapan, pilih menu yang tinggi protein.",
            "Batasi makanan cepat saji dan gorengan.",
            "Luangkan waktu untuk stretching setelah duduk lama.",
            "Kelola stres dengan meditasi atau hobi yang kamu suka.",
            "Hindari merokok dan minuman beralkohol.",
            "Periksa kesehatan secara rutin bila diperlukan."
    };

    private static final Random random = new Random();


    public static void setRandomTip(TextView textViewTips) {
        int index = random.nextInt(tipsList.length);
        textViewTips.setText(getRandomTip());
    }

    public static String getRandomTip() {
        int index = random.nextInt(tipsList.length);
        return tipsList[index];
    }
}
