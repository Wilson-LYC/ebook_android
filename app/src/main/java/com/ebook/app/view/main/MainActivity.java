package com.ebook.app.view.main;

import android.os.Bundle;

import com.ebook.app.R;
import com.ebook.app.databinding.FrameMainBinding;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

public class MainActivity extends AppCompatActivity {
    private FrameMainBinding binding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        binding = FrameMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.activity), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        BottomNavigationView navView = findViewById(R.id.main_nav_button);
        navView.setItemIconTintList(null);
        //底部导航栏
        NavController navController = Navigation.findNavController(this, R.id.main_nav_page);
        NavigationUI.setupWithNavController(binding.mainNavButton, navController);
        //顶部导航栏
//        AppBarConfiguration appBarConfiguration = new AppBarConfiguration.Builder(
//                R.id.main_nav_home, R.id.main_nav_ai, R.id.main_nav_mine)
//                .build();
//        NavigationUI.setupActionBarWithNavController(this, navController, appBarConfiguration);
    }
}