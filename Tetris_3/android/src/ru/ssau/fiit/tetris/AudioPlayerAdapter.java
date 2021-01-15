package ru.ssau.fiit.tetris;

import android.content.Context;
import android.view.View;

import androidx.annotation.NonNull;

import java.util.List;

public class AudioPlayerAdapter extends AudioAdapter {
    private int selectedAudio = -1;

    public AudioPlayerAdapter(Context context, List<Audio> audioList, OnAudioListener onAudioListener) {
        super(context, audioList, onAudioListener);
    }

    @Override
    public void onBindViewHolder(@NonNull final MyViewHolder holder, final int position) {
        super.onBindViewHolder(holder, position);
        //удаление кнопки удаления аудио
        holder.getDeleteView().setVisibility(View.GONE);
        //удаление управления проигрыванием при отсутствии аудио
        if (audioList.get(position).getUri() == null) {
            holder.getPlayView().setVisibility(View.GONE);
            holder.getSeekView().setVisibility(View.GONE);
        }
        //событие выбора аудио
        holder.getView().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                selectedAudio = position;
                notifyDataSetChanged();
                holder.getOnAudioListener().onAudioClick(position);
            }
        });
        //установка выбранного элементы
        if (selectedAudio == position) {
            holder.getView().setBackgroundResource(R.drawable.selected_item);
        } else {
            holder.getView().setBackgroundResource(R.drawable.edit_text_style);
        }
    }
}
