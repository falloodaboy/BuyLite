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

import com.google.firebase.auth.FirebaseAuth;

public class FAQ_Activity extends AppCompatActivity {
        private ActionBar actionbar;
        private DataViewModel datamodel;
        private static final String TAG = "FAQ_Activity";
        private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_faq_);
        actionbar = getSupportActionBar();
        datamodel = ViewModelProviders.of(this).get(DataViewModel.class);
        mAuth = FirebaseAuth.getInstance();
        if(actionbar != null) {
            actionbar.setDisplayHomeAsUpEnabled(true);
        }
        else
            Log.d(TAG, "actionbar is null! ");
        datamodel.testDatabase();
    }

    @Override
    protected void onStart() {
        super.onStart();
     //   datamodel.testDatabase();
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
            if(mAuth.getCurrentUser() != null) {
                mAuth.signOut();
                Toast.makeText(getApplicationContext(), "Signed Out User", Toast.LENGTH_SHORT).show();
                this.finish();
            }
            return true;
        }
        else if(item.getItemId() == R.id.CREATEBUYORDER){
            if(mAuth.getCurrentUser() != null){
                startActivity(new Intent(getApplicationContext(), CreateBuyOrder.class));

            }
            return true;
        }
        else{
            return super.onOptionsItemSelected(item);
        }
    }
}
