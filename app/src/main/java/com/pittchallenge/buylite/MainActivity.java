package com.pittchallenge.buylite;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProviders;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {
    private LifeCycleObserver observer;
    private UserViewModel usermodel;
    private EditText memail, mpassword;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        usermodel = ViewModelProviders.of(this).get(UserViewModel.class);
        memail = findViewById(R.id.EmailTextField);
        mpassword = findViewById(R.id.PasswordTextField);
        observer = new LifeCycleObserver();
        this.getLifecycle().addObserver(observer);
    }

    public void SignInUser(View view){
        usermodel.SignInUser(memail.getText().toString(), mpassword.getText().toString());
    }
    public void launchSignUp(View view){
        this.startActivity(new Intent(this, SignUpActivity.class));
    }

    public void launchFAQ(View view){
        this.startActivity(new Intent(this, FAQ_Activity.class));
    }

}
