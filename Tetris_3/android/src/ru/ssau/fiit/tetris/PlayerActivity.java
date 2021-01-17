package ru.ssau.fiit.tetris;

import androidx.annotation.NonNull;
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

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.concurrent.TimeUnit;

import ru.ssau.fiit.tetris.db.DBHelper;

public class PlayerActivity extends AppCompatActivity {
    private TextView userName;
    private static int score = -1;
    private static long time;
    private ArrayList <Record> records = new ArrayList<>();
    private User user;

    private RecyclerView recyclerView;
    private DatabaseReference databaseReference;

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
        recyclerView = findViewById(R.id.records);
        databaseReference = FirebaseDatabase.getInstance().getReference("records");
    }

    @Override
    protected void onStart() {
        super.onStart();
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                records.clear();
                for (DataSnapshot snap : snapshot.getChildren()) {
                    if (snap.child("userId").getValue(String.class).equals(user.getUsername())) {
                        Record record = snap.getValue(Record.class);
                        records.add(record);
                    }
                }
                RecordAdapter adapter = new RecordAdapter(PlayerActivity.this, records);
                recyclerView.setAdapter(adapter);
                RecyclerView.LayoutManager manager = new LinearLayoutManager(PlayerActivity.this);
                recyclerView.setLayoutManager(manager);
                recyclerView.setHasFixedSize(false);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
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
            DBHelper.addRecordToDataBase(new Record(user.getUsername(), score + "",
                    String.format(" %02d : %02d ",
                            TimeUnit.MILLISECONDS.toMinutes(time),
                            TimeUnit.MILLISECONDS.toSeconds(time) -
                                    TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.toMinutes(time)))));
        score = -1;
    }

    //назад
    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }
}