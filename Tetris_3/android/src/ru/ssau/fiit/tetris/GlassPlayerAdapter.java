package ru.ssau.fiit.tetris;

import android.content.Context;
import android.view.View;

import androidx.annotation.NonNull;

import java.util.List;

public class GlassPlayerAdapter extends GlassAdapter {
    private int selectedGlass = -1;

    public GlassPlayerAdapter(Context context, List<Glass> glasses, OnGlassListener onGlassListener) {
        super(context, glasses, onGlassListener);
    }

    @Override
    public void onBindViewHolder(@NonNull final MyViewHolder holder, final int position) {
        super.onBindViewHolder(holder, position);
        //событие выбора стакана
        holder.getView().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                selectedGlass = position;
                notifyDataSetChanged();
                holder.getOnGlassListener().onGlassClick(position);
            }
        });
        if (selectedGlass == position) {
            holder.getView().setBackgroundResource(R.drawable.selected_item);
        } else {
            holder.getView().setBackgroundResource(R.drawable.edit_text_style);
        }
    }
}
