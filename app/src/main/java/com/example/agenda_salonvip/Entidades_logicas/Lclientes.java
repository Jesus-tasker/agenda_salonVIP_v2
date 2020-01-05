package com.example.agenda_salonvip.Entidades_logicas;

import com.example.agenda_salonvip.Entidades_fire.Clientes;
import com.example.agenda_salonvip.Entidades_fire.Usuario;

public class Lclientes {

    private String key;

    private Clientes clientes;

    private LUsuario lUsuario; //esta sera la llave del objeto conectado, ews un objeto loogico

    public Lclientes(String key, Clientes clientes) {
        this.key = key;
        this.clientes = clientes;
    }

    public Lclientes(String key, Clientes clientes, Lclientes lclientes, LUsuario lUsuario) {
        this.key = key;
        this.clientes = clientes;
        this.lUsuario = lUsuario;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public Clientes getClientes() {
        return clientes;
    }

    public void setClientes(Clientes clientes) {
        this.clientes = clientes;
    }



    public LUsuario getlUsuario() {
        return lUsuario;
    }

    public void setlUsuario(LUsuario lUsuario) {
        this.lUsuario = lUsuario;
    }
}
