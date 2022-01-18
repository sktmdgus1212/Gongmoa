package com.example.gongmoa;

import android.os.Bundle;
import android.view.MenuItem;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    HomeActivity homeActivity;
    MycontestActivity mycontestActivity;
    InfoActivity infoActivity;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        homeActivity = new HomeActivity();
        mycontestActivity = new MycontestActivity();
        infoActivity = new InfoActivity();

        getSupportFragmentManager().beginTransaction().replace(R.id.container, homeActivity).commit();
        BottomNavigationView bottom_menu = findViewById(R.id.bottom_menu);
        bottom_menu.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch(item.getItemId()) {
                    case R.id.first_tab:
                        getSupportFragmentManager().beginTransaction().replace(R.id.container, homeActivity).commit();
                        return true;
                    case R.id.second_tab:
                        getSupportFragmentManager().beginTransaction().replace(R.id.container, mycontestActivity).commit();
                        return true;
                    case R.id.third_tab:
                        getSupportFragmentManager().beginTransaction().replace(R.id.container, infoActivity).commit();
                        return true;

                }
                return false;
            }
        });



    }


}