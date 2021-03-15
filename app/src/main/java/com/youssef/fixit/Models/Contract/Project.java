
package com.youssef.fixit.Models.Contract
        ;

import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Project {

    @SerializedName("id")
    @Expose
    private Integer id;
    @SerializedName("currency")
    @Expose
    private String currency;
    @SerializedName("created_by")
    @Expose
    private Integer createdBy;
    @SerializedName("is_paid")
    @Expose
    private Integer isPaid;
    @SerializedName("bids_count")
    @Expose
    private Integer bidsCount;
    @SerializedName("amount_spent")
    @Expose
    private Integer amountSpent;
    @SerializedName("unpaid_milestones_count")
    @Expose
    private Integer unpaidMilestonesCount;
    @SerializedName("have_rate")
    @Expose
    private Object haveRate;
    @SerializedName("payment_logs")
    @Expose
    private List<Object> paymentLogs = null;
    @SerializedName("contract")
    @Expose
    private Contract contract;
    @SerializedName("title")
    @Expose
    private String title;
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }

    public Integer getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(Integer createdBy) {
        this.createdBy = createdBy;
    }

    public Integer getIsPaid() {
        return isPaid;
    }

    public void setIsPaid(Integer isPaid) {
        this.isPaid = isPaid;
    }

    public Integer getBidsCount() {
        return bidsCount;
    }

    public void setBidsCount(Integer bidsCount) {
        this.bidsCount = bidsCount;
    }

    public Integer getAmountSpent() {
        return amountSpent;
    }

    public void setAmountSpent(Integer amountSpent) {
        this.amountSpent = amountSpent;
    }

    public Integer getUnpaidMilestonesCount() {
        return unpaidMilestonesCount;
    }

    public void setUnpaidMilestonesCount(Integer unpaidMilestonesCount) {
        this.unpaidMilestonesCount = unpaidMilestonesCount;
    }

    public Object getHaveRate() {
        return haveRate;
    }

    public void setHaveRate(Object haveRate) {
        this.haveRate = haveRate;
    }

    public List<Object> getPaymentLogs() {
        return paymentLogs;
    }

    public void setPaymentLogs(List<Object> paymentLogs) {
        this.paymentLogs = paymentLogs;
    }

    public Contract getContract() {
        return contract;
    }

    public void setContract(Contract contract) {
        this.contract = contract;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
}
