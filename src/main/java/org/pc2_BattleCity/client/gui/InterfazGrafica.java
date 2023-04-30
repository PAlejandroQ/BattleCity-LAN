package org.pc2_BattleCity.client.gui;

import org.json.JSONArray;
import org.json.JSONObject;
import org.pc2_BattleCity.ComplementFunctions;
import org.pc2_BattleCity.Constants;
import org.pc2_BattleCity.serverTest2.Cliente;
import org.pc2_BattleCity.serverTest2.ManagementArmament;

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.plaf.synth.SynthTextAreaUI;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;

public class InterfazGrafica extends JFrame implements KeyListener {
    public GameBoardCanvas gameBoardCanvas;
    public static final int HEIGHT = 600;
    public static final int WIDTH = 600;
    public static final int GRIDSIZE = 15;
    private int NIVEL = 1;
    private int idClient;


//    Juego juego;

    private JSONObject state;
    private JSONObject beforeState = new JSONObject("{'nothing':'nothing'}");
    ThreadRevisaStado revisaStado;


    public void setState(JSONObject state) {
        this.state = state;
    }


    private JSONObject searchObject(String type, int idClient) {
        JSONObject objEntontrado = new JSONObject();
        JSONArray arr = state.getJSONArray(type);

        for (Object objAux : (JSONArray) arr) {

            JSONObject obj = new JSONObject((objAux).toString());

            if (obj.getInt(Constants.ID_OWNER_LABEL) == (idClient)) {
                objEntontrado = obj;
                break;
            }
        }
        return objEntontrado;
    }

    public InterfazGrafica(JSONObject state, int idClient) {
        this.idClient = idClient;
        System.out.println(state.toString() + "Estado");
        this.state = state;
//        this.juego = j;
        this.gameBoardCanvas = new GameBoardCanvas();
        gameBoardCanvas.setPreferredSize(new Dimension(WIDTH, HEIGHT));

        gameBoardCanvas.e = new EaglePanel();
        this.add(gameBoardCanvas);
        this.setPreferredSize(new Dimension(WIDTH + 10, HEIGHT + 40));
        this.pack();
        this.setResizable(false);
        this.setTitle("BATTLECITY");
        this.setVisible(true);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        addKeyListener(this);

        // Crear objetos
        // ...

        // Configurar paneles
        // ...

        // Añadir paneles a la ventana
        // ...

        // Iniciar el juego
        // ...

        /**
         * Iniciado manejaddor de estado
         */
        revisaStado = new ThreadRevisaStado();
        revisaStado.start();

//        Render();//RENDER INICIAL
    }


    public void Render(){
        JSONArray tanques = state.getJSONArray(Constants.TANQUES_LABEL);

        int index = 0;
        for (Object tanqueAux: tanques) {
            JSONObject tanque = new JSONObject(tanqueAux.toString());
            gameBoardCanvas.p[index] = new ImagePanel(tanque);
            index++;
        }
        
//        gameBoardCanvas.p[idClient].setPosition(
//                ((JSONObject) tanques.get(idClient)).getInt(Constants.X_POSITION_LABEL),
//                ((JSONObject)tanques.get(idClient)).getInt(Constants.Y_POSITION_LABEL)
//        );
        gameBoardCanvas.repaint();
    }

    public void keyPressed(KeyEvent e) {
        int key = e.getKeyCode();

//        try {
//            this.juego.conexionCliente.enviarMensaje(String.valueOf(key));
//        } catch (IOException ex) {
//            throw new RuntimeException(ex);
//        }

        // Mueve el tanque según la tecla presionada
        switch (key) {
            case KeyEvent.VK_W:
                System.out.println("Presionando arriba");
                moverJugador(idClient, Direccion.TOP_DIRECTION);
                break;
            case KeyEvent.VK_A:
                System.out.println("Presionando izquierda");
                moverJugador(idClient, Direccion.LEFT_DIRECTION);
                break;
            case KeyEvent.VK_S:
                System.out.println("Presionando abajo");
                moverJugador(idClient, Direccion.BOTTOM_DIRECTION);
                break;
            case KeyEvent.VK_D:
                System.out.println("Presionando derecha");
                moverJugador(idClient, Direccion.RIGHT_DIRECTION);
                break;
            case KeyEvent.VK_SPACE:
//                dispararJugador(0,juego.getTanque(0).getDireccion());
                break;
        }



    }

