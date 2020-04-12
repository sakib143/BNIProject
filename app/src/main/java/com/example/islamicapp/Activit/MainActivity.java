package com.example.islamicapp.Activit;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.res.Configuration;
import android.graphics.Color;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.islamicapp.Adapter.FragmentAdapter;
import com.example.islamicapp.CustomView.SKtextViewAladin;
import com.example.islamicapp.CustomView.SKviewPager;
import com.example.islamicapp.R;
import com.example.islamicapp.Utils.BackgroundService;
import com.example.islamicapp.Utils.Utils;

public class MainActivity extends AppCompatActivity {
    /* access modifiers changed from: private */
    public boolean doubleBackToExitPressedOnce = false;
    /* access modifiers changed from: private */
    public ImageView imgAbout;
    /* access modifiers changed from: private */
    public ImageView imgPrograms;
    /* access modifiers changed from: private */
    public ImageView imgStream;
    private LinearLayout mAboutLayout;
    /* access modifiers changed from: private */
    public SKtextViewAladin mAboutTextView;
    /* access modifiers changed from: private */
    public RelativeLayout mActionBar;
    private LinearLayout mBottomNavigation;
    private final BroadcastReceiver mConnectivityChangeReceiver = new BroadcastReceiver() {
        public void onReceive(Context context, Intent intent) {
            debugIntent(intent, "IBN-TV");
            if (!MainActivity.this.isNetworkAvailable()) {
                Log.d("IBN-TV", "==no internet");
                MainActivity.this.showNetworkCheckingDialog();
                return;
            }
            Log.d("IBN-TV", "==yes internet");
            if (MainActivity.this.mDialog != null) {
                Log.d("IBN-TV", "==1");
                if (MainActivity.this.mDialog.isShowing()) {
                    Log.d("IBN-TV", "==2");
                    MainActivity.this.mDialog.dismiss();
                }
                MainActivity.this.mDialog = null;
            }
        }

        private void debugIntent(Intent intent, String tag) {
            Log.v(tag, "action: " + intent.getAction());
            Log.v(tag, "component: " + intent.getComponent());
            Bundle extras = intent.getExtras();
            if (extras != null) {
                for (String key : extras.keySet()) {
                    Log.v(tag, "key [" + key + "]: " + extras.get(key));
                }
                return;
            }
            Log.v(tag, "no extras");
        }
    };
    /* access modifiers changed from: private */
    public AlertDialog mDialog;
    private FragmentAdapter mFragmentAdapter;
    private LinearLayout mProgramLayout;
    private LinearLayout mStreamLayout;
    /* access modifiers changed from: private */
    public SKtextViewAladin mStreamTextView;
    private TextView mTextMessage;
    /* access modifiers changed from: private */
    public SKviewPager mViewPager;
    /* access modifiers changed from: private */
    public SKtextViewAladin mprogramsTextView;
    /* access modifiers changed from: private */
    public int selectedColor;
    /* access modifiers changed from: private */
    public int unSeleclectedcolor;

