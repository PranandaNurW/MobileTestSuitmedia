package com.example.mobiletest;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NavUtils;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import jp.wasabeef.picasso.transformations.CropCircleTransformation;

public class SecondScreen extends AppCompatActivity {
    TextView tvNama, tvEmail, tvUsername;
    ImageView ivImage;
    String image, textnama;
    Button btnChoose;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second_screen);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        tvNama = findViewById(R.id.tvNama);
        tvUsername = findViewById(R.id.tvUsername);
        tvEmail = findViewById(R.id.tvEmail);
        ivImage = findViewById(R.id.ivImage);

        textnama = getIntent().getStringExtra("textnama");
        tvNama.setText(textnama);

        btnChoose = findViewById(R.id.btnChoose);
        btnChoose.setOnClickListener(view -> {
            Intent i = new Intent(SecondScreen.this, ThirdScreen.class);
            i.putExtra("textnama", textnama);
            startActivity(i);
            finish();
        });

        getData();
    }

    void getData(){
        tvUsername.setText(getIntent().getStringExtra("nama"));
        tvEmail.setText(getIntent().getStringExtra("email"));
        image = getIntent().getStringExtra("image");
        Picasso.get().load(image).transform(new CropCircleTransformation()).into(ivImage);
    }
}