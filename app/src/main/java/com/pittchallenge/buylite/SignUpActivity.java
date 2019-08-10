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
import android.widget.EditText;

public class SignUpActivity extends AppCompatActivity {
    UserViewModel usermodel;
    private Button signUpButton;
    private final static String TAG  = "SignUpActivity: ";
    private EditText memail, mpassword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);
        signUpButton = findViewById(R.id.SignUpSubmitButton);
        usermodel = ViewModelProviders.of(this).get(UserViewModel.class);
        memail = findViewById(R.id.SignUpEmailField);
        mpassword = findViewById(R.id.SignUpPasswordField);
        signUpButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               usermodel.SignUpUser(memail.getText().toString(), mpassword.getText().toString());
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
