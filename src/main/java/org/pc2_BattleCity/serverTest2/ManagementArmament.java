package org.pc2_BattleCity.serverTest2;

import org.json.JSONArray;
import org.json.JSONObject;
import org.pc2_BattleCity.Constants;
import org.pc2_BattleCity.client.gui.Draw;


public class ManagementArmament {

    int idAvailable = 0;
    JSONObject stateGame = new JSONObject();


    public ManagementArmament(){
        stateGame.put("mapa",Constants.MAPA_NIVEL_1.getMap());
    }

    public void setNewState(JSONObject newState){
        stateGame = newState;
    }
    public void addObject(JSONObject object,String type) {
        try{
            object.put(Constants.ID_OBJECT_LABEL, idAvailable);
            stateGame.put(type, new JSONObject().put(Integer.toString(idAvailable), object));
            idAvailable++;
        }catch (Exception e){
            System.out.println(e);
        }
    }
//
//    //Update data objet
//    public JSONObject setObject(JSONObject object) {
//        int index = searchObject(object);
//        objects.remove(index);
//        objects.put(object);
//        return objects.getJSONObject(index);
//    }
//
//    public void deleteObject(JSONObject object) {
//        int index = searchObject(object);
//        objects.remove(index);
//    }
//
//    private int searchObject(JSONObject object) {
//        int i;
//        for (i = 0; i < objects.length(); i++) {
//            if (object.get(Constants.ID_OBJECT_LABEL) == (objects.getJSONObject(i)).get(Constants.ID_OBJECT_LABEL)) break;
//        }
//        return i;
//    }
//
//
//    /**
//     * Creador de objetos armamento
//     */
//
//    public static JSONObject createObject(int idOwner,String type,int xPosition,int yPosition,String direction){
//        JSONObject newObj = new JSONObject();
//        newObj.put(Constants.ID_OWNER_LABEL,idOwner);
//        newObj.put(Constants.TYPE_BALA_LABEL,type);
//        newObj.put(Constants.X_POSITION_LABEL,xPosition);
//        newObj.put(Constants.Y_POSITION_LABEL,yPosition);
//        newObj.put(Constants.DIRECTION,direction);
//        return newObj;
//    }




}
