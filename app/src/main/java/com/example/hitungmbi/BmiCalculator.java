package com.example.hitungmbi;

public class BmiCalculator {
    // Class kecil untuk menampung informasi BMI


    public static BmiInfo getBmiInfo(double bmi) {
        if (bmi < 18.5) {
            return new BmiInfo(
                    "Kurus",
                    "Berat Badan Kurang",
                    "Berat badanmu berada di bawah rentang normal.",
                    "Cobalah untuk meningkatkan asupan kalori dengan makanan bergizi seimbang dan konsultasikan ke tenaga kesehatan bila perlu."
            );
        } else if (bmi < 25) {
            return new BmiInfo(
                    "Normal",
                    "Berat Badan Normal",
                    "Berat badanmu berada pada rentang sehat.",
                    "Pertahankan pola makan seimbang, rutin berolahraga, cukup tidur, dan kelola stres agar kesehatan tetap terjaga."
            );
        } else if (bmi < 30) {
            return new BmiInfo(
                    "Berlebih",
                    "Berat Badan Berlebih",
                    "Berat badanmu sedikit di atas rentang normal.",
                    "Mulailah mengatur pola makan, kurangi makanan tinggi gula dan lemak jenuh, serta tambahkan aktivitas fisik ringan-sedang."
            );
        } else {
            return new BmiInfo(
                    "Obesitas",
                    "Obesitas",
                    "Berat badanmu sudah berada pada kategori obesitas.",
                    "Disarankan berkonsultasi dengan dokter atau ahli gizi untuk rencana penurunan berat badan yang aman dan bertahap."
            );
        }
    }

    public static double calculateBmi(double weightKg, double heightCm) {
        if (weightKg <= 0 || heightCm <= 0) {
            throw new IllegalArgumentException("Berat dan tinggi harus > 0");
        }

        double heightMeter = heightCm / 100.0;
        return weightKg / (heightMeter * heightMeter);
    }
}
