package com.example.islamicapp.Utils;

import android.app.Service;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.AsyncTask.Status;
import android.os.Binder;
import android.os.Handler;
import android.os.IBinder;

import com.example.islamicapp.Interface.UpdatePlayListListener;

import java.util.Timer;


public class BackgroundService  extends Service {
    public static final int notify = 20000;
    private CallService callService;
    /* access modifiers changed from: private */
    public int i = 0;
    private final IBinder iBinder = new LocalBinder();
    /* access modifiers changed from: private */
    public Handler mHandler = new Handler();
    private Timer mTimer = null;
    /* access modifiers changed from: private */
    public UpdatePlayListListener mUpdatePlayListListener;

    private class CallService extends AsyncTask<Void, Void, Void> {
        private CallService() {
        }

        /* access modifiers changed from: protected */
        public Void doInBackground(Void... params) {
            while (BackgroundService.this.i < 101) {
                if (BackgroundService.this.i < 100) {
                    BackgroundService.this.i = BackgroundService.this.i + 1;
                } else {
                    BackgroundService.this.i = 0;
                }
                try {
                    Thread.sleep(300000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                BackgroundService.this.mHandler.post(new Runnable() {
                    public void run() {
                        if (BackgroundService.this.mUpdatePlayListListener != null) {
                            BackgroundService.this.mUpdatePlayListListener.updatePlayList();
                        }
                    }
                });
                if (isCancelled()) {
                    break;
                }
            }
            return null;
        }
    }

    public class LocalBinder extends Binder {
        public LocalBinder() {
        }

        public BackgroundService getService() {
            return BackgroundService.this;
        }
    }

    public IBinder onBind(Intent intent) {
        return this.iBinder;
    }

    public void cancelCallService() {
        if (this.callService != null && this.callService.getStatus() != Status.FINISHED) {
            this.callService.cancel(true);
        }
    }

    public void onCreate() {
        super.onCreate();
    }

    public int onStartCommand(Intent intent, int flags, int startId) {
        this.callService = new CallService();
        this.callService.execute(new Void[0]);
        return super.onStartCommand(intent, flags, startId);
    }

    public void onDestroy() {
        super.onDestroy();
    }

    public void setUpdatePlayListListener(UpdatePlayListListener updatePlayListListener) {
        this.mUpdatePlayListListener = updatePlayListListener;
    }
}
