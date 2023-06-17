package com.example.proxyapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class EndActivity extends AppCompatActivity {

    private Button backMain;
    private TextView endtextView;

    private List<PostModel> posts;


    private SecondJsonPlaceHolderApi secondJsonPlaceHolderApi;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_end);


        backMain = findViewById(R.id.backMain);
        endtextView = findViewById(R.id.endtextView);

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://192.168.33.7:5000/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();


        secondJsonPlaceHolderApi = retrofit.create(SecondJsonPlaceHolderApi.class);

        createPost();


/*
        Call<List<PostModel>> call = secondJsonPlaceHolderApi.getJsons();

        call.enqueue(new Callback<List<PostModel>>() {
            @Override
            public void onResponse(Call<List<PostModel>> call, Response<List<PostModel>> response) {

                if (!response.isSuccessful()) {
                    endtextView.setText("Code: " + response.code());
                    return;
                }


                posts = response.body();

                for (PostModel post : posts) {
                    String content = "";
                    content += "ID: " + post.getId() + "\n";
                    content += "Site: " + post.getSource() + "\n";
                    content += "Timeout: " + post.getTimeout() + "\n";

                    endtextView.append(content);

                }
            }

            @Override
            public void onFailure(Call<List<PostModel>> call, Throwable t) {
                endtextView.setText(t.getMessage());
            }
        });*/

    }

    private void createPost() {
        PostModel post = new PostModel(SecondActivity.siteBase64,5);
        Call<PostModel> call = secondJsonPlaceHolderApi.sendJsonToServer(post);

         call.enqueue(new Callback<PostModel>() {
             @Override
             public void onResponse(Call<PostModel> call, Response<PostModel> response) {

                 if (!response.isSuccessful()) {
                     endtextView.setText("Code: " + response.code());
                     return;
                 }
                 Log.d("Post", "Response: " + response);

                 String content = "";
                 content += "Code: " + response.code() + "\n";
                 content += "Zapisano na serwerze. \n";
                 endtextView.append(content);
             }

             @Override
             public void onFailure(Call<PostModel> call, Throwable t) {
                 endtextView.setText(t.getMessage());
             }
         });

    }

    public void onClick(View view) {
        Intent intent = new Intent(EndActivity.this, MainActivity.class);
        startActivity(intent);

    }
}
