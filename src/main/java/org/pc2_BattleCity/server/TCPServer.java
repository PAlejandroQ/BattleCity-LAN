package org.pc2_BattleCity.server;

import java.io.BufferedReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;

public class TCPServer {

    private String message;

    int numeroClients = 0;
    public static final int SERVER_PORT = 4444;

    private OnMessageReceived messageListener = null;
    private boolean running = false;
    TCPServerThread[] clients = new TCPServerThread[10];
    ServerSocket serverSocket;
    PrintWriter mOut;
    BufferedReader in;

    public TCPServer(OnMessageReceived messageListener) {
        this.messageListener = messageListener;
    }

    public OnMessageReceived getMessageListener() {
        return this.messageListener;
    }


    public void sendMessageTCPServer(String message) {
        for (int i = 0; i < numeroClients; i++) {
            clients[i].sendMessage(message);
            System.out.println("Enviando al jugador " + i);
        }
    }


    public void partRun() {
        running = true;
        try {
            System.out.println("TCP Server" + "S: Connecting...");
            serverSocket = new ServerSocket(SERVER_PORT);
            while (running) {
                Socket client = serverSocket.accept();
                System.out.println("TCP Server" + "S: Receiving...");

                System.out.println("Engendrado " + numeroClients);
                clients[numeroClients] = new TCPServerThread(client, this, numeroClients, clients);
                clients[numeroClients].start();
                numeroClients++;
//                Thread t = new Thread(clients[numeroClients]);
//                t.start();
                System.out.println("Nuevo conectado:" + numeroClients + " jugadores conectados");

            }
        } catch (Exception e) {
            System.out.println("Error:" + e.getMessage());
        }
    }

    public interface OnMessageReceived {
        public void messageReceived(String message);
    }
}
