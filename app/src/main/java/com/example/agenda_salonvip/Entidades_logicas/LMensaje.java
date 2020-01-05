package com.example.agenda_salonvip.Entidades_logicas;

import com.example.agenda_salonvip.Entidades_fire.Mensaje;

import org.ocpsoft.prettytime.PrettyTime;

import java.util.Date;
import java.util.Locale;

public class LMensaje {
    //nota com estos se conectana  firebase debe exitit un objeto logica de Usuario para el nombre de los mensajes


    private  String key; //la llave de uid que tiene el nodo
    private Mensaje mensaje;
    private LUsuario lUsuario; //esta sera la llave del objeto conectado, ews un objeto loogico
    private  Lclientes lclientes;

    public LMensaje(String key, Mensaje mensaje) {
        this.key = key;
        this.mensaje = mensaje;
    }


    public LMensaje(String key, Mensaje mensaje, LUsuario lUsuario) {
        this.key = key;
        this.mensaje = mensaje;
        this.lUsuario = lUsuario;
    }
    public LMensaje(String key, Mensaje mensaje, Lclientes lclientes) {
        this.key = key;
        this.mensaje = mensaje;
        this.lclientes= lclientes;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public Mensaje getMensaje() {
        return mensaje;
    }

    public void setMensaje(Mensaje mensaje) {
        this.mensaje = mensaje;
    }
    //para greagar la hora dle emnsaje
    public Long getCreatedTimestamp(){

        return (long)mensaje.getCreateTimestamp();
    }

    public LUsuario getlUsuario() {
        return lUsuario;
    }

    public void setlUsuario(LUsuario lUsuario) {
        this.lUsuario = lUsuario;
    }

    public  String fecha_enviado_mensaje(){

        Date date=new Date(getCreatedTimestamp());
        PrettyTime prettyTime=new PrettyTime(new Date(), Locale.getDefault()); //obtiene el formato del dispositivo local
        return  prettyTime.format(date);
        /*
       // Long cdigoHora=mensajeList.get(position).getHora();
       // al pedir un ong para funcionar simplemente enviamos el lon getCreatedTimestamp()
        Date d=new Date(getCreatedTimestamp());
        SimpleDateFormat sdf=new SimpleDateFormat("hh:mm:ss a", Locale.getDefault());
        return sdf.format(d); */
    }

    public Lclientes getLclientes() {
        return lclientes;
    }

    public void setLclientes(Lclientes lclientes) {
        this.lclientes = lclientes;
    }
}
