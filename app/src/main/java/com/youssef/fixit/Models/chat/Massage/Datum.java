
package com.youssef.fixit.Models.chat.Massage;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Datum {
    public Datum(Integer id, Integer roomId, Integer createdBy, To to, String message, String createdAt, From from) {
        this.id = id;
        this.roomId = roomId;
        this.createdBy = createdBy;
        this.to = to;
        this.message = message;
        this.createdAt = createdAt;
        this.from = from;
    }

    @SerializedName("id")
    @Expose
    private Integer id;
    @SerializedName("room_id")
    @Expose
    private Integer roomId;
    @SerializedName("created_by")
    @Expose
    private Integer createdBy;
    @SerializedName("to")
    @Expose
    private To to;
    @SerializedName("message")
    @Expose
    private String message;
    @SerializedName("read_at")
    @Expose
    private Object readAt;
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
    @SerializedName("from")
    @Expose
    private From from;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getRoomId() {
        return roomId;
    }

    public void setRoomId(Integer roomId) {
        this.roomId = roomId;
    }

    public Integer getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(Integer createdBy) {
        this.createdBy = createdBy;
    }

    public To getTo() {
        return to;
    }

    public void setTo(To to) {
        this.to = to;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Object getReadAt() {
        return readAt;
    }

    public void setReadAt(Object readAt) {
        this.readAt = readAt;
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

    public From getFrom() {
        return from;
    }

    public void setFrom(From from) {
        this.from = from;
    }

}
