package org.pc2_BattleCity.client;

import java.util.Scanner;

public class Client {
    TCPClient tcpClient;
    Scanner sc;

    public static void main(String[] args) {
        Client client = new Client();
        client.startLive();
    }

    void startLive() {
        new Thread(
                new Runnable() {
                    @Override
                    public void run() {
                        tcpClient = new TCPClient("localhost", new TCPClient.OnMessageReceived() {
                            @Override
                            public void messageReceived(String message) {
                                clientReceive(message);
                            }
                        });
                        tcpClient.partRun();
                    }
                }
        ).start();
        String quitGame = "ClientSayNoQuitGame";
        sc = new Scanner(System.in);
        System.out.println("Te uniste al juego");

        while (!quitGame.equals("ClientSayYesQuitGame")) {
            quitGame = sc.nextLine();
            clientSend(quitGame);
        }
        System.out.println("You left the game");
    }

    void clientReceive(String message) {
        System.out.println("Recibiendo::" + message);

    }

    void clientSend(String message) {
        if (tcpClient != null) {
            tcpClient.sendMessage(message);
        }
    }

}