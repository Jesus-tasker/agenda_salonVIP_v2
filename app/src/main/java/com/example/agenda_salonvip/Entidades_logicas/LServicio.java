package com.example.agenda_salonvip.Entidades_logicas;

import com.example.agenda_salonvip.Entidades_fire.Servicios;

public class LServicio {
    private String key;

    private Servicios servicios;

    private LUsuario lUsuario;

    public LServicio() {
    }

    public LServicio(String key, Servicios servicios) {
        this.key = key;
        this.servicios = servicios;
    }

    public LServicio(String key, Servicios servicios, LUsuario lUsuario) {
        this.key = key;
        this.servicios = servicios;
        this.lUsuario = lUsuario;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public Servicios getServicios() {
        return servicios;
    }

    public void setServicios(Servicios servicios) {
        this.servicios = servicios;
    }

    public LUsuario getlUsuario() {
        return lUsuario;
    }

    public void setlUsuario(LUsuario lUsuario) {
        this.lUsuario = lUsuario;
    }
}
