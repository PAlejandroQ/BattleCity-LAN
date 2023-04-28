package org.pc2_BattleCity.client.gui;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class SquareFrame extends JFrame {

    public SquareFrame() {
        super("Square Frame");
        this.add(new ImagePanel());
        setSize(300, 300);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }

    public void paint(Graphics g) {
        super.paint(g);
        g.drawOval(0, 0, 200, 200);
    }

    public static void main(String[] args) {
        SquareFrame frame = new SquareFrame();

        frame.setVisible(true);
    }

    public class ImagePanel extends JPanel {
        int x=0,y=0;
        private BufferedImage image;

        public ImagePanel() {

            try {
                image = ImageIO.read(new File("src/main/java/org/pc2_BattleCity/client/gui/tank.png"));
            } catch (IOException ex) {
                System.out.println("No se encontró imagen aqui");
            }
        }

        @Override
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);
            System.out.println("Pintando");
            g.drawImage(image, 20, 20,120,120,this); // see javadoc for more info on the parameters
        }

    }
}
