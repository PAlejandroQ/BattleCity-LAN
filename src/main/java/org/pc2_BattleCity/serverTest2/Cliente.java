package org.pc2_BattleCity.serverTest2;

import org.pc2_BattleCity.client.gui.Juego;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.Scanner;

public class Cliente {


    private int id;//Id que identifica al usuario
    public Juego juego;
    private ObjectOutputStream salida;
    private Scanner scanner;

    public void setJuego(Juego juegoEnlazado) {
        juego = juegoEnlazado;
    }

    public Cliente(Juego juegoEnlazado){
        juego = juegoEnlazado;
    }


    public void iniciar() {
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
                            analizaMensajeRecived(mensaje);
                            juego.addMessageFromServer(mensaje);
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



    private void analizaMensajeRecived(String message){
        if(message.substring(0,11)=="Your id is:") {
            id = Integer.parseInt(message.substring(message.length() - 1));
        }
        else{//Acualizar algunos objetos

        }
    }

    public void enviarMensaje(String mensaje) throws IOException {
        salida.writeObject(mensaje);
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
