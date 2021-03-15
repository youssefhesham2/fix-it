
package com.youssef.fixit.Models.ShowProfile;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Image {

    @SerializedName("id")
    @Expose
    private Integer id;
    @SerializedName("image")
    @Expose
    private String image;
    @SerializedName("imageable_id")
    @Expose
    private Integer imageableId;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public Integer getImageableId() {
        return imageableId;
    }

    public void setImageableId(Integer imageableId) {
        this.imageableId = imageableId;
    }

}
