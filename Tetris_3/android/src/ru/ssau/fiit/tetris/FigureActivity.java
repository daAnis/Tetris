package ru.ssau.fiit.tetris;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.NumberPicker;
import android.widget.Toast;

public class FigureActivity extends AppCompatActivity {
    private FigureView figureView;
    private FigureArray figureArray;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_figure);

        figureView = findViewById(R.id.new_figure);

        Button saveFigure = findViewById(R.id.save_figure);
        Button deleteFigure = findViewById(R.id.cancel_figure);

        //изменение уровня
        NumberPicker numberPicker = findViewById(R.id.level);
        numberPicker.setMinValue(1);
        numberPicker.setMaxValue(10);
        numberPicker.setOnValueChangedListener(new NumberPicker.OnValueChangeListener() {
            @Override
            public void onValueChange(NumberPicker numberPicker, int oldValue, int newValue) {
                figureArray.setLevel(newValue);
            }
        });

        //получить параметры фигуры и задать значения полей
        Bundle arguments = getIntent().getExtras();
        if (arguments != null)
            figureArray = (FigureArray) arguments.getSerializable(FigureArray.class.getSimpleName());
        if (figureArray != null) {
            figureView.setFigure(figureArray);
            numberPicker.setValue(figureArray.getLevel());
        } else {
            figureArray = new FigureArray();
            numberPicker.setValue(1);
            deleteFigure.setVisibility(View.GONE);
        }

        //сохранение изменений
        saveFigure.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                figureArray.setStructure(figureView.getStructure());
                //проверка на целостность
                if (figureArray.hasDiscontinuities()) {
                    Toast.makeText(FigureActivity.this, "В структуре присутствуют разрывы!", Toast.LENGTH_LONG).show();
                    return;
                }
                try {
                    //проверка на уникальность и сохранение
                    AdminActivity.saveFigure(figureArray);
                } catch (Exception e) {
                    Toast.makeText(FigureActivity.this, e.getMessage(), Toast.LENGTH_LONG).show();
                    return;
                }
                finish();
            }
        });

        //удаление
        deleteFigure.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                figureArray.setStructure(figureView.getStructure());
                AdminActivity.deleteFigure(figureArray);
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