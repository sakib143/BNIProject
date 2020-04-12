package com.example.islamicapp.Fragment;

import android.app.Activity;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.islamicapp.Adapter.ProgramListAdapter;
import com.example.islamicapp.Adapter.ProgramScheduleDayAdapter;
import com.example.islamicapp.CustomView.SKtextViewRobotoRegular;
import com.example.islamicapp.Interface.AllDaysScheduleCallBack;
import com.example.islamicapp.Interface.DaySelectionCallBack;
import com.example.islamicapp.Model.ProgramListModel;
import com.example.islamicapp.Model.ScheduleListEventModel;
import com.example.islamicapp.Model.ScheduleListScheduleModel;
import com.example.islamicapp.R;
import com.example.islamicapp.Utils.Utils;

import java.util.ArrayList;
import java.util.List;

public class ProgramScheduleFragment extends Fragment implements DaySelectionCallBack {
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    /* access modifiers changed from: private */
    public static ArrayList<ScheduleListScheduleModel> mScheduleList = new ArrayList<>();
    /* access modifiers changed from: private */
    public String currentDayAdapter;
    private RecyclerView daySelectionList;
    private ProgramScheduleDayAdapter mDayAdapter;
    private ArrayList<String> mDaysList = new ArrayList<>();
    private SKtextViewRobotoRegular mNoSchedule;
    private ArrayList<ProgramListModel> mProgramList = new ArrayList<>();
    private ProgramListAdapter mProgramListAdapter;
    private RecyclerView mProgramListView;
    private View mRootView;
    private List<ScheduleListEventModel> mScheduleListEventModel = new ArrayList();

    public static ProgramScheduleFragment newInstance(String param1, String param2) {
        ProgramScheduleFragment fragment = new ProgramScheduleFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            String string = getArguments().getString(ARG_PARAM1);
            getArguments().getString(ARG_PARAM2);
        }
    }

    @Nullable
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        this.mRootView = inflater.inflate(R.layout.fragment_program_schedule, container, false);
        initUi();
        Log.e("called", "program schedule");
        return this.mRootView;
    }

    private void initUi() {
        this.mNoSchedule = (SKtextViewRobotoRegular) this.mRootView.findViewById(R.id.mNoSchedule);
        this.mProgramListView = (RecyclerView) this.mRootView.findViewById(R.id.recyclerViewProgramList);
        if (this.mScheduleListEventModel == null || this.mScheduleListEventModel.size() <= 0) {
//            this.mNoSchedule.setVisibility(0);
        } else {
//            this.mNoSchedule.setVisibility(8);
        }
        this.currentDayAdapter = Utils.currentDay();
        setDaySelection(this.currentDayAdapter);
        Utils.passApiResponseToSchedulePage(new AllDaysScheduleCallBack() {
            public void passApiResponseToSchedulePage(ArrayList<ScheduleListScheduleModel> schedule, String name) {
                if (ProgramScheduleFragment.mScheduleList != null && ProgramScheduleFragment.mScheduleList.size() > 0) {
                    ProgramScheduleFragment.mScheduleList.clear();
                }
                ProgramScheduleFragment.mScheduleList = schedule;
                String currentDay = Utils.currentDay();
                if (ProgramScheduleFragment.mScheduleList.size() > 0) {
                    ProgramScheduleFragment.this.setDaySelection(ProgramScheduleFragment.this.currentDayAdapter);
//                    ProgramScheduleFragment.this.setValuesToAdapter(currentDay);
                }
            }
        });
    }

    /* access modifiers changed from: private */
    public void setDaySelection(String day) {
        int position = 0;
        this.mDaysList.clear();
        if (mScheduleList != null && mScheduleList.size() > 0) {
            for (int i = 0; i < mScheduleList.size(); i++) {
                this.mDaysList.add(((ScheduleListScheduleModel) mScheduleList.get(i)).getDay());
            }
        }
        this.mDayAdapter = new ProgramScheduleDayAdapter(day, getActivity(), this.mDaysList, this);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity(), RecyclerView.HORIZONTAL, false);
        this.daySelectionList = (RecyclerView) this.mRootView.findViewById(R.id.recyclerViewDayMenu);
        this.daySelectionList.setLayoutManager(layoutManager);
        this.daySelectionList.setItemAnimator(new DefaultItemAnimator());
        this.daySelectionList.setHasFixedSize(true);
        this.daySelectionList.setAdapter(this.mDayAdapter);
//        char c = 65535;
//        switch (day.hashCode()) {
//            case -2114201671:
//                if (day.equals("saturday")) {
//                    c = 6;
//                    break;
//                }
//                break;
//            case -1266285217:
//                if (day.equals("friday")) {
//                    c = 5;
//                    break;
//                }
//                break;
//            case -1068502768:
//                if (day.equals("monday")) {
//                    c = 1;
//                    break;
//                }
//                break;
//            case -977343923:
//                if (day.equals("tuesday")) {
//                    c = 2;
//                    break;
//                }
//                break;
//            case -891186736:
//                if (day.equals("sunday")) {
//                    c = 0;
//                    break;
//                }
//                break;
//            case 1393530710:
//                if (day.equals("wednesday")) {
//                    c = 3;
//                    break;
//                }
//                break;
//            case 1572055514:
//                if (day.equals("thursday")) {
//                    c = 4;
//                    break;
//                }
//                break;
//        }
//        switch (c) {
//            case 0:
//                position = 0;
//                break;
//            case 1:
//                position = 1;
//                break;
//            case 2:
//                position = 2;
//                break;
//            case 3:
//                position = 3;
//                break;
//            case 4:
//                position = 4;
//                break;
//            case 5:
//                position = 5;
//                break;
//            case 6:
//                position = 6;
//                break;
//        }
        this.daySelectionList.getLayoutManager().scrollToPosition(position - 1);
    }

    /* access modifiers changed from: private */
