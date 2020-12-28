package ru.ssau.fiit.tetris;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageButton;

public class LoginActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        ImageButton imageButton = findViewById(R.id.imageButton);
        imageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                EditText loginET = findViewById(R.id.editTextTextPersonName);
                EditText passwordET = findViewById(R.id.editTextTextPassword);
                String login = loginET.getText().toString();
                String password = passwordET.getText().toString();

                if ((login.compareTo(UserInformation.ADMIN_LOGIN) == 0) &&
                        (password.compareTo(UserInformation.ADMIN_PASSWORD) == 0)) {
                    startActivity(new Intent(LoginActivity.this, AdminActivity.class));
                    finish();
                }

                if ((login.compareTo(UserInformation.PLAYER_LOGIN) == 0) &&
                        (password.compareTo(UserInformation.PLAYER_PASSWORD) == 0)) {
                    startActivity(new Intent(LoginActivity.this, PlayerActivity.class));
                    finish();
                }
            }
        });
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();

        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
        finish();
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        if (ev.getAction() == MotionEvent.ACTION_DOWN) {
            View v = getCurrentFocus();
            if (v instanceof EditText) {
                v.clearFocus();
                InputMethodManager inputMethodManager = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                inputMethodManager.hideSoftInputFromWindow(v.getWindowToken(), 0);
            }
        }
        return super.dispatchTouchEvent(ev);
    }
}