package com.pittchallenge.buylite.models;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.android.gms.maps.model.LatLng;
import com.pittchallenge.buylite.BuyOrderItem;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class BuyOrder implements Parcelable {
    public String name;
    public String host;
    public  LatLng pickuplocation;
    public List<OrderPayload> customers;
    public List<BuyOrderItem> itemcatalog;

    public BuyOrder(){

    }

    protected BuyOrder(Parcel in){
        name = in.readString();
        host = in.readString();
        customers = new ArrayList<>();
        itemcatalog = new ArrayList<>();
        in.readList(customers,OrderPayload.class.getClassLoader());
        in.readList(itemcatalog, BuyOrderItem.class.getClassLoader());
    }

    public BuyOrder(String name, String host, LatLng loc){
        this.name = name;
        this.host = host;
        this.pickuplocation = loc;
        customers = new ArrayList<>();
        itemcatalog = new ArrayList<>();
    }

    public void addCustomer(OrderPayload cust){
        customers.add(cust);
    }

    public Map<String, Object> toMap(){
        Map<String, Object> map = new HashMap<>();
        map.put("name", name);
        map.put("host", host);
        map.put("location", pickuplocation);
        map.put("payload", customers);
        return map;
    }

    public void addToCatalog(BuyOrderItem item){
        itemcatalog.add(item);
    }

    public BuyOrderItem getItem(int i){
        return itemcatalog.get(i);
    }


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
    }



}
