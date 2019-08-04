package com.pittchallenge.buylite;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;

public class MainActivity extends AppCompatActivity {
    private LifeCycleObserver observer;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        observer = new LifeCycleObserver();
        this.getLifecycle().addObserver(observer);
    }


}
