package ru.ssau.fiit.tetris;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.webkit.WebView;
import android.widget.ImageButton;
import android.widget.TextView;

import java.util.ArrayList;

public class PlayerActivity extends AppCompatActivity {
    private TextView userName;
    private static int score = -1;
    private ArrayList <Record> records = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_player);

        //получить имя игрока
        userName = findViewById(R.id.user_name);

        Bundle arguments = getIntent().getExtras();
        if (arguments != null) {
            String result = arguments.get("username").toString();
            userName.setText(result);
        }

        //начать новую игру
        TextView textView = findViewById(R.id.tw);
        textView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(PlayerActivity.this, AndroidLauncher.class));
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

    public static void setScore(int score) {
        PlayerActivity.score = score;
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (score != -1)
            records.add(new Record(score + "", "123"));
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }
}