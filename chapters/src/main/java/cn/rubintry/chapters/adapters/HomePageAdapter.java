package cn.rubintry.chapters.adapters;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.lifecycle.Lifecycle;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import java.util.List;

/**
 * @author logcat
 */
public class HomePageAdapter extends FragmentStateAdapter {

    private List<Fragment> pageList;

    public HomePageAdapter(@NonNull FragmentManager fragmentManager, @NonNull Lifecycle lifecycle, List<Fragment> pageList) {
        super(fragmentManager, lifecycle);
        this.pageList = pageList;
    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {
        return pageList.get(position);
    }

    @Override
    public int getItemCount() {
        return pageList.size();
    }
}
