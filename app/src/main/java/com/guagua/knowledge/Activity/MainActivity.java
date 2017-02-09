package com.guagua.knowledge.Activity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.design.widget.Snackbar;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;

import com.baidu.appx.BDBannerAd;
import com.guagua.innews.R;
import com.guagua.knowledge.Callback.AdListener;
import com.guagua.knowledge.Fragment.DayProseListFragment;
import com.guagua.knowledge.Fragment.DayPsongFragment;
import com.guagua.knowledge.Fragment.DayRecommandFragment;
import com.guagua.knowledge.Fragment.DayProseFragment;
import com.guagua.knowledge.Fragment.DayWriteFragment;
import com.guagua.knowledge.FragmentType;

import cn.bmob.v3.Bmob;

import static com.guagua.knowledge.GankUrl.BDAdView.SDK_APP_KEY;
import static com.guagua.knowledge.GankUrl.BDAdView.SDK_BANNER_AD_ID;

public class MainActivity extends AppCompatActivity implements DayPsongFragment.OnFragmentInteractionListener {

    private DrawerLayout drawerlayout;
    private Toolbar toobar;
    private ActionBarDrawerToggle drawerToggle;
    private NavigationView navigationview;

    private DayProseListFragment dayProseListFragment;
    private DayWriteFragment dayWriteFragment;
    private DayPsongFragment daySongFragment;
    private DayRecommandFragment dayRecommandFragment;
    private LinearLayout ads_container;
    private BDBannerAd  bannerview;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_home);
        navigationview = (NavigationView) findViewById(R.id.navigation_view);
        drawerlayout = (DrawerLayout) findViewById(R.id.drawerlayout);
        ads_container = (LinearLayout) findViewById(R.id.adview_container);


        toobar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toobar);
        toobar.setNavigationIcon(R.drawable.menu);
        setupNavigation();
        //addBDBanerAD();
        Bmob.initialize(this, "60ca8c597a4aa976c882f2d69b3c66fb");
    }

    public void addBDBanerAD(){
        bannerview = new BDBannerAd(this, SDK_APP_KEY, SDK_BANNER_AD_ID);
        bannerview.setAdSize(BDBannerAd.SIZE_FULL_FLEXIBLE);
        bannerview.setAdListener(new AdListener());

        ads_container.addView(bannerview);
    }
    public void setupNavigation(){
        drawerToggle = new ActionBarDrawerToggle(this,drawerlayout,toobar,
                R.string.drawer_open, R.string.drawer_close){
            @Override
            public void onDrawerOpened(View drawerView) {
                super.onDrawerOpened(drawerView);
            }

            @Override
            public void onDrawerClosed(View drawerView) {
                super.onDrawerClosed(drawerView);
            }
        };
        drawerlayout.addDrawerListener(drawerToggle);
        navigationview.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                android.support.v4.app.Fragment initFragment = getSupportFragmentManager().findFragmentById(R.id.fragment_home);
                FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
                switch (item.getItemId()){
                    case R.id.menu_recommand:
                        if (initFragment != null){
                            getSupportFragmentManager().beginTransaction().show(initFragment).commit();
                        }else {
                            dayRecommandFragment = new DayRecommandFragment();
                            fragmentTransaction.replace(R.id.framelayout, dayRecommandFragment);
                            toobar.setTitle("每日推荐");
                        }
                        break;
                    case R.id.menu_day_prose:
                        if (initFragment != null){
                            getSupportFragmentManager().beginTransaction().hide(initFragment).commit();
                        }
                        dayProseListFragment = DayProseListFragment.newInstance(FragmentType.prose);
                        fragmentTransaction.replace(R.id.framelayout, dayProseListFragment);
                        toobar.setTitle("每日散文");
                        break;
                    case R.id.menu_day_write:
                        if (initFragment != null){
                            getSupportFragmentManager().beginTransaction().hide(initFragment).commit();
                        }
                        dayProseListFragment = DayProseListFragment.newInstance(FragmentType.suibi);
                        fragmentTransaction.replace(R.id.framelayout, dayProseListFragment);
                        toobar.setTitle("每日随笔");
                        break;
                    case R.id.menu_day_song:
                        if (initFragment != null){
                            getSupportFragmentManager().beginTransaction().hide(initFragment).commit();
                        }
                        daySongFragment = new DayPsongFragment();
                        fragmentTransaction.replace(R.id.framelayout, daySongFragment);
                        toobar.setTitle("每日诗歌");
                        break;
                }
                fragmentTransaction.commit();
                item.setChecked(true);
                drawerlayout.closeDrawers();
                return true;
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.menu_setting:
                Snackbar.make(drawerlayout,"click setting",Snackbar.LENGTH_SHORT).show();
                break;
            case R.id.menu_about:
                Intent intent = new Intent(MainActivity.this, AboutActivity.class);
                startActivity(intent);
                Snackbar.make(drawerlayout,"ckick about",Snackbar.LENGTH_SHORT).show();
                break;
        }
        return true;
    }

    @Override
    public void onFragmentInteraction(Uri uri) {

    }
    @Override
    public void onDestroy() {
        if (bannerview != null) {
            bannerview.destroy();
        }
        super.onDestroy();
    }
}
