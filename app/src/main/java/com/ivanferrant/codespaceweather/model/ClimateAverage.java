package com.ivanferrant.codespaceweather.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class ClimateAverage {
    @SerializedName("month")
    @Expose
    private List<Month> month = null;

    public List<Month> getMonth() {
        return month;
    }

    public void setMonth(List<Month> month) {
        this.month = month;
    }
}