    /* access modifiers changed from: protected */

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initUi();
        registerReceiver(this.mConnectivityChangeReceiver, new IntentFilter("android.net.conn.CONNECTIVITY_CHANGE"));
    }

    private void initUi() {
        getWindow().setFlags(1024, 1024);
        this.mViewPager = (SKviewPager) findViewById(R.id.fragmentContainer);
        this.mActionBar = (RelativeLayout) findViewById(R.id.actionBar);
        this.mViewPager.setPagingEnabled(false);
        this.mViewPager.setOffscreenPageLimit(3);
        setupViewPager(this.mViewPager);
        this.imgAbout = (ImageView) findViewById(R.id.imgAbout);
        this.imgPrograms = (ImageView) findViewById(R.id.imgPrograms);
        this.imgStream = (ImageView) findViewById(R.id.imgStream);
        this.mStreamLayout = (LinearLayout) findViewById(R.id.streamLayout);
        this.mProgramLayout = (LinearLayout) findViewById(R.id.prgramsLayout);
        this.mAboutLayout = (LinearLayout) findViewById(R.id.aboutLayout);
        this.mBottomNavigation = (LinearLayout) findViewById(R.id.navigation);
        this.mStreamTextView = (SKtextViewAladin) findViewById(R.id.streamTextView);
        this.mprogramsTextView = (SKtextViewAladin) findViewById(R.id.programsTextView);
        this.mAboutTextView = (SKtextViewAladin) findViewById(R.id.aboutTextView);
        this.selectedColor = Color.parseColor("#fce76c");
        this.unSeleclectedcolor = Color.parseColor("#FFFFFF");
        this.imgStream.setColorFilter(this.selectedColor);
        this.imgPrograms.setColorFilter(this.unSeleclectedcolor);
        this.imgAbout.setColorFilter(this.unSeleclectedcolor);
        this.mStreamTextView.setTextColor(getResources().getColor(R.color.selected_color));
        this.mprogramsTextView.setTextColor(getResources().getColor(R.color.white_color));
        this.mAboutTextView.setTextColor(getResources().getColor(R.color.white_color));
        this.mViewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
            }

            public void onPageSelected(int position) {
                switch (position) {
                    case 0:
                        MainActivity.this.mActionBar.setVisibility(View.GONE);
                        return;
                    case 1:
                        MainActivity.this.mActionBar.setVisibility(View.GONE);
                        return;
                    case 2:
                        MainActivity.this.mActionBar.setVisibility(View.GONE);
                        return;
                    default:
                        return;
                }
            }

            public void onPageScrollStateChanged(int state) {
            }
        });
        fetchSchedule();
        View.OnClickListener listener = new View.OnClickListener() {
            public void onClick(View v) {
                switch (v.getId()) {
                    case R.id.aboutLayout /*2131296278*/:
                        MainActivity.this.mViewPager.setCurrentItem(2, false);
                        MainActivity.this.mStreamTextView.setTextColor(MainActivity.this.getResources().getColor(R.color.white_color));
                        MainActivity.this.mprogramsTextView.setTextColor(MainActivity.this.getResources().getColor(R.color.white_color));
                        MainActivity.this.mAboutTextView.setTextColor(MainActivity.this.getResources().getColor(R.color.selected_color));
                        MainActivity.this.imgStream.setColorFilter(MainActivity.this.unSeleclectedcolor);
                        MainActivity.this.imgPrograms.setColorFilter(MainActivity.this.unSeleclectedcolor);
                        MainActivity.this.imgAbout.setColorFilter(MainActivity.this.selectedColor);
                        return;
                    case R.id.prgramsLayout /*2131296413*/:
                        MainActivity.this.mViewPager.setCurrentItem(1);
                        MainActivity.this.mStreamTextView.setTextColor(MainActivity.this.getResources().getColor(R.color.white_color));
                        MainActivity.this.mprogramsTextView.setTextColor(MainActivity.this.getResources().getColor(R.color.selected_color));
                        MainActivity.this.mAboutTextView.setTextColor(MainActivity.this.getResources().getColor(R.color.white_color));
                        MainActivity.this.imgStream.setColorFilter(MainActivity.this.unSeleclectedcolor);
                        MainActivity.this.imgPrograms.setColorFilter(MainActivity.this.selectedColor);
                        MainActivity.this.imgAbout.setColorFilter(MainActivity.this.unSeleclectedcolor);
                        return;
                    case R.id.streamLayout /*2131296468*/:
                        MainActivity.this.mViewPager.setCurrentItem(0);
                        MainActivity.this.mStreamTextView.setTextColor(MainActivity.this.getResources().getColor(R.color.selected_color));
                        MainActivity.this.mprogramsTextView.setTextColor(MainActivity.this.getResources().getColor(R.color.white_color));
                        MainActivity.this.mAboutTextView.setTextColor(MainActivity.this.getResources().getColor(R.color.white_color));
                        MainActivity.this.imgStream.setColorFilter(MainActivity.this.selectedColor);
                        MainActivity.this.imgPrograms.setColorFilter(MainActivity.this.unSeleclectedcolor);
                        MainActivity.this.imgAbout.setColorFilter(MainActivity.this.unSeleclectedcolor);
                        return;
                    default:
                        return;
                }
            }
        };
        this.mStreamLayout.setOnClickListener(listener);
        this.mProgramLayout.setOnClickListener(listener);
        this.mAboutLayout.setOnClickListener(listener);
        int i = getResources().getConfiguration().orientation;
        if (getResources().getConfiguration().orientation == 2) {
            this.mActionBar.setVisibility(View.VISIBLE);
            this.mBottomNavigation.setVisibility(View.VISIBLE);
        }
        if (getResources().getConfiguration().orientation == 1) {
            this.mActionBar.setVisibility(View.GONE);
            this.mBottomNavigation.setVisibility(View.GONE);
        }
    }

    private void fetchSchedule() {
        if (Utils.isNetworkAvailable(this)) {
            Utils.callSchedule(this);
        }
    }

    private void setupViewPager(ViewPager viewPager) {
        this.mFragmentAdapter = new FragmentAdapter(getSupportFragmentManager());
        viewPager.setAdapter(this.mFragmentAdapter);
    }

    public void onBackPressed() {
        if (this.doubleBackToExitPressedOnce) {
            super.onBackPressed();
            return;
        }
        this.doubleBackToExitPressedOnce = true;
        Toast.makeText(this, "Please click BACK again to exit", Toast.LENGTH_LONG).show();
        new Handler().postDelayed(new Runnable() {
            public void run() {
                MainActivity.this.doubleBackToExitPressedOnce = false;
            }
        }, 2000);
    }

    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        if (newConfig.orientation == 2) {
            this.mActionBar.setVisibility(View.VISIBLE);
            this.mBottomNavigation.setVisibility(View.VISIBLE);
        } else if (newConfig.orientation == 1) {
            this.mActionBar.setVisibility(View.GONE);
            this.mBottomNavigation.setVisibility(View.GONE);
        }
    }

    /* access modifiers changed from: protected */
    public void onDestroy() {
        if (this.mDialog != null) {
            if (this.mDialog.isShowing()) {
                this.mDialog.dismiss();
            }
            this.mDialog = null;
        }
        unregisterReceiver(this.mConnectivityChangeReceiver);
        super.onDestroy();
        stopService(new Intent(this, BackgroundService.class));
    }

    /* access modifiers changed from: private */
    public void showNetworkCheckingDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle((CharSequence) "Lost Internet");
        builder.setMessage((CharSequence) "You cannot proceed because you have lost internet connection. Please make sure that you have active WIFI or Data Connection  enabled.");
        builder.setCancelable(false);
        this.mDialog = builder.create();
        this.mDialog.show();
    }

    /* access modifiers changed from: private */
    public boolean isNetworkAvailable() {
        ConnectivityManager connectivityManager = (ConnectivityManager) getSystemService("connectivity");
        NetworkInfo activeNetworkInfo = null;
        if (connectivityManager != null) {
            activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        }
        return activeNetworkInfo != null && activeNetworkInfo.isConnected();
    }
}
