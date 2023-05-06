package org.pc2_BattleCity.client.gui;

import javax.swing.*;
import java.awt.*;

public class StartWindow extends JFrame {
    Juego juego;
    JButton inicioButton;

    public StartWindow(Juego juego) {
        this.juego = juego;
        // Configuración de la ventana
        setPreferredSize(new Dimension(700,700));
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setTitle("Battle City");

        // Creación del panel principal y asignación del GridBagLayout
        JPanel panel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        panel.setBackground(new Color(0x0F1C37));
        add(panel);

        // Creación del JLabel del título
        JLabel tituloLabel = new JLabel("Battle City");
        tituloLabel.setForeground(Color.WHITE);
        tituloLabel.setFont(new Font("Arial", Font.BOLD, 48));
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 3;
        gbc.insets = new Insets(50, 0, 50, 0);
        panel.add(tituloLabel, gbc);

        // Creación del JLabel para pedir el nombre
        JLabel nombreLabel = new JLabel("IP del Server:");
        nombreLabel.setFont(new Font("Arial", Font.PLAIN, 20));
        nombreLabel.setForeground(Color.WHITE);
        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.gridwidth = 1;
        gbc.insets = new Insets(0, 50, 10, 0);
        panel.add(nombreLabel, gbc);

        // Creación del JTextField para ingresar el ip
        JTextField ipServerField = new JTextField(20);

        gbc.gridx = 1;
        gbc.gridy = 1;
        gbc.gridwidth = 2;
        gbc.insets = new Insets(0, 0, 10, 50);
        panel.add(ipServerField, gbc);

        //Pedir nivel
        JLabel labelSeleccion = new JLabel("Selecciona el nivel:");
        labelSeleccion.setForeground(Color.WHITE);

        labelSeleccion.setFont(new Font("Arial", Font.PLAIN, 20));

        JRadioButton opcion1 = new JRadioButton("nivel 1",true);
        opcion1.setBackground(new Color(0x0F1C37));
        opcion1.setActionCommand("1");
        JRadioButton opcion2 = new JRadioButton("nivel 2");
        opcion2.setBackground(new Color(0x0F1C37));
        opcion2.setActionCommand("2");
        JRadioButton opcion3 = new JRadioButton("nivel 3");
        opcion3.setBackground(new Color(0x0F1C37));
        opcion3.setActionCommand("3");

        opcion1.setForeground(Color.WHITE);
//        opcion1.setSelected(true);//Default
        opcion2.setForeground(Color.WHITE);
        opcion3.setForeground(Color.WHITE);

        ButtonGroup grupoOpciones = new ButtonGroup();

        grupoOpciones.add(opcion1);
        grupoOpciones.add(opcion2);
        grupoOpciones.add(opcion3);

        GridBagConstraints gbcSeleccion = new GridBagConstraints();
        gbcSeleccion.gridx = 0;
        gbcSeleccion.gridy = 2;

        gbcSeleccion.insets = new Insets(0, 0, 20, 0); //Padding

        panel.add(labelSeleccion, gbcSeleccion);

        gbcSeleccion.gridx = 1;
        panel.add(opcion1, gbcSeleccion);
        gbcSeleccion.gridx = 2;
        panel.add(opcion2, gbcSeleccion);
        gbcSeleccion.gridx = 3;
        panel.add(opcion3, gbcSeleccion);

        // Creación del botón de inicio
        inicioButton = new JButton("Iniciar");
        gbc.gridx = 1;
        gbc.gridy = 4;
        gbc.gridwidth =2;
        gbc.insets = new Insets(50, 0, 0, 0);
        panel.add(inicioButton, gbc);
        inicioButton.addActionListener(e->{
            juego.StartJuagoAfterSuccessfulConnection(ipServerField.getText(),grupoOpciones.getSelection().getActionCommand());
            this.dispose();
        });

        this.pack();
        this.setResizable(false);
        this.setTitle("BATTLECITY");
        this.setVisible(true);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }



}
