package ru.ssau.fiit.tetris;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class GlassAdapter extends RecyclerView.Adapter <GlassAdapter.MyViewHolder> {

    protected LayoutInflater inflater;
    protected List<Glass> glasses;
    private OnGlassListener onGlassListener;

    public GlassAdapter(Context context, List <Glass> glasses, OnGlassListener onGlassListener) {
        this.glasses = glasses;
        this.inflater = LayoutInflater.from(context);
        this.onGlassListener = onGlassListener;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.glass_item, parent, false);
        return new GlassAdapter.MyViewHolder(view, onGlassListener);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        holder.getGlassView().setGlass(glasses.get(position));
    }

    @Override
    public int getItemCount() { return glasses.size(); }

    public static class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        private GlassView glassView;
        private OnGlassListener onGlassListener;
        private View view;

        public MyViewHolder(@NonNull View itemView, OnGlassListener onGlassListener) {
            super(itemView);
            view = itemView;
            glassView = itemView.findViewById(R.id.glass_item);
            this.onGlassListener = onGlassListener;
            itemView.setOnClickListener(this);
        }

        public GlassView getGlassView() { return glassView; }
        public OnGlassListener getOnGlassListener() { return onGlassListener; }
        public View getView() { return view; }

        @Override
        public void onClick(View view) {
            onGlassListener.onGlassClick(getAdapterPosition());
        }
    }

    public interface OnGlassListener {
        void onGlassClick(int position);
    }
}
