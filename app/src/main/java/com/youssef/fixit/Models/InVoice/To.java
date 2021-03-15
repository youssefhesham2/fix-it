
package com.youssef.fixit.Models.InVoice;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class To {
    @SerializedName("id")
    @Expose
    private Integer id;
    @SerializedName("name")
    @Expose
    private String name;
    @SerializedName("image")
    @Expose
    private String image;
    @SerializedName("area_id")
    @Expose
    private Integer areaId;
    @SerializedName("rate")
    @Expose
    private Float rate;
    @SerializedName("total_reviews")
    @Expose
    private Integer totalReviews;
    @SerializedName("area")
    @Expose
    private String area;
    @SerializedName("completed_projects")
    @Expose
    private Integer completedProjects;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public Integer getAreaId() {
        return areaId;
    }

    public void setAreaId(Integer areaId) {
        this.areaId = areaId;
    }

    public Float getRate() {
        return rate;
    }

    public void setRate(Float rate) {
        this.rate = rate;
    }

    public Integer getTotalReviews() {
        return totalReviews;
    }

    public void setTotalReviews(Integer totalReviews) {
        this.totalReviews = totalReviews;
    }

    public String getArea() {
        return area;
    }

    public void setArea(String area) {
        this.area = area;
    }

    public Integer getCompletedProjects() {
        return completedProjects;
    }

    public void setCompletedProjects(Integer completedProjects) {
        this.completedProjects = completedProjects;
    }

}
