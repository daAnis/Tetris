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

        //перерисовать стакан при изменении размеров
        glassView = findViewById(R.id.new_glass);
        width_editable = findViewById(R.id.w);
        height_editable = findViewById(R.id.h);
        glass = new Glass();
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
        colorPickerView.subscribe(new ColorObserver() {
            @Override
            public void onColor(int color, boolean fromUser, boolean shouldPropagate) {
                glassView.setGlassColor(color);
            }
        });
    }
}