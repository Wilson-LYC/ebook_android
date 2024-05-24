package com.ebook.app.views.authority;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.Lifecycle;
import androidx.viewpager2.adapter.FragmentStateAdapter;

public class AuthorityPagerAdapter extends FragmentStateAdapter {

    public AuthorityPagerAdapter(@NonNull FragmentManager fragmentManager, @NonNull Lifecycle lifecycle) {
        super(fragmentManager, lifecycle);
    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {
        switch (position) {
            case 0:
                return new LoginFragment();
            case 1:
                return new RegisterFragment();
            default:
                return new Fragment(); // 或者处理这种情况，比如抛出异常
        }
    }

    @Override
    public int getItemCount() {
        return 2; // 返回Fragment的数量
    }
}
