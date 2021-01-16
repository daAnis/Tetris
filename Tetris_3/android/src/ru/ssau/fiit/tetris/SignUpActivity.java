package ru.ssau.fiit.tetris;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
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

public class SignUpActivity extends AppCompatActivity {
    private EditText usernameET;
    private EditText passwordET;
    private EditText passwordRepeatET;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        usernameET = findViewById(R.id.editTextTextPersonName_s);
        passwordET = findViewById(R.id.editTextTextPassword_s);
        passwordRepeatET = findViewById(R.id.editTextTextPasswordRepeat);

        ImageButton imageButton = findViewById(R.id.imageButton_s);
        imageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final String password = passwordET.getText().toString();
                String passwordRepeat = passwordRepeatET.getText().toString();
                if (!password.equals(passwordRepeat)) {
                    Toast.makeText(SignUpActivity.this, "Пароли не совпадают!", Toast.LENGTH_LONG).show();
                    return;
                }
                if ((password.length() < 5)||(password.length() > 10)) {
                    Toast.makeText(SignUpActivity.this,
                            "Длина пароля должна находиться в пределах между 5 и 10 символами!", Toast.LENGTH_LONG).show();
                    return;
                }
                final String username = usernameET.getText().toString();
                if ((username.length() < 4)||(username.length() > 12)) {
                    Toast.makeText(SignUpActivity.this,
                            "Длина логина должна находиться в пределах между 4 и 12 символами!", Toast.LENGTH_LONG).show();
                    return;
                }
                DatabaseReference reference = FirebaseDatabase.getInstance().getReference("users");
                Query checkUser = reference.orderByChild("username").equalTo(username);

                checkUser.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        if (!snapshot.exists()) {
                            User newUser = new User(username, password);
                            DBHelper.addUserToDataBase(newUser);
                            Intent intent = new Intent(SignUpActivity.this, PlayerActivity.class);
                            intent.putExtra(User.class.getSimpleName(), newUser);
                            startActivity(intent);
                            finish();
                        } else {
                            Toast.makeText(SignUpActivity.this, "Такой пользователь уже зарегистрирован!", Toast.LENGTH_LONG).show();
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