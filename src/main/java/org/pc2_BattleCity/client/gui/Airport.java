package org.pc2_BattleCity.client.gui;

import org.json.JSONObject;
import org.pc2_BattleCity.Constants;
import org.pc2_BattleCity.serverTest2.Cliente;

import java.io.IOException;

public class Airport {
    int idClient;

    public Airport(int idClient){
        this.idClient = idClient;
    }

    public JSONObject unpackMessageBeforeReceived(String message) {
        JSONObject msj = new JSONObject(message);
        return msj;

    }

    public void packMessageAndSend(int keyBoard) {

        JSONObject msj = new JSONObject();
        msj.put(Constants.ID_CLIENT_LABEL,idClient);
        msj.put(Constants.TYPE_REQUEST_LABEL,Constants.KEYBOARD_MESSAGE_REQUEST);
        msj.put(Constants.PAYLOAD_LABEL,keyBoard);


        Thread envia = new EnviaThread(msj.toString());
        envia.start();
    }

    class EnviaThread extends Thread {

        String message;

        public EnviaThread(String message) {
            this.message = message;
        }

        @Override
        public void run() {
            super.run();
            try {
                Cliente.salida.writeObject(message);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }


}
