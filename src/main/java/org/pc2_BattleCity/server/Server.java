package org.pc2_BattleCity.server;

import java.util.Scanner;

public class Server {

    TCPServer tcpServer;
    Scanner sc;

    public static void main(String[] args) {
        Server server = new Server();
        server.startLive();
    }

    void startLive() {
        new Thread(
                new Runnable() {
                    @Override
                    public void run() {
                        tcpServer = new TCPServer(
                                new TCPServer.OnMessageReceived() {
                                    @Override
                                    public void messageReceived(String message) {
                                        serverReceive(message);
                                    }
                                }
                        );
                        tcpServer.partRun();
                    }
                }
        ).start();

        String quitGame = "serverSayNoQuitGame";
        sc = new Scanner(System.in);
        System.out.println("El servidor comenzo el juego");
        while (!quitGame.equals("serverSayYesQuitGame")) {
            quitGame = sc.nextLine();
            serverSend(quitGame);
        }
        System.out.println("Servidor termino el juego");
    }


    void serverReceive(String message) {
        if (message == "quitGame") {
            //Close the thread i of connection client server
            serverSend("Un jugador dejo el juego");
        }
        serverSend("update coordenates");
        System.out.println("client El mensaje:" + message);
    }

    void serverSend(String message) {
        if (tcpServer != null) {
            tcpServer.sendMessageTCPServer(message);
        }
    }

}

/*
public class s {
    private ServerSocket servidorSocket;
    private ArrayList<Client> clientesConectados;
    private Mapa mapa;
    private ArrayList<Tanque> tanques;
    private ArrayList<Bala> balas;
    private ArrayList<Enemigo> enemigos;
    private int puerto;

    public s(int puerto) {
        this.puerto = puerto;
        this.clientesConectados = new ArrayList<>();
        //this.mapa = new Mapa(new int[100][100]);
        this.tanques = new ArrayList<>();
        this.balas = new ArrayList<>();
        this.enemigos = new ArrayList<>();
    }

    public void iniciarServidor() {
        try {
            servidorSocket = new ServerSocket(puerto);
            System.out.println("Servidor iniciado en el puerto: " + puerto);

            while (true) {
                Socket socketCliente = servidorSocket.accept();
                BlockingQueue<Mensaje> colaMensajes = new LinkedBlockingQueue<>();
                Client cliente = new Client(socketCliente, colaMensajes );
                clientesConectados.add(cliente);
                System.out.println("Nuevo cliente conectado desde la dirección: " + socketCliente.getInetAddress());

                // Enviamos el mapa y los objetos iniciales al cliente
                cliente.enviarMapa(mapa);
                cliente.enviarTanques(tanques);
                cliente.enviarEnemigos(enemigos);
            }
        } catch (IOException e) {
            System.out.println("Error al iniciar el servidor: " + e.getMessage());
        }
    }

    public void recibirMovimiento(Tanque tanque) {
        // Actualizamos la posición del tanque
        for (Tanque t : tanques) {
            if (t.equals(tanque)) {
                t.setX(tanque.getX());
                t.setY(tanque.getY());
                break;
            }
        }

        // Enviamos la actualización a todos los clientes
        for (Client cliente : clientesConectados) {
            cliente.enviarTanques(tanques);
        }
    }

    public void recibirDisparo(Bala bala) {
        balas.add(bala);

        // Enviamos la actualización a todos los clientes
        for (Client cliente : clientesConectados) {
            cliente.enviarBalas(balas);
        }
    }

    public void actualizar() {
        // Actualizamos la lógica del juego (movimiento de enemigos, colisiones, etc.)
        // ...

        // Enviamos la actualización a todos los clientes
        for (Client cliente : clientesConectados) {
            cliente.enviarTanques(tanques);
            cliente.enviarBalas(balas);
            cliente.enviarEnemigos(enemigos);
        }
    }

//    public static void main(String[] args) {
//        s servidor = new s(5000);
//        servidor.iniciarServidor();
//    }
}


 */
