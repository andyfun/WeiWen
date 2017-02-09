package com.guagua.knowledge.Fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.guagua.innews.R;
import com.guagua.knowledge.Adapter.HomeAdapter;
import com.guagua.knowledge.FragmentType;

/**
 * Created by andy on 9/4/2016.
 * 每日推荐
 */

public class DayRecommandFragment extends Fragment {

    private TabLayout tablayout;
    private ViewPager viewPager;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home,container,false);
        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        tablayout = (TabLayout) view.findViewById(R.id.tablayout);
        viewPager = (ViewPager) view.findViewById(R.id.viewpager);
        viewPager.setOffscreenPageLimit(4);

        if (viewPager!=null){
            setupViewPager();
        }
        tablayout.setupWithViewPager(viewPager,true);

    }
    private void setupViewPager() {
        HomeAdapter homeAdapter = new HomeAdapter(getChildFragmentManager());
        homeAdapter.addFragment(DayWriteFragment.newInstance(FragmentType.xiutuijian),getString(R.string.string_tab_recommand));
        homeAdapter.addFragment(DayWriteFragment.newInstance(FragmentType.xiulizhi),getString(R.string.string_tab_lizhi));
        homeAdapter.addFragment(DayWriteFragment.newInstance(FragmentType.xiulove),getString(R.string.string_tab_love));
        homeAdapter.addFragment(DayWriteFragment.newInstance(FragmentType.xiuzheli),getString(R.string.string_tab_zheli));
        viewPager.setAdapter(homeAdapter);
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }
}
