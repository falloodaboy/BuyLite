package com.pittchallenge.buylite;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NavUtils;

import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.widget.Toolbar;

public class FAQ_Activity extends AppCompatActivity {
        ActionBar actionbar;
        private static final String TAG = "FAQ_Activity";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_faq_);
        actionbar = getSupportActionBar();
        if(actionbar != null)
        actionbar.setDisplayHomeAsUpEnabled(true);
        else
            Log.d(TAG, "actionbar is null! ");

    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if(item.getItemId() == android.R.id.home){
            this.finish();
            return true;
        }
        else{
            return true;
        }
    }
}
