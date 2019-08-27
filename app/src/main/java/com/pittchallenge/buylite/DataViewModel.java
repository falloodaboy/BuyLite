package com.pittchallenge.buylite;


import android.util.Log;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModel;

import com.google.android.gms.maps.model.LatLng;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.pittchallenge.buylite.models.BuyOrder;
import com.pittchallenge.buylite.models.OrderPayload;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class DataViewModel extends ViewModel {
    private FirebaseDatabase mBase;
    private static final String TAG = "DataViewModel: ";
    private MutableLiveData<List<BuyOrder>> listwatcher;
    private DatabaseReference buyorder;
    private ChildEventListener listener;

    public DataViewModel(){

        mBase = FirebaseDatabase.getInstance();
        listwatcher = new MutableLiveData<>();
    }

    /**
     * Add an observer to the LiveData object which
     * will be notified whenever the data in it
     * changes. Perform any changes to the UI of
     * the activity with an instance of this view model
     * using this method.
     **/
    public void attachObserverToItemList(LifecycleOwner owner, Observer<List<BuyOrder>> observer){
        listwatcher.observe(owner, observer);
    }

    /**
     * Observers which are attached to this livedata
     * stay with the owner until it is destroyed.
     * Call this method to free memory when destroying
     * your LifeCycleOwner.
     **/
    public void removeObservers(LifecycleOwner owner){
            listwatcher.removeObservers(owner);

    }

    @Override
    protected void onCleared() {
        if(buyorder != null && listener != null){
            buyorder.removeEventListener(listener);

        }
        super.onCleared();
    }

    /**
    * Get the DataSnapShot at the BuyOrder child
    * with a list of the buyorders and set the
    * MutableLiveData listwatcher with it. listwatcher
    * cannot be null.
    **/
    protected void setBuyOrdersList(){
        ValueEventListener orderlistener = new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                List<BuyOrder> list = new ArrayList<>();
               for(DataSnapshot snap : dataSnapshot.getChildren()){
                    list.add(snap.getValue(BuyOrder.class));
               }
                listwatcher.setValue(list);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Log.d(TAG, "onCancelled: BuyOrder List read was cancelled");
                Log.d(TAG, "onCancelled: Error: " + databaseError);

            }
        };
        Log.d(TAG, "setBuyOrdersList: listwatcher: " + listwatcher);

    }



    public void testDatabase(){
        buyorder = mBase.getReference().child("BuyOrder");
        String key = buyorder.push().getKey();
        Map<String, Object> newmap = new HashMap<>();
        BuyOrder hold = new BuyOrder("BuyOrder1","Jason Todd",new LatLng(2.773,2.771));
        hold.addCustomer(new OrderPayload("Ashton Kutcher", "Ketchup", 2));
        hold.addToCatalog(new BuyOrderItem("Ketchup", "Heinz Ketchup."));

        newmap.put(key, hold);
        newmap.put("order_id", key);
        listener = buyorder.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                Log.d(TAG, " onChildAdded: Successfully added data");
                //Log.d(TAG, dataSnapshot.getValue().toString());
            }

            @Override
            public void onChildChanged(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

            }

            @Override
            public void onChildRemoved(@NonNull DataSnapshot dataSnapshot) {

            }

            @Override
            public void onChildMoved(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Log.d(TAG, "onCancelled: Failed to add data.");
                Log.d(TAG, "onCancelled: " + databaseError);
            }
        });
        buyorder.updateChildren(newmap);
        setBuyOrdersList();
    }
}

