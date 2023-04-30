package org.pc2_BattleCity.serverTest2;

import org.json.JSONObject;
import org.pc2_BattleCity.ComplementFunctions;
import org.pc2_BattleCity.Constants;
import org.pc2_BattleCity.client.gui.Draw;
import org.pc2_BattleCity.client.gui.InterfazGrafica;
import org.pc2_BattleCity.client.gui.Juego;

import javax.management.JMException;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.Scanner;

public class Cliente {

    static  private int idClient;//Id que identifica al usuario
    static public InterfazGrafica interfazGrafica;
    static private ObjectOutputStream salida;
    static private Scanner scanner;

//    public void setJuego(Juego juegoEnlazado) {
//        juego = juegoEnlazado;
//    }

    public static JSONObject stadoAnterior = new JSONObject();
//    public static ManagementArmament managementArmament = new ManagementArmament();

//    public Cliente(Juego juegoEnlazado) {
//        juego = juegoEnlazado;
//    }
//

    private static boolean suscrito = false;

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

            //
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
                            handleMessage(mensaje);
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


    private static void handleMessage(String message) {

        System.out.println("Resibiendo del server:"+message);

        JSONObject jMessage = new JSONObject(message);


        if(jMessage.get(Constants.REQUEST_TYPE_LABEL).equals(Constants.REQUEST_UPDATE_STATE)){
            System.out.println("El servidos envio nuevo estado");
            updateState(jMessage.getJSONObject(Constants.PAYLOAD_LABEL));
        }

        if(jMessage.get(Constants.REQUEST_TYPE_LABEL).equals(Constants.REQUEST_MESSAGE)){
            System.out.println("El servidor envio mesaje simple:"+jMessage);
        }



//        if (message.substring(0, 1).equals("{")) {
//            System.out.println("Llega aqui:" +message);
//            //Verificamos su primer estado
//            if (!elClienteSeIdentifico) {
//                System.out.println("Primera ves");
//
//                JSONObject msg = new JSONObject(message);
//
//                idClient = msg.getInt("id");
//                managementArmament.setNewState((JSONObject) (msg).get("state"));
//                initGame();
//            }
//            elClienteSeIdentifico = true;
//            updateState(message);
//            System.out.println(message + "Awui los pibes");
//
//        } else {
//            System.out.println("Awui los pibes k");
//            System.out.println(message);
//        }
    }

    private static void updateState(JSONObject state) {
        //Se ejecuta a la primera ves que llega un cambio de estado (Par inicial el juego)
        if(!suscrito){
            idClient = state.getInt(Constants.ID_CLIENT_LABEL);
            initGame(state.getJSONObject(Constants.STATE_LABEL));
            System.out.println("Iniciaste el juego, su id es:"+idClient);
        }
        suscrito =true;
        interfazGrafica.setState(state.getJSONObject(Constants.STATE_LABEL));
    }

    public static void enviarMensaje(String message) throws IOException {
        JSONObject jMessage = new JSONObject();
        jMessage.put(Constants.REQUEST_TYPE_LABEL,Constants.REQUEST_MESSAGE);
        jMessage.put(Constants.PAYLOAD_LABEL,message);
        salida.writeObject(jMessage.toString());
    }

    public static void sendStateToServer(JSONObject state) throws IOException {

        JSONObject jMessage = new JSONObject();

        jMessage.put(Constants.REQUEST_TYPE_LABEL,Constants.REQUEST_UPDATE_STATE);

        JSONObject payload = new JSONObject();
        payload.put(Constants.ID_CLIENT_LABEL,idClient);
        payload.put(Constants.STATE_LABEL,state);
        jMessage.put(Constants.PAYLOAD_LABEL,payload);

        salida.writeObject(jMessage.toString());

    }



    private static void initGame(JSONObject state) {
        System.out.println("Iniciando juego");
        interfazGrafica = new InterfazGrafica(state, idClient);
    }

//    private static void drawGame() {
////        juego.crearTanques(managementArmament.stateGame.getJSONArray(Constants.TANQUES_LABEL));
//          interfazGrafica.gameBoardCanvas.repaint();
//    }


    public static void main(String[] args) throws IOException {
        iniciar();//Inicia
    }
}

