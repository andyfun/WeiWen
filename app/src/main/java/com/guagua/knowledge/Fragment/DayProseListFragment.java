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
 * 每日散文列表
 */

public class DayProseListFragment extends Fragment {

    private TabLayout tablayout;
    private ViewPager viewPager;
    private static final String ARGS_PARAM = "enum";
    private FragmentType  type;

    public DayProseListFragment(){}

    public static DayProseListFragment newInstance(FragmentType  type){

        DayProseListFragment dayProseListFragment = new DayProseListFragment();

        Bundle args = new Bundle();
        args.putSerializable(ARGS_PARAM,type);
        dayProseListFragment.setArguments(args);

        return dayProseListFragment;
    }



    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home,container,false);
        return view;
    }


    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        if (getArguments() != null){
             type = (FragmentType) getArguments().getSerializable(ARGS_PARAM);
        }

        tablayout = (TabLayout) view.findViewById(R.id.tablayout);
        viewPager = (ViewPager) view.findViewById(R.id.viewpager);
        viewPager.setOffscreenPageLimit(7);

        if (viewPager!=null){
            setupViewPager(type);
        }
        tablayout.setupWithViewPager(viewPager,true);

    }
    private void setupViewPager(FragmentType type) {
        /**
         * love,
         shuqing,
         jingmei,
         shanggan,
         qingan,
         qinqing,
         jingdian
         * @param type
         */
        HomeAdapter homeAdapter = new HomeAdapter(getChildFragmentManager());

        switch (type){
            case prose:
                homeAdapter.addFragment(DayProseFragment.newInstance(type,FragmentType.jingmei),getString(R.string.string_tab_jingmei));
                homeAdapter.addFragment(DayProseFragment.newInstance(type,FragmentType.jingdian),getString(R.string.string_tab_jingdian));
                homeAdapter.addFragment(DayProseFragment.newInstance(type,FragmentType.shanggan),getString(R.string.string_tab_shanggan));
                homeAdapter.addFragment(DayProseFragment.newInstance(type,FragmentType.love),getString(R.string.string_tab_love));
                homeAdapter.addFragment(DayProseFragment.newInstance(type,FragmentType.shuqing),getString(R.string.string_tab_shuqing));
                homeAdapter.addFragment(DayProseFragment.newInstance(type,FragmentType.qingan),getString(R.string.string_tab_qingan));
                homeAdapter.addFragment(DayProseFragment.newInstance(type,FragmentType.qinqing),getString(R.string.string_tab_qinqing));
                break;
            case suibi:
                homeAdapter.addFragment(DayProseFragment.newInstance(type,FragmentType.suibilife),getString(R.string.string_life));
                homeAdapter.addFragment(DayProseFragment.newInstance(type,FragmentType.suibireadbook),getString(R.string.string_readbook));
                homeAdapter.addFragment(DayProseFragment.newInstance(type,FragmentType.suibiqinggan),getString(R.string.string_qinggan));
                homeAdapter.addFragment(DayProseFragment.newInstance(type,FragmentType.suibisanyecao),getString(R.string.string_sanyecao));

                break;
            case shige:
                break;
        }


        viewPager.setAdapter(homeAdapter);
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }
}
