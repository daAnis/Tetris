package ru.ssau.fiit.tetris;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

import ru.ssau.fiit.tetris.db.DBHelper;

public class AdminActivity extends AppCompatActivity
        implements FigureAdapter.OnFigureListener, GlassAdapter.OnGlassListener, AudioAdapter.OnAudioListener {

    private RecyclerView glassList;
    private static ArrayList<Glass> glasses = new ArrayList<>();
    private static GlassAdapter glassAdapter;

    private RecyclerView figuresList;
    private static ArrayList<FigureArray> figureArrays = new ArrayList<>();
    private static FigureAdapter figureAdapter;

    private RecyclerView audioList;
    private static ArrayList<Audio> audios = new ArrayList<>();
    private static AudioAdapter audioAdapter;

    private DatabaseReference dataTableGlasses;
    private DatabaseReference dataTableFigures;
    private DatabaseReference dataTableAudios;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin);

        //отобразить список стаканов
        glassList = findViewById(R.id.glass_list);
        dataTableGlasses = FirebaseDatabase.getInstance().getReference("glasses");

        //отобразить список фигур
        figuresList = findViewById(R.id.figure_list);
        dataTableFigures = FirebaseDatabase.getInstance().getReference("figures");

        //отобразить список аудио
        audioList = findViewById(R.id.music_list);
        dataTableAudios = FirebaseDatabase.getInstance().getReference("audios");

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
                glassAdapter = new GlassAdapter(AdminActivity.this, glasses, AdminActivity.this);
                glassList.setAdapter(glassAdapter);
                RecyclerView.LayoutManager manager = new LinearLayoutManager(AdminActivity.this, LinearLayoutManager.HORIZONTAL, false);
                glassList.setLayoutManager(manager);
                glassList.setHasFixedSize(false);
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) { }
        });

        dataTableFigures.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                figureArrays.clear();
                for (DataSnapshot snap : snapshot.getChildren()) {
                    Figure figure = snap.getValue(Figure.class);
                    FigureArray figureArray = new FigureArray(figure.getFigureId(), figure.getLevel(), figure.getStructureString());
                    figureArrays.add(figureArray);
                }
                figureAdapter = new FigureAdapter(AdminActivity.this, figureArrays, AdminActivity.this);
                figuresList.setAdapter(figureAdapter);
                RecyclerView.LayoutManager manager1 = new LinearLayoutManager(AdminActivity.this, LinearLayoutManager.HORIZONTAL, false);
                figuresList.setLayoutManager(manager1);
                figuresList.setHasFixedSize(false);
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
                audioAdapter = new AudioAdapter(AdminActivity.this, audios, AdminActivity.this);
                audioList.setAdapter(audioAdapter);
                RecyclerView.LayoutManager manager2 = new LinearLayoutManager(AdminActivity.this);
                audioList.setLayoutManager(manager2);
                audioList.setHasFixedSize(false);
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) { }
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
                    saveAudio(new Audio(fileName, data.getData().toString()));
                }
                break;
            }
        }
    }

    public static void saveGlass(Glass glass) throws Exception {
        for (Glass g : glasses) {
            if (glass.equals(g)) {
                throw new Exception("Стакан не уникален!");
            }
        }
        DBHelper.addGlassToDataBase(glass);
    }

    public static void deleteGlass(Glass glass) {
        DBHelper.deleteGlassFromDataBase(glass);
    }

    public static void saveFigure(FigureArray figureArray) throws Exception {
        /*int t_n = 0;
        while (t_n < 4) {
            for (FigureArray f : figureArrays) {
                if (figureArray.equals(f)) {
                    throw new Exception("Фигура не уникальна!");
                }
            }
            figureArray.setStructureRotate();
            t_n++;
        }*/
        for (FigureArray f : figureArrays) {
            if (figureArray.equals(f)) {
                throw new Exception("Фигура не уникальна!");
            }
        }
        DBHelper.addFigureToDataBase(figureArray.getFigure());
    }

    public static void deleteFigure(FigureArray figureArray) {
        DBHelper.deleteFigureFromDataBase(figureArray.getFigure());
    }

    public static void saveAudio(Audio audio) {
        DBHelper.addAudioToDataBase(audio);
    }

    public static void deleteAudio(Audio audio) {
        DBHelper.deleteAudioFromDataBase(audio);
    }

    //событие нажатия на фигуру
    @Override
    public void onFigureClick(int position) {
        Intent intent = new Intent(this, FigureActivity.class);
        intent.putExtra(FigureArray.class.getSimpleName(), figureArrays.get(position));
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