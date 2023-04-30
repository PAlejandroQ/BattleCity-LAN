package org.pc2_BattleCity.server;

import java.io.*;
import java.net.Socket;

public class TCPServerThread extends Thread {

    private Socket client;
    private TCPServer tcpServer;

    private int clientId;
    private boolean running = false;
    public PrintWriter mOut;
    public BufferedReader in;
    private TCPServer.OnMessageReceived messageListener = null;

    private String message;
    TCPServerThread[] clientsFriends;

    public TCPServerThread(Socket client, TCPServer tcpServer, int clientId, TCPServerThread[] clientsFriends) {
        this.client = client;
        this.tcpServer = tcpServer;
        this.clientId = clientId;
        this.clientsFriends = clientsFriends;
    }

    public void run() {
        running = true;
        try {
            try {

                mOut = new PrintWriter(new BufferedWriter(new OutputStreamWriter(client.getOutputStream())), false);
                System.out.println("TCP Server" + "C: Sent.");
                messageListener = tcpServer.getMessageListener();
                in = new BufferedReader(new InputStreamReader(client.getInputStream()));
                while (running) {
                    message = in.readLine();
                    if (message != null && messageListener != null) {
                        messageListener.messageReceived(message);
                    }
                    message = null;
                }
                System.out.println("RESPONSE FROM CLIENT" + "S: Received message: '" + message + "'");
            } catch (Exception e) {
                System.out.println("TCP Server" + "S:Error" + e);
            } finally {
                client.close();
            }
        } catch (Exception e) {
            System.out.println("TCP Server" + "C: Error" + e);
        }
    }

    public void sendMessage(String message) {
        if (mOut != null && !mOut.checkError()) {
            mOut.println(message);
            mOut.flush();

        }
    }


}
