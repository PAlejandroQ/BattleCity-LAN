package org.pc2_BattleCity.client.gui;

import org.json.JSONObject;
import org.pc2_BattleCity.serverTest2.Cliente;

import java.io.IOException;

public class Airport {

    static public void unpackMessageBeforeReceived(String message) {
        JSONObject msj = new JSONObject(message);
    }

    public void packMessageAndSend(JSONObject message) {


        JSONObject msj = new JSONObject();

        Thread envia = new EnviaThread(msj);
        envia.start();


    }

    class EnviaThread extends Thread {

        JSONObject message;

        public EnviaThread(JSONObject message) {
            this.message = message;
        }

        @Override
        public void run() {
            super.run();
            try {
                Cliente.enviarMensaje(message.toString());
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }


}
