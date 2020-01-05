package com.example.agenda_salonvip.Entidades_fire;

import com.google.firebase.database.ServerValue;

public class Mensaje {

    private String mensaje; //en este caso el Mensaje recibira el url del Mensaje
    private  String urlfoto;
    private  boolean contienefoto;
    private String keyemior;
    private Object createTimestamp;



    public Mensaje() {
        createTimestamp= ServerValue.TIMESTAMP; //cuando se envio el Mensaje firebase
    }

    public String getKeyemior() {
        return keyemior;
    }

    public void setKeyemior(String keyemior) {
        this.keyemior = keyemior;
    }

    public boolean isContienefoto() {
        return contienefoto;
    }

    public void setContienefoto(boolean contienefoto) {
        this.contienefoto = contienefoto;
    }


    public String getMensaje() {
        return mensaje;
    }

    public void setMensaje(String mensaje) {
        this.mensaje = mensaje;
    }



    public String getUrlfoto() {
        return urlfoto;
    }

    public void setUrlfoto(String urlfoto) {
        this.urlfoto = urlfoto;
    }

    public Object getCreateTimestamp() {
        return createTimestamp;
    }
}
