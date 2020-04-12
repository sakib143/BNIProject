package com.example.islamicapp.Activit;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.util.Log;

import com.example.islamicapp.R;

public class CommonBaseActivity extends AppCompatActivity {

    private final BroadcastReceiver mConnectivityChangeReceiver = new BroadcastReceiver() {
        public void onReceive(Context context, Intent intent) {
            debugIntent(intent, "IBN-TV");
            if (!CommonBaseActivity.this.isNetworkAvailable()) {
                Log.d("IBN-TV", "==no internet");
                CommonBaseActivity.this.showNetworkCheckingDialog();
                return;
            }
            Log.d("IBN-TV", "==yes internet");
            if (CommonBaseActivity.this.mDialog != null) {
                Log.d("IBN-TV", "==1");
                if (CommonBaseActivity.this.mDialog.isShowing()) {
                    Log.d("IBN-TV", "==2");
                    CommonBaseActivity.this.mDialog.dismiss();
                    CommonBaseActivity.this.recreate();
                }
                CommonBaseActivity.this.mDialog = null;
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


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        registerReceiver(this.mConnectivityChangeReceiver, new IntentFilter("android.net.conn.CONNECTIVITY_CHANGE"));
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
        NetworkInfo activeNetworkInfo = ((ConnectivityManager) getSystemService("connectivity")).getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isConnected();
    }
}
