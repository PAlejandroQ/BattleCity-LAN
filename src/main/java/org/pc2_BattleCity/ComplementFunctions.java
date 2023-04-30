package org.pc2_BattleCity;

import org.json.JSONObject;

 public  class ComplementFunctions {
    public static boolean isEqualJSONs(JSONObject json1, JSONObject json2){
        boolean response = true;
        String strJson1 =  json1.toString();
        String strJson2 =  json2.toString();

        if(strJson1.length() != strJson2.length()){
            return false;
        }
        for (int i = 0; i < strJson1.length(); i++) {
            if(strJson1.charAt(i) != strJson2.charAt(i)){
                return false;
            }
        }
        return  response;
    }
}
