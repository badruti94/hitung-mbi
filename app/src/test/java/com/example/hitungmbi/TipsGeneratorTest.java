package com.example.hitungmbi;

import org.junit.Test;

import static org.junit.Assert.assertTrue;
import static org.junit.Assert.assertNotNull;

public class TipsGeneratorTest {

    @Test
    public void getRandomTip_notNull() {
        String tip = TipsGenerator.getRandomTip();
        assertNotNull(tip);
    }

    @Test
    public void getRandomTip_isInList() {
        String tip = TipsGenerator.getRandomTip();

        boolean found = false;
        for (String item : TipsGenerator.tipsList) {
            if (item.equals(tip)) {
                found = true;
                break;
            }
        }

        assertTrue("Tip tidak ditemukan di daftar tips!", found);
    }

    @Test
    public void getRandomTip_multipleCallsReturnValidValues() {
        // test dipanggil 50 kali untuk memastikan semua valid
        for (int i = 0; i < 50; i++) {
            String tip = TipsGenerator.getRandomTip();
            boolean found = false;

            for (String item : TipsGenerator.tipsList) {
                if (item.equals(tip)) {
                    found = true;
                    break;
                }
            }

            assertTrue("Ada tip yang bukan dari list!", found);
        }
    }
}
