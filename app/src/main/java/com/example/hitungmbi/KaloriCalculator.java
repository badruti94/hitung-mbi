package com.example.hitungmbi;

public class KaloriCalculator {
    // Faktor Aktivitas:
    // 0 = Sangat ringan
    // 1 = Ringan
    // 2 = Sedang
    // 3 = Berat
    // 4 = Sangat Berat
    public static double hitungBMR(double weight, double height, int age, boolean isMale) {
        if (isMale) {
            return (10 * weight) + (6.25 * height) - (5 * age) + 5;
        } else {
            return (10 * weight) + (6.25 * height) - (5 * age) - 161;
        }
    }
    public static double hitungTDEE(double bmr, int index) {
        double factor;
        switch (index) {
            case 0:
                factor = 1.2;   // sangat ringan
                break;
            case 1:
                factor = 1.375; // ringan
                break;
            case 2:
                factor = 1.55;  // sedang
                break;
            case 3:
                factor = 1.725; // berat
                break;
            case 4:
                factor = 1.9;   // sangat berat
                break;
            default:
                factor = 1.55;
        }
        return bmr * factor;
    }

    public static String getActivityLabel(int index) {
        switch (index) {
            case 0:
                return "Sangat Ringan (jarang olahraga)";
            case 1:
                return "Ringan (1–2x seminggu)";
            case 2:
                return "Sedang (3–5x seminggu)";
            case 3:
                return "Berat (6–7x seminggu)";
            case 4:
                return "Sangat Berat (atlet)";
            default:
                return "Tidak diketahui";
        }
    }
}
