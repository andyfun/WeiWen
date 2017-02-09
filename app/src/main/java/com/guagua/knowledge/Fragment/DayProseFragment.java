package com.guagua.knowledge.Fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.baidu.appx.BDBannerAd;
import com.guagua.innews.R;
import com.guagua.knowledge.Activity.SanWenDetailActivity;
import com.guagua.knowledge.Adapter.ProseAdapter;
import com.guagua.knowledge.Bean.GuaArticle;
import com.guagua.knowledge.Callback.HtmlCallback;
import com.guagua.knowledge.Callback.OnRecyclerViewItemClickListener;
import com.guagua.knowledge.FragmentType;
import com.guagua.knowledge.GankUrl;
import com.guagua.knowledge.Network.GankRetrofit;

import java.util.ArrayList;
import java.util.List;

import static com.guagua.knowledge.GankUrl.ARG_PARAM;
import static com.guagua.knowledge.GankUrl.ARG_PARAM_MENU;

/**
 * Created by andy on 9/4/2016.
 * 每日散文  列表
 */

public class DayProseFragment extends BaseFragment {
    public static final String TAG = DayProseFragment.class.getSimpleName();
    private List<GuaArticle> technologyNewsInfo = new ArrayList<>();

    private RecyclerView recyclerview;
    private ProseAdapter proseAdapter;
   // private LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
    private SwipeRefreshLayout swipeRefreshLayout;
    private LinearLayout ads_container;
    private String mkeyType;
    private FragmentType menuType;
    private LinearLayoutManager layoutManager;
    private int indexMorePage = 1+(int)(Math.random()*60);
    private int indexRefreshPage =1+(int)(Math.random()*60);
    private int lastVisableItem;

    public DayProseFragment(){
    }

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
    public static  DayProseFragment  newInstance(FragmentType menu,FragmentType type){
        String keyType = null;
        switch (type){
            case love:
                keyType = GankUrl.GuaGua_LOVE;
                break;
            case shuqing:
                keyType = GankUrl.GuaGua_SHUQING;
                break;
            case jingmei:
                keyType = GankUrl.GuaGua_JINGMEI;
                break;
            case shanggan:
                keyType = GankUrl.GuaGua_SHANGGAN;
                break;
            case qingan:
                keyType = GankUrl.GuaGua_QINGANG;
                break;
            case qinqing:
                keyType = GankUrl.GuaGua_QINGQING;
                break;
            case jingdian:
                keyType = GankUrl.GuaGua_JINGDIAN;
                break;
            case suibilife:
                keyType = GankUrl.GuaGua_suibi_SHENGHUO;
                break;
            case suibireadbook:
                keyType = GankUrl.GuaGua_suibi_dushu;
                break;
            case suibiqinggan:
                keyType = GankUrl.GuaGua_suibi_qinggan;
                break;
            case suibisanyecao:
                keyType = GankUrl.GuaGua_suibi_sanyecao;
                break;
        }

        DayProseFragment dayProseFragment = new DayProseFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM,keyType);
        args.putSerializable(ARG_PARAM_MENU,menu);
        dayProseFragment.setArguments(args);
        return dayProseFragment;

    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_prose,container,false);
        isPrepared = true;
        return view;
    }


    public void startDetailActivity(int position){
        Intent intent = new Intent(getActivity(), SanWenDetailActivity.class);
        intent.putExtra("guaArticle", proseAdapter.getItem(position));
        Log.d(TAG,"detailurl = "+ proseAdapter.getItem(position).getDetailUrl());
        startActivity(intent);
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null){
            mkeyType = getArguments().getString(ARG_PARAM);
            menuType = (FragmentType) getArguments().getSerializable(ARG_PARAM_MENU);
        }
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        recyclerview = (RecyclerView) view.findViewById(R.id.recyclerview_technology);
        swipeRefreshLayout = (SwipeRefreshLayout) view.findViewById(R.id.swiperefresh_technology);

        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                dataRequest(false);
            }
        });
        layoutManager = new LinearLayoutManager(getContext());
        recyclerview.setLayoutManager(layoutManager);
        proseAdapter = new ProseAdapter(getContext(), technologyNewsInfo);

        recyclerview.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                if (newState == RecyclerView.SCROLL_STATE_IDLE && lastVisableItem == layoutManager.getItemCount()-1 ){
                    dataRequest(true);
                }
            }

            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                lastVisableItem = layoutManager.findLastVisibleItemPosition();
                Log.d(TAG,"lastVisableItem = "+lastVisableItem);
            }
        });

        proseAdapter.setOnRecyclerViewItemClick(new OnRecyclerViewItemClickListener() {
            @Override
            public void onRecyclerViewItemClick(int position, View view) {
                startDetailActivity(position);
            }
        });
       lazyLoad();
    }



    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
    }

    public void dataRequest(final boolean loadMore){
        int index;
        if (loadMore){
            if (indexMorePage>10){
                swipeRefreshLayout.setRefreshing(false);
                return;
            }
            index = indexMorePage;
            indexMorePage ++;
        }else {
            if (indexRefreshPage <2){
                swipeRefreshLayout.setRefreshing(false);
                return;
            }
            Log.d(TAG,"index = "+indexRefreshPage);
            index = indexRefreshPage;
            indexRefreshPage--;
        }
        swipeRefreshLayout.setRefreshing(true);
        String sanwen = null;
        if (menuType ==FragmentType.shige){
            sanwen = GankUrl.GuaGua_shige;
        }else if (menuType == FragmentType.suibi){
            sanwen = GankUrl.GuaGua_suibi;
        }else if (menuType == FragmentType.prose){
            sanwen = GankUrl.GuaGua_SANWEN;
        }
        GankRetrofit.requestProseList(sanwen,mkeyType, index, new HtmlCallback<GuaArticle>(){

            @Override
            public  void onSuccessParseHtml(List<GuaArticle> obj) {

                if (obj!= null){
                    technologyNewsInfo = obj;
                    if (loadMore){
                        proseAdapter.addMoreData(obj);
                    }else {
                        proseAdapter.addData(obj);
                    }
                }
                mHasLoadedOnce = true;
                if (swipeRefreshLayout.isRefreshing()){
                    swipeRefreshLayout.setRefreshing(false);
                }
            }

            @Override
            public void onFailedParseHtml(String error) {
                Snackbar.make(recyclerview,error,Snackbar.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    protected void lazyLoad() {
        if (!isPrepared || !isVisible || mHasLoadedOnce) {
            return;
        }
        recyclerview.setAdapter(proseAdapter);
        dataRequest(false);
        proseAdapter.addData(technologyNewsInfo);
    }

}