    //Busac tanque por id owner


    public void keyTyped(KeyEvent e) {
    }

    public void keyReleased(KeyEvent e) {
    }

    public class GameBoardCanvas extends JPanel {
        ImagePanel[] p= new ImagePanel[10];//10 jugadores
        EaglePanel e;

        @Override
        public void paintComponent(Graphics g) {
            System.out.println("REPINTANDO");
            super.paintComponent(g);
            crearObjetos(g);
            setBackground(new Color(4, 6, 46));
            e.paintComponent(g);

            //Pintando tanques



            JSONArray tanques = state.getJSONArray(Constants.TANQUES_LABEL);

            for (Object tAux : (JSONArray) tanques) {

                JSONObject tanque = new JSONObject((tAux).toString());
                p[tanque.getInt(Constants.ID_OWNER_LABEL)].paintComponent(g);

            }


//            for(int i=0;i<state.getJSONArray(Constants.TANQUES_LABEL).length();++i){
//                p[i].paintComponent(g);
//            }
        }

        public void actualizar() {
            repaint();
        }

    }


    private void dibujaMetal(int x, int y, Graphics g2d) {
        g2d.setColor(Color.BLACK);
        g2d.drawRect(x * GRIDSIZE, y * GRIDSIZE, GRIDSIZE, GRIDSIZE);
        g2d.setColor(new Color(92, 92, 92));
        g2d.fillRect(x * GRIDSIZE, y * GRIDSIZE, GRIDSIZE, GRIDSIZE);
    }

    private void dibujaLadrillo(int x, int y, Graphics g2d) {
        g2d.setColor(Color.BLACK);
        g2d.drawRect(x * GRIDSIZE, y * GRIDSIZE, GRIDSIZE, GRIDSIZE);
        g2d.setColor(Color.getHSBColor(25, 0.7f, 0.59f));
        g2d.fillRect(x * GRIDSIZE, y * GRIDSIZE, GRIDSIZE, GRIDSIZE);
    }

    private int [][] getMatrix(JSONArray map) {
        int[][] mapInt = new int[map.length()][map.length()];
        for (int i = 0; i < map.length(); i++) {
            for (int j = 0; j < map.length(); j++) {
                mapInt[i][j] = (int) ((JSONArray)map.get(i)).get(j);
            }
        }
        return mapInt;
    }

    private void crearObjetos(Graphics g) {
        Graphics2D g2d = (Graphics2D) g;
        int [][] mapa = getMatrix((JSONArray) state.get("mapa"));

        for(int j=0; j<mapa.length; ++j){
            for(int i=0; i<mapa.length; ++i){

                if(mapa[i][j] == 1){
                    dibujaMetal(i,j,g2d);
                }
                else if(mapa[i][j]==2){
                    dibujaLadrillo(i,j,g2d);
                }
            }
        }


        // Crear jugadores
        // ...

        // Crear enemigos
        // ...

        // Crear etiquetas para mostrar información de los jugadores
        // ...

        // Crear etiquetas para mostrar información de los enemigos
        // ...
    }

    private void configurarPaneles() {
        // Configurar panel del mapa
        // ...

        // Configurar panel de información
        // ...
    }

    private void añadirPaneles() {
        // Añadir panel del mapa
        // ...

        // Añadir panel de información
        // ...
    }

    private void iniciarJuego() {
        // Iniciar ciclo del juego
        // ...
    }

    private void actualizar() {
        // Actualizar posiciones de los objetos
        // ...

        // Actualizar la interfaz gráfica
        // ...
    }

