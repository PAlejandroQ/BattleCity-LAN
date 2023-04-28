package org.pc2_BattleCity.client;

import java.io.*;
import java.net.InetAddress;
import java.net.Socket;

public class TCPClient {
    private String serverMessage;
    public String SERVER_IP;
    public static final int SERVER_PORT = 4444;
    private OnMessageReceived messageListener = null;
    private boolean running = false;
    PrintWriter out;
    BufferedReader in;

    public TCPClient(String IP, OnMessageReceived messageListener) {
        this.SERVER_IP = IP;
        this.messageListener = messageListener;
    }

    public void sendMessage(String message) {
        if (out != null && !out.checkError()) {
            out.println(message);
            out.flush(); //Para que el client le pueda enviar el mansaje en la red
        }
    }

    public void partRun() {
        running = true;
        try {
            InetAddress serverAddr = InetAddress.getByName(SERVER_IP);
            System.out.println("TCP Client" + "C: Connecting ...");
            Socket socket = new Socket(serverAddr, SERVER_PORT);
            try {
                out = new PrintWriter(new BufferedWriter(new OutputStreamWriter(socket.getOutputStream())), true);
                System.out.println("TCP Client" + "C: Sent.");
                System.out.println("TCP Client" + "C: Done.");
                in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                while (running) {
                    serverMessage = in.readLine();
                    if (serverMessage != null && messageListener != null) {
                        messageListener.messageReceived(serverMessage);
                    }
                    serverMessage = null;
                }

            } catch (Exception e) {
                System.out.println("TCP" + "S: Error" + e);
            } finally {
                socket.close();
            }
        } catch (Exception e) {
            System.out.println("TCP" + "C: Error " + e);
        }
    }


    public interface OnMessageReceived {
        public void messageReceived(String message);
    }
}
