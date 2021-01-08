package ru.ssau.fiit.tetris;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class FigureAdapter extends RecyclerView.Adapter<FigureAdapter.MyViewHolder> {
    private LayoutInflater inflater;
    private List<Figure> figures;

    public FigureAdapter(Context context, List<Figure> figures) {
        this.figures = figures;
        this.inflater = LayoutInflater.from(context);
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.figure_item, parent, false);
        return new FigureAdapter.MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        holder.getFigureView().setFigure(figures.get(position));
    }

    @Override
    public int getItemCount() { return figures.size(); }

    public static class MyViewHolder extends RecyclerView.ViewHolder {
        private FigureView figureView;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            figureView = itemView.findViewById(R.id.figure_item);
        }

        public FigureView getFigureView() {
            return figureView;
        }
    }
}
