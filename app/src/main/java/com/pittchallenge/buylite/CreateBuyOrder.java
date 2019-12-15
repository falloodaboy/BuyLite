package com.pittchallenge.buylite;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.pittchallenge.buylite.models.OrderLatLng;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.pittchallenge.buylite.models.BuyOrder;

import java.util.ArrayList;
import java.util.List;

public class CreateBuyOrder extends AppCompatActivity {
      private int GET_ORDER = 0;
      private String TAG = "CreateBuyOrder: ";
      private  EditText namefield;
      private EditText storefield;
      private Button location, addItem;
      private FloatingActionButton fabadd;
      private RecyclerView itemlist;
      private DataViewModel datamodel;
      private  UserViewModel usermodel;
      private List<BuyOrderItem> lister;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_buy_order);

        //layout references
        namefield = findViewById(R.id.CreateBuyOrderNameField);
        storefield = findViewById(R.id.CreateBuyOrderStoreField);
        location = findViewById(R.id.LocationButton);
        addItem = findViewById(R.id.AddItemButton);
        fabadd = findViewById(R.id.FinishBuyOrderCreationButton);
        itemlist = findViewById(R.id.ItemRecyclerView);

        usermodel = ViewModelProviders.of(this).get(UserViewModel.class);
        datamodel = ViewModelProviders.of(this).get(DataViewModel.class);

        lister  = new ArrayList<>();
        itemlist.setLayoutManager(new LinearLayoutManager(this));
        itemlist.setAdapter(new CreateBuyOrderAdapter(getApplicationContext(), lister));

        addItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivityForResult(new Intent(getApplicationContext(), AddItem.class), GET_ORDER);
            }
        });

        fabadd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(!TextUtils.isEmpty(namefield.getText()) && !TextUtils.isEmpty(storefield.getText())){
                    BuyOrder order = new BuyOrder(namefield.getText().toString(), usermodel.getUserName(), new OrderLatLng(2.111,2.222));
                    for(int i=0; i < lister.size(); i++){
                        order.addToCatalog(lister.get(i));
                        Intent newBuyOrder = new Intent();
                        newBuyOrder.putExtra("NEW_BUYORDER", order);
                        setResult(RESULT_OK);
                        CreateBuyOrder.this.finish();
                        //((CreateBuyOrder)getApplicationContext()).finish();

                    }
                }

            }
        });
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == GET_ORDER){
            if(resultCode == RESULT_OK){
                BuyOrderItem item = new BuyOrderItem(data.getStringExtra("ITEM_NAME"), data.getStringExtra("ITEM_DESCRIPTION"));
                lister.add(item);
                itemlist.getAdapter().notifyItemInserted(lister.size());
            }
            else if(resultCode == RESULT_CANCELED){
                Log.d(TAG, "onActivityResult: Item Addition was cancelled");
            }
        }
    }
}




