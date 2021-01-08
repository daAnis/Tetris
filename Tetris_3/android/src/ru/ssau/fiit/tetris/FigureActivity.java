package ru.ssau.fiit.tetris;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.NumberPicker;

public class FigureActivity extends AppCompatActivity {
    private FigureView figureView;
    private Figure figure;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_figure);

        figureView = findViewById(R.id.new_figure);

        //изменение уровня
        NumberPicker numberPicker = findViewById(R.id.level);
        numberPicker.setMinValue(1);
        numberPicker.setMaxValue(10);
        numberPicker.setOnValueChangedListener(new NumberPicker.OnValueChangeListener() {
            @Override
            public void onValueChange(NumberPicker numberPicker, int oldValue, int newValue) {
                figure.setLevel(newValue);
            }
        });

        //получить параметры фигуры и задать значения полей
        Bundle arguments = getIntent().getExtras();
        if (arguments != null)
            figure = (Figure) arguments.getSerializable(Glass.class.getSimpleName());
        if (figure != null) {
            figureView.setFigure(figure);
            numberPicker.setValue(figure.getLevel());
        } else {
            figure = new Figure();
            numberPicker.setValue(1);
        }

        //сохранение изменений
        Button saveFigure = findViewById(R.id.save_figure);
        saveFigure.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //todo проверка на уникальность
                //todo проверка на целостность
                AdminActivity.saveFigure(figure);
                finish();
            }
        });

        //удаление
        Button deleteFigure = findViewById(R.id.cancel_figure);
        deleteFigure.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AdminActivity.deleteFigure(figure);
                finish();
            }
        });
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }
}