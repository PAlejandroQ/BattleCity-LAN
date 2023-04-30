package org.pc2_BattleCity;

import org.json.JSONObject;
import org.pc2_BattleCity.client.gui.Direccion;
import org.pc2_BattleCity.client.gui.Tanque;

public class Prueba {

    public static void main(String[] args) {
        JSONObject json = new JSONObject();
//        json.put("mapa",Constants.MAPA_NIVEL_1.getMap());
//        int[][] other = Constants.MAPA_NIVEL_1.getMap();
//        other[0][0] = 20;
//        json.put("mapa",other);

//        Tanque t = new Tanque(2,3, Direccion.LEFT_DIRECTION,5);
//        json.put("tanque",t.getTanqueJsonObject());
//        System.out.print(json);


        ThreadRevisaStado thread = new ThreadRevisaStado();
        thread.start();
    }


    public static class ThreadRevisaStado extends Thread{
        @Override
        public void run() {

            int i = 0;
            while (true){
                try {
                    Thread.sleep(100);
                    System.out.print(i+"\n");
                    i++;
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }

            }
        }
    }
}
