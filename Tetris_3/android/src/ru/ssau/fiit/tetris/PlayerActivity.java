package ru.ssau.fiit.tetris;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

public class PlayerActivity extends AppCompatActivity {
    private RecyclerView records;
    private TextView userName;
    private static int score = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_player);

        TextView textView = findViewById(R.id.tw);
        textView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(PlayerActivity.this, AndroidLauncher.class));
                //finish();
            }
        });

        records = findViewById(R.id.records);
        userName = findViewById(R.id.user_name);

        Bundle arguments = getIntent().getExtras();
        if (arguments != null) {
            String result = arguments.get("username").toString();
            userName.setText(result);
        }

    }

    public static void setScore(int score) {
        PlayerActivity.score = score;
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }
}