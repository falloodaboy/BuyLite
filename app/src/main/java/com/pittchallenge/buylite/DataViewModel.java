package com.pittchallenge.buylite;


import android.util.Log;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.LiveData;
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
import com.pittchallenge.buylite.models.OrderLatLng;
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
    private ValueEventListener listener;

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

    public MutableLiveData<List<BuyOrder>> exposeList(){
        if(listwatcher == null)
            return listwatcher = new MutableLiveData<>();
        else
            return listwatcher;
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
    private void setBuyOrdersList(DatabaseReference ref){
        ValueEventListener orderlistener = new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                List<BuyOrder> setList = new ArrayList<>();
                for(DataSnapshot snap : dataSnapshot.getChildren()){
                    if(snap != null){
                        setList.add((BuyOrder) snap.getValue());
                    }
                }
                if(!setList.isEmpty())
                    setListWatcher(setList);
                else
                    Log.d(TAG, "onDataChange: BuyOrder List DataSnapshot is null for some reason.");
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Log.d(TAG, "onCancelled: BuyOrder List read was cancelled");
                Log.d(TAG, "onCancelled: Error: " + databaseError);

            }
        };
        ref.addValueEventListener(orderlistener);


    }
    private void setListWatcher(List<BuyOrder> list){
        listwatcher.setValue(list);
        Log.d(TAG, "setListWatcher: setListWatcher has been called. listwatcher: " + listwatcher.getValue());
    }


    public void testDatabase(){
        buyorder = mBase.getReference().child("BuyOrder");
        String key = buyorder.push().getKey();
        Map<String, Object> newmap = new HashMap<>();
        BuyOrder hold = new BuyOrder("BuyOrder1","Jason Todd",new OrderLatLng(2.773,2.771));
        hold.addCustomer(new OrderPayload("Ashton Kutcher", "Ketchup", 2));
        hold.addToCatalog(new BuyOrderItem("Ketchup", "Heinz Ketchup."));
        hold.key = key;
        newmap.put(key, hold);

            listener = buyorder.child(key).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if(dataSnapshot != null){
                    BuyOrder order = dataSnapshot.getValue(BuyOrder.class);
                    if(order != null){
                        Log.d(TAG, "onDataChange: \n" + order.toString());
                    }

                    else
                        Log.d(TAG, "onDataChange: BuyOrder is null");
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Log.d(TAG, "onCancelled: The Data Operation was cancelled");
                Log.d(TAG, "onCancelled: Reason: " + databaseError);
            }
        });
        setBuyOrdersList(buyorder);
        buyorder.updateChildren(newmap);

    }
}

