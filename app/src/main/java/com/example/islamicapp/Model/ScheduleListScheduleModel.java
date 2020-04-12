package com.example.islamicapp.Model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class ScheduleListScheduleModel {
    @SerializedName("day")
    @Expose
    private String day;
    @SerializedName("events")
    @Expose
    private List<ScheduleListEventModel> events = null;

    public String getDay() {
        return this.day;
    }

    public void setDay(String day2) {
        this.day = day2;
    }

    public List<ScheduleListEventModel> getEvents() {
        return this.events;
    }

    public void setEvents(List<ScheduleListEventModel> events2) {
        this.events = events2;
    }
}
