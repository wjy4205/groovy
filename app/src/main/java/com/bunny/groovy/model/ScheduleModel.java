package com.bunny.groovy.model;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/****************************************
 * 功能说明:  
 *
 * Author: Created by bayin on 2017/12/28.
 ****************************************/

public class ScheduleModel {
    private Map<String, List<ShowModel>> mMap = new HashMap<>();

    public void setMap(String key,List<ShowModel> value){
        mMap.put(key,value);
    }

    public List<ShowModel> getShowModelList(String key){
        return mMap.get(key);
    }
}
