package com.example.agenda_salonvip.Entidades_fire;

public class Usuario {


    private String fotodePerfilURI;
    private   String usuario_nombre;
    private String correo_usuario;
    private  Long fechaDeNacimiento;
    private String genero;
    private  String profesion;
    private  String habilidad_profesional;
    private  String teleono_usuario;
    private  Object create_time_tamp;


    public Usuario() {



    }

    public String getFotodePerfilURI() {
        return fotodePerfilURI;
    }

    public void setFotodePerfilURI(String fotodePerfilURI) {
        this.fotodePerfilURI = fotodePerfilURI;
    }

    public String getUsuario_nombre() {
        return usuario_nombre;
    }

    public void setUsuario_nombre(String usuario_nombre) {
        this.usuario_nombre = usuario_nombre;
    }

    public String getProfesion() {
        return profesion;
    }

    public String getHabilidad_profesional() {
        return habilidad_profesional;
    }

    public void setHabilidad_profesional(String habilidad_profesional) {
        this.habilidad_profesional = habilidad_profesional;
    }

    public void setProfesion(String profesion) {
        this.profesion = profesion;
    }

    public String getTeleono_usuario() {
        return teleono_usuario;
    }

    public void setTeleono_usuario(String teleono_usuario) {
        this.teleono_usuario = teleono_usuario;
    }

    public String getCorreo_usuario() {
        return correo_usuario;
    }

    public void setCorreo_usuario(String correo_usuario) {
        this.correo_usuario = correo_usuario;
    }


    public Long getFechaDeNacimiento() {
        return fechaDeNacimiento;
    }

    public void setFechaDeNacimiento(Long fechaDeNacimiento) {
        this.fechaDeNacimiento = fechaDeNacimiento;
    }
    public Usuario(String correo_usuario, String usuario_nombre) {
        this.correo_usuario = correo_usuario;
        this.usuario_nombre = usuario_nombre;

    }

    public String getGenero() {
        return genero;
    }

    public void setGenero(String genero) {
        this.genero = genero;
    }

    public Object getCreate_time_tamp() {
        return create_time_tamp;
    }

    public void setCreate_time_tamp(Object create_time_tamp) {
        this.create_time_tamp = create_time_tamp;
    }
}
