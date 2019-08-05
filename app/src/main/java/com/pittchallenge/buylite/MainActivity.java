package com.pittchallenge.buylite;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class MainActivity extends AppCompatActivity {
    private LifeCycleObserver observer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        observer = new LifeCycleObserver();
        this.getLifecycle().addObserver(observer);
    }

    public void launchSignUp(View view){
        Intent intent = new Intent(this, SignUpActivity.class);
        this.startActivity(intent);
    }

}
