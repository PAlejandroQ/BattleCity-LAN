package org.pc2_BattleCity.ServerAndClient;

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
    private static boolean suscrito = false;
    static public void iniciar(String ipServer) {

        try {
            Socket socket = new Socket(ipServer, 5000);
            System.out.println("Conectado al servidor");

            // Obtener el nombre de usuario
            scanner = new Scanner(System.in);

            // Obtener el canal de salida del socket
            salida = new ObjectOutputStream(socket.getOutputStream());

            // Delegar thread de salida
            Thread salidaThread = new Thread(new Runnable() {
                @Override
                public void run() {

                    String mensaje = "";
                    while (true) {
//                     Leer el mensaje a enviar desde la consola
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

                            if (mensaje.substring(0, 1).equals("{")) {
                                receiveMessage(mensaje);
                            }
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
            //aq
        }
    }


    private static void sendSimpleMessageToServer(String message) {
        JSONObject msj = new JSONObject();
        msj.put(Constants.ID_CLIENT_LABEL, idClient);
        msj.put(Constants.TYPE_REQUEST_LABEL, Constants.SIMPLE_MESSAGE_REQUEST);
        msj.put(Constants.PAYLOAD_LABEL, message);
        try {
            salida.writeObject(msj);
        } catch (IOException e) {
            System.err.println("Error al enviar mensaje a cliente: " + e.getMessage());
        }
    }



    static private void receiveMessage(String message) {

        if (!suscrito) {
            idClient = new JSONObject(message).getInt(Constants.ID_CLIENT_LABEL);
            airport = new Airport(idClient);
        }
        suscrito = true;
        JSONObject msj = airport.unpackMessageBeforeReceived(message);

        String type = msj.getString(Constants.TYPE_REQUEST_LABEL);
        if (type.equals(Constants.SIMPLE_MESSAGE_REQUEST)) {
            System.out.println("El jugador con id " + message);
        } else {
            System.out.println("El jugador con id " + msj.getInt(Constants.ID_CLIENT_LABEL) + " Presiono " + msj.getInt(Constants.PAYLOAD_LABEL));

            juego.window.actionAfterKeyPressed(msj.getInt(Constants.ID_CLIENT_LABEL), msj.getInt(Constants.PAYLOAD_LABEL));
        }

    }

    public static void main(String[] args) {
        juego = new Juego();
    }

}

