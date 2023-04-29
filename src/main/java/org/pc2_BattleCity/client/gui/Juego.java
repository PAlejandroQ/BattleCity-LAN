package org.pc2_BattleCity.client.gui;

import org.json.CDL;
import org.json.JSONArray;
import org.json.JSONObject;
import org.pc2_BattleCity.Constants;
import org.pc2_BattleCity.client.gui.*;
import org.pc2_BattleCity.serverTest2.Cliente;
import org.pc2_BattleCity.serverTest2.ManagementArmament;

import java.util.*;

public class Juego {

    InterfazGrafica window;
    private int numJugadores = 1;
    public Mapa mapa;

    private List<Tanque> tanques;
    public List<Bala> balas;
    private List<Enemigo> enemigos;

//    public Cliente conexionCliente;
    private JSONObject state;

    Queue<String> colaEntrantes;

    // Constructor
    public Juego() {
        // Crear el mapa, los tanques, las balas y los enemigos
        this.mapa = new Mapa(1);
        this.tanques = new ArrayList<>();
        this.balas = new ArrayList<>();
        this.enemigos = new ArrayList<>();
//        crearTanques();
//        crearEnemigos();
//        this.conexionCliente = new Cliente(this);
//        this.window = new InterfazGrafica(this);
//        this.conexionCliente.iniciar();
//        stado = this.conexionCliente.managementArmament.getStateGame();
        colaEntrantes = new LinkedList<>();
    }

    public void setState(JSONObject state){
        this.state = state;
    }

    public void addMessageFromServer(String datoDeServer) {
        this.colaEntrantes.add(datoDeServer);
    }

    // Método para crear los tanques
    public void crearTanques(JSONArray jTanks) {
        // Crear los tanques y agregarlos a la lista de tanques
        /*Tanque tanque1 = new Tanque(0, 0, Direccion.ARRIBA, 1);
        Tanque tanque2 = new Tanque(mapa.getAncho() - 1, mapa.getAlto() - 1, Direccion.ABAJO, 1);
        tanques.add(tanque1);
        tanques.add(tanque2);*/


        //Extrayendo los tanques del estado
System.out.println(jTanks+"TANQUES");
        for (Object tankAux : jTanks) {
            JSONObject tank = new JSONObject(tankAux);
            Tanque t = new Tanque(
                    Integer.parseInt(tank.getString(Constants.X_POSITION_LABEL)),
                    Integer.parseInt(tank.getString(Constants.Y_POSITION_LABEL)),
                    Constants.getDirectionEnum((String) tank.getString(Constants.DIRECTION_LABEL)),
                    Integer.parseInt(tank.getString(Constants.SPEED_LABEL)));
            tanques.add(t);
        }
//        for(int i=0; i<jTanques.length(); ++i){
//            Tanque t = new Tanque(2,2,Direccion.TOP_DIRECTION, 1);
//            tanques.add(t);
//        }
    }

    // Método para crear los enemigos
    private void crearEnemigos() {
        // Crear los enemigos y agregarlos a la lista de enemigos
        Enemigo enemigo1 = new Enemigo(5, 5, Direccion.LEFT_DIRECTION);
        Enemigo enemigo2 = new Enemigo(10, 10, Direccion.RIGHT_DIRECTION);
        enemigos.add(enemigo1);
        enemigos.add(enemigo2);
    }

    // Método para mover los tanques
    public void moverTanques(int indiceTanque, Direccion direccion) {
        // Mover el tanque con el índice dado en la dirección dada
        tanques.get(indiceTanque).mover(direccion);
    }

    // Método para disparar una bala desde un tanque
    public void disparar(int indiceTanque) {
        // Crear una nueva bala en la posición del tanque y con la dirección del cañón
        Tanque tanque = tanques.get(indiceTanque);
        Bala bala;
        if (tanque.getDireccion() == Direccion.TOP_DIRECTION) {
            bala = new Bala(tanque.getX() + 1, tanque.getY(), tanque.getDireccionCanon(), 1);
        } else if (tanque.getDireccion() == Direccion.RIGHT_DIRECTION) {
            bala = new Bala(tanque.getX() + 3, tanque.getY() + 1, tanque.getDireccionCanon(), 1);
        } else if (tanque.getDireccion() == Direccion.BOTTOM_DIRECTION) {
            bala = new Bala(tanque.getX() + 1, tanque.getY() + 3, tanque.getDireccionCanon(), 1);
        } else {
            bala = new Bala(tanque.getX(), tanque.getY() + 1, tanque.getDireccionCanon(), 1);
        }

        balas.add(bala);
        ActualizarBalaThread t = new ActualizarBalaThread(bala);
        t.start();
    }

    class ActualizarBalaThread extends Thread {
        Bala bala;

        ActualizarBalaThread(Bala bala) {
            this.bala = bala;
        }

        @Override
        public void run() {
            actualizarBala(bala, this);
        }
    }

    // Método para mover las balas y detectar colisiones
    // Método para mover las balas y detectar colisiones
    public void actualizarBala(Bala bala, Thread t) {
        // Mover cada bala y comprobar si choca con un tanque o un enemigo
        boolean hit = false;
        while (!hit) {
            bala.mover();
//            window.actualizarBalas();
            //System.out.println("ACTUALIZA BALA: " + bala.getX() + " " + bala.getY());
            // Comprobar si la bala choca con un tanque
            for (Tanque tanque : tanques) {
                if (bala.getX() == tanque.getX() && bala.getY() == tanque.getY()) {
                    // La bala chocó con un tanque
                    tanque.recibirDanio(bala.getPotencia());
                    balas.remove(bala);
                    hit = true;
                    break;
                }
            }

            // Comprobar si la bala choca con un enemigo
            for (Enemigo enemigo : enemigos) {
                if (bala.getX() == enemigo.getX() && bala.getY() == enemigo.getY()) {
                    // La bala chocó con un enemigo
                    enemigo.recibirDanio(bala.getPotencia());
                    balas.remove(bala);
                    hit = true;
                    break;
                }
            }

            // Comprobar si la bala choca con un muro destruible
            if (mapa.esMuroDestruible(bala.getX(), bala.getY())) {
                mapa.destruirMuro(bala.getX(), bala.getY());
                balas.remove(bala);
                hit = true;
                continue;
            }

            // Comprobar si la bala choca con un muro no destruible
            if (mapa.esMuroNoDestruible(bala.getX(), bala.getY())) {
                balas.remove(bala);
                hit = true;
                continue;
            }

            // Comprobar si la bala sale del mapa
            if (bala.getX() < 0 || bala.getX() >= mapa.getAncho() || bala.getY() < 0 || bala.getY() >= mapa.getAlto()) {
                balas.remove(bala);
                hit = true;
                continue;
            }

            try {
                synchronized (t) {
                    t.wait(10);
                    System.out.println("ESPERA");
                }
            } catch (Exception e) {
                System.out.println(e);
            }
        }
    }

    public int getNumTanques() {
        return tanques.size();
    }

    public Tanque getTanque(int index) {
        return tanques.get(index);
    }


//    public static void main(String[] args) {
//        new Juego();
//    }
}

