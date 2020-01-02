package com.pittchallenge.buylite.models;

/**
 * Each instance of the OrderPayload class will represent how much of a particular item a customer wants in a BuyOrder.
 */
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
