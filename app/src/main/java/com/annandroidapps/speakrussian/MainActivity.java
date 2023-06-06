package com.annandroidapps.speakrussian;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        View numbersView = findViewById(R.id.numbers_layout);
        numbersView.setOnClickListener(view -> {
            Intent i = new Intent(MainActivity.this, Numbers.class);
            startActivity(i);
        });

        View colorsView = findViewById(R.id.colors_layout);
        colorsView.setOnClickListener(view -> {
            Intent i = new Intent(MainActivity.this, Colors.class);
            startActivity(i);
        });

        View insideView = findViewById(R.id.inside_layout);
        insideView.setOnClickListener(view -> {
            Intent i = new Intent(MainActivity.this, Inside.class);
            startActivity(i);
        });

        View outsideView = findViewById(R.id.outside_layout);
        outsideView.setOnClickListener(view -> {
            Intent i = new Intent(MainActivity.this, Outside.class);
            startActivity(i);
        });

        View phrasesView = findViewById(R.id.phrases_layout);
        phrasesView.setOnClickListener(view -> {
            Intent i = new Intent(MainActivity.this, Communication.class);
            startActivity(i);
        });
    }
}