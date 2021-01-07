package ru.ssau.fiit.tetris;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import top.defaults.colorpicker.ColorObserver;
import top.defaults.colorpicker.ColorPickerView;

public class GlassActivity extends AppCompatActivity {
    private GlassView glassView;
    private EditText width_editable, height_editable, speed_editable, points_editable;
    private ColorPickerView colorPickerView;
    private Glass glass;
    private int width, height;
    private double speed, points;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_glass);

        //получить параметры стакана и задать значения полей
        Bundle arguments = getIntent().getExtras();
        if (arguments != null)
            glass = (Glass) arguments.getSerializable(Glass.class.getSimpleName());

        init();
    }

    private void init() {
        glassView = findViewById(R.id.new_glass);
        width_editable = findViewById(R.id.w);
        height_editable = findViewById(R.id.h);
        speed_editable = findViewById(R.id.speed_k);
        points_editable = findViewById(R.id.points_k);

        //изменение цвета
        colorPickerView = findViewById(R.id.color_picker);
        colorPickerView.subscribe(new ColorObserver() {
            @Override
            public void onColor(int color, boolean fromUser, boolean shouldPropagate) {
                glassView.setGlassColor(color);
                glass.setColor(color);
            }
        });

        //начальная инициализация полей
        if (glass != null) {
            width_editable.setText(glass.getWidth());
            height_editable.setText(glass.getHeight());
            colorPickerView.setInitialColor(glass.getColor());
            speed_editable.setText(String.format("%s", glass.getSpeed_k()));
            points_editable.setText(String.format("%s", glass.getPoints_k()));
            glassView.setGlass(glass);
        } else {
            glass = new Glass();
            colorPickerView.setInitialColor(getResources().getColor(R.color.check_box_color));
        }

        //перерисовать стакан при изменении размеров
        Button drawGlass = findViewById(R.id.draw_glass);
        drawGlass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    width = Integer.parseInt(width_editable.getText().toString());
                } catch (NumberFormatException e) {
                    Toast.makeText(GlassActivity.this, "Ширина задана неверно!", Toast.LENGTH_LONG).show();
                    return;
                }
                glass.setWidth(width);
                try {
                    height = Integer.parseInt(height_editable.getText().toString());
                } catch (NumberFormatException e) {
                    Toast.makeText(GlassActivity.this, "Высота задана неверно!", Toast.LENGTH_LONG).show();
                    return;
                }
                glass.setHeight(height);
                glassView.setGlassSize(width, height);
            }
        });

        //сохранение изменений
        Button saveGlass = findViewById(R.id.save_glass);
        saveGlass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    speed = Double.parseDouble(speed_editable.getText().toString());
                } catch (NumberFormatException e) {
                    Toast.makeText(GlassActivity.this, "Скорость задана неверно!", Toast.LENGTH_LONG).show();
                    return;
                }
                glass.setSpeed_k(speed);
                try {
                    points = Double.parseDouble(points_editable.getText().toString());
                } catch (NumberFormatException e) {
                    Toast.makeText(GlassActivity.this, "Количество очков задано неверно!", Toast.LENGTH_LONG).show();
                    return;
                }
                glass.setPoints_k(points);
                //todo проверка на уникальность
                //todo передача стакана в бд
                finish();
            }
        });

        //удаление
        Button cancelGlass = findViewById(R.id.cancel_glass);
        cancelGlass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //todo удаление стакана из бд
                finish();
            }
        });
    }
}