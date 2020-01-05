package com.example.agenda_salonvip.Holder;

import android.net.Uri;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.example.agenda_salonvip.R;

import de.hdodenhof.circleimageview.CircleImageView;

public class Mensajes_holder extends RecyclerView.ViewHolder {


    private TextView nombre;
    private  TextView mensaje_enviado;
    private TextView hora;
    private CircleImageView fotomensaje_foto_perfil;
    private ImageView foto_enviada_en_mensaje;
    private Uri urifotos;

    public Mensajes_holder(View itemView){

        super(itemView);
        //como se llama al methodo padre debemos llamar al tipo de vista del objeto
        nombre=(TextView)itemView.findViewById(R.id.nombre_mensaje);
        mensaje_enviado=(TextView)itemView.findViewById(R.id.mensajes_mensaje);
        hora=(TextView)itemView.findViewById(R.id.hora_mensaje);
        fotomensaje_foto_perfil=(CircleImageView) itemView.findViewById(R.id.foto_perfil_mensaje);
        foto_enviada_en_mensaje=(ImageView)itemView.findViewById(R.id.mensajes_foto);
    }


    public Uri getUrifotos() {
        return urifotos;
    }

    public void setUrifotos(Uri urifotos) {
        this.urifotos = urifotos;
    }

    public TextView getNombre() {
        return nombre;
    }

    public void setNombre(TextView nombre) {
        this.nombre = nombre;
    }

    public TextView getMensaje_enviado() {//el Mensaje que se envia el campo
        return mensaje_enviado;
    }

    public void setMensaje_enviado(TextView mensaje_enviado) {
        this.mensaje_enviado = mensaje_enviado;
    }

    public TextView getHora() {
        return hora;
    }

    public void setHora(TextView hora) {
        this.hora = hora;
    }

    public CircleImageView getFotomensaje_foto_perfil() {
        return fotomensaje_foto_perfil;
    }

    public void setFotomensaje_foto_perfil(CircleImageView fotomensaje_foto_perfil) {
        this.fotomensaje_foto_perfil = fotomensaje_foto_perfil;
    }

    public ImageView getFoto_enviada_en_mensaje() {
        return foto_enviada_en_mensaje;
    }

    public void setFoto_enviada_en_mensaje(ImageView foto_enviada_en_mensaje) {
        this.foto_enviada_en_mensaje = foto_enviada_en_mensaje;
    }

}
