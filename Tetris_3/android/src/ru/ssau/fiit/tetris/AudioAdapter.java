package ru.ssau.fiit.tetris;

import android.content.Context;
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
    private OnAudioListener onAudioListener;

    public AudioAdapter(Context context, List<Audio> audioList, OnAudioListener onAudioListener) {
        this.audioList = audioList;
        this.inflater = LayoutInflater.from(context);
        this.onAudioListener = onAudioListener;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.audio_item, parent, false);
        return new MyViewHolder(view, onAudioListener);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, final int position) {
        Audio audio = audioList.get(position);
        holder.getNameView().setText(audio.getName());

    }

    @Override
    public int getItemCount() { return audioList.size(); }

    public static class MyViewHolder extends RecyclerView.ViewHolder {
        private TextView nameView;
        private ImageView playView, deleteView;
        private SeekBar seekView;
        private OnAudioListener onAudioListener;

        public MyViewHolder(@NonNull View itemView, final OnAudioListener onAudioListener) {
            super(itemView);
            nameView = itemView.findViewById(R.id.audio_name);
            playView = itemView.findViewById(R.id.audio_play);
            deleteView = itemView.findViewById(R.id.audio_delete);
            seekView = itemView.findViewById(R.id.audio_seek);

            this.onAudioListener = onAudioListener;
            playView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    onAudioListener.onAudioPlay(getAdapterPosition());
                }
            });
            deleteView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    onAudioListener.onAudioDelete(getAdapterPosition());
                }
            });
        }

        public TextView getNameView() { return nameView; }
    }

    public interface OnAudioListener {
        void onAudioPlay(int position);
        void onAudioDelete(int position);
    }
}
