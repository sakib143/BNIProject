package com.example.islamicapp.Model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class ScheduleListBaseModel {
    @SerializedName("schedule")
    @Expose
    private List<ScheduleListScheduleModel> schedule = null;

    public List<ScheduleListScheduleModel> getSchedule() {
        return this.schedule;
    }

    public void setSchedule(List<ScheduleListScheduleModel> schedule2) {
        this.schedule = schedule2;
    }
}