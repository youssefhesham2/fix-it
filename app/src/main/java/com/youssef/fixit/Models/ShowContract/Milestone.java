
package com.youssef.fixit.Models.ShowContract;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Milestone {

    @SerializedName("id")
    @Expose
    private Integer id;
    @SerializedName("details")
    @Expose
    private String details;
    @SerializedName("price")
    @Expose
    private Integer price;
    @SerializedName("contract_id")
    @Expose
    private Integer contractId;
    @SerializedName("status")
    @Expose
    private String status;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getDetails() {
        return details;
    }

    public void setDetails(String details) {
        this.details = details;
    }

    public Integer getPrice() {
        return price;
    }

    public void setPrice(Integer price) {
        this.price = price;
    }

    public Integer getContractId() {
        return contractId;
    }

    public void setContractId(Integer contractId) {
        this.contractId = contractId;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

}
