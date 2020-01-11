package com.pittchallenge.buylite;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;


import java.util.List;


public class CreateBuyOrderAdapter extends RecyclerView.Adapter<CreateBuyOrderAdapter.OrderViewHolder> {
    //get List of BuyOrders here for adapter.
    protected LayoutInflater flater;
    protected List<BuyOrderItem> adaptorderlist;

    public CreateBuyOrderAdapter(Context cont, List<BuyOrderItem> list){
        flater = LayoutInflater.from(cont);
        adaptorderlist = list;
    }

    @NonNull
    @Override
    public OrderViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = flater.inflate(R.layout.buyorderlistitem, parent,false);
        return new OrderViewHolder(view, this);
    }

    @Override
    public void onBindViewHolder(@NonNull OrderViewHolder holder, int position) {
            holder.getDisplay().setText(adaptorderlist.get(position).itemName);
            holder.getItemDescription().setText(adaptorderlist.get(position).description);

    }

    @Override
    public int getItemCount() {
        return adaptorderlist.size();
    }


    class OrderViewHolder extends RecyclerView.ViewHolder{
       private CreateBuyOrderAdapter adapter;
       private TextView itemDisplay, itemDescription;

        public OrderViewHolder(View itemView, CreateBuyOrderAdapter dapt){
            super(itemView);
            adapter = dapt;
            itemDisplay = itemView.findViewById(R.id.item_display);
            itemDescription = itemView.findViewById(R.id.item_discription);

        }

        public TextView getDisplay(){
            return itemDisplay;
        }

        public TextView getItemDescription(){
            return itemDescription;
        }

    }
}
