package com.example.myapp_broke_petr;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import android.os.Bundle;
import android.view.MenuItem;
import android.widget.Toast;

import com.example.myapp_broke_petr.Fragments.AddItemFragment;
import com.example.myapp_broke_petr.Fragments.HomeFragment;
import com.example.myapp_broke_petr.Fragments.ReportsFragment;
import com.example.myapp_broke_petr.Fragments.SearchFragment;
import com.example.myapp_broke_petr.Fragments.ViewTransFragment;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class Main2Activity extends AppCompatActivity {

    private BottomNavigationView bottomNavigationView;
    final FragmentManager fragmentManager = getSupportFragmentManager();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
        bottomNavigationView=findViewById(R.id.bottom_navigation);

        //set up listener for bottom nagvigation
        //define where it should go when user clicks on each tab
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                Fragment fragment = new Fragment();
                if(menuItem.getItemId() == R.id.opHome)
                {
                    fragment= new HomeFragment();

                    //Toast.makeText(Main2Activity.this, "Clicked on Home", Toast.LENGTH_SHORT).show();

                }
                else if(menuItem.getItemId() == R.id.opAdd)
                {
                    //Toast.makeText(Main2Activity.this, "Clicked on Add", Toast.LENGTH_SHORT).show();
                    fragment= new AddItemFragment();


                }

                else if(menuItem.getItemId() == R.id.opTrans)
                {
                    fragment= new ViewTransFragment();

                    //Toast.makeText(Main2Activity.this, "Clicked on Trans", Toast.LENGTH_SHORT).show();

                }
                else if(menuItem.getItemId() == R.id.opSearch)
                {
                    //Toast.makeText(Main2Activity.this, "Clicked on Search", Toast.LENGTH_SHORT).show();
                    fragment= new SearchFragment();


                }
                else if(menuItem.getItemId() == R.id.opShowReport)
                {
                    //Toast.makeText(Main2Activity.this, "Clicked on Search", Toast.LENGTH_SHORT).show();
                    fragment= new ReportsFragment();


                }



                fragmentManager.beginTransaction().replace(R.id.flContainer, fragment).commit();

                return true;
            }
        });
        // Set default selection
        bottomNavigationView.setSelectedItemId(R.id.opHome);

    }
}