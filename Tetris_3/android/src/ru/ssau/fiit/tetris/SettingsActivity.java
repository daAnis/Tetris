package ru.ssau.fiit.tetris;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.CheckBox;
import android.widget.ImageButton;
import android.widget.RadioButton;
import android.widget.RadioGroup;

public class SettingsActivity extends AppCompatActivity {
    public static int results_show = R.id.rb_points;
    public static boolean nextF_show = false;
    public static boolean musicOnOff_show = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        //закрытие экрана
        ImageButton cancel = findViewById(R.id.cancel_button_s);
        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        //показ следующей фигуры
        final CheckBox nextF = findViewById(R.id.next_figure);
        nextF.setChecked(nextF_show);
        nextF.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                nextF_show = nextF.isChecked();
            }
        });

        //звуковое сопровождение
        final CheckBox musicOnOff = findViewById(R.id.music_on_off);
        musicOnOff.setChecked(musicOnOff_show);
        musicOnOff.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                musicOnOff_show = musicOnOff.isChecked();
            }
        });

        //отображение очков
        RadioButton radioButton = findViewById(results_show);
        radioButton.setChecked(true);
        RadioGroup group = findViewById(R.id.show_results);
        group.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                switch (i) {
                    case R.id.rb_points:
                        results_show = R.id.rb_points;
                        break;
                    case R.id.rb_time:
                        results_show = R.id.rb_time;
                        break;
                    case R.id.rb_no:
                        results_show = R.id.rb_no;
                        break;
                }
            }
        });
    }

    //назад
    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }
}