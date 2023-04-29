package org.pc2_BattleCity;

import org.pc2_BattleCity.client.gui.Mapa;

import java.util.ArrayList;
import java.util.HashMap;

public class Constants {


    public static final String ID_OWNER_LABEL= "idOwner";
    public static final String ID_OBJECT_LABEL= "idObject";
    public static final String X_POSITION_LABEL= "xPosition";
    public static final String Y_POSITION_LABEL= "yPosition";
    public static final String Y_TYPE_LABEL= "type";
    public static final String DIRECTION = "direction";


    //Tipos de objetos que se usan en la guerra
    public static final  String TYPE_TANQUE_LABEL = "wartank";
    public static final  String TYPE_BALA_LABEL = "bala";

    public static final  String TYPE_BASE_LABEL = "base"; //Consideramos a la base tambien en este grupo

    //Directions
    public static final String LEFT_DIRECTION = "left";
    public static final String RIGHT_DIRECTION= "right";
    public static final String TOP_DIRECTION = "top";
    public static final String BOTTOM_DIRECTION = "bottom";
    public static final String NEUTRAL_DIRECTION = "neutral";

    public static final String[] DIRECTIONS = {LEFT_DIRECTION,RIGHT_DIRECTION,TOP_DIRECTION,BOTTOM_DIRECTION,NEUTRAL_DIRECTION};


    //Datos numericos
    public  static  final int GRIDSIZE = 15;

    //Ubicacion de las bases de acuerdo a la llegada (Se puede agregar mas)
    public static int[][] BASE_UBICATIONS = {{20*GRIDSIZE,0},{40*GRIDSIZE,20*GRIDSIZE},{20*GRIDSIZE,40*GRIDSIZE},{0,20*GRIDSIZE}};

    //Configuracion de los bloques

    public static Mapa MAPA_NIVEL_1 = new Mapa(1);




}
