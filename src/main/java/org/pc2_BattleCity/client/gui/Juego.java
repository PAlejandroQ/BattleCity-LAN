package org.pc2_BattleCity.client.gui;

import org.pc2_BattleCity.ServerAndClient.Cliente;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import java.lang.*;

public class Juego {

    public StartWindow startWindow;
    public InterfazGrafica window;

    private int numJugadores=1;
    public Mapa mapa;
    public List<Tanque> tanques;
    public List<Bala> balas;
    private List<Enemigo> enemigos;

    public Cliente conexionCliente;
    public String nivel;
    private long T_DISPARO;

    Queue<String> colaEntrantes;



    // Constructor
    public Juego() {
        this.startWindow = new StartWindow(this);
    }

    public void StartJuagoAfterSuccessfulConnection(String ipServer,String nivel){
        // Crear el mapa, los tanques, las balas y los enemigos
        this.nivel = nivel;
        this.mapa = new Mapa(Integer.parseInt(nivel));
        this.tanques = new ArrayList<>();
        this.balas = new ArrayList<>();
        this.enemigos = new ArrayList<>();
        this.conexionCliente.iniciar(ipServer);
        colaEntrantes  = new LinkedList<>();
        crearEnemigos();
        this.T_DISPARO = 0;
        this.window = new InterfazGrafica(this);
    }
    // Método para crear los tanques
    public void crearTanque(int id) {
        // Crear los tanques y agregarlos a la lista de tanques
        Tanque t = new Tanque(0,0, Direccion.ARRIBA,1);
        posicionInicial(t, id);
        tanques.add(t);
    }

    public void posicionInicial(Tanque t, int id){
        switch(id){
            case 0:
                t.setX(2); t.setY(2);t.setDireccion(Direccion.DERECHA);
                break;
            case 1:
                t.setX(35); t.setY(2);t.setDireccion(Direccion.ABAJO);
                break;
            case 2:
                t.setX(35); t.setY(35);t.setDireccion(Direccion.IZQUIERDA);
                break;
            case 3:
                t.setX(2); t.setY(35);t.setDireccion(Direccion.ARRIBA);
                break;
        }
    }

    // Método para crear los enemigos
    private void crearEnemigos() {
        // Crear los enemigos y agregarlos a la lista de enemigos
        Enemigo enemigo1 = new Enemigo(5, 5, Direccion.IZQUIERDA);
        Enemigo enemigo2 = new Enemigo(10, 10, Direccion.DERECHA);
        enemigos.add(enemigo1);
        enemigos.add(enemigo2);
    }


    // Método para disparar una bala desde un tanque
    public void disparar(int indiceTanque) {
        // Crear una nueva bala en la posición del tanque y con la dirección del cañón
        long t_actual = System.currentTimeMillis();
        if(t_actual - T_DISPARO<=1000){
            return;
        }

        Tanque tanque = tanques.get(indiceTanque);
        Bala bala;
        if(tanque.getDireccion()==Direccion.ARRIBA){
            bala = new Bala(tanque.getX() + 1, tanque.getY()-1, tanque.getDireccionCanon(), 1);
        }
        else if(tanque.getDireccion()==Direccion.DERECHA){
            bala = new Bala(tanque.getX() + 3, tanque.getY()+1, tanque.getDireccionCanon(), 1);
        }
        else if(tanque.getDireccion()==Direccion.ABAJO){
            bala = new Bala(tanque.getX() + 1, tanque.getY()+3, tanque.getDireccionCanon(), 1);
        }
        else{
            bala = new Bala(tanque.getX()-1, tanque.getY()+1, tanque.getDireccionCanon(), 1);
        }

        balas.add(bala);
        ActualizarBalaThread t = new ActualizarBalaThread(bala);
        T_DISPARO = t_actual;
        t.start();
    }

    class ActualizarBalaThread extends Thread{
        Bala bala;
        ActualizarBalaThread(Bala bala){
            this.bala = bala;
        }
        @Override
        public void run(){
            actualizarBala(bala, this);
        }
    }

    // Método para mover las balas y detectar colisiones
    public void actualizarBala(Bala bala, Thread t) {
        // Mover cada bala y comprobar si choca con un tanque o un enemigo
        boolean hit=false;
        while(!hit){
                window.actualizarBalas();
                //System.out.println("ACTUALIZA BALA: " + bala.getX() + " " + bala.getY());
                // Comprobar si la bala choca con un tanque
                for (Tanque tanque : tanques) {
                    for(int i=1;i<3;++i){
                        for(int j=1;j<3;++j){
                            if (bala.getX() == tanque.getX()+i && bala.getY() == tanque.getY()+j) {
                                // La bala chocó con un tanque
                                tanque.recibirDanio(bala.getPotencia());
                                balas.remove(bala);
                                hit=true;
                                break;
                            }
                        }
                    }
                }

                // Comprobar si la bala choca con un enemigo
                for (Enemigo enemigo : enemigos) {
                    if (bala.getX() == enemigo.getX() && bala.getY() == enemigo.getY()) {
                        // La bala chocó con un enemigo
                        enemigo.recibirDanio(bala.getPotencia());
                        balas.remove(bala);
                        hit=true;
                        break;
                    }
                }

                // Comprobar si la bala choca con un muro destruible
                if (mapa.esMuroDestruible(bala.getX(), bala.getY())) {
                    mapa.destruirMuro(bala.getX(), bala.getY());
                    balas.remove(bala);
                    hit=true;
                    continue;
                }

                // Comprobar si la bala choca con un muro no destruible
                if (mapa.esMuroNoDestruible(bala.getX(), bala.getY())) {
                    balas.remove(bala);
                    hit=true;
                    continue;
                }

                // Comprobar si la bala sale del mapa
                if (bala.getX() < 0 || bala.getX() >= mapa.getAncho() || bala.getY() < 0 || bala.getY() >= mapa.getAlto()) {
                    balas.remove(bala);
                    hit=true;
                    continue;
                }
                bala.mover();

            try{
                synchronized (t){
                    t.wait(10);
                    System.out.println("ESPERA");
                }
            }
            catch(Exception e){
                System.out.println(e);
            }
        }
    }

    public int getNumTanques(){
        return tanques.size();
    }

    public Tanque getTanque(int index){
        return tanques.get(index);
    }


}

