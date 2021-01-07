package ru.ssau.fiit.tetris;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class GlassActivity extends AppCompatActivity {
    GlassView glassView;
    EditText width, height;
    Glass glass;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_glass);

        //перерисовать стакан при изменении размеров
        glassView = findViewById(R.id.new_glass);
        width = findViewById(R.id.w);
        height = findViewById(R.id.h);
        glass = new Glass();
        Button drawGlass = findViewById(R.id.draw_glass);
        drawGlass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    glass.setWidth(Integer.parseInt(width.getText().toString()));
                } catch (NumberFormatException e) {
                    Toast.makeText(GlassActivity.this, "Ширина задана неверно!", Toast.LENGTH_LONG);
                    return;
                }
                try {
                    glass.setHeight(Integer.parseInt(height.getText().toString()));
                } catch (NumberFormatException e) {
                    Toast.makeText(GlassActivity.this, "Высота задана неверно!", Toast.LENGTH_LONG);
                    return;
                }
                glassView.setGlass(glass);
            }
        });
    }
}