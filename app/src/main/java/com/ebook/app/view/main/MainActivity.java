package com.ebook.app.view.main;

import android.os.Bundle;

import com.ebook.app.R;
import com.ebook.app.databinding.ActivityMainBinding;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

public class MainActivity extends AppCompatActivity {
    private ActivityMainBinding binding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        BottomNavigationView navView = findViewById(R.id.main_nav_view);
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        navView.setItemIconTintList(null);
        AppBarConfiguration appBarConfiguration = new AppBarConfiguration.Builder(
                R.id.main_nav_home, R.id.main_nav_ai, R.id.main_nav_mine)
                .build();
        NavController navController = Navigation.findNavController(this, R.id.main_nav_host_fragment);
        NavigationUI.setupActionBarWithNavController(this, navController, appBarConfiguration);
        NavigationUI.setupWithNavController(binding.mainNavView, navController);
    }
}