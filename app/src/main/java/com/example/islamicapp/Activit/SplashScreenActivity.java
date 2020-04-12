package com.example.islamicapp.Activit;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;

import com.example.islamicapp.AppController;
import com.example.islamicapp.Model.Channel;
import com.example.islamicapp.R;
import com.example.islamicapp.Utils.PreferencesHandler;
import com.example.islamicapp.Utils.Utils;
import com.example.islamicapp.WebServices.APIClient;
import com.example.islamicapp.WebServices.APIInterface;

import java.util.concurrent.ExecutionException;
import retrofit2.Call;
import android.annotation.SuppressLint;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager.NameNotFoundException;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;

import org.jsoup.Jsoup;

import java.util.concurrent.ExecutionException;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SplashScreenActivity extends AppCompatActivity {

    public static final long SPLASH_WAIT_TIME = 2500;
    private PreferencesHandler mPreferencesHandler;


    @SuppressLint({"StaticFieldLeak"})
    private class GetLatestVersion extends AsyncTask<String, String, String> {
        String latestVersion;

        private GetLatestVersion() {
        }

        /* access modifiers changed from: protected */
        public void onPreExecute() {
            super.onPreExecute();
        }

        /* access modifiers changed from: protected */
        public String doInBackground(String... params) {
            try {
                this.latestVersion = Jsoup.connect("https://play.google.com/store/apps/details?id=" + SplashScreenActivity.this.getPackageName()).get().getElementsByAttributeValue("itemprop", "softwareVersion").first().text();
            } catch (Exception e) {
                e.printStackTrace();
            }
            return this.latestVersion;
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);

        initUi();
    }

    private void initUi() {
        getWindow().setFlags(1024, 1024);
        this.mPreferencesHandler = PreferencesHandler.getInstance(this);
        this.mPreferencesHandler.setNavigation("splash");
        if (!Utils.isNetworkAvailable(this)) {
            return;
        }
        if (!AppController.VIDEO_STREAMING_URL.equals("")) {
            startMainActivity();
        } else {
            webServiceChannelUrl();
        }
    }

    private void webServiceChannelUrl() {
        if (AppController.WEB_SERVICE_URL.equals("") || AppController.GET_VIDEO_URl_ENDPOINT.equals("") || AppController.WEB_SERVICE_URL.equalsIgnoreCase("<...your Base URL...>")) {
            startMainActivity();
        } else {
            ((APIInterface) APIClient.getList().create(APIInterface.class)).channelUrl().enqueue(new Callback<Channel>() {
                public void onResponse(Call<Channel> call, Response<Channel> response) {
                    if (response.code() == 200) {
                        Channel channel = (Channel) response.body();
                        if (channel == null) {
                            SplashScreenActivity.this.startMainActivity();
                        } else if (channel.getChannelUrl() == null || channel.getChannelUrl().equals("")) {
                            SplashScreenActivity.this.startMainActivity();
                        } else {
                            AppController.VIDEO_STREAMING_URL = channel.getChannelUrl();
                            SplashScreenActivity.this.startMainActivity();
                        }
                    }
                }

                public void onFailure(Call call, Throwable t) {
                    Log.e("", "Got error : " + t.getLocalizedMessage());
                }
            });
        }
    }

    private void checkLatestVersion() {
        String latestVersion = "";
        String currentVersion = getCurrentVersion();
        Log.d("current version", "Current version = " + currentVersion);
        try {
            latestVersion = (String) new GetLatestVersion().execute(new String[0]).get();
            Log.d("latest version", "Latest version = " + latestVersion);
        } catch (InterruptedException | ExecutionException e) {
            e.printStackTrace();
        }
        if (!currentVersion.equals(latestVersion)) {
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setTitle((CharSequence) "An Update is Available");
            builder.setCancelable(false);
            builder.setPositiveButton((CharSequence) "Update", (OnClickListener) new OnClickListener() {
                public void onClick(DialogInterface dialog, int which) {
                    SplashScreenActivity.this.startActivity(new Intent("android.intent.action.VIEW", Uri.parse("market://details?id=" + SplashScreenActivity.this.getPackageName())));
                    dialog.dismiss();
                    SplashScreenActivity.this.finish();
                }
            });
            builder.setNegativeButton((CharSequence) "Cancel", (OnClickListener) new OnClickListener() {
                public void onClick(DialogInterface dialog, int which) {
                    SplashScreenActivity.this.finish();
                }
            });
            builder.setCancelable(false);
            builder.show();
            return;
        }
        new Thread() {
            public void run() {
                try {
                    sleep(SplashScreenActivity.SPLASH_WAIT_TIME);
                    SplashScreenActivity.this.startActivity(new Intent(SplashScreenActivity.this.getApplicationContext(), MainActivity.class));
                    SplashScreenActivity.this.finish();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }.start();
    }

    private String getCurrentVersion() {
        PackageInfo pInfo = null;
        try {
            pInfo = getPackageManager().getPackageInfo(getPackageName(), 0);
        } catch (NameNotFoundException e1) {
            e1.printStackTrace();
        }
        return pInfo.versionName;
    }

//    private void noNetworkConnection() {
//        AlertDialog.Builder builder = new AlertDialog.Builder(this, R.style.AlertDialogTheme);
//        builder.setTitle((CharSequence) "No active internet connection");
//        builder.setCancelable(false);
//        builder.setNegativeButton((CharSequence) "Ok", (OnClickListener) new OnClickListener() {
//            public void onClick(DialogInterface dialog, int which) {
//                SplashScreenActivity.this.finish();
//            }
//        });
//        builder.setCancelable(false);
//        builder.show();
//    }

    /* access modifiers changed from: private */
    public void startMainActivity() {
        new Thread() {
            public void run() {
                try {
                    sleep(SplashScreenActivity.SPLASH_WAIT_TIME);
                    SplashScreenActivity.this.startActivity(new Intent(SplashScreenActivity.this.getApplicationContext(), MainActivity.class));
                    SplashScreenActivity.this.finish();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }.start();
    }
}
