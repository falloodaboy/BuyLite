package com.pittchallenge.buylite;

import android.graphics.Color;
import android.os.Bundle;

import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;
import com.pittchallenge.buylite.fragments.BuyOrderListFragment;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager.widget.ViewPager;

import java.util.ArrayList;
import java.util.List;

public class FragmentBaseTest extends AppCompatActivity {

    ViewPager viewPager2;
    LandingPageAdapter adapter;
    TabLayoutMediator mediator;
    TabLayout tabLayout;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fragment_base_test);
        viewPager2 = findViewById(R.id.view_pager2);
        tabLayout = findViewById(R.id.tab_layout);
        adapter = new LandingPageAdapter(getSupportFragmentManager(), FragmentPagerAdapter.BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT);
        viewPager2.setAdapter(adapter);
        tabLayout.setupWithViewPager(viewPager2, true);
        tabLayout.setTabTextColors(Color.RED, Color.WHITE);
        tabLayout.setBackgroundColor(getResources().getColor(R.color.colorPrimary));
    }



    class LandingPageAdapter extends FragmentPagerAdapter {
      //  FragmentManager context;
        BuyOrderListFragment fragment1, fragment2, fragment3;
        List<Fragment> fraglist;

        public LandingPageAdapter(@NonNull FragmentManager fm, int behavior) {
            super(fm, behavior);
          //  context = fm;
            fraglist = new ArrayList<>();
            fragment1 = new BuyOrderListFragment();
            fragment2 = new BuyOrderListFragment();
            fragment3 = new BuyOrderListFragment();
            fraglist.add(fragment1);
            fraglist.add(fragment2);
            fraglist.add(fragment3);
        }



        @Nullable
        @Override
        public CharSequence getPageTitle(int position) {
            if(position == 0){
                return "User Profile";
            }
            else if(position == 1){
                return "My BuyOrders";
            }
            else if(position == 2){
                return "BuyOrderList";
            }
            else {
                return "Extraneous";
            }
        }

        @NonNull
        @Override
        public Fragment getItem(int position) {
            return fraglist.get(position);
        }

        @Override
        public int getCount() {
            return 3;
        }
    }
}
