package com.pittchallenge.buylite.fragments;


import android.app.SearchManager;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.Filter;
import android.widget.ImageButton;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.SearchView;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.pittchallenge.buylite.BuyOrderFragListAdapter;
import com.pittchallenge.buylite.DataViewModel;
import com.pittchallenge.buylite.R;

public class BuyOrderListFragment extends Fragment {
//TODO: Check for layout options to ensure fragment is dynamic
        private RecyclerView orderlist;
        private DataViewModel datamodel;
        private static final String TAG = "BuyOrderListFragment";
        private SearchView search;
        private  BuyOrderFragListAdapter adapter;

        public BuyOrderListFragment() {
            //required for building fragment
         }



    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,Bundle savedInstanceState) {
       View view  = inflater.inflate(R.layout.fragment_buy_order_list, container);
       orderlist = view.findViewById(R.id.fragbuyorderlist);
       datamodel = ViewModelProviders.of(this).get(DataViewModel.class);
       orderlist.setLayoutManager(new LinearLayoutManager(getContext()));
       adapter = new BuyOrderFragListAdapter(datamodel.exposeList().getValue());
       orderlist.setAdapter(adapter);

       return view;
    }


    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);

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


}
