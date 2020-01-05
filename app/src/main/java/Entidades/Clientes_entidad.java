package Entidades;

public class Clientes_entidad {
    int foto;
    String nombre;
    String info;


    public Clientes_entidad(int foto, String nombre, String info) {
        this.foto = foto;
        this.nombre = nombre;
        this.info = info;
    }

    public int getFoto() {
        return foto;
    }

    public void setFoto(int foto) {
        this.foto = foto;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getInfo() {
        return info;
    }

    public void setInfo(String info) {
        this.info = info;
    }
}
