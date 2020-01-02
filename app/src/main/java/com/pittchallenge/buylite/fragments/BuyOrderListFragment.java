package com.pittchallenge.buylite.fragments;


import android.annotation.SuppressLint;
import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.transition.TransitionManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ActionMenuView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.SearchView;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.LifecycleObserver;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.pittchallenge.buylite.BuyOrderFragListAdapter;
import com.pittchallenge.buylite.DataViewModel;
import com.pittchallenge.buylite.DummyAdapter;
import com.pittchallenge.buylite.R;
import com.pittchallenge.buylite.models.BuyOrder;
import com.pittchallenge.buylite.models.OrderLatLng;

import java.util.ArrayList;
import java.util.List;

public class BuyOrderListFragment extends Fragment implements BuyOrderFragListAdapter.onBuyOrderDetailsListener{
//TODO: Check for layout options to ensure fragment is dynamic
        private RecyclerView orderlist;
        private DataViewModel datamodel;
        private static final String TAG = "BuyOrderListFragment";
        private SearchView search;
        private  BuyOrderFragListAdapter adapter;
        private DummyAdapter dumadapt;
        private LiveData<List<BuyOrder>> list;
        private TextView emptyrecyclerdisplay;
        private List<BuyOrder> dummylist;


        public BuyOrderListFragment() {
            //required for building fragment
         }

//    @Override
//    public void onViewStateRestored(@Nullable Bundle savedInstanceState) {
//        super.onViewStateRestored(savedInstanceState);
//        if(savedInstanceState != null){
//            if(savedInstanceState.getString("SEARCH") != null){
//                if(search != null){
//                    search.setQuery(savedInstanceState.getString("SEARCH"), false);
//                }
//                else{
//                    Log.d(TAG, "onViewStateRestored: Search Widget is null for some reason.");
//                }
//            }
//        }
//        else{
//            Log.d(TAG, "onViewStateRestored: bundle is null");
//        }
//
//    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);



        orderlist = view.findViewById(R.id.fragbuyorderlist);
        datamodel = ViewModelProviders.of(this).get(DataViewModel.class);
        setHasOptionsMenu(true);
        dummylist = new ArrayList<>();
        emptyrecyclerdisplay = view.findViewById(R.id.EmptyContainerFragmentText);
        emptyrecyclerdisplay.setText(R.string.no_buyorders_frag_list);
        list = datamodel.getBuyOrderList();

        for(int i=0; i < 20; i++){
            dummylist.add(new BuyOrder("BuyOrder " + i, "George Lucas", new OrderLatLng(2.0, 1.0)));
        }
        orderlist.setLayoutManager(new LinearLayoutManager(getContext()));
        adapter = new BuyOrderFragListAdapter(/*list.getValue()*/ dummylist, datamodel, this);
       //  adapter = new BuyOrderFragListAdapter(datamodel.exposeList().getValue()); //DataModel Does not expose list anymore.
        orderlist.setAdapter(adapter);
//
//       Undo this once ready to test with real data.
//       if(list.getValue() == null  || list.getValue().isEmpty() ){
//            if(emptyrecyclerdisplay.getVisibility() == View.GONE){
//                orderlist.setVisibility(View.INVISIBLE);
//                emptyrecyclerdisplay.setVisibility(View.VISIBLE);
//
//            }
//       }

        if(list != null){
            list.observe(this, new Observer<List<BuyOrder>>() {
                @Override
                public void onChanged(List<BuyOrder> buyOrders) {
                    //update UI based on list change.
                    //notify recyclerview adapter that dataset has been changed so update it.
                    adapter.setAdapterlist(buyOrders); //<-- sets the adapterlist variable to this. Should this get rid of old list object by
                    //garbage collector?
                    adapter.notifyDataSetChanged();
                }
            });
        }
        else
            Log.d(TAG, "onAttach: LiveData buyorder list is null for some reason.");
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,Bundle savedInstanceState) {
       return inflater.inflate(R.layout.fragment_buy_order_list, container, false);
    }



    @Override
    public void onResume() {
        super.onResume();
            
//        if(list.getValue() != null && list.getValue().isEmpty()){
//            if(emptyrecyclerdisplay.getVisibility() == View.GONE){
//                emptyrecyclerdisplay.setVisibility(View.VISIBLE);
//                orderlist.setVisibility(View.INVISIBLE);
//            }
//        }

    }




    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
        inflater.inflate(R.menu.app_options, menu);
        search = (SearchView) menu.findItem(R.id.searchwidget).getActionView();


        search.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                adapter.getFilter().filter(newText);
                return false;
            }
        });


    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
    }


    @Override
    public void onBuyOrderDetailsClick(int pos) {
       // Intent intent = new Intent();
        Log.d(TAG, "onBuyOrderDetailsClick: ViewHolder or imageView have been clicked.");
    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        if(search != null && search.isFocused()){
            outState.putString("SEARCH", search.getQuery().toString());
            Log.d(TAG, "onSaveInstanceState: put search in outState");
        }

    }

}
