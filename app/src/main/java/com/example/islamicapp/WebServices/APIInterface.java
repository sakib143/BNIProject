package com.example.islamicapp.WebServices;

import com.example.islamicapp.Model.Channel;
import com.example.islamicapp.Model.ScheduleListBaseModel;

import retrofit2.Call;
import retrofit2.http.GET;

public interface APIInterface {
    @GET("<...End point of your video streaming url..>")
    Call<Channel> channelUrl();

    @GET("5a901dda2f000061006cab56")
    Call<ScheduleListBaseModel> getScheduleList();
}
