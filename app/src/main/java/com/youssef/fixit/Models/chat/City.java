
package com.youssef.fixit.Models.chat;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class City {

    @SerializedName("id")
    @Expose
    private Integer id;
    @SerializedName("city_id")
    @Expose
    private Integer cityId;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getCityId() {
        return cityId;
    }

    public void setCityId(Integer cityId) {
        this.cityId = cityId;
    }

}
