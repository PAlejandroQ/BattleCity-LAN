package org.pc2_BattleCity.serverTest2;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;

import org.json.JSONObject;
import org.pc2_BattleCity.Constants;
import org.pc2_BattleCity.client.gui.Mapa;

import javax.swing.*;

public class Servidor {
    private final static int PUERTO = 5000;
    private static ManagementArmament currentManagementArmament =  new ManagementArmament();;
    private static ArrayList<ConexionCliente> clientesConectados = new ArrayList<>();

    private static int[][] mapaNivel1 = Constants.MAPA_NIVEL_1.getMap();




    public static void main(String[] args) throws IOException {

        int idAvailable = 0;
        ServerSocket servidor = new ServerSocket(PUERTO);

        System.out.println("Servidor iniciado en el puerto " + PUERTO);

        addMap();

        while (true) {
            Socket socketCliente = servidor.accept();
            System.out.println("Nuevo cliente conectado desde " + socketCliente.getInetAddress().getHostAddress());
            ConexionCliente clienteConectado = new ConexionCliente(socketCliente,idAvailable);
            clientesConectados.add(clienteConectado);

            //Agregamos al cliente
            addClientsArmaments(idAvailable);
            //Enviamos al cliente su id correspondiente
            //Para que el cliente pueda actualizar el estado de sus objetos propios

            sendMessageToOneClient("Your id is:"+idAvailable,idAvailable);

            new Thread(clienteConectado).start();

            idAvailable++;
        }
    }

    private static class ConexionCliente implements Runnable {
        private final Socket socketCliente;
        private final ObjectOutputStream salida;
        private final ObjectInputStream entrada;
        private int idClient;
        private String nombreCliente;

        public ConexionCliente(Socket socketCliente,int idClient) throws IOException {
            this.socketCliente = socketCliente;
            this.salida = new ObjectOutputStream(socketCliente.getOutputStream());
            this.entrada = new ObjectInputStream(socketCliente.getInputStream());
            this.idClient = idClient;
        }

        @Override
        public void run() {

            Thread broadcastCliente = new Thread(() -> {
                try {
                    enviarMensajes("Bienvenido al chat.");
                    nombreCliente = (String) entrada.readObject();
                    enviarMensajes("El cliente " + nombreCliente + " se ha conectado.");

                    while (true) {
                        String mensaje = (String) entrada.readObject();
                        enviarMensajes(nombreCliente + ": " + mensaje);
                    }
                } catch (IOException | ClassNotFoundException e) {
                    System.err.println("Error al leer el mensaje del cliente: " + e.getMessage());
                } finally {
                    desconectarCliente();
                }

            });
            broadcastCliente.start();
        }

        private void desconectarCliente() {
            clientesConectados.remove(this);
            enviarMensajes("El cliente " + nombreCliente + " se ha desconectado.");
            try {
                socketCliente.close();
            } catch (IOException e) {
                System.err.println("Error al cerrar el socket del cliente: " + e.getMessage());
            }
        }


    }


    private static void enviarMensajes(String mensaje) {
        System.out.println(mensaje);
        for (ConexionCliente cliente : clientesConectados) {
            try {
                cliente.salida.writeObject(mensaje);
            } catch (IOException e) {
                System.err.println("Error al enviar mensaje a cliente: " + e.getMessage());
            }
        }
    }
    private static void sendMessageToOneClient(String message,int idClient){
        try{
            clientesConectados.get(idClient).salida.writeObject(message);
        }catch (IOException e){
            System.err.println("Error al enviar mensaje a cliente: " + e.getMessage());
        }
    }

    private static void addMap(){
        //Aqui se agregara la configuracion del los bloques (mapa)
        //Cada casilla sera como un objeto que se hara un seguimiento
        //idOwner sera -1, dado que no le pernenece a ningun juagador
        for (int i = 0; i < mapaNivel1.length; i++) {
            for (int j = 0; j < mapaNivel1[i].length; j++) {
                currentManagementArmament.addObject(ManagementArmament.createObject(-1,Integer.toString(mapaNivel1[i][j]),i,j,Constants.NEUTRAL_DIRECTION));
            }
        }
    }

    private static void addClientsArmaments(int idAvailable){
        /**
         * Agregando los objetos armamento iniciales, la base del jugador tambien se considera armament
         */
        //Agregando tanque
        currentManagementArmament.addObject(ManagementArmament.createObject(idAvailable, Constants.TYPE_TANQUE_LABEL,2*Constants.GRIDSIZE,2*Constants.GRIDSIZE,Constants.TOP_DIRECTION));
        //Agregando Base
        currentManagementArmament.addObject(ManagementArmament.createObject(idAvailable,Constants.TYPE_BASE_LABEL,Constants.BASE_UBICATIONS[idAvailable][0],Constants.BASE_UBICATIONS[idAvailable][1],Constants.DIRECTIONS[idAvailable]));

        //Las balas se agregaran en el transcurso

    }
}


/*public class Servidor {
    private static final int PUERTO = 5000;
    private static final List<ConexionCliente> clientesConectados = new ArrayList<>();

    public static void main(String[] args) {
        try (ServerSocket serverSocket = new ServerSocket(PUERTO)) {
            System.out.println("Servidor iniciado en puerto " + PUERTO);

            while (true) {
                Socket socketCliente = serverSocket.accept();
                ConexionCliente conexion = new ConexionCliente(socketCliente, clientesConectados);
                clientesConectados.add(conexion);
                conexion.start();
            }
        } catch (IOException e) {
            System.out.println("Error en el servidor: " + e.getMessage());
        }
    }
}*/
