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
    private List<FigureArray> figureArrays;
    private OnFigureListener onFigureListener;

    public FigureAdapter(Context context, List<FigureArray> figureArrays, OnFigureListener onFigureListener) {
        this.figureArrays = figureArrays;
        this.inflater = LayoutInflater.from(context);
        this.onFigureListener = onFigureListener;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.figure_item, parent, false);
        return new FigureAdapter.MyViewHolder(view, onFigureListener);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        holder.getFigureView().setFigure(figureArrays.get(position));
    }

    @Override
    public int getItemCount() { return figureArrays.size(); }

    public static class MyViewHolder extends RecyclerView.ViewHolder  implements View.OnClickListener {
        private FigureView figureView;
        private OnFigureListener onFigureListener;

        public MyViewHolder(@NonNull View itemView, OnFigureListener onFigureListener) {
            super(itemView);
            figureView = itemView.findViewById(R.id.figure_item);
            this.onFigureListener = onFigureListener;
            itemView.setOnClickListener(this);
        }

        public FigureView getFigureView() {
            return figureView;
        }

        @Override
        public void onClick(View view) {
            onFigureListener.onFigureClick(getAdapterPosition());
        }
    }

    public interface OnFigureListener {
        void onFigureClick(int position);
    }
}
