package com.example.islamicapp.Interface;

import com.example.islamicapp.Model.ScheduleListScheduleModel;

import java.util.ArrayList;

public interface AllDaysScheduleCallBack {
    void passApiResponseToSchedulePage(ArrayList<ScheduleListScheduleModel> arrayList, String str);
}
