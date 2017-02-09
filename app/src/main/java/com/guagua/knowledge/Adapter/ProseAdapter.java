package com.guagua.knowledge.Adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.guagua.innews.R;
import com.guagua.knowledge.Bean.GuaArticle;
import com.guagua.knowledge.Callback.OnRecyclerViewItemClickListener;

import java.util.List;

/**
 * Created by andy on 9/5/2016.
 */

public class ProseAdapter extends RecyclerView.Adapter<ProseAdapter.TechnologyViewHolder> {

    private Context mContext;
    private LayoutInflater mInflater;
    private List<GuaArticle> mNewsInfos;
    private OnRecyclerViewItemClickListener mOnRecyclerViewItemClickListener;

    public ProseAdapter(Context context, List<GuaArticle> newsInfos) {
        mContext = context;
        mInflater = LayoutInflater.from(mContext);
        mNewsInfos = newsInfos;
    }

    public GuaArticle getItem(int position){
        if (mNewsInfos!=null){
            return mNewsInfos.get(position);
        }
        return null;
    }

    @Override
    public int getItemCount() {
        return mNewsInfos.size();
    }

    @Override
    public void onBindViewHolder(TechnologyViewHolder holder, int position) {
       // holder.newsDate.setText(mNewsInfos.get(position).getTime());
        holder.newsTitle.setText(mNewsInfos.get(position).getTitle());
        holder.guagua_content.setText(Html.fromHtml(mNewsInfos.get(position).getContent()));
        Log.d(" tag ",mNewsInfos.get(position).getTitle());
//        Picasso.with(mContext)
//                .load(mNewsInfos.get(position).getNewsImageUrl())
//                .placeholder(R.drawable.placeholder)
//                .fit()
//                .into(holder.newsImage);
    }

    @Override
    public TechnologyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new TechnologyViewHolder(mInflater.inflate(R.layout.drawer_news_item,parent,false),
                mOnRecyclerViewItemClickListener);
    }

    public class TechnologyViewHolder extends RecyclerView.ViewHolder{
        public TextView guagua_content;
        public TextView newsTitle;
        //public TextView newsDate;
        private OnRecyclerViewItemClickListener onClickListener;

        public TechnologyViewHolder(View view, final OnRecyclerViewItemClickListener onItemClickListener){
            super(view);
            guagua_content = (TextView) view.findViewById(R.id.guagua_content);
            newsTitle = (TextView) view.findViewById(R.id.cardview_news_title);
            //newsDate = (TextView) view.findViewById(R.id.cardview_news_date);
            view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    onClickListener = onItemClickListener;
                    onClickListener.onRecyclerViewItemClick(getLayoutPosition(),view);
                }
            });
        }
    }

    public void addData(List<GuaArticle> list){
        mNewsInfos = list;
        notifyDataSetChanged();
    }
    public void addMoreData(List<GuaArticle> list){
        mNewsInfos.addAll(list);
        notifyDataSetChanged();
    }
    public void setOnRecyclerViewItemClick(OnRecyclerViewItemClickListener listener){
        mOnRecyclerViewItemClickListener = listener;
    }
}
