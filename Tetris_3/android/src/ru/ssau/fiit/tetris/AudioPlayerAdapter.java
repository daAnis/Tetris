package ru.ssau.fiit.tetris;

import android.content.Context;
import android.view.View;

import androidx.annotation.NonNull;

import java.util.List;

public class AudioPlayerAdapter extends AudioAdapter {
    public AudioPlayerAdapter(Context context, List<Audio> audioList, OnAudioListener onAudioListener) {
        super(context, audioList, onAudioListener);
    }

    @Override
    public void onBindViewHolder(@NonNull final MyViewHolder holder, final int position) {
        super.onBindViewHolder(holder, position);

        holder.getDeleteView().setVisibility(View.GONE);
        holder.getView().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(holder.getView().getBackground() != inflater.getContext().getResources().getDrawable(R.drawable.selected_item)) {
                    holder.getView().setBackground(inflater.getContext().getResources().getDrawable(R.drawable.selected_item));
                }  else {
                    holder.getView().setBackground(null);
                }
                holder.getOnAudioListener().onAudioClick(position);
            }
        });
    }
}
