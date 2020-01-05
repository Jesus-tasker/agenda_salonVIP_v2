package com.example.agenda_salonvip.Entidades_fire;

public class CitaEnt {

    private String fotodePerfilURI;
    private String nombre_cliente_cita;
    private String telefono_cliente_cita;
    private  Long fecha_de_cita;
    private String servicio;
    private String tiempo_proceso;
    private String key_cita;


    public CitaEnt() {
    }

    public CitaEnt(String fotodePerfilURI, String nombre_cliente_cita, String telefono_cliente_cita, Long fecha_de_cita, String servicio, String tiempo_proceso, String key_cita) {
        this.fotodePerfilURI = fotodePerfilURI;
        this.nombre_cliente_cita = nombre_cliente_cita;
        this.telefono_cliente_cita = telefono_cliente_cita;
        this.fecha_de_cita = fecha_de_cita;
        this.servicio = servicio;
        this.tiempo_proceso = tiempo_proceso;
        this.key_cita = key_cita;
    }

    public String getFotodePerfilURI() {
        return fotodePerfilURI;
    }

    public void setFotodePerfilURI(String fotodePerfilURI) {
        this.fotodePerfilURI = fotodePerfilURI;
    }

    public String getNombre_cliente_cita() {
        return nombre_cliente_cita;
    }

    public void setNombre_cliente_cita(String nombre_cliente_cita) {
        this.nombre_cliente_cita = nombre_cliente_cita;
    }

    public String getTelefono_cliente_cita() {
        return telefono_cliente_cita;
    }

    public void setTelefono_cliente_cita(String telefono_cliente_cita) {
        this.telefono_cliente_cita = telefono_cliente_cita;
    }

    public Long getFecha_de_cita() {
        return fecha_de_cita;
    }

    public void setFecha_de_cita(Long fecha_de_cita) {
        this.fecha_de_cita = fecha_de_cita;
    }

    public String getServicio() {
        return servicio;
    }

    public void setServicio(String servicio) {
        this.servicio = servicio;
    }

    public String getTiempo_proceso() {
        return tiempo_proceso;
    }

    public void setTiempo_proceso(String tiempo_proceso) {
        this.tiempo_proceso = tiempo_proceso;
    }

    public String getKey_cita() {
        return key_cita;
    }

    public void setKey_cita(String key_cita) {
        this.key_cita = key_cita;
    }
}
