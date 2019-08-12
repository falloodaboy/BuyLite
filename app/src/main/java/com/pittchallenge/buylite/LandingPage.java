package com.pittchallenge.buylite;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class LandingPage extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_landing_page);

        Button createBuyOrder = findViewById(R.id.button8);
        Button viewProfile = findViewById(R.id.button4);
        Button faq = findViewById(R.id.button7);
        Button searchOrders = findViewById(R.id.button);
        Button logOut = findViewById(R.id.button2);
        Button viewMyOrders = findViewById(R.id.button3);



        createBuyOrder.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                // Code here executes on main thread after user presses button
            }
        });

        viewProfile.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                // Code here executes on main thread after user presses button
                startActivity(new Intent(LandingPage.this, UserProfile.class));
            }
        });

        faq.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                // Code here executes on main thread after user presses button
                startActivity(new Intent(LandingPage.this, FAQ_Activity.class));
            }
        });

        searchOrders.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                // Code here executes on main thread after user presses button
            }
        });

        logOut.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                // Code here executes on main thread after user presses button
                startActivity(new Intent(LandingPage.this, MainActivity.class));
            }
        });

        viewMyOrders.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                // Code here executes on main thread after user presses button
            }
        });


    }
}
