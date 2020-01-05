package com.example.agenda_salonvip.Entidades_fire;

public class Clientes {


    private String fotodePerfilURI;
    private   String usuario_cliente;
    private String correo_clilente;
    private String telefono_cliente;
    private  Long fechaDeNacimiento;
    private String key_cita;



    public Clientes() {

    }

    public Clientes(String fotodePerfilURI, String usuario_cliente, String telefono_cliente) {
        this.fotodePerfilURI = fotodePerfilURI;
        this.usuario_cliente = usuario_cliente;
        this.telefono_cliente = telefono_cliente;
    }



    public String getKey_cita() {
        return key_cita;
    }

    public void setKey_cita(String key_cita) {
        this.key_cita = key_cita;
    }

    public String getTelefono_cliente() {
        return telefono_cliente;
    }

    public void setTelefono_cliente(String telefono_cliente) {
        this.telefono_cliente = telefono_cliente;
    }

    public String getFotodePerfilURI() {
        return fotodePerfilURI;
    }

    public void setFotodePerfilURI(String fotodePerfilURI) {
        this.fotodePerfilURI = fotodePerfilURI;
    }

    public String getUsuario_cliente() {
        return usuario_cliente;
    }

    public void setUsuario_cliente(String usuario_cliente) {
        this.usuario_cliente = usuario_cliente;
    }

    public String getCorreo_clilente() {
        return correo_clilente;
    }

    public void setCorreo_clilente(String correo_clilente) {
        this.correo_clilente = correo_clilente;
    }

    public Long getFechaDeNacimiento() {
        return fechaDeNacimiento;
    }

    public void setFechaDeNacimiento(Long fechaDeNacimiento) {
        this.fechaDeNacimiento = fechaDeNacimiento;
    }


}
