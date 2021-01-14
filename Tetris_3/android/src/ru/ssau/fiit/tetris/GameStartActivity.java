package ru.ssau.fiit.tetris;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;

import java.util.ArrayList;

public class GameStartActivity extends AppCompatActivity
        implements GlassAdapter.OnGlassListener, AudioAdapter.OnAudioListener {

    private static ArrayList<Glass> glasses = new ArrayList<>();
    private static GlassAdapter glassAdapter;

    private static ArrayList<Audio> audios = new ArrayList<>();
    private static AudioPlayerAdapter audioAdapter;

    private Glass glass;
    private Audio audio;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game_start);

        glasses.add(new Glass(15, 25, Color.BLACK, 0.1, 0.1));
        glasses.add(new Glass(5, 5, Color.BLUE, 0.1, 0.1));
        audios.add(new Audio("Звуки природы"));
        audios.add(new Audio("Звуки моря"));
        audios.add(new Audio("Звуки дождя"));

        //отобразить список стаканов
        RecyclerView glassList = findViewById(R.id.glass_list_player);
        glassAdapter = new GlassAdapter(this, glasses, this);
        glassList.setAdapter(glassAdapter);
        RecyclerView.LayoutManager manager = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
        glassList.setLayoutManager(manager);
        glassList.setHasFixedSize(false);

        //отобразить список аудио
        RecyclerView audioList = findViewById(R.id.music_list_player);
        audioAdapter = new AudioPlayerAdapter(this, audios, this);
        audioList.setAdapter(audioAdapter);
        RecyclerView.LayoutManager manager2 = new LinearLayoutManager(this);
        audioList.setLayoutManager(manager2);
        audioList.setHasFixedSize(false);

        //начать игру
        ImageButton startButton = findViewById(R.id.start_game);
        startButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(GameStartActivity.this, AndroidLauncher.class);
                intent.putExtra(Glass.class.getSimpleName(), glass);
                intent.putExtra(Audio.class.getSimpleName(), audio);
                startActivity(intent);
            }
        });
    }

    @Override
    public void onAudioDelete(int position) { }

    @Override
    public void onAudioClick(int position) {

    }

    @Override
    public void onGlassClick(int position) {

    }
}