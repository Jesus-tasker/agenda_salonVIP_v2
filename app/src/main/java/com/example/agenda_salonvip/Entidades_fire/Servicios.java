package com.example.agenda_salonvip.Entidades_fire;

public class Servicios {

    private String fotodePerfilURI_Servicio;
    private String fotode_servicio_URI;
    private String nombre_emisor;

    private String nombre_servicio;
    private String ubicacion;
    private String precio;
    private String tiempo_proceso; //aun no interada
    private String key_servicio;

    public Servicios() {
    }

    public Servicios(String fotodePerfilURI_Servicio, String fotode_servicio_URI, String nombre_emisor, String nombre_servicio, String ubicacion, String precio, String tiempo_proceso, String key_servicio) {
        this.fotodePerfilURI_Servicio = fotodePerfilURI_Servicio;
        this.fotode_servicio_URI = fotode_servicio_URI;
        this.nombre_emisor = nombre_emisor;
        this.nombre_servicio = nombre_servicio;
        this.ubicacion = ubicacion;
        this.precio = precio;
        this.tiempo_proceso = tiempo_proceso;
        this.key_servicio = key_servicio;
    }

    public String getFotodePerfilURI_Servicio() {
        return fotodePerfilURI_Servicio;
    }

    public void setFotodePerfilURI_Servicio(String fotodePerfilURI_Servicio) {
        this.fotodePerfilURI_Servicio = fotodePerfilURI_Servicio;
    }

    public String getFotode_servicio_URI() {
        return fotode_servicio_URI;
    }

    public void setFotode_servicio_URI(String fotode_servicio_URI) {
        this.fotode_servicio_URI = fotode_servicio_URI;
    }

    public String getNombre_emisor() {
        return nombre_emisor;
    }

    public void setNombre_emisor(String nombre_emisor) {
        this.nombre_emisor = nombre_emisor;
    }

    public String getNombre_servicio() {
        return nombre_servicio;
    }

    public void setNombre_servicio(String nombre_servicio) {
        this.nombre_servicio = nombre_servicio;
    }

    public String getUbicacion() {
        return ubicacion;
    }

    public void setUbicacion(String ubicacion) {
        this.ubicacion = ubicacion;
    }

    public String getPrecio() {
        return precio;
    }

    public void setPrecio(String precio) {
        this.precio = precio;
    }

    public String getTiempo_proceso() {
        return tiempo_proceso;
    }

    public void setTiempo_proceso(String tiempo_proceso) {
        this.tiempo_proceso = tiempo_proceso;
    }

    public String getKey_servicio() {
        return key_servicio;
    }

    public void setKey_servicio(String key_servicio) {
        this.key_servicio = key_servicio;
    }



}


