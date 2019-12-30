package com.pittchallenge.buylite.models;

import androidx.annotation.NonNull;

import com.pittchallenge.buylite.BuyOrderItem;

import java.util.List;

public class OrderlistItem {

    public String order_id;
    public String Title;
    public List<BuyOrderItem> catalog;
    public OrderLatLng location;
    public String host;





    public OrderlistItem(){

    }

    public OrderlistItem(String order_id, List<BuyOrderItem> items, OrderLatLng location, String title, String host){
        this.order_id = order_id;
        this.catalog = items;
        this.location = location;
        this.Title = title;
        this.host = host;
    }


    //should catalog be included in this toString method?
    @NonNull
    public String toString(){
        return order_id + "," + Title + "," + location;
    }


    //any user getting this string should use the \n as the delimiter to get individual BuyOrderItem Strings.
    public String getCatalog(){
        if(catalog != null){
            StringBuilder holder = new StringBuilder();
            for(BuyOrderItem item : catalog){
                holder.append(item.toString() + ","); //should the delimiter be a \n instead?
            }
            return holder.toString();
        }
        else{
            return null;
        }
    }
}
