package com.example.mobiletest;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;

import javax.net.ssl.HttpsURLConnection;

public class ThirdScreen extends AppCompatActivity {
    private RecyclerView recyclerView;
    String fname, lname, email,image, textnama;
    ArrayList<String> aNama = new ArrayList<>();
    ArrayList<String> aEmail = new ArrayList<>();
    ArrayList<String> aImage = new ArrayList<>();
    SwipeRefreshLayout swipeRefreshLayout;
    RecyclerAdapter recyclerAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_third_screen);
        textnama = getIntent().getStringExtra("textnama");

        recyclerView = findViewById(R.id.rView);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        getData getData = new getData();
        getData.execute();

        swipeRefreshLayout = findViewById(R.id.swRefresh);
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                int p = getData.getPage();
                if(p == 12){
                    Toast.makeText(ThirdScreen.this, "data sudah terload semua", Toast.LENGTH_SHORT).show();
                    return;
                } else {
                    getData getData = new getData();
                    getData.execute();
                    recyclerAdapter.notifyDataSetChanged();
                    swipeRefreshLayout.setRefreshing(false);
                }
            }
        });
    }

    public class getData extends AsyncTask<String,String,String> implements RecyclerAdapter.ClickUser{
        public int p=12;

        @Override
        protected String doInBackground(String... strings) {
            String current="";
            try {
                URL url;
                HttpsURLConnection urlConnection = null;

                try{
                    String page = String.valueOf(p);
                    String JSON_URL = "https://reqres.in/api/users?page=1&per_page="+page;
                    Log.d("jumlah", JSON_URL);
                    url = new URL(JSON_URL);
                    urlConnection = (HttpsURLConnection) url.openConnection();

                    InputStream inputStream = urlConnection.getInputStream();
                    InputStreamReader inputStreamReader = new InputStreamReader(inputStream);

                    int data = inputStreamReader.read();
                    while(data != -1){
                        current += (char) data;
                        data = inputStreamReader.read();
                    }
                    return current;
                } catch (MalformedURLException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                } finally {
                    if(urlConnection != null) urlConnection.disconnect();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(String s) {
            try{
                JSONObject jsonObject = new JSONObject(s);
                JSONArray jsonArray = jsonObject.getJSONArray("data");
                for(int i = 0; i < jsonArray.length(); i++){
                    JSONObject object = jsonArray.getJSONObject(i);
                    fname = object.getString("first_name");
                    lname = object.getString("last_name");
                    email = object.getString("email");
                    image = object.getString("avatar");

                    ArrayList<String> Users = new ArrayList<>();
                    String nama = new StringBuilder(fname).append(" ").append(lname).toString();
                    aNama.add(nama);
                    aEmail.add(email);
                    aImage.add(image);
                }
                recyclerAdapter = new RecyclerAdapter(ThirdScreen.this, aNama, aEmail, aImage, ThirdScreen.getData.this);
                recyclerView.setAdapter(recyclerAdapter);
                recyclerAdapter.notifyDataSetChanged();
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }

        public int getPage(){
            return this.p;
        }

        public void OnUserClick(int position) {
            Intent intent = new Intent(ThirdScreen.this, SecondScreen.class);
            intent.putExtra("count", "2");
            intent.putExtra("nama", aNama.get(position));
            intent.putExtra("email", aNama.get(position));
            intent.putExtra("image", aImage.get(position));
            intent.putExtra("textnama", textnama);
            startActivity(intent);
//        Log.d("Activity", "Onclick berhasil" + position);
        }
    }
}