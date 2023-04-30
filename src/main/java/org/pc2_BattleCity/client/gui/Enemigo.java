package org.pc2_BattleCity.client.gui;

import java.util.Random;

public class Enemigo {

//    private static final int VELOCIDAD = 2;
//    private static final int INTERVALO_DISPARO_MIN = 30;
//    private static final int INTERVALO_DISPARO_MAX = 60;
//
//    private int intervaloDisparo;
//    private int tiempoDesdeUltimoDisparo;
//
//    public Enemigo(int x, int y, Direccion direccion) {
//        super(x, y, direccion, VELOCIDAD);
//        Random random = new Random();
//        this.intervaloDisparo = random.nextInt(INTERVALO_DISPARO_MAX - INTERVALO_DISPARO_MIN + 1) + INTERVALO_DISPARO_MIN;
//        this.tiempoDesdeUltimoDisparo = 0;
//    }
//
//    public Bala disparar() {
//        if (tiempoDesdeUltimoDisparo < intervaloDisparo) {
//            return null;
//        }
//
//        tiempoDesdeUltimoDisparo = 0;
//        return super.disparar();
//    }
//
//    public void actualizar() {
//        tiempoDesdeUltimoDisparo++;
//        Random random = new Random();
//        int accion = random.nextInt(4);
//
//        switch (accion) {
//            case 0:
//                mover(Direccion.TOP_DIRECTION);
//                break;
//            case 1:
//                mover(Direccion.BOTTOM_DIRECTION);
//                break;
//            case 2:
//                mover(Direccion.LEFT_DIRECTION);
//                break;
//            case 3:
//                mover(Direccion.RIGHT_DIRECTION);
//                break;
//        }
//    }
}
