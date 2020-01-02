package com.pittchallenge.buylite.models;

import android.os.Bundle;
import android.os.Parcel;
import android.os.Parcelable;
import android.util.Log;

import com.google.android.gms.maps.model.LatLng;
import com.pittchallenge.buylite.BuyOrderItem;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * This Class represents an entity which consists of the host, list of customerPayloads, and ItemCatalog for the database.
 * It also has the pickup location that the host sets. It has a key which is the unique identity for a particular instance
 * when pushing it to the Database.
 */
public class BuyOrder implements Parcelable {
    public String name;
    public String host;
    public OrderLatLng pickuplocation;
    public List<OrderPayload> customers;
    public List<BuyOrderItem> itemcatalog;
    public String key;

    public BuyOrder(){

    }

    protected BuyOrder(Parcel in){
        name = in.readString();
        host = in.readString();
        customers = new ArrayList<>();
        itemcatalog = new ArrayList<>();
        in.readList(customers,OrderPayload.class.getClassLoader());
        in.readList(itemcatalog, BuyOrderItem.class.getClassLoader());
        String str = in.readString();

        if(str != null){
            String[] arr = str.split(",");
            pickuplocation = new OrderLatLng(Double.parseDouble(arr[0]), Double.parseDouble(arr[1]));
        }
        else
            Log.d("BuyOrder: ", "The OrderLatLng string is null for some reason");

        if(pickuplocation == null)
            Log.d("BuyOrder: ", "Pickuplocation OrderLatLng object is null in parcel constructor.");
    }

    public BuyOrder(String name, String host, OrderLatLng loc){
        this.name = name;
        this.host = host;
        this.pickuplocation = loc;
        customers = new ArrayList<>();
        itemcatalog = new ArrayList<>();
    }

    public void addCustomer(OrderPayload cust){
        customers.add(cust);
    }

    public void addToCatalog(BuyOrderItem item){
        itemcatalog.add(item);
    }

    public Map<String, Object> toMap(){
        Map<String, Object> map = new HashMap<>();
        map.put("name", name);
        map.put("host", host);
        map.put("location", pickuplocation);
        map.put("payload", customers);
        map.put("itemcatalog", itemcatalog);
        return map;
    }



//    public BuyOrderItem getItem(int i){
//        return itemcatalog.get(i);
//    }


    public static Parcelable.Creator<BuyOrder> CREATOR = new Creator<BuyOrder>() {
        @Override
        public BuyOrder createFromParcel(Parcel parcel) {
            return new BuyOrder(parcel);
        }

        @Override
        public BuyOrder[] newArray(int i) {
            return new BuyOrder[0];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
            parcel.writeString(name);
            parcel.writeString(host);
            parcel.writeList(customers);
            parcel.writeList(itemcatalog);
            parcel.writeString(pickuplocation.toString());

    }

    public String toString(){
        StringBuilder builder = new StringBuilder();
        builder.append(name + "\n");
        builder.append(host + "\n");
        builder.append(customers + "\n");
        builder.append(itemcatalog + "\n");
        builder.append(pickuplocation + "\n");
        return builder.toString();

    }

    public String getCatalog(){
        StringBuilder builder = new StringBuilder();
        for(BuyOrderItem i : itemcatalog){
            builder.append(i + ",");
        }
        return itemcatalog.toString();
    }

}
