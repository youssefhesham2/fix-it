
package com.youssef.fixit.Models.Jobs;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Datum {

    @SerializedName("id")
    @Expose
    private Integer id;
    @SerializedName("price")
    @Expose
    private String price;
    @SerializedName("status")
    @Expose
    private String status;
    @SerializedName("title")
    @Expose
    private String title;
    @SerializedName("address")
    @Expose
    private String address;
    @SerializedName("category_id")
    @Expose
    private Integer categoryId;
    @SerializedName("is_paid")
    @Expose
    private Integer isPaid;
    @SerializedName("currency")
    @Expose
    private String currency;
    @SerializedName("area_id")
    @Expose
    private Integer areaId;
    @SerializedName("created_at")
    @Expose
    private String createdAt;
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
    @SerializedName("area")
    @Expose
    private Area area;
    @SerializedName("category")
    @Expose
    private Category category;
    @SerializedName("contract")
    @Expose
    private Contract contract;
    /*@SerializedName("payment_logs")
    @Expose
    private List<String> paymentLogs = null;*/

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
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

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public Integer getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(Integer categoryId) {
        this.categoryId = categoryId;
    }

    public Integer getIsPaid() {
        return isPaid;
    }

    public void setIsPaid(Integer isPaid) {
        this.isPaid = isPaid;
    }

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }

    public Integer getAreaId() {
        return areaId;
    }

    public void setAreaId(Integer areaId) {
        this.areaId = areaId;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
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

    public Area getArea() {
        return area;
    }

    public void setArea(Area area) {
        this.area = area;
    }

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }

    public Contract getContract() {
        return contract;
    }

    public void setContract(Contract contract) {
        this.contract = contract;
    }
