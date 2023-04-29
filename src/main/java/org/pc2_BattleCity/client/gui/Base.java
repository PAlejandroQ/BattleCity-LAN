package org.pc2_BattleCity.client.gui;

import org.json.JSONObject;
import org.pc2_BattleCity.Constants;

public class Base {
    int x;
    int y;
    public Base(int x, int y){
        x = x;
                y =y;
    }
    public JSONObject getBaseJsonObject(){
        JSONObject jBase = new JSONObject();
        jBase.put(Constants.X_POSITION_LABEL,x);
        jBase.put(Constants.Y_POSITION_LABEL,y);
        return  jBase;
    }
}
