package ru.ssau.fiit.tetris;

import android.content.Context;
import android.media.MediaPlayer;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class AudioAdapter extends RecyclerView.Adapter<AudioAdapter.MyViewHolder> {
    private LayoutInflater inflater;
    private List<Audio> audioList;
    private OnAudioDeleteListener onAudioDeleteListener;

    public AudioAdapter(Context context, List<Audio> audioList, OnAudioDeleteListener onAudioDeleteListener) {
        this.audioList = audioList;
        this.inflater = LayoutInflater.from(context);
        this.onAudioDeleteListener = onAudioDeleteListener;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.audio_item, parent, false);
        return new MyViewHolder(view, onAudioDeleteListener);
    }

    @Override
    public void onBindViewHolder(@NonNull final MyViewHolder holder, final int position) {
        Audio audio = audioList.get(position);
        holder.getNameView().setText(audio.getName());

        final MediaPlayer mediaPlayer = MediaPlayer.create(inflater.getContext(), R.raw.test);
        final Handler handler = new Handler();
        final Runnable runnable = new Runnable() {
            @Override
            public void run() {
                holder.getSeekView().setProgress(mediaPlayer.getCurrentPosition());
                handler.postDelayed(this, 500);
            }
        };

        holder.getPlayView().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                holder.getPlayView().setVisibility(View.GONE);
                holder.getPauseView().setVisibility(View.VISIBLE);
                mediaPlayer.start();
                holder.getSeekView().setMax(mediaPlayer.getDuration());
                handler.postDelayed(runnable, 0);
            }
        });

        holder.getPauseView().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                holder.getPauseView().setVisibility(View.GONE);
                holder.getPlayView().setVisibility(View.VISIBLE);
                mediaPlayer.pause();
                handler.removeCallbacks(runnable);
            }
        });

        holder.getSeekView().setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                if (fromUser) {
                    mediaPlayer.seekTo(progress);
                }
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) { }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) { }
        });

        mediaPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
            @Override
            public void onCompletion(MediaPlayer mediaPlayer) {
                holder.getPauseView().setVisibility(View.GONE);
                holder.getPlayView().setVisibility(View.VISIBLE);
                mediaPlayer.seekTo(0);
            }
        });
    }

    @Override
    public int getItemCount() { return audioList.size(); }

    public static class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        private TextView nameView;
        private ImageView playView, pauseView, deleteView;
        private SeekBar seekView;
        private OnAudioDeleteListener onAudioDeleteListener;

        public MyViewHolder(@NonNull View itemView, final OnAudioDeleteListener onAudioDeleteListener) {
            super(itemView);
            nameView = itemView.findViewById(R.id.audio_name);
            playView = itemView.findViewById(R.id.audio_play);
            pauseView = itemView.findViewById(R.id.audio_pause);
            deleteView = itemView.findViewById(R.id.audio_delete);
            seekView = itemView.findViewById(R.id.audio_seek);

            //удаление аудио
            this.onAudioDeleteListener = onAudioDeleteListener;
            deleteView.setOnClickListener(this);
        }

        public TextView getNameView() { return nameView; }

        public ImageView getPlayView() { return playView; }

        public ImageView getPauseView() { return pauseView; }

        public SeekBar getSeekView() { return seekView; }

        @Override
        public void onClick(View view) { onAudioDeleteListener.onAudioDelete(getAdapterPosition()); }
    }

    public interface OnAudioDeleteListener {
        void onAudioDelete(int position);
    }
}
