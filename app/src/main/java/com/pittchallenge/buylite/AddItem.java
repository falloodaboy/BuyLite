package com.pittchallenge.buylite;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class AddItem extends AppCompatActivity {
    private String TAG = "AddItemActivity: ";
    private TextView namefield, description;
    private FloatingActionButton fabadditem;
    public String NameOfItem = "ITEM_NAME";
    public String DescriptionOfItem = "ITEM_DESCRIPTION";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_item);
        Intent intent = getIntent();
        if(intent == null)
        {
            Log.d(TAG, "onCreate: Intent should not be null.");
            setResult(RESULT_CANCELED);
            finish();
        }
        namefield = findViewById(R.id.AddNewItemNameField);
        description = findViewById(R.id.AddNewItemDescriptionField);
        fabadditem = findViewById(R.id.AddItemFAB);

        fabadditem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(TextUtils.isEmpty(namefield.toString()))
                    Toast.makeText(getApplicationContext(), "Name field cannot be null", Toast.LENGTH_SHORT).show();
                else{
                    Intent data = new Intent();
                    data.putExtra(NameOfItem, namefield.getText().toString());
                    data.putExtra(DescriptionOfItem, description.getText().toString());
                    setResult(RESULT_OK, data);
                    AddItem.this.finish();
                }

            }
        });
    }


}
