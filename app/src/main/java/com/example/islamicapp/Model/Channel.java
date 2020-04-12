package com.example.islamicapp.Model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Channel {
    @SerializedName("channel_url")
    @Expose
    private String channelUrl;

    public String getChannelUrl() {
        return this.channelUrl;
    }

    public void setChannelUrl(String channelUrl2) {
        this.channelUrl = channelUrl2;
    }
}
