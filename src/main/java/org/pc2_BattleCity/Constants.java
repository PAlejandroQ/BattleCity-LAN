package org.pc2_BattleCity;

import org.pc2_BattleCity.client.gui.Direccion;
import org.pc2_BattleCity.client.gui.Mapa;

import java.security.PublicKey;
import java.util.ArrayList;
import java.util.HashMap;

public class Constants {


    public static final String ID_OWNER_LABEL = "ID_OWNER_LABEL";
    public static final String ID_OBJECT_LABEL = "ID_OBJECT_LABEL";
    public static final String X_POSITION_LABEL = "X_POSITION_LABEL";
    public static final String Y_POSITION_LABEL = "Y_POSITION_LABEL";
    public static final String Y_TYPE_LABEL = "Y_TYPE_LABEL";


    //Tipos de objetos que se usan en la guerra
    public static final String TYPE_TANQUE_LABEL = "TYPE_TANQUE_LABEL";
    public static final String TYPE_BALA_LABEL = "TYPE_BALA_LABEL";
    public static final String DIRECTION_LABEL = "DIRECTION_LABEL";
    public static final String SPEED_LABEL = "SPEED_LABEL";
    public static final String SHOT_POWER = "SHOT_POWER";

    public static final String TYPE_BASE_LABEL = "TYPE_BASE_LABEL"; //Consideramos a la base tambien en este grupo

    //Directions
    public static final String LEFT_DIRECTION = "LEFT_DIRECTION";
    public static final String RIGHT_DIRECTION = "RIGHT_DIRECTION";
    public static final String TOP_DIRECTION = "TOP_DIRECTION";
    public static final String BOTTOM_DIRECTION = "BOTTOM_DIRECTION";
    public static final String NEUTRAL_DIRECTION = "NEUTRAL_DIRECTION";


    public static final String BALAS_LABEL = "BALAS_LABEL";
    public static final String TANQUES_LABEL = "TANQUES_LABEL";

    public static final String BASES_LABEL = "BASES_LABEL";

    public static final String[] DIRECTIONS = {LEFT_DIRECTION, RIGHT_DIRECTION, TOP_DIRECTION, BOTTOM_DIRECTION, NEUTRAL_DIRECTION};

    public static Direccion getDirectionEnum(String labelDirection) {
        switch (labelDirection) {
            case TOP_DIRECTION:
                return Direccion.TOP_DIRECTION;
            case LEFT_DIRECTION:
                return Direccion.LEFT_DIRECTION;
            case BOTTOM_DIRECTION:
                return Direccion.BOTTOM_DIRECTION;
            case RIGHT_DIRECTION:
                return Direccion.RIGHT_DIRECTION;
            default:
                return Direccion.NEUTRAL_DIRECTION;
        }
    }


    //Datos numericos
    public static final int GRIDSIZE = 15;

    //Ubicacion de las bases de acuerdo a la llegada (Se puede agregar mas)
    public static int[][] BASE_UBICATIONS = {{20 * GRIDSIZE, 0}, {40 * GRIDSIZE, 20 * GRIDSIZE}, {20 * GRIDSIZE, 40 * GRIDSIZE}, {0, 20 * GRIDSIZE}};

    //Configuracion de los bloques

    public static Mapa MAPA_NIVEL_1 = new Mapa(1);
    public static String MAP_LABEL = "MAP_LABEL";
    public static String ID_LABEL = "ID_LABEL";

    /**
     * Tipos de solicitud
     */

    public static String  REQUEST_MESSAGE = "REQUEST_MESSAGE";
    public static String REQUEST_UPDATE_STATE = "REQUEST_UPDATE_STATE";

    public static String REQUEST_TYPE_LABEL = "REQUEST_TYPE_LABEL";
    public static String PAYLOAD_LABEL = "PAYLOAD_LABEL";

    public static String ID_CLIENT_LABEL = "ID_CLIENT_LABEL";
    public static  String STATE_LABEL = "STATE_LABEL";

}
