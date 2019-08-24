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
    private FirebaseDatabase mBase;
    private DatabaseReference root;
    private FirebaseUser user;
    private  String username;

    public UserViewModel(){
        mBase = FirebaseDatabase.getInstance();
        root = mBase.getReference();

    }

    public String getUserName(){
        return username;
    }

    @Override
    protected void onCleared() {
        super.onCleared();
    }

    public void setUser(FirebaseUser u){
        user = u;
    }

    //Upload the BuyLiteUser object to the firebase database.
    public void UpStreamUser(FirebaseUser userid, String username){
        BuyLiteUser mainuser = new BuyLiteUser(userid.getEmail(), userid.getUid(),username);
        root.child("users").child(userid.getUid()).setValue(mainuser);
        user = userid;
        this.username = username;
    }

    /**Define custom classes here!**/
//     class UserStateChangeListener implements FirebaseAuth.AuthStateListener {
//
//        public UserStateChangeListener(){
//
//        }
//
//        @Override
//        public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
//            user = firebaseAuth.getCurrentUser();
//           if(firebaseAuth.getCurrentUser() != null){
//               Log.d(TAG, "onAuthStateChanged: " + firebaseAuth.getCurrentUser().getEmail());
//           }
//        }
//    }
//




}
