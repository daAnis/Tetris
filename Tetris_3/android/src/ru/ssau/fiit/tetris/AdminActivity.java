package ru.ssau.fiit.tetris;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;

import java.util.ArrayList;

public class AdminActivity extends AppCompatActivity
        implements FigureAdapter.OnFigureListener, GlassAdapter.OnGlassListener, AudioAdapter.OnAudioListener {

    private static ArrayList<Glass> glasses = new ArrayList<>();
    private static GlassAdapter glassAdapter;

    private static ArrayList<Figure> figures = new ArrayList<>();
    private static FigureAdapter figureAdapter;

    private static ArrayList<Audio> audios = new ArrayList<>();
    private static AudioAdapter audioAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin);

        glasses.add(new Glass(15, 25, Color.BLACK, 0.1, 0.1));
        glasses.add(new Glass(5, 5, Color.BLUE, 0.1, 0.1));
        figures.add(new Figure(5, new byte[][]{{0,0,0,0},{0,1,1,0},{0,1,0,0},{0,0,0,0}}));

        //отобразить список стаканов
        RecyclerView glassList = findViewById(R.id.glass_list);
        glassAdapter = new GlassAdapter(this, glasses, this);
        glassList.setAdapter(glassAdapter);
        RecyclerView.LayoutManager manager = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
        glassList.setLayoutManager(manager);
        glassList.setHasFixedSize(false);

        //отобразить список фигур
        RecyclerView figuresList = findViewById(R.id.figure_list);
        figureAdapter = new FigureAdapter(this, figures, this);
        figuresList.setAdapter(figureAdapter);
        RecyclerView.LayoutManager manager1 = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
        figuresList.setLayoutManager(manager1);
        figuresList.setHasFixedSize(false);

        //отобразить список аудио
        RecyclerView audioList = findViewById(R.id.music_list);

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
        glassAdapter.notifyDataSetChanged();
    }

    public static void deleteGlass(Glass glass) {
        //todo удаление стакана из бд
        for (Glass g : glasses) {
            if (glass.equals(g)) {
                glasses.remove(g);
                break;
            }
        }
        glassAdapter.notifyDataSetChanged();
    }

    public static void saveFigure(Figure figure) {
        //todo передача фигуры в бд
        figures.add(figure);
        figureAdapter.notifyDataSetChanged();
    }

    public static void deleteFigure(Figure figure) {
        //todo удаление фигуры из бд
        for (Figure f : figures) {
            if (figure.equals(f)) {
                figures.remove(f);
                break;
            }
        }
        figureAdapter.notifyDataSetChanged();
    }

    public static void deleteAudio(Audio audio) {
        //todo удаление аудио из бд
        for (Audio a : audios) {
            if (audio.equals(a)) {
                audios.remove(a);
                break;
            }
        }
        audioAdapter.notifyDataSetChanged();
    }

    //событие нажатия на фигуру
    @Override
    public void onFigureClick(int position) {
        Intent intent = new Intent(this, FigureActivity.class);
        intent.putExtra(Figure.class.getSimpleName(), figures.get(position));
        startActivity(intent);
    }

    //событие нажатия на стакан
    @Override
    public void onGlassClick(int position) {
        Intent intent = new Intent(this, GlassActivity.class);
        intent.putExtra(Glass.class.getSimpleName(), glasses.get(position));
        startActivity(intent);
    }

    @Override
    public void onAudioPlay(int position) {

    }

    @Override
    public void onAudioDelete(int position) {

    }
}