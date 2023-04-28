package org.pc2_BattleCity.client.gui;

import java.time.Instant;

public class Bala {
    public Instant tiempoDisparo;
    private int x; // posición x de la bala
    private int y; // posición y de la bala
    private Direccion direccion; // dirección de la bala (1=arriba, 2=derecha, 3=abajo, 4=izquierda)
    private int potencia; // potencia de la bala

    // Constructor
    public Bala(int x, int y, Direccion direccion, int potencia) {
        this.tiempoDisparo= Instant.now();
        this.x = x;
        this.y = y;
        this.direccion = direccion;
        this.potencia = potencia;
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

    public int getPotencia() {
        return potencia;
    }

    public void setPotencia(int potencia) {
        this.potencia = potencia;
    }

    // Método para mover la bala en la dirección actual
    public void mover() {
        switch (direccion) {
            case ARRIBA: // arriba
                y--;
                break;
            case DERECHA: // derecha
                x++;
                break;
            case ABAJO: // abajo
                y++;
                break;
            case IZQUIERDA: // izquierda
                x--;
                break;
        }
        tiempoDisparo = Instant.now();
    }
}
