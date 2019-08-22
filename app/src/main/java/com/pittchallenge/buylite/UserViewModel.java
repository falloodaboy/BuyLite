package com.pittchallenge.buylite;

import android.os.AsyncTask;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.pittchallenge.buylite.models.BuyLiteUser;

public class UserViewModel extends ViewModel {
    private static final String TAG = "UserViewModel: ";
    private FirebaseAuth mAuth;
    private FirebaseDatabase mBase;
    private DatabaseReference root;
    private FirebaseUser user;
    private static String username;
    private UserStateChangeListener listener;

    public UserViewModel(){
        mAuth = FirebaseAuth.getInstance();
        mBase = FirebaseDatabase.getInstance();
        root = mBase.getReference();
        listener = new UserStateChangeListener();
        mAuth.addAuthStateListener(listener);
    }



    protected void SignInUser(String email, String password, @NonNull OnCompleteListener<AuthResult> listener){
        mAuth.signInWithEmailAndPassword(email, password).addOnCompleteListener(listener);

    }

    protected void SignUpUser(String email, String password, String username, @NonNull OnCompleteListener<AuthResult> listener ){
        mAuth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(listener);
        this.username = username;
    }

    public boolean isUserAuthenticated(){
        return user != null;
    }


    public String getUserName(){
        return username;
    }

    @Override
    protected void onCleared() {
        mAuth.removeAuthStateListener(listener);
        super.onCleared();
    }

    protected void SignOutUser(){
        mAuth.signOut();
        Log.d(TAG, "SignOutUser: Successful User Sign Out");
    }

    //Upload the BuyLiteUser object to the firebase database.
    public void UpStreamUser(FirebaseUser userid){
        BuyLiteUser mainuser = new BuyLiteUser(userid.getEmail(), userid.getUid(),username);
        root.child("users").child(userid.getUid()).setValue(mainuser);
        user = userid;
    }

    /**Define custom classes here!**/
     class UserStateChangeListener implements FirebaseAuth.AuthStateListener {

        public UserStateChangeListener(){

        }

        @Override
        public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
            user = firebaseAuth.getCurrentUser();
           if(firebaseAuth.getCurrentUser() != null){
               Log.d(TAG, "onAuthStateChanged: " + firebaseAuth.getCurrentUser().getEmail());
           }
        }
    }





}
