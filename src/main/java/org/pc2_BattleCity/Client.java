package org.pc2_BattleCity;



/*
public class Client implements Runnable {
    private Socket socketCliente;
    private ObjectInputStream entrada;
    private ObjectOutputStream salida;
    private BlockingQueue<Mensaje> colaMensajes;

    public Client(Socket socketCliente, BlockingQueue<Mensaje> colaMensajes) throws IOException {
        this.socketCliente = socketCliente;
        this.entrada = new ObjectInputStream(socketCliente.getInputStream());
        this.salida = new ObjectOutputStream(socketCliente.getOutputStream());
        this.colaMensajes = colaMensajes;
    }

    public static void main(String[] args) {
        //Start the gui (When a user want to play a game, this is the beginning)
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                new InterfazGrafica(new Juego());
            }
        });

        //


    }


    public void enviarMensaje(Mensaje mensaje) {
        try {
            salida.writeObject(mensaje);
            salida.reset();
        } catch (IOException e) {
            System.out.println("Error al enviar mensaje al cliente");
        }
    }

    @Override
    public void run() {
        try {
            while (true) {
                // Esperar a recibir un mensaje del cliente
                Mensaje mensaje = (Mensaje) entrada.readObject();
                colaMensajes.put(mensaje);
            }
        } catch (IOException | ClassNotFoundException | InterruptedException e) {
            System.out.println("Error al recibir mensaje del cliente");
        } finally {
            try {
                socketCliente.close();
            } catch (IOException e) {
                System.out.println("Error al cerrar la conexión con el cliente");
            }
        }
    }

    public void enviarMapa(Mapa mapa) {
    }

    public void enviarTanques(ArrayList<Tanque> tanques) {
    }

    public void enviarEnemigos(ArrayList<Enemigo> enemigos) {
    }

    public void enviarBalas(ArrayList<Bala> balas) {
    }


}
*/
