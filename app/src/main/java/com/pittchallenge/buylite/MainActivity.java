package com.pittchallenge.buylite;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProviders;

import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;

public class MainActivity extends AppCompatActivity {

    private UserViewModel usermodel;
    private EditText memail, mpassword;
    //private FirebaseAuth mAuth;
    private static String TAG = "MainActivity: ";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        FirebaseDatabase.getInstance().setPersistenceEnabled(true); //keep an eye on this when working on other code.
        usermodel = ViewModelProviders.of(this).get(UserViewModel.class);
        memail = findViewById(R.id.EmailTextField);
        mpassword = findViewById(R.id.PasswordTextField);
       // mAuth = FirebaseAuth.getInstance(); //should not get instance in Activity. Must be relegated to Repository.

        if(savedInstanceState != null){
            memail.setText(savedInstanceState.getString("EMAIL"));
            mpassword.setText(savedInstanceState.getString("PASSWORD"));
        }
    }

    public void SignInUser(View view){
        /*this.SignInUser*/usermodel.SignInUser(memail.getText().toString(), mpassword.getText().toString(), new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if(task.isSuccessful())
                {
                    Log.d(TAG, "onComplete: User SignIn Successful.");
                    memail.setText("");
                    mpassword.setText("");
                    //usermodel.setUser(task.getResult().getUser());
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
        Intent intent = new Intent(new Intent(this, FAQ_Activity.class));
        //intent.putExtra("MAIN_CONT", (Parcelable) getApplicationContext());
        this.startActivity(intent);
    }

    @Override
    protected void onSaveInstanceState(@NonNull Bundle outState) {
        outState.putString("EMAIL", memail.getText().toString());
        outState.putString("PASSWORD", mpassword.getText().toString());
        super.onSaveInstanceState(outState);
    }

    @Override
    protected void onDestroy() {
        if(/*mAuth.getCurrentUser()*/ usermodel.getUser() != null){
            //mAuth.signOut();
            usermodel.signOut();
        }
        super.onDestroy();
    }

//    protected void SignInUser(String email, String password, @NonNull OnCompleteListener<AuthResult> listener){
//        mAuth.signInWithEmailAndPassword(email, password).addOnCompleteListener(listener);
//
//    }

}
// usermodel.SignInUser(memail.getText().toString(), mpassword.getText().toString(), new OnCompleteListener<AuthResult>() {
//            @Override
//            public void onComplete(@NonNull Task<AuthResult> task) {
//                if(task.isSuccessful())
//                {
//                    Log.d(TAG, "onComplete: User SignIn Successful.");
//                    memail.setText("");
//                    mpassword.setText("");
//                    usermodel.setUser(task.getResult().getUser());
//                }
//
//                else{
//                    Toast.makeText(getApplicationContext(), "Something went wrong with the login", Toast.LENGTH_LONG).show();
//                }
//
//            }
//        });