//
//    public List<Object> getPaymentLogs() {
//        return paymentLogs;
//    }
//
//    public void setPaymentLogs(List<Object> paymentLogs) {
//        this.paymentLogs = paymentLogs;
//    }
//kkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkk
//
//    @SerializedName("id")
//    @Expose
//    private Integer id;
//    @SerializedName("title")
//    @Expose
//    private String title;
//    @SerializedName("description")
//    @Expose
//    private String description;
//    @SerializedName("pay_type")
//    @Expose
//    private String payType;
//    @SerializedName("price_from")
//    @Expose
//    private Integer priceFrom;
//    @SerializedName("price_to")
//    @Expose
//    private Integer priceTo;
//    @SerializedName("address")
//    @Expose
//    private String address;
//    @SerializedName("how_long")
//    @Expose
//    private String howLong;
//    @SerializedName("status")
//    @Expose
//    private String status;
//    @SerializedName("price")
//    @Expose
//    private String price;
//    @SerializedName("currency")
//    @Expose
//    private String currency;
//    @SerializedName("area_id")
//    @Expose
//    private Integer areaId;
//    @SerializedName("category_id")
//    @Expose
//    private Integer categoryId;
//    @SerializedName("created_by")
//    @Expose
//    private Integer createdBy;
//    @SerializedName("deleted_at")
//    @Expose
//    private Object deletedAt;
//    @SerializedName("created_at")
//    @Expose
//    private String createdAt;
//    @SerializedName("updated_at")
//    @Expose
//    private String updatedAt;
//    @SerializedName("bids_count")
//    @Expose
//    private Integer bidsCount;
//    @SerializedName("area")
//    @Expose
//    private Area area;
//    @SerializedName("category")
//    @Expose
//    private Category category;
//    @SerializedName("contract")
//    @Expose
//    private Contract contract;
//
//    @SerializedName("unpaid_milestones_count")
//    @Expose
//    private Integer unpaidMilestonesCount;
//    @SerializedName("have_rate")
//    @Expose
//    private Object haveRate;
//
//    @SerializedName("is_paid")
//    @Expose
//    private Integer isPaid;
//
//    public Integer getUnpaidMilestonesCount() {
//        return unpaidMilestonesCount;
//    }
//
//    public void setUnpaidMilestonesCount(Integer unpaidMilestonesCount) {
//        this.unpaidMilestonesCount = unpaidMilestonesCount;
//    }
//
//    public Object getHaveRate() {
//        return haveRate;
//    }
//
//    public void setHaveRate(Object haveRate) {
//        this.haveRate = haveRate;
//    }
//
//    public Integer getIsPaid() {
//        return isPaid;
//    }
//
//    public void setIsPaid(Integer isPaid) {
//        this.isPaid = isPaid;
//    }
//
//    public Contract getContract() {
//        return contract;
//    }
//
//    public void setContract(Contract contract) {
//        this.contract = contract;
//    }
//
//    public Integer getId() {
//        return id;
//    }
//
//    public void setId(Integer id) {
//        this.id = id;
//    }
//
//    public String getTitle() {
//        return title;
//    }
//
//    public void setTitle(String title) {
//        this.title = title;
//    }
//
//    public String getDescription() {
//        return description;
//    }
//
//    public void setDescription(String description) {
//        this.description = description;
//    }
//
//    public String getPayType() {
//        return payType;
//    }
//
//    public void setPayType(String payType) {
//        this.payType = payType;
//    }
//
//    public Integer getPriceFrom() {
//        return priceFrom;
//    }
//
//    public void setPriceFrom(Integer priceFrom) {
//        this.priceFrom = priceFrom;
//    }
//
//    public Integer getPriceTo() {
//        return priceTo;
//    }
//
//    public void setPriceTo(Integer priceTo) {
//        this.priceTo = priceTo;
//    }
//
//    public String getAddress() {
//        return address;
//    }
//
//    public void setAddress(String address) {
//        this.address = address;
//    }
//
//    public String getHowLong() {
//        return howLong;
//    }
//
//    public void setHowLong(String howLong) {
//        this.howLong = howLong;
//    }
//
//    public Integer getAreaId() {
//        return areaId;
//    }
//
//    public void setAreaId(Integer areaId) {
//        this.areaId = areaId;
//    }
//
//    public Integer getCategoryId() {
//        return categoryId;
//    }
//
//    public void setCategoryId(Integer categoryId) {
//        this.categoryId = categoryId;
//    }
//
//    public Integer getCreatedBy() {
//        return createdBy;
//    }
//
//    public void setCreatedBy(Integer createdBy) {
//        this.createdBy = createdBy;
//    }
//
//    public Object getDeletedAt() {
//        return deletedAt;
//    }
//
//    public void setDeletedAt(Object deletedAt) {
//        this.deletedAt = deletedAt;
//    }
//
//    public String getCreatedAt() {
//        return createdAt;
//    }
//
//    public void setCreatedAt(String createdAt) {
//        this.createdAt = createdAt;
//    }
//
//    public String getUpdatedAt() {
//        return updatedAt;
//    }
//
//    public void setUpdatedAt(String updatedAt) {
//        this.updatedAt = updatedAt;
//    }
//
//    public Area getArea() {
//        return area;
//    }
//
//    public void setArea(Area area) {
//        this.area = area;
//    }
//
//    public Category getCategory() {
//        return category;
//    }
//
//    public void setCategory(Category category) {
//        this.category = category;
//    }
//
//    public String getStatus() {
//        return status;
//    }
//
//    public void setStatus(String status) {
//        this.status = status;
//    }
//
//    public String getPrice() {
//        return price;
//    }
//
//    public void setPrice(String price) {
//        this.price = price;
//    }
//
//    public String getCurrency() {
//        return currency;
//    }
//
//    public void setCurrency(String currency) {
//        this.currency = currency;
//    }
//
//    public Integer getBidsCount() {
//        return bidsCount;
//    }
//
//    public void setBidsCount(Integer bidsCount) {
//        this.bidsCount = bidsCount;
//    }
}
