
package com.youssef.fixit.Models.Category;

import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Errors {

    @SerializedName("parent_id")
    @Expose
    private List<String> parentId = null;

    public List<String> getParentId() {
        return parentId;
    }

    public void setParentId(List<String> parentId) {
        this.parentId = parentId;
    }

}
