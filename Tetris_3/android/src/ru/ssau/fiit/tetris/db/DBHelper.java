package ru.ssau.fiit.tetris.db;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import ru.ssau.fiit.tetris.Audio;
import ru.ssau.fiit.tetris.Figure;
import ru.ssau.fiit.tetris.FigureArray;
import ru.ssau.fiit.tetris.Glass;
import ru.ssau.fiit.tetris.Record;
import ru.ssau.fiit.tetris.User;

public class DBHelper {

    public static void addUserToDataBase(User user) {
        DatabaseReference reference = FirebaseDatabase.getInstance().getReference("users");
        //String id = reference.push().getKey();
        //user.setUserId(id);
        reference.child(user.getUsername()).setValue(user);
    }

    public static void addRecordToDataBase(Record record) {
        DatabaseReference reference = FirebaseDatabase.getInstance().getReference("records");
        String id = reference.push().getKey();
        record.setRecordId(id);
        reference.child(id).setValue(record);
    }

    public static void addGlassToDataBase(Glass glass) {
        DatabaseReference reference = FirebaseDatabase.getInstance().getReference("glasses");
        String id = reference.push().getKey();
        glass.setGlassId(id);
        reference.child(id).setValue(glass);

    }

    public static void deleteGlassFromDataBase(Glass glass) {
        DatabaseReference reference = FirebaseDatabase.getInstance().getReference("glasses").child(glass.getGlassId());
        reference.removeValue();
    }

    public static void addFigureToDataBase(Figure figure) {
        DatabaseReference reference = FirebaseDatabase.getInstance().getReference("figures");
        String id = reference.push().getKey();
        figure.setFigureId(id);
        reference.child(id).setValue(figure);
    }

    public static void deleteFigureFromDataBase(Figure figure) {
        DatabaseReference reference = FirebaseDatabase.getInstance().getReference("figures").child(figure.getFigureId());
        reference.removeValue();
    }

    public static void addAudioToDataBase(Audio audio) {
        DatabaseReference reference = FirebaseDatabase.getInstance().getReference("audios");
        String id = reference.push().getKey();
        audio.setAudioId(id);
        reference.child(id).setValue(audio);
    }

    public static void deleteAudioFromDataBase(Audio audio) {
        DatabaseReference reference = FirebaseDatabase.getInstance().getReference("audios").child(audio.getAudioId());
        reference.removeValue();
    }
}
