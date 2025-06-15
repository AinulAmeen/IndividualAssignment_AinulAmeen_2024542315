package com.example.projectassignment;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;

public class AboutActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about);
        setTitle("About");

        TextView aboutText = findViewById(R.id.aboutText);
        Button githubButton = findViewById(R.id.githubButton);

        String aboutInfo = "Name: AHMAD AINUL AMEEN BIN BADRUL ZAMAN\nStudent ID: 2024542315" +
                "\nCourse: ICT602 Mobile Technology\nCopyright © 2025";
        aboutText.setText(aboutInfo);

        githubButton.setOnClickListener(v -> {
            String githubUrl = "[Your GitHub URL]"; // Replace with your URL
            Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(githubUrl));
            startActivity(intent);
        });
    }
}