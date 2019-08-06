package com.pittchallenge.buylite;

import android.util.Log;

import androidx.lifecycle.ViewModel;

public class UserViewModel extends ViewModel {
    private static final String TAG = "UserViewModel: ";

    public void SignInUser(){
        Log.d(TAG, "SignInUser: User Sign In is Done");
    }
}
