package org.pc2_BattleCity.client.gui;

public class Tanque {
    private int x; // posición x del tanque
    private int y; // posición y del tanque
    private int vidas = 5;
    private Direccion direccion; // dirección del tanque (1=arriba, 2=derecha, 3=abajo, 4=izquierda)
    private boolean vivo; // indica si el tanque está vivo o muerto
    private int velocidad; // velocidad de movimiento del tanque
    private int potenciaDisparo; // potencia de disparo del tanque
    public int id;

    // Constructor
    public Tanque(int x, int y, Direccion direccion, int velocidad, int id) {
        this.x = x;
        this.y = y;
        this.direccion = direccion;
        this.vivo = true;
        this.velocidad = 1;
        this.potenciaDisparo = 1;
        this.id = id;
    }
    public Tanque(int x, int y, Direccion direccion, int velocidad) {
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
        return vidas>0;
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
            case ARRIBA: // arriba
                y -= velocidad;
                break;
            case DERECHA: // derecha
                x += velocidad;
                break;
            case ABAJO: // abajo
                y += velocidad;
                break;
            case IZQUIERDA: // izquierda
                x -= velocidad;
                break;
        }

    }

    // Método para disparar una bala
    public Bala disparar() {
        // crea una nueva bala en la posición del tanque y con la dirección del disparo
        return new Bala(x, y, direccion, potenciaDisparo);
    }

    public void recibirDanio(int potencia) {
        vidas--;
        if(vidas==0){
            return;
        }
    }

    public Direccion getDireccionCanon() {
        return direccion;
    }
}
