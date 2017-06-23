package com.guagua.knowledge.Fragment;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.guagua.innews.R;
import com.guagua.knowledge.Activity.XiuWenDetailActivity;
import com.guagua.knowledge.Bean.GuaArticle;
import com.guagua.knowledge.Callback.HtmlCallback;
import com.guagua.knowledge.GankUrl;
import com.guagua.knowledge.Adapter.ArticalAdapter;
import com.guagua.knowledge.Callback.OnRecyclerViewItemClickListener;
import com.guagua.knowledge.FragmentType;
import com.guagua.knowledge.Network.GankRetrofit;

import java.util.List;

/**
 * Created by tusalin on 9/6/2016.
 */

public class DayWriteFragment extends BaseFragment{
    public static final String TAG = DayWriteFragment.class.getSimpleName();
    private String mkeyType;
    private FragmentType mfragmentType;
    private RecyclerView recyclerview;
    private SwipeRefreshLayout swipeRefreshLayout;
    private RecyclerView.LayoutManager layoutManager;
    private ArticalAdapter articalAdapter;
    private int lastVisibleItem;
    private int page ;
    private int pageRefresh = 1+(int)(Math.random()*10);
    private int pageMore = 1+(int)(Math.random()*10);

    public DayWriteFragment(){
    }
    public static  DayWriteFragment newInstance(FragmentType type){
        String keyType = null;
        switch (type){
            case xiutuijian:
                keyType = GankUrl.GuaGua_XIU_jingdian;
                break;
            case xiulizhi:
                keyType = GankUrl.GuaGua_XIU_lizhi;
                break;
            case xiuxiaoyuan:
                keyType = GankUrl.GuaGua_XIU_xiaoyuan;
                break;
            case xiugaoxiao:
                keyType = GankUrl.GuaGua_XIU_gaoxiao;
                break;
            case xiulove:
                keyType = GankUrl.GuaGua_XIU_aiqin;
                break;

            case xiuzheli:
                keyType = GankUrl.GuaGua_XIU_zheli;
                break;


        }
        DayWriteFragment  dayWriteFragment = new DayWriteFragment();
        Bundle args = new Bundle();
        args.putString(GankUrl.ARG_PARAM,keyType);
        args.putSerializable(GankUrl.ARG_PARAM_MENU,type);
        dayWriteFragment.setArguments(args);

        return dayWriteFragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_artical,container,false);
        isPrepared = true;
        return view;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        recyclerview = (RecyclerView) view.findViewById(R.id.artical_recyclerview);
        swipeRefreshLayout = (SwipeRefreshLayout) view.findViewById(R.id.artical_swiperefresh);

        if (getArguments() != null){
            mkeyType = getArguments().getString(GankUrl.ARG_PARAM);
            mfragmentType = (FragmentType) getArguments().getSerializable(GankUrl.ARG_PARAM_MENU);
        }

        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                requestGank(false);
            }
        });
        setAdapter();
        setDefaultLayoutManager();
        articalAdapter.setOnRecyclerViewItemClick(new OnRecyclerViewItemClickListener() {
            @Override
            public void onRecyclerViewItemClick(int position, View view ) {
                GuaArticle guaArticle = articalAdapter.getItem(position);
                Log.d(TAG,"guaArticle = "+guaArticle.getDetailUrl());
                startDetailActivity(position);
            }
        });
        recyclerview.setAdapter(articalAdapter);
        recyclerview.setLayoutManager(layoutManager);

        recyclerview.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                if (newState == RecyclerView.SCROLL_STATE_IDLE && lastVisibleItem == layoutManager.getItemCount()-1){
                    requestGank(true);
                }
            }

            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                lastVisibleItem = findLastVisibleItem();
            }
        });
        lazyLoad();
    }

    private void startDetailActivity(int position) {
        Log.d(TAG,"position = "+position);
        Intent intent = new Intent(getActivity(), XiuWenDetailActivity.class);
        intent.putExtra("articals",articalAdapter.getItem(position));
        startActivity(intent, ActivityOptionsCompat.makeSceneTransitionAnimation(getActivity()).toBundle());
    }

    private int findLastVisibleItem() {
        int lastVisibleItemPosition = 0;
        RecyclerView.LayoutManager layoutManager = recyclerview.getLayoutManager();
        lastVisibleItemPosition = ((LinearLayoutManager)layoutManager).findLastVisibleItemPosition();

        return lastVisibleItemPosition;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        swipeRefreshLayout.setRefreshing(true);

        super.onActivityCreated(savedInstanceState);
    }

    public void requestGank(final boolean loadMore){
        swipeRefreshLayout.measure(View.MEASURED_SIZE_MASK,View.MEASURED_HEIGHT_STATE_SHIFT);
        if (loadMore){
            page = pageMore;
            pageMore ++;
        }else {
            if (pageRefresh <2){
                swipeRefreshLayout.setRefreshing(false);
                return;
            }
            Log.d(TAG,"index = "+pageRefresh);
            page = pageRefresh;
            pageRefresh--;
        }
        String pathIndex = null;
        if (mfragmentType == FragmentType.xiutuijian){
            pathIndex = "20_"+String.valueOf(page);
        }else if (mfragmentType == FragmentType.xiulizhi){
            pathIndex = "22_"+String.valueOf(page);
        }else if (mfragmentType == FragmentType.xiuxiaoyuan){
            pathIndex = "19_"+String.valueOf(page);
        }else if (mfragmentType == FragmentType.xiugaoxiao){
            pathIndex = "23_"+String.valueOf(page);
        }else if (mfragmentType == FragmentType.xiulove){
            pathIndex = "15_"+String.valueOf(page);
        }else if (mfragmentType == FragmentType.xiuzheli){
            pathIndex = "21_"+String.valueOf(page);
        }
        swipeRefreshLayout.setRefreshing(true);
        Log.d(TAG," --lazyLoad--mkeyType:"+mkeyType+",pathIndex = "+pathIndex);
        GankRetrofit.requestArticleList(mkeyType,pathIndex,new HtmlCallback<GuaArticle>(){

            @Override
            public void onSuccessParseHtml(List<GuaArticle> obj) {
                if (obj != null){
                    if (loadMore){
                        articalAdapter.setArticalResults(-1,obj);
                    }else {
                        articalAdapter.setArticalResults(0,obj);
                    }

                    articalAdapter.notifyDataSetChanged();
                }
                mHasLoadedOnce = true;
                swipeRefreshLayout.setRefreshing(false);
            }

            @Override
            public void onFailedParseHtml(String error) {
                swipeRefreshLayout.setRefreshing(false);
                Snackbar.make(recyclerview,error,Snackbar.LENGTH_SHORT).show();
            }
        });

        Log.d(TAG,"---modong");
        GankRetrofit.requestModong();

    }


    public void setAdapter(){
        articalAdapter = new ArticalAdapter(mfragmentType,getContext());
    }

    public void setDefaultLayoutManager(){
        layoutManager = new LinearLayoutManager(getContext());

    }

    @Override
    protected void lazyLoad() {
        if (!isPrepared || !isVisible || mHasLoadedOnce) {
            return;
        }
        requestGank(false);
    }
}
