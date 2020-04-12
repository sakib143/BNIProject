package com.example.islamicapp.Fragment;

import android.annotation.SuppressLint;
import android.content.ComponentName;
import android.content.Intent;
import android.content.ServiceConnection;
import android.content.res.Configuration;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.AsyncTask.Status;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout.LayoutParams;
import android.widget.RelativeLayout;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.islamicapp.Adapter.CurrentDayprogramScheduleAdapter;
import com.example.islamicapp.AppController;
import com.example.islamicapp.BetterVideoPlayer.BetterVideoCallback;
import com.example.islamicapp.BetterVideoPlayer.BetterVideoPlayer;
import com.example.islamicapp.CustomView.SKtextViewRobotoRegular;
import com.example.islamicapp.Interface.ItemSelectionListner;
import com.example.islamicapp.Interface.ScheduleApiCallBack;
import com.example.islamicapp.Interface.UpdatePlayListListener;
import com.example.islamicapp.Model.ProgramListModel;
import com.example.islamicapp.Model.ScheduleListEventModel;
import com.example.islamicapp.Model.ScheduleListScheduleModel;
import com.example.islamicapp.R;
import com.example.islamicapp.Utils.BackgroundService;
import com.example.islamicapp.Utils.Utils;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class VideoPlayerFragment extends Fragment implements BetterVideoCallback, ItemSelectionListner, UpdatePlayListListener {
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    public static List<ScheduleListEventModel> mScheduleListEventModel = new ArrayList();
    static int position = -1;
    int HANDLER_TIME = 1000;
    Async async = new Async();
    /* access modifiers changed from: private */
    public boolean backgroundServiceBound = false;
    private ServiceConnection backgroundServiceConnection = new ServiceConnection() {
        public void onServiceConnected(ComponentName name, IBinder service) {
            VideoPlayerFragment.this.updateProgramNamesService = ((BackgroundService.LocalBinder) service).getService();
            VideoPlayerFragment.this.backgroundServiceBound = true;
            VideoPlayerFragment.this.updateProgramNamesService.setUpdatePlayListListener(VideoPlayerFragment.this);
        }

        public void onServiceDisconnected(ComponentName name) {
            VideoPlayerFragment.this.backgroundServiceBound = false;
        }
    };
    private BetterVideoPlayer bvp;
    /* access modifiers changed from: private */
    public ImageView imageViewResize;
    /* access modifiers changed from: private */
    public RecyclerView mCurrentDayProgramList;
    /* access modifiers changed from: private */
    public CurrentDayprogramScheduleAdapter mCurrentDayprogramScheduleAdapter;
    private RelativeLayout mFrameLayOutContainer;
    private ImageView mImageViewFullScreen;
    public SKtextViewRobotoRegular mNoSchedule;
    private SKtextViewRobotoRegular mNoStreamUrl;
    private String mParam1;
    private String mParam2;
    private RelativeLayout mProgramListContainer;
    private ArrayList<ProgramListModel> mProgramListCurrentDay = new ArrayList<>();
    private View mRootView;
    /* access modifiers changed from: private */
    public ArrayList<ScheduleListScheduleModel> mScheduleList;
    private LayoutParams paramsFrameLayout;
    private LayoutParams paramsProgramContainer;
    private LayoutParams paramsProgramsEqual;
    private Button pauseButton;
    /* access modifiers changed from: private */
    public BackgroundService updateProgramNamesService;

    @SuppressLint({"StaticFieldLeak"})
    public class Async extends AsyncTask<Void, Integer, String> {
        public Async() {
        }

        /* access modifiers changed from: protected */
        public void onPreExecute() {
            super.onPreExecute();
            VideoPlayerFragment.this.mCurrentDayprogramScheduleAdapter = new CurrentDayprogramScheduleAdapter(VideoPlayerFragment.this.getActivity(), VideoPlayerFragment.mScheduleListEventModel);
            VideoPlayerFragment.this.mCurrentDayProgramList.setLayoutManager(new LinearLayoutManager(VideoPlayerFragment.this.getActivity()));
            VideoPlayerFragment.this.mCurrentDayProgramList.setItemAnimator(new DefaultItemAnimator());
            VideoPlayerFragment.this.mCurrentDayProgramList.setHasFixedSize(true);
            VideoPlayerFragment.this.mCurrentDayProgramList.setAdapter(VideoPlayerFragment.this.mCurrentDayprogramScheduleAdapter);
        }

        /* access modifiers changed from: protected */
        public String doInBackground(Void... arg0) {
            return "You are at PostExecute";
        }

        /* access modifiers changed from: protected */
        public void onProgressUpdate(Integer... a) {
            super.onProgressUpdate(a);
        }

        /* access modifiers changed from: protected */
        public void onPostExecute(String result) {
            super.onPostExecute(result);
            VideoPlayerFragment.this.mCurrentDayProgramList.scrollToPosition(VideoPlayerFragment.position);
        }
    }

    public static VideoPlayerFragment newInstance(String param1, String param2) {
        VideoPlayerFragment fragment = new VideoPlayerFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            this.mParam1 = getArguments().getString(ARG_PARAM1);
            this.mParam2 = getArguments().getString(ARG_PARAM2);
        }
        if (!this.backgroundServiceBound) {
            Intent playerIntent = new Intent(getActivity(), BackgroundService.class);
            getActivity().startService(playerIntent);
            getActivity().bindService(playerIntent, this.backgroundServiceConnection, 1);
        }
    }

    @Nullable
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        this.mRootView = inflater.inflate(R.layout.fragment_video_player, container, false);
        Log.e("called", "videoplayer");
        initUi();
        return this.mRootView;
    }

    private void initUi() {
        this.paramsFrameLayout = new LayoutParams(-1, -2, 2.0f);
        this.paramsProgramContainer = new LayoutParams(-1, -2, 0.0f);
        this.paramsProgramsEqual = new LayoutParams(-1, -2, 1.0f);
        this.mNoSchedule = (SKtextViewRobotoRegular) this.mRootView.findViewById(R.id.mNoSchedule);
        this.mNoStreamUrl = (SKtextViewRobotoRegular) this.mRootView.findViewById(R.id.mNoStreamUrl);
        if (AppController.WEB_SERVICE_URL.equals("") || AppController.GET_SCHEDULE_SERVICE_ENDPOINT.equals("")) {
            this.mNoSchedule.setVisibility(0);
        } else {
            this.mNoSchedule.setVisibility(8);
        }
        this.imageViewResize = (ImageView) this.mRootView.findViewById(R.id.imageViewResize);
        this.mImageViewFullScreen = (ImageView) this.mRootView.findViewById(R.id.fullScreenView);
        this.mProgramListContainer = (RelativeLayout) this.mRootView.findViewById(R.id.programListCOntainer);
        this.mFrameLayOutContainer = (RelativeLayout) this.mRootView.findViewById(R.id.frameLayOutContainer);
        this.imageViewResize.setOnClickListener(new OnClickListener() {
            public void onClick(View v) {
                VideoPlayerFragment.this.imageViewResize.setVisibility(8);
                if (VideoPlayerFragment.this.getActivity() != null) {
                    VideoPlayerFragment.this.getActivity().setRequestedOrientation(1);
                }
                VideoPlayerFragment.this.changeOrientaionToSenser();
            }
        });
        this.mImageViewFullScreen.setOnClickListener(new OnClickListener() {
            public void onClick(View view) {
                VideoPlayerFragment.this.imageViewResize.setVisibility(0);
                RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(-2, -2);
                params.setMargins(0, 0, 40, 40);
                VideoPlayerFragment.this.imageViewResize.setLayoutParams(params);
                if (VideoPlayerFragment.this.getActivity() != null) {
                    VideoPlayerFragment.this.getActivity().setRequestedOrientation(0);
                }
                VideoPlayerFragment.this.changeOrientaionToSenser();
            }
        });
        try {
            setVideoplayer();
        } catch (Exception e) {
            e.printStackTrace();
        }
        Utils.passApiResponse(new ScheduleApiCallBack() {
            public void passScheduleApiResponse(ArrayList<ScheduleListScheduleModel> schedule, String name) {
                if (VideoPlayerFragment.this.mScheduleList != null && VideoPlayerFragment.this.mScheduleList.size() > 0) {
                    VideoPlayerFragment.this.mScheduleList.clear();
                }
                VideoPlayerFragment.this.mScheduleList = schedule;
                VideoPlayerFragment.this.setValuesToAdapter();
            }
        });
        if (getResources().getConfiguration().orientation == 2) {
            ladscapeMode();
        }
        if (getResources().getConfiguration().orientation == 1) {
            portriateMode();
        }
    }

    /* access modifiers changed from: private */
    public void changeOrientaionToSenser() {
        new Handler().postDelayed(new Runnable() {
            public void run() {
                if (VideoPlayerFragment.this.getActivity() != null) {
                    VideoPlayerFragment.this.getActivity().setRequestedOrientation(4);
                }
            }
        }, (long) this.HANDLER_TIME);
    }

    private void setVideoplayer() {
        this.bvp = (BetterVideoPlayer) this.mRootView.findViewById(R.id.bvp);
        this.bvp.setCallback(this);
        if (!AppController.VIDEO_STREAMING_URL.equals("")) {
            System.out.println(">>>>>>>>" + AppController.VIDEO_STREAMING_URL);
            this.bvp.setSource(Uri.parse(AppController.VIDEO_STREAMING_URL));
            this.mNoStreamUrl.setVisibility(8);
            return;
        }
        this.mNoStreamUrl.setVisibility(0);
    }

    /* access modifiers changed from: private */
    public void setValuesToAdapter() {
        String currentDay = Utils.currentDay();
        mScheduleListEventModel.clear();
        char c = 65535;
        switch (currentDay.hashCode()) {
            case -2114201671:
                if (currentDay.equals("saturday")) {
                    c = 6;
                    break;
                }
                break;
            case -1266285217:
                if (currentDay.equals("friday")) {
                    c = 5;
                    break;
                }
                break;
            case -1068502768:
                if (currentDay.equals("monday")) {
                    c = 1;
                    break;
                }
                break;
            case -977343923:
                if (currentDay.equals("tuesday")) {
                    c = 2;
                    break;
                }
                break;
            case -891186736:
                if (currentDay.equals("sunday")) {
                    c = 0;
                    break;
                }
                break;
            case 1393530710:
                if (currentDay.equals("wednesday")) {
                    c = 3;
                    break;
                }
                break;
            case 1572055514:
                if (currentDay.equals("thursday")) {
                    c = 4;
                    break;
                }
                break;
        }
        switch (c) {
            case 0:
                if (this.mScheduleList.size() > 0 && ((ScheduleListScheduleModel) this.mScheduleList.get(0)).getDay().equalsIgnoreCase("sunday") && ((ScheduleListScheduleModel) this.mScheduleList.get(0)).getEvents() != null && ((ScheduleListScheduleModel) this.mScheduleList.get(0)).getEvents().size() > 0) {
                    mScheduleListEventModel = ((ScheduleListScheduleModel) this.mScheduleList.get(0)).getEvents();
                    break;
                }
            case 1:
                if (this.mScheduleList.size() > 1 && ((ScheduleListScheduleModel) this.mScheduleList.get(1)).getDay().equalsIgnoreCase("monday") && ((ScheduleListScheduleModel) this.mScheduleList.get(1)).getEvents() != null && ((ScheduleListScheduleModel) this.mScheduleList.get(1)).getEvents().size() > 0) {
                    mScheduleListEventModel = ((ScheduleListScheduleModel) this.mScheduleList.get(1)).getEvents();
                    break;
                }
            case 2:
                if (this.mScheduleList.size() > 2 && ((ScheduleListScheduleModel) this.mScheduleList.get(2)).getDay().equalsIgnoreCase("tuesday") && ((ScheduleListScheduleModel) this.mScheduleList.get(2)).getEvents() != null && ((ScheduleListScheduleModel) this.mScheduleList.get(2)).getEvents().size() > 0) {
                    mScheduleListEventModel = ((ScheduleListScheduleModel) this.mScheduleList.get(2)).getEvents();
                    break;
                }
            case 3:
                if (this.mScheduleList.size() > 3 && ((ScheduleListScheduleModel) this.mScheduleList.get(3)).getDay().equalsIgnoreCase("wednesday") && ((ScheduleListScheduleModel) this.mScheduleList.get(3)).getEvents() != null && ((ScheduleListScheduleModel) this.mScheduleList.get(3)).getEvents().size() > 0) {
                    mScheduleListEventModel = ((ScheduleListScheduleModel) this.mScheduleList.get(3)).getEvents();
                    break;
                }
            case 4:
                if (this.mScheduleList.size() > 4 && ((ScheduleListScheduleModel) this.mScheduleList.get(4)).getDay().equalsIgnoreCase("thursday") && ((ScheduleListScheduleModel) this.mScheduleList.get(4)).getEvents() != null && ((ScheduleListScheduleModel) this.mScheduleList.get(4)).getEvents().size() > 0) {
                    mScheduleListEventModel = ((ScheduleListScheduleModel) this.mScheduleList.get(4)).getEvents();
                    break;
                }
            case 5:
                if (this.mScheduleList.size() > 5 && ((ScheduleListScheduleModel) this.mScheduleList.get(5)).getDay().equalsIgnoreCase("friday") && ((ScheduleListScheduleModel) this.mScheduleList.get(5)).getEvents() != null && ((ScheduleListScheduleModel) this.mScheduleList.get(5)).getEvents().size() > 0) {
                    mScheduleListEventModel = ((ScheduleListScheduleModel) this.mScheduleList.get(5)).getEvents();
                    break;
                }
            case 6:
                if (this.mScheduleList.size() > 6 && ((ScheduleListScheduleModel) this.mScheduleList.get(6)).getDay().equalsIgnoreCase("saturday") && ((ScheduleListScheduleModel) this.mScheduleList.get(6)).getEvents() != null && ((ScheduleListScheduleModel) this.mScheduleList.get(6)).getEvents().size() > 0) {
                    mScheduleListEventModel = ((ScheduleListScheduleModel) this.mScheduleList.get(6)).getEvents();
                    break;
                }
        }
        this.mCurrentDayProgramList = (RecyclerView) this.mRootView.findViewById(R.id.recyclerViewCurrentProgramList);
        if (mScheduleListEventModel == null || mScheduleListEventModel.size() <= 0) {
            this.mNoSchedule.setVisibility(0);
            this.mNoSchedule.setText("No schedule for today");
        } else {
            int it = 0;
            while (it < mScheduleListEventModel.size()) {
                String timeOfDay = new SimpleDateFormat("hh:mm aa").format(Calendar.getInstance().getTime());
                String startTime = ((ScheduleListEventModel) mScheduleListEventModel.get(it)).getShowTimeStart();
                String endTime = ((ScheduleListEventModel) mScheduleListEventModel.get(it)).getShowTimeEnd();
                SimpleDateFormat sdf = new SimpleDateFormat("hh:mm aa");
                try {
                    Date startTimeFormat = sdf.parse(startTime);
                    Date endTimeFormat = sdf.parse(endTime);
                    Date timeOfDayFormat = sdf.parse(timeOfDay);
                    long endTimeDifference = endTimeFormat.getTime() - timeOfDayFormat.getTime();
                    if (timeOfDayFormat.getTime() - startTimeFormat.getTime() < 0 || endTimeDifference < 0) {
                        ((ScheduleListEventModel) mScheduleListEventModel.get(it)).setSelected(false);
                        it++;
                    } else {
                        ((ScheduleListEventModel) mScheduleListEventModel.get(it)).setSelected(true);
                        position = it;
                        it++;
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
        this.async.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR, new Void[0]);
    }

    public void onStarted(BetterVideoPlayer player) {
    }

    public void onPaused(BetterVideoPlayer player) {
    }

    public void onPreparing(BetterVideoPlayer player) {
    }

    public void onPrepared(BetterVideoPlayer player) {
    }

    public void onBuffering(int percent) {
    }

    public void onError(BetterVideoPlayer player, Exception e) {
        e.printStackTrace();
        if (player != null) {
            player.reset();
            player.release();
            Toast.makeText(getActivity(), "Url not valid", 0).show();
        }
    }

    public void onCompletion(BetterVideoPlayer player) {
        if (Utils.isNetworkAvailable(getActivity()) && player != null && this.bvp != null) {
            player.mBtnPlayPause.setClickable(false);
            player.mBtnPlayPause.setEnabled(false);
            this.bvp.reset();
            this.bvp.setCallback(this);
            if (!AppController.VIDEO_STREAMING_URL.equals("")) {
                this.bvp.setSource(Uri.parse(AppController.VIDEO_STREAMING_URL));
            }
        }
    }

    public void onToggleControls(BetterVideoPlayer player, boolean isShowing) {
    }

    public void onPause() {
        super.onPause();
        if (this.bvp != null && this.bvp.isPlaying()) {
            this.bvp.pause();
        }
    }

    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        if (getResources().getConfiguration().orientation == 2) {
            ladscapeMode();
        } else {
            portriateMode();
        }
    }

    private void portriateMode() {
        boolean z = true;
        this.mCurrentDayProgramList = (RecyclerView) this.mRootView.findViewById(R.id.recyclerViewCurrentProgramList);
        boolean z2 = this.mImageViewFullScreen != null;
        if (this.mCurrentDayProgramList == null) {
            z = false;
        }
        if (!((!z2 || !z) || this.mProgramListContainer == null || this.mFrameLayOutContainer == null || this.mProgramListContainer == null)) {
            this.mImageViewFullScreen.setVisibility(0);
            this.mCurrentDayProgramList.setVisibility(0);
            this.mProgramListContainer.setVisibility(0);
            this.mFrameLayOutContainer.setLayoutParams(this.paramsProgramsEqual);
            this.mProgramListContainer.setLayoutParams(this.paramsProgramsEqual);
        }
        this.imageViewResize.setVisibility(8);
    }

    private void ladscapeMode() {
        boolean z;
        boolean z2 = true;
        this.mCurrentDayProgramList = (RecyclerView) this.mRootView.findViewById(R.id.recyclerViewCurrentProgramList);
        if (this.mImageViewFullScreen != null) {
            z = true;
        } else {
            z = false;
        }
        if (this.mCurrentDayProgramList == null) {
            z2 = false;
        }
        if (!((!z || !z2) || this.mProgramListContainer == null || this.mFrameLayOutContainer == null || this.mProgramListContainer == null)) {
            this.mImageViewFullScreen.setVisibility(8);
            this.mCurrentDayProgramList.setVisibility(8);
            this.mProgramListContainer.setVisibility(8);
            this.mFrameLayOutContainer.setLayoutParams(this.paramsFrameLayout);
            this.mProgramListContainer.setLayoutParams(this.paramsProgramContainer);
        }
        this.imageViewResize.setVisibility(0);
        RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(-2, -2);
        params.setMargins(0, 0, 40, 40);
        this.imageViewResize.setLayoutParams(params);
    }

    public void itemSelection(int position2) {
        this.mCurrentDayProgramList.scrollToPosition(position2);
    }

    public void updatePlayList() {
        if (!(this.async == null || this.async.getStatus() == Status.FINISHED)) {
            this.async.cancel(true);
        }
        if (mScheduleListEventModel != null && mScheduleListEventModel.size() > 0) {
            this.mCurrentDayProgramList.setVisibility(0);
            this.async = new Async();
            this.async.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR, new Void[0]);
        }
    }

    public void onDestroy() {
        super.onDestroy();
        if (this.backgroundServiceBound) {
            if (this.updateProgramNamesService != null) {
                this.updateProgramNamesService.cancelCallService();
                this.updateProgramNamesService.stopSelf();
                this.updateProgramNamesService.setUpdatePlayListListener(null);
            }
            getActivity().unbindService(this.backgroundServiceConnection);
            getActivity().stopService(new Intent(getActivity(), BackgroundService.class));
        }
    }
}
