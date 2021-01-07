package ru.ssau.fiit.tetris;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.NumberPicker;

public class FigureActivity extends AppCompatActivity {

    private Figure figure;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_figure);

        figure = new Figure();

        //изменение уровня
        NumberPicker numberPicker = findViewById(R.id.level);
        numberPicker.setMinValue(1);
        numberPicker.setMaxValue(10);
        numberPicker.setValue(1);
        numberPicker.setOnValueChangedListener(new NumberPicker.OnValueChangeListener() {
            @Override
            public void onValueChange(NumberPicker numberPicker, int oldValue, int newValue) {
                figure.setLevel(newValue);
            }
        });

        //сохранение изменений
        Button saveFigure = findViewById(R.id.save_figure);
        saveFigure.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //todo проверка на уникальность
                //todo проверка на целостность
                //todo передача стакана в бд
                finish();
            }
        });

        //удаление
        Button deleteFigure = findViewById(R.id.cancel_figure);
        deleteFigure.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //todo удаление стакана из бд
                finish();
            }
        });
    }
}