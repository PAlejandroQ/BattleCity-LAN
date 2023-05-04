package org.pc2_BattleCity.serverTest2;

import org.json.JSONObject;
import org.pc2_BattleCity.Constants;
import org.pc2_BattleCity.client.gui.Airport;
import org.pc2_BattleCity.client.gui.Juego;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.Scanner;

public class Cliente {


    static private int idClient;//Id que identifica al usuario
    static public Juego juego;
    static public Airport airport;
    static public ObjectOutputStream salida;
    static private Scanner scanner;

//    static public void setJuego(Juego juegoEnlazado) {
//        juego = juegoEnlazado;
//    }

//    public Cliente(Juego juegoEnlazado){
//        juego = juegoEnlazado;
//    }
    private static boolean suscrito = false;


    static public void iniciar() {
        try {
            Socket socket = new Socket("localhost", 5000);
            System.out.println("Conectado al servidor");

            // Obtener el nombre de usuario
            scanner = new Scanner(System.in);
//            System.out.print("Ingrese su nombre de usuario: ");
//            String nombreUsuario = scanner.nextLine();

            // Obtener el canal de salida del socket
            salida = new ObjectOutputStream(socket.getOutputStream());

            // Enviar el nombre de usuario al servidor
//            salida.writeObject(nombreUsuario);

            // Delegar thread de salida
            Thread salidaThread = new Thread(new Runnable() {
                @Override
                public void run() {

                        String mensaje = "";
                        while (true) {
//                             Leer el mensaje a enviar desde la consola
                            System.out.print(">> ");
                            mensaje = scanner.nextLine();
                            sendSimpleMessageToServer(mensaje);
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

                            if(mensaje.substring(0,1).equals("{")){
                                receiveMessage(mensaje);
                            }
//                            juego.addMessageFromServer(mensaje);
                            System.out.println(mensaje);
                        }
                    } catch (IOException | ClassNotFoundException ex) {
                        ex.printStackTrace();
                    }
                }
            });
            entradaThread.start();

        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }




    private static void sendSimpleMessageToServer(String message){
        JSONObject msj = new JSONObject();
        msj.put(Constants.ID_CLIENT_LABEL,idClient);
        msj.put(Constants.TYPE_REQUEST_LABEL,Constants.SIMPLE_MESSAGE_REQUEST);
        msj.put(Constants.PAYLOAD_LABEL,message);
        try{
            salida.writeObject(msj);
        }catch (IOException e){
            System.err.println("Error al enviar mensaje a cliente: " + e.getMessage());
        }
    }

//    static private void receivedMessage(String message){
//
//        System.out.println(message);
//
//    }



    static private void receiveMessage(String message){
        System.out.println("Resibiendo 2:"+message);

        if(!suscrito){
            idClient = new JSONObject(message).getInt(Constants.ID_CLIENT_LABEL);
            airport = new Airport(idClient);
        }

        JSONObject msj = airport.unpackMessageBeforeReceived(message);
        suscrito =true;

        String type = msj.getString(Constants.TYPE_REQUEST_LABEL);
        if(type.equals(Constants.SIMPLE_MESSAGE_REQUEST)){
            System.out.println("El jugador con id "+message);
        }else{
            System.out.println("El jugador con id "+msj.getInt(Constants.ID_CLIENT_LABEL)+" Presiono "+msj.getInt(Constants.PAYLOAD_LABEL));

//            juego.window.actionBeforeKeyPressed(msj.getInt(Constants.PAYLOAD_LABEL));
        }

    }

//    static public void enviarMensaje(String mensaje) throws IOException {
//        salida.writeObject(mensaje);
//    }

    public static void main(String[] args) {
        juego =  new Juego();
//        Cliente.iniciar();
    }



//    public static void main(String[] args) throws IOException {
//        Cliente cliente = new Cliente(new Juego());
//        cliente.iniciar();
//        cliente.enviarMensaje("HolaMetodo");
//    }

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
