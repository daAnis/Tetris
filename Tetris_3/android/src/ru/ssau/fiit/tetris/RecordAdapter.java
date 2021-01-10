package ru.ssau.fiit.tetris;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class RecordAdapter extends RecyclerView.Adapter<RecordAdapter.MyViewHolder> {

    private LayoutInflater inflater;
    private List <Record> records;

    public RecordAdapter (Context context, List <Record> records) {
        this.records = records;
        this.inflater = LayoutInflater.from(context);
    }

    @NonNull
    @Override
    public RecordAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.record_item, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecordAdapter.MyViewHolder holder, int position) {
        Record record = records.get(position);
        holder.getPointsView().setText(record.getPoints());
        holder.getTimeView().setText(record.getTime());
    }

    @Override
    public int getItemCount() {
        return records.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder {
        private TextView pointsView, timeView;

        public MyViewHolder (View view) {
            super(view);
            pointsView = view.findViewById(R.id.points);
            timeView = view.findViewById(R.id.time);
        }

        public TextView getPointsView() { return pointsView; }

        public TextView getTimeView() { return timeView; }
    }
}
