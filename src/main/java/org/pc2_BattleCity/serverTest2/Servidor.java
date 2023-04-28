package org.pc2_BattleCity.serverTest2;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;

import org.json.JSONObject;
import org.pc2_BattleCity.Constants;

public class
Servidor {
    private final static int PUERTO = 5000;
    private static ManagementArmament currentManagementArmament =  new ManagementArmament();;
    private static ArrayList<ConexionCliente> clientesConectados = new ArrayList<>();




    public static void main(String[] args) throws IOException {

        int idAvailable = 0;
        ServerSocket servidor = new ServerSocket(PUERTO);

        System.out.println("Servidor iniciado en el puerto " + PUERTO);

        while (true) {
            Socket socketCliente = servidor.accept();
            System.out.println("Nuevo cliente conectado desde " + socketCliente.getInetAddress().getHostAddress());
            ConexionCliente clienteConectado = new ConexionCliente(socketCliente);
            clientesConectados.add(clienteConectado);

            /**
             * Agregando los objetos armamento iniciales, la base del jugador tambien se considera armament
             */
            //Agregando tanque
            currentManagementArmament.addObject(ManagementArmament.createObject(idAvailable, Constants.TYPE_TANQUE_LABEL,2*Constants.GRIDSIZE,2*Constants.GRIDSIZE,Constants.TOP_DIRECTION));
            //Agregando Base
            currentManagementArmament.addObject(ManagementArmament.createObject(idAvailable,Constants.TYPE_BASE_LABEL,Constants.BASE_UBICATIONS[idAvailable][0],Constants.BASE_UBICATIONS[idAvailable][1],Constants.DIRECTIONS[idAvailable]));

            //Aqui se agregara la configuracion del los bloques


            //Las balas se agregaran en el transcurso

            new Thread(clienteConectado).start();
            idAvailable++;
        }
    }

    private static class ConexionCliente implements Runnable {
        private final Socket socketCliente;
        private final ObjectOutputStream salida;
        private final ObjectInputStream entrada;
        private String nombreCliente;

        public ConexionCliente(Socket socketCliente) throws IOException {
            this.socketCliente = socketCliente;
            this.salida = new ObjectOutputStream(socketCliente.getOutputStream());
            this.entrada = new ObjectInputStream(socketCliente.getInputStream());
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

        private void enviarMensajes(String mensaje) {
            System.out.println(mensaje);
            for (ConexionCliente cliente : clientesConectados) {
                try {
                    cliente.salida.writeObject(mensaje);
                } catch (IOException e) {
                    System.err.println("Error al enviar mensaje a cliente: " + e.getMessage());
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
