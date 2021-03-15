
package com.youssef.fixit.Models.chat;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Users {

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
    @SerializedName("role_id")
    @Expose
    private Integer roleId;
    @SerializedName("area")
    @Expose
    private String area;
    @SerializedName("city")
    @Expose
    private City city;
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

    public Integer getRoleId() {
        return roleId;
    }

    public void setRoleId(Integer roleId) {
        this.roleId = roleId;
    }

    public String getArea() {
        return area;
    }

    public void setArea(String area) {
        this.area = area;
    }

    public City getCity() {
        return city;
    }

    public void setCity(City city) {
        this.city = city;
    }

    public Integer getCompletedProjects() {
        return completedProjects;
    }

    public void setCompletedProjects(Integer completedProjects) {
        this.completedProjects = completedProjects;
    }

}
