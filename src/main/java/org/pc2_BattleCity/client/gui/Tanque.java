package org.pc2_BattleCity.client.gui;

import org.json.JSONObject;
import org.pc2_BattleCity.Constants;

import javax.swing.*;

public class Tanque {
    private int x; // posición x del tanque
    private int y; // posición y del tanque
    private Direccion direccion; // dirección del tanque (1=arriba, 2=derecha, 3=abajo, 4=izquierda)
    private boolean vivo; // indica si el tanque está vivo o muerto
    private int velocidad; // velocidad de movimiento del tanque
    private int potenciaDisparo; // potencia de disparo del tanque
    private int idObject;
    private  int idOwner;

    // Constructor
    public Tanque(int idObject,int idOwner,int x, int y, Direccion direccion, int velocidad) {
        this.idObject = idObject;
        this.idOwner = idOwner;
        this.x = x;
        this.y = y;
        this.direccion = direccion;
        this.vivo = true;
        this.velocidad = 1;
        this.potenciaDisparo = 1;
    }

    // Métodos getter y setter
    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }

    public Direccion getDireccion() {
        return direccion;
    }

    public void setDireccion(Direccion direccion) {
        this.direccion = direccion;
    }

    public boolean isVivo() {
        return vivo;
    }

    public void setVivo(boolean vivo) {
        this.vivo = vivo;
    }

    public int getVelocidad() {
        return velocidad;
    }

    public void setVelocidad(int velocidad) {
        this.velocidad = velocidad;
    }

    public int getPotenciaDisparo() {
        return potenciaDisparo;
    }

    public void setPotenciaDisparo(int potenciaDisparo) {
        this.potenciaDisparo = potenciaDisparo;
    }

    // Método para mover el tanque en la dirección actual
    public void mover(Direccion direccion) {
        this.direccion = direccion;
        switch (direccion) {
            case TOP_DIRECTION: // arriba
                y -= velocidad;
                break;
            case RIGHT_DIRECTION: // derecha
                x += velocidad;
                break;
            case BOTTOM_DIRECTION: // abajo
                y += velocidad;
                break;
            case LEFT_DIRECTION: // izquierda
                x -= velocidad;
                break;
            default:
                System.out.println("No direction");
        }

    }

    // Método para disparar una bala
    public Bala disparar() {
        // crea una nueva bala en la posición del tanque y con la dirección del disparo
        return new Bala(x, y, direccion, potenciaDisparo);
    }

    public void recibirDanio(int potencia) {
    }

    public Direccion getDireccionCanon() {
        return direccion;
    }

    public JSONObject getTanqueJsonObject(){
        JSONObject jTanque = new JSONObject();
        jTanque.put(Constants.X_POSITION_LABEL,x);
        jTanque.put(Constants.Y_POSITION_LABEL,y);
        jTanque.put(Constants.DIRECTION_LABEL,direccion);
        jTanque.put(Constants.SPEED_LABEL,velocidad);
        jTanque.put(Constants.SHOT_POWER,potenciaDisparo);
        jTanque.put(Constants.ID_OWNER_LABEL,idOwner);
        jTanque.put(Constants.ID_OBJECT_LABEL,idObject);

        return jTanque;
    }


}
