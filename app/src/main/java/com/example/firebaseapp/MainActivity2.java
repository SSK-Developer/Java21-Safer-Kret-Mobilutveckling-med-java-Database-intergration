package com.example.firebaseapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

public class MainActivity2 extends AppCompatActivity {

    TextView userNameText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);

        userNameText = findViewById(R.id.usernameText);

        Intent intent = getIntent();
        String userName = intent.getStringExtra("userName");
        userNameText.setText("Welcome " + userName);
    }
}