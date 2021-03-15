
package com.youssef.fixit.Models.Ads.Package;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Datum {

    @SerializedName("id")
    @Expose
    private Integer id;
    @SerializedName("title")
    @Expose
    private String title;
    @SerializedName("description")
    @Expose
    private String description;
    @SerializedName("price")
    @Expose
    private Integer price;
    @SerializedName("add_to_home")
    @Expose
    private Integer addToHome;
    @SerializedName("add_to_cat")
    @Expose
    private Integer addToCat;
    @SerializedName("daily_duration")
    @Expose
    private String dailyDuration;
    @SerializedName("permanent")
    @Expose
    private Integer permanent;
    @SerializedName("ads_count")
    @Expose
    private Integer adsCount;
    @SerializedName("months")
    @Expose
    private Integer months;
    @SerializedName("created_by")
    @Expose
    private Integer createdBy;
    @SerializedName("deleted_by")
    @Expose
    private Object deletedBy;
    @SerializedName("deleted_at")
    @Expose
    private Object deletedAt;
    @SerializedName("created_at")
    @Expose
    private String createdAt;
    @SerializedName("updated_at")
    @Expose
    private String updatedAt;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Integer getPrice() {
        return price;
    }

    public void setPrice(Integer price) {
        this.price = price;
    }

    public Integer getAddToHome() {
        return addToHome;
    }

    public void setAddToHome(Integer addToHome) {
        this.addToHome = addToHome;
    }

    public Integer getAddToCat() {
        return addToCat;
    }

    public void setAddToCat(Integer addToCat) {
        this.addToCat = addToCat;
    }

    public String getDailyDuration() {
        return dailyDuration;
    }

    public void setDailyDuration(String dailyDuration) {
        this.dailyDuration = dailyDuration;
    }

    public Integer getPermanent() {
        return permanent;
    }

    public void setPermanent(Integer permanent) {
        this.permanent = permanent;
    }

    public Integer getAdsCount() {
        return adsCount;
    }

    public void setAdsCount(Integer adsCount) {
        this.adsCount = adsCount;
    }

    public Integer getMonths() {
        return months;
    }

    public void setMonths(Integer months) {
        this.months = months;
    }

    public Integer getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(Integer createdBy) {
        this.createdBy = createdBy;
    }

    public Object getDeletedBy() {
        return deletedBy;
    }

    public void setDeletedBy(Object deletedBy) {
        this.deletedBy = deletedBy;
    }

    public Object getDeletedAt() {
        return deletedAt;
    }

    public void setDeletedAt(Object deletedAt) {
        this.deletedAt = deletedAt;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }

    public String getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(String updatedAt) {
        this.updatedAt = updatedAt;
    }

}
