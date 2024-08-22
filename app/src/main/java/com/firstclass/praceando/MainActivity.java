package com.firstclass.praceando;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.os.Bundle;
import android.view.MenuItem;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;

public class MainActivity extends AppCompatActivity implements BottomNavigationView.OnNavigationItemSelectedListener {
    private BottomNavigationView navigationView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        navigationView = (BottomNavigationView) findViewById(R.id.bottonNavigation);
        navigationView.setOnNavigationItemSelectedListener(this);


        loadFragment(new HomeFragment(), true);
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        int itemId = item.getItemId();

        if (itemId == R.id.navigation_home){
            loadFragment(new HomeFragment(), false);

        } else if (itemId == R.id.navigation_map) {
            loadFragment(new MapFragment(), false);

        } else if (itemId == R.id.navigation_calendar) {
            loadFragment(new CalendarFragment(), false);

        } else if (itemId == R.id.navigation_market) {
            loadFragment(new MarketFragment(), false);

        } else if (itemId == R.id.navigation_perfil) {
            loadFragment(new PerfilFragment(), false);

        }

        return true;

    }

    private void loadFragment(Fragment fragment, boolean isAppInitialized ) {
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction  fragmentTransaction = fragmentManager.beginTransaction();

        if (isAppInitialized){
            fragmentTransaction.add(R.id.frameLayout, fragment);

        } else {
            fragmentTransaction.replace(R.id.frameLayout, fragment);
        }

        fragmentTransaction.commit();
    }
}