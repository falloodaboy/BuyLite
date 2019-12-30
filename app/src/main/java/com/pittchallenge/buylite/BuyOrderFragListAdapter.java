package com.pittchallenge.buylite;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.ColorFilter;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.RecyclerView;

import com.pittchallenge.buylite.models.BuyOrder;

import java.io.UncheckedIOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import static android.content.ContentValues.TAG;

public class BuyOrderFragListAdapter extends RecyclerView.Adapter<BuyOrderFragListAdapter.BuyOrderHolder> implements Filterable {
        private List<BuyOrder> adapterlist;
        private List<BuyOrder> secondarylist;
        private Filter listFilter;
        private DataViewModel model;
        private onBuyOrderDetailsListener mdetailslistener;
        private String TAG = "BuyOrderFragListAdapter";



        public BuyOrderFragListAdapter(List<BuyOrder> list, DataViewModel model, onBuyOrderDetailsListener newdetialslistener ){
            adapterlist = list;
            secondarylist = new ArrayList<>(adapterlist);
            mdetailslistener = newdetialslistener;
            this.model = model;
            listFilter = new Filter() {
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
                @SuppressWarnings("unchecked")
                @Override
                protected void publishResults(CharSequence charSequence,  FilterResults filterResults) {
                    adapterlist.clear();
                    adapterlist.addAll((List<BuyOrder>) filterResults.values);
                    notifyDataSetChanged();

                }
            };
        }

    @NonNull
    @Override
    public BuyOrderHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater flater = LayoutInflater.from(parent.getContext());
        View view = flater.inflate(R.layout.fraglistitem, parent, false);
        return new BuyOrderHolder(view, mdetailslistener);
    }

    @Override
    public void onBindViewHolder(@NonNull BuyOrderHolder holder, int position) {
            //define what data goes into viewholders at each position and in what view elements.
            //define behavior of views here as well.
            //this will be provided by the Fragment to fill in here in the adapter.
            //change this into something more pleasant to look at.
            String titleInfo = adapterlist.get(position).name; //+ //" " + adapterlist.get(position).host + " " + adapterlist.get(position).pickuplocation.toString();

            holder.setTitleInfo(titleInfo);
            holder.FillItems(adapterlist.get(position).getCatalog());
        }

    @Override
    public void onViewDetachedFromWindow(@NonNull BuyOrderHolder holder) {
         //may not need this to remove listener from views once they leave
        super.onViewDetachedFromWindow(holder);
    }

    @Override
    public int getItemCount() {
        return adapterlist.size();
    }

    private void reloadList(){
            adapterlist = new ArrayList<>(secondarylist);
    }

    @Override
    public Filter getFilter() {
        return listFilter;
    }

    public void setAdapterlist(List<BuyOrder> newlist){
            adapterlist = newlist;
    }


    class BuyOrderHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        //define fields here for the RecyclerView ViewHolder
        //create the ViewHolder xml for this recyclerview
        private TextView OrderlistViewHolderTitle, OrderlistViewHolderItems;
        private ImageView OrderlistViewHolderMoreInfo;
        private onBuyOrderDetailsListener mlistener;


        public BuyOrderHolder(View view, onBuyOrderDetailsListener listener){
            super(view);
            OrderlistViewHolderTitle = view.findViewById(R.id.OrderlistViewHolder);
            OrderlistViewHolderItems = view.findViewById(R.id.OrderlistViewHolderListItems);
            OrderlistViewHolderMoreInfo = view.findViewById(R.id.OrderlistViewHolderMoreInfo);
            OrderlistViewHolderMoreInfo.setBackgroundResource(R.drawable.ic_launcher_foreground);
            mlistener = listener;
            view.setOnClickListener(this);
            OrderlistViewHolderMoreInfo.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mlistener.onBuyOrderDetailsClick(getAdapterPosition());
                }
            });
        }

        public void FillItems(String item){
            OrderlistViewHolderItems.setText(item);
        }


        public void setTitleInfo(String info){
            OrderlistViewHolderTitle.setText(info);
        }

        //provide functionality to both the moreinfo imageview and the entire viewholder itself.
        @Override
        public void onClick(View v) {
            mlistener.onBuyOrderDetailsClick(getAdapterPosition());
        }
    }

    public interface onBuyOrderDetailsListener {

      public void onBuyOrderDetailsClick(int pos);

    }














    //may be used later if swiping is allowed
    class BuyOrderTouchHelper extends ItemTouchHelper.SimpleCallback{

            private BuyOrderFragListAdapter mAdapter;

        public BuyOrderTouchHelper(int Dragdir, int Swipedir, BuyOrderFragListAdapter adapter){
            super(Dragdir, Swipedir);
            mAdapter  = adapter;
        }


        @Override
        public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
            return false;
        }

        @Override
        public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {
            if(direction == ItemTouchHelper.RIGHT || direction == ItemTouchHelper.LEFT){
                mAdapter.adapterlist.remove(viewHolder.getAdapterPosition());
                mAdapter.notifyItemRemoved(viewHolder.getAdapterPosition());
            }
        }

        @Override
        public void onChildDraw(@NonNull Canvas c, @NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, float dX, float dY, int actionState, boolean isCurrentlyActive) {
            super.onChildDraw(c, recyclerView, viewHolder, dX, dY, actionState, isCurrentlyActive);

            if(actionState == ItemTouchHelper.ACTION_STATE_SWIPE){
                ColorDrawable drawable = new ColorDrawable(Color.RED);
                drawable.setBounds(0, recyclerView.getTop(),  recyclerView.getLeft() + (int) dX, recyclerView.getBottom());
                drawable.draw(c);
            }

        }

        @Override
        public boolean isLongPressDragEnabled() {
            return false;
        }

        @Override
        public boolean isItemViewSwipeEnabled() {
            return true;
        }
    }
}
