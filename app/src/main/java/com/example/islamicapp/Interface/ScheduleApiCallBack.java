package com.example.islamicapp.Interface;


import com.example.islamicapp.Model.ScheduleListScheduleModel;
import java.util.ArrayList;

public interface ScheduleApiCallBack {
    void passScheduleApiResponse(ArrayList<ScheduleListScheduleModel> arrayList, String str);
}
