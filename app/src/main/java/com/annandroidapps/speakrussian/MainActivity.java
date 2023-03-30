package com.annandroidapps.speakrussian;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        TextView numbersView = findViewById(R.id.numbers);
        numbersView.setOnClickListener(view -> {
            Intent i = new Intent(MainActivity.this, Numbers.class);
            startActivity(i);
        });

        TextView colorsView = findViewById(R.id.colors);
        colorsView.setOnClickListener(view -> {
            Intent i = new Intent(MainActivity.this, Colors.class);
            startActivity(i);
        });

        TextView insideView = findViewById(R.id.inside);
        insideView.setOnClickListener(view -> {
            Intent i = new Intent(MainActivity.this, Inside.class);
            startActivity(i);
        });

        TextView outsideView = findViewById(R.id.outside);
        outsideView.setOnClickListener(view -> {
            Intent i = new Intent(MainActivity.this, Outside.class);
            startActivity(i);
        });

        TextView phrasesView = findViewById(R.id.phrases);
        phrasesView.setOnClickListener(view -> {
            Intent i = new Intent(MainActivity.this, Communication.class);
            startActivity(i);
        });
    }
}