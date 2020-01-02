package com.pittchallenge.buylite.models;

import androidx.annotation.NonNull;

import java.util.ArrayList;

public class BuyLiteUser {
    public String email;
    public String uid;
    public String username;
    public ArrayList<String> purchases;

    public BuyLiteUser(){

    }

    public BuyLiteUser(@NonNull String email, @NonNull String uid, @NonNull String username, ArrayList<String> purchases){
        this.email = email;
        this.uid = uid;
        this.username = username;
        if(purchases == null){
            this.purchases = null;
        }
        else{
            this.purchases = new ArrayList<>(purchases);
        }

    }


}
