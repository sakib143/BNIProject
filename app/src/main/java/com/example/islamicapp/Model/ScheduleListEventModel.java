package com.example.islamicapp.Model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ScheduleListEventModel {
    @SerializedName("duration")
    @Expose
    private String duration;
    private boolean isSelected;
    @SerializedName("show_description")
    @Expose
    private String showDescription;
    @SerializedName("show_time_end")
    @Expose
    private String showTimeEnd;
    @SerializedName("show_time_start")
    @Expose
    private String showTimeStart;
    @SerializedName("show_title")
    @Expose
    private String showTitle;

    public boolean isSelected() {
        return this.isSelected;
    }

    public void setSelected(boolean selected) {
        this.isSelected = selected;
    }

    public String getShowTimeStart() {
        return this.showTimeStart;
    }

    public void setShowTimeStart(String showTimeStart2) {
        this.showTimeStart = showTimeStart2;
    }

    public String getShowTimeEnd() {
        return this.showTimeEnd;
    }

    public void setShowTimeEnd(String showTimeEnd2) {
        this.showTimeEnd = showTimeEnd2;
    }

    public String getShowTitle() {
        return this.showTitle;
    }

    public void setShowTitle(String showTitle2) {
        this.showTitle = showTitle2;
    }

    public String getDuration() {
        return this.duration;
    }

    public void setDuration(String duration2) {
        this.duration = duration2;
    }

    public String getShowDescription() {
        return this.showDescription;
    }

    public void setShowDescription(String showDescription2) {
        this.showDescription = showDescription2;
    }
}
