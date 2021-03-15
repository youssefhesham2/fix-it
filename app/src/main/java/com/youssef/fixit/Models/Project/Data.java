
package com.youssef.fixit.Models.Project;

import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Data {

    @SerializedName("title")
    @Expose
    private String title;
    @SerializedName("description")
    @Expose
    private String description;
    @SerializedName("pay_type")
    @Expose
    private String payType;
    @SerializedName("price_from")
    @Expose
    private Integer priceFrom;
    @SerializedName("price_to")
    @Expose
    private Integer priceTo;
    @SerializedName("price")
    @Expose
    private String price;
    @SerializedName("status")
    @Expose
    private String status;
    @SerializedName("currency")
    @Expose
    private String currency;
    @SerializedName("area")
    @Expose
    private String area;
    @SerializedName("category")
    @Expose
    private String category;
    @SerializedName("bids_count")
    @Expose
    private Integer bidsCount;
    @SerializedName("unpaid_milestones_count")
    @Expose
    private Object unpaidMilestonesCount;
    @SerializedName("have_rate")
    @Expose
    private Object haveRate;
    @SerializedName("address")
    @Expose
    private String address;
    @SerializedName("how_long")
    @Expose
    private String howLong;
    @SerializedName("user")
    @Expose
    private User user;
    @SerializedName("time_left")
    @Expose
    private String timeLeft;
    @SerializedName("images")
    @Expose
    private List<Object> images = null;

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

    public String getPayType() {
        return payType;
    }

    public void setPayType(String payType) {
        this.payType = payType;
    }

    public Integer getPriceFrom() {
        return priceFrom;
    }

    public void setPriceFrom(Integer priceFrom) {
        this.priceFrom = priceFrom;
    }

    public Integer getPriceTo() {
        return priceTo;
    }

    public void setPriceTo(Integer priceTo) {
        this.priceTo = priceTo;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }

    public String getArea() {
        return area;
    }

    public void setArea(String area) {
        this.area = area;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public Integer getBidsCount() {
        return bidsCount;
    }

    public void setBidsCount(Integer bidsCount) {
        this.bidsCount = bidsCount;
    }

    public Object getUnpaidMilestonesCount() {
        return unpaidMilestonesCount;
    }

    public void setUnpaidMilestonesCount(Object unpaidMilestonesCount) {
        this.unpaidMilestonesCount = unpaidMilestonesCount;
    }

    public Object getHaveRate() {
        return haveRate;
    }

    public void setHaveRate(Object haveRate) {
        this.haveRate = haveRate;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getHowLong() {
        return howLong;
    }

    public void setHowLong(String howLong) {
        this.howLong = howLong;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public String getTimeLeft() {
        return timeLeft;
    }

    public void setTimeLeft(String timeLeft) {
        this.timeLeft = timeLeft;
    }

    public List<Object> getImages() {
        return images;
    }

    public void setImages(List<Object> images) {
        this.images = images;
    }

}
