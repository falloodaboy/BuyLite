package com.pittchallenge.buylite.models;

public class OrderPayload {
    public String customerUID;
    public String itemName;
    public int amount;


    public OrderPayload(){

    }

    public OrderPayload(String custUID, String item, int amt){
        customerUID = custUID;
        itemName = item;
        amount = amt;
    }
}
