
package com.youssef.fixit.Models.Jobs;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Contract {

    @SerializedName("project_id")
    @Expose
    private Integer projectId;
    @SerializedName("id")
    @Expose
    private Integer id;
    @SerializedName("status")
    @Expose
    private String status;

    public Integer getProjectId() {
        return projectId;
    }

    public void setProjectId(Integer projectId) {
        this.projectId = projectId;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

}
