package com.example.agenda_salonvip.Entidades_logicas;

import com.example.agenda_salonvip.Entidades_fire.Usuario;
import com.example.agenda_salonvip.persistencias_fire.Usuario_DAO;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class LUsuario {
    private String key;
    public Usuario usuarioss;

    public LUsuario(String key, Usuario usuario) {
        this.key = key;
        this.usuarioss=usuario;
    }





    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public Usuario getUsuarioss() {
        return usuarioss;
    }


    public void setUsuarioss(Usuario usuarioss) {
        this.usuarioss = usuarioss;
    }

    public String obte_fecha_de_creacion(){ //este esta conectado con persistencia firebase usuario_DAO
        SimpleDateFormat simpleDateFormat=new SimpleDateFormat("dd//MM/yyyy", Locale.getDefault());
        Date date=new Date(Usuario_DAO.getInstance().fechadeCreacionCuenta());
        return simpleDateFormat.format(date);

    }
    public String obte_fecha_de_ultima_logeada(){ //este esta conectado con persistencia firebase usuario_DAO
        SimpleDateFormat simpleDateFormat=new SimpleDateFormat("dd//MM/yyyy",Locale.getDefault());
        Date date=new Date(Usuario_DAO.getInstance().ultimaFechaConexion());
        return simpleDateFormat.format(date);

    }
}
