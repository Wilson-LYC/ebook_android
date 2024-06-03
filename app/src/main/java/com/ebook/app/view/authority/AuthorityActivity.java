package com.ebook.app.view.authority;

import android.os.Bundle;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.viewpager2.widget.ViewPager2;

import com.ebook.app.R;
import com.ebook.app.view.authority.adapter.AuthorityPagerAdapter;
import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;

public class AuthorityActivity extends AppCompatActivity {
    private ViewPager2 viewPager;
    private TabLayout tabLayout;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_authority);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.authority_activity), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        viewPager = findViewById(R.id.authority_view_pager);
        tabLayout = findViewById(R.id.authority_tab_layout);
        AuthorityPagerAdapter authorityPagerAdapter = new AuthorityPagerAdapter(getSupportFragmentManager(), getLifecycle());
        viewPager.setAdapter(authorityPagerAdapter);
        // 使用TabLayoutMediator来连接TabLayout与ViewPager2
        new TabLayoutMediator(tabLayout, viewPager, (tab, position) -> {
            switch (position) {
                case 0:
                    tab.setText("登录");
                    break;
                case 1:
                    tab.setText("注册");
                    break;
            }
        }).attach();
    }

    public void changeToRegister() {
        viewPager.setCurrentItem(1);
    }
    public void changeToLogin() {
        viewPager.setCurrentItem(0);
    }
}