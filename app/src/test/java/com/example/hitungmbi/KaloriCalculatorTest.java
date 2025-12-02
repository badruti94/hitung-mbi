package com.example.hitungmbi;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class KaloriCalculatorTest {

    @Test
    public void hitungBMR_male_isCorrect() {
        // Contoh: berat 70kg, tinggi 170cm, umur 25, laki-laki
        // Rumus: (10 * 70) + (6.25 * 170) - (5 * 25) + 5 = 1642.5
        double bmr = KaloriCalculator.hitungBMR(70, 170, 25, true);
        assertEquals(1642.5, bmr, 0.01);
    }

    @Test
    public void hitungBMR_female_isCorrect() {
        // Contoh sama tapi perempuan
        // Rumus: (10 * 70) + (6.25 * 170) - (5 * 25) - 161 = 1476.5
        double bmr = KaloriCalculator.hitungBMR(70, 170, 25, false);
        assertEquals(1476.5, bmr, 0.01);
    }

    @Test
    public void hitungTDEE_sedang_isCorrect() {
        // Pakai BMR laki-laki di atas: 1642.5
        // Index 2 = faktor 1.55 → 1642.5 * 1.55 = 2545.875
        double tdee = KaloriCalculator.hitungTDEE(1642.5, 2);
        assertEquals(2545.875, tdee, 0.01);
    }

    @Test
    public void hitungTDEE_defaultIndex_useSedangFactor() {
        // Index tidak dikenal (misal 99) harus pakai faktor 1.55 (sedang)
        double bmr = 1600.0;
        double tdeeDefault = KaloriCalculator.hitungTDEE(bmr, 99);
        double tdeeSedang = KaloriCalculator.hitungTDEE(bmr, 2);

        assertEquals(tdeeSedang, tdeeDefault, 0.0);
    }

    @Test
    public void getActivityLabel_isCorrect() {
        assertEquals("Sangat Ringan (jarang olahraga)", KaloriCalculator.getActivityLabel(0));
        assertEquals("Ringan (1–2x seminggu)", KaloriCalculator.getActivityLabel(1));
        assertEquals("Sedang (3–5x seminggu)", KaloriCalculator.getActivityLabel(2));
        assertEquals("Berat (6–7x seminggu)", KaloriCalculator.getActivityLabel(3));
        assertEquals("Sangat Berat (atlet)", KaloriCalculator.getActivityLabel(4));
        assertEquals("Tidak diketahui", KaloriCalculator.getActivityLabel(99));
    }
}