package com.guagua.knowledge.Adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.guagua.innews.R;
import com.guagua.knowledge.Bean.GuaArticle;
import com.squareup.picasso.Picasso;
import com.guagua.knowledge.Bean.GankNews;
import com.guagua.knowledge.Callback.OnRecyclerViewItemClickListener;
import com.guagua.knowledge.FragmentType;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by tusalin on 9/6/2016.
 */

public class ArticalAdapter extends RecyclerView.Adapter<ArticalAdapter.ArticalViewHolder> {

    private FragmentType fragmentType;
    private List<GuaArticle> articalResults = new ArrayList<>();
    private OnRecyclerViewItemClickListener mOnRecyclerViewItemClickListener;

    public ArticalAdapter(FragmentType type, Context context){
        fragmentType = type;
    }

    public GuaArticle getItem(int position){
        if (articalResults!=null){
            return articalResults.get(position);
        }
        return null;
    }
    public void setArticalResults(int index,List gankNews){
        //articalResults.clear();
        if (index<0){
            articalResults.addAll(gankNews);
        }else {
            articalResults.addAll(index,gankNews);
        }

        notifyDataSetChanged();
    }


    @Override
    public int getItemCount() {
       return articalResults.size();
    }

    @Override
    public void onBindViewHolder(ArticalViewHolder holder, int position) {
        GuaArticle artical = articalResults.get(position);
        Log.d("articalSize",articalResults.size()+"");
//        if (girlResults!= null){
//            girl = girlResults.get((int) (Math.random()*girlResults.size()));
//        }
        holder.textViewContent.setText(Html.fromHtml(artical.getContent()));
        holder.textViewTitle.setText(Html.fromHtml(artical.getTitle()));

    }

    @Override
    public ArticalViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = null;
        view = LayoutInflater.from(parent.getContext()).inflate(R.layout.artical_item_linear,
                parent,false);

        ArticalViewHolder viewHolder = new ArticalViewHolder(view,mOnRecyclerViewItemClickListener);
        return viewHolder;

    }

    public class ArticalViewHolder extends RecyclerView.ViewHolder{
        //private ImageView imageView;
        private TextView textViewTitle;
        private TextView textViewContent;
        private OnRecyclerViewItemClickListener onItemClickListener;

        public ArticalViewHolder(View view,OnRecyclerViewItemClickListener listener){
            super(view);
            onItemClickListener = listener;
            textViewContent = (TextView) view.findViewById(R.id.cardview_artical_content);
            textViewTitle = (TextView) view.findViewById(R.id.cardview_artical_title);
            view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (onItemClickListener!=null){
                        onItemClickListener.onRecyclerViewItemClick(getLayoutPosition(),view);
                    }
                }
            });
        }

    }

    public void setOnRecyclerViewItemClick(OnRecyclerViewItemClickListener onRecyclerViewItemClickListener){
        mOnRecyclerViewItemClickListener = onRecyclerViewItemClickListener;
    }
}
