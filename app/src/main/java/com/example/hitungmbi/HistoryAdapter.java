package com.example.hitungmbi;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.hitungmbi.data.BmiRecord;

import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class HistoryAdapter extends RecyclerView.Adapter<HistoryAdapter.VH> {

    private final List<BmiRecord> items = new ArrayList<>();
    private final DecimalFormat bmiFormat = new DecimalFormat("0.0");
    private final SimpleDateFormat dateFormat = new SimpleDateFormat("dd MMM yyyy HH:mm", new Locale("id", "ID"));

    public void submit(List<BmiRecord> newItems) {
        items.clear();
        if (newItems != null) items.addAll(newItems);
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public VH onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_history, parent, false);
        return new VH(v);
    }

    @Override
    public void onBindViewHolder(@NonNull VH h, int position) {
        BmiRecord r = items.get(position);

        String gender = (r.gender == null) ? "" : r.gender;
        BmiInfo info;
        try {
            info = BmiCalculator.getBmiInfo(r.bmi, gender);
        } catch (Exception e) {
            info = new BmiInfo("-", "-", "", "");
        }

        h.textBmi.setText("BMI: " + bmiFormat.format(r.bmi) + " (" + info.category + ")");
        h.textMeta.setText("TB " + r.heightCm + " cm - BB " + r.weightKg + " kg - " + gender);
        h.textDate.setText(dateFormat.format(new Date(r.createdAtMillis)));
        h.textSync.setText(r.synced ? "Synced" : "Pending");
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    static class VH extends RecyclerView.ViewHolder {
        TextView textBmi, textMeta, textDate, textSync;

        VH(@NonNull View itemView) {
            super(itemView);
            textBmi = itemView.findViewById(R.id.textItemBmi);
            textMeta = itemView.findViewById(R.id.textItemMeta);
            textDate = itemView.findViewById(R.id.textItemDate);
            textSync = itemView.findViewById(R.id.textItemSync);
        }
    }
}
