package com.example.islamicapp.Utils;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.NetworkInfo.State;

import androidx.core.app.NotificationCompat;

import com.example.islamicapp.AppController;
import com.example.islamicapp.Interface.AllDaysScheduleCallBack;
import com.example.islamicapp.Interface.ScheduleApiCallBack;
import com.example.islamicapp.Model.ScheduleListBaseModel;
import com.example.islamicapp.Model.ScheduleListScheduleModel;
import com.example.islamicapp.WebServices.APIClient;
import com.example.islamicapp.WebServices.APIInterface;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Calendar;
import org.json.JSONException;
import org.json.JSONObject;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class Utils {
    public static AllDaysScheduleCallBack mAllDaysScheduleApiCallBack;
    private static APIInterface mApiInterface;
    public static ScheduleApiCallBack mScheduleApiCallBack;
    /* access modifiers changed from: private */
    public static ScheduleListBaseModel mScheduleListBaseModel;
    public static ArrayList<ScheduleListScheduleModel> schedule = new ArrayList<>();

    public static boolean isNetworkAvailable(Context context) {
        boolean status = false;
        try {
            ConnectivityManager cm = (ConnectivityManager) context.getSystemService("connectivity");
            NetworkInfo netInfo = null;
            if (cm != null) {
                netInfo = cm.getNetworkInfo(0);
            }
            if (netInfo == null || netInfo.getState() != State.CONNECTED) {
                NetworkInfo netInfo2 = cm.getNetworkInfo(1);
                if (netInfo2 != null && netInfo2.getState() == State.CONNECTED) {
                    status = true;
                }
            } else {
                status = true;
            }
            return status;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public static void callSchedule(Context context) {
        if (!AppController.WEB_SERVICE_URL.equals("") && !AppController.GET_SCHEDULE_SERVICE_ENDPOINT.equals("") && !AppController.WEB_SERVICE_URL.equalsIgnoreCase("<...your Base URL...>")) {
            mApiInterface = (APIInterface) APIClient.getList().create(APIInterface.class);
            mApiInterface.getScheduleList().enqueue(new Callback() {
                public void onResponse(Call call, Response response) {
                    if (response.body() != null) {
                        Utils.mScheduleListBaseModel = (ScheduleListBaseModel) response.body();
                        if (!(Utils.mScheduleListBaseModel == null || Utils.mScheduleListBaseModel.getSchedule() == null || Utils.mScheduleListBaseModel.getSchedule().size() <= 0)) {
                            Utils.schedule.clear();
                            int size = Utils.mScheduleListBaseModel.getSchedule().size();
                            for (int i = 0; i < size; i++) {
                                Utils.schedule.add(Utils.mScheduleListBaseModel.getSchedule().get(i));
                            }
                            Utils.mScheduleApiCallBack.passScheduleApiResponse(Utils.schedule, "HomeFragment");
                            Utils.mAllDaysScheduleApiCallBack.passApiResponseToSchedulePage(Utils.schedule, "HomeFragment");
                        }
                    }
                    if (response.errorBody() != null) {
                        try {
                            new JSONObject(response.errorBody().string()).getString(NotificationCompat.CATEGORY_MESSAGE);
                        } catch (IOException | JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }

                public void onFailure(Call call, Throwable t) {
                    call.cancel();
                }
            });
        }
    }

    public static String currentDay() {
        String mDay = "";
        switch (Calendar.getInstance().get(7)) {
            case 1:
                return "sunday";
            case 2:
                return "monday";
            case 3:
                return "tuesday";
            case 4:
                return "wednesday";
            case 5:
                return "thursday";
            case 6:
                return "friday";
            case 7:
                return "saturday";
            default:
                return mDay;
        }
    }

    public static void passApiResponse(ScheduleApiCallBack scheduleApiCallBack) {
        mScheduleApiCallBack = scheduleApiCallBack;
    }

    public static void passApiResponseToSchedulePage(AllDaysScheduleCallBack alldaysScheduleApiCallBack) {
        mAllDaysScheduleApiCallBack = alldaysScheduleApiCallBack;
    }
}
