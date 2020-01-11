package com.pittchallenge.buylite.models;

import androidx.annotation.NonNull;

public class BuyLiteUser {
    public String email;
    public String uid;
    public String username;

    public BuyLiteUser(){

    }

    public BuyLiteUser(@NonNull String email, @NonNull String uid, @NonNull String username){
        this.email = email;
        this.uid = uid;
        this.username = username;
    }


}
