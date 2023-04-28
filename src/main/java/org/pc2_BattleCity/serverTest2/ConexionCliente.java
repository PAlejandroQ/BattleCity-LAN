package org.pc2_BattleCity.serverTest2;

/*import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.List;

public class ConexionCliente extends Thread {
    private final Socket socket;
    private final List<ConexionCliente> clientesConectados;
    private String nombre;

    public ConexionCliente(Socket socket, List<ConexionCliente> clientesConectados) {
        this.socket = socket;
        this.clientesConectados = clientesConectados;
    }

    @Override
    public void run() {
        try {
            ObjectInputStream entrada = new ObjectInputStream(socket.getInputStream());
            ObjectOutputStream salida = new ObjectOutputStream(socket.getOutputStream());

            // Pedir al cliente que ingrese su nombre
            salida.writeObject("Ingrese su nombre:");
            nombre = (String) entrada.readObject();
            System.out.println(nombre + " se ha conectado.");

            // Notificar a los demás clientes de la nueva conexión
            for (ConexionCliente cliente : clientesConectados) {
                if (!cliente.equals(this)) {
                    cliente.enviarMensaje(nombre + " se ha conectado.");
                }
            }

            while (true) {
                String mensaje = (String) entrada.readObject();

                // Notificar a los demás clientes del mensaje recibido
                for (ConexionCliente cliente : clientesConectados) {
                    if (!cliente.equals(this)) {
                        cliente.enviarMensaje(nombre + ": " + mensaje);
                    }
                }
            }
        } catch (IOException | ClassNotFoundException e) {
            System.out.println("Error en la conexión: " + e.getMessage());
        } finally {
            desconectar();
        }
    }

    private void desconectar() {
        try {
            clientesConectados.remove(this);
            System.out.println(nombre + " se ha desconectado.");

            // Notificar a los demás clientes de la desconexión
            for (ConexionCliente cliente : clientesConectados) {
                cliente.enviarMensaje(nombre + " se ha desconectado.");
            }

            socket.close();
        } catch (IOException e) {
            System.out.println("Error al cerrar la conexión: " + e.getMessage());
        }
    }

    public void enviarMensaje(String mensaje) throws IOException {
        ObjectOutputStream salida = new ObjectOutputStream(socket.getOutputStream());
        salida.writeObject(mensaje);
    }
}*/
