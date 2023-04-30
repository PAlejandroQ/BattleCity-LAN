package org.pc2_BattleCity.serverTest2;

import org.json.JSONArray;
import org.json.JSONObject;
import org.pc2_BattleCity.ComplementFunctions;
import org.pc2_BattleCity.Constants;
import org.pc2_BattleCity.client.gui.Draw;

import java.util.concurrent.ExecutionException;


public class ManagementArmament {

    int idAvailable = 0;
    JSONObject stateGame = new JSONObject();


    public ManagementArmament() {
        stateGame.put("mapa", Constants.MAPA_NIVEL_1.getMap());
        stateGame.put(Constants.BASES_LABEL, new JSONArray());
        stateGame.put(Constants.BALAS_LABEL, new JSONArray());
        stateGame.put(Constants.TANQUES_LABEL, new JSONArray());
    }

    public JSONObject getStateGame() {
        return stateGame;
    }

    public synchronized void setNewState(JSONObject newState) {
        stateGame = newState;
    }

    public synchronized void addObject(JSONObject object, String type) {
        try {
            object.put(Constants.ID_OBJECT_LABEL, idAvailable);
            JSONArray currArr = stateGame.getJSONArray(type);
            currArr.put(object);
            stateGame.put(type, currArr);
            idAvailable++;
        } catch (Exception e) {
            System.out.println(e);
        }
    }

    //Update data objet
    public void setObject(JSONObject object, String type) {

        try {
            int index = searchObject(object, type);
            JSONArray arr = stateGame.getJSONArray(type);
            arr.put(index, object);
            stateGame.put(type, arr);
        } catch (Exception e) {
            System.out.println(e + "Estoy aqui");
        }

    }

    public void deleteObject(JSONObject object, String type) {
        try {
            int index = searchObject(object, type);
            JSONArray arr = stateGame.getJSONArray(type);
            arr.remove(index);
            stateGame.put(type, arr);
        } catch (Exception e) {
            System.out.println(e);
        }
    }

    private int searchObject(JSONObject object, String type) {

        int index = 0;
        for (Object objAux : stateGame.getJSONArray(type)
        ) {
            JSONObject obj = new JSONObject((objAux).toString());
            if (obj.get(Constants.ID_OBJECT_LABEL).equals(object.get(Constants.ID_OBJECT_LABEL))) {
                break;
            }
            index++;
        }
        return index;
    }
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
