
package org.pc2_BattleCity.client.gui;


public class InterfazGraficaTESTEO {

}
/*
public class InterfazGraficaTESTEO extends JPanel implements KeyListener {
    private static final int ANCHO_VENTANA = 640;
    private static final int ALTO_VENTANA = 480;
    private static final int ANCHO_MAPA = 416;
    private static final int ALTO_MAPA = 416;
    private static final int ANCHO_PANEL_INFO = 224;
    private static final int ALTO_PANEL_INFO = 416;
    private static final int ANCHO_TANQUE = 32;
    private static final int ALTO_TANQUE = 32;

    private Mapa mapa;
    private Tanque jugador;
*//*

*/
/*    private ArrayList<Tanque> enemigos;
    private ArrayList<Bala> balas;
    private JPanel panelMapa;
    private JPanel panelInfo;*//*
*/
/*

    private boolean juegoTerminado;

//    public InterfazGrafica() {
//        setPreferredSize(new Dimension(ANCHO_VENTANA, ALTO_VENTANA));
//        setFocusable(true);
//        addKeyListener(this);
//
//        juegoTerminado = false;
//
//        mapa = new Mapa();
//        jugador = new Tanque(192, 384, Direccion.ARRIBA, 4);
//        enemigos = new ArrayList<>();
//        enemigos.add(new Enemigo(32, 32, Direccion.ABAJO));
//        enemigos.add(new Enemigo(192, 32, Direccion.ABAJO));
//        balas = new ArrayList<>();
//
//
//
//        panelMapa = new JPanel() {
//            @Override
//            protected void paintComponent(Graphics g) {
//                super.paintComponent(g);
//
//                for (int y = 0; y < mapa.getAlto(); y++) {
//                    for (int x = 0; x < mapa.getAncho(); x++) {
//                        int valor = mapa.getValor(x, y);
//
//                        if (valor == 1) {
//                            g.setColor(Color.GRAY);
//                            g.fillRect(x, y, 1, 1);
//                        } else if (valor == 2) {
//                            g.setColor(Color.BLACK);
//                            g.fillRect(x, y, 1, 1);
//                        }
//                    }
//                }
//
//                g.setColor(Color.GREEN);
//                g.fillRect(jugador.getX(), jugador.getY(), ANCHO_TANQUE, ALTO_TANQUE);
//
//                for (Tanque enemigo : enemigos) {
//                    g.setColor(Color.RED);
//                    g.fillRect(enemigo.getX(), enemigo.getY(), ANCHO_TANQUE, ALTO_TANQUE);
//                }
//
//                g.setColor(Color.WHITE);
//                for (Bala bala : balas) {
//                    g.fillRect(bala.getX(), bala.getY(), 2, 2);
//                }
//            }
//        };
//
//        panelMapa.setPreferredSize(new Dimension(ANCHO_MAPA, ALTO_MAPA));
//        panelMapa.setFocusable(false);
//
//        panelInfo = new JPanel();
//        panelInfo.setPreferredSize(new Dimension(ANCHO_PANEL_INFO, ALTO_PANEL_INFO));
//        panelInfo.setFocusable(false);
//        panelInfo.setBackground(Color.BLUE);
//
//        setLayout(new BorderLayout());
//        add(panelMapa, BorderLayout.WEST);
//        add(panelInfo, BorderLayout.EAST);
//
//        actualizar();
//
//    }
//
//    private void actualizar() {
//        // Actualizar tanques
//        for (Tanque tanque : tanques) {
//            tanque.actualizar();
//        }
//
//        // Actualizar enemigos
//        for (Enemigo enemigo : enemigos) {
//            enemigo.actualizar();
//        }
//
//        // Actualizar balas
//        actualizarBalas();
//
//        // Verificar colisiones
//        verificarColisiones();
//
//        // Verificar si se ha ganado o perdido el juego
//        if (enemigos.isEmpty() && tanques.size() == 1) {
//            JOptionPane.showMessageDialog(this, "¡Felicidades, has ganado!");
//            System.exit(0);
//        } else if (tanques.isEmpty()) {
//            JOptionPane.showMessageDialog(this, "¡Has perdido!");
//            System.exit(0);
//        }
//    }
//
//    private void verificarColisiones() {
//    }
//
//    private void actualizarBalas() {
//    }
//    // Agregar objetos al mapa
//        mapa.agregarObjeto(new Pared(false), 1, 1);
//        mapa.agregarObjeto(new Pared(false), 1, 2);
//        mapa.agregarObjeto(new Pared(false), 2, 1);
//        mapa.agregarObjeto(new Pared(false), 2, 2);
//        mapa.agregarObjeto(new Pared(true), 0, 0);
//        mapa.agregarObjeto(new Pared(true), 0, 1);
//        mapa.agregarObjeto(new Pared(true), 0, 2);
//        mapa.agregarObjeto(new Pared(true), 3, 3);
//        mapa.agregarObjeto(new Pared(true), 3, 4);
//        mapa.agregarObjeto(new Pared(true), 3, 5);
//        mapa.agregarObjeto(new Pared(true), 3, 6);
//        mapa.agregarObjeto(new Pared(true), 3, 7);
//        mapa.agregarObjeto(new Pared(true), 3, 8);
//        mapa.agregarObjeto(new Pared(true), 3, 9);
//        mapa.agregarObjeto(new Pared(true), 3, 10);
//        mapa.agregarObjeto(new Pared(true), 3, 11);
//        mapa.agregarObjeto(new Pared(true), 3, 12);
//        mapa.agregarObjeto(new Pared(true), 9, 1);
//        mapa.agregarObjeto(new Pared(true), 9, 2);
//        mapa.agregarObjeto(new Pared(true), 9, 3);
//        mapa.agregarObjeto(new Pared(true), 9, 4);
//        mapa.agregarObjeto(new Pared(true), 9, 5);
//        mapa.agregarObjeto(new Pared(true), 9, 6);
//        mapa.agregarObjeto(new Pared(true), 9, 7);
//        mapa.agregarObjeto(new Pared(true), 9, 8);
//        mapa.agregarObjeto(new Pared(true), 9, 9);
//        mapa.agregarObjeto(new Pared(true), 9, 10);
//        mapa.agregarObjeto(new Pared(true), 9, 11);
//        mapa.agregarObjeto(new Pared(true), 9, 12);
//
//    // Crear tanque jugador
//    Tanque tanqueJugador = new Tanque(true, Direccion.ARRIBA, mapa);
//        tanques.add(tanqueJugador);
//        mapa.agregarObjeto(tanqueJugador, 6,
//
//
//
//
//
//
//}

*/

