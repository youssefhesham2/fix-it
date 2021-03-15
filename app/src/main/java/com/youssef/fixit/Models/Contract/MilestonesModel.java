package com.youssef.fixit.Models.Contract;

import java.util.HashMap;

public class MilestonesModel {
    HashMap<String,Object> hashMap;

    public MilestonesModel(HashMap<String, Object> hashMap) {
        this.hashMap = hashMap;
    }

    public HashMap<String, Object> getHashMap() {
        return hashMap;
    }

    public void setHashMap(HashMap<String, Object> hashMap) {
        this.hashMap = hashMap;
    }
}
