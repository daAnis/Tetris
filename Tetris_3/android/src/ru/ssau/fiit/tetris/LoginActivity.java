package ru.ssau.fiit.tetris;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import ru.ssau.fiit.tetris.db.DBHelper;

public class LoginActivity extends AppCompatActivity {
    EditText usernameET;
    EditText passwordET;
    Button signUpButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        usernameET = findViewById(R.id.editTextTextPersonName);
        passwordET = findViewById(R.id.editTextTextPassword);
        signUpButton = findViewById(R.id.sign_up_button);

        signUpButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(LoginActivity.this, SignUpActivity.class));
                finish();
            }
        });
        signUpButton.setVisibility(View.GONE);

        ImageButton imageButton = findViewById(R.id.imageButton);
        imageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final String login = usernameET.getText().toString();
                String password = passwordET.getText().toString();

                if ((login.compareTo(User.ADMIN_LOGIN) == 0) &&
                        (password.compareTo(User.ADMIN_PASSWORD) == 0)) {
                    startActivity(new Intent(LoginActivity.this, AdminActivity.class));
                    finish();
                    return;
                }

                if ((login.compareTo(User.PLAYER_LOGIN) == 0) &&
                        (password.compareTo(User.PLAYER_PASSWORD) == 0)) {
                    Intent intent = new Intent(LoginActivity.this, PlayerActivity.class);
                    intent.putExtra("username", User.PLAYER_LOGIN);
                    startActivity(intent);
                    finish();
                    return;
                }

                DatabaseReference reference = FirebaseDatabase.getInstance().getReference("users");
                Query checkUser = reference.orderByChild("username").equalTo(login);

                checkUser.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        if (snapshot.exists()) {
                            User user = snapshot.child(login).getValue(User.class);
                            Intent intent = new Intent(LoginActivity.this, PlayerActivity.class);
                            intent.putExtra(User.class.getSimpleName(), user);
                            startActivity(intent);
                            finish();
                        } else {
                            Toast.makeText(LoginActivity.this, "Такого пользователя не существует!", Toast.LENGTH_LONG).show();
                            signUpButton.setVisibility(View.VISIBLE);
                        }
                    }
                    @Override
                    public void onCancelled(@NonNull DatabaseError error) { }
                });
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