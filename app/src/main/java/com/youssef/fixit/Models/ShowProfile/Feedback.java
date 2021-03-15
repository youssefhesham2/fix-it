
package com.youssef.fixit.Models.ShowProfile;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Feedback {

    @SerializedName("comment")
    @Expose
    private String comment;
    @SerializedName("rate")
    @Expose
    private String rate;
    @SerializedName("name")
    @Expose
    private String name;
    @SerializedName("total_price")
    @Expose
    private Integer totalPrice;
    @SerializedName("pay_type")
    @Expose
    private String payType;

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public String getRate() {
        return rate;
    }

    public void setRate(String rate) {
        this.rate = rate;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(Integer totalPrice) {
        this.totalPrice = totalPrice;
    }

    public String getPayType() {
        return payType;
    }

    public void setPayType(String payType) {
        this.payType = payType;
    }

}
