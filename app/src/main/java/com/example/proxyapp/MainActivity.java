package com.example.proxyapp;


import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity {
    private TextView textViewResult;
    private EditText idNumber;
    private TextView pageIdText;

    private List<GetModel> posts;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        textViewResult = findViewById(R.id.text_view_result);

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://192.168.33.7:5000/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();


        JsonPlaceHolderApi jsonPlaceHolderApi = retrofit.create(JsonPlaceHolderApi.class);

        Call<List<GetModel>> call = jsonPlaceHolderApi.getPosts();

        call.enqueue(new Callback<List<GetModel>>() {
            @Override
            public void onResponse(Call<List<GetModel>> call, Response<List<GetModel>> response) {

                if (!response.isSuccessful()) {
                    textViewResult.setText("Code: " + response.code());
                    return;
                }

                posts = response.body();

                for (GetModel post : posts) {
                    String content = "";
                    content += "ID: " + post.getId() + "\n";
                    content += "Site: " + post.getSite() + "\n";
                    content += "Timeout: " + post.getTimeout() + "\n";

                    textViewResult.append(content);

                }
            }

            @Override
            public void onFailure(Call<List<GetModel>> call, Throwable t) {
                textViewResult.setText(t.getMessage());

            }
        });


    }

    public void onClick(View view) {
        idNumber = findViewById(R.id.idNumber);
        pageIdText = findViewById(R.id.pageIdText);
        pageIdText.setText(idNumber.getText());

        String siteName = findNameById(Integer.parseInt(idNumber.getText().toString()));

        Intent intent = new Intent(MainActivity.this, SecondActivity.class);
        intent.putExtra("siteName", siteName);
        startActivity(intent);

    }

    private String findNameById(int id) {
        String siteUrl = "";
        for (GetModel post : posts) {
            if (post.getId() == id)
                siteUrl = post.getSite();
        }
        return siteUrl;
    }

}