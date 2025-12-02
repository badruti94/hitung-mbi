package com.example.hitungmbi;

import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThrows;

public class BmiCalculatorTest {
    @Test
    public void calculateBmi_normalValue_isCorrect() {
        // tinggi 170 cm, berat 65 kg → BMI kira-kira 22.49
        double bmi = BmiCalculator.calculateBmi(65.0, 170.0);
        assertEquals(22.49, bmi, 0.01); // delta 0.01 untuk toleransi pembulatan
    }

    @Test
    public void calculateBmi_smallHeight_isCorrect() {
        // tinggi 150 cm, berat 45 kg → BMI kira-kira 20.00
        double bmi = BmiCalculator.calculateBmi(45.0, 150.0);
        assertEquals(20.00, bmi, 0.01);
    }
}
