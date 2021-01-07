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
    GlassView glassView;
    EditText width_editable, height_editable;
    ColorPickerView colorPickerView;
    Glass glass;
    int width, height;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_glass);

        //получить параметры стакана и задать значения полей
        Bundle arguments = getIntent().getExtras();
        if (arguments != null)
            glass = (Glass) arguments.getSerializable(Glass.class.getSimpleName());
        init();

        /*
        //перерисовать стакан при изменении размеров
        glassView = findViewById(R.id.new_glass);
        width_editable = findViewById(R.id.w);
        height_editable = findViewById(R.id.h);
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
                try {
                    height = Integer.parseInt(height_editable.getText().toString());
                } catch (NumberFormatException e) {
                    Toast.makeText(GlassActivity.this, "Высота задана неверно!", Toast.LENGTH_LONG).show();
                    return;
                }
                glass.setWidth(width);
                glass.setHeight(height);
                glassView.setGlass(glass);
            }
        });

        //изменение цвета
        colorPickerView = findViewById(R.id.color_picker);
        colorPickerView.subscribe(new ColorObserver() {
            @Override
            public void onColor(int color, boolean fromUser, boolean shouldPropagate) {
                glassView.setGlassColor(color);
            }
        });
        colorPickerView.setInitialColor(getResources().getColor(R.color.check_box_color));

        //сохранение изменений
        Button saveGlass = findViewById(R.id.save_glass);
        saveGlass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });*/
    }

    private void init() {
        glassView = findViewById(R.id.new_glass);
        width_editable = findViewById(R.id.w);
        height_editable = findViewById(R.id.h);

        //изменение цвета
        colorPickerView = findViewById(R.id.color_picker);
        colorPickerView.subscribe(new ColorObserver() {
            @Override
            public void onColor(int color, boolean fromUser, boolean shouldPropagate) {
                glassView.setGlassColor(color);
            }
        });

        //начальная инициализация полей
        if (glass != null) {
            width_editable.setText(glass.getWidth());
            height_editable.setText(glass.getHeight());
            colorPickerView.setInitialColor(glass.getColor());

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
                try {
                    height = Integer.parseInt(height_editable.getText().toString());
                } catch (NumberFormatException e) {
                    Toast.makeText(GlassActivity.this, "Высота задана неверно!", Toast.LENGTH_LONG).show();
                    return;
                }
                glassView.setGlassSize(width, height);
            }
        });

        //сохранение изменений
        Button saveGlass = findViewById(R.id.save_glass);
        saveGlass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //glassView.getGlass().setPoints_k();

            }
        });
    }
}