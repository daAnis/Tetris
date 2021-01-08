package ru.ssau.fiit.tetris;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class GlassAdapter extends RecyclerView.Adapter <GlassAdapter.MyViewHolder> {

    private LayoutInflater inflater;
    private List<Glass> glasses;

    public GlassAdapter(Context context, List <Glass> glasses) {
        this.glasses = glasses;
        this.inflater = LayoutInflater.from(context);
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.glass_item, parent, false);
        return new GlassAdapter.MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        holder.getGlassView().setGlass(glasses.get(position));
    }

    @Override
    public int getItemCount() { return glasses.size(); }

    public static class MyViewHolder extends RecyclerView.ViewHolder {
        private GlassView glassView;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            glassView = itemView.findViewById(R.id.glass_item);
        }

        public GlassView getGlassView() {
            return glassView;
        }
    }
}