//    public void setValuesToAdapter(String currentDay) {
//        char c = 65535;
//        switch (currentDay.hashCode()) {
//            case -2114201671:
//                if (currentDay.equals("saturday")) {
//                    c = 6;
//                    break;
//                }
//                break;
//            case -1266285217:
//                if (currentDay.equals("friday")) {
//                    c = 5;
//                    break;
//                }
//                break;
//            case -1068502768:
//                if (currentDay.equals("monday")) {
//                    c = 1;
//                    break;
//                }
//                break;
//            case -977343923:
//                if (currentDay.equals("tuesday")) {
//                    c = 2;
//                    break;
//                }
//                break;
//            case -891186736:
//                if (currentDay.equals("sunday")) {
//                    c = 0;
//                    break;
//                }
//                break;
//            case 1393530710:
//                if (currentDay.equals("wednesday")) {
//                    c = 3;
//                    break;
//                }
//                break;
//            case 1572055514:
//                if (currentDay.equals("thursday")) {
//                    c = 4;
//                    break;
//                }
//                break;
//        }
//        switch (c) {
//            case 0:
//                if (mScheduleList.size() > 0 && ((ScheduleListScheduleModel) mScheduleList.get(0)).getEvents() != null && ((ScheduleListScheduleModel) mScheduleList.get(0)).getEvents().size() > 0) {
//                    this.mScheduleListEventModel = ((ScheduleListScheduleModel) mScheduleList.get(0)).getEvents();
//                    break;
//                }
//            case 1:
//                if (mScheduleList.size() > 1 && ((ScheduleListScheduleModel) mScheduleList.get(1)).getEvents() != null && ((ScheduleListScheduleModel) mScheduleList.get(1)).getEvents().size() > 0) {
//                    this.mScheduleListEventModel = ((ScheduleListScheduleModel) mScheduleList.get(1)).getEvents();
//                    break;
//                }
//            case 2:
//                if (mScheduleList.size() > 2 && ((ScheduleListScheduleModel) mScheduleList.get(2)).getEvents() != null && ((ScheduleListScheduleModel) mScheduleList.get(2)).getEvents().size() > 0) {
//                    this.mScheduleListEventModel = ((ScheduleListScheduleModel) mScheduleList.get(2)).getEvents();
//                    break;
//                }
//            case 3:
//                if (mScheduleList.size() > 3 && ((ScheduleListScheduleModel) mScheduleList.get(3)).getEvents() != null && ((ScheduleListScheduleModel) mScheduleList.get(3)).getEvents().size() > 0) {
//                    this.mScheduleListEventModel = ((ScheduleListScheduleModel) mScheduleList.get(3)).getEvents();
//                    break;
//                }
//            case 4:
//                if (mScheduleList.size() > 4 && ((ScheduleListScheduleModel) mScheduleList.get(4)).getEvents() != null && ((ScheduleListScheduleModel) mScheduleList.get(4)).getEvents().size() > 0) {
//                    this.mScheduleListEventModel = ((ScheduleListScheduleModel) mScheduleList.get(4)).getEvents();
//                    break;
//                }
//            case 5:
//                if (mScheduleList.size() > 5 && ((ScheduleListScheduleModel) mScheduleList.get(5)).getEvents() != null && ((ScheduleListScheduleModel) mScheduleList.get(5)).getEvents().size() > 0) {
//                    this.mScheduleListEventModel = ((ScheduleListScheduleModel) mScheduleList.get(5)).getEvents();
//                    break;
//                }
//            case 6:
//                if (mScheduleList.size() > 6 && ((ScheduleListScheduleModel) mScheduleList.get(6)).getEvents() != null && ((ScheduleListScheduleModel) mScheduleList.get(6)).getEvents().size() > 0) {
//                    this.mScheduleListEventModel = ((ScheduleListScheduleModel) mScheduleList.get(6)).getEvents();
//                    break;
//                }
//        }
//        if (this.mScheduleListEventModel == null || this.mScheduleListEventModel.size() <= 0) {
////            this.mNoSchedule.setVisibility(0);
//            this.mNoSchedule.setText("No schedule for today");
//            return;
//        }
////        this.mNoSchedule.setVisibility(8);
//        this.mProgramListAdapter = new ProgramListAdapter(getActivity(), this.mScheduleListEventModel);
//        this.mProgramListView.setLayoutManager(new LinearLayoutManager(getActivity()));
//        this.mProgramListView.setItemAnimator(new DefaultItemAnimator());
//        this.mProgramListView.setHasFixedSize(true);
//        this.mProgramListView.setAdapter(this.mProgramListAdapter);
//    }

    public void daySelected(String name, int position) {
        if (mScheduleList.size() > 0) {
//            setValuesToAdapter(name);
            this.currentDayAdapter = name;
            setDaySelection(name);
            this.daySelectionList.getLayoutManager().scrollToPosition(position - 1);
        }
    }

    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if (isVisibleToUser) {
            Activity a = getActivity();
            if (a != null) {
                a.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_FULL_SENSOR);
            }
        }
    }
}
