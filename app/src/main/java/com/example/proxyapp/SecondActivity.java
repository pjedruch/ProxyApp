package com.example.proxyapp;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import java.util.Base64;

public class SecondActivity extends AppCompatActivity {
    private TextView siteNameText;

    public static String siteBase64;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second);

        siteNameText = findViewById(R.id.siteName);

        String siteName = getIntent().getStringExtra("siteName");
        siteNameText.setText(siteName);

        String url = "https://" +siteName; // Replace with your actual URL

        // Instantiate the RequestQueue
        RequestQueue queue = Volley.newRequestQueue(this);

        // Request a string response from the provided URL
        StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        // Handle the response
                        Log.d("Volley", "Response: " + response);
                        siteBase64 = Base64.getEncoder().encodeToString(response.getBytes());
                        Log.d("Base64", "Response: " + siteBase64);
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                // Handle the error
                Log.e("Volley", "Error: " + error.getMessage());
            }
        });

        // Add the request to the RequestQueue
        queue.add(stringRequest);
    }


    public void onClick(View view) {
        Intent intent = new Intent(SecondActivity.this, EndActivity.class);

        startActivity(intent);

    }
}
