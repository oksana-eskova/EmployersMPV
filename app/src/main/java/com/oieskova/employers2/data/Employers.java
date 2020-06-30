package com.oieskova.employers2.data;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class Employers {
    @SerializedName("response")
    @Expose
    private List<Employee> response = null;

    public List<Employee> getResponse() {
        return response;
    }

    public void setResponse(List<Employee> response) {
        this.response = response;
    }
}
