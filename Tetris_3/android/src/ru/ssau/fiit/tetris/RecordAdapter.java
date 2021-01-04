package ru.ssau.fiit.tetris;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class RecordAdapter extends RecyclerView.Adapter<RecordAdapter.ViewHolder> {

    private final LayoutInflater inflater;
    private final List <Record> records;

    RecordAdapter (Context context, List <Record> records) {
        this.records = records;
        this.inflater = LayoutInflater.from(context);
    }

    @NonNull
    @Override
    public RecordAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.record_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecordAdapter.ViewHolder holder, int position) {
        Record record = records.get(position);
        holder.pointsView.setText(record.getPoints());
        holder.timeView.setText(record.getTime());
    }

    @Override
    public int getItemCount() {
        return records.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        final TextView pointsView, timeView;
        ViewHolder (View view) {
            super(view);
            pointsView = view.findViewById(R.id.points);
            timeView = view.findViewById(R.id.time);
        }
    }
}
