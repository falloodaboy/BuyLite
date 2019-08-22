package com.pittchallenge.buylite;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProviders;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;

public class MainActivity extends AppCompatActivity {

    private UserViewModel usermodel;
    private EditText memail, mpassword;
    private static String TAG = "MainActivity: ";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        usermodel = ViewModelProviders.of(this).get(UserViewModel.class);
        memail = findViewById(R.id.EmailTextField);
        mpassword = findViewById(R.id.PasswordTextField);


        if(savedInstanceState != null){
            memail.setText(savedInstanceState.getString("EMAIL"));
            mpassword.setText(savedInstanceState.getString("PASSWORD"));
        }
    }

    public void SignInUser(View view){
        usermodel.SignInUser(memail.getText().toString(), mpassword.getText().toString(), new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if(task.isSuccessful())
                {
                    Log.d(TAG, "onComplete: User SignIn Successful.");
                    memail.setText("");
                    mpassword.setText("");
                }

                else{
                    Toast.makeText(getApplicationContext(), "Something went wrong with the login", Toast.LENGTH_LONG).show();
                }

            }
        });
    }

    public void launchSignUp(View view){
        this.startActivity(new Intent(this, SignUpActivity.class));
    }

    public void launchFAQ(View view){
        this.startActivity(new Intent(this, FAQ_Activity.class));
    }

    @Override
    protected void onSaveInstanceState(@NonNull Bundle outState) {
        outState.putString("EMAIL", memail.getText().toString());
        outState.putString("PASSWORD", mpassword.getText().toString());
        super.onSaveInstanceState(outState);
    }

    @Override
    protected void onDestroy() {
        usermodel.SignOutUser();
        super.onDestroy();
    }
}
