package org.pc2_BattleCity.serverTest2;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;

import org.json.JSONObject;
import org.pc2_BattleCity.ComplementFunctions;
import org.pc2_BattleCity.Constants;
import org.pc2_BattleCity.client.gui.Base;
import org.pc2_BattleCity.client.gui.Direccion;
import org.pc2_BattleCity.client.gui.Mapa;
import org.pc2_BattleCity.client.gui.Tanque;

import javax.swing.*;

public class Servidor {
    private final static int PUERTO = 5000;
    private static ManagementArmament managementArmament = new ManagementArmament();
    ;
    private static ArrayList<ConexionCliente> clientesConectados = new ArrayList<>();

    private static ThreadRevisaStado revisaStado;
    private static JSONObject beforeState = new JSONObject();
    public static void main(String[] args) throws IOException {

        int idAvailable = 0;
        ServerSocket servidor = new ServerSocket(PUERTO);

        System.out.println("Servidor iniciado en el puerto " + PUERTO);

        //Inica el servidor, y tambien inicia el thread que verifica cambios de estado
        revisaStado = new ThreadRevisaStado();
        revisaStado.start();


        while (true) {
            Socket socketCliente = servidor.accept();
            System.out.println("Nuevo cliente conectado desde " + socketCliente.getInetAddress().getHostAddress());
            ConexionCliente clienteConectado = new ConexionCliente(socketCliente, idAvailable);
            //Agregamos al cliente
            clientesConectados.add(clienteConectado);
            //Agregamos armamentos iniciales (Esto incluye la base)
            addClientsArmaments(idAvailable);
            //Enviamos el primer stado al cliente
            // con un objeto json con si correspondinete id y la configuracion incial del mapa
            sendStateToOneClient(idAvailable,clienteConectado);
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

        public ConexionCliente(Socket socketCliente, int idClient) throws IOException {
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
                    sendStateBroadCast();//El primer broadcas para todos
                    while (true) {
                        String mensaje = (String) entrada.readObject();
                        System.out.println("resibiendo del cliente:"+mensaje);
                        analizaMensaje(mensaje);
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

    private static void  analizaMensaje(String message){
        System.out.println("Analizando mensaje");
        if("{".equals(message.substring(0,1))){
            updateState(message);
        }else{
            System.out.println(message);
        }
    }

    private static void updateState(String message){
        System.out.println("Actualizando estado");
        managementArmament.setNewState((JSONObject) new JSONObject(message).get("state"));
        sendStateBroadCast();
    }


    private static void sendStateBroadCast() {
        System.out.println("enviviando breadcast");
        int i = 0;
        for (ConexionCliente cliente : clientesConectados) {
            sendStateToOneClient(i,cliente);
            i++;
        }
    }


    private static void sendStateToOneClient(int idClient,ConexionCliente client) {
        try {
            JSONObject jMessage = new JSONObject();
            jMessage.put("id", idClient);
            jMessage.put("state", managementArmament.stateGame);
            System.out.println("Enviando:"+jMessage.toString());
            client.salida.writeObject(jMessage.toString());
        } catch (IOException e) {
            System.err.println("Error al enviar mensaje a cliente: " + e.getMessage());
        }
    }


    private static void addClientsArmaments(int idAvailable) {
        /**
         * Agregando los objetos armamento iniciales, la base del jugador tambien se considera armament
         */
        //Agregando tanque
        Tanque tanque = new Tanque(-1, idAvailable,2,2, Direccion.RIGHT_DIRECTION, 2);
        JSONObject jTanque = tanque.getTanqueJsonObject();
        managementArmament.addObject(jTanque, Constants.TANQUES_LABEL);

        //Agregando Base
//        currentManagementArmament.addObject(ManagementArmament.createObject(idAvailable,Constants.TYPE_BASE_LABEL,Constants.BASE_UBICATIONS[idAvailable][0],Constants.BASE_UBICATIONS[idAvailable][1],Constants.DIRECTIONS[idAvailable]));

        Base base = new Base(Constants.BASE_UBICATIONS[idAvailable][0], Constants.BASE_UBICATIONS[idAvailable][1]);
        JSONObject jBase = base.getBaseJsonObject();
        jBase.put(Constants.ID_OWNER_LABEL, idAvailable);
        managementArmament.addObject(jBase, Constants.BASES_LABEL);


        //Las balas se agregaran en el transcurso pero desde el cliente

    }

    //Verifica si cambia el estado
    public static class  ThreadRevisaStado extends Thread{
        @Override
        public void run() {
            while (true){
                try {
                    Thread.sleep(100);
                    if(!ComplementFunctions.isEqualJSONs(beforeState,managementArmament.stateGame)){
                        //Envia a los demas BroadCasts
                        System.out.print("Cambio estado:"+managementArmament.getStateGame());
                        sendStateBroadCast();
                    }
                    beforeState = new JSONObject (managementArmament.stateGame.toString());
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }

            }
        }
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
