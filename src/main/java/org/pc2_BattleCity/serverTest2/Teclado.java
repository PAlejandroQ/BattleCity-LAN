package org.pc2_BattleCity.serverTest2;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.util.Arrays;

public class Teclado implements KeyListener {

    private boolean w, a, s, d, p;
    private boolean[] teclas;
    private boolean[] lastTeclas;
    private boolean fragPress=false;
     private ObjectOutputStream canalSalida;
    public Teclado(ObjectOutputStream canalSalida) {
        w = false;
        a = false;
        s = false;
        d = false;
        p = false;
        teclas = new boolean[5];
        lastTeclas = new boolean[]{true, true, true, true, true};
        this.canalSalida = canalSalida;
    }

    public String getTeclasPresionadas() {
        boolean[] teclas = new boolean[5];
        teclas[0] = w;
        teclas[1] = a;
        teclas[2] = s;
        teclas[3] = d;
        teclas[4] = p;
        return Arrays.toString(teclas);
    }

    @Override
    public void keyPressed(KeyEvent e) {

        fragPress = true;
        switch (e.getKeyCode()) {
            case KeyEvent.VK_W:
                w = true;
                teclas[0] = w;
                break;
            case KeyEvent.VK_A:
                a = true;
                teclas[1] = a;
                break;
            case KeyEvent.VK_S:
                s = true;
                teclas[2] = s;
                break;
            case KeyEvent.VK_D:
                d = true;
                teclas[3] = d;
                break;
            case KeyEvent.VK_P:
                p = true;
                teclas[4] = p;
                break;
        }
        try {
            this.canalSalida.writeObject(getTeclasPresionadas());
        } catch (IOException ex) {
            throw new RuntimeException(ex);
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {
        fragPress = false;
        switch (e.getKeyCode()) {
            case KeyEvent.VK_W:
                w = false;
                teclas[0] = w;
                break;
            case KeyEvent.VK_A:
                a = false;
                teclas[1] = a;
                break;
            case KeyEvent.VK_S:
                s = false;
                teclas[2] = s;
                break;
            case KeyEvent.VK_D:
                d = false;
                teclas[3] = d;
                break;
            case KeyEvent.VK_P:
                p = false;
                teclas[4] = p;
                break;
        }
    }

    @Override
    public void keyTyped(KeyEvent e) {}

    public boolean anyPressed() {
        return fragPress;
    }

    public boolean isDifferent(){
//        if(!anyPressed()) return false;

        if(lastTeclas.equals(teclas)){
            return false;
        }else{
            System.arraycopy(teclas,0, lastTeclas, 0, 5);
            return true;
        }

    }
}
