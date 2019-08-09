package com.pittchallenge.buylite;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProviders;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

public class SignUpActivity extends AppCompatActivity {
    UserViewModel model;
    private Button signUpButton;
    private final static String TAG  = "SignUpActivity: ";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);
        signUpButton = findViewById(R.id.SignUpSubmitButton);
        model = ViewModelProviders.of(this).get(UserViewModel.class);

        signUpButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               model.SignInUser();
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.app_options, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
            switch(item.getItemId()){
                case R.id.FAQ:
                    Log.d(TAG, "Clicking this will launch the FAQ activity");
                    startActivity(new Intent(this, FAQ_Activity.class));
                default:
                    return super.onOptionsItemSelected(item);
            }
    }
}
