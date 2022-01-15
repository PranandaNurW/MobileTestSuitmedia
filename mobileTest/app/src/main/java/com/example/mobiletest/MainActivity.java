package com.example.mobiletest;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    private EditText etName, etPalindrom;
    private Button btnCheck, btnNext;
    private String palindrom;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        etName = findViewById(R.id.etName);
        etPalindrom = findViewById(R.id.etPalindrome);

        btnCheck = findViewById(R.id.btnCheck);
        btnNext = findViewById(R.id.btnNext);

        btnCheck.setOnClickListener(view -> {
            palindrom = etPalindrom.getText().toString();
            if(isPalindrome(palindrom)){
                Toast.makeText(MainActivity.this, "isPalindrom", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(MainActivity.this, "not Palindrom", Toast.LENGTH_SHORT).show();
            }
        });

        btnNext.setOnClickListener(view -> {
            if(etName.getText().toString().trim().length() == 0){
                Toast.makeText(MainActivity.this, "isi nama dahulu", Toast.LENGTH_SHORT).show();
                return;
            } else {
                Intent i = new Intent(MainActivity.this, SecondScreen.class);
                i.putExtra("textnama", etName.getText().toString());
                startActivity(i);
                finish();
            }
        });


    }

    public static boolean isPalindrome(String str){
        int l = 0;
        int h = str.length()-1;
        str = str.toLowerCase();

        while(l <= h) {
            char getAtl = str.charAt(l);
            char getAth = str.charAt(h);

            if (!(getAtl >= 'a' && getAtl <= 'z'))
                l++;
            else if(!(getAth >= 'a' && getAth <= 'z'))
                h--;
            else if( getAtl == getAth) {
                l++;
                h--;
            } else
                return false;
        }
        return true;
    }
}