    private void moverJugador(int idClient, Direccion direccion) {

//        Tanque t = juego.getTanque(jugador);
        int [][] mapa = getMatrix((JSONArray) state.get("mapa"));
        JSONObject tAux = searchObject(Constants.TANQUES_LABEL,idClient);
        Tanque t = new Tanque(
                tAux.getInt(Constants.ID_OBJECT_LABEL),
                tAux.getInt(Constants.ID_OWNER_LABEL),
                tAux.getInt(Constants.X_POSITION_LABEL),
                tAux.getInt(Constants.Y_POSITION_LABEL),
                Constants.getDirectionEnum(tAux.getString(Constants.DIRECTION_LABEL)),
                tAux.getInt(Constants.SPEED_LABEL));


        if(t.getDireccion()!=direccion){
            System.out.println("Camia direcion a"+direccion);
            t.setDireccion(direccion);
            //Actualizando el estado
            Cliente.managementArmament.setObject(t.getTanqueJsonObject(),Constants.TANQUES_LABEL);
            setState(Cliente.managementArmament.getStateGame());
//            Render();
            //            gameBoardCanvas.repaint();
            //Ya que se volvera a renderizar cada que cambia el estado
            return;
        }

        int x = t.getX(), y=t.getY();
        if(direccion.equals(Direccion.TOP_DIRECTION)){

            for(int i=0; i<3;i++){
                if(mapa[x+i][ y-1]!=0){
                    return;
                }
            }
            System.out.println("Moviendoce a "+direccion);
        }
        else if(direccion.equals(Direccion.RIGHT_DIRECTION)){
            for(int i=0; i<3;i++){
                if(mapa[x+3][ y+i]!=0){
                    return;
                }
            }
            System.out.println("Moviendoce a "+direccion);
        }
        else if(direccion.equals(Direccion.BOTTOM_DIRECTION)){
            for(int i=0; i<3;i++){
                if(mapa[x+i][ y+3]!=0){
                    return;
                }
            }
            System.out.println("Moviendoce a "+direccion);
        }
        else if(direccion.equals(Direccion.LEFT_DIRECTION)){
            for(int i=0; i<3;i++){
                if(mapa[x-1][ y+i]!=0){
                    return;
                }
            }
            System.out.println("Moviendoce a "+direccion);
        }
        t.mover(direccion);
        Cliente.managementArmament.setObject(t.getTanqueJsonObject(),Constants.TANQUES_LABEL);
        setState(Cliente.managementArmament.getStateGame());
    }

//    private void dispararJugador(int jugador, Direccion direccion) {
//        juego.disparar(jugador);
//    }

//    public void actualizarBalas() {
//        Graphics2D g = (Graphics2D) gameBoardCanvas.getGraphics();
//        g.setBackground(new Color(4,6,46));
//        for(Bala bala : juego.balas){
//            if(bala.getDireccion()==Direccion.TOP_DIRECTION){
//                g.clearRect(bala.getX()*GRIDSIZE, (bala.getY()+1)*GRIDSIZE, GRIDSIZE, GRIDSIZE);
//            }
//            else if(bala.getDireccion()==Direccion.BOTTOM_DIRECTION){
//                g.clearRect(bala.getX()*GRIDSIZE, (bala.getY()-1)*GRIDSIZE, GRIDSIZE, GRIDSIZE);
//            }
//            else if(bala.getDireccion()==Direccion.LEFT_DIRECTION){
//                g.clearRect((bala.getX()+1)*GRIDSIZE, bala.getY()*GRIDSIZE, GRIDSIZE, GRIDSIZE);
//            }
//            else if(bala.getDireccion()==Direccion.RIGHT_DIRECTION){
//                g.clearRect((bala.getX()-1)*GRIDSIZE, bala.getY()*GRIDSIZE, GRIDSIZE, GRIDSIZE);
//            }
//            g.setColor(Color.white);
//            g.fillOval(bala.getX()*GRIDSIZE, bala.getY()*GRIDSIZE, GRIDSIZE, GRIDSIZE);
//            System.out.println(bala.getX() + "|" + bala.getY());
//        }
//    }

    private void moverEnemigos() {
        // Mover enemigos en dirección aleatoria
        // ...
    }

    private void dispararEnemigos() {
        // Crear balas de los enemigos
        // ...
    }

    private void actualizarVidaJugadores() {
        // Actualizar etiquetas de vida de los jugadores
        // ...
    }

    private void actualizarPuntajeJugadores() {
        // Actualizar etiquetas de puntaje de los jugadores
        // ...
    }

    private void actualizarVidaEnemigos() {
        // Actualizar etiquetas de vida de los enemigos
        // ...
    }

    private void actualizarPuntajeEnemigos() {
        // Actualizar etiquetas de puntaje de los enemigos
        // ...
    }

    public class ImagePanel extends JPanel {
        int x = 0, y = 0;
        JSONObject t;
        int direccion = 0;
        private BufferedImage image;
        Image img;

