package ru.ssau.fiit.tetris;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import java.util.ArrayList;

public class AdminActivity extends AppCompatActivity {

    private static ArrayList<Glass> glasses = new ArrayList<>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin);

        //отобразить список стаканов
        RecyclerView glassList = findViewById(R.id.glass_list);
        GlassAdapter glassAdapter = new GlassAdapter(glasses);
        glassList.setAdapter(glassAdapter);
        RecyclerView.LayoutManager manager = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
        glassList.setLayoutManager(manager);
        glassList.setHasFixedSize(false);

        //добавить стакан
        ImageView addGlass = findViewById(R.id.iw);
        addGlass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(AdminActivity.this, GlassActivity.class));
            }
        });

        //добавить фигуру
        ImageView addFigure = findViewById(R.id.iw1);
        addFigure.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(AdminActivity.this, FigureActivity.class));
            }
        });
    }

    //назад
    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }

    public static void saveGlass(Glass glass) {
        //todo передача стакана в бд
        glasses.add(glass);
    }

    public static void deleteGlass(Glass glass) {
        //todo удаление стакана из бд
        glasses.remove(glass);
    }
}