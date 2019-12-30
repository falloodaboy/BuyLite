package com.pittchallenge.buylite;


import android.renderscript.Sampler;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.pittchallenge.buylite.models.BuyOrder;
import com.pittchallenge.buylite.models.OrderLatLng;
import com.pittchallenge.buylite.models.OrderPayload;
import com.pittchallenge.buylite.models.OrderlistItem;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class DataViewModel extends ViewModel {
    private FirebaseDatabase mBase;
    private static final String TAG = "DataViewModel: ";
    private MutableLiveData<List<BuyOrder>> liveDataBuyOrderList;
    private MutableLiveData<List<OrderlistItem>> liveDataOrderList;
    private DatabaseReference buyorderlist, orderlist;
    private ValueEventListener listener; //observe changes on a specific BuyOrder
    private ValueEventListener listListener; //observe changes on the entire Buyorder list.
    private ValueEventListener orderlistListener;


    public DataViewModel(){
        mBase = FirebaseDatabase.getInstance();
       // mBase.setPersistenceEnabled(true);//allow offline caching of data. keeps crashing. where exactly should persistence be set?
        liveDataBuyOrderList = new MutableLiveData<>();
        buyorderlist = mBase.getReference().child("BuyOrder");
        orderlist = mBase.getReference().child("Orderlist");
        setBuyOrdersList(buyorderlist);
        setOrderList(orderlist);

    }


   public LiveData<List<BuyOrder>> getBuyOrderList(){
        if(liveDataBuyOrderList == null){
            liveDataBuyOrderList = new MutableLiveData<>();
            setBuyOrdersList(buyorderlist);
            return liveDataBuyOrderList;
        }
        else
            return liveDataBuyOrderList;
   }

//Detach all listeners attached in this ViewModel.
    @Override
    protected void onCleared() {
        if(buyorderlist != null && listener != null){
            buyorderlist.removeEventListener(listener);

        }
        if(buyorderlist != null && listListener != null){
            buyorderlist.removeEventListener(listListener);
        }
        if(orderlist != null && orderlistListener != null){
            orderlist.removeEventListener(orderlistListener);
        }

        super.onCleared();
    }

    /**
    * Get the DataSnapShot at the BuyOrder child
    * with a list of the buyorders and set the
    * MutableLiveData liveDataBuyOrderList with it. liveDataBuyOrderList
    * cannot be null.
    **/
    private void setBuyOrdersList(DatabaseReference ref){
           if(listListener == null){
               listListener = new ValueEventListener() {
                   @Override
                   public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                       Log.d(TAG, "onDataChange: Listener called after updating buyorderslist.");
                       List<BuyOrder> setList = new ArrayList<>();
                       for(DataSnapshot snap : dataSnapshot.getChildren()){
                           if(snap != null){
                               setList.add(snap.getValue(BuyOrder.class));
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
               ref.addValueEventListener(listListener);
           }

    }

    private void setOrderList(DatabaseReference ref){

        orderlistListener = new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                List<OrderlistItem> temporderlist = new ArrayList<>();
                    for(DataSnapshot snapshot : dataSnapshot.getChildren()){
                        if(snapshot != null){
                            temporderlist.add(snapshot.getValue(OrderlistItem.class));
                        }
                    }
                    setOrderlist(temporderlist);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Log.d(TAG, "onCancelled: Attempted to read DataSnapshot at Orderlist but operation was cancelled. Error: " + databaseError);
            }
        };
        ref.addValueEventListener(orderlistListener);
    }

    private void setOrderlist(List<OrderlistItem> temporderlist) {
        liveDataOrderList.setValue(temporderlist);
        Log.d(TAG, "setOrderlist: called! liveDataOrderlist: " + liveDataOrderList);
    }

    public LiveData<List<OrderlistItem>> getOrderlist(){
        return liveDataOrderList;
    }

    private void setListWatcher(List<BuyOrder> list){
        liveDataBuyOrderList.setValue(list);
        Log.d(TAG, "setListWatcher: setListWatcher has been called. liveDataBuyOrderList: " + liveDataBuyOrderList.getValue());
    }


    public void writeNewOrderListItemtoDatabase(OrderlistItem item){
        Map<String, Object> newchild = new HashMap<>();
        newchild.put(item.order_id, item);
        orderlist.updateChildren(newchild);
    }

   public void writeNewBuyOrdertoDatabase(BuyOrder order){
        if(order.key.isEmpty()){
            throw new RuntimeException("writeBuyOrdertoDatabase called but order.key is empty or null.");
        }
        else{
            Map<String, Object> newmap = new HashMap<>();
            order.key = buyorderlist.push().getKey();
            newmap.put(order.key, order);
            buyorderlist.updateChildren(newmap);
        }

   }

    @SuppressWarnings("null")
   public BuyOrder getBuyOrder(String key){

           for(int i = 0; i < liveDataBuyOrderList.getValue().size(); i++){
               if(liveDataBuyOrderList.getValue().get(i).key.equals(key)){
                   return liveDataBuyOrderList.getValue().get(i);
               }
           }

           return null;
   }
























    public void testDatabase(){
        buyorderlist = mBase.getReference().child("BuyOrder");
        String key = buyorderlist.push().getKey();
        Map<String, Object> newmap = new HashMap<>();
        BuyOrder hold = new BuyOrder("BuyOrder1","Jason Todd",new OrderLatLng(2.773,2.771));
        hold.addCustomer(new OrderPayload("Ashton Kutcher", "Ketchup", 2));
        hold.addToCatalog(new BuyOrderItem("Ketchup", "Heinz Ketchup."));
        hold.key = key;
        newmap.put(key, hold);

            listener = buyorderlist.child(key).addValueEventListener(new ValueEventListener() {
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
        setBuyOrdersList(buyorderlist);
        buyorderlist.updateChildren(newmap);

    }
}

