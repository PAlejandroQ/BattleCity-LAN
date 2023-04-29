package org.pc2_BattleCity.serverTest2;

import org.json.JSONObject;
import org.pc2_BattleCity.Constants;
import org.pc2_BattleCity.client.gui.Draw;
import org.pc2_BattleCity.client.gui.InterfazGrafica;
import org.pc2_BattleCity.client.gui.Juego;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.Scanner;

public class Cliente {


    private static int idClient;//Id que identifica al usuario
    static public InterfazGrafica interfazGrafica;
    static private ObjectOutputStream salida;
    static private Scanner scanner;

//    public void setJuego(Juego juegoEnlazado) {
//        juego = juegoEnlazado;
//    }


    public static ManagementArmament managementArmament = new ManagementArmament();

//    public Cliente(Juego juegoEnlazado) {
//        juego = juegoEnlazado;
//    }
//

    private static boolean elClienteSeIdentifico = false;

    static public void iniciar() {
        try {
            Socket socket = new Socket("localhost", 5000);
            System.out.println("Conectado al servidor");

            // Obtener el nombre de usuario
            scanner = new Scanner(System.in);
            System.out.print("Ingrese su nombre de usuario: ");
            String nombreUsuario = scanner.nextLine();

            // Obtener el canal de salida del socket
            salida = new ObjectOutputStream(socket.getOutputStream());

            // Enviar el nombre de usuario al servidor
            salida.writeObject(nombreUsuario);

            // Delegar thread de salida
            Thread salidaThread = new Thread(new Runnable() {
                @Override
                public void run() {
                    try {
                        String mensaje = "";
                        while (true) {
                            // Leer el mensaje a enviar desde la consola
                            System.out.print(">> ");
                            mensaje = scanner.nextLine();
                            enviarMensaje(mensaje);
                        }
                    } catch (IOException ex) {
                        ex.printStackTrace();
                    }
                }
            });
            salidaThread.start();

            // Delegar thread de entrada
            Thread entradaThread = new Thread(new Runnable() {
                @Override
                public void run() {
                    try {
                        // Obtener el canal de entrada del socket
                        ObjectInputStream entrada = new ObjectInputStream(socket.getInputStream());
                        while (true) {
                            // Leer el mensaje recibido y mostrarlo en pantalla
                            String mensaje = (String) entrada.readObject();
                            analizaMensaje(mensaje);
//                            juego.addMessageFromServer(mensaje);

                        }
                    } catch (IOException | ClassNotFoundException ex) {
                        ex.printStackTrace();
                    }
                }
            });
            entradaThread.start();

            //#Inicando el manejador de cambios de estado
            ThreadRevisaStado revisa = new ThreadRevisaStado();
            revisa.start();


        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }


    private static void analizaMensaje(String message) {
        if (message.substring(0, 1).equals("{")) {
            //Verificamos su primer estado
            if (!elClienteSeIdentifico) {
                idClient = (int) ((JSONObject)new JSONObject(message)).get("id");
                initGame();
            }
            elClienteSeIdentifico = true;
            updateState(message);
            System.out.println(message + "Awui los pibes");

        } else {
            System.out.println("Awui los pibes k");
            System.out.println(message);
        }
    }

    private static void updateState(String stateInString) {
        managementArmament.setNewState((JSONObject) (new JSONObject(stateInString)).get("state"));

    }

    public static void enviarMensaje(String mensaje) throws IOException {
        salida.writeObject(mensaje);
    }

    public static void sendStateToServer(String mensaje) throws IOException {

        salida.writeObject(mensaje);

    }


    //Verifica si cambia el estado
    public static class ThreadRevisaStado extends Thread {
        @Override
        public void run() {
            JSONObject anterior = new JSONObject();
            while (true) {
                try {
                    Thread.sleep(100);
                    if (anterior.toString() == managementArmament.stateGame.toString()) {
                        //Envia nuevo estado al servidor
                        sendStateToServer(managementArmament.stateGame.toString());
                        //Renderisa con el nuevo estado la gui
                        // Pero se podria optimizarce, para que dibuje solo los cambios
                        drawGame();
                        System.out.print("Cambio estado->Renderizando");
                    }
                    anterior = managementArmament.stateGame;
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }

            }
        }
    }


    public static void main(String[] args) throws IOException {

        //Iniacimos conexion con el server
        //Aqui trae la configuracion inicial
        iniciar();

        //Renderizando inicial
//        drawGame();
    }

    private static void initGame() {
//        juego = new Juego(); //Creando el juego
//        juego.setState(managementArmament.stateGame);
        interfazGrafica = new InterfazGrafica(managementArmament.stateGame, idClient);
        drawGame();
    }


    private static void drawGame() {
//        juego.crearTanques(managementArmament.stateGame.getJSONArray(Constants.TANQUES_LABEL));
    }
}


/*
import java.io.*;
import java.net.*;
import java.util.Scanner;

public class Cliente {

    private Juego juego;
    private ObjectOutputStream salida;

    public void setJuego(Juego juegoEnlazado){
        juego = juegoEnlazado;
    }
    public void enviarMensaje(){

    }
    public void iniciar(){
        try {
            Socket socket = new Socket("localhost", 5000);
            System.out.println("Conectado al servidor");

            // Delegar thread de salida
            Thread salida = new Thread(new Runnable() {
                @Override
                public void run() {
                    try {
                        // Obtener el nombre de usuario
                        Scanner scanner = new Scanner(System.in);
                        System.out.print("Ingrese su nombre de usuario: ");
                        String nombreUsuario = scanner.nextLine();

                        // Obtener el canal de salida del socket
                        ObjectOutputStream salida = new ObjectOutputStream(socket.getOutputStream());

                        // Enviar el nombre de usuario al servidor
                        salida.writeObject(nombreUsuario);



                        String mensaje = "";
                        while (true) {
                            // Leer el mensaje a enviar desde la consola
                            System.out.print(">> ");
                            mensaje = scanner.nextLine();
                            salida.writeObject(mensaje);
//                            }
                            // Enviar el mensaje al servidor

                        }
                    } catch (IOException ex) {
                        ex.printStackTrace();
                    }
                }
            });
            salida.start();

            // Delegar thread de entrada
            Thread entrada = new Thread(new Runnable() {
                @Override
                public void run() {
                    try {
                        // Obtener el canal de entrada del socket
                        ObjectInputStream entrada = new ObjectInputStream(socket.getInputStream());

                        while (true) {
                            // Leer el mensaje recibido y mostrarlo en pantalla
                            String mensaje = (String) entrada.readObject();
                            System.out.println(mensaje);
                        }
                    } catch (IOException | ClassNotFoundException ex) {
                        ex.printStackTrace();
                    }
                }
            });
            entrada.start();

        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    public static void main(String[] args) {
        Cliente cliente = new Cliente();
        cliente.iniciar();

    }

}
*/