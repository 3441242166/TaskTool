package com.example.wanhao.tasktool.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.GravityCompat;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.KeyEvent;
import android.view.MenuItem;
import android.widget.Toast;

import com.example.wanhao.tasktool.R;
import com.example.wanhao.tasktool.adapter.PagerAdapter;
import com.example.wanhao.tasktool.fragment.MainFragment;
import com.example.wanhao.tasktool.fragment.OtherFragment;
import com.example.wanhao.tasktool.presenter.MainActivityPresenter;
import com.example.wanhao.tasktool.service.TimeTaskService;
import com.example.wanhao.tasktool.tool.Constant;
import com.example.wanhao.tasktool.view.IMainActivity;

import java.util.ArrayList;
import java.util.List;


public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener,IMainActivity{

    DrawerLayout drawer;
    Toolbar toolbar;
    ViewPager viewPager;
    TabLayout tabLayout;
    NavigationView navigationView;
    MainActivityPresenter presenter;
    List<Fragment> list = new ArrayList<>();

    private long exitTime = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_drawer_main);

        init();
    }

    private void init() {
        Constant.FirstInit(this);

        toolbar = (Toolbar)findViewById(R.id.toolbar);
        viewPager = (ViewPager)findViewById(R.id.pager);
        tabLayout = (TabLayout)findViewById(R.id.tab_layout);
        navigationView = (NavigationView) findViewById(R.id.nav_view) ;

        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);

        navigationView.setNavigationItemSelectedListener(this);


        //拿到DrawerLayout对象，设置ActionBarDrawerToggle监听
        drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();
        //---------------------------------------------------
        list.add(new MainFragment());
        list.add(new OtherFragment());
        viewPager.setAdapter(new PagerAdapter(getSupportFragmentManager(),list));
        tabLayout.setupWithViewPager(viewPager);

        presenter = new MainActivityPresenter(this,this);
        presenter.endDateTaskCheck();
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if(keyCode == KeyEvent.KEYCODE_BACK && event.getAction() == KeyEvent.ACTION_DOWN){
            if((System.currentTimeMillis()-exitTime) > 2000){
                Toast.makeText(getApplicationContext(), "再按一次退出程序", Toast.LENGTH_SHORT).show();
                exitTime = System.currentTimeMillis();
            } else {
                Intent intent = new Intent(this, TimeTaskService.class);
                stopService(intent);
                finish();
                System.exit(0);
            }
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        Intent intent = null;

        int id = item.getItemId();
        if (id == R.id.main_drawer_tip) {
            presenter.tipAcivityStart();
        } else if (id == R.id.main_drawer_task) {
            intent = new Intent(this,TaskListActivity.class);
            startActivity(intent);
        } else if (id == R.id.main_drawer_world) {
            intent = new Intent(this,WordListActivity.class);
            startActivity(intent);
        } else if (id == R.id.main_drawer_set) {
            intent = new Intent(this,OptionActivity.class);
            startActivity(intent);
        } else if (id == R.id.nav_share) {
            Toast.makeText(this,"nav_share",Toast.LENGTH_SHORT).show();
        } else if (id == R.id.nav_message) {
            intent = new Intent(this,AppMessageActivity.class);
            startActivity(intent);
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    public void startTipActivity() {
        Intent intent = new Intent(this, TimeTaskActivity.class);
        startActivity(intent);
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        //如果打开了Drawer  则关闭Drawer  否则退出程序
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }
}
