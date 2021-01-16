package ru.ssau.fiit.tetris;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.concurrent.TimeUnit;

public class PlayerActivity extends AppCompatActivity {
    private TextView userName;
    private static int score = -1;
    private static long time;
    private ArrayList <Record> records = new ArrayList<>();
    private User user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_player);

        //получить имя игрока
        userName = findViewById(R.id.user_name);
        Bundle arguments = getIntent().getExtras();
        if (arguments != null) {
            user = (User) arguments.getSerializable(User.class.getSimpleName());
            userName.setText(user.getUsername());
        }

        //насторойки
        TextView setTW = findViewById(R.id.settings);
        setTW.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(PlayerActivity.this, SettingsActivity.class));
            }
        });

        //начать новую игру
        TextView textView = findViewById(R.id.tw);
        textView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(PlayerActivity.this, GameStartActivity.class));
            }
        });

        ImageView imageView = findViewById(R.id.iw);
        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(PlayerActivity.this, GameStartActivity.class));
            }
        });

        //справка
        ImageButton infoButton = findViewById(R.id.info_button);
        infoButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(PlayerActivity.this, WebViewActivity.class));
            }
        });

        //отобразить список результатов
        RecyclerView recyclerView = findViewById(R.id.records);
        RecordAdapter adapter = new RecordAdapter(this, records);
        recyclerView.setAdapter(adapter);
        RecyclerView.LayoutManager manager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(manager);
        recyclerView.setHasFixedSize(false);
    }

    //получить текущий результат
    public static void setScore(int score, long time) {
        PlayerActivity.score = score;
        PlayerActivity.time = time;
    }

    //возвращаемся в меню после игры
    @SuppressLint("DefaultLocale")
    @Override
    protected void onResume() {
        super.onResume();
        if (score != -1)
            records.add(new Record(score + "",
                    String.format(" %02d : %02d ",
                            TimeUnit.MILLISECONDS.toMinutes(time),
                            TimeUnit.MILLISECONDS.toSeconds(time) -
                                    TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.toMinutes(time)))));
    }

    //назад
    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }
}