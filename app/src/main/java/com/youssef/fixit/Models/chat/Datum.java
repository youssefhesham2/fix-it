
package com.youssef.fixit.Models.chat;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class Datum {

    @SerializedName("id")
    @Expose
    private Integer id;
//    @SerializedName("created_by")
//    @Expose
//    private Integer createdBy;
    @SerializedName("user_id")
    @Expose
    private Integer userId;
    @SerializedName("deleted_by")
    @Expose
    private Integer deletedBy;
    @SerializedName("deleted_at")
    @Expose
    private Object deletedAt;
    @SerializedName("created_at")
    @Expose
    private String createdAt;
    @SerializedName("updated_at")
    @Expose
    private String updatedAt;
    @SerializedName("users")
    @Expose
    private Users users;
    @SerializedName("created_by")
    @Expose
    private CreatedBy createdBy_;
    @SerializedName("messages")
    @Expose
    private List<Message> messages;

    public List<Message> getMessages() {
        return messages;
    }

    public void setMessages(List<Message> messages) {
        this.messages = messages;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

//    public Integer getCreatedBy() {
//        return createdBy;
//    }

//    public void setCreatedBy(Integer createdBy) {
//        this.createdBy = createdBy;
//    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public Integer getDeletedBy() {
        return deletedBy;
    }

    public void setDeletedBy(Integer deletedBy) {
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

    public Users getUsers() {
        return users;
    }

    public void setUsers(Users users) {
        this.users = users;
    }

    public CreatedBy getCreatedBy_() {
        return createdBy_;
    }
}
