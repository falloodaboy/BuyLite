package com.pittchallenge.buylite;

import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.pittchallenge.buylite.models.BuyOrder;

import java.io.UncheckedIOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class BuyOrderFragListAdapter extends RecyclerView.Adapter<BuyOrderFragListAdapter.BuyOrderHolder> implements Filterable {
        private List<BuyOrder> adapterlist;
        private List<BuyOrder> secondarylist;


        public BuyOrderFragListAdapter(List<BuyOrder> list){
            adapterlist = list;
            secondarylist = new ArrayList<>(adapterlist);
        }

    @NonNull
    @Override
    public BuyOrderHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater flater = LayoutInflater.from(parent.getContext());
        View view = flater.inflate(R.layout.fraglistitem, parent, false);
        return new BuyOrderHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull BuyOrderHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return 0;
    }

    private void reloadList(){
            adapterlist = new ArrayList<>(secondarylist);
    }

    @Override
    public Filter getFilter() {
        return listFilter;
    }

    Filter listFilter = new Filter() {
        @Override
        protected FilterResults performFiltering(CharSequence constraint) {
                 reloadList();
                List<BuyOrder> filteredList = new ArrayList<>();
                if(constraint == null || TextUtils.isEmpty(constraint)){
                    filteredList.addAll(adapterlist);
                }
                else{
                    String pattern = constraint.toString().toLowerCase().trim();
                    for(BuyOrder order: adapterlist){
                        if(order.name.toLowerCase().contains(pattern)){
                            filteredList.add(order);
                        }
                    }
                }
                FilterResults result = new FilterResults();
                result.values = filteredList;
                return result;
        }
        @Override
        protected void publishResults(CharSequence charSequence, FilterResults filterResults) {
                adapterlist.clear();
                adapterlist.addAll((List<BuyOrder>) filterResults.values);
                notifyDataSetChanged();

        }
    };

    class BuyOrderHolder extends RecyclerView.ViewHolder {

        public BuyOrderHolder(View view){
            super(view);
        }

    }
}
