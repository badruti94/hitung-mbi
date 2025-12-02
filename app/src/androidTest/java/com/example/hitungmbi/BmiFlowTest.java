package com.example.hitungmbi;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.Espresso.pressBack;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.action.ViewActions.closeSoftKeyboard;
import static androidx.test.espresso.action.ViewActions.typeText;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.Matchers.not;
import static org.hamcrest.Matchers.isEmptyOrNullString;
import static org.hamcrest.Matchers.containsString;

import android.view.View;
import android.widget.TextView;

import androidx.test.espresso.UiController;
import androidx.test.espresso.ViewAction;
import androidx.test.ext.junit.rules.ActivityScenarioRule;
import androidx.test.ext.junit.runners.AndroidJUnit4;

import org.hamcrest.Matcher;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

@RunWith(AndroidJUnit4.class)
public class BmiFlowTest {

    @Rule
    public ActivityScenarioRule<MainActivity> activityRule =
            new ActivityScenarioRule<>(MainActivity.class);

    @Test
    public void cekAlurHitungBmi_dariMain_sampaiKembaliKeMain() {

        // 1. Dari MainActivity, klik layoutBmi
        onView(withId(R.id.layoutBmi))
                .check(matches(isDisplayed()))
                .perform(click());

        // 2. Sekarang di BMI Input Activity: isi tinggi & berat
        onView(withId(R.id.editTextHeight))
                .check(matches(isDisplayed()))
                .perform(typeText("170"), closeSoftKeyboard());

        onView(withId(R.id.editTextWeight))
                .check(matches(isDisplayed()))
                .perform(typeText("65"), closeSoftKeyboard());

        // 3. Klik buttonHitung -> pindah ke BMI Result Activity
        onView(withId(R.id.buttonHitung))
                .perform(click());

        // 4. Cek textViewBmiValue dan textViewBmiCategory tampil
        onView(withId(R.id.textViewBmiValue))
                .check(matches(isDisplayed()))
                .check(matches(withText("22,49")));
        // Kalau kamu mau test nilai tertentu, bisa pakai:
        // .check(matches(withText("22.5")))  // sesuaikan formatmu

        onView(withId(R.id.textViewBmiCategory))
                .check(matches(isDisplayed()))
                // misal kategorinya "Normal", atau mengandung kata Normal
                .check(matches(withText(containsString("Normal"))));

        // 5. Klik buttonHitungKembali -> kembali ke BMI Input Activity
        onView(withId(R.id.buttonHitungKembali))
                .check(matches(isDisplayed()))
                .perform(click());

        // 6. Di activity ini (BMI Input), tekan tombol back
        onView(withId(R.id.editTextHeight))
                .check(matches(isDisplayed())); // memastikan memang sudah di BMI Input lagi

        pressBack();

        // 7. Pastikan kembali ke MainActivity (layoutBmi muncul lagi)
        onView(withId(R.id.layoutBmi))
                .check(matches(isDisplayed()));


    }

    @Test
    public void cekAlurKalkulatorKalori_dariMain_sampaiKembaliKeMain() {

        // 1. Dari MainActivity, klik layoutKalori
        onView(withId(R.id.layoutKalori))
                .check(matches(isDisplayed()))
                .perform(click());

        // 2. Di CalorieInputActivity: isi usia, tinggi, berat
        onView(withId(R.id.editTextAge))
                .check(matches(isDisplayed()))
                .perform(typeText("20"), closeSoftKeyboard());

        onView(withId(R.id.editTextHeightKalori))
                .check(matches(isDisplayed()))
                .perform(typeText("170"), closeSoftKeyboard());

        onView(withId(R.id.editTextWeightKalori))
                .check(matches(isDisplayed()))
                .perform(typeText("65"), closeSoftKeyboard());

        // 3. Klik buttonHitungKalori → pindah ke CalorieResultActivity
        onView(withId(R.id.buttonHitungKalori))
                .perform(click());

        // 4. Cek nilai kalori tampil
        onView(withId(R.id.tvCalorieValue))
                .check(matches(isDisplayed()))
                .check(matches(withText("1941")));

        // 5. Klik tombol "Hitung Kembali"
        onView(withId(R.id.btnHitungKaloriLagi))
                .check(matches(isDisplayed()))
                .perform(click());

        // 6. Pastikan kembali di CalorieInputActivity
        onView(withId(R.id.editTextAge))
                .check(matches(isDisplayed()));

        // 7. Tekan tombol back → kembali ke MainActivity
        pressBack();

        // 8. Pastikan MainActivity terlihat lagi
        onView(withId(R.id.layoutKalori))
                .check(matches(isDisplayed()));
    }



    @Test
    public void cekTipsBerubah_setelahDiklik() {

        // 1. Dari MainActivity → klik layoutTips
        onView(withId(R.id.layoutTips))
                .check(matches(isDisplayed()))
                .perform(click());

        // 2. Pastikan di TipsActivity, textView7 muncul
        onView(withId(R.id.textView7))
                .check(matches(isDisplayed()));

        // 3. Simpan teks sebelum perubahan
        final String[] before = { null };
        onView(withId(R.id.textView7))
                .perform(new ViewAction() {
                    @Override
                    public Matcher<View> getConstraints() {
                        // hanya untuk TextView
                        return isDisplayed();
                    }

                    @Override
                    public String getDescription() {
                        return "Ambil teks dari textView7";
                    }

                    @Override
                    public void perform(UiController uiController, View view) {
                        before[0] = ((TextView) view).getText().toString();
                    }
                });

        // 4. Klik layoutTips → generate tips baru
        onView(withId(R.id.layoutTips))
                .perform(click());

        // 5. Pastikan textView7 berubah (tidak sama dengan teks sebelumnya)
        onView(withId(R.id.textView7))
                .check(matches(not(withText(before[0]))));

        // 6. Back → kembali ke MainActivity
        pressBack();

        onView(withId(R.id.layoutTips))
                .check(matches(isDisplayed()));
    }
}
