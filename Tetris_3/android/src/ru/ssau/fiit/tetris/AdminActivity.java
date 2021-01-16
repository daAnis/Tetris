package ru.ssau.fiit.tetris;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.database.Cursor;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

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

        /*glasses.add(new Glass(15, 25, Color.BLACK, 0.1, 0.1));
        glasses.add(new Glass(5, 5, Color.BLUE, 0.1, 0.1));
        figures.add(new Figure(5, new byte[][]{{0,0,0,0},{0,1,1,0},{0,1,0,0},{0,0,0,0}}));
        audios.add(new Audio("Звуки природы", getRawUri("test")));*/

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
        audioAdapter = new AudioAdapter(this, audios, this);
        audioList.setAdapter(audioAdapter);
        RecyclerView.LayoutManager manager2 = new LinearLayoutManager(this);
        audioList.setLayoutManager(manager2);
        audioList.setHasFixedSize(false);

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

        //добавить аудио
        ImageView addAudio = findViewById(R.id.iw2);
        addAudio.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
                intent.setType("audio/*");
                startActivityForResult(intent, 3);
            }
        });
    }

    //назад
    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }

    //возврат из системы устройства в приложение
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {
            case 3: {
                if (resultCode == RESULT_OK)
                {
                    final Cursor cursor = getContentResolver().query( data.getData(), null, null, null, null );
                    cursor.moveToFirst();
                    final String fileName = cursor.getString(2);
                    cursor.close();
                    saveAudio(new Audio(fileName, data.getData()));
                }
                break;
            }
        }
    }

    //метод для получения Uri из файлов ресурсов
    private Uri getRawUri(String filename) {
        return Uri.parse("android.resource://" + getPackageName() + "/raw/" + filename);
    }

    public static void saveGlass(Glass glass) throws Exception {
        //todo передача стакана в бд
        for (Glass g : glasses) {
            if (glass.equals(g)) {
                throw new Exception("Стакан не уникален!");
            }
        }
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

    public static void saveFigure(Figure figure) throws Exception {
        //todo передача фигуры в бд
        for (Figure f : figures) {
            if (figure.equals(f)) {
                throw new Exception("Фигура не уникальна!");
            }
        }
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

    public static void saveAudio(Audio audio) {
        //todo добавление аудио в бд
        audios.add(audio);
        audioAdapter.notifyDataSetChanged();
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

    //событие удаления аудио
    @Override
    public void onAudioDelete(int position) {
        AdminActivity.deleteAudio(audios.get(position));
    }

    @Override
    public void onAudioClick(int position) { }
}