        public ImagePanel(JSONObject t) {
            this.t = t;
            System.out.println("\nRUNBA:"+this.t.toString());
            this.x = (t.getInt(Constants.X_POSITION_LABEL)) * GRIDSIZE;
            this.y = (t.getInt(Constants.Y_POSITION_LABEL)) * GRIDSIZE;
            try {
                image = ImageIO.read(new File("src/main/java/org/pc2_BattleCity/client/gui/tank.png"));
                ImageIcon icon = new ImageIcon(image.getScaledInstance(3 * GRIDSIZE, 3 * GRIDSIZE, Image.SCALE_SMOOTH));
                img = icon.getImage();
            } catch (IOException ex) {
                System.out.println("No se encontró imagen w");
            }
        }

        @Override
        protected void paintComponent(Graphics g) {

            super.paintComponent(g);
            System.out.println(x / GRIDSIZE + " " + y / GRIDSIZE);
            Graphics2D g2d = (Graphics2D) g;
//            System.out.println(t.getDireccion());
            if (Constants.getDirectionEnum(t.getString(Constants.DIRECTION_LABEL)).equals(Direccion.RIGHT_DIRECTION)) {
                g2d.rotate(Math.PI / 2, x + 3 * GRIDSIZE / 2, y + 3 * GRIDSIZE / 2);
            } else if (Constants.getDirectionEnum(t.getString(Constants.DIRECTION_LABEL)).equals(Direccion.LEFT_DIRECTION)) {
                g2d.rotate(-Math.PI / 2, x + 3 * GRIDSIZE / 2, y + 3 * GRIDSIZE / 2);
            } else if (Constants.getDirectionEnum(t.getString(Constants.DIRECTION_LABEL)).equals(Direccion.BOTTOM_DIRECTION)) {
                g2d.rotate(2 * Math.PI / 2, x + 3 * GRIDSIZE / 2, y + 3 * GRIDSIZE / 2);
            }


            g2d.drawImage(img, t.getInt(Constants.X_POSITION_LABEL) * GRIDSIZE, t.getInt(Constants.Y_POSITION_LABEL) * GRIDSIZE, null); // see javadoc for more info on the parameters

            if (Constants.getDirectionEnum(t.getString(Constants.DIRECTION_LABEL)).equals(Direccion.RIGHT_DIRECTION)) {
                g2d.rotate(-Math.PI / 2, x + 3 * GRIDSIZE / 2, y + 3 * GRIDSIZE / 2);
            } else if (Constants.getDirectionEnum(t.getString(Constants.DIRECTION_LABEL)).equals(Direccion.LEFT_DIRECTION)) {
                g2d.rotate(Math.PI / 2, x + 3 * GRIDSIZE / 2, y + 3 * GRIDSIZE / 2);
            } else if (Constants.getDirectionEnum(t.getString(Constants.DIRECTION_LABEL)).equals(Direccion.BOTTOM_DIRECTION)) {
                g2d.rotate(-2 * Math.PI / 2, x + 3 * GRIDSIZE / 2, y + 3 * GRIDSIZE / 2);
            }
        }

        public void setPosition(int x, int y) {

            this.x = x * GRIDSIZE;
            this.y = y * GRIDSIZE;
            repaint();
        }

    }

    public class EaglePanel extends JPanel {
        int x = 18, y = 34;
        private BufferedImage image;
        Image img;

        public EaglePanel() {
            try {
                image = ImageIO.read(new File("src/main/java/org/pc2_BattleCity/client/gui/eagle.png"));
                ImageIcon icon = new ImageIcon(image.getScaledInstance(4 * GRIDSIZE, 4 * GRIDSIZE, Image.SCALE_SMOOTH));
                img = icon.getImage();
            } catch (IOException ex) {
                System.out.println("No se encontró imagen");
            }
        }

        @Override
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);
            Graphics2D g2d = (Graphics2D) g;
            g2d.drawImage(img, x*GRIDSIZE, y*GRIDSIZE,null); // see javadoc for more info on the parameters
        }

    }


    /**
     * Revisador de estado
     * Para renderizar la pantalla, si esta cambia
     */
    public class ThreadRevisaStado extends Thread{
        @Override
        public void run() {
            while (true){
                try {
                    Thread.sleep(100);
                    if(!ComplementFunctions.isEqualJSONs(beforeState,state)){
                        //Envia a los demas BroadCasts
                        Cliente.managementArmament.setNewState(state);
                        Cliente.sendStateToServer();
                        System.out.print("Cambio estado (Renderizando):"+state);
                        Render();
                    }
                    beforeState = new JSONObject (state.toString());
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }

            }
        }
    }

}
