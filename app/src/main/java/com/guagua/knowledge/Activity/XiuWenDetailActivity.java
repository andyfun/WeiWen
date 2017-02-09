package com.guagua.knowledge.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.Html;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.guagua.innews.R;
import com.guagua.knowledge.Bean.GuaArticle;
import com.guagua.knowledge.Callback.HtmlCallback;
import com.guagua.knowledge.Network.GankRetrofit;
import com.guagua.knowledge.Utils.ZTDeviceInfo;
import com.squareup.picasso.Picasso;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by tusalin on 9/7/2016.
 */

public class XiuWenDetailActivity extends AppCompatActivity {
    private static final String TAG = XiuWenDetailActivity.class.getSimpleName();
    @BindView(R.id.detail_toolbar)
    Toolbar detailToolbar;
    @BindView(R.id.detail_progressbar)
    ProgressBar detailProgressbar;
    @BindView(R.id.guagua_title)
    TextView guaguaTitle;
    @BindView(R.id.guagua_author)
    TextView guaguaAuthor;
    @BindView(R.id.guagua_img)
    ImageView guaguaImg;
    @BindView(R.id.detail_content)
    TextView detailContent;
    @BindView(R.id.fab)
    FloatingActionButton fab;
    @BindView(R.id.coordinatorlayout)
    CoordinatorLayout coordinatorlayout;
    private GuaArticle articalsData;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.detail_activity);
        ButterKnife.bind(this);
        Intent intent = getIntent();
        articalsData = (GuaArticle) intent.getSerializableExtra("articals");
        setSupportActionBar(detailToolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        detailToolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
              //  Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(articalsData.getUrl()));
               // startActivity(intent);
            }
        });
        loadData();
    }

    public  void loadData(){
        String detailUrl = articalsData.getDetailUrl();
        Log.d(TAG, "detailUrl = " + detailUrl);
        GankRetrofit.requestArticleDetail(detailUrl, new HtmlCallback<GuaArticle>() {

            @Override
            public void onSuccessParseHtml(List<GuaArticle> obj) {
                if (obj != null) {
                    GuaArticle guaArticle = obj.get(0);
                    detailContent.setText(Html.fromHtml(guaArticle.getContent()));
                    guaguaTitle.setText(Html.fromHtml(guaArticle.getTitle()));
                   // guaguaAuthor.setText(Html.fromHtml(guaArticle.getAuthor()));
                   // String img = guaArticle.getImgUrl();
                   // Log.e(TAG," img = "+img);
//                    if (!TextUtils.isEmpty(img)) {
//                        Picasso.with(getApplicationContext())
//                                .load(img)
//                                .resize(Integer.valueOf(ZTDeviceInfo.getInstance().getWidth()),300)
//                                .centerInside()
//                                .into(guaguaImg);
//                        guaguaImg.setVisibility(View.VISIBLE);
//                    }else {
//                        guaguaImg.setVisibility(View.GONE);
//                    }
                }

            }

            @Override
            public void onFailedParseHtml(String error) {

            }
        });
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        this.finish();
    }

}
