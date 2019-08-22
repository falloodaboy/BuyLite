package com.pittchallenge.buylite;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NavUtils;
import androidx.lifecycle.ViewModelProviders;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.Toast;
import android.widget.Toolbar;

public class FAQ_Activity extends AppCompatActivity {
        private ActionBar actionbar;
        private UserViewModel usermodel;
        private static final String TAG = "FAQ_Activity";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_faq_);
        actionbar = getSupportActionBar();
        usermodel = ViewModelProviders.of(this).get(UserViewModel.class);
        if(actionbar != null) {
            actionbar.setDisplayHomeAsUpEnabled(true);
        }
        else
            Log.d(TAG, "actionbar is null! ");
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.app_options, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if(item.getItemId() == android.R.id.home){
            this.finish();
            return true;
        }
        else if(item.getItemId() == R.id.SIGNOUT){
            if(usermodel.isUserAuthenticated()) {
                usermodel.SignOutUser();
                Toast.makeText(getApplicationContext(), "Signed Out User", Toast.LENGTH_SHORT).show();
                this.finish();
            }
            return true;
        }
        else if(item.getItemId() == R.id.CREATEBUYORDER){
            if(usermodel.isUserAuthenticated()){
                startActivity(new Intent(getApplicationContext(), CreateBuyOrder.class));

            }
            return true;
        }
        else{
            return super.onOptionsItemSelected(item);
        }
    }
}
