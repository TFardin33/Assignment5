package com.example.hello;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class HomePage extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_home_page);

        TextView textView = findViewById(R.id.textView);
        TextView textView1 = findViewById(R.id.textViewDescription);
        Button button = findViewById(R.id.button);

        button.setOnClickListener(v -> {
            Intent mainIntent = new Intent(HomePage.this, MainActivity.class);
            startActivity(mainIntent);
        });
    }
}
