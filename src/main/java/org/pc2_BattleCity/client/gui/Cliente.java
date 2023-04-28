package org.pc2_BattleCity.client.gui;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

public class Cliente {
    private final String ip;
    private final int puerto;
    private boolean conectado;
    private Thread hiloCliente;
    private Socket socket;
    private ObjectInputStream entrada;
    private ObjectOutputStream salida;

    public Cliente(String ip, int puerto) {
        this.ip = ip;
        this.puerto = puerto;
        this.conectado = false;
    }

    public void conectar() {
        if (conectado) {
            return;
        }

        try {
            socket = new Socket(ip, puerto);
            entrada = new ObjectInputStream(socket.getInputStream());
            salida = new ObjectOutputStream(socket.getOutputStream());
            // Se inicia un nuevo hilo para manejar la comunicación con el servidor
            hiloCliente = new Thread(this::manejarConexion);
            hiloCliente.start();
            conectado = true;
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void desconectar() {
        if (!conectado) {
            return;
        }

        conectado = false;
        try {
            // Se cierran los streams y el socket para detener la ejecución del hilo del cliente
            entrada.close();
            salida.close();
            socket.close();
            hiloCliente.join();
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }
    }

    public void enviarDatos(Object datos) {
        if (!conectado) {
            return;
        }

        try {
            salida.writeObject(datos);
            salida.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void manejarConexion() {
        // Aquí se maneja la lógica de comunicación con el servidor (enviar y recibir datos)
        // Puedes implementarla según tus necesidades
        while (conectado) {
            try {
                Object datos = entrada.readObject();
                // Se procesan los datos recibidos del servidor
                // ...
            } catch (IOException | ClassNotFoundException e) {
                e.printStackTrace();
            }
        }
    }
}
