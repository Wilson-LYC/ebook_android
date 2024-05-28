package com.ebook.app.views.main;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.Lifecycle;
import androidx.viewpager2.adapter.FragmentStateAdapter;

public class MainPagerAdapter extends FragmentStateAdapter {

    public MainPagerAdapter(@NonNull FragmentManager fragmentManager, @NonNull Lifecycle lifecycle) {
        super(fragmentManager, lifecycle);
    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {
        switch (position) {
            case 0:
                return new HomeFragment();
            case 1:
                return new EAiFragment();
            case 2:
                return new MineFragment();
            default:
                return new Fragment(); // 或者处理这种情况，比如抛出异常
        }
    }

    @Override
    public int getItemCount() {
        return 3; // 返回Fragment的数量
    }
}
