package ru.ssau.fiit.tetris;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class GlassAdapter extends RecyclerView.Adapter <GlassAdapter.MyViewHolder> {

    private List<Glass> glasses;

    public GlassAdapter(List <Glass> glasses) {
        this.glasses = glasses;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = new GlassView(parent.getContext());
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
            glassView = (GlassView) itemView;
        }

        public GlassView getGlassView() {
            return glassView;
        }
    }
}
