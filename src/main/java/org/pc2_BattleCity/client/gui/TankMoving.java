package org.pc2_BattleCity.client.gui;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class TankMoving extends JFrame implements KeyListener {
    @Override
    public void keyTyped(KeyEvent keyEvent) {

    }

    @Override
    public void keyPressed(KeyEvent keyEvent) {

    }

    @Override
    public void keyReleased(KeyEvent keyEvent) {

    }
//
//    private JLabel tanqueLabel;
//    private ImageIcon tanqueIcon;
//    private int tanqueX = 100;
//    private int tanqueY = 100;
//
//    public static final int HEIGHT=800;
//    public static final int WIDTH=800;
//    public static final int GRIDSIZE=20;
//    private int NIVEL=1;
//
//    Juego juego;
//    private Mapa mapa;
//    private Tanque[] jugadores;
////    private Enemigo[] enemigos;
//    private JLabel[] lblVidaJugadores;
//    private JLabel[] lblPuntajeJugadores;
//    private JLabel[] lblVidaEnemigos;
//    private JLabel[] lblPuntajeEnemigos;
//
//    public TankMoving() {
//        super("BattleCity");
//        this.juego = new Juego();
//
//        // Carga la imagen del tanque y redimensionarla
//        tanqueIcon = new ImageIcon("src/main/java/org/pc2_BattleCity/client/gui/tank.png");
//        Image imagenOriginal = tanqueIcon.getImage();
//        Image imagenRedimensionada = imagenOriginal.getScaledInstance(3*GRIDSIZE, 3*GRIDSIZE, Image.SCALE_SMOOTH);
//        tanqueIcon = new ImageIcon(imagenRedimensionada);
//
//
//        // Crea un JLabel con la imagen del tanque
//        tanqueLabel = new JLabel(tanqueIcon);
//        tanqueLabel.setBounds(tanqueX, tanqueY, tanqueIcon.getIconWidth(), tanqueIcon.getIconHeight());
//
//        // Crea un JPanel para el mapa y agrega el JLabel del tanque
//        GameBoardCanvas panelMapa = new GameBoardCanvas();
////        panelMapa.setBackground(Color.BLACK);
//        panelMapa.setPreferredSize(new Dimension(WIDTH, HEIGHT));
//        panelMapa.add(tanqueLabel);
//
//        // Agrega el panel del mapa al JFrame
//        getContentPane().add(panelMapa);
//
//        // Agrega el KeyListener a la ventana
//        addKeyListener(this);
//
//        // Configura la ventana
//        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
//        setResizable(false);
//        pack();
//        setLocationRelativeTo(null);
//        setVisible(true);
//    }
//
//    public void keyPressed(KeyEvent e) {
//        int key = e.getKeyCode();
//
//        // Mueve el tanque según la tecla presionada
//        switch (key) {
//            case KeyEvent.VK_W:
//                tanqueY -= GRIDSIZE;
//                break;
//            case KeyEvent.VK_A:
//                tanqueX -= GRIDSIZE;
//                break;
//            case KeyEvent.VK_S:
//                tanqueY += GRIDSIZE;
//                break;
//            case KeyEvent.VK_D:
//                tanqueX += GRIDSIZE;
//                break;
//        }
//
//        // Actualiza la posición del JLabel del tanque
//        tanqueLabel.setBounds(tanqueX, tanqueY, tanqueIcon.getIconWidth(), tanqueIcon.getIconHeight());
//    }
//
//    public void keyTyped(KeyEvent e) {}
//
//    public void keyReleased(KeyEvent e) {}
//
//    class GameBoardCanvas extends JPanel{
//        InterfazGrafica.ImagePanel p[] = new InterfazGrafica.ImagePanel[3];
//
//        int numTanques = 3;
//
//        @Override
//        public void paintComponent(Graphics g){
//            super.paintComponent(g);
//            crearObjetos(g);
////            dibujaTanques();
//            setBackground(new Color(4,6,46));
////            for(int i=0;i<numTanques;++i){
////                p[i].paintComponent(g);
////            }
//        }
////        public void dibujaTanques(){
////            p[0]=new InterfazGrafica.ImagePanel(2,2);
////            p[1]= new InterfazGrafica.ImagePanel(2, juego.mapa.getAlto()-5);
////            p[2]= new InterfazGrafica.ImagePanel(juego.mapa.getAncho()-5, 2);
////        }
//
//    }
//
//    private void dibujaMetal(int x, int y,Graphics g2d){
//        g2d.setColor(Color.BLACK);
//        g2d.drawRect(x*GRIDSIZE, y*GRIDSIZE,GRIDSIZE, GRIDSIZE);
//        g2d.setColor(new Color(92,92,92));
//        g2d.fillRect(x*GRIDSIZE, y*GRIDSIZE,GRIDSIZE, GRIDSIZE);
//    }
//
//    private void dibujaLadrillo(int x, int y, Graphics g2d){
//        g2d.setColor(Color.BLACK);
//        g2d.drawRect(x*GRIDSIZE, y*GRIDSIZE,GRIDSIZE, GRIDSIZE);
//        g2d.setColor(Color.getHSBColor(25, 0.7f,0.59f));
//        g2d.fillRect(x*GRIDSIZE, y*GRIDSIZE,GRIDSIZE, GRIDSIZE);
//    }
//
//
//    private void crearObjetos(Graphics g) {
//        Graphics2D g2d = (Graphics2D) g;
//
//        for(int j=0; j<this.juego.mapa.getAlto(); ++j){
//            for(int i=0; i<this.juego.mapa.getAncho(); ++i){
//
//                if(this.juego.mapa.getCasilla(i,j) == 1){
//                    dibujaMetal(i,j,g2d);
//                }
//                else if(this.juego.mapa.getCasilla(i,j)==2){
//                    dibujaLadrillo(i,j,g2d);
//                }
//            }
//        }
//
//
//        // Crear jugadores
//        // ...
//
//        // Crear enemigos
//        // ...
//
//        // Crear etiquetas para mostrar información de los jugadores
//        // ...
//
//        // Crear etiquetas para mostrar información de los enemigos
//        // ...
//    }
//    public static void main(String[] args) {
//        new TankMoving();
//    }
}
