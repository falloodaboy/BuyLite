package com.pittchallenge.buylite;

import android.util.Log;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.FirebaseDatabase;

public class UserViewModel extends ViewModel {
    private static final String TAG = "UserViewModel: ";
    private FirebaseAuth mAuth;
    private FirebaseDatabase mBase;
    private FirebaseUser user;

    public UserViewModel(){
        mAuth = FirebaseAuth.getInstance();
        mBase = FirebaseDatabase.getInstance();
    }


    protected void SignInUser(String email, String password){
        mAuth.signInWithEmailAndPassword(email, password)
                .addOnSuccessListener(new OnSuccessListener<AuthResult>() {
            @Override
            public void onSuccess(AuthResult authResult) {
                Log.d(TAG, "SignInUser: User Sign In is Done");
            }
        })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.d(TAG, "SignInUser: User Sign In unsuccessful" + e);
                    }
                });
        user = mAuth.getCurrentUser();
    }

    protected void SignUpUser(String email, String password){
        mAuth.createUserWithEmailAndPassword(email, password)
                .addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Log.d(TAG, "SignUpUser: " + e);
            }
        })
                .addOnSuccessListener(new OnSuccessListener<AuthResult>() {
            @Override
            public void onSuccess(AuthResult authResult) {
                Log.d(TAG, "SignUpUser: User Sign Up is Done");
            }
        });

        user = mAuth.getCurrentUser();

    }

    protected void SignOutUser(){
        mAuth.signOut();
        Log.d(TAG, "SignOutUser: User Sign Out is Done");
    }
}
