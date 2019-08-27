package com.pittchallenge.buylite;

public class BuyOrderItem {

    public String itemName;
    public String description;

    public BuyOrderItem(){
        //empty constructor needed for model.
    }

    public BuyOrderItem(String paramname, String paramdescription){
        itemName = paramname;
        description = paramdescription;
    }



}
