package org.pc2_BattleCity.ServerAndClient;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.stream.Collectors;

import org.json.JSONObject;
import org.pc2_BattleCity.Constants;

public class Servidor {
    private final static int PUERTO = 5000;
    private static ArrayList<ConexionCliente> clientesConectados = new ArrayList<>();

    public static void main(String[] args) throws IOException {

        int idAvailable = 0;
        ServerSocket servidor = new ServerSocket(PUERTO);

        System.out.println("Servidor iniciado en el puerto " + PUERTO);


        while (true) {
            idAvailable = idAvailable % 4;
            Socket socketCliente = servidor.accept();
            System.out.println("Nuevo cliente conectado desde " + socketCliente.getInetAddress().getHostAddress());

            while(true){
                int finalIdAvailable = idAvailable;
                if(!clientesConectados.isEmpty() && !(clientesConectados.stream().filter(client -> client.idClient == finalIdAvailable).count() == 0)){
                    System.out.println(idAvailable);
                    idAvailable++;
                }
                else{
                    break;
                }
            }

            System.out.println("OK");
            if(clientesConectados.size()<4) {

                ConexionCliente clienteConectado = new ConexionCliente(socketCliente, idAvailable);
                clientesConectados.add(clienteConectado);

                sendSimpleMessageToOneClient(idAvailable, "TU ID ES:" + idAvailable);

                new Thread(clienteConectado).start();
            }
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
                    sendSimpleMessageToOneClient(idClient,"Bienvenido al chat.");
                    nombreCliente = (String) entrada.readObject();
//
                    while (socketCliente.isConnected()) {
                        String message = (String) entrada.readObject();
                        sendBroadCastMessageKeyPressed(message);
                    }
                    System.out.println("Cliente desconectado");

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
            sendSimpleMessageToOneClient(idClient,"El cliente " + nombreCliente + " se ha desconectado.");
            try {
                socketCliente.close();
            } catch (IOException e) {
                System.err.println("Error al cerrar el socket del cliente: " + e.getMessage());
            }
        }


    }


    private static void sendBroadCastMessageKeyPressed(String message) {


        System.out.println("enviando key to bradcast:"+message);
        //LLega y se distribuye sini analizar

        for (ConexionCliente cliente : clientesConectados) {
            try {
                cliente.salida.writeObject(message);
            } catch (IOException e) {
                System.err.println("Error al enviar mensaje a cliente: " + e.getMessage());
            }
        }
    }
    private static void sendSimpleMessageToOneClient(int idClient,String message){

        JSONObject msj = new JSONObject();
        msj.put(Constants.ID_CLIENT_LABEL,idClient);
        msj.put(Constants.TYPE_REQUEST_LABEL,Constants.SIMPLE_MESSAGE_REQUEST);
        msj.put(Constants.PAYLOAD_LABEL,message);

        System.out.println("enviando simple message:"+msj);
        try{
            //filtra las conexiones para hallar la que tiene el id (las conexiones pueden no estar ordenadas de acuerdo al id, por eso la filtracion)
            ConexionCliente cliente = clientesConectados.stream().filter(client -> client.idClient==idClient).collect(Collectors.toList()).get(0);
            cliente.salida.writeObject(msj.toString());
        }catch (IOException e){
            System.err.println("Error al enviar mensaje a cliente: " + e.getMessage());
        }
    }

}


