package com.pittchallenge.buylite;

import android.os.AsyncTask;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.pittchallenge.buylite.models.BuyLiteUser;

public class UserViewModel extends ViewModel implements FirebaseAuth.AuthStateListener{
    private static final String TAG = "UserViewModel: ";
    private FirebaseDatabase mBase;
    private DatabaseReference root;
    private MutableLiveData<FirebaseUser> user;
    private String username, phone;
    private FirebaseAuth mAuth;


    public UserViewModel(){
        mBase = FirebaseDatabase.getInstance();
        root = mBase.getReference();
        mAuth = FirebaseAuth.getInstance();
        user = new MutableLiveData<>();
        user.setValue(mAuth.getCurrentUser());
        mAuth.addAuthStateListener(this);
    }

    public void SignUpUser(String email, String password,String username, OnCompleteListener<AuthResult> listener){
        mAuth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(listener);
        this.username = username;
    }

    public void SignInUser(String email, String password, OnCompleteListener<AuthResult> listener){
        mAuth.signInWithEmailAndPassword(email, password).addOnCompleteListener(listener);
    }

    public String getUserName(){
        return username;
    }
    
    public LiveData<FirebaseUser> getUser(){
        return user;
    }

    @Override
    protected void onCleared() {
        mAuth.removeAuthStateListener(this);
        super.onCleared();

    }

    public void setUser(FirebaseUser u){
        user.setValue(u);
    }

    //Upload the BuyLiteUser object to the firebase database.
    public void UpStreamUser(FirebaseUser userid, String username, String phone){
        BuyLiteUser mainuser = new BuyLiteUser(userid.getEmail(), userid.getUid(),username, null);
        root.child("users").child(userid.getUid()).setValue(mainuser);
        root.child("users").child(userid.getUid()).child("phone").setValue(phone);
        user.setValue(userid);
        this.username = username;
        this.phone = phone;
    }

    public void signOut(){
        mAuth.signOut();
    }

    //update the backend with the new username for the user.
    public void updateUserName(String newUsername){
       String uid =  user.getValue().getUid();

       if(!uid.isEmpty()){
          DatabaseReference childnode = root.child("users").child(uid).child("username");
           Log.d(TAG, "updateUserName: username before explicitly setting user again from mAuth: " + user.getValue());
          childnode.setValue(username);
       }
       else{
           Log.d(TAG, "updateUserName: User uid is either empty or null.");
       }
    }

    //update the backend with the new email for the user.
    public void updateUserEmail(String newUserEmail){
        String uid = mAuth.getUid();

        if(!uid.isEmpty()){
            DatabaseReference emailchild = root.child("users").child(uid).child("email");
            emailchild.setValue(newUserEmail);
            Log.d(TAG, "updateUserEmail: user email Before explicitly setting user again from mAuth: " + user.getValue().getEmail());
            /*user.getValue().reload();*/ user.setValue(mAuth.getCurrentUser()); //might update the user object.
            Log.d(TAG, "updateUserEmail: user email After explicitly setting user again from mAuth: " + user.getValue().getEmail());
            
        }
        else{
            Log.d(TAG, "updateUserEmail: Attempted to update user email. Failed because uid is null or empty string.");
        }

    }

    @Override
    public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
        Log.d(TAG, "onAuthStateChanged: Auth state change detected in UserViewModel instance. User: " + mAuth.getCurrentUser());
        user.setValue(firebaseAuth.getCurrentUser());
    }

}





