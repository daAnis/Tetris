package ru.ssau.fiit.tetris;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class GameStartActivity extends AppCompatActivity
        implements GlassAdapter.OnGlassListener, AudioAdapter.OnAudioListener {

    private RecyclerView glassList;
    private static ArrayList<Glass> glasses;
    private static GlassPlayerAdapter glassAdapter;

    private RecyclerView audioList;
    private static ArrayList<Audio> audios;
    private static AudioPlayerAdapter audioAdapter;

    private Glass glass;
    private Audio audio;

    private DatabaseReference dataTableGlasses;
    private DatabaseReference dataTableFigures;
    private DatabaseReference dataTableAudios;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game_start);

        glass = null;
        audio = null;

        glasses = new ArrayList<>();
        audios = new ArrayList<>();

        //отобразить список стаканов
        glassList = findViewById(R.id.glass_list_player);
        dataTableGlasses = FirebaseDatabase.getInstance().getReference("glasses");

        //отобразить список аудио
        audioList = findViewById(R.id.music_list_player);
        dataTableAudios = FirebaseDatabase.getInstance().getReference("audios");

        //начать игру
        ImageButton startButton = findViewById(R.id.start_game);
        startButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (glass == null) {
                    Toast.makeText(GameStartActivity.this, "Выберите стакан", Toast.LENGTH_LONG).show();
                    return;
                }
                if (audio == null) {
                    Toast.makeText(GameStartActivity.this, "Выберите аудио", Toast.LENGTH_LONG).show();
                    return;
                }
                Intent intent = new Intent(GameStartActivity.this, AndroidLauncher.class);
                intent.putExtra(Glass.class.getSimpleName(), glass);
                intent.putExtra(Audio.class.getSimpleName(), audio);
                startActivity(intent);
                finish();
            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();
        dataTableGlasses.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                glasses.clear();
                for (DataSnapshot snap : snapshot.getChildren()) {
                    Glass glass = snap.getValue(Glass.class);
                    glasses.add(glass);
                }
                glassAdapter = new GlassPlayerAdapter(GameStartActivity.this, glasses, GameStartActivity.this);
                glassList.setAdapter(glassAdapter);
                RecyclerView.LayoutManager manager = new LinearLayoutManager(GameStartActivity.this, LinearLayoutManager.HORIZONTAL, false);
                glassList.setLayoutManager(manager);
                glassList.setHasFixedSize(false);
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) { }
        });

        dataTableAudios.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                audios.clear();
                for (DataSnapshot snap : snapshot.getChildren()) {
                    Audio audio = snap.getValue(Audio.class);
                    audios.add(audio);
                }
                audioAdapter = new AudioPlayerAdapter(GameStartActivity.this, audios, GameStartActivity.this);
                audioList.setAdapter(audioAdapter);
                RecyclerView.LayoutManager manager2 = new LinearLayoutManager(GameStartActivity.this);
                audioList.setLayoutManager(manager2);
                audioList.setHasFixedSize(false);
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) { }
        });
    }

    @Override
    public void onAudioDelete(int position) { }

    @Override
    public void onAudioClick(int position) {
        audio = audios.get(position);
    }

    @Override
    public void onGlassClick(int position) {
        glass = glasses.get(position);
    }
}