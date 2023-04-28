package org.pc2_BattleCity.client.gui;

import java.io.Serializable;

public class Mensaje implements Serializable {

    private static final long serialVersionUID = 1L;
    private TipoMensaje tipo;
    private Object datos;

    public Mensaje(TipoMensaje tipo, Object datos) {
        this.tipo = tipo;
        this.datos = datos;
    }

    public TipoMensaje getTipo() {
        return tipo;
    }

    public Object getDatos() {
        return datos;
    }

    public void setID(int id) {
    }
}

