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
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class SignUpActivity extends AppCompatActivity {
    UserViewModel usermodel;
    private Button signUpButton;
    private FirebaseAuth mAuth;
    private final static String TAG  = "SignUpActivity: ";
    private EditText memail, mpassword, musername, mphone;
    private DataViewModel datamodel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);
        signUpButton = findViewById(R.id.SignUpSubmitButton);

        usermodel = ViewModelProviders.of(this).get(UserViewModel.class);
        //datamodel = ViewModelProviders.of(this).get(DataViewModel.class);

        memail = findViewById(R.id.SignUpEmailField);
        mpassword = findViewById(R.id.SignUpPasswordField);
        musername = findViewById(R.id.SignUpUserNameField);
        mphone = findViewById(R.id.SignUpPhoneField);
       // mAuth = FirebaseAuth.getInstance(); //activities should only be observing data and not changing it. can this be called from the viewmodel.
        if(savedInstanceState != null){
            memail.setText(savedInstanceState.getString("EMAIL"));
            mpassword.setText(savedInstanceState.getString("PASSWORD"));
        }

        signUpButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if(mpassword.getText().toString().isEmpty()
                || memail.getText().toString().isEmpty() || !memail.getText().toString().contains("@")){
                    Toast.makeText(getApplicationContext(), "Email or Password field is incorrect", Toast.LENGTH_SHORT).show();
                }
                else if(!mpassword.getText().toString().contains("*") && !containsNumber(mpassword.getText().toString()))
                {
                    Toast.makeText(getApplicationContext(), "*Password must contain a number or *", Toast.LENGTH_SHORT).show();
                }
                else{   /*SignUpActivity.this.SignUpUser*/
                usermodel.SignUpUser(memail.getText().toString(), mpassword.getText().toString(), musername.getText().toString()
                        , new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                if(!task.isSuccessful()){
                                    memail.setText("");
                                    mpassword.setText("");
                                    musername.setText("");
                                    mphone.setText("");
                                    Toast.makeText(getApplicationContext(), "Looks like something went wrong with the Sign Up", Toast.LENGTH_SHORT).show();

                                }
                                else{
//                                     usermodel.getUser().getValue().sendEmailVerification();
////                                             .addOnCompleteListener(new OnCompleteListener<Void>() {
////                                         @Override
////                                         public void onComplete(@NonNull Task<Void> task) {
////                                             if()
////                                         }
////                                     });
                                     usermodel.UpStreamUser(task.getResult().getUser(), musername.getText().toString(), mphone.getText().toString());
                                    Log.d(TAG, "onComplete: User SignUp Successful");
                                    //   usermodel.setUser(task.getResult().getUser()); //<-- need this to make sure all cases of user object in ViewModel are covered.
                                    //startActivity(new Intent(SignUpActivity.this, LandingPage.class )); <-- need to set this up after creating the landing page.
                                    SignUpActivity.this.finish();
                                }
                            }
                        });

                }

            }
        });
    }
//    protected void SignUpUser(String email, String password, String username, @NonNull OnCompleteListener<AuthResult> listener ){
//        mAuth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(listener);
//
//    }

    private boolean containsNumber(String s){
        boolean response = false;
        for(int i=0; i < s.length(); i++){
            if(s.charAt(i) == '1' || s.charAt(i) == '2' || s.charAt(i) == '3' || s.charAt(i) == '4' || s.charAt(i) == '5' ||
            s.charAt(i) == '6' || s.charAt(i) == '7' || s.charAt(i) == '8' || s.charAt(i) == '9' || s.charAt(i) == '0'){
                response = true;
            }
        }
        return response;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.app_options, menu);
        menu.removeItem(R.id.searchwidget);
        menu.removeItem(R.id.start_frag_test);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
            switch(item.getItemId()){
                case R.id.FAQ:
                    Log.d(TAG, "Clicking this will launch the FAQ activity");
                    startActivity(new Intent(this, FAQ_Activity.class));
                    return true;
                case R.id.SIGNOUT:
                    if(/*mAuth.getCurrentUser()*/ usermodel.getUser() != null) {
                        //mAuth.signOut();
                        usermodel.signOut();
                    }
                    return true;
                default:
                    return super.onOptionsItemSelected(item);
            }
    }

    @Override
    protected void onSaveInstanceState(@NonNull Bundle outState) {
        outState.putString("EMAIL", memail.getText().toString());
        outState.putString("PASSWORD", mpassword.getText().toString());
        super.onSaveInstanceState(outState);
    }
}












//                  usermodel.SignUpUser(memail.getText().toString(), mpassword.getText().toString()
//                          ,musername.getText().toString(), new OnCompleteListener<AuthResult>() {
//                              @Override
//                              public void onComplete(@NonNull Task<AuthResult> task) {
//                                  if(!task.isSuccessful()){
//                                      memail.setText("");
//                                      mpassword.setText("");
//                                      musername.setText("");
//                                      Toast.makeText(getApplicationContext(), "Looks like something went wrong with the Sign Up", Toast.LENGTH_SHORT).show();
//
//                                  }
//                                  else{
//                                      usermodel.UpStreamUser(task.getResult().getUser());
//                                      Log.d(TAG, "onComplete: User SignUp Successful");
//                                      SignUpActivity.this.finish();
//                                      //startActivity(new Intent(SignUpActivity.this, LandingPage.class )); <-- BuyOrderList Activity
//                                  }
//                              }
//